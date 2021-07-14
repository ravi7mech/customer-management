package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.CustNewsLetterConfig;
import com.apptium.customer.domain.Customer;
import com.apptium.customer.domain.NewsLetterType;
import com.apptium.customer.repository.CustNewsLetterConfigRepository;
import com.apptium.customer.service.criteria.CustNewsLetterConfigCriteria;
import com.apptium.customer.service.dto.CustNewsLetterConfigDTO;
import com.apptium.customer.service.mapper.CustNewsLetterConfigMapper;
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
 * Integration tests for the {@link CustNewsLetterConfigResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustNewsLetterConfigResourceIT {

    private static final Long DEFAULT_NEW_LETTER_TYPE_ID = 1L;
    private static final Long UPDATED_NEW_LETTER_TYPE_ID = 2L;
    private static final Long SMALLER_NEW_LETTER_TYPE_ID = 1L - 1L;

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;
    private static final Long SMALLER_CUSTOMER_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/cust-news-letter-configs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustNewsLetterConfigRepository custNewsLetterConfigRepository;

    @Autowired
    private CustNewsLetterConfigMapper custNewsLetterConfigMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustNewsLetterConfigMockMvc;

    private CustNewsLetterConfig custNewsLetterConfig;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustNewsLetterConfig createEntity(EntityManager em) {
        CustNewsLetterConfig custNewsLetterConfig = new CustNewsLetterConfig()
            .newLetterTypeId(DEFAULT_NEW_LETTER_TYPE_ID)
            .value(DEFAULT_VALUE)
            .customerId(DEFAULT_CUSTOMER_ID);
        return custNewsLetterConfig;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustNewsLetterConfig createUpdatedEntity(EntityManager em) {
        CustNewsLetterConfig custNewsLetterConfig = new CustNewsLetterConfig()
            .newLetterTypeId(UPDATED_NEW_LETTER_TYPE_ID)
            .value(UPDATED_VALUE)
            .customerId(UPDATED_CUSTOMER_ID);
        return custNewsLetterConfig;
    }

    @BeforeEach
    public void initTest() {
        custNewsLetterConfig = createEntity(em);
    }

    @Test
    @Transactional
    void createCustNewsLetterConfig() throws Exception {
        int databaseSizeBeforeCreate = custNewsLetterConfigRepository.findAll().size();
        // Create the CustNewsLetterConfig
        CustNewsLetterConfigDTO custNewsLetterConfigDTO = custNewsLetterConfigMapper.toDto(custNewsLetterConfig);
        restCustNewsLetterConfigMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custNewsLetterConfigDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CustNewsLetterConfig in the database
        List<CustNewsLetterConfig> custNewsLetterConfigList = custNewsLetterConfigRepository.findAll();
        assertThat(custNewsLetterConfigList).hasSize(databaseSizeBeforeCreate + 1);
        CustNewsLetterConfig testCustNewsLetterConfig = custNewsLetterConfigList.get(custNewsLetterConfigList.size() - 1);
        assertThat(testCustNewsLetterConfig.getNewLetterTypeId()).isEqualTo(DEFAULT_NEW_LETTER_TYPE_ID);
        assertThat(testCustNewsLetterConfig.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCustNewsLetterConfig.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void createCustNewsLetterConfigWithExistingId() throws Exception {
        // Create the CustNewsLetterConfig with an existing ID
        custNewsLetterConfig.setId(1L);
        CustNewsLetterConfigDTO custNewsLetterConfigDTO = custNewsLetterConfigMapper.toDto(custNewsLetterConfig);

        int databaseSizeBeforeCreate = custNewsLetterConfigRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustNewsLetterConfigMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custNewsLetterConfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustNewsLetterConfig in the database
        List<CustNewsLetterConfig> custNewsLetterConfigList = custNewsLetterConfigRepository.findAll();
        assertThat(custNewsLetterConfigList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNewLetterTypeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custNewsLetterConfigRepository.findAll().size();
        // set the field null
        custNewsLetterConfig.setNewLetterTypeId(null);

        // Create the CustNewsLetterConfig, which fails.
        CustNewsLetterConfigDTO custNewsLetterConfigDTO = custNewsLetterConfigMapper.toDto(custNewsLetterConfig);

        restCustNewsLetterConfigMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custNewsLetterConfigDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustNewsLetterConfig> custNewsLetterConfigList = custNewsLetterConfigRepository.findAll();
        assertThat(custNewsLetterConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = custNewsLetterConfigRepository.findAll().size();
        // set the field null
        custNewsLetterConfig.setValue(null);

        // Create the CustNewsLetterConfig, which fails.
        CustNewsLetterConfigDTO custNewsLetterConfigDTO = custNewsLetterConfigMapper.toDto(custNewsLetterConfig);

        restCustNewsLetterConfigMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custNewsLetterConfigDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustNewsLetterConfig> custNewsLetterConfigList = custNewsLetterConfigRepository.findAll();
        assertThat(custNewsLetterConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custNewsLetterConfigRepository.findAll().size();
        // set the field null
        custNewsLetterConfig.setCustomerId(null);

        // Create the CustNewsLetterConfig, which fails.
        CustNewsLetterConfigDTO custNewsLetterConfigDTO = custNewsLetterConfigMapper.toDto(custNewsLetterConfig);

        restCustNewsLetterConfigMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custNewsLetterConfigDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustNewsLetterConfig> custNewsLetterConfigList = custNewsLetterConfigRepository.findAll();
        assertThat(custNewsLetterConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustNewsLetterConfigs() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        // Get all the custNewsLetterConfigList
        restCustNewsLetterConfigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custNewsLetterConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].newLetterTypeId").value(hasItem(DEFAULT_NEW_LETTER_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())));
    }

    @Test
    @Transactional
    void getCustNewsLetterConfig() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        // Get the custNewsLetterConfig
        restCustNewsLetterConfigMockMvc
            .perform(get(ENTITY_API_URL_ID, custNewsLetterConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custNewsLetterConfig.getId().intValue()))
            .andExpect(jsonPath("$.newLetterTypeId").value(DEFAULT_NEW_LETTER_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()));
    }

    @Test
    @Transactional
    void getCustNewsLetterConfigsByIdFiltering() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        Long id = custNewsLetterConfig.getId();

        defaultCustNewsLetterConfigShouldBeFound("id.equals=" + id);
        defaultCustNewsLetterConfigShouldNotBeFound("id.notEquals=" + id);

        defaultCustNewsLetterConfigShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustNewsLetterConfigShouldNotBeFound("id.greaterThan=" + id);

        defaultCustNewsLetterConfigShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustNewsLetterConfigShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustNewsLetterConfigsByNewLetterTypeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        // Get all the custNewsLetterConfigList where newLetterTypeId equals to DEFAULT_NEW_LETTER_TYPE_ID
        defaultCustNewsLetterConfigShouldBeFound("newLetterTypeId.equals=" + DEFAULT_NEW_LETTER_TYPE_ID);

        // Get all the custNewsLetterConfigList where newLetterTypeId equals to UPDATED_NEW_LETTER_TYPE_ID
        defaultCustNewsLetterConfigShouldNotBeFound("newLetterTypeId.equals=" + UPDATED_NEW_LETTER_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllCustNewsLetterConfigsByNewLetterTypeIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        // Get all the custNewsLetterConfigList where newLetterTypeId not equals to DEFAULT_NEW_LETTER_TYPE_ID
        defaultCustNewsLetterConfigShouldNotBeFound("newLetterTypeId.notEquals=" + DEFAULT_NEW_LETTER_TYPE_ID);

        // Get all the custNewsLetterConfigList where newLetterTypeId not equals to UPDATED_NEW_LETTER_TYPE_ID
        defaultCustNewsLetterConfigShouldBeFound("newLetterTypeId.notEquals=" + UPDATED_NEW_LETTER_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllCustNewsLetterConfigsByNewLetterTypeIdIsInShouldWork() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        // Get all the custNewsLetterConfigList where newLetterTypeId in DEFAULT_NEW_LETTER_TYPE_ID or UPDATED_NEW_LETTER_TYPE_ID
        defaultCustNewsLetterConfigShouldBeFound("newLetterTypeId.in=" + DEFAULT_NEW_LETTER_TYPE_ID + "," + UPDATED_NEW_LETTER_TYPE_ID);

        // Get all the custNewsLetterConfigList where newLetterTypeId equals to UPDATED_NEW_LETTER_TYPE_ID
        defaultCustNewsLetterConfigShouldNotBeFound("newLetterTypeId.in=" + UPDATED_NEW_LETTER_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllCustNewsLetterConfigsByNewLetterTypeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        // Get all the custNewsLetterConfigList where newLetterTypeId is not null
        defaultCustNewsLetterConfigShouldBeFound("newLetterTypeId.specified=true");

        // Get all the custNewsLetterConfigList where newLetterTypeId is null
        defaultCustNewsLetterConfigShouldNotBeFound("newLetterTypeId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustNewsLetterConfigsByNewLetterTypeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        // Get all the custNewsLetterConfigList where newLetterTypeId is greater than or equal to DEFAULT_NEW_LETTER_TYPE_ID
        defaultCustNewsLetterConfigShouldBeFound("newLetterTypeId.greaterThanOrEqual=" + DEFAULT_NEW_LETTER_TYPE_ID);

        // Get all the custNewsLetterConfigList where newLetterTypeId is greater than or equal to UPDATED_NEW_LETTER_TYPE_ID
        defaultCustNewsLetterConfigShouldNotBeFound("newLetterTypeId.greaterThanOrEqual=" + UPDATED_NEW_LETTER_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllCustNewsLetterConfigsByNewLetterTypeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        // Get all the custNewsLetterConfigList where newLetterTypeId is less than or equal to DEFAULT_NEW_LETTER_TYPE_ID
        defaultCustNewsLetterConfigShouldBeFound("newLetterTypeId.lessThanOrEqual=" + DEFAULT_NEW_LETTER_TYPE_ID);

        // Get all the custNewsLetterConfigList where newLetterTypeId is less than or equal to SMALLER_NEW_LETTER_TYPE_ID
        defaultCustNewsLetterConfigShouldNotBeFound("newLetterTypeId.lessThanOrEqual=" + SMALLER_NEW_LETTER_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllCustNewsLetterConfigsByNewLetterTypeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        // Get all the custNewsLetterConfigList where newLetterTypeId is less than DEFAULT_NEW_LETTER_TYPE_ID
        defaultCustNewsLetterConfigShouldNotBeFound("newLetterTypeId.lessThan=" + DEFAULT_NEW_LETTER_TYPE_ID);

        // Get all the custNewsLetterConfigList where newLetterTypeId is less than UPDATED_NEW_LETTER_TYPE_ID
        defaultCustNewsLetterConfigShouldBeFound("newLetterTypeId.lessThan=" + UPDATED_NEW_LETTER_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllCustNewsLetterConfigsByNewLetterTypeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        // Get all the custNewsLetterConfigList where newLetterTypeId is greater than DEFAULT_NEW_LETTER_TYPE_ID
        defaultCustNewsLetterConfigShouldNotBeFound("newLetterTypeId.greaterThan=" + DEFAULT_NEW_LETTER_TYPE_ID);

        // Get all the custNewsLetterConfigList where newLetterTypeId is greater than SMALLER_NEW_LETTER_TYPE_ID
        defaultCustNewsLetterConfigShouldBeFound("newLetterTypeId.greaterThan=" + SMALLER_NEW_LETTER_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllCustNewsLetterConfigsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        // Get all the custNewsLetterConfigList where value equals to DEFAULT_VALUE
        defaultCustNewsLetterConfigShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the custNewsLetterConfigList where value equals to UPDATED_VALUE
        defaultCustNewsLetterConfigShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustNewsLetterConfigsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        // Get all the custNewsLetterConfigList where value not equals to DEFAULT_VALUE
        defaultCustNewsLetterConfigShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the custNewsLetterConfigList where value not equals to UPDATED_VALUE
        defaultCustNewsLetterConfigShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustNewsLetterConfigsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        // Get all the custNewsLetterConfigList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultCustNewsLetterConfigShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the custNewsLetterConfigList where value equals to UPDATED_VALUE
        defaultCustNewsLetterConfigShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustNewsLetterConfigsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        // Get all the custNewsLetterConfigList where value is not null
        defaultCustNewsLetterConfigShouldBeFound("value.specified=true");

        // Get all the custNewsLetterConfigList where value is null
        defaultCustNewsLetterConfigShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    void getAllCustNewsLetterConfigsByValueContainsSomething() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        // Get all the custNewsLetterConfigList where value contains DEFAULT_VALUE
        defaultCustNewsLetterConfigShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the custNewsLetterConfigList where value contains UPDATED_VALUE
        defaultCustNewsLetterConfigShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustNewsLetterConfigsByValueNotContainsSomething() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        // Get all the custNewsLetterConfigList where value does not contain DEFAULT_VALUE
        defaultCustNewsLetterConfigShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the custNewsLetterConfigList where value does not contain UPDATED_VALUE
        defaultCustNewsLetterConfigShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustNewsLetterConfigsByCustomerIdIsEqualToSomething() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        // Get all the custNewsLetterConfigList where customerId equals to DEFAULT_CUSTOMER_ID
        defaultCustNewsLetterConfigShouldBeFound("customerId.equals=" + DEFAULT_CUSTOMER_ID);

        // Get all the custNewsLetterConfigList where customerId equals to UPDATED_CUSTOMER_ID
        defaultCustNewsLetterConfigShouldNotBeFound("customerId.equals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustNewsLetterConfigsByCustomerIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        // Get all the custNewsLetterConfigList where customerId not equals to DEFAULT_CUSTOMER_ID
        defaultCustNewsLetterConfigShouldNotBeFound("customerId.notEquals=" + DEFAULT_CUSTOMER_ID);

        // Get all the custNewsLetterConfigList where customerId not equals to UPDATED_CUSTOMER_ID
        defaultCustNewsLetterConfigShouldBeFound("customerId.notEquals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustNewsLetterConfigsByCustomerIdIsInShouldWork() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        // Get all the custNewsLetterConfigList where customerId in DEFAULT_CUSTOMER_ID or UPDATED_CUSTOMER_ID
        defaultCustNewsLetterConfigShouldBeFound("customerId.in=" + DEFAULT_CUSTOMER_ID + "," + UPDATED_CUSTOMER_ID);

        // Get all the custNewsLetterConfigList where customerId equals to UPDATED_CUSTOMER_ID
        defaultCustNewsLetterConfigShouldNotBeFound("customerId.in=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustNewsLetterConfigsByCustomerIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        // Get all the custNewsLetterConfigList where customerId is not null
        defaultCustNewsLetterConfigShouldBeFound("customerId.specified=true");

        // Get all the custNewsLetterConfigList where customerId is null
        defaultCustNewsLetterConfigShouldNotBeFound("customerId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustNewsLetterConfigsByCustomerIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        // Get all the custNewsLetterConfigList where customerId is greater than or equal to DEFAULT_CUSTOMER_ID
        defaultCustNewsLetterConfigShouldBeFound("customerId.greaterThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the custNewsLetterConfigList where customerId is greater than or equal to UPDATED_CUSTOMER_ID
        defaultCustNewsLetterConfigShouldNotBeFound("customerId.greaterThanOrEqual=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustNewsLetterConfigsByCustomerIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        // Get all the custNewsLetterConfigList where customerId is less than or equal to DEFAULT_CUSTOMER_ID
        defaultCustNewsLetterConfigShouldBeFound("customerId.lessThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the custNewsLetterConfigList where customerId is less than or equal to SMALLER_CUSTOMER_ID
        defaultCustNewsLetterConfigShouldNotBeFound("customerId.lessThanOrEqual=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustNewsLetterConfigsByCustomerIdIsLessThanSomething() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        // Get all the custNewsLetterConfigList where customerId is less than DEFAULT_CUSTOMER_ID
        defaultCustNewsLetterConfigShouldNotBeFound("customerId.lessThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the custNewsLetterConfigList where customerId is less than UPDATED_CUSTOMER_ID
        defaultCustNewsLetterConfigShouldBeFound("customerId.lessThan=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustNewsLetterConfigsByCustomerIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        // Get all the custNewsLetterConfigList where customerId is greater than DEFAULT_CUSTOMER_ID
        defaultCustNewsLetterConfigShouldNotBeFound("customerId.greaterThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the custNewsLetterConfigList where customerId is greater than SMALLER_CUSTOMER_ID
        defaultCustNewsLetterConfigShouldBeFound("customerId.greaterThan=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustNewsLetterConfigsByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);
        Customer customer = CustomerResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        custNewsLetterConfig.setCustomer(customer);
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);
        Long customerId = customer.getId();

        // Get all the custNewsLetterConfigList where customer equals to customerId
        defaultCustNewsLetterConfigShouldBeFound("customerId.equals=" + customerId);

        // Get all the custNewsLetterConfigList where customer equals to (customerId + 1)
        defaultCustNewsLetterConfigShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }

    @Test
    @Transactional
    void getAllCustNewsLetterConfigsByNewsLetterTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);
        NewsLetterType newsLetterType = NewsLetterTypeResourceIT.createEntity(em);
        em.persist(newsLetterType);
        em.flush();
        custNewsLetterConfig.setNewsLetterType(newsLetterType);
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);
        Long newsLetterTypeId = newsLetterType.getId();

        // Get all the custNewsLetterConfigList where newsLetterType equals to newsLetterTypeId
        defaultCustNewsLetterConfigShouldBeFound("newsLetterTypeId.equals=" + newsLetterTypeId);

        // Get all the custNewsLetterConfigList where newsLetterType equals to (newsLetterTypeId + 1)
        defaultCustNewsLetterConfigShouldNotBeFound("newsLetterTypeId.equals=" + (newsLetterTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustNewsLetterConfigShouldBeFound(String filter) throws Exception {
        restCustNewsLetterConfigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custNewsLetterConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].newLetterTypeId").value(hasItem(DEFAULT_NEW_LETTER_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())));

        // Check, that the count call also returns 1
        restCustNewsLetterConfigMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustNewsLetterConfigShouldNotBeFound(String filter) throws Exception {
        restCustNewsLetterConfigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustNewsLetterConfigMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustNewsLetterConfig() throws Exception {
        // Get the custNewsLetterConfig
        restCustNewsLetterConfigMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustNewsLetterConfig() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        int databaseSizeBeforeUpdate = custNewsLetterConfigRepository.findAll().size();

        // Update the custNewsLetterConfig
        CustNewsLetterConfig updatedCustNewsLetterConfig = custNewsLetterConfigRepository.findById(custNewsLetterConfig.getId()).get();
        // Disconnect from session so that the updates on updatedCustNewsLetterConfig are not directly saved in db
        em.detach(updatedCustNewsLetterConfig);
        updatedCustNewsLetterConfig.newLetterTypeId(UPDATED_NEW_LETTER_TYPE_ID).value(UPDATED_VALUE).customerId(UPDATED_CUSTOMER_ID);
        CustNewsLetterConfigDTO custNewsLetterConfigDTO = custNewsLetterConfigMapper.toDto(updatedCustNewsLetterConfig);

        restCustNewsLetterConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custNewsLetterConfigDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custNewsLetterConfigDTO))
            )
            .andExpect(status().isOk());

        // Validate the CustNewsLetterConfig in the database
        List<CustNewsLetterConfig> custNewsLetterConfigList = custNewsLetterConfigRepository.findAll();
        assertThat(custNewsLetterConfigList).hasSize(databaseSizeBeforeUpdate);
        CustNewsLetterConfig testCustNewsLetterConfig = custNewsLetterConfigList.get(custNewsLetterConfigList.size() - 1);
        assertThat(testCustNewsLetterConfig.getNewLetterTypeId()).isEqualTo(UPDATED_NEW_LETTER_TYPE_ID);
        assertThat(testCustNewsLetterConfig.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustNewsLetterConfig.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustNewsLetterConfig() throws Exception {
        int databaseSizeBeforeUpdate = custNewsLetterConfigRepository.findAll().size();
        custNewsLetterConfig.setId(count.incrementAndGet());

        // Create the CustNewsLetterConfig
        CustNewsLetterConfigDTO custNewsLetterConfigDTO = custNewsLetterConfigMapper.toDto(custNewsLetterConfig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustNewsLetterConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custNewsLetterConfigDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custNewsLetterConfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustNewsLetterConfig in the database
        List<CustNewsLetterConfig> custNewsLetterConfigList = custNewsLetterConfigRepository.findAll();
        assertThat(custNewsLetterConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustNewsLetterConfig() throws Exception {
        int databaseSizeBeforeUpdate = custNewsLetterConfigRepository.findAll().size();
        custNewsLetterConfig.setId(count.incrementAndGet());

        // Create the CustNewsLetterConfig
        CustNewsLetterConfigDTO custNewsLetterConfigDTO = custNewsLetterConfigMapper.toDto(custNewsLetterConfig);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustNewsLetterConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custNewsLetterConfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustNewsLetterConfig in the database
        List<CustNewsLetterConfig> custNewsLetterConfigList = custNewsLetterConfigRepository.findAll();
        assertThat(custNewsLetterConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustNewsLetterConfig() throws Exception {
        int databaseSizeBeforeUpdate = custNewsLetterConfigRepository.findAll().size();
        custNewsLetterConfig.setId(count.incrementAndGet());

        // Create the CustNewsLetterConfig
        CustNewsLetterConfigDTO custNewsLetterConfigDTO = custNewsLetterConfigMapper.toDto(custNewsLetterConfig);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustNewsLetterConfigMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custNewsLetterConfigDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustNewsLetterConfig in the database
        List<CustNewsLetterConfig> custNewsLetterConfigList = custNewsLetterConfigRepository.findAll();
        assertThat(custNewsLetterConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustNewsLetterConfigWithPatch() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        int databaseSizeBeforeUpdate = custNewsLetterConfigRepository.findAll().size();

        // Update the custNewsLetterConfig using partial update
        CustNewsLetterConfig partialUpdatedCustNewsLetterConfig = new CustNewsLetterConfig();
        partialUpdatedCustNewsLetterConfig.setId(custNewsLetterConfig.getId());

        partialUpdatedCustNewsLetterConfig.value(UPDATED_VALUE);

        restCustNewsLetterConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustNewsLetterConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustNewsLetterConfig))
            )
            .andExpect(status().isOk());

        // Validate the CustNewsLetterConfig in the database
        List<CustNewsLetterConfig> custNewsLetterConfigList = custNewsLetterConfigRepository.findAll();
        assertThat(custNewsLetterConfigList).hasSize(databaseSizeBeforeUpdate);
        CustNewsLetterConfig testCustNewsLetterConfig = custNewsLetterConfigList.get(custNewsLetterConfigList.size() - 1);
        assertThat(testCustNewsLetterConfig.getNewLetterTypeId()).isEqualTo(DEFAULT_NEW_LETTER_TYPE_ID);
        assertThat(testCustNewsLetterConfig.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustNewsLetterConfig.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustNewsLetterConfigWithPatch() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        int databaseSizeBeforeUpdate = custNewsLetterConfigRepository.findAll().size();

        // Update the custNewsLetterConfig using partial update
        CustNewsLetterConfig partialUpdatedCustNewsLetterConfig = new CustNewsLetterConfig();
        partialUpdatedCustNewsLetterConfig.setId(custNewsLetterConfig.getId());

        partialUpdatedCustNewsLetterConfig.newLetterTypeId(UPDATED_NEW_LETTER_TYPE_ID).value(UPDATED_VALUE).customerId(UPDATED_CUSTOMER_ID);

        restCustNewsLetterConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustNewsLetterConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustNewsLetterConfig))
            )
            .andExpect(status().isOk());

        // Validate the CustNewsLetterConfig in the database
        List<CustNewsLetterConfig> custNewsLetterConfigList = custNewsLetterConfigRepository.findAll();
        assertThat(custNewsLetterConfigList).hasSize(databaseSizeBeforeUpdate);
        CustNewsLetterConfig testCustNewsLetterConfig = custNewsLetterConfigList.get(custNewsLetterConfigList.size() - 1);
        assertThat(testCustNewsLetterConfig.getNewLetterTypeId()).isEqualTo(UPDATED_NEW_LETTER_TYPE_ID);
        assertThat(testCustNewsLetterConfig.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustNewsLetterConfig.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustNewsLetterConfig() throws Exception {
        int databaseSizeBeforeUpdate = custNewsLetterConfigRepository.findAll().size();
        custNewsLetterConfig.setId(count.incrementAndGet());

        // Create the CustNewsLetterConfig
        CustNewsLetterConfigDTO custNewsLetterConfigDTO = custNewsLetterConfigMapper.toDto(custNewsLetterConfig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustNewsLetterConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custNewsLetterConfigDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custNewsLetterConfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustNewsLetterConfig in the database
        List<CustNewsLetterConfig> custNewsLetterConfigList = custNewsLetterConfigRepository.findAll();
        assertThat(custNewsLetterConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustNewsLetterConfig() throws Exception {
        int databaseSizeBeforeUpdate = custNewsLetterConfigRepository.findAll().size();
        custNewsLetterConfig.setId(count.incrementAndGet());

        // Create the CustNewsLetterConfig
        CustNewsLetterConfigDTO custNewsLetterConfigDTO = custNewsLetterConfigMapper.toDto(custNewsLetterConfig);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustNewsLetterConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custNewsLetterConfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustNewsLetterConfig in the database
        List<CustNewsLetterConfig> custNewsLetterConfigList = custNewsLetterConfigRepository.findAll();
        assertThat(custNewsLetterConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustNewsLetterConfig() throws Exception {
        int databaseSizeBeforeUpdate = custNewsLetterConfigRepository.findAll().size();
        custNewsLetterConfig.setId(count.incrementAndGet());

        // Create the CustNewsLetterConfig
        CustNewsLetterConfigDTO custNewsLetterConfigDTO = custNewsLetterConfigMapper.toDto(custNewsLetterConfig);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustNewsLetterConfigMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custNewsLetterConfigDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustNewsLetterConfig in the database
        List<CustNewsLetterConfig> custNewsLetterConfigList = custNewsLetterConfigRepository.findAll();
        assertThat(custNewsLetterConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustNewsLetterConfig() throws Exception {
        // Initialize the database
        custNewsLetterConfigRepository.saveAndFlush(custNewsLetterConfig);

        int databaseSizeBeforeDelete = custNewsLetterConfigRepository.findAll().size();

        // Delete the custNewsLetterConfig
        restCustNewsLetterConfigMockMvc
            .perform(delete(ENTITY_API_URL_ID, custNewsLetterConfig.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustNewsLetterConfig> custNewsLetterConfigList = custNewsLetterConfigRepository.findAll();
        assertThat(custNewsLetterConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
