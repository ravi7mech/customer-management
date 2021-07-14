package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.CustPasswordChar;
import com.apptium.customer.domain.Customer;
import com.apptium.customer.repository.CustPasswordCharRepository;
import com.apptium.customer.service.criteria.CustPasswordCharCriteria;
import com.apptium.customer.service.dto.CustPasswordCharDTO;
import com.apptium.customer.service.mapper.CustPasswordCharMapper;
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
 * Integration tests for the {@link CustPasswordCharResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustPasswordCharResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;
    private static final Long SMALLER_CUSTOMER_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/cust-password-chars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustPasswordCharRepository custPasswordCharRepository;

    @Autowired
    private CustPasswordCharMapper custPasswordCharMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustPasswordCharMockMvc;

    private CustPasswordChar custPasswordChar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustPasswordChar createEntity(EntityManager em) {
        CustPasswordChar custPasswordChar = new CustPasswordChar()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .valueType(DEFAULT_VALUE_TYPE)
            .customerId(DEFAULT_CUSTOMER_ID);
        return custPasswordChar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustPasswordChar createUpdatedEntity(EntityManager em) {
        CustPasswordChar custPasswordChar = new CustPasswordChar()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valueType(UPDATED_VALUE_TYPE)
            .customerId(UPDATED_CUSTOMER_ID);
        return custPasswordChar;
    }

    @BeforeEach
    public void initTest() {
        custPasswordChar = createEntity(em);
    }

    @Test
    @Transactional
    void createCustPasswordChar() throws Exception {
        int databaseSizeBeforeCreate = custPasswordCharRepository.findAll().size();
        // Create the CustPasswordChar
        CustPasswordCharDTO custPasswordCharDTO = custPasswordCharMapper.toDto(custPasswordChar);
        restCustPasswordCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custPasswordCharDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CustPasswordChar in the database
        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeCreate + 1);
        CustPasswordChar testCustPasswordChar = custPasswordCharList.get(custPasswordCharList.size() - 1);
        assertThat(testCustPasswordChar.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustPasswordChar.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCustPasswordChar.getValueType()).isEqualTo(DEFAULT_VALUE_TYPE);
        assertThat(testCustPasswordChar.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void createCustPasswordCharWithExistingId() throws Exception {
        // Create the CustPasswordChar with an existing ID
        custPasswordChar.setId(1L);
        CustPasswordCharDTO custPasswordCharDTO = custPasswordCharMapper.toDto(custPasswordChar);

        int databaseSizeBeforeCreate = custPasswordCharRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustPasswordCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custPasswordCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustPasswordChar in the database
        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = custPasswordCharRepository.findAll().size();
        // set the field null
        custPasswordChar.setName(null);

        // Create the CustPasswordChar, which fails.
        CustPasswordCharDTO custPasswordCharDTO = custPasswordCharMapper.toDto(custPasswordChar);

        restCustPasswordCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custPasswordCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = custPasswordCharRepository.findAll().size();
        // set the field null
        custPasswordChar.setValue(null);

        // Create the CustPasswordChar, which fails.
        CustPasswordCharDTO custPasswordCharDTO = custPasswordCharMapper.toDto(custPasswordChar);

        restCustPasswordCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custPasswordCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = custPasswordCharRepository.findAll().size();
        // set the field null
        custPasswordChar.setValueType(null);

        // Create the CustPasswordChar, which fails.
        CustPasswordCharDTO custPasswordCharDTO = custPasswordCharMapper.toDto(custPasswordChar);

        restCustPasswordCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custPasswordCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custPasswordCharRepository.findAll().size();
        // set the field null
        custPasswordChar.setCustomerId(null);

        // Create the CustPasswordChar, which fails.
        CustPasswordCharDTO custPasswordCharDTO = custPasswordCharMapper.toDto(custPasswordChar);

        restCustPasswordCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custPasswordCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustPasswordChars() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        // Get all the custPasswordCharList
        restCustPasswordCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custPasswordChar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())));
    }

    @Test
    @Transactional
    void getCustPasswordChar() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        // Get the custPasswordChar
        restCustPasswordCharMockMvc
            .perform(get(ENTITY_API_URL_ID, custPasswordChar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custPasswordChar.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.valueType").value(DEFAULT_VALUE_TYPE))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()));
    }

    @Test
    @Transactional
    void getCustPasswordCharsByIdFiltering() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        Long id = custPasswordChar.getId();

        defaultCustPasswordCharShouldBeFound("id.equals=" + id);
        defaultCustPasswordCharShouldNotBeFound("id.notEquals=" + id);

        defaultCustPasswordCharShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustPasswordCharShouldNotBeFound("id.greaterThan=" + id);

        defaultCustPasswordCharShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustPasswordCharShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustPasswordCharsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        // Get all the custPasswordCharList where name equals to DEFAULT_NAME
        defaultCustPasswordCharShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the custPasswordCharList where name equals to UPDATED_NAME
        defaultCustPasswordCharShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustPasswordCharsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        // Get all the custPasswordCharList where name not equals to DEFAULT_NAME
        defaultCustPasswordCharShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the custPasswordCharList where name not equals to UPDATED_NAME
        defaultCustPasswordCharShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustPasswordCharsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        // Get all the custPasswordCharList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCustPasswordCharShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the custPasswordCharList where name equals to UPDATED_NAME
        defaultCustPasswordCharShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustPasswordCharsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        // Get all the custPasswordCharList where name is not null
        defaultCustPasswordCharShouldBeFound("name.specified=true");

        // Get all the custPasswordCharList where name is null
        defaultCustPasswordCharShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCustPasswordCharsByNameContainsSomething() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        // Get all the custPasswordCharList where name contains DEFAULT_NAME
        defaultCustPasswordCharShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the custPasswordCharList where name contains UPDATED_NAME
        defaultCustPasswordCharShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustPasswordCharsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        // Get all the custPasswordCharList where name does not contain DEFAULT_NAME
        defaultCustPasswordCharShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the custPasswordCharList where name does not contain UPDATED_NAME
        defaultCustPasswordCharShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustPasswordCharsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        // Get all the custPasswordCharList where value equals to DEFAULT_VALUE
        defaultCustPasswordCharShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the custPasswordCharList where value equals to UPDATED_VALUE
        defaultCustPasswordCharShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustPasswordCharsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        // Get all the custPasswordCharList where value not equals to DEFAULT_VALUE
        defaultCustPasswordCharShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the custPasswordCharList where value not equals to UPDATED_VALUE
        defaultCustPasswordCharShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustPasswordCharsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        // Get all the custPasswordCharList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultCustPasswordCharShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the custPasswordCharList where value equals to UPDATED_VALUE
        defaultCustPasswordCharShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustPasswordCharsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        // Get all the custPasswordCharList where value is not null
        defaultCustPasswordCharShouldBeFound("value.specified=true");

        // Get all the custPasswordCharList where value is null
        defaultCustPasswordCharShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    void getAllCustPasswordCharsByValueContainsSomething() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        // Get all the custPasswordCharList where value contains DEFAULT_VALUE
        defaultCustPasswordCharShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the custPasswordCharList where value contains UPDATED_VALUE
        defaultCustPasswordCharShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustPasswordCharsByValueNotContainsSomething() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        // Get all the custPasswordCharList where value does not contain DEFAULT_VALUE
        defaultCustPasswordCharShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the custPasswordCharList where value does not contain UPDATED_VALUE
        defaultCustPasswordCharShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustPasswordCharsByValueTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        // Get all the custPasswordCharList where valueType equals to DEFAULT_VALUE_TYPE
        defaultCustPasswordCharShouldBeFound("valueType.equals=" + DEFAULT_VALUE_TYPE);

        // Get all the custPasswordCharList where valueType equals to UPDATED_VALUE_TYPE
        defaultCustPasswordCharShouldNotBeFound("valueType.equals=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllCustPasswordCharsByValueTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        // Get all the custPasswordCharList where valueType not equals to DEFAULT_VALUE_TYPE
        defaultCustPasswordCharShouldNotBeFound("valueType.notEquals=" + DEFAULT_VALUE_TYPE);

        // Get all the custPasswordCharList where valueType not equals to UPDATED_VALUE_TYPE
        defaultCustPasswordCharShouldBeFound("valueType.notEquals=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllCustPasswordCharsByValueTypeIsInShouldWork() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        // Get all the custPasswordCharList where valueType in DEFAULT_VALUE_TYPE or UPDATED_VALUE_TYPE
        defaultCustPasswordCharShouldBeFound("valueType.in=" + DEFAULT_VALUE_TYPE + "," + UPDATED_VALUE_TYPE);

        // Get all the custPasswordCharList where valueType equals to UPDATED_VALUE_TYPE
        defaultCustPasswordCharShouldNotBeFound("valueType.in=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllCustPasswordCharsByValueTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        // Get all the custPasswordCharList where valueType is not null
        defaultCustPasswordCharShouldBeFound("valueType.specified=true");

        // Get all the custPasswordCharList where valueType is null
        defaultCustPasswordCharShouldNotBeFound("valueType.specified=false");
    }

    @Test
    @Transactional
    void getAllCustPasswordCharsByValueTypeContainsSomething() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        // Get all the custPasswordCharList where valueType contains DEFAULT_VALUE_TYPE
        defaultCustPasswordCharShouldBeFound("valueType.contains=" + DEFAULT_VALUE_TYPE);

        // Get all the custPasswordCharList where valueType contains UPDATED_VALUE_TYPE
        defaultCustPasswordCharShouldNotBeFound("valueType.contains=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllCustPasswordCharsByValueTypeNotContainsSomething() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        // Get all the custPasswordCharList where valueType does not contain DEFAULT_VALUE_TYPE
        defaultCustPasswordCharShouldNotBeFound("valueType.doesNotContain=" + DEFAULT_VALUE_TYPE);

        // Get all the custPasswordCharList where valueType does not contain UPDATED_VALUE_TYPE
        defaultCustPasswordCharShouldBeFound("valueType.doesNotContain=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllCustPasswordCharsByCustomerIdIsEqualToSomething() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        // Get all the custPasswordCharList where customerId equals to DEFAULT_CUSTOMER_ID
        defaultCustPasswordCharShouldBeFound("customerId.equals=" + DEFAULT_CUSTOMER_ID);

        // Get all the custPasswordCharList where customerId equals to UPDATED_CUSTOMER_ID
        defaultCustPasswordCharShouldNotBeFound("customerId.equals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustPasswordCharsByCustomerIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        // Get all the custPasswordCharList where customerId not equals to DEFAULT_CUSTOMER_ID
        defaultCustPasswordCharShouldNotBeFound("customerId.notEquals=" + DEFAULT_CUSTOMER_ID);

        // Get all the custPasswordCharList where customerId not equals to UPDATED_CUSTOMER_ID
        defaultCustPasswordCharShouldBeFound("customerId.notEquals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustPasswordCharsByCustomerIdIsInShouldWork() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        // Get all the custPasswordCharList where customerId in DEFAULT_CUSTOMER_ID or UPDATED_CUSTOMER_ID
        defaultCustPasswordCharShouldBeFound("customerId.in=" + DEFAULT_CUSTOMER_ID + "," + UPDATED_CUSTOMER_ID);

        // Get all the custPasswordCharList where customerId equals to UPDATED_CUSTOMER_ID
        defaultCustPasswordCharShouldNotBeFound("customerId.in=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustPasswordCharsByCustomerIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        // Get all the custPasswordCharList where customerId is not null
        defaultCustPasswordCharShouldBeFound("customerId.specified=true");

        // Get all the custPasswordCharList where customerId is null
        defaultCustPasswordCharShouldNotBeFound("customerId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustPasswordCharsByCustomerIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        // Get all the custPasswordCharList where customerId is greater than or equal to DEFAULT_CUSTOMER_ID
        defaultCustPasswordCharShouldBeFound("customerId.greaterThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the custPasswordCharList where customerId is greater than or equal to UPDATED_CUSTOMER_ID
        defaultCustPasswordCharShouldNotBeFound("customerId.greaterThanOrEqual=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustPasswordCharsByCustomerIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        // Get all the custPasswordCharList where customerId is less than or equal to DEFAULT_CUSTOMER_ID
        defaultCustPasswordCharShouldBeFound("customerId.lessThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the custPasswordCharList where customerId is less than or equal to SMALLER_CUSTOMER_ID
        defaultCustPasswordCharShouldNotBeFound("customerId.lessThanOrEqual=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustPasswordCharsByCustomerIdIsLessThanSomething() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        // Get all the custPasswordCharList where customerId is less than DEFAULT_CUSTOMER_ID
        defaultCustPasswordCharShouldNotBeFound("customerId.lessThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the custPasswordCharList where customerId is less than UPDATED_CUSTOMER_ID
        defaultCustPasswordCharShouldBeFound("customerId.lessThan=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustPasswordCharsByCustomerIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        // Get all the custPasswordCharList where customerId is greater than DEFAULT_CUSTOMER_ID
        defaultCustPasswordCharShouldNotBeFound("customerId.greaterThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the custPasswordCharList where customerId is greater than SMALLER_CUSTOMER_ID
        defaultCustPasswordCharShouldBeFound("customerId.greaterThan=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustPasswordCharsByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);
        Customer customer = CustomerResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        custPasswordChar.setCustomer(customer);
        custPasswordCharRepository.saveAndFlush(custPasswordChar);
        Long customerId = customer.getId();

        // Get all the custPasswordCharList where customer equals to customerId
        defaultCustPasswordCharShouldBeFound("customerId.equals=" + customerId);

        // Get all the custPasswordCharList where customer equals to (customerId + 1)
        defaultCustPasswordCharShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustPasswordCharShouldBeFound(String filter) throws Exception {
        restCustPasswordCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custPasswordChar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())));

        // Check, that the count call also returns 1
        restCustPasswordCharMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustPasswordCharShouldNotBeFound(String filter) throws Exception {
        restCustPasswordCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustPasswordCharMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustPasswordChar() throws Exception {
        // Get the custPasswordChar
        restCustPasswordCharMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustPasswordChar() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        int databaseSizeBeforeUpdate = custPasswordCharRepository.findAll().size();

        // Update the custPasswordChar
        CustPasswordChar updatedCustPasswordChar = custPasswordCharRepository.findById(custPasswordChar.getId()).get();
        // Disconnect from session so that the updates on updatedCustPasswordChar are not directly saved in db
        em.detach(updatedCustPasswordChar);
        updatedCustPasswordChar.name(UPDATED_NAME).value(UPDATED_VALUE).valueType(UPDATED_VALUE_TYPE).customerId(UPDATED_CUSTOMER_ID);
        CustPasswordCharDTO custPasswordCharDTO = custPasswordCharMapper.toDto(updatedCustPasswordChar);

        restCustPasswordCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custPasswordCharDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custPasswordCharDTO))
            )
            .andExpect(status().isOk());

        // Validate the CustPasswordChar in the database
        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeUpdate);
        CustPasswordChar testCustPasswordChar = custPasswordCharList.get(custPasswordCharList.size() - 1);
        assertThat(testCustPasswordChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustPasswordChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustPasswordChar.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testCustPasswordChar.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustPasswordChar() throws Exception {
        int databaseSizeBeforeUpdate = custPasswordCharRepository.findAll().size();
        custPasswordChar.setId(count.incrementAndGet());

        // Create the CustPasswordChar
        CustPasswordCharDTO custPasswordCharDTO = custPasswordCharMapper.toDto(custPasswordChar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustPasswordCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custPasswordCharDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custPasswordCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustPasswordChar in the database
        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustPasswordChar() throws Exception {
        int databaseSizeBeforeUpdate = custPasswordCharRepository.findAll().size();
        custPasswordChar.setId(count.incrementAndGet());

        // Create the CustPasswordChar
        CustPasswordCharDTO custPasswordCharDTO = custPasswordCharMapper.toDto(custPasswordChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustPasswordCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custPasswordCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustPasswordChar in the database
        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustPasswordChar() throws Exception {
        int databaseSizeBeforeUpdate = custPasswordCharRepository.findAll().size();
        custPasswordChar.setId(count.incrementAndGet());

        // Create the CustPasswordChar
        CustPasswordCharDTO custPasswordCharDTO = custPasswordCharMapper.toDto(custPasswordChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustPasswordCharMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custPasswordCharDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustPasswordChar in the database
        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustPasswordCharWithPatch() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        int databaseSizeBeforeUpdate = custPasswordCharRepository.findAll().size();

        // Update the custPasswordChar using partial update
        CustPasswordChar partialUpdatedCustPasswordChar = new CustPasswordChar();
        partialUpdatedCustPasswordChar.setId(custPasswordChar.getId());

        partialUpdatedCustPasswordChar.name(UPDATED_NAME).value(UPDATED_VALUE).valueType(UPDATED_VALUE_TYPE);

        restCustPasswordCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustPasswordChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustPasswordChar))
            )
            .andExpect(status().isOk());

        // Validate the CustPasswordChar in the database
        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeUpdate);
        CustPasswordChar testCustPasswordChar = custPasswordCharList.get(custPasswordCharList.size() - 1);
        assertThat(testCustPasswordChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustPasswordChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustPasswordChar.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testCustPasswordChar.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustPasswordCharWithPatch() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        int databaseSizeBeforeUpdate = custPasswordCharRepository.findAll().size();

        // Update the custPasswordChar using partial update
        CustPasswordChar partialUpdatedCustPasswordChar = new CustPasswordChar();
        partialUpdatedCustPasswordChar.setId(custPasswordChar.getId());

        partialUpdatedCustPasswordChar
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valueType(UPDATED_VALUE_TYPE)
            .customerId(UPDATED_CUSTOMER_ID);

        restCustPasswordCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustPasswordChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustPasswordChar))
            )
            .andExpect(status().isOk());

        // Validate the CustPasswordChar in the database
        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeUpdate);
        CustPasswordChar testCustPasswordChar = custPasswordCharList.get(custPasswordCharList.size() - 1);
        assertThat(testCustPasswordChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustPasswordChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustPasswordChar.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testCustPasswordChar.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustPasswordChar() throws Exception {
        int databaseSizeBeforeUpdate = custPasswordCharRepository.findAll().size();
        custPasswordChar.setId(count.incrementAndGet());

        // Create the CustPasswordChar
        CustPasswordCharDTO custPasswordCharDTO = custPasswordCharMapper.toDto(custPasswordChar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustPasswordCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custPasswordCharDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custPasswordCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustPasswordChar in the database
        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustPasswordChar() throws Exception {
        int databaseSizeBeforeUpdate = custPasswordCharRepository.findAll().size();
        custPasswordChar.setId(count.incrementAndGet());

        // Create the CustPasswordChar
        CustPasswordCharDTO custPasswordCharDTO = custPasswordCharMapper.toDto(custPasswordChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustPasswordCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custPasswordCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustPasswordChar in the database
        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustPasswordChar() throws Exception {
        int databaseSizeBeforeUpdate = custPasswordCharRepository.findAll().size();
        custPasswordChar.setId(count.incrementAndGet());

        // Create the CustPasswordChar
        CustPasswordCharDTO custPasswordCharDTO = custPasswordCharMapper.toDto(custPasswordChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustPasswordCharMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custPasswordCharDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustPasswordChar in the database
        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustPasswordChar() throws Exception {
        // Initialize the database
        custPasswordCharRepository.saveAndFlush(custPasswordChar);

        int databaseSizeBeforeDelete = custPasswordCharRepository.findAll().size();

        // Delete the custPasswordChar
        restCustPasswordCharMockMvc
            .perform(delete(ENTITY_API_URL_ID, custPasswordChar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustPasswordChar> custPasswordCharList = custPasswordCharRepository.findAll();
        assertThat(custPasswordCharList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
