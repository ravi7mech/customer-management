package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.CustSecurityChar;
import com.apptium.customer.domain.Customer;
import com.apptium.customer.repository.CustSecurityCharRepository;
import com.apptium.customer.service.criteria.CustSecurityCharCriteria;
import com.apptium.customer.service.dto.CustSecurityCharDTO;
import com.apptium.customer.service.mapper.CustSecurityCharMapper;
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
 * Integration tests for the {@link CustSecurityCharResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustSecurityCharResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;
    private static final Long SMALLER_CUSTOMER_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/cust-security-chars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustSecurityCharRepository custSecurityCharRepository;

    @Autowired
    private CustSecurityCharMapper custSecurityCharMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustSecurityCharMockMvc;

    private CustSecurityChar custSecurityChar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustSecurityChar createEntity(EntityManager em) {
        CustSecurityChar custSecurityChar = new CustSecurityChar()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .valueType(DEFAULT_VALUE_TYPE)
            .customerId(DEFAULT_CUSTOMER_ID);
        return custSecurityChar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustSecurityChar createUpdatedEntity(EntityManager em) {
        CustSecurityChar custSecurityChar = new CustSecurityChar()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valueType(UPDATED_VALUE_TYPE)
            .customerId(UPDATED_CUSTOMER_ID);
        return custSecurityChar;
    }

    @BeforeEach
    public void initTest() {
        custSecurityChar = createEntity(em);
    }

    @Test
    @Transactional
    void createCustSecurityChar() throws Exception {
        int databaseSizeBeforeCreate = custSecurityCharRepository.findAll().size();
        // Create the CustSecurityChar
        CustSecurityCharDTO custSecurityCharDTO = custSecurityCharMapper.toDto(custSecurityChar);
        restCustSecurityCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custSecurityCharDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CustSecurityChar in the database
        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeCreate + 1);
        CustSecurityChar testCustSecurityChar = custSecurityCharList.get(custSecurityCharList.size() - 1);
        assertThat(testCustSecurityChar.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustSecurityChar.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCustSecurityChar.getValueType()).isEqualTo(DEFAULT_VALUE_TYPE);
        assertThat(testCustSecurityChar.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void createCustSecurityCharWithExistingId() throws Exception {
        // Create the CustSecurityChar with an existing ID
        custSecurityChar.setId(1L);
        CustSecurityCharDTO custSecurityCharDTO = custSecurityCharMapper.toDto(custSecurityChar);

        int databaseSizeBeforeCreate = custSecurityCharRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustSecurityCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custSecurityCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustSecurityChar in the database
        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = custSecurityCharRepository.findAll().size();
        // set the field null
        custSecurityChar.setName(null);

        // Create the CustSecurityChar, which fails.
        CustSecurityCharDTO custSecurityCharDTO = custSecurityCharMapper.toDto(custSecurityChar);

        restCustSecurityCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custSecurityCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = custSecurityCharRepository.findAll().size();
        // set the field null
        custSecurityChar.setValue(null);

        // Create the CustSecurityChar, which fails.
        CustSecurityCharDTO custSecurityCharDTO = custSecurityCharMapper.toDto(custSecurityChar);

        restCustSecurityCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custSecurityCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = custSecurityCharRepository.findAll().size();
        // set the field null
        custSecurityChar.setValueType(null);

        // Create the CustSecurityChar, which fails.
        CustSecurityCharDTO custSecurityCharDTO = custSecurityCharMapper.toDto(custSecurityChar);

        restCustSecurityCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custSecurityCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custSecurityCharRepository.findAll().size();
        // set the field null
        custSecurityChar.setCustomerId(null);

        // Create the CustSecurityChar, which fails.
        CustSecurityCharDTO custSecurityCharDTO = custSecurityCharMapper.toDto(custSecurityChar);

        restCustSecurityCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custSecurityCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustSecurityChars() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        // Get all the custSecurityCharList
        restCustSecurityCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custSecurityChar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())));
    }

    @Test
    @Transactional
    void getCustSecurityChar() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        // Get the custSecurityChar
        restCustSecurityCharMockMvc
            .perform(get(ENTITY_API_URL_ID, custSecurityChar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custSecurityChar.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.valueType").value(DEFAULT_VALUE_TYPE))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()));
    }

    @Test
    @Transactional
    void getCustSecurityCharsByIdFiltering() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        Long id = custSecurityChar.getId();

        defaultCustSecurityCharShouldBeFound("id.equals=" + id);
        defaultCustSecurityCharShouldNotBeFound("id.notEquals=" + id);

        defaultCustSecurityCharShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustSecurityCharShouldNotBeFound("id.greaterThan=" + id);

        defaultCustSecurityCharShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustSecurityCharShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustSecurityCharsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        // Get all the custSecurityCharList where name equals to DEFAULT_NAME
        defaultCustSecurityCharShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the custSecurityCharList where name equals to UPDATED_NAME
        defaultCustSecurityCharShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustSecurityCharsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        // Get all the custSecurityCharList where name not equals to DEFAULT_NAME
        defaultCustSecurityCharShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the custSecurityCharList where name not equals to UPDATED_NAME
        defaultCustSecurityCharShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustSecurityCharsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        // Get all the custSecurityCharList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCustSecurityCharShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the custSecurityCharList where name equals to UPDATED_NAME
        defaultCustSecurityCharShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustSecurityCharsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        // Get all the custSecurityCharList where name is not null
        defaultCustSecurityCharShouldBeFound("name.specified=true");

        // Get all the custSecurityCharList where name is null
        defaultCustSecurityCharShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCustSecurityCharsByNameContainsSomething() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        // Get all the custSecurityCharList where name contains DEFAULT_NAME
        defaultCustSecurityCharShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the custSecurityCharList where name contains UPDATED_NAME
        defaultCustSecurityCharShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustSecurityCharsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        // Get all the custSecurityCharList where name does not contain DEFAULT_NAME
        defaultCustSecurityCharShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the custSecurityCharList where name does not contain UPDATED_NAME
        defaultCustSecurityCharShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustSecurityCharsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        // Get all the custSecurityCharList where value equals to DEFAULT_VALUE
        defaultCustSecurityCharShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the custSecurityCharList where value equals to UPDATED_VALUE
        defaultCustSecurityCharShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustSecurityCharsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        // Get all the custSecurityCharList where value not equals to DEFAULT_VALUE
        defaultCustSecurityCharShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the custSecurityCharList where value not equals to UPDATED_VALUE
        defaultCustSecurityCharShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustSecurityCharsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        // Get all the custSecurityCharList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultCustSecurityCharShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the custSecurityCharList where value equals to UPDATED_VALUE
        defaultCustSecurityCharShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustSecurityCharsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        // Get all the custSecurityCharList where value is not null
        defaultCustSecurityCharShouldBeFound("value.specified=true");

        // Get all the custSecurityCharList where value is null
        defaultCustSecurityCharShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    void getAllCustSecurityCharsByValueContainsSomething() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        // Get all the custSecurityCharList where value contains DEFAULT_VALUE
        defaultCustSecurityCharShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the custSecurityCharList where value contains UPDATED_VALUE
        defaultCustSecurityCharShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustSecurityCharsByValueNotContainsSomething() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        // Get all the custSecurityCharList where value does not contain DEFAULT_VALUE
        defaultCustSecurityCharShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the custSecurityCharList where value does not contain UPDATED_VALUE
        defaultCustSecurityCharShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustSecurityCharsByValueTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        // Get all the custSecurityCharList where valueType equals to DEFAULT_VALUE_TYPE
        defaultCustSecurityCharShouldBeFound("valueType.equals=" + DEFAULT_VALUE_TYPE);

        // Get all the custSecurityCharList where valueType equals to UPDATED_VALUE_TYPE
        defaultCustSecurityCharShouldNotBeFound("valueType.equals=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllCustSecurityCharsByValueTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        // Get all the custSecurityCharList where valueType not equals to DEFAULT_VALUE_TYPE
        defaultCustSecurityCharShouldNotBeFound("valueType.notEquals=" + DEFAULT_VALUE_TYPE);

        // Get all the custSecurityCharList where valueType not equals to UPDATED_VALUE_TYPE
        defaultCustSecurityCharShouldBeFound("valueType.notEquals=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllCustSecurityCharsByValueTypeIsInShouldWork() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        // Get all the custSecurityCharList where valueType in DEFAULT_VALUE_TYPE or UPDATED_VALUE_TYPE
        defaultCustSecurityCharShouldBeFound("valueType.in=" + DEFAULT_VALUE_TYPE + "," + UPDATED_VALUE_TYPE);

        // Get all the custSecurityCharList where valueType equals to UPDATED_VALUE_TYPE
        defaultCustSecurityCharShouldNotBeFound("valueType.in=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllCustSecurityCharsByValueTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        // Get all the custSecurityCharList where valueType is not null
        defaultCustSecurityCharShouldBeFound("valueType.specified=true");

        // Get all the custSecurityCharList where valueType is null
        defaultCustSecurityCharShouldNotBeFound("valueType.specified=false");
    }

    @Test
    @Transactional
    void getAllCustSecurityCharsByValueTypeContainsSomething() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        // Get all the custSecurityCharList where valueType contains DEFAULT_VALUE_TYPE
        defaultCustSecurityCharShouldBeFound("valueType.contains=" + DEFAULT_VALUE_TYPE);

        // Get all the custSecurityCharList where valueType contains UPDATED_VALUE_TYPE
        defaultCustSecurityCharShouldNotBeFound("valueType.contains=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllCustSecurityCharsByValueTypeNotContainsSomething() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        // Get all the custSecurityCharList where valueType does not contain DEFAULT_VALUE_TYPE
        defaultCustSecurityCharShouldNotBeFound("valueType.doesNotContain=" + DEFAULT_VALUE_TYPE);

        // Get all the custSecurityCharList where valueType does not contain UPDATED_VALUE_TYPE
        defaultCustSecurityCharShouldBeFound("valueType.doesNotContain=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllCustSecurityCharsByCustomerIdIsEqualToSomething() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        // Get all the custSecurityCharList where customerId equals to DEFAULT_CUSTOMER_ID
        defaultCustSecurityCharShouldBeFound("customerId.equals=" + DEFAULT_CUSTOMER_ID);

        // Get all the custSecurityCharList where customerId equals to UPDATED_CUSTOMER_ID
        defaultCustSecurityCharShouldNotBeFound("customerId.equals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustSecurityCharsByCustomerIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        // Get all the custSecurityCharList where customerId not equals to DEFAULT_CUSTOMER_ID
        defaultCustSecurityCharShouldNotBeFound("customerId.notEquals=" + DEFAULT_CUSTOMER_ID);

        // Get all the custSecurityCharList where customerId not equals to UPDATED_CUSTOMER_ID
        defaultCustSecurityCharShouldBeFound("customerId.notEquals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustSecurityCharsByCustomerIdIsInShouldWork() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        // Get all the custSecurityCharList where customerId in DEFAULT_CUSTOMER_ID or UPDATED_CUSTOMER_ID
        defaultCustSecurityCharShouldBeFound("customerId.in=" + DEFAULT_CUSTOMER_ID + "," + UPDATED_CUSTOMER_ID);

        // Get all the custSecurityCharList where customerId equals to UPDATED_CUSTOMER_ID
        defaultCustSecurityCharShouldNotBeFound("customerId.in=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustSecurityCharsByCustomerIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        // Get all the custSecurityCharList where customerId is not null
        defaultCustSecurityCharShouldBeFound("customerId.specified=true");

        // Get all the custSecurityCharList where customerId is null
        defaultCustSecurityCharShouldNotBeFound("customerId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustSecurityCharsByCustomerIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        // Get all the custSecurityCharList where customerId is greater than or equal to DEFAULT_CUSTOMER_ID
        defaultCustSecurityCharShouldBeFound("customerId.greaterThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the custSecurityCharList where customerId is greater than or equal to UPDATED_CUSTOMER_ID
        defaultCustSecurityCharShouldNotBeFound("customerId.greaterThanOrEqual=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustSecurityCharsByCustomerIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        // Get all the custSecurityCharList where customerId is less than or equal to DEFAULT_CUSTOMER_ID
        defaultCustSecurityCharShouldBeFound("customerId.lessThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the custSecurityCharList where customerId is less than or equal to SMALLER_CUSTOMER_ID
        defaultCustSecurityCharShouldNotBeFound("customerId.lessThanOrEqual=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustSecurityCharsByCustomerIdIsLessThanSomething() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        // Get all the custSecurityCharList where customerId is less than DEFAULT_CUSTOMER_ID
        defaultCustSecurityCharShouldNotBeFound("customerId.lessThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the custSecurityCharList where customerId is less than UPDATED_CUSTOMER_ID
        defaultCustSecurityCharShouldBeFound("customerId.lessThan=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustSecurityCharsByCustomerIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        // Get all the custSecurityCharList where customerId is greater than DEFAULT_CUSTOMER_ID
        defaultCustSecurityCharShouldNotBeFound("customerId.greaterThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the custSecurityCharList where customerId is greater than SMALLER_CUSTOMER_ID
        defaultCustSecurityCharShouldBeFound("customerId.greaterThan=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustSecurityCharsByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);
        Customer customer = CustomerResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        custSecurityChar.setCustomer(customer);
        custSecurityCharRepository.saveAndFlush(custSecurityChar);
        Long customerId = customer.getId();

        // Get all the custSecurityCharList where customer equals to customerId
        defaultCustSecurityCharShouldBeFound("customerId.equals=" + customerId);

        // Get all the custSecurityCharList where customer equals to (customerId + 1)
        defaultCustSecurityCharShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustSecurityCharShouldBeFound(String filter) throws Exception {
        restCustSecurityCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custSecurityChar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())));

        // Check, that the count call also returns 1
        restCustSecurityCharMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustSecurityCharShouldNotBeFound(String filter) throws Exception {
        restCustSecurityCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustSecurityCharMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustSecurityChar() throws Exception {
        // Get the custSecurityChar
        restCustSecurityCharMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustSecurityChar() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        int databaseSizeBeforeUpdate = custSecurityCharRepository.findAll().size();

        // Update the custSecurityChar
        CustSecurityChar updatedCustSecurityChar = custSecurityCharRepository.findById(custSecurityChar.getId()).get();
        // Disconnect from session so that the updates on updatedCustSecurityChar are not directly saved in db
        em.detach(updatedCustSecurityChar);
        updatedCustSecurityChar.name(UPDATED_NAME).value(UPDATED_VALUE).valueType(UPDATED_VALUE_TYPE).customerId(UPDATED_CUSTOMER_ID);
        CustSecurityCharDTO custSecurityCharDTO = custSecurityCharMapper.toDto(updatedCustSecurityChar);

        restCustSecurityCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custSecurityCharDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custSecurityCharDTO))
            )
            .andExpect(status().isOk());

        // Validate the CustSecurityChar in the database
        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeUpdate);
        CustSecurityChar testCustSecurityChar = custSecurityCharList.get(custSecurityCharList.size() - 1);
        assertThat(testCustSecurityChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustSecurityChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustSecurityChar.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testCustSecurityChar.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustSecurityChar() throws Exception {
        int databaseSizeBeforeUpdate = custSecurityCharRepository.findAll().size();
        custSecurityChar.setId(count.incrementAndGet());

        // Create the CustSecurityChar
        CustSecurityCharDTO custSecurityCharDTO = custSecurityCharMapper.toDto(custSecurityChar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustSecurityCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custSecurityCharDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custSecurityCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustSecurityChar in the database
        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustSecurityChar() throws Exception {
        int databaseSizeBeforeUpdate = custSecurityCharRepository.findAll().size();
        custSecurityChar.setId(count.incrementAndGet());

        // Create the CustSecurityChar
        CustSecurityCharDTO custSecurityCharDTO = custSecurityCharMapper.toDto(custSecurityChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustSecurityCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custSecurityCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustSecurityChar in the database
        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustSecurityChar() throws Exception {
        int databaseSizeBeforeUpdate = custSecurityCharRepository.findAll().size();
        custSecurityChar.setId(count.incrementAndGet());

        // Create the CustSecurityChar
        CustSecurityCharDTO custSecurityCharDTO = custSecurityCharMapper.toDto(custSecurityChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustSecurityCharMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custSecurityCharDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustSecurityChar in the database
        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustSecurityCharWithPatch() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        int databaseSizeBeforeUpdate = custSecurityCharRepository.findAll().size();

        // Update the custSecurityChar using partial update
        CustSecurityChar partialUpdatedCustSecurityChar = new CustSecurityChar();
        partialUpdatedCustSecurityChar.setId(custSecurityChar.getId());

        partialUpdatedCustSecurityChar.value(UPDATED_VALUE).customerId(UPDATED_CUSTOMER_ID);

        restCustSecurityCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustSecurityChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustSecurityChar))
            )
            .andExpect(status().isOk());

        // Validate the CustSecurityChar in the database
        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeUpdate);
        CustSecurityChar testCustSecurityChar = custSecurityCharList.get(custSecurityCharList.size() - 1);
        assertThat(testCustSecurityChar.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustSecurityChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustSecurityChar.getValueType()).isEqualTo(DEFAULT_VALUE_TYPE);
        assertThat(testCustSecurityChar.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustSecurityCharWithPatch() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        int databaseSizeBeforeUpdate = custSecurityCharRepository.findAll().size();

        // Update the custSecurityChar using partial update
        CustSecurityChar partialUpdatedCustSecurityChar = new CustSecurityChar();
        partialUpdatedCustSecurityChar.setId(custSecurityChar.getId());

        partialUpdatedCustSecurityChar
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valueType(UPDATED_VALUE_TYPE)
            .customerId(UPDATED_CUSTOMER_ID);

        restCustSecurityCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustSecurityChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustSecurityChar))
            )
            .andExpect(status().isOk());

        // Validate the CustSecurityChar in the database
        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeUpdate);
        CustSecurityChar testCustSecurityChar = custSecurityCharList.get(custSecurityCharList.size() - 1);
        assertThat(testCustSecurityChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustSecurityChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustSecurityChar.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testCustSecurityChar.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustSecurityChar() throws Exception {
        int databaseSizeBeforeUpdate = custSecurityCharRepository.findAll().size();
        custSecurityChar.setId(count.incrementAndGet());

        // Create the CustSecurityChar
        CustSecurityCharDTO custSecurityCharDTO = custSecurityCharMapper.toDto(custSecurityChar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustSecurityCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custSecurityCharDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custSecurityCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustSecurityChar in the database
        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustSecurityChar() throws Exception {
        int databaseSizeBeforeUpdate = custSecurityCharRepository.findAll().size();
        custSecurityChar.setId(count.incrementAndGet());

        // Create the CustSecurityChar
        CustSecurityCharDTO custSecurityCharDTO = custSecurityCharMapper.toDto(custSecurityChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustSecurityCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custSecurityCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustSecurityChar in the database
        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustSecurityChar() throws Exception {
        int databaseSizeBeforeUpdate = custSecurityCharRepository.findAll().size();
        custSecurityChar.setId(count.incrementAndGet());

        // Create the CustSecurityChar
        CustSecurityCharDTO custSecurityCharDTO = custSecurityCharMapper.toDto(custSecurityChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustSecurityCharMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custSecurityCharDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustSecurityChar in the database
        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustSecurityChar() throws Exception {
        // Initialize the database
        custSecurityCharRepository.saveAndFlush(custSecurityChar);

        int databaseSizeBeforeDelete = custSecurityCharRepository.findAll().size();

        // Delete the custSecurityChar
        restCustSecurityCharMockMvc
            .perform(delete(ENTITY_API_URL_ID, custSecurityChar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustSecurityChar> custSecurityCharList = custSecurityCharRepository.findAll();
        assertThat(custSecurityCharList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
