package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.IndContact;
import com.apptium.customer.domain.IndContactChar;
import com.apptium.customer.domain.Individual;
import com.apptium.customer.repository.IndContactRepository;
import com.apptium.customer.service.criteria.IndContactCriteria;
import com.apptium.customer.service.dto.IndContactDTO;
import com.apptium.customer.service.mapper.IndContactMapper;
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
 * Integration tests for the {@link IndContactResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IndContactResourceIT {

    private static final String DEFAULT_PREFERRED = "AAAAAAAAAA";
    private static final String UPDATED_PREFERRED = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_INDIVIDUAL_ID = 1L;
    private static final Long UPDATED_INDIVIDUAL_ID = 2L;
    private static final Long SMALLER_INDIVIDUAL_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/ind-contacts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IndContactRepository indContactRepository;

    @Autowired
    private IndContactMapper indContactMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndContactMockMvc;

    private IndContact indContact;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndContact createEntity(EntityManager em) {
        IndContact indContact = new IndContact()
            .preferred(DEFAULT_PREFERRED)
            .type(DEFAULT_TYPE)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO)
            .individualId(DEFAULT_INDIVIDUAL_ID);
        return indContact;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndContact createUpdatedEntity(EntityManager em) {
        IndContact indContact = new IndContact()
            .preferred(UPDATED_PREFERRED)
            .type(UPDATED_TYPE)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .individualId(UPDATED_INDIVIDUAL_ID);
        return indContact;
    }

    @BeforeEach
    public void initTest() {
        indContact = createEntity(em);
    }

    @Test
    @Transactional
    void createIndContact() throws Exception {
        int databaseSizeBeforeCreate = indContactRepository.findAll().size();
        // Create the IndContact
        IndContactDTO indContactDTO = indContactMapper.toDto(indContact);
        restIndContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactDTO)))
            .andExpect(status().isCreated());

        // Validate the IndContact in the database
        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeCreate + 1);
        IndContact testIndContact = indContactList.get(indContactList.size() - 1);
        assertThat(testIndContact.getPreferred()).isEqualTo(DEFAULT_PREFERRED);
        assertThat(testIndContact.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testIndContact.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testIndContact.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
        assertThat(testIndContact.getIndividualId()).isEqualTo(DEFAULT_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void createIndContactWithExistingId() throws Exception {
        // Create the IndContact with an existing ID
        indContact.setId(1L);
        IndContactDTO indContactDTO = indContactMapper.toDto(indContact);

        int databaseSizeBeforeCreate = indContactRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IndContact in the database
        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPreferredIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactRepository.findAll().size();
        // set the field null
        indContact.setPreferred(null);

        // Create the IndContact, which fails.
        IndContactDTO indContactDTO = indContactMapper.toDto(indContact);

        restIndContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactDTO)))
            .andExpect(status().isBadRequest());

        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactRepository.findAll().size();
        // set the field null
        indContact.setType(null);

        // Create the IndContact, which fails.
        IndContactDTO indContactDTO = indContactMapper.toDto(indContact);

        restIndContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactDTO)))
            .andExpect(status().isBadRequest());

        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactRepository.findAll().size();
        // set the field null
        indContact.setValidFrom(null);

        // Create the IndContact, which fails.
        IndContactDTO indContactDTO = indContactMapper.toDto(indContact);

        restIndContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactDTO)))
            .andExpect(status().isBadRequest());

        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactRepository.findAll().size();
        // set the field null
        indContact.setValidTo(null);

        // Create the IndContact, which fails.
        IndContactDTO indContactDTO = indContactMapper.toDto(indContact);

        restIndContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactDTO)))
            .andExpect(status().isBadRequest());

        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIndividualIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactRepository.findAll().size();
        // set the field null
        indContact.setIndividualId(null);

        // Create the IndContact, which fails.
        IndContactDTO indContactDTO = indContactMapper.toDto(indContact);

        restIndContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactDTO)))
            .andExpect(status().isBadRequest());

        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIndContacts() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get all the indContactList
        restIndContactMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].preferred").value(hasItem(DEFAULT_PREFERRED)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())))
            .andExpect(jsonPath("$.[*].individualId").value(hasItem(DEFAULT_INDIVIDUAL_ID.intValue())));
    }

    @Test
    @Transactional
    void getIndContact() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get the indContact
        restIndContactMockMvc
            .perform(get(ENTITY_API_URL_ID, indContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(indContact.getId().intValue()))
            .andExpect(jsonPath("$.preferred").value(DEFAULT_PREFERRED))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()))
            .andExpect(jsonPath("$.individualId").value(DEFAULT_INDIVIDUAL_ID.intValue()));
    }

    @Test
    @Transactional
    void getIndContactsByIdFiltering() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        Long id = indContact.getId();

        defaultIndContactShouldBeFound("id.equals=" + id);
        defaultIndContactShouldNotBeFound("id.notEquals=" + id);

        defaultIndContactShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultIndContactShouldNotBeFound("id.greaterThan=" + id);

        defaultIndContactShouldBeFound("id.lessThanOrEqual=" + id);
        defaultIndContactShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllIndContactsByPreferredIsEqualToSomething() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get all the indContactList where preferred equals to DEFAULT_PREFERRED
        defaultIndContactShouldBeFound("preferred.equals=" + DEFAULT_PREFERRED);

        // Get all the indContactList where preferred equals to UPDATED_PREFERRED
        defaultIndContactShouldNotBeFound("preferred.equals=" + UPDATED_PREFERRED);
    }

    @Test
    @Transactional
    void getAllIndContactsByPreferredIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get all the indContactList where preferred not equals to DEFAULT_PREFERRED
        defaultIndContactShouldNotBeFound("preferred.notEquals=" + DEFAULT_PREFERRED);

        // Get all the indContactList where preferred not equals to UPDATED_PREFERRED
        defaultIndContactShouldBeFound("preferred.notEquals=" + UPDATED_PREFERRED);
    }

    @Test
    @Transactional
    void getAllIndContactsByPreferredIsInShouldWork() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get all the indContactList where preferred in DEFAULT_PREFERRED or UPDATED_PREFERRED
        defaultIndContactShouldBeFound("preferred.in=" + DEFAULT_PREFERRED + "," + UPDATED_PREFERRED);

        // Get all the indContactList where preferred equals to UPDATED_PREFERRED
        defaultIndContactShouldNotBeFound("preferred.in=" + UPDATED_PREFERRED);
    }

    @Test
    @Transactional
    void getAllIndContactsByPreferredIsNullOrNotNull() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get all the indContactList where preferred is not null
        defaultIndContactShouldBeFound("preferred.specified=true");

        // Get all the indContactList where preferred is null
        defaultIndContactShouldNotBeFound("preferred.specified=false");
    }

    @Test
    @Transactional
    void getAllIndContactsByPreferredContainsSomething() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get all the indContactList where preferred contains DEFAULT_PREFERRED
        defaultIndContactShouldBeFound("preferred.contains=" + DEFAULT_PREFERRED);

        // Get all the indContactList where preferred contains UPDATED_PREFERRED
        defaultIndContactShouldNotBeFound("preferred.contains=" + UPDATED_PREFERRED);
    }

    @Test
    @Transactional
    void getAllIndContactsByPreferredNotContainsSomething() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get all the indContactList where preferred does not contain DEFAULT_PREFERRED
        defaultIndContactShouldNotBeFound("preferred.doesNotContain=" + DEFAULT_PREFERRED);

        // Get all the indContactList where preferred does not contain UPDATED_PREFERRED
        defaultIndContactShouldBeFound("preferred.doesNotContain=" + UPDATED_PREFERRED);
    }

    @Test
    @Transactional
    void getAllIndContactsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get all the indContactList where type equals to DEFAULT_TYPE
        defaultIndContactShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the indContactList where type equals to UPDATED_TYPE
        defaultIndContactShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllIndContactsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get all the indContactList where type not equals to DEFAULT_TYPE
        defaultIndContactShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the indContactList where type not equals to UPDATED_TYPE
        defaultIndContactShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllIndContactsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get all the indContactList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultIndContactShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the indContactList where type equals to UPDATED_TYPE
        defaultIndContactShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllIndContactsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get all the indContactList where type is not null
        defaultIndContactShouldBeFound("type.specified=true");

        // Get all the indContactList where type is null
        defaultIndContactShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllIndContactsByTypeContainsSomething() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get all the indContactList where type contains DEFAULT_TYPE
        defaultIndContactShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the indContactList where type contains UPDATED_TYPE
        defaultIndContactShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllIndContactsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get all the indContactList where type does not contain DEFAULT_TYPE
        defaultIndContactShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the indContactList where type does not contain UPDATED_TYPE
        defaultIndContactShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllIndContactsByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get all the indContactList where validFrom equals to DEFAULT_VALID_FROM
        defaultIndContactShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the indContactList where validFrom equals to UPDATED_VALID_FROM
        defaultIndContactShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    void getAllIndContactsByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get all the indContactList where validFrom not equals to DEFAULT_VALID_FROM
        defaultIndContactShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the indContactList where validFrom not equals to UPDATED_VALID_FROM
        defaultIndContactShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    void getAllIndContactsByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get all the indContactList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultIndContactShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the indContactList where validFrom equals to UPDATED_VALID_FROM
        defaultIndContactShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    void getAllIndContactsByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get all the indContactList where validFrom is not null
        defaultIndContactShouldBeFound("validFrom.specified=true");

        // Get all the indContactList where validFrom is null
        defaultIndContactShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    void getAllIndContactsByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get all the indContactList where validTo equals to DEFAULT_VALID_TO
        defaultIndContactShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the indContactList where validTo equals to UPDATED_VALID_TO
        defaultIndContactShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    void getAllIndContactsByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get all the indContactList where validTo not equals to DEFAULT_VALID_TO
        defaultIndContactShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the indContactList where validTo not equals to UPDATED_VALID_TO
        defaultIndContactShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    void getAllIndContactsByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get all the indContactList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultIndContactShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the indContactList where validTo equals to UPDATED_VALID_TO
        defaultIndContactShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    void getAllIndContactsByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get all the indContactList where validTo is not null
        defaultIndContactShouldBeFound("validTo.specified=true");

        // Get all the indContactList where validTo is null
        defaultIndContactShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    void getAllIndContactsByIndividualIdIsEqualToSomething() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get all the indContactList where individualId equals to DEFAULT_INDIVIDUAL_ID
        defaultIndContactShouldBeFound("individualId.equals=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the indContactList where individualId equals to UPDATED_INDIVIDUAL_ID
        defaultIndContactShouldNotBeFound("individualId.equals=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndContactsByIndividualIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get all the indContactList where individualId not equals to DEFAULT_INDIVIDUAL_ID
        defaultIndContactShouldNotBeFound("individualId.notEquals=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the indContactList where individualId not equals to UPDATED_INDIVIDUAL_ID
        defaultIndContactShouldBeFound("individualId.notEquals=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndContactsByIndividualIdIsInShouldWork() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get all the indContactList where individualId in DEFAULT_INDIVIDUAL_ID or UPDATED_INDIVIDUAL_ID
        defaultIndContactShouldBeFound("individualId.in=" + DEFAULT_INDIVIDUAL_ID + "," + UPDATED_INDIVIDUAL_ID);

        // Get all the indContactList where individualId equals to UPDATED_INDIVIDUAL_ID
        defaultIndContactShouldNotBeFound("individualId.in=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndContactsByIndividualIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get all the indContactList where individualId is not null
        defaultIndContactShouldBeFound("individualId.specified=true");

        // Get all the indContactList where individualId is null
        defaultIndContactShouldNotBeFound("individualId.specified=false");
    }

    @Test
    @Transactional
    void getAllIndContactsByIndividualIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get all the indContactList where individualId is greater than or equal to DEFAULT_INDIVIDUAL_ID
        defaultIndContactShouldBeFound("individualId.greaterThanOrEqual=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the indContactList where individualId is greater than or equal to UPDATED_INDIVIDUAL_ID
        defaultIndContactShouldNotBeFound("individualId.greaterThanOrEqual=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndContactsByIndividualIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get all the indContactList where individualId is less than or equal to DEFAULT_INDIVIDUAL_ID
        defaultIndContactShouldBeFound("individualId.lessThanOrEqual=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the indContactList where individualId is less than or equal to SMALLER_INDIVIDUAL_ID
        defaultIndContactShouldNotBeFound("individualId.lessThanOrEqual=" + SMALLER_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndContactsByIndividualIdIsLessThanSomething() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get all the indContactList where individualId is less than DEFAULT_INDIVIDUAL_ID
        defaultIndContactShouldNotBeFound("individualId.lessThan=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the indContactList where individualId is less than UPDATED_INDIVIDUAL_ID
        defaultIndContactShouldBeFound("individualId.lessThan=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndContactsByIndividualIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        // Get all the indContactList where individualId is greater than DEFAULT_INDIVIDUAL_ID
        defaultIndContactShouldNotBeFound("individualId.greaterThan=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the indContactList where individualId is greater than SMALLER_INDIVIDUAL_ID
        defaultIndContactShouldBeFound("individualId.greaterThan=" + SMALLER_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndContactsByIndContactCharIsEqualToSomething() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);
        IndContactChar indContactChar = IndContactCharResourceIT.createEntity(em);
        em.persist(indContactChar);
        em.flush();
        indContact.addIndContactChar(indContactChar);
        indContactRepository.saveAndFlush(indContact);
        Long indContactCharId = indContactChar.getId();

        // Get all the indContactList where indContactChar equals to indContactCharId
        defaultIndContactShouldBeFound("indContactCharId.equals=" + indContactCharId);

        // Get all the indContactList where indContactChar equals to (indContactCharId + 1)
        defaultIndContactShouldNotBeFound("indContactCharId.equals=" + (indContactCharId + 1));
    }

    @Test
    @Transactional
    void getAllIndContactsByIndividualIsEqualToSomething() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);
        Individual individual = IndividualResourceIT.createEntity(em);
        em.persist(individual);
        em.flush();
        indContact.setIndividual(individual);
        indContactRepository.saveAndFlush(indContact);
        Long individualId = individual.getId();

        // Get all the indContactList where individual equals to individualId
        defaultIndContactShouldBeFound("individualId.equals=" + individualId);

        // Get all the indContactList where individual equals to (individualId + 1)
        defaultIndContactShouldNotBeFound("individualId.equals=" + (individualId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultIndContactShouldBeFound(String filter) throws Exception {
        restIndContactMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indContact.getId().intValue())))
            .andExpect(jsonPath("$.[*].preferred").value(hasItem(DEFAULT_PREFERRED)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())))
            .andExpect(jsonPath("$.[*].individualId").value(hasItem(DEFAULT_INDIVIDUAL_ID.intValue())));

        // Check, that the count call also returns 1
        restIndContactMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultIndContactShouldNotBeFound(String filter) throws Exception {
        restIndContactMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIndContactMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingIndContact() throws Exception {
        // Get the indContact
        restIndContactMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIndContact() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        int databaseSizeBeforeUpdate = indContactRepository.findAll().size();

        // Update the indContact
        IndContact updatedIndContact = indContactRepository.findById(indContact.getId()).get();
        // Disconnect from session so that the updates on updatedIndContact are not directly saved in db
        em.detach(updatedIndContact);
        updatedIndContact
            .preferred(UPDATED_PREFERRED)
            .type(UPDATED_TYPE)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .individualId(UPDATED_INDIVIDUAL_ID);
        IndContactDTO indContactDTO = indContactMapper.toDto(updatedIndContact);

        restIndContactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indContactDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indContactDTO))
            )
            .andExpect(status().isOk());

        // Validate the IndContact in the database
        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeUpdate);
        IndContact testIndContact = indContactList.get(indContactList.size() - 1);
        assertThat(testIndContact.getPreferred()).isEqualTo(UPDATED_PREFERRED);
        assertThat(testIndContact.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testIndContact.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testIndContact.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testIndContact.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void putNonExistingIndContact() throws Exception {
        int databaseSizeBeforeUpdate = indContactRepository.findAll().size();
        indContact.setId(count.incrementAndGet());

        // Create the IndContact
        IndContactDTO indContactDTO = indContactMapper.toDto(indContact);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndContactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indContactDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indContactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndContact in the database
        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIndContact() throws Exception {
        int databaseSizeBeforeUpdate = indContactRepository.findAll().size();
        indContact.setId(count.incrementAndGet());

        // Create the IndContact
        IndContactDTO indContactDTO = indContactMapper.toDto(indContact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndContactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indContactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndContact in the database
        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIndContact() throws Exception {
        int databaseSizeBeforeUpdate = indContactRepository.findAll().size();
        indContact.setId(count.incrementAndGet());

        // Create the IndContact
        IndContactDTO indContactDTO = indContactMapper.toDto(indContact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndContactMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the IndContact in the database
        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIndContactWithPatch() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        int databaseSizeBeforeUpdate = indContactRepository.findAll().size();

        // Update the indContact using partial update
        IndContact partialUpdatedIndContact = new IndContact();
        partialUpdatedIndContact.setId(indContact.getId());

        partialUpdatedIndContact
            .preferred(UPDATED_PREFERRED)
            .type(UPDATED_TYPE)
            .validFrom(UPDATED_VALID_FROM)
            .individualId(UPDATED_INDIVIDUAL_ID);

        restIndContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndContact.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndContact))
            )
            .andExpect(status().isOk());

        // Validate the IndContact in the database
        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeUpdate);
        IndContact testIndContact = indContactList.get(indContactList.size() - 1);
        assertThat(testIndContact.getPreferred()).isEqualTo(UPDATED_PREFERRED);
        assertThat(testIndContact.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testIndContact.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testIndContact.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
        assertThat(testIndContact.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void fullUpdateIndContactWithPatch() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        int databaseSizeBeforeUpdate = indContactRepository.findAll().size();

        // Update the indContact using partial update
        IndContact partialUpdatedIndContact = new IndContact();
        partialUpdatedIndContact.setId(indContact.getId());

        partialUpdatedIndContact
            .preferred(UPDATED_PREFERRED)
            .type(UPDATED_TYPE)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .individualId(UPDATED_INDIVIDUAL_ID);

        restIndContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndContact.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndContact))
            )
            .andExpect(status().isOk());

        // Validate the IndContact in the database
        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeUpdate);
        IndContact testIndContact = indContactList.get(indContactList.size() - 1);
        assertThat(testIndContact.getPreferred()).isEqualTo(UPDATED_PREFERRED);
        assertThat(testIndContact.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testIndContact.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testIndContact.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testIndContact.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void patchNonExistingIndContact() throws Exception {
        int databaseSizeBeforeUpdate = indContactRepository.findAll().size();
        indContact.setId(count.incrementAndGet());

        // Create the IndContact
        IndContactDTO indContactDTO = indContactMapper.toDto(indContact);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, indContactDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indContactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndContact in the database
        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIndContact() throws Exception {
        int databaseSizeBeforeUpdate = indContactRepository.findAll().size();
        indContact.setId(count.incrementAndGet());

        // Create the IndContact
        IndContactDTO indContactDTO = indContactMapper.toDto(indContact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indContactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndContact in the database
        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIndContact() throws Exception {
        int databaseSizeBeforeUpdate = indContactRepository.findAll().size();
        indContact.setId(count.incrementAndGet());

        // Create the IndContact
        IndContactDTO indContactDTO = indContactMapper.toDto(indContact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndContactMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(indContactDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IndContact in the database
        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIndContact() throws Exception {
        // Initialize the database
        indContactRepository.saveAndFlush(indContact);

        int databaseSizeBeforeDelete = indContactRepository.findAll().size();

        // Delete the indContact
        restIndContactMockMvc
            .perform(delete(ENTITY_API_URL_ID, indContact.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IndContact> indContactList = indContactRepository.findAll();
        assertThat(indContactList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
