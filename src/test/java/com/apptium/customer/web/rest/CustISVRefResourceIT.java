package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.CustISVChar;
import com.apptium.customer.domain.CustISVRef;
import com.apptium.customer.domain.Customer;
import com.apptium.customer.repository.CustISVRefRepository;
import com.apptium.customer.service.criteria.CustISVRefCriteria;
import com.apptium.customer.service.dto.CustISVRefDTO;
import com.apptium.customer.service.mapper.CustISVRefMapper;
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
 * Integration tests for the {@link CustISVRefResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustISVRefResourceIT {

    private static final Integer DEFAULT_ISV_ID = 1;
    private static final Integer UPDATED_ISV_ID = 2;
    private static final Integer SMALLER_ISV_ID = 1 - 1;

    private static final String DEFAULT_ISV_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ISV_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_ISV_CUST_ID = 1L;
    private static final Long UPDATED_ISV_CUST_ID = 2L;
    private static final Long SMALLER_ISV_CUST_ID = 1L - 1L;

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;
    private static final Long SMALLER_CUSTOMER_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/cust-isv-refs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustISVRefRepository custISVRefRepository;

    @Autowired
    private CustISVRefMapper custISVRefMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustISVRefMockMvc;

    private CustISVRef custISVRef;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustISVRef createEntity(EntityManager em) {
        CustISVRef custISVRef = new CustISVRef()
            .isvId(DEFAULT_ISV_ID)
            .isvName(DEFAULT_ISV_NAME)
            .isvCustId(DEFAULT_ISV_CUST_ID)
            .customerId(DEFAULT_CUSTOMER_ID);
        return custISVRef;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustISVRef createUpdatedEntity(EntityManager em) {
        CustISVRef custISVRef = new CustISVRef()
            .isvId(UPDATED_ISV_ID)
            .isvName(UPDATED_ISV_NAME)
            .isvCustId(UPDATED_ISV_CUST_ID)
            .customerId(UPDATED_CUSTOMER_ID);
        return custISVRef;
    }

    @BeforeEach
    public void initTest() {
        custISVRef = createEntity(em);
    }

    @Test
    @Transactional
    void createCustISVRef() throws Exception {
        int databaseSizeBeforeCreate = custISVRefRepository.findAll().size();
        // Create the CustISVRef
        CustISVRefDTO custISVRefDTO = custISVRefMapper.toDto(custISVRef);
        restCustISVRefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custISVRefDTO)))
            .andExpect(status().isCreated());

        // Validate the CustISVRef in the database
        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeCreate + 1);
        CustISVRef testCustISVRef = custISVRefList.get(custISVRefList.size() - 1);
        assertThat(testCustISVRef.getIsvId()).isEqualTo(DEFAULT_ISV_ID);
        assertThat(testCustISVRef.getIsvName()).isEqualTo(DEFAULT_ISV_NAME);
        assertThat(testCustISVRef.getIsvCustId()).isEqualTo(DEFAULT_ISV_CUST_ID);
        assertThat(testCustISVRef.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void createCustISVRefWithExistingId() throws Exception {
        // Create the CustISVRef with an existing ID
        custISVRef.setId(1L);
        CustISVRefDTO custISVRefDTO = custISVRefMapper.toDto(custISVRef);

        int databaseSizeBeforeCreate = custISVRefRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustISVRefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custISVRefDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustISVRef in the database
        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIsvIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custISVRefRepository.findAll().size();
        // set the field null
        custISVRef.setIsvId(null);

        // Create the CustISVRef, which fails.
        CustISVRefDTO custISVRefDTO = custISVRefMapper.toDto(custISVRef);

        restCustISVRefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custISVRefDTO)))
            .andExpect(status().isBadRequest());

        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsvNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = custISVRefRepository.findAll().size();
        // set the field null
        custISVRef.setIsvName(null);

        // Create the CustISVRef, which fails.
        CustISVRefDTO custISVRefDTO = custISVRefMapper.toDto(custISVRef);

        restCustISVRefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custISVRefDTO)))
            .andExpect(status().isBadRequest());

        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsvCustIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custISVRefRepository.findAll().size();
        // set the field null
        custISVRef.setIsvCustId(null);

        // Create the CustISVRef, which fails.
        CustISVRefDTO custISVRefDTO = custISVRefMapper.toDto(custISVRef);

        restCustISVRefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custISVRefDTO)))
            .andExpect(status().isBadRequest());

        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custISVRefRepository.findAll().size();
        // set the field null
        custISVRef.setCustomerId(null);

        // Create the CustISVRef, which fails.
        CustISVRefDTO custISVRefDTO = custISVRefMapper.toDto(custISVRef);

        restCustISVRefMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custISVRefDTO)))
            .andExpect(status().isBadRequest());

        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustISVRefs() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList
        restCustISVRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custISVRef.getId().intValue())))
            .andExpect(jsonPath("$.[*].isvId").value(hasItem(DEFAULT_ISV_ID)))
            .andExpect(jsonPath("$.[*].isvName").value(hasItem(DEFAULT_ISV_NAME)))
            .andExpect(jsonPath("$.[*].isvCustId").value(hasItem(DEFAULT_ISV_CUST_ID.intValue())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())));
    }

    @Test
    @Transactional
    void getCustISVRef() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get the custISVRef
        restCustISVRefMockMvc
            .perform(get(ENTITY_API_URL_ID, custISVRef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custISVRef.getId().intValue()))
            .andExpect(jsonPath("$.isvId").value(DEFAULT_ISV_ID))
            .andExpect(jsonPath("$.isvName").value(DEFAULT_ISV_NAME))
            .andExpect(jsonPath("$.isvCustId").value(DEFAULT_ISV_CUST_ID.intValue()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()));
    }

    @Test
    @Transactional
    void getCustISVRefsByIdFiltering() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        Long id = custISVRef.getId();

        defaultCustISVRefShouldBeFound("id.equals=" + id);
        defaultCustISVRefShouldNotBeFound("id.notEquals=" + id);

        defaultCustISVRefShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustISVRefShouldNotBeFound("id.greaterThan=" + id);

        defaultCustISVRefShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustISVRefShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustISVRefsByIsvIdIsEqualToSomething() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList where isvId equals to DEFAULT_ISV_ID
        defaultCustISVRefShouldBeFound("isvId.equals=" + DEFAULT_ISV_ID);

        // Get all the custISVRefList where isvId equals to UPDATED_ISV_ID
        defaultCustISVRefShouldNotBeFound("isvId.equals=" + UPDATED_ISV_ID);
    }

    @Test
    @Transactional
    void getAllCustISVRefsByIsvIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList where isvId not equals to DEFAULT_ISV_ID
        defaultCustISVRefShouldNotBeFound("isvId.notEquals=" + DEFAULT_ISV_ID);

        // Get all the custISVRefList where isvId not equals to UPDATED_ISV_ID
        defaultCustISVRefShouldBeFound("isvId.notEquals=" + UPDATED_ISV_ID);
    }

    @Test
    @Transactional
    void getAllCustISVRefsByIsvIdIsInShouldWork() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList where isvId in DEFAULT_ISV_ID or UPDATED_ISV_ID
        defaultCustISVRefShouldBeFound("isvId.in=" + DEFAULT_ISV_ID + "," + UPDATED_ISV_ID);

        // Get all the custISVRefList where isvId equals to UPDATED_ISV_ID
        defaultCustISVRefShouldNotBeFound("isvId.in=" + UPDATED_ISV_ID);
    }

    @Test
    @Transactional
    void getAllCustISVRefsByIsvIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList where isvId is not null
        defaultCustISVRefShouldBeFound("isvId.specified=true");

        // Get all the custISVRefList where isvId is null
        defaultCustISVRefShouldNotBeFound("isvId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustISVRefsByIsvIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList where isvId is greater than or equal to DEFAULT_ISV_ID
        defaultCustISVRefShouldBeFound("isvId.greaterThanOrEqual=" + DEFAULT_ISV_ID);

        // Get all the custISVRefList where isvId is greater than or equal to UPDATED_ISV_ID
        defaultCustISVRefShouldNotBeFound("isvId.greaterThanOrEqual=" + UPDATED_ISV_ID);
    }

    @Test
    @Transactional
    void getAllCustISVRefsByIsvIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList where isvId is less than or equal to DEFAULT_ISV_ID
        defaultCustISVRefShouldBeFound("isvId.lessThanOrEqual=" + DEFAULT_ISV_ID);

        // Get all the custISVRefList where isvId is less than or equal to SMALLER_ISV_ID
        defaultCustISVRefShouldNotBeFound("isvId.lessThanOrEqual=" + SMALLER_ISV_ID);
    }

    @Test
    @Transactional
    void getAllCustISVRefsByIsvIdIsLessThanSomething() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList where isvId is less than DEFAULT_ISV_ID
        defaultCustISVRefShouldNotBeFound("isvId.lessThan=" + DEFAULT_ISV_ID);

        // Get all the custISVRefList where isvId is less than UPDATED_ISV_ID
        defaultCustISVRefShouldBeFound("isvId.lessThan=" + UPDATED_ISV_ID);
    }

    @Test
    @Transactional
    void getAllCustISVRefsByIsvIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList where isvId is greater than DEFAULT_ISV_ID
        defaultCustISVRefShouldNotBeFound("isvId.greaterThan=" + DEFAULT_ISV_ID);

        // Get all the custISVRefList where isvId is greater than SMALLER_ISV_ID
        defaultCustISVRefShouldBeFound("isvId.greaterThan=" + SMALLER_ISV_ID);
    }

    @Test
    @Transactional
    void getAllCustISVRefsByIsvNameIsEqualToSomething() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList where isvName equals to DEFAULT_ISV_NAME
        defaultCustISVRefShouldBeFound("isvName.equals=" + DEFAULT_ISV_NAME);

        // Get all the custISVRefList where isvName equals to UPDATED_ISV_NAME
        defaultCustISVRefShouldNotBeFound("isvName.equals=" + UPDATED_ISV_NAME);
    }

    @Test
    @Transactional
    void getAllCustISVRefsByIsvNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList where isvName not equals to DEFAULT_ISV_NAME
        defaultCustISVRefShouldNotBeFound("isvName.notEquals=" + DEFAULT_ISV_NAME);

        // Get all the custISVRefList where isvName not equals to UPDATED_ISV_NAME
        defaultCustISVRefShouldBeFound("isvName.notEquals=" + UPDATED_ISV_NAME);
    }

    @Test
    @Transactional
    void getAllCustISVRefsByIsvNameIsInShouldWork() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList where isvName in DEFAULT_ISV_NAME or UPDATED_ISV_NAME
        defaultCustISVRefShouldBeFound("isvName.in=" + DEFAULT_ISV_NAME + "," + UPDATED_ISV_NAME);

        // Get all the custISVRefList where isvName equals to UPDATED_ISV_NAME
        defaultCustISVRefShouldNotBeFound("isvName.in=" + UPDATED_ISV_NAME);
    }

    @Test
    @Transactional
    void getAllCustISVRefsByIsvNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList where isvName is not null
        defaultCustISVRefShouldBeFound("isvName.specified=true");

        // Get all the custISVRefList where isvName is null
        defaultCustISVRefShouldNotBeFound("isvName.specified=false");
    }

    @Test
    @Transactional
    void getAllCustISVRefsByIsvNameContainsSomething() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList where isvName contains DEFAULT_ISV_NAME
        defaultCustISVRefShouldBeFound("isvName.contains=" + DEFAULT_ISV_NAME);

        // Get all the custISVRefList where isvName contains UPDATED_ISV_NAME
        defaultCustISVRefShouldNotBeFound("isvName.contains=" + UPDATED_ISV_NAME);
    }

    @Test
    @Transactional
    void getAllCustISVRefsByIsvNameNotContainsSomething() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList where isvName does not contain DEFAULT_ISV_NAME
        defaultCustISVRefShouldNotBeFound("isvName.doesNotContain=" + DEFAULT_ISV_NAME);

        // Get all the custISVRefList where isvName does not contain UPDATED_ISV_NAME
        defaultCustISVRefShouldBeFound("isvName.doesNotContain=" + UPDATED_ISV_NAME);
    }

    @Test
    @Transactional
    void getAllCustISVRefsByIsvCustIdIsEqualToSomething() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList where isvCustId equals to DEFAULT_ISV_CUST_ID
        defaultCustISVRefShouldBeFound("isvCustId.equals=" + DEFAULT_ISV_CUST_ID);

        // Get all the custISVRefList where isvCustId equals to UPDATED_ISV_CUST_ID
        defaultCustISVRefShouldNotBeFound("isvCustId.equals=" + UPDATED_ISV_CUST_ID);
    }

    @Test
    @Transactional
    void getAllCustISVRefsByIsvCustIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList where isvCustId not equals to DEFAULT_ISV_CUST_ID
        defaultCustISVRefShouldNotBeFound("isvCustId.notEquals=" + DEFAULT_ISV_CUST_ID);

        // Get all the custISVRefList where isvCustId not equals to UPDATED_ISV_CUST_ID
        defaultCustISVRefShouldBeFound("isvCustId.notEquals=" + UPDATED_ISV_CUST_ID);
    }

    @Test
    @Transactional
    void getAllCustISVRefsByIsvCustIdIsInShouldWork() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList where isvCustId in DEFAULT_ISV_CUST_ID or UPDATED_ISV_CUST_ID
        defaultCustISVRefShouldBeFound("isvCustId.in=" + DEFAULT_ISV_CUST_ID + "," + UPDATED_ISV_CUST_ID);

        // Get all the custISVRefList where isvCustId equals to UPDATED_ISV_CUST_ID
        defaultCustISVRefShouldNotBeFound("isvCustId.in=" + UPDATED_ISV_CUST_ID);
    }

    @Test
    @Transactional
    void getAllCustISVRefsByIsvCustIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList where isvCustId is not null
        defaultCustISVRefShouldBeFound("isvCustId.specified=true");

        // Get all the custISVRefList where isvCustId is null
        defaultCustISVRefShouldNotBeFound("isvCustId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustISVRefsByIsvCustIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList where isvCustId is greater than or equal to DEFAULT_ISV_CUST_ID
        defaultCustISVRefShouldBeFound("isvCustId.greaterThanOrEqual=" + DEFAULT_ISV_CUST_ID);

        // Get all the custISVRefList where isvCustId is greater than or equal to UPDATED_ISV_CUST_ID
        defaultCustISVRefShouldNotBeFound("isvCustId.greaterThanOrEqual=" + UPDATED_ISV_CUST_ID);
    }

    @Test
    @Transactional
    void getAllCustISVRefsByIsvCustIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList where isvCustId is less than or equal to DEFAULT_ISV_CUST_ID
        defaultCustISVRefShouldBeFound("isvCustId.lessThanOrEqual=" + DEFAULT_ISV_CUST_ID);

        // Get all the custISVRefList where isvCustId is less than or equal to SMALLER_ISV_CUST_ID
        defaultCustISVRefShouldNotBeFound("isvCustId.lessThanOrEqual=" + SMALLER_ISV_CUST_ID);
    }

    @Test
    @Transactional
    void getAllCustISVRefsByIsvCustIdIsLessThanSomething() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList where isvCustId is less than DEFAULT_ISV_CUST_ID
        defaultCustISVRefShouldNotBeFound("isvCustId.lessThan=" + DEFAULT_ISV_CUST_ID);

        // Get all the custISVRefList where isvCustId is less than UPDATED_ISV_CUST_ID
        defaultCustISVRefShouldBeFound("isvCustId.lessThan=" + UPDATED_ISV_CUST_ID);
    }

    @Test
    @Transactional
    void getAllCustISVRefsByIsvCustIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList where isvCustId is greater than DEFAULT_ISV_CUST_ID
        defaultCustISVRefShouldNotBeFound("isvCustId.greaterThan=" + DEFAULT_ISV_CUST_ID);

        // Get all the custISVRefList where isvCustId is greater than SMALLER_ISV_CUST_ID
        defaultCustISVRefShouldBeFound("isvCustId.greaterThan=" + SMALLER_ISV_CUST_ID);
    }

    @Test
    @Transactional
    void getAllCustISVRefsByCustomerIdIsEqualToSomething() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList where customerId equals to DEFAULT_CUSTOMER_ID
        defaultCustISVRefShouldBeFound("customerId.equals=" + DEFAULT_CUSTOMER_ID);

        // Get all the custISVRefList where customerId equals to UPDATED_CUSTOMER_ID
        defaultCustISVRefShouldNotBeFound("customerId.equals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustISVRefsByCustomerIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList where customerId not equals to DEFAULT_CUSTOMER_ID
        defaultCustISVRefShouldNotBeFound("customerId.notEquals=" + DEFAULT_CUSTOMER_ID);

        // Get all the custISVRefList where customerId not equals to UPDATED_CUSTOMER_ID
        defaultCustISVRefShouldBeFound("customerId.notEquals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustISVRefsByCustomerIdIsInShouldWork() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList where customerId in DEFAULT_CUSTOMER_ID or UPDATED_CUSTOMER_ID
        defaultCustISVRefShouldBeFound("customerId.in=" + DEFAULT_CUSTOMER_ID + "," + UPDATED_CUSTOMER_ID);

        // Get all the custISVRefList where customerId equals to UPDATED_CUSTOMER_ID
        defaultCustISVRefShouldNotBeFound("customerId.in=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustISVRefsByCustomerIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList where customerId is not null
        defaultCustISVRefShouldBeFound("customerId.specified=true");

        // Get all the custISVRefList where customerId is null
        defaultCustISVRefShouldNotBeFound("customerId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustISVRefsByCustomerIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList where customerId is greater than or equal to DEFAULT_CUSTOMER_ID
        defaultCustISVRefShouldBeFound("customerId.greaterThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the custISVRefList where customerId is greater than or equal to UPDATED_CUSTOMER_ID
        defaultCustISVRefShouldNotBeFound("customerId.greaterThanOrEqual=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustISVRefsByCustomerIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList where customerId is less than or equal to DEFAULT_CUSTOMER_ID
        defaultCustISVRefShouldBeFound("customerId.lessThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the custISVRefList where customerId is less than or equal to SMALLER_CUSTOMER_ID
        defaultCustISVRefShouldNotBeFound("customerId.lessThanOrEqual=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustISVRefsByCustomerIdIsLessThanSomething() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList where customerId is less than DEFAULT_CUSTOMER_ID
        defaultCustISVRefShouldNotBeFound("customerId.lessThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the custISVRefList where customerId is less than UPDATED_CUSTOMER_ID
        defaultCustISVRefShouldBeFound("customerId.lessThan=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustISVRefsByCustomerIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        // Get all the custISVRefList where customerId is greater than DEFAULT_CUSTOMER_ID
        defaultCustISVRefShouldNotBeFound("customerId.greaterThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the custISVRefList where customerId is greater than SMALLER_CUSTOMER_ID
        defaultCustISVRefShouldBeFound("customerId.greaterThan=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustISVRefsByCustISVCharIsEqualToSomething() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);
        CustISVChar custISVChar = CustISVCharResourceIT.createEntity(em);
        em.persist(custISVChar);
        em.flush();
        custISVRef.addCustISVChar(custISVChar);
        custISVRefRepository.saveAndFlush(custISVRef);
        Long custISVCharId = custISVChar.getId();

        // Get all the custISVRefList where custISVChar equals to custISVCharId
        defaultCustISVRefShouldBeFound("custISVCharId.equals=" + custISVCharId);

        // Get all the custISVRefList where custISVChar equals to (custISVCharId + 1)
        defaultCustISVRefShouldNotBeFound("custISVCharId.equals=" + (custISVCharId + 1));
    }

    @Test
    @Transactional
    void getAllCustISVRefsByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);
        Customer customer = CustomerResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        custISVRef.setCustomer(customer);
        custISVRefRepository.saveAndFlush(custISVRef);
        Long customerId = customer.getId();

        // Get all the custISVRefList where customer equals to customerId
        defaultCustISVRefShouldBeFound("customerId.equals=" + customerId);

        // Get all the custISVRefList where customer equals to (customerId + 1)
        defaultCustISVRefShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustISVRefShouldBeFound(String filter) throws Exception {
        restCustISVRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custISVRef.getId().intValue())))
            .andExpect(jsonPath("$.[*].isvId").value(hasItem(DEFAULT_ISV_ID)))
            .andExpect(jsonPath("$.[*].isvName").value(hasItem(DEFAULT_ISV_NAME)))
            .andExpect(jsonPath("$.[*].isvCustId").value(hasItem(DEFAULT_ISV_CUST_ID.intValue())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())));

        // Check, that the count call also returns 1
        restCustISVRefMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustISVRefShouldNotBeFound(String filter) throws Exception {
        restCustISVRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustISVRefMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustISVRef() throws Exception {
        // Get the custISVRef
        restCustISVRefMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustISVRef() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        int databaseSizeBeforeUpdate = custISVRefRepository.findAll().size();

        // Update the custISVRef
        CustISVRef updatedCustISVRef = custISVRefRepository.findById(custISVRef.getId()).get();
        // Disconnect from session so that the updates on updatedCustISVRef are not directly saved in db
        em.detach(updatedCustISVRef);
        updatedCustISVRef.isvId(UPDATED_ISV_ID).isvName(UPDATED_ISV_NAME).isvCustId(UPDATED_ISV_CUST_ID).customerId(UPDATED_CUSTOMER_ID);
        CustISVRefDTO custISVRefDTO = custISVRefMapper.toDto(updatedCustISVRef);

        restCustISVRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custISVRefDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custISVRefDTO))
            )
            .andExpect(status().isOk());

        // Validate the CustISVRef in the database
        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeUpdate);
        CustISVRef testCustISVRef = custISVRefList.get(custISVRefList.size() - 1);
        assertThat(testCustISVRef.getIsvId()).isEqualTo(UPDATED_ISV_ID);
        assertThat(testCustISVRef.getIsvName()).isEqualTo(UPDATED_ISV_NAME);
        assertThat(testCustISVRef.getIsvCustId()).isEqualTo(UPDATED_ISV_CUST_ID);
        assertThat(testCustISVRef.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustISVRef() throws Exception {
        int databaseSizeBeforeUpdate = custISVRefRepository.findAll().size();
        custISVRef.setId(count.incrementAndGet());

        // Create the CustISVRef
        CustISVRefDTO custISVRefDTO = custISVRefMapper.toDto(custISVRef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustISVRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custISVRefDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custISVRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustISVRef in the database
        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustISVRef() throws Exception {
        int databaseSizeBeforeUpdate = custISVRefRepository.findAll().size();
        custISVRef.setId(count.incrementAndGet());

        // Create the CustISVRef
        CustISVRefDTO custISVRefDTO = custISVRefMapper.toDto(custISVRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustISVRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custISVRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustISVRef in the database
        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustISVRef() throws Exception {
        int databaseSizeBeforeUpdate = custISVRefRepository.findAll().size();
        custISVRef.setId(count.incrementAndGet());

        // Create the CustISVRef
        CustISVRefDTO custISVRefDTO = custISVRefMapper.toDto(custISVRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustISVRefMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custISVRefDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustISVRef in the database
        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustISVRefWithPatch() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        int databaseSizeBeforeUpdate = custISVRefRepository.findAll().size();

        // Update the custISVRef using partial update
        CustISVRef partialUpdatedCustISVRef = new CustISVRef();
        partialUpdatedCustISVRef.setId(custISVRef.getId());

        partialUpdatedCustISVRef.isvId(UPDATED_ISV_ID).isvCustId(UPDATED_ISV_CUST_ID).customerId(UPDATED_CUSTOMER_ID);

        restCustISVRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustISVRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustISVRef))
            )
            .andExpect(status().isOk());

        // Validate the CustISVRef in the database
        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeUpdate);
        CustISVRef testCustISVRef = custISVRefList.get(custISVRefList.size() - 1);
        assertThat(testCustISVRef.getIsvId()).isEqualTo(UPDATED_ISV_ID);
        assertThat(testCustISVRef.getIsvName()).isEqualTo(DEFAULT_ISV_NAME);
        assertThat(testCustISVRef.getIsvCustId()).isEqualTo(UPDATED_ISV_CUST_ID);
        assertThat(testCustISVRef.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustISVRefWithPatch() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        int databaseSizeBeforeUpdate = custISVRefRepository.findAll().size();

        // Update the custISVRef using partial update
        CustISVRef partialUpdatedCustISVRef = new CustISVRef();
        partialUpdatedCustISVRef.setId(custISVRef.getId());

        partialUpdatedCustISVRef
            .isvId(UPDATED_ISV_ID)
            .isvName(UPDATED_ISV_NAME)
            .isvCustId(UPDATED_ISV_CUST_ID)
            .customerId(UPDATED_CUSTOMER_ID);

        restCustISVRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustISVRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustISVRef))
            )
            .andExpect(status().isOk());

        // Validate the CustISVRef in the database
        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeUpdate);
        CustISVRef testCustISVRef = custISVRefList.get(custISVRefList.size() - 1);
        assertThat(testCustISVRef.getIsvId()).isEqualTo(UPDATED_ISV_ID);
        assertThat(testCustISVRef.getIsvName()).isEqualTo(UPDATED_ISV_NAME);
        assertThat(testCustISVRef.getIsvCustId()).isEqualTo(UPDATED_ISV_CUST_ID);
        assertThat(testCustISVRef.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustISVRef() throws Exception {
        int databaseSizeBeforeUpdate = custISVRefRepository.findAll().size();
        custISVRef.setId(count.incrementAndGet());

        // Create the CustISVRef
        CustISVRefDTO custISVRefDTO = custISVRefMapper.toDto(custISVRef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustISVRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custISVRefDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custISVRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustISVRef in the database
        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustISVRef() throws Exception {
        int databaseSizeBeforeUpdate = custISVRefRepository.findAll().size();
        custISVRef.setId(count.incrementAndGet());

        // Create the CustISVRef
        CustISVRefDTO custISVRefDTO = custISVRefMapper.toDto(custISVRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustISVRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custISVRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustISVRef in the database
        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustISVRef() throws Exception {
        int databaseSizeBeforeUpdate = custISVRefRepository.findAll().size();
        custISVRef.setId(count.incrementAndGet());

        // Create the CustISVRef
        CustISVRefDTO custISVRefDTO = custISVRefMapper.toDto(custISVRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustISVRefMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(custISVRefDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustISVRef in the database
        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustISVRef() throws Exception {
        // Initialize the database
        custISVRefRepository.saveAndFlush(custISVRef);

        int databaseSizeBeforeDelete = custISVRefRepository.findAll().size();

        // Delete the custISVRef
        restCustISVRefMockMvc
            .perform(delete(ENTITY_API_URL_ID, custISVRef.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustISVRef> custISVRefList = custISVRefRepository.findAll();
        assertThat(custISVRefList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
