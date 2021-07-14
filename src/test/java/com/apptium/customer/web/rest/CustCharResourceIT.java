package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.CustChar;
import com.apptium.customer.domain.Customer;
import com.apptium.customer.repository.CustCharRepository;
import com.apptium.customer.service.criteria.CustCharCriteria;
import com.apptium.customer.service.dto.CustCharDTO;
import com.apptium.customer.service.mapper.CustCharMapper;
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
 * Integration tests for the {@link CustCharResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustCharResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;
    private static final Long SMALLER_CUSTOMER_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/cust-chars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustCharRepository custCharRepository;

    @Autowired
    private CustCharMapper custCharMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustCharMockMvc;

    private CustChar custChar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustChar createEntity(EntityManager em) {
        CustChar custChar = new CustChar()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .valueType(DEFAULT_VALUE_TYPE)
            .customerId(DEFAULT_CUSTOMER_ID);
        return custChar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustChar createUpdatedEntity(EntityManager em) {
        CustChar custChar = new CustChar()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valueType(UPDATED_VALUE_TYPE)
            .customerId(UPDATED_CUSTOMER_ID);
        return custChar;
    }

    @BeforeEach
    public void initTest() {
        custChar = createEntity(em);
    }

    @Test
    @Transactional
    void createCustChar() throws Exception {
        int databaseSizeBeforeCreate = custCharRepository.findAll().size();
        // Create the CustChar
        CustCharDTO custCharDTO = custCharMapper.toDto(custChar);
        restCustCharMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custCharDTO)))
            .andExpect(status().isCreated());

        // Validate the CustChar in the database
        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeCreate + 1);
        CustChar testCustChar = custCharList.get(custCharList.size() - 1);
        assertThat(testCustChar.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustChar.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCustChar.getValueType()).isEqualTo(DEFAULT_VALUE_TYPE);
        assertThat(testCustChar.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void createCustCharWithExistingId() throws Exception {
        // Create the CustChar with an existing ID
        custChar.setId(1L);
        CustCharDTO custCharDTO = custCharMapper.toDto(custChar);

        int databaseSizeBeforeCreate = custCharRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustCharMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custCharDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustChar in the database
        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = custCharRepository.findAll().size();
        // set the field null
        custChar.setName(null);

        // Create the CustChar, which fails.
        CustCharDTO custCharDTO = custCharMapper.toDto(custChar);

        restCustCharMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custCharDTO)))
            .andExpect(status().isBadRequest());

        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = custCharRepository.findAll().size();
        // set the field null
        custChar.setValue(null);

        // Create the CustChar, which fails.
        CustCharDTO custCharDTO = custCharMapper.toDto(custChar);

        restCustCharMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custCharDTO)))
            .andExpect(status().isBadRequest());

        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = custCharRepository.findAll().size();
        // set the field null
        custChar.setValueType(null);

        // Create the CustChar, which fails.
        CustCharDTO custCharDTO = custCharMapper.toDto(custChar);

        restCustCharMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custCharDTO)))
            .andExpect(status().isBadRequest());

        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custCharRepository.findAll().size();
        // set the field null
        custChar.setCustomerId(null);

        // Create the CustChar, which fails.
        CustCharDTO custCharDTO = custCharMapper.toDto(custChar);

        restCustCharMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custCharDTO)))
            .andExpect(status().isBadRequest());

        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustChars() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        // Get all the custCharList
        restCustCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custChar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())));
    }

    @Test
    @Transactional
    void getCustChar() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        // Get the custChar
        restCustCharMockMvc
            .perform(get(ENTITY_API_URL_ID, custChar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custChar.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.valueType").value(DEFAULT_VALUE_TYPE))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()));
    }

    @Test
    @Transactional
    void getCustCharsByIdFiltering() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        Long id = custChar.getId();

        defaultCustCharShouldBeFound("id.equals=" + id);
        defaultCustCharShouldNotBeFound("id.notEquals=" + id);

        defaultCustCharShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustCharShouldNotBeFound("id.greaterThan=" + id);

        defaultCustCharShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustCharShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustCharsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        // Get all the custCharList where name equals to DEFAULT_NAME
        defaultCustCharShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the custCharList where name equals to UPDATED_NAME
        defaultCustCharShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustCharsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        // Get all the custCharList where name not equals to DEFAULT_NAME
        defaultCustCharShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the custCharList where name not equals to UPDATED_NAME
        defaultCustCharShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustCharsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        // Get all the custCharList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCustCharShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the custCharList where name equals to UPDATED_NAME
        defaultCustCharShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustCharsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        // Get all the custCharList where name is not null
        defaultCustCharShouldBeFound("name.specified=true");

        // Get all the custCharList where name is null
        defaultCustCharShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCustCharsByNameContainsSomething() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        // Get all the custCharList where name contains DEFAULT_NAME
        defaultCustCharShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the custCharList where name contains UPDATED_NAME
        defaultCustCharShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustCharsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        // Get all the custCharList where name does not contain DEFAULT_NAME
        defaultCustCharShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the custCharList where name does not contain UPDATED_NAME
        defaultCustCharShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustCharsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        // Get all the custCharList where value equals to DEFAULT_VALUE
        defaultCustCharShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the custCharList where value equals to UPDATED_VALUE
        defaultCustCharShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustCharsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        // Get all the custCharList where value not equals to DEFAULT_VALUE
        defaultCustCharShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the custCharList where value not equals to UPDATED_VALUE
        defaultCustCharShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustCharsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        // Get all the custCharList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultCustCharShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the custCharList where value equals to UPDATED_VALUE
        defaultCustCharShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustCharsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        // Get all the custCharList where value is not null
        defaultCustCharShouldBeFound("value.specified=true");

        // Get all the custCharList where value is null
        defaultCustCharShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    void getAllCustCharsByValueContainsSomething() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        // Get all the custCharList where value contains DEFAULT_VALUE
        defaultCustCharShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the custCharList where value contains UPDATED_VALUE
        defaultCustCharShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustCharsByValueNotContainsSomething() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        // Get all the custCharList where value does not contain DEFAULT_VALUE
        defaultCustCharShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the custCharList where value does not contain UPDATED_VALUE
        defaultCustCharShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustCharsByValueTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        // Get all the custCharList where valueType equals to DEFAULT_VALUE_TYPE
        defaultCustCharShouldBeFound("valueType.equals=" + DEFAULT_VALUE_TYPE);

        // Get all the custCharList where valueType equals to UPDATED_VALUE_TYPE
        defaultCustCharShouldNotBeFound("valueType.equals=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllCustCharsByValueTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        // Get all the custCharList where valueType not equals to DEFAULT_VALUE_TYPE
        defaultCustCharShouldNotBeFound("valueType.notEquals=" + DEFAULT_VALUE_TYPE);

        // Get all the custCharList where valueType not equals to UPDATED_VALUE_TYPE
        defaultCustCharShouldBeFound("valueType.notEquals=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllCustCharsByValueTypeIsInShouldWork() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        // Get all the custCharList where valueType in DEFAULT_VALUE_TYPE or UPDATED_VALUE_TYPE
        defaultCustCharShouldBeFound("valueType.in=" + DEFAULT_VALUE_TYPE + "," + UPDATED_VALUE_TYPE);

        // Get all the custCharList where valueType equals to UPDATED_VALUE_TYPE
        defaultCustCharShouldNotBeFound("valueType.in=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllCustCharsByValueTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        // Get all the custCharList where valueType is not null
        defaultCustCharShouldBeFound("valueType.specified=true");

        // Get all the custCharList where valueType is null
        defaultCustCharShouldNotBeFound("valueType.specified=false");
    }

    @Test
    @Transactional
    void getAllCustCharsByValueTypeContainsSomething() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        // Get all the custCharList where valueType contains DEFAULT_VALUE_TYPE
        defaultCustCharShouldBeFound("valueType.contains=" + DEFAULT_VALUE_TYPE);

        // Get all the custCharList where valueType contains UPDATED_VALUE_TYPE
        defaultCustCharShouldNotBeFound("valueType.contains=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllCustCharsByValueTypeNotContainsSomething() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        // Get all the custCharList where valueType does not contain DEFAULT_VALUE_TYPE
        defaultCustCharShouldNotBeFound("valueType.doesNotContain=" + DEFAULT_VALUE_TYPE);

        // Get all the custCharList where valueType does not contain UPDATED_VALUE_TYPE
        defaultCustCharShouldBeFound("valueType.doesNotContain=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllCustCharsByCustomerIdIsEqualToSomething() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        // Get all the custCharList where customerId equals to DEFAULT_CUSTOMER_ID
        defaultCustCharShouldBeFound("customerId.equals=" + DEFAULT_CUSTOMER_ID);

        // Get all the custCharList where customerId equals to UPDATED_CUSTOMER_ID
        defaultCustCharShouldNotBeFound("customerId.equals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustCharsByCustomerIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        // Get all the custCharList where customerId not equals to DEFAULT_CUSTOMER_ID
        defaultCustCharShouldNotBeFound("customerId.notEquals=" + DEFAULT_CUSTOMER_ID);

        // Get all the custCharList where customerId not equals to UPDATED_CUSTOMER_ID
        defaultCustCharShouldBeFound("customerId.notEquals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustCharsByCustomerIdIsInShouldWork() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        // Get all the custCharList where customerId in DEFAULT_CUSTOMER_ID or UPDATED_CUSTOMER_ID
        defaultCustCharShouldBeFound("customerId.in=" + DEFAULT_CUSTOMER_ID + "," + UPDATED_CUSTOMER_ID);

        // Get all the custCharList where customerId equals to UPDATED_CUSTOMER_ID
        defaultCustCharShouldNotBeFound("customerId.in=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustCharsByCustomerIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        // Get all the custCharList where customerId is not null
        defaultCustCharShouldBeFound("customerId.specified=true");

        // Get all the custCharList where customerId is null
        defaultCustCharShouldNotBeFound("customerId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustCharsByCustomerIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        // Get all the custCharList where customerId is greater than or equal to DEFAULT_CUSTOMER_ID
        defaultCustCharShouldBeFound("customerId.greaterThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the custCharList where customerId is greater than or equal to UPDATED_CUSTOMER_ID
        defaultCustCharShouldNotBeFound("customerId.greaterThanOrEqual=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustCharsByCustomerIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        // Get all the custCharList where customerId is less than or equal to DEFAULT_CUSTOMER_ID
        defaultCustCharShouldBeFound("customerId.lessThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the custCharList where customerId is less than or equal to SMALLER_CUSTOMER_ID
        defaultCustCharShouldNotBeFound("customerId.lessThanOrEqual=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustCharsByCustomerIdIsLessThanSomething() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        // Get all the custCharList where customerId is less than DEFAULT_CUSTOMER_ID
        defaultCustCharShouldNotBeFound("customerId.lessThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the custCharList where customerId is less than UPDATED_CUSTOMER_ID
        defaultCustCharShouldBeFound("customerId.lessThan=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustCharsByCustomerIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        // Get all the custCharList where customerId is greater than DEFAULT_CUSTOMER_ID
        defaultCustCharShouldNotBeFound("customerId.greaterThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the custCharList where customerId is greater than SMALLER_CUSTOMER_ID
        defaultCustCharShouldBeFound("customerId.greaterThan=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustCharsByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);
        Customer customer = CustomerResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        custChar.setCustomer(customer);
        custCharRepository.saveAndFlush(custChar);
        Long customerId = customer.getId();

        // Get all the custCharList where customer equals to customerId
        defaultCustCharShouldBeFound("customerId.equals=" + customerId);

        // Get all the custCharList where customer equals to (customerId + 1)
        defaultCustCharShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustCharShouldBeFound(String filter) throws Exception {
        restCustCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custChar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())));

        // Check, that the count call also returns 1
        restCustCharMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustCharShouldNotBeFound(String filter) throws Exception {
        restCustCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustCharMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustChar() throws Exception {
        // Get the custChar
        restCustCharMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustChar() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        int databaseSizeBeforeUpdate = custCharRepository.findAll().size();

        // Update the custChar
        CustChar updatedCustChar = custCharRepository.findById(custChar.getId()).get();
        // Disconnect from session so that the updates on updatedCustChar are not directly saved in db
        em.detach(updatedCustChar);
        updatedCustChar.name(UPDATED_NAME).value(UPDATED_VALUE).valueType(UPDATED_VALUE_TYPE).customerId(UPDATED_CUSTOMER_ID);
        CustCharDTO custCharDTO = custCharMapper.toDto(updatedCustChar);

        restCustCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custCharDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCharDTO))
            )
            .andExpect(status().isOk());

        // Validate the CustChar in the database
        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeUpdate);
        CustChar testCustChar = custCharList.get(custCharList.size() - 1);
        assertThat(testCustChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustChar.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testCustChar.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustChar() throws Exception {
        int databaseSizeBeforeUpdate = custCharRepository.findAll().size();
        custChar.setId(count.incrementAndGet());

        // Create the CustChar
        CustCharDTO custCharDTO = custCharMapper.toDto(custChar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custCharDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustChar in the database
        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustChar() throws Exception {
        int databaseSizeBeforeUpdate = custCharRepository.findAll().size();
        custChar.setId(count.incrementAndGet());

        // Create the CustChar
        CustCharDTO custCharDTO = custCharMapper.toDto(custChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustChar in the database
        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustChar() throws Exception {
        int databaseSizeBeforeUpdate = custCharRepository.findAll().size();
        custChar.setId(count.incrementAndGet());

        // Create the CustChar
        CustCharDTO custCharDTO = custCharMapper.toDto(custChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustCharMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custCharDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustChar in the database
        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustCharWithPatch() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        int databaseSizeBeforeUpdate = custCharRepository.findAll().size();

        // Update the custChar using partial update
        CustChar partialUpdatedCustChar = new CustChar();
        partialUpdatedCustChar.setId(custChar.getId());

        partialUpdatedCustChar.name(UPDATED_NAME).value(UPDATED_VALUE);

        restCustCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustChar))
            )
            .andExpect(status().isOk());

        // Validate the CustChar in the database
        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeUpdate);
        CustChar testCustChar = custCharList.get(custCharList.size() - 1);
        assertThat(testCustChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustChar.getValueType()).isEqualTo(DEFAULT_VALUE_TYPE);
        assertThat(testCustChar.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustCharWithPatch() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        int databaseSizeBeforeUpdate = custCharRepository.findAll().size();

        // Update the custChar using partial update
        CustChar partialUpdatedCustChar = new CustChar();
        partialUpdatedCustChar.setId(custChar.getId());

        partialUpdatedCustChar.name(UPDATED_NAME).value(UPDATED_VALUE).valueType(UPDATED_VALUE_TYPE).customerId(UPDATED_CUSTOMER_ID);

        restCustCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustChar))
            )
            .andExpect(status().isOk());

        // Validate the CustChar in the database
        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeUpdate);
        CustChar testCustChar = custCharList.get(custCharList.size() - 1);
        assertThat(testCustChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustChar.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testCustChar.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustChar() throws Exception {
        int databaseSizeBeforeUpdate = custCharRepository.findAll().size();
        custChar.setId(count.incrementAndGet());

        // Create the CustChar
        CustCharDTO custCharDTO = custCharMapper.toDto(custChar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custCharDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustChar in the database
        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustChar() throws Exception {
        int databaseSizeBeforeUpdate = custCharRepository.findAll().size();
        custChar.setId(count.incrementAndGet());

        // Create the CustChar
        CustCharDTO custCharDTO = custCharMapper.toDto(custChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustChar in the database
        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustChar() throws Exception {
        int databaseSizeBeforeUpdate = custCharRepository.findAll().size();
        custChar.setId(count.incrementAndGet());

        // Create the CustChar
        CustCharDTO custCharDTO = custCharMapper.toDto(custChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustCharMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(custCharDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustChar in the database
        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustChar() throws Exception {
        // Initialize the database
        custCharRepository.saveAndFlush(custChar);

        int databaseSizeBeforeDelete = custCharRepository.findAll().size();

        // Delete the custChar
        restCustCharMockMvc
            .perform(delete(ENTITY_API_URL_ID, custChar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustChar> custCharList = custCharRepository.findAll();
        assertThat(custCharList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
