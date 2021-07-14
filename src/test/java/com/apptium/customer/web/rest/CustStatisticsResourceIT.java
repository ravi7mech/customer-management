package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.CustStatistics;
import com.apptium.customer.domain.Customer;
import com.apptium.customer.repository.CustStatisticsRepository;
import com.apptium.customer.service.criteria.CustStatisticsCriteria;
import com.apptium.customer.service.dto.CustStatisticsDTO;
import com.apptium.customer.service.mapper.CustStatisticsMapper;
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
 * Integration tests for the {@link CustStatisticsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustStatisticsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUETYPE = "AAAAAAAAAA";
    private static final String UPDATED_VALUETYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;
    private static final Long SMALLER_CUSTOMER_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/cust-statistics";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustStatisticsRepository custStatisticsRepository;

    @Autowired
    private CustStatisticsMapper custStatisticsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustStatisticsMockMvc;

    private CustStatistics custStatistics;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustStatistics createEntity(EntityManager em) {
        CustStatistics custStatistics = new CustStatistics()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .valuetype(DEFAULT_VALUETYPE)
            .customerId(DEFAULT_CUSTOMER_ID);
        return custStatistics;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustStatistics createUpdatedEntity(EntityManager em) {
        CustStatistics custStatistics = new CustStatistics()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valuetype(UPDATED_VALUETYPE)
            .customerId(UPDATED_CUSTOMER_ID);
        return custStatistics;
    }

    @BeforeEach
    public void initTest() {
        custStatistics = createEntity(em);
    }

    @Test
    @Transactional
    void createCustStatistics() throws Exception {
        int databaseSizeBeforeCreate = custStatisticsRepository.findAll().size();
        // Create the CustStatistics
        CustStatisticsDTO custStatisticsDTO = custStatisticsMapper.toDto(custStatistics);
        restCustStatisticsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custStatisticsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CustStatistics in the database
        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeCreate + 1);
        CustStatistics testCustStatistics = custStatisticsList.get(custStatisticsList.size() - 1);
        assertThat(testCustStatistics.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustStatistics.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCustStatistics.getValuetype()).isEqualTo(DEFAULT_VALUETYPE);
        assertThat(testCustStatistics.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void createCustStatisticsWithExistingId() throws Exception {
        // Create the CustStatistics with an existing ID
        custStatistics.setId(1L);
        CustStatisticsDTO custStatisticsDTO = custStatisticsMapper.toDto(custStatistics);

        int databaseSizeBeforeCreate = custStatisticsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustStatisticsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custStatisticsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustStatistics in the database
        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = custStatisticsRepository.findAll().size();
        // set the field null
        custStatistics.setName(null);

        // Create the CustStatistics, which fails.
        CustStatisticsDTO custStatisticsDTO = custStatisticsMapper.toDto(custStatistics);

        restCustStatisticsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custStatisticsDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = custStatisticsRepository.findAll().size();
        // set the field null
        custStatistics.setValue(null);

        // Create the CustStatistics, which fails.
        CustStatisticsDTO custStatisticsDTO = custStatisticsMapper.toDto(custStatistics);

        restCustStatisticsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custStatisticsDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValuetypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = custStatisticsRepository.findAll().size();
        // set the field null
        custStatistics.setValuetype(null);

        // Create the CustStatistics, which fails.
        CustStatisticsDTO custStatisticsDTO = custStatisticsMapper.toDto(custStatistics);

        restCustStatisticsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custStatisticsDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custStatisticsRepository.findAll().size();
        // set the field null
        custStatistics.setCustomerId(null);

        // Create the CustStatistics, which fails.
        CustStatisticsDTO custStatisticsDTO = custStatisticsMapper.toDto(custStatistics);

        restCustStatisticsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custStatisticsDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustStatistics() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        // Get all the custStatisticsList
        restCustStatisticsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custStatistics.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valuetype").value(hasItem(DEFAULT_VALUETYPE)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())));
    }

    @Test
    @Transactional
    void getCustStatistics() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        // Get the custStatistics
        restCustStatisticsMockMvc
            .perform(get(ENTITY_API_URL_ID, custStatistics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custStatistics.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.valuetype").value(DEFAULT_VALUETYPE))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()));
    }

    @Test
    @Transactional
    void getCustStatisticsByIdFiltering() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        Long id = custStatistics.getId();

        defaultCustStatisticsShouldBeFound("id.equals=" + id);
        defaultCustStatisticsShouldNotBeFound("id.notEquals=" + id);

        defaultCustStatisticsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustStatisticsShouldNotBeFound("id.greaterThan=" + id);

        defaultCustStatisticsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustStatisticsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustStatisticsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        // Get all the custStatisticsList where name equals to DEFAULT_NAME
        defaultCustStatisticsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the custStatisticsList where name equals to UPDATED_NAME
        defaultCustStatisticsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustStatisticsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        // Get all the custStatisticsList where name not equals to DEFAULT_NAME
        defaultCustStatisticsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the custStatisticsList where name not equals to UPDATED_NAME
        defaultCustStatisticsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustStatisticsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        // Get all the custStatisticsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCustStatisticsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the custStatisticsList where name equals to UPDATED_NAME
        defaultCustStatisticsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustStatisticsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        // Get all the custStatisticsList where name is not null
        defaultCustStatisticsShouldBeFound("name.specified=true");

        // Get all the custStatisticsList where name is null
        defaultCustStatisticsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCustStatisticsByNameContainsSomething() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        // Get all the custStatisticsList where name contains DEFAULT_NAME
        defaultCustStatisticsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the custStatisticsList where name contains UPDATED_NAME
        defaultCustStatisticsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustStatisticsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        // Get all the custStatisticsList where name does not contain DEFAULT_NAME
        defaultCustStatisticsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the custStatisticsList where name does not contain UPDATED_NAME
        defaultCustStatisticsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustStatisticsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        // Get all the custStatisticsList where value equals to DEFAULT_VALUE
        defaultCustStatisticsShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the custStatisticsList where value equals to UPDATED_VALUE
        defaultCustStatisticsShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustStatisticsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        // Get all the custStatisticsList where value not equals to DEFAULT_VALUE
        defaultCustStatisticsShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the custStatisticsList where value not equals to UPDATED_VALUE
        defaultCustStatisticsShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustStatisticsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        // Get all the custStatisticsList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultCustStatisticsShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the custStatisticsList where value equals to UPDATED_VALUE
        defaultCustStatisticsShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustStatisticsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        // Get all the custStatisticsList where value is not null
        defaultCustStatisticsShouldBeFound("value.specified=true");

        // Get all the custStatisticsList where value is null
        defaultCustStatisticsShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    void getAllCustStatisticsByValueContainsSomething() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        // Get all the custStatisticsList where value contains DEFAULT_VALUE
        defaultCustStatisticsShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the custStatisticsList where value contains UPDATED_VALUE
        defaultCustStatisticsShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustStatisticsByValueNotContainsSomething() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        // Get all the custStatisticsList where value does not contain DEFAULT_VALUE
        defaultCustStatisticsShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the custStatisticsList where value does not contain UPDATED_VALUE
        defaultCustStatisticsShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustStatisticsByValuetypeIsEqualToSomething() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        // Get all the custStatisticsList where valuetype equals to DEFAULT_VALUETYPE
        defaultCustStatisticsShouldBeFound("valuetype.equals=" + DEFAULT_VALUETYPE);

        // Get all the custStatisticsList where valuetype equals to UPDATED_VALUETYPE
        defaultCustStatisticsShouldNotBeFound("valuetype.equals=" + UPDATED_VALUETYPE);
    }

    @Test
    @Transactional
    void getAllCustStatisticsByValuetypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        // Get all the custStatisticsList where valuetype not equals to DEFAULT_VALUETYPE
        defaultCustStatisticsShouldNotBeFound("valuetype.notEquals=" + DEFAULT_VALUETYPE);

        // Get all the custStatisticsList where valuetype not equals to UPDATED_VALUETYPE
        defaultCustStatisticsShouldBeFound("valuetype.notEquals=" + UPDATED_VALUETYPE);
    }

    @Test
    @Transactional
    void getAllCustStatisticsByValuetypeIsInShouldWork() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        // Get all the custStatisticsList where valuetype in DEFAULT_VALUETYPE or UPDATED_VALUETYPE
        defaultCustStatisticsShouldBeFound("valuetype.in=" + DEFAULT_VALUETYPE + "," + UPDATED_VALUETYPE);

        // Get all the custStatisticsList where valuetype equals to UPDATED_VALUETYPE
        defaultCustStatisticsShouldNotBeFound("valuetype.in=" + UPDATED_VALUETYPE);
    }

    @Test
    @Transactional
    void getAllCustStatisticsByValuetypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        // Get all the custStatisticsList where valuetype is not null
        defaultCustStatisticsShouldBeFound("valuetype.specified=true");

        // Get all the custStatisticsList where valuetype is null
        defaultCustStatisticsShouldNotBeFound("valuetype.specified=false");
    }

    @Test
    @Transactional
    void getAllCustStatisticsByValuetypeContainsSomething() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        // Get all the custStatisticsList where valuetype contains DEFAULT_VALUETYPE
        defaultCustStatisticsShouldBeFound("valuetype.contains=" + DEFAULT_VALUETYPE);

        // Get all the custStatisticsList where valuetype contains UPDATED_VALUETYPE
        defaultCustStatisticsShouldNotBeFound("valuetype.contains=" + UPDATED_VALUETYPE);
    }

    @Test
    @Transactional
    void getAllCustStatisticsByValuetypeNotContainsSomething() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        // Get all the custStatisticsList where valuetype does not contain DEFAULT_VALUETYPE
        defaultCustStatisticsShouldNotBeFound("valuetype.doesNotContain=" + DEFAULT_VALUETYPE);

        // Get all the custStatisticsList where valuetype does not contain UPDATED_VALUETYPE
        defaultCustStatisticsShouldBeFound("valuetype.doesNotContain=" + UPDATED_VALUETYPE);
    }

    @Test
    @Transactional
    void getAllCustStatisticsByCustomerIdIsEqualToSomething() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        // Get all the custStatisticsList where customerId equals to DEFAULT_CUSTOMER_ID
        defaultCustStatisticsShouldBeFound("customerId.equals=" + DEFAULT_CUSTOMER_ID);

        // Get all the custStatisticsList where customerId equals to UPDATED_CUSTOMER_ID
        defaultCustStatisticsShouldNotBeFound("customerId.equals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustStatisticsByCustomerIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        // Get all the custStatisticsList where customerId not equals to DEFAULT_CUSTOMER_ID
        defaultCustStatisticsShouldNotBeFound("customerId.notEquals=" + DEFAULT_CUSTOMER_ID);

        // Get all the custStatisticsList where customerId not equals to UPDATED_CUSTOMER_ID
        defaultCustStatisticsShouldBeFound("customerId.notEquals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustStatisticsByCustomerIdIsInShouldWork() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        // Get all the custStatisticsList where customerId in DEFAULT_CUSTOMER_ID or UPDATED_CUSTOMER_ID
        defaultCustStatisticsShouldBeFound("customerId.in=" + DEFAULT_CUSTOMER_ID + "," + UPDATED_CUSTOMER_ID);

        // Get all the custStatisticsList where customerId equals to UPDATED_CUSTOMER_ID
        defaultCustStatisticsShouldNotBeFound("customerId.in=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustStatisticsByCustomerIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        // Get all the custStatisticsList where customerId is not null
        defaultCustStatisticsShouldBeFound("customerId.specified=true");

        // Get all the custStatisticsList where customerId is null
        defaultCustStatisticsShouldNotBeFound("customerId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustStatisticsByCustomerIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        // Get all the custStatisticsList where customerId is greater than or equal to DEFAULT_CUSTOMER_ID
        defaultCustStatisticsShouldBeFound("customerId.greaterThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the custStatisticsList where customerId is greater than or equal to UPDATED_CUSTOMER_ID
        defaultCustStatisticsShouldNotBeFound("customerId.greaterThanOrEqual=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustStatisticsByCustomerIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        // Get all the custStatisticsList where customerId is less than or equal to DEFAULT_CUSTOMER_ID
        defaultCustStatisticsShouldBeFound("customerId.lessThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the custStatisticsList where customerId is less than or equal to SMALLER_CUSTOMER_ID
        defaultCustStatisticsShouldNotBeFound("customerId.lessThanOrEqual=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustStatisticsByCustomerIdIsLessThanSomething() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        // Get all the custStatisticsList where customerId is less than DEFAULT_CUSTOMER_ID
        defaultCustStatisticsShouldNotBeFound("customerId.lessThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the custStatisticsList where customerId is less than UPDATED_CUSTOMER_ID
        defaultCustStatisticsShouldBeFound("customerId.lessThan=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustStatisticsByCustomerIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        // Get all the custStatisticsList where customerId is greater than DEFAULT_CUSTOMER_ID
        defaultCustStatisticsShouldNotBeFound("customerId.greaterThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the custStatisticsList where customerId is greater than SMALLER_CUSTOMER_ID
        defaultCustStatisticsShouldBeFound("customerId.greaterThan=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustStatisticsByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);
        Customer customer = CustomerResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        custStatistics.setCustomer(customer);
        custStatisticsRepository.saveAndFlush(custStatistics);
        Long customerId = customer.getId();

        // Get all the custStatisticsList where customer equals to customerId
        defaultCustStatisticsShouldBeFound("customerId.equals=" + customerId);

        // Get all the custStatisticsList where customer equals to (customerId + 1)
        defaultCustStatisticsShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustStatisticsShouldBeFound(String filter) throws Exception {
        restCustStatisticsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custStatistics.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valuetype").value(hasItem(DEFAULT_VALUETYPE)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())));

        // Check, that the count call also returns 1
        restCustStatisticsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustStatisticsShouldNotBeFound(String filter) throws Exception {
        restCustStatisticsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustStatisticsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustStatistics() throws Exception {
        // Get the custStatistics
        restCustStatisticsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustStatistics() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        int databaseSizeBeforeUpdate = custStatisticsRepository.findAll().size();

        // Update the custStatistics
        CustStatistics updatedCustStatistics = custStatisticsRepository.findById(custStatistics.getId()).get();
        // Disconnect from session so that the updates on updatedCustStatistics are not directly saved in db
        em.detach(updatedCustStatistics);
        updatedCustStatistics.name(UPDATED_NAME).value(UPDATED_VALUE).valuetype(UPDATED_VALUETYPE).customerId(UPDATED_CUSTOMER_ID);
        CustStatisticsDTO custStatisticsDTO = custStatisticsMapper.toDto(updatedCustStatistics);

        restCustStatisticsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custStatisticsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custStatisticsDTO))
            )
            .andExpect(status().isOk());

        // Validate the CustStatistics in the database
        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeUpdate);
        CustStatistics testCustStatistics = custStatisticsList.get(custStatisticsList.size() - 1);
        assertThat(testCustStatistics.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustStatistics.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustStatistics.getValuetype()).isEqualTo(UPDATED_VALUETYPE);
        assertThat(testCustStatistics.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustStatistics() throws Exception {
        int databaseSizeBeforeUpdate = custStatisticsRepository.findAll().size();
        custStatistics.setId(count.incrementAndGet());

        // Create the CustStatistics
        CustStatisticsDTO custStatisticsDTO = custStatisticsMapper.toDto(custStatistics);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustStatisticsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custStatisticsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custStatisticsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustStatistics in the database
        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustStatistics() throws Exception {
        int databaseSizeBeforeUpdate = custStatisticsRepository.findAll().size();
        custStatistics.setId(count.incrementAndGet());

        // Create the CustStatistics
        CustStatisticsDTO custStatisticsDTO = custStatisticsMapper.toDto(custStatistics);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustStatisticsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custStatisticsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustStatistics in the database
        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustStatistics() throws Exception {
        int databaseSizeBeforeUpdate = custStatisticsRepository.findAll().size();
        custStatistics.setId(count.incrementAndGet());

        // Create the CustStatistics
        CustStatisticsDTO custStatisticsDTO = custStatisticsMapper.toDto(custStatistics);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustStatisticsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custStatisticsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustStatistics in the database
        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustStatisticsWithPatch() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        int databaseSizeBeforeUpdate = custStatisticsRepository.findAll().size();

        // Update the custStatistics using partial update
        CustStatistics partialUpdatedCustStatistics = new CustStatistics();
        partialUpdatedCustStatistics.setId(custStatistics.getId());

        partialUpdatedCustStatistics.value(UPDATED_VALUE).valuetype(UPDATED_VALUETYPE);

        restCustStatisticsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustStatistics.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustStatistics))
            )
            .andExpect(status().isOk());

        // Validate the CustStatistics in the database
        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeUpdate);
        CustStatistics testCustStatistics = custStatisticsList.get(custStatisticsList.size() - 1);
        assertThat(testCustStatistics.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustStatistics.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustStatistics.getValuetype()).isEqualTo(UPDATED_VALUETYPE);
        assertThat(testCustStatistics.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustStatisticsWithPatch() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        int databaseSizeBeforeUpdate = custStatisticsRepository.findAll().size();

        // Update the custStatistics using partial update
        CustStatistics partialUpdatedCustStatistics = new CustStatistics();
        partialUpdatedCustStatistics.setId(custStatistics.getId());

        partialUpdatedCustStatistics.name(UPDATED_NAME).value(UPDATED_VALUE).valuetype(UPDATED_VALUETYPE).customerId(UPDATED_CUSTOMER_ID);

        restCustStatisticsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustStatistics.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustStatistics))
            )
            .andExpect(status().isOk());

        // Validate the CustStatistics in the database
        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeUpdate);
        CustStatistics testCustStatistics = custStatisticsList.get(custStatisticsList.size() - 1);
        assertThat(testCustStatistics.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustStatistics.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustStatistics.getValuetype()).isEqualTo(UPDATED_VALUETYPE);
        assertThat(testCustStatistics.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustStatistics() throws Exception {
        int databaseSizeBeforeUpdate = custStatisticsRepository.findAll().size();
        custStatistics.setId(count.incrementAndGet());

        // Create the CustStatistics
        CustStatisticsDTO custStatisticsDTO = custStatisticsMapper.toDto(custStatistics);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustStatisticsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custStatisticsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custStatisticsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustStatistics in the database
        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustStatistics() throws Exception {
        int databaseSizeBeforeUpdate = custStatisticsRepository.findAll().size();
        custStatistics.setId(count.incrementAndGet());

        // Create the CustStatistics
        CustStatisticsDTO custStatisticsDTO = custStatisticsMapper.toDto(custStatistics);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustStatisticsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custStatisticsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustStatistics in the database
        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustStatistics() throws Exception {
        int databaseSizeBeforeUpdate = custStatisticsRepository.findAll().size();
        custStatistics.setId(count.incrementAndGet());

        // Create the CustStatistics
        CustStatisticsDTO custStatisticsDTO = custStatisticsMapper.toDto(custStatistics);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustStatisticsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custStatisticsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustStatistics in the database
        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustStatistics() throws Exception {
        // Initialize the database
        custStatisticsRepository.saveAndFlush(custStatistics);

        int databaseSizeBeforeDelete = custStatisticsRepository.findAll().size();

        // Delete the custStatistics
        restCustStatisticsMockMvc
            .perform(delete(ENTITY_API_URL_ID, custStatistics.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustStatistics> custStatisticsList = custStatisticsRepository.findAll();
        assertThat(custStatisticsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
