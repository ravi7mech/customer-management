package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.BankCardType;
import com.apptium.customer.domain.CustBillingRef;
import com.apptium.customer.domain.CustPaymentMethod;
import com.apptium.customer.repository.CustPaymentMethodRepository;
import com.apptium.customer.service.criteria.CustPaymentMethodCriteria;
import com.apptium.customer.service.dto.CustPaymentMethodDTO;
import com.apptium.customer.service.mapper.CustPaymentMethodMapper;
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
 * Integration tests for the {@link CustPaymentMethodResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustPaymentMethodResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PREFERRED = false;
    private static final Boolean UPDATED_PREFERRED = true;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_AUTHORIZATION_CODE = 1;
    private static final Integer UPDATED_AUTHORIZATION_CODE = 2;
    private static final Integer SMALLER_AUTHORIZATION_CODE = 1 - 1;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_STATUS_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STATUS_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;
    private static final Long SMALLER_CUSTOMER_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/cust-payment-methods";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustPaymentMethodRepository custPaymentMethodRepository;

    @Autowired
    private CustPaymentMethodMapper custPaymentMethodMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustPaymentMethodMockMvc;

    private CustPaymentMethod custPaymentMethod;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustPaymentMethod createEntity(EntityManager em) {
        CustPaymentMethod custPaymentMethod = new CustPaymentMethod()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .preferred(DEFAULT_PREFERRED)
            .type(DEFAULT_TYPE)
            .authorizationCode(DEFAULT_AUTHORIZATION_CODE)
            .status(DEFAULT_STATUS)
            .statusDate(DEFAULT_STATUS_DATE)
            .details(DEFAULT_DETAILS)
            .customerId(DEFAULT_CUSTOMER_ID);
        return custPaymentMethod;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustPaymentMethod createUpdatedEntity(EntityManager em) {
        CustPaymentMethod custPaymentMethod = new CustPaymentMethod()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .preferred(UPDATED_PREFERRED)
            .type(UPDATED_TYPE)
            .authorizationCode(UPDATED_AUTHORIZATION_CODE)
            .status(UPDATED_STATUS)
            .statusDate(UPDATED_STATUS_DATE)
            .details(UPDATED_DETAILS)
            .customerId(UPDATED_CUSTOMER_ID);
        return custPaymentMethod;
    }

    @BeforeEach
    public void initTest() {
        custPaymentMethod = createEntity(em);
    }

    @Test
    @Transactional
    void createCustPaymentMethod() throws Exception {
        int databaseSizeBeforeCreate = custPaymentMethodRepository.findAll().size();
        // Create the CustPaymentMethod
        CustPaymentMethodDTO custPaymentMethodDTO = custPaymentMethodMapper.toDto(custPaymentMethod);
        restCustPaymentMethodMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custPaymentMethodDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CustPaymentMethod in the database
        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeCreate + 1);
        CustPaymentMethod testCustPaymentMethod = custPaymentMethodList.get(custPaymentMethodList.size() - 1);
        assertThat(testCustPaymentMethod.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustPaymentMethod.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCustPaymentMethod.getPreferred()).isEqualTo(DEFAULT_PREFERRED);
        assertThat(testCustPaymentMethod.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCustPaymentMethod.getAuthorizationCode()).isEqualTo(DEFAULT_AUTHORIZATION_CODE);
        assertThat(testCustPaymentMethod.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCustPaymentMethod.getStatusDate()).isEqualTo(DEFAULT_STATUS_DATE);
        assertThat(testCustPaymentMethod.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testCustPaymentMethod.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void createCustPaymentMethodWithExistingId() throws Exception {
        // Create the CustPaymentMethod with an existing ID
        custPaymentMethod.setId(1L);
        CustPaymentMethodDTO custPaymentMethodDTO = custPaymentMethodMapper.toDto(custPaymentMethod);

        int databaseSizeBeforeCreate = custPaymentMethodRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustPaymentMethodMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custPaymentMethodDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustPaymentMethod in the database
        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = custPaymentMethodRepository.findAll().size();
        // set the field null
        custPaymentMethod.setName(null);

        // Create the CustPaymentMethod, which fails.
        CustPaymentMethodDTO custPaymentMethodDTO = custPaymentMethodMapper.toDto(custPaymentMethod);

        restCustPaymentMethodMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custPaymentMethodDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPreferredIsRequired() throws Exception {
        int databaseSizeBeforeTest = custPaymentMethodRepository.findAll().size();
        // set the field null
        custPaymentMethod.setPreferred(null);

        // Create the CustPaymentMethod, which fails.
        CustPaymentMethodDTO custPaymentMethodDTO = custPaymentMethodMapper.toDto(custPaymentMethod);

        restCustPaymentMethodMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custPaymentMethodDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = custPaymentMethodRepository.findAll().size();
        // set the field null
        custPaymentMethod.setType(null);

        // Create the CustPaymentMethod, which fails.
        CustPaymentMethodDTO custPaymentMethodDTO = custPaymentMethodMapper.toDto(custPaymentMethod);

        restCustPaymentMethodMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custPaymentMethodDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAuthorizationCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = custPaymentMethodRepository.findAll().size();
        // set the field null
        custPaymentMethod.setAuthorizationCode(null);

        // Create the CustPaymentMethod, which fails.
        CustPaymentMethodDTO custPaymentMethodDTO = custPaymentMethodMapper.toDto(custPaymentMethod);

        restCustPaymentMethodMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custPaymentMethodDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = custPaymentMethodRepository.findAll().size();
        // set the field null
        custPaymentMethod.setStatus(null);

        // Create the CustPaymentMethod, which fails.
        CustPaymentMethodDTO custPaymentMethodDTO = custPaymentMethodMapper.toDto(custPaymentMethod);

        restCustPaymentMethodMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custPaymentMethodDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = custPaymentMethodRepository.findAll().size();
        // set the field null
        custPaymentMethod.setStatusDate(null);

        // Create the CustPaymentMethod, which fails.
        CustPaymentMethodDTO custPaymentMethodDTO = custPaymentMethodMapper.toDto(custPaymentMethod);

        restCustPaymentMethodMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custPaymentMethodDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDetailsIsRequired() throws Exception {
        int databaseSizeBeforeTest = custPaymentMethodRepository.findAll().size();
        // set the field null
        custPaymentMethod.setDetails(null);

        // Create the CustPaymentMethod, which fails.
        CustPaymentMethodDTO custPaymentMethodDTO = custPaymentMethodMapper.toDto(custPaymentMethod);

        restCustPaymentMethodMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custPaymentMethodDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custPaymentMethodRepository.findAll().size();
        // set the field null
        custPaymentMethod.setCustomerId(null);

        // Create the CustPaymentMethod, which fails.
        CustPaymentMethodDTO custPaymentMethodDTO = custPaymentMethodMapper.toDto(custPaymentMethod);

        restCustPaymentMethodMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custPaymentMethodDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethods() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList
        restCustPaymentMethodMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custPaymentMethod.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].preferred").value(hasItem(DEFAULT_PREFERRED.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].authorizationCode").value(hasItem(DEFAULT_AUTHORIZATION_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].statusDate").value(hasItem(DEFAULT_STATUS_DATE.toString())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())));
    }

    @Test
    @Transactional
    void getCustPaymentMethod() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get the custPaymentMethod
        restCustPaymentMethodMockMvc
            .perform(get(ENTITY_API_URL_ID, custPaymentMethod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custPaymentMethod.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.preferred").value(DEFAULT_PREFERRED.booleanValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.authorizationCode").value(DEFAULT_AUTHORIZATION_CODE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.statusDate").value(DEFAULT_STATUS_DATE.toString()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()));
    }

    @Test
    @Transactional
    void getCustPaymentMethodsByIdFiltering() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        Long id = custPaymentMethod.getId();

        defaultCustPaymentMethodShouldBeFound("id.equals=" + id);
        defaultCustPaymentMethodShouldNotBeFound("id.notEquals=" + id);

        defaultCustPaymentMethodShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustPaymentMethodShouldNotBeFound("id.greaterThan=" + id);

        defaultCustPaymentMethodShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustPaymentMethodShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where name equals to DEFAULT_NAME
        defaultCustPaymentMethodShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the custPaymentMethodList where name equals to UPDATED_NAME
        defaultCustPaymentMethodShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where name not equals to DEFAULT_NAME
        defaultCustPaymentMethodShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the custPaymentMethodList where name not equals to UPDATED_NAME
        defaultCustPaymentMethodShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCustPaymentMethodShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the custPaymentMethodList where name equals to UPDATED_NAME
        defaultCustPaymentMethodShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where name is not null
        defaultCustPaymentMethodShouldBeFound("name.specified=true");

        // Get all the custPaymentMethodList where name is null
        defaultCustPaymentMethodShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByNameContainsSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where name contains DEFAULT_NAME
        defaultCustPaymentMethodShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the custPaymentMethodList where name contains UPDATED_NAME
        defaultCustPaymentMethodShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where name does not contain DEFAULT_NAME
        defaultCustPaymentMethodShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the custPaymentMethodList where name does not contain UPDATED_NAME
        defaultCustPaymentMethodShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where description equals to DEFAULT_DESCRIPTION
        defaultCustPaymentMethodShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the custPaymentMethodList where description equals to UPDATED_DESCRIPTION
        defaultCustPaymentMethodShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where description not equals to DEFAULT_DESCRIPTION
        defaultCustPaymentMethodShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the custPaymentMethodList where description not equals to UPDATED_DESCRIPTION
        defaultCustPaymentMethodShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCustPaymentMethodShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the custPaymentMethodList where description equals to UPDATED_DESCRIPTION
        defaultCustPaymentMethodShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where description is not null
        defaultCustPaymentMethodShouldBeFound("description.specified=true");

        // Get all the custPaymentMethodList where description is null
        defaultCustPaymentMethodShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where description contains DEFAULT_DESCRIPTION
        defaultCustPaymentMethodShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the custPaymentMethodList where description contains UPDATED_DESCRIPTION
        defaultCustPaymentMethodShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where description does not contain DEFAULT_DESCRIPTION
        defaultCustPaymentMethodShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the custPaymentMethodList where description does not contain UPDATED_DESCRIPTION
        defaultCustPaymentMethodShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByPreferredIsEqualToSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where preferred equals to DEFAULT_PREFERRED
        defaultCustPaymentMethodShouldBeFound("preferred.equals=" + DEFAULT_PREFERRED);

        // Get all the custPaymentMethodList where preferred equals to UPDATED_PREFERRED
        defaultCustPaymentMethodShouldNotBeFound("preferred.equals=" + UPDATED_PREFERRED);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByPreferredIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where preferred not equals to DEFAULT_PREFERRED
        defaultCustPaymentMethodShouldNotBeFound("preferred.notEquals=" + DEFAULT_PREFERRED);

        // Get all the custPaymentMethodList where preferred not equals to UPDATED_PREFERRED
        defaultCustPaymentMethodShouldBeFound("preferred.notEquals=" + UPDATED_PREFERRED);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByPreferredIsInShouldWork() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where preferred in DEFAULT_PREFERRED or UPDATED_PREFERRED
        defaultCustPaymentMethodShouldBeFound("preferred.in=" + DEFAULT_PREFERRED + "," + UPDATED_PREFERRED);

        // Get all the custPaymentMethodList where preferred equals to UPDATED_PREFERRED
        defaultCustPaymentMethodShouldNotBeFound("preferred.in=" + UPDATED_PREFERRED);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByPreferredIsNullOrNotNull() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where preferred is not null
        defaultCustPaymentMethodShouldBeFound("preferred.specified=true");

        // Get all the custPaymentMethodList where preferred is null
        defaultCustPaymentMethodShouldNotBeFound("preferred.specified=false");
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where type equals to DEFAULT_TYPE
        defaultCustPaymentMethodShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the custPaymentMethodList where type equals to UPDATED_TYPE
        defaultCustPaymentMethodShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where type not equals to DEFAULT_TYPE
        defaultCustPaymentMethodShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the custPaymentMethodList where type not equals to UPDATED_TYPE
        defaultCustPaymentMethodShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultCustPaymentMethodShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the custPaymentMethodList where type equals to UPDATED_TYPE
        defaultCustPaymentMethodShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where type is not null
        defaultCustPaymentMethodShouldBeFound("type.specified=true");

        // Get all the custPaymentMethodList where type is null
        defaultCustPaymentMethodShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByTypeContainsSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where type contains DEFAULT_TYPE
        defaultCustPaymentMethodShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the custPaymentMethodList where type contains UPDATED_TYPE
        defaultCustPaymentMethodShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where type does not contain DEFAULT_TYPE
        defaultCustPaymentMethodShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the custPaymentMethodList where type does not contain UPDATED_TYPE
        defaultCustPaymentMethodShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByAuthorizationCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where authorizationCode equals to DEFAULT_AUTHORIZATION_CODE
        defaultCustPaymentMethodShouldBeFound("authorizationCode.equals=" + DEFAULT_AUTHORIZATION_CODE);

        // Get all the custPaymentMethodList where authorizationCode equals to UPDATED_AUTHORIZATION_CODE
        defaultCustPaymentMethodShouldNotBeFound("authorizationCode.equals=" + UPDATED_AUTHORIZATION_CODE);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByAuthorizationCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where authorizationCode not equals to DEFAULT_AUTHORIZATION_CODE
        defaultCustPaymentMethodShouldNotBeFound("authorizationCode.notEquals=" + DEFAULT_AUTHORIZATION_CODE);

        // Get all the custPaymentMethodList where authorizationCode not equals to UPDATED_AUTHORIZATION_CODE
        defaultCustPaymentMethodShouldBeFound("authorizationCode.notEquals=" + UPDATED_AUTHORIZATION_CODE);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByAuthorizationCodeIsInShouldWork() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where authorizationCode in DEFAULT_AUTHORIZATION_CODE or UPDATED_AUTHORIZATION_CODE
        defaultCustPaymentMethodShouldBeFound("authorizationCode.in=" + DEFAULT_AUTHORIZATION_CODE + "," + UPDATED_AUTHORIZATION_CODE);

        // Get all the custPaymentMethodList where authorizationCode equals to UPDATED_AUTHORIZATION_CODE
        defaultCustPaymentMethodShouldNotBeFound("authorizationCode.in=" + UPDATED_AUTHORIZATION_CODE);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByAuthorizationCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where authorizationCode is not null
        defaultCustPaymentMethodShouldBeFound("authorizationCode.specified=true");

        // Get all the custPaymentMethodList where authorizationCode is null
        defaultCustPaymentMethodShouldNotBeFound("authorizationCode.specified=false");
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByAuthorizationCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where authorizationCode is greater than or equal to DEFAULT_AUTHORIZATION_CODE
        defaultCustPaymentMethodShouldBeFound("authorizationCode.greaterThanOrEqual=" + DEFAULT_AUTHORIZATION_CODE);

        // Get all the custPaymentMethodList where authorizationCode is greater than or equal to UPDATED_AUTHORIZATION_CODE
        defaultCustPaymentMethodShouldNotBeFound("authorizationCode.greaterThanOrEqual=" + UPDATED_AUTHORIZATION_CODE);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByAuthorizationCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where authorizationCode is less than or equal to DEFAULT_AUTHORIZATION_CODE
        defaultCustPaymentMethodShouldBeFound("authorizationCode.lessThanOrEqual=" + DEFAULT_AUTHORIZATION_CODE);

        // Get all the custPaymentMethodList where authorizationCode is less than or equal to SMALLER_AUTHORIZATION_CODE
        defaultCustPaymentMethodShouldNotBeFound("authorizationCode.lessThanOrEqual=" + SMALLER_AUTHORIZATION_CODE);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByAuthorizationCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where authorizationCode is less than DEFAULT_AUTHORIZATION_CODE
        defaultCustPaymentMethodShouldNotBeFound("authorizationCode.lessThan=" + DEFAULT_AUTHORIZATION_CODE);

        // Get all the custPaymentMethodList where authorizationCode is less than UPDATED_AUTHORIZATION_CODE
        defaultCustPaymentMethodShouldBeFound("authorizationCode.lessThan=" + UPDATED_AUTHORIZATION_CODE);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByAuthorizationCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where authorizationCode is greater than DEFAULT_AUTHORIZATION_CODE
        defaultCustPaymentMethodShouldNotBeFound("authorizationCode.greaterThan=" + DEFAULT_AUTHORIZATION_CODE);

        // Get all the custPaymentMethodList where authorizationCode is greater than SMALLER_AUTHORIZATION_CODE
        defaultCustPaymentMethodShouldBeFound("authorizationCode.greaterThan=" + SMALLER_AUTHORIZATION_CODE);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where status equals to DEFAULT_STATUS
        defaultCustPaymentMethodShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the custPaymentMethodList where status equals to UPDATED_STATUS
        defaultCustPaymentMethodShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where status not equals to DEFAULT_STATUS
        defaultCustPaymentMethodShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the custPaymentMethodList where status not equals to UPDATED_STATUS
        defaultCustPaymentMethodShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultCustPaymentMethodShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the custPaymentMethodList where status equals to UPDATED_STATUS
        defaultCustPaymentMethodShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where status is not null
        defaultCustPaymentMethodShouldBeFound("status.specified=true");

        // Get all the custPaymentMethodList where status is null
        defaultCustPaymentMethodShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByStatusContainsSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where status contains DEFAULT_STATUS
        defaultCustPaymentMethodShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the custPaymentMethodList where status contains UPDATED_STATUS
        defaultCustPaymentMethodShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where status does not contain DEFAULT_STATUS
        defaultCustPaymentMethodShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the custPaymentMethodList where status does not contain UPDATED_STATUS
        defaultCustPaymentMethodShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByStatusDateIsEqualToSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where statusDate equals to DEFAULT_STATUS_DATE
        defaultCustPaymentMethodShouldBeFound("statusDate.equals=" + DEFAULT_STATUS_DATE);

        // Get all the custPaymentMethodList where statusDate equals to UPDATED_STATUS_DATE
        defaultCustPaymentMethodShouldNotBeFound("statusDate.equals=" + UPDATED_STATUS_DATE);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByStatusDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where statusDate not equals to DEFAULT_STATUS_DATE
        defaultCustPaymentMethodShouldNotBeFound("statusDate.notEquals=" + DEFAULT_STATUS_DATE);

        // Get all the custPaymentMethodList where statusDate not equals to UPDATED_STATUS_DATE
        defaultCustPaymentMethodShouldBeFound("statusDate.notEquals=" + UPDATED_STATUS_DATE);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByStatusDateIsInShouldWork() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where statusDate in DEFAULT_STATUS_DATE or UPDATED_STATUS_DATE
        defaultCustPaymentMethodShouldBeFound("statusDate.in=" + DEFAULT_STATUS_DATE + "," + UPDATED_STATUS_DATE);

        // Get all the custPaymentMethodList where statusDate equals to UPDATED_STATUS_DATE
        defaultCustPaymentMethodShouldNotBeFound("statusDate.in=" + UPDATED_STATUS_DATE);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByStatusDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where statusDate is not null
        defaultCustPaymentMethodShouldBeFound("statusDate.specified=true");

        // Get all the custPaymentMethodList where statusDate is null
        defaultCustPaymentMethodShouldNotBeFound("statusDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where details equals to DEFAULT_DETAILS
        defaultCustPaymentMethodShouldBeFound("details.equals=" + DEFAULT_DETAILS);

        // Get all the custPaymentMethodList where details equals to UPDATED_DETAILS
        defaultCustPaymentMethodShouldNotBeFound("details.equals=" + UPDATED_DETAILS);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByDetailsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where details not equals to DEFAULT_DETAILS
        defaultCustPaymentMethodShouldNotBeFound("details.notEquals=" + DEFAULT_DETAILS);

        // Get all the custPaymentMethodList where details not equals to UPDATED_DETAILS
        defaultCustPaymentMethodShouldBeFound("details.notEquals=" + UPDATED_DETAILS);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByDetailsIsInShouldWork() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where details in DEFAULT_DETAILS or UPDATED_DETAILS
        defaultCustPaymentMethodShouldBeFound("details.in=" + DEFAULT_DETAILS + "," + UPDATED_DETAILS);

        // Get all the custPaymentMethodList where details equals to UPDATED_DETAILS
        defaultCustPaymentMethodShouldNotBeFound("details.in=" + UPDATED_DETAILS);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByDetailsIsNullOrNotNull() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where details is not null
        defaultCustPaymentMethodShouldBeFound("details.specified=true");

        // Get all the custPaymentMethodList where details is null
        defaultCustPaymentMethodShouldNotBeFound("details.specified=false");
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByDetailsContainsSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where details contains DEFAULT_DETAILS
        defaultCustPaymentMethodShouldBeFound("details.contains=" + DEFAULT_DETAILS);

        // Get all the custPaymentMethodList where details contains UPDATED_DETAILS
        defaultCustPaymentMethodShouldNotBeFound("details.contains=" + UPDATED_DETAILS);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByDetailsNotContainsSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where details does not contain DEFAULT_DETAILS
        defaultCustPaymentMethodShouldNotBeFound("details.doesNotContain=" + DEFAULT_DETAILS);

        // Get all the custPaymentMethodList where details does not contain UPDATED_DETAILS
        defaultCustPaymentMethodShouldBeFound("details.doesNotContain=" + UPDATED_DETAILS);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByCustomerIdIsEqualToSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where customerId equals to DEFAULT_CUSTOMER_ID
        defaultCustPaymentMethodShouldBeFound("customerId.equals=" + DEFAULT_CUSTOMER_ID);

        // Get all the custPaymentMethodList where customerId equals to UPDATED_CUSTOMER_ID
        defaultCustPaymentMethodShouldNotBeFound("customerId.equals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByCustomerIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where customerId not equals to DEFAULT_CUSTOMER_ID
        defaultCustPaymentMethodShouldNotBeFound("customerId.notEquals=" + DEFAULT_CUSTOMER_ID);

        // Get all the custPaymentMethodList where customerId not equals to UPDATED_CUSTOMER_ID
        defaultCustPaymentMethodShouldBeFound("customerId.notEquals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByCustomerIdIsInShouldWork() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where customerId in DEFAULT_CUSTOMER_ID or UPDATED_CUSTOMER_ID
        defaultCustPaymentMethodShouldBeFound("customerId.in=" + DEFAULT_CUSTOMER_ID + "," + UPDATED_CUSTOMER_ID);

        // Get all the custPaymentMethodList where customerId equals to UPDATED_CUSTOMER_ID
        defaultCustPaymentMethodShouldNotBeFound("customerId.in=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByCustomerIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where customerId is not null
        defaultCustPaymentMethodShouldBeFound("customerId.specified=true");

        // Get all the custPaymentMethodList where customerId is null
        defaultCustPaymentMethodShouldNotBeFound("customerId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByCustomerIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where customerId is greater than or equal to DEFAULT_CUSTOMER_ID
        defaultCustPaymentMethodShouldBeFound("customerId.greaterThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the custPaymentMethodList where customerId is greater than or equal to UPDATED_CUSTOMER_ID
        defaultCustPaymentMethodShouldNotBeFound("customerId.greaterThanOrEqual=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByCustomerIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where customerId is less than or equal to DEFAULT_CUSTOMER_ID
        defaultCustPaymentMethodShouldBeFound("customerId.lessThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the custPaymentMethodList where customerId is less than or equal to SMALLER_CUSTOMER_ID
        defaultCustPaymentMethodShouldNotBeFound("customerId.lessThanOrEqual=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByCustomerIdIsLessThanSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where customerId is less than DEFAULT_CUSTOMER_ID
        defaultCustPaymentMethodShouldNotBeFound("customerId.lessThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the custPaymentMethodList where customerId is less than UPDATED_CUSTOMER_ID
        defaultCustPaymentMethodShouldBeFound("customerId.lessThan=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByCustomerIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        // Get all the custPaymentMethodList where customerId is greater than DEFAULT_CUSTOMER_ID
        defaultCustPaymentMethodShouldNotBeFound("customerId.greaterThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the custPaymentMethodList where customerId is greater than SMALLER_CUSTOMER_ID
        defaultCustPaymentMethodShouldBeFound("customerId.greaterThan=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByBankCardTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);
        BankCardType bankCardType = BankCardTypeResourceIT.createEntity(em);
        em.persist(bankCardType);
        em.flush();
        custPaymentMethod.addBankCardType(bankCardType);
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);
        Long bankCardTypeId = bankCardType.getId();

        // Get all the custPaymentMethodList where bankCardType equals to bankCardTypeId
        defaultCustPaymentMethodShouldBeFound("bankCardTypeId.equals=" + bankCardTypeId);

        // Get all the custPaymentMethodList where bankCardType equals to (bankCardTypeId + 1)
        defaultCustPaymentMethodShouldNotBeFound("bankCardTypeId.equals=" + (bankCardTypeId + 1));
    }

    @Test
    @Transactional
    void getAllCustPaymentMethodsByCustBillingRefIsEqualToSomething() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);
        CustBillingRef custBillingRef = CustBillingRefResourceIT.createEntity(em);
        em.persist(custBillingRef);
        em.flush();
        custPaymentMethod.setCustBillingRef(custBillingRef);
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);
        Long custBillingRefId = custBillingRef.getId();

        // Get all the custPaymentMethodList where custBillingRef equals to custBillingRefId
        defaultCustPaymentMethodShouldBeFound("custBillingRefId.equals=" + custBillingRefId);

        // Get all the custPaymentMethodList where custBillingRef equals to (custBillingRefId + 1)
        defaultCustPaymentMethodShouldNotBeFound("custBillingRefId.equals=" + (custBillingRefId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustPaymentMethodShouldBeFound(String filter) throws Exception {
        restCustPaymentMethodMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custPaymentMethod.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].preferred").value(hasItem(DEFAULT_PREFERRED.booleanValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].authorizationCode").value(hasItem(DEFAULT_AUTHORIZATION_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].statusDate").value(hasItem(DEFAULT_STATUS_DATE.toString())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())));

        // Check, that the count call also returns 1
        restCustPaymentMethodMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustPaymentMethodShouldNotBeFound(String filter) throws Exception {
        restCustPaymentMethodMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustPaymentMethodMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustPaymentMethod() throws Exception {
        // Get the custPaymentMethod
        restCustPaymentMethodMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustPaymentMethod() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        int databaseSizeBeforeUpdate = custPaymentMethodRepository.findAll().size();

        // Update the custPaymentMethod
        CustPaymentMethod updatedCustPaymentMethod = custPaymentMethodRepository.findById(custPaymentMethod.getId()).get();
        // Disconnect from session so that the updates on updatedCustPaymentMethod are not directly saved in db
        em.detach(updatedCustPaymentMethod);
        updatedCustPaymentMethod
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .preferred(UPDATED_PREFERRED)
            .type(UPDATED_TYPE)
            .authorizationCode(UPDATED_AUTHORIZATION_CODE)
            .status(UPDATED_STATUS)
            .statusDate(UPDATED_STATUS_DATE)
            .details(UPDATED_DETAILS)
            .customerId(UPDATED_CUSTOMER_ID);
        CustPaymentMethodDTO custPaymentMethodDTO = custPaymentMethodMapper.toDto(updatedCustPaymentMethod);

        restCustPaymentMethodMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custPaymentMethodDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custPaymentMethodDTO))
            )
            .andExpect(status().isOk());

        // Validate the CustPaymentMethod in the database
        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeUpdate);
        CustPaymentMethod testCustPaymentMethod = custPaymentMethodList.get(custPaymentMethodList.size() - 1);
        assertThat(testCustPaymentMethod.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustPaymentMethod.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCustPaymentMethod.getPreferred()).isEqualTo(UPDATED_PREFERRED);
        assertThat(testCustPaymentMethod.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCustPaymentMethod.getAuthorizationCode()).isEqualTo(UPDATED_AUTHORIZATION_CODE);
        assertThat(testCustPaymentMethod.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustPaymentMethod.getStatusDate()).isEqualTo(UPDATED_STATUS_DATE);
        assertThat(testCustPaymentMethod.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testCustPaymentMethod.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = custPaymentMethodRepository.findAll().size();
        custPaymentMethod.setId(count.incrementAndGet());

        // Create the CustPaymentMethod
        CustPaymentMethodDTO custPaymentMethodDTO = custPaymentMethodMapper.toDto(custPaymentMethod);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustPaymentMethodMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custPaymentMethodDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custPaymentMethodDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustPaymentMethod in the database
        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = custPaymentMethodRepository.findAll().size();
        custPaymentMethod.setId(count.incrementAndGet());

        // Create the CustPaymentMethod
        CustPaymentMethodDTO custPaymentMethodDTO = custPaymentMethodMapper.toDto(custPaymentMethod);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustPaymentMethodMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custPaymentMethodDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustPaymentMethod in the database
        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = custPaymentMethodRepository.findAll().size();
        custPaymentMethod.setId(count.incrementAndGet());

        // Create the CustPaymentMethod
        CustPaymentMethodDTO custPaymentMethodDTO = custPaymentMethodMapper.toDto(custPaymentMethod);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustPaymentMethodMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custPaymentMethodDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustPaymentMethod in the database
        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustPaymentMethodWithPatch() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        int databaseSizeBeforeUpdate = custPaymentMethodRepository.findAll().size();

        // Update the custPaymentMethod using partial update
        CustPaymentMethod partialUpdatedCustPaymentMethod = new CustPaymentMethod();
        partialUpdatedCustPaymentMethod.setId(custPaymentMethod.getId());

        partialUpdatedCustPaymentMethod
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .authorizationCode(UPDATED_AUTHORIZATION_CODE)
            .status(UPDATED_STATUS);

        restCustPaymentMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustPaymentMethod.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustPaymentMethod))
            )
            .andExpect(status().isOk());

        // Validate the CustPaymentMethod in the database
        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeUpdate);
        CustPaymentMethod testCustPaymentMethod = custPaymentMethodList.get(custPaymentMethodList.size() - 1);
        assertThat(testCustPaymentMethod.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustPaymentMethod.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCustPaymentMethod.getPreferred()).isEqualTo(DEFAULT_PREFERRED);
        assertThat(testCustPaymentMethod.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCustPaymentMethod.getAuthorizationCode()).isEqualTo(UPDATED_AUTHORIZATION_CODE);
        assertThat(testCustPaymentMethod.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustPaymentMethod.getStatusDate()).isEqualTo(DEFAULT_STATUS_DATE);
        assertThat(testCustPaymentMethod.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testCustPaymentMethod.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustPaymentMethodWithPatch() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        int databaseSizeBeforeUpdate = custPaymentMethodRepository.findAll().size();

        // Update the custPaymentMethod using partial update
        CustPaymentMethod partialUpdatedCustPaymentMethod = new CustPaymentMethod();
        partialUpdatedCustPaymentMethod.setId(custPaymentMethod.getId());

        partialUpdatedCustPaymentMethod
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .preferred(UPDATED_PREFERRED)
            .type(UPDATED_TYPE)
            .authorizationCode(UPDATED_AUTHORIZATION_CODE)
            .status(UPDATED_STATUS)
            .statusDate(UPDATED_STATUS_DATE)
            .details(UPDATED_DETAILS)
            .customerId(UPDATED_CUSTOMER_ID);

        restCustPaymentMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustPaymentMethod.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustPaymentMethod))
            )
            .andExpect(status().isOk());

        // Validate the CustPaymentMethod in the database
        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeUpdate);
        CustPaymentMethod testCustPaymentMethod = custPaymentMethodList.get(custPaymentMethodList.size() - 1);
        assertThat(testCustPaymentMethod.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustPaymentMethod.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCustPaymentMethod.getPreferred()).isEqualTo(UPDATED_PREFERRED);
        assertThat(testCustPaymentMethod.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCustPaymentMethod.getAuthorizationCode()).isEqualTo(UPDATED_AUTHORIZATION_CODE);
        assertThat(testCustPaymentMethod.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustPaymentMethod.getStatusDate()).isEqualTo(UPDATED_STATUS_DATE);
        assertThat(testCustPaymentMethod.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testCustPaymentMethod.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = custPaymentMethodRepository.findAll().size();
        custPaymentMethod.setId(count.incrementAndGet());

        // Create the CustPaymentMethod
        CustPaymentMethodDTO custPaymentMethodDTO = custPaymentMethodMapper.toDto(custPaymentMethod);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustPaymentMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custPaymentMethodDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custPaymentMethodDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustPaymentMethod in the database
        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = custPaymentMethodRepository.findAll().size();
        custPaymentMethod.setId(count.incrementAndGet());

        // Create the CustPaymentMethod
        CustPaymentMethodDTO custPaymentMethodDTO = custPaymentMethodMapper.toDto(custPaymentMethod);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustPaymentMethodMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custPaymentMethodDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustPaymentMethod in the database
        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustPaymentMethod() throws Exception {
        int databaseSizeBeforeUpdate = custPaymentMethodRepository.findAll().size();
        custPaymentMethod.setId(count.incrementAndGet());

        // Create the CustPaymentMethod
        CustPaymentMethodDTO custPaymentMethodDTO = custPaymentMethodMapper.toDto(custPaymentMethod);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustPaymentMethodMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custPaymentMethodDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustPaymentMethod in the database
        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustPaymentMethod() throws Exception {
        // Initialize the database
        custPaymentMethodRepository.saveAndFlush(custPaymentMethod);

        int databaseSizeBeforeDelete = custPaymentMethodRepository.findAll().size();

        // Delete the custPaymentMethod
        restCustPaymentMethodMockMvc
            .perform(delete(ENTITY_API_URL_ID, custPaymentMethod.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustPaymentMethod> custPaymentMethodList = custPaymentMethodRepository.findAll();
        assertThat(custPaymentMethodList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
