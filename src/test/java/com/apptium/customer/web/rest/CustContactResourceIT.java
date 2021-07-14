package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.CustContact;
import com.apptium.customer.domain.CustContactChar;
import com.apptium.customer.domain.Customer;
import com.apptium.customer.domain.GeographicSiteRef;
import com.apptium.customer.repository.CustContactRepository;
import com.apptium.customer.service.criteria.CustContactCriteria;
import com.apptium.customer.service.dto.CustContactDTO;
import com.apptium.customer.service.mapper.CustContactMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CustContactResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustContactResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PREFERRED = false;
    private static final Boolean UPDATED_PREFERRED = true;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;
    private static final Long SMALLER_CUSTOMER_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/cust-contacts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustContactRepository custContactRepository;

    @Autowired
    private CustContactMapper custContactMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustContactMockMvc;

    private CustContact custContact;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustContact createEntity(EntityManager em) {
        CustContact custContact = new CustContact()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .preferred(DEFAULT_PREFERRED)
            .type(DEFAULT_TYPE)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO)
            .customerId(DEFAULT_CUSTOMER_ID);
        return custContact;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustContact createUpdatedEntity(EntityManager em) {
        CustContact custContact = new CustContact()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .preferred(UPDATED_PREFERRED)
            .type(UPDATED_TYPE)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .customerId(UPDATED_CUSTOMER_ID);
        return custContact;
    }

    @BeforeEach
    public void initTest() {
        custContact = createEntity(em);
    }

    @Test
    @Transactional
    void createCustContact() throws Exception {
        int databaseSizeBeforeCreate = custContactRepository.findAll().size();
        // Create the CustContact
        CustContactDTO custContactDTO = custContactMapper.toDto(custContact);
        restCustContactMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CustContact in the database
        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeCreate + 1);
        CustContact testCustContact = custContactList.get(custContactList.size() - 1);
        assertThat(testCustContact.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustContact.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCustContact.getPreferred()).isEqualTo(DEFAULT_PREFERRED);
        assertThat(testCustContact.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCustContact.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testCustContact.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
        assertThat(testCustContact.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void createCustContactWithExistingId() throws Exception {
        // Create the CustContact with an existing ID
        custContact.setId(1L);
        CustContactDTO custContactDTO = custContactMapper.toDto(custContact);

        int databaseSizeBeforeCreate = custContactRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustContactMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustContact in the database
        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactRepository.findAll().size();
        // set the field null
        custContact.setName(null);

        // Create the CustContact, which fails.
        CustContactDTO custContactDTO = custContactMapper.toDto(custContact);

        restCustContactMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPreferredIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactRepository.findAll().size();
        // set the field null
        custContact.setPreferred(null);

        // Create the CustContact, which fails.
        CustContactDTO custContactDTO = custContactMapper.toDto(custContact);

        restCustContactMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactRepository.findAll().size();
        // set the field null
        custContact.setType(null);

        // Create the CustContact, which fails.
        CustContactDTO custContactDTO = custContactMapper.toDto(custContact);

        restCustContactMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactRepository.findAll().size();
        // set the field null
        custContact.setValidFrom(null);

        // Create the CustContact, which fails.
        CustContactDTO custContactDTO = custContactMapper.toDto(custContact);

        restCustContactMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactRepository.findAll().size();
        // set the field null
        custContact.setValidTo(null);

        // Create the CustContact, which fails.
        CustContactDTO custContactDTO = custContactMapper.toDto(custContact);

        restCustContactMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactRepository.findAll().size();
        // set the field null
        custContact.setCustomerId(null);

        // Create the CustContact, which fails.
        CustContactDTO custContactDTO = custContactMapper.toDto(custContact);

        restCustContactMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustContacts() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList
        restCustContactMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].preferred").value(hasItem(DEFAULT_PREFERRED.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())));
    }

    @Test
    @Transactional
    void getCustContact() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get the custContact
        restCustContactMockMvc
            .perform(get(ENTITY_API_URL_ID, custContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custContact.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.preferred").value(DEFAULT_PREFERRED.booleanValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()));
    }

    @Test
    @Transactional
    void getCustContactsByIdFiltering() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        Long id = custContact.getId();

        defaultCustContactShouldBeFound("id.equals=" + id);
        defaultCustContactShouldNotBeFound("id.notEquals=" + id);

        defaultCustContactShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustContactShouldNotBeFound("id.greaterThan=" + id);

        defaultCustContactShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustContactShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustContactsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where name equals to DEFAULT_NAME
        defaultCustContactShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the custContactList where name equals to UPDATED_NAME
        defaultCustContactShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustContactsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where name not equals to DEFAULT_NAME
        defaultCustContactShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the custContactList where name not equals to UPDATED_NAME
        defaultCustContactShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustContactsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCustContactShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the custContactList where name equals to UPDATED_NAME
        defaultCustContactShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustContactsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where name is not null
        defaultCustContactShouldBeFound("name.specified=true");

        // Get all the custContactList where name is null
        defaultCustContactShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCustContactsByNameContainsSomething() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where name contains DEFAULT_NAME
        defaultCustContactShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the custContactList where name contains UPDATED_NAME
        defaultCustContactShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustContactsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where name does not contain DEFAULT_NAME
        defaultCustContactShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the custContactList where name does not contain UPDATED_NAME
        defaultCustContactShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustContactsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where description equals to DEFAULT_DESCRIPTION
        defaultCustContactShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the custContactList where description equals to UPDATED_DESCRIPTION
        defaultCustContactShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCustContactsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where description not equals to DEFAULT_DESCRIPTION
        defaultCustContactShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the custContactList where description not equals to UPDATED_DESCRIPTION
        defaultCustContactShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCustContactsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCustContactShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the custContactList where description equals to UPDATED_DESCRIPTION
        defaultCustContactShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCustContactsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where description is not null
        defaultCustContactShouldBeFound("description.specified=true");

        // Get all the custContactList where description is null
        defaultCustContactShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllCustContactsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where description contains DEFAULT_DESCRIPTION
        defaultCustContactShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the custContactList where description contains UPDATED_DESCRIPTION
        defaultCustContactShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCustContactsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where description does not contain DEFAULT_DESCRIPTION
        defaultCustContactShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the custContactList where description does not contain UPDATED_DESCRIPTION
        defaultCustContactShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCustContactsByPreferredIsEqualToSomething() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where preferred equals to DEFAULT_PREFERRED
        defaultCustContactShouldBeFound("preferred.equals=" + DEFAULT_PREFERRED);

        // Get all the custContactList where preferred equals to UPDATED_PREFERRED
        defaultCustContactShouldNotBeFound("preferred.equals=" + UPDATED_PREFERRED);
    }

    @Test
    @Transactional
    void getAllCustContactsByPreferredIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where preferred not equals to DEFAULT_PREFERRED
        defaultCustContactShouldNotBeFound("preferred.notEquals=" + DEFAULT_PREFERRED);

        // Get all the custContactList where preferred not equals to UPDATED_PREFERRED
        defaultCustContactShouldBeFound("preferred.notEquals=" + UPDATED_PREFERRED);
    }

    @Test
    @Transactional
    void getAllCustContactsByPreferredIsInShouldWork() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where preferred in DEFAULT_PREFERRED or UPDATED_PREFERRED
        defaultCustContactShouldBeFound("preferred.in=" + DEFAULT_PREFERRED + "," + UPDATED_PREFERRED);

        // Get all the custContactList where preferred equals to UPDATED_PREFERRED
        defaultCustContactShouldNotBeFound("preferred.in=" + UPDATED_PREFERRED);
    }

    @Test
    @Transactional
    void getAllCustContactsByPreferredIsNullOrNotNull() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where preferred is not null
        defaultCustContactShouldBeFound("preferred.specified=true");

        // Get all the custContactList where preferred is null
        defaultCustContactShouldNotBeFound("preferred.specified=false");
    }

    @Test
    @Transactional
    void getAllCustContactsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where type equals to DEFAULT_TYPE
        defaultCustContactShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the custContactList where type equals to UPDATED_TYPE
        defaultCustContactShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCustContactsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where type not equals to DEFAULT_TYPE
        defaultCustContactShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the custContactList where type not equals to UPDATED_TYPE
        defaultCustContactShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCustContactsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultCustContactShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the custContactList where type equals to UPDATED_TYPE
        defaultCustContactShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCustContactsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where type is not null
        defaultCustContactShouldBeFound("type.specified=true");

        // Get all the custContactList where type is null
        defaultCustContactShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllCustContactsByTypeContainsSomething() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where type contains DEFAULT_TYPE
        defaultCustContactShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the custContactList where type contains UPDATED_TYPE
        defaultCustContactShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCustContactsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where type does not contain DEFAULT_TYPE
        defaultCustContactShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the custContactList where type does not contain UPDATED_TYPE
        defaultCustContactShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCustContactsByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where validFrom equals to DEFAULT_VALID_FROM
        defaultCustContactShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the custContactList where validFrom equals to UPDATED_VALID_FROM
        defaultCustContactShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    void getAllCustContactsByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where validFrom not equals to DEFAULT_VALID_FROM
        defaultCustContactShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the custContactList where validFrom not equals to UPDATED_VALID_FROM
        defaultCustContactShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    void getAllCustContactsByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultCustContactShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the custContactList where validFrom equals to UPDATED_VALID_FROM
        defaultCustContactShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    void getAllCustContactsByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where validFrom is not null
        defaultCustContactShouldBeFound("validFrom.specified=true");

        // Get all the custContactList where validFrom is null
        defaultCustContactShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    void getAllCustContactsByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where validTo equals to DEFAULT_VALID_TO
        defaultCustContactShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the custContactList where validTo equals to UPDATED_VALID_TO
        defaultCustContactShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    void getAllCustContactsByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where validTo not equals to DEFAULT_VALID_TO
        defaultCustContactShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the custContactList where validTo not equals to UPDATED_VALID_TO
        defaultCustContactShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    void getAllCustContactsByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultCustContactShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the custContactList where validTo equals to UPDATED_VALID_TO
        defaultCustContactShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    void getAllCustContactsByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where validTo is not null
        defaultCustContactShouldBeFound("validTo.specified=true");

        // Get all the custContactList where validTo is null
        defaultCustContactShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    void getAllCustContactsByCustomerIdIsEqualToSomething() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where customerId equals to DEFAULT_CUSTOMER_ID
        defaultCustContactShouldBeFound("customerId.equals=" + DEFAULT_CUSTOMER_ID);

        // Get all the custContactList where customerId equals to UPDATED_CUSTOMER_ID
        defaultCustContactShouldNotBeFound("customerId.equals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustContactsByCustomerIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where customerId not equals to DEFAULT_CUSTOMER_ID
        defaultCustContactShouldNotBeFound("customerId.notEquals=" + DEFAULT_CUSTOMER_ID);

        // Get all the custContactList where customerId not equals to UPDATED_CUSTOMER_ID
        defaultCustContactShouldBeFound("customerId.notEquals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustContactsByCustomerIdIsInShouldWork() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where customerId in DEFAULT_CUSTOMER_ID or UPDATED_CUSTOMER_ID
        defaultCustContactShouldBeFound("customerId.in=" + DEFAULT_CUSTOMER_ID + "," + UPDATED_CUSTOMER_ID);

        // Get all the custContactList where customerId equals to UPDATED_CUSTOMER_ID
        defaultCustContactShouldNotBeFound("customerId.in=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustContactsByCustomerIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where customerId is not null
        defaultCustContactShouldBeFound("customerId.specified=true");

        // Get all the custContactList where customerId is null
        defaultCustContactShouldNotBeFound("customerId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustContactsByCustomerIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where customerId is greater than or equal to DEFAULT_CUSTOMER_ID
        defaultCustContactShouldBeFound("customerId.greaterThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the custContactList where customerId is greater than or equal to UPDATED_CUSTOMER_ID
        defaultCustContactShouldNotBeFound("customerId.greaterThanOrEqual=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustContactsByCustomerIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where customerId is less than or equal to DEFAULT_CUSTOMER_ID
        defaultCustContactShouldBeFound("customerId.lessThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the custContactList where customerId is less than or equal to SMALLER_CUSTOMER_ID
        defaultCustContactShouldNotBeFound("customerId.lessThanOrEqual=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustContactsByCustomerIdIsLessThanSomething() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where customerId is less than DEFAULT_CUSTOMER_ID
        defaultCustContactShouldNotBeFound("customerId.lessThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the custContactList where customerId is less than UPDATED_CUSTOMER_ID
        defaultCustContactShouldBeFound("customerId.lessThan=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustContactsByCustomerIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        // Get all the custContactList where customerId is greater than DEFAULT_CUSTOMER_ID
        defaultCustContactShouldNotBeFound("customerId.greaterThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the custContactList where customerId is greater than SMALLER_CUSTOMER_ID
        defaultCustContactShouldBeFound("customerId.greaterThan=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustContactsByGeographicSiteRefIsEqualToSomething() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);
        GeographicSiteRef geographicSiteRef = GeographicSiteRefResourceIT.createEntity(em);
        em.persist(geographicSiteRef);
        em.flush();
        custContact.setGeographicSiteRef(geographicSiteRef);
        custContactRepository.saveAndFlush(custContact);
        Long geographicSiteRefId = geographicSiteRef.getId();

        // Get all the custContactList where geographicSiteRef equals to geographicSiteRefId
        defaultCustContactShouldBeFound("geographicSiteRefId.equals=" + geographicSiteRefId);

        // Get all the custContactList where geographicSiteRef equals to (geographicSiteRefId + 1)
        defaultCustContactShouldNotBeFound("geographicSiteRefId.equals=" + (geographicSiteRefId + 1));
    }

    @Test
    @Transactional
    void getAllCustContactsByCustContactCharIsEqualToSomething() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);
        CustContactChar custContactChar = CustContactCharResourceIT.createEntity(em);
        em.persist(custContactChar);
        em.flush();
        custContact.addCustContactChar(custContactChar);
        custContactRepository.saveAndFlush(custContact);
        Long custContactCharId = custContactChar.getId();

        // Get all the custContactList where custContactChar equals to custContactCharId
        defaultCustContactShouldBeFound("custContactCharId.equals=" + custContactCharId);

        // Get all the custContactList where custContactChar equals to (custContactCharId + 1)
        defaultCustContactShouldNotBeFound("custContactCharId.equals=" + (custContactCharId + 1));
    }

    @Test
    @Transactional
    void getAllCustContactsByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);
        Customer customer = CustomerResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        custContact.setCustomer(customer);
        custContactRepository.saveAndFlush(custContact);
        Long customerId = customer.getId();

        // Get all the custContactList where customer equals to customerId
        defaultCustContactShouldBeFound("customerId.equals=" + customerId);

        // Get all the custContactList where customer equals to (customerId + 1)
        defaultCustContactShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustContactShouldBeFound(String filter) throws Exception {
        restCustContactMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].preferred").value(hasItem(DEFAULT_PREFERRED.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())));

        // Check, that the count call also returns 1
        restCustContactMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustContactShouldNotBeFound(String filter) throws Exception {
        restCustContactMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustContactMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustContact() throws Exception {
        // Get the custContact
        restCustContactMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustContact() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        int databaseSizeBeforeUpdate = custContactRepository.findAll().size();

        // Update the custContact
        CustContact updatedCustContact = custContactRepository.findById(custContact.getId()).get();
        // Disconnect from session so that the updates on updatedCustContact are not directly saved in db
        em.detach(updatedCustContact);
        updatedCustContact
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .preferred(UPDATED_PREFERRED)
            .type(UPDATED_TYPE)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .customerId(UPDATED_CUSTOMER_ID);
        CustContactDTO custContactDTO = custContactMapper.toDto(updatedCustContact);

        restCustContactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custContactDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custContactDTO))
            )
            .andExpect(status().isOk());

        // Validate the CustContact in the database
        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeUpdate);
        CustContact testCustContact = custContactList.get(custContactList.size() - 1);
        assertThat(testCustContact.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustContact.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCustContact.getPreferred()).isEqualTo(UPDATED_PREFERRED);
        assertThat(testCustContact.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCustContact.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testCustContact.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testCustContact.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustContact() throws Exception {
        int databaseSizeBeforeUpdate = custContactRepository.findAll().size();
        custContact.setId(count.incrementAndGet());

        // Create the CustContact
        CustContactDTO custContactDTO = custContactMapper.toDto(custContact);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustContactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custContactDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custContactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustContact in the database
        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustContact() throws Exception {
        int databaseSizeBeforeUpdate = custContactRepository.findAll().size();
        custContact.setId(count.incrementAndGet());

        // Create the CustContact
        CustContactDTO custContactDTO = custContactMapper.toDto(custContact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustContactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custContactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustContact in the database
        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustContact() throws Exception {
        int databaseSizeBeforeUpdate = custContactRepository.findAll().size();
        custContact.setId(count.incrementAndGet());

        // Create the CustContact
        CustContactDTO custContactDTO = custContactMapper.toDto(custContact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustContactMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustContact in the database
        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustContactWithPatch() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        int databaseSizeBeforeUpdate = custContactRepository.findAll().size();

        // Update the custContact using partial update
        CustContact partialUpdatedCustContact = new CustContact();
        partialUpdatedCustContact.setId(custContact.getId());

        partialUpdatedCustContact
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .validFrom(UPDATED_VALID_FROM)
            .customerId(UPDATED_CUSTOMER_ID);

        restCustContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustContact.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustContact))
            )
            .andExpect(status().isOk());

        // Validate the CustContact in the database
        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeUpdate);
        CustContact testCustContact = custContactList.get(custContactList.size() - 1);
        assertThat(testCustContact.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustContact.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCustContact.getPreferred()).isEqualTo(DEFAULT_PREFERRED);
        assertThat(testCustContact.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCustContact.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testCustContact.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
        assertThat(testCustContact.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustContactWithPatch() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        int databaseSizeBeforeUpdate = custContactRepository.findAll().size();

        // Update the custContact using partial update
        CustContact partialUpdatedCustContact = new CustContact();
        partialUpdatedCustContact.setId(custContact.getId());

        partialUpdatedCustContact
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .preferred(UPDATED_PREFERRED)
            .type(UPDATED_TYPE)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .customerId(UPDATED_CUSTOMER_ID);

        restCustContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustContact.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustContact))
            )
            .andExpect(status().isOk());

        // Validate the CustContact in the database
        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeUpdate);
        CustContact testCustContact = custContactList.get(custContactList.size() - 1);
        assertThat(testCustContact.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustContact.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCustContact.getPreferred()).isEqualTo(UPDATED_PREFERRED);
        assertThat(testCustContact.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCustContact.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testCustContact.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testCustContact.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustContact() throws Exception {
        int databaseSizeBeforeUpdate = custContactRepository.findAll().size();
        custContact.setId(count.incrementAndGet());

        // Create the CustContact
        CustContactDTO custContactDTO = custContactMapper.toDto(custContact);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custContactDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custContactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustContact in the database
        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustContact() throws Exception {
        int databaseSizeBeforeUpdate = custContactRepository.findAll().size();
        custContact.setId(count.incrementAndGet());

        // Create the CustContact
        CustContactDTO custContactDTO = custContactMapper.toDto(custContact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custContactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustContact in the database
        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustContact() throws Exception {
        int databaseSizeBeforeUpdate = custContactRepository.findAll().size();
        custContact.setId(count.incrementAndGet());

        // Create the CustContact
        CustContactDTO custContactDTO = custContactMapper.toDto(custContact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustContactMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(custContactDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustContact in the database
        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustContact() throws Exception {
        // Initialize the database
        custContactRepository.saveAndFlush(custContact);

        int databaseSizeBeforeDelete = custContactRepository.findAll().size();

        // Delete the custContact
        restCustContactMockMvc
            .perform(delete(ENTITY_API_URL_ID, custContact.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustContact> custContactList = custContactRepository.findAll();
        assertThat(custContactList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
