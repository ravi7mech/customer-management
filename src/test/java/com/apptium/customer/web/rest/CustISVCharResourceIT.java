package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.CustISVChar;
import com.apptium.customer.domain.CustISVRef;
import com.apptium.customer.repository.CustISVCharRepository;
import com.apptium.customer.service.criteria.CustISVCharCriteria;
import com.apptium.customer.service.dto.CustISVCharDTO;
import com.apptium.customer.service.mapper.CustISVCharMapper;
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
 * Integration tests for the {@link CustISVCharResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustISVCharResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_VALUE = 1;
    private static final Integer UPDATED_VALUE = 2;
    private static final Integer SMALLER_VALUE = 1 - 1;

    private static final String DEFAULT_VALUE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CUST_ISV_ID = 1;
    private static final Integer UPDATED_CUST_ISV_ID = 2;
    private static final Integer SMALLER_CUST_ISV_ID = 1 - 1;

    private static final String ENTITY_API_URL = "/api/cust-isv-chars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustISVCharRepository custISVCharRepository;

    @Autowired
    private CustISVCharMapper custISVCharMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustISVCharMockMvc;

    private CustISVChar custISVChar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustISVChar createEntity(EntityManager em) {
        CustISVChar custISVChar = new CustISVChar()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .valueType(DEFAULT_VALUE_TYPE)
            .custIsvId(DEFAULT_CUST_ISV_ID);
        return custISVChar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustISVChar createUpdatedEntity(EntityManager em) {
        CustISVChar custISVChar = new CustISVChar()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valueType(UPDATED_VALUE_TYPE)
            .custIsvId(UPDATED_CUST_ISV_ID);
        return custISVChar;
    }

    @BeforeEach
    public void initTest() {
        custISVChar = createEntity(em);
    }

    @Test
    @Transactional
    void createCustISVChar() throws Exception {
        int databaseSizeBeforeCreate = custISVCharRepository.findAll().size();
        // Create the CustISVChar
        CustISVCharDTO custISVCharDTO = custISVCharMapper.toDto(custISVChar);
        restCustISVCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custISVCharDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CustISVChar in the database
        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeCreate + 1);
        CustISVChar testCustISVChar = custISVCharList.get(custISVCharList.size() - 1);
        assertThat(testCustISVChar.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustISVChar.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCustISVChar.getValueType()).isEqualTo(DEFAULT_VALUE_TYPE);
        assertThat(testCustISVChar.getCustIsvId()).isEqualTo(DEFAULT_CUST_ISV_ID);
    }

    @Test
    @Transactional
    void createCustISVCharWithExistingId() throws Exception {
        // Create the CustISVChar with an existing ID
        custISVChar.setId(1L);
        CustISVCharDTO custISVCharDTO = custISVCharMapper.toDto(custISVChar);

        int databaseSizeBeforeCreate = custISVCharRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustISVCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custISVCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustISVChar in the database
        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = custISVCharRepository.findAll().size();
        // set the field null
        custISVChar.setName(null);

        // Create the CustISVChar, which fails.
        CustISVCharDTO custISVCharDTO = custISVCharMapper.toDto(custISVChar);

        restCustISVCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custISVCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = custISVCharRepository.findAll().size();
        // set the field null
        custISVChar.setValue(null);

        // Create the CustISVChar, which fails.
        CustISVCharDTO custISVCharDTO = custISVCharMapper.toDto(custISVChar);

        restCustISVCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custISVCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = custISVCharRepository.findAll().size();
        // set the field null
        custISVChar.setValueType(null);

        // Create the CustISVChar, which fails.
        CustISVCharDTO custISVCharDTO = custISVCharMapper.toDto(custISVChar);

        restCustISVCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custISVCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustIsvIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custISVCharRepository.findAll().size();
        // set the field null
        custISVChar.setCustIsvId(null);

        // Create the CustISVChar, which fails.
        CustISVCharDTO custISVCharDTO = custISVCharMapper.toDto(custISVChar);

        restCustISVCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custISVCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustISVChars() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get all the custISVCharList
        restCustISVCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custISVChar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE)))
            .andExpect(jsonPath("$.[*].custIsvId").value(hasItem(DEFAULT_CUST_ISV_ID)));
    }

    @Test
    @Transactional
    void getCustISVChar() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get the custISVChar
        restCustISVCharMockMvc
            .perform(get(ENTITY_API_URL_ID, custISVChar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custISVChar.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.valueType").value(DEFAULT_VALUE_TYPE))
            .andExpect(jsonPath("$.custIsvId").value(DEFAULT_CUST_ISV_ID));
    }

    @Test
    @Transactional
    void getCustISVCharsByIdFiltering() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        Long id = custISVChar.getId();

        defaultCustISVCharShouldBeFound("id.equals=" + id);
        defaultCustISVCharShouldNotBeFound("id.notEquals=" + id);

        defaultCustISVCharShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustISVCharShouldNotBeFound("id.greaterThan=" + id);

        defaultCustISVCharShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustISVCharShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustISVCharsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get all the custISVCharList where name equals to DEFAULT_NAME
        defaultCustISVCharShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the custISVCharList where name equals to UPDATED_NAME
        defaultCustISVCharShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustISVCharsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get all the custISVCharList where name not equals to DEFAULT_NAME
        defaultCustISVCharShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the custISVCharList where name not equals to UPDATED_NAME
        defaultCustISVCharShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustISVCharsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get all the custISVCharList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCustISVCharShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the custISVCharList where name equals to UPDATED_NAME
        defaultCustISVCharShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustISVCharsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get all the custISVCharList where name is not null
        defaultCustISVCharShouldBeFound("name.specified=true");

        // Get all the custISVCharList where name is null
        defaultCustISVCharShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCustISVCharsByNameContainsSomething() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get all the custISVCharList where name contains DEFAULT_NAME
        defaultCustISVCharShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the custISVCharList where name contains UPDATED_NAME
        defaultCustISVCharShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustISVCharsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get all the custISVCharList where name does not contain DEFAULT_NAME
        defaultCustISVCharShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the custISVCharList where name does not contain UPDATED_NAME
        defaultCustISVCharShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustISVCharsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get all the custISVCharList where value equals to DEFAULT_VALUE
        defaultCustISVCharShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the custISVCharList where value equals to UPDATED_VALUE
        defaultCustISVCharShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustISVCharsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get all the custISVCharList where value not equals to DEFAULT_VALUE
        defaultCustISVCharShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the custISVCharList where value not equals to UPDATED_VALUE
        defaultCustISVCharShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustISVCharsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get all the custISVCharList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultCustISVCharShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the custISVCharList where value equals to UPDATED_VALUE
        defaultCustISVCharShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustISVCharsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get all the custISVCharList where value is not null
        defaultCustISVCharShouldBeFound("value.specified=true");

        // Get all the custISVCharList where value is null
        defaultCustISVCharShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    void getAllCustISVCharsByValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get all the custISVCharList where value is greater than or equal to DEFAULT_VALUE
        defaultCustISVCharShouldBeFound("value.greaterThanOrEqual=" + DEFAULT_VALUE);

        // Get all the custISVCharList where value is greater than or equal to UPDATED_VALUE
        defaultCustISVCharShouldNotBeFound("value.greaterThanOrEqual=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustISVCharsByValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get all the custISVCharList where value is less than or equal to DEFAULT_VALUE
        defaultCustISVCharShouldBeFound("value.lessThanOrEqual=" + DEFAULT_VALUE);

        // Get all the custISVCharList where value is less than or equal to SMALLER_VALUE
        defaultCustISVCharShouldNotBeFound("value.lessThanOrEqual=" + SMALLER_VALUE);
    }

    @Test
    @Transactional
    void getAllCustISVCharsByValueIsLessThanSomething() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get all the custISVCharList where value is less than DEFAULT_VALUE
        defaultCustISVCharShouldNotBeFound("value.lessThan=" + DEFAULT_VALUE);

        // Get all the custISVCharList where value is less than UPDATED_VALUE
        defaultCustISVCharShouldBeFound("value.lessThan=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllCustISVCharsByValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get all the custISVCharList where value is greater than DEFAULT_VALUE
        defaultCustISVCharShouldNotBeFound("value.greaterThan=" + DEFAULT_VALUE);

        // Get all the custISVCharList where value is greater than SMALLER_VALUE
        defaultCustISVCharShouldBeFound("value.greaterThan=" + SMALLER_VALUE);
    }

    @Test
    @Transactional
    void getAllCustISVCharsByValueTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get all the custISVCharList where valueType equals to DEFAULT_VALUE_TYPE
        defaultCustISVCharShouldBeFound("valueType.equals=" + DEFAULT_VALUE_TYPE);

        // Get all the custISVCharList where valueType equals to UPDATED_VALUE_TYPE
        defaultCustISVCharShouldNotBeFound("valueType.equals=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllCustISVCharsByValueTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get all the custISVCharList where valueType not equals to DEFAULT_VALUE_TYPE
        defaultCustISVCharShouldNotBeFound("valueType.notEquals=" + DEFAULT_VALUE_TYPE);

        // Get all the custISVCharList where valueType not equals to UPDATED_VALUE_TYPE
        defaultCustISVCharShouldBeFound("valueType.notEquals=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllCustISVCharsByValueTypeIsInShouldWork() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get all the custISVCharList where valueType in DEFAULT_VALUE_TYPE or UPDATED_VALUE_TYPE
        defaultCustISVCharShouldBeFound("valueType.in=" + DEFAULT_VALUE_TYPE + "," + UPDATED_VALUE_TYPE);

        // Get all the custISVCharList where valueType equals to UPDATED_VALUE_TYPE
        defaultCustISVCharShouldNotBeFound("valueType.in=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllCustISVCharsByValueTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get all the custISVCharList where valueType is not null
        defaultCustISVCharShouldBeFound("valueType.specified=true");

        // Get all the custISVCharList where valueType is null
        defaultCustISVCharShouldNotBeFound("valueType.specified=false");
    }

    @Test
    @Transactional
    void getAllCustISVCharsByValueTypeContainsSomething() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get all the custISVCharList where valueType contains DEFAULT_VALUE_TYPE
        defaultCustISVCharShouldBeFound("valueType.contains=" + DEFAULT_VALUE_TYPE);

        // Get all the custISVCharList where valueType contains UPDATED_VALUE_TYPE
        defaultCustISVCharShouldNotBeFound("valueType.contains=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllCustISVCharsByValueTypeNotContainsSomething() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get all the custISVCharList where valueType does not contain DEFAULT_VALUE_TYPE
        defaultCustISVCharShouldNotBeFound("valueType.doesNotContain=" + DEFAULT_VALUE_TYPE);

        // Get all the custISVCharList where valueType does not contain UPDATED_VALUE_TYPE
        defaultCustISVCharShouldBeFound("valueType.doesNotContain=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllCustISVCharsByCustIsvIdIsEqualToSomething() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get all the custISVCharList where custIsvId equals to DEFAULT_CUST_ISV_ID
        defaultCustISVCharShouldBeFound("custIsvId.equals=" + DEFAULT_CUST_ISV_ID);

        // Get all the custISVCharList where custIsvId equals to UPDATED_CUST_ISV_ID
        defaultCustISVCharShouldNotBeFound("custIsvId.equals=" + UPDATED_CUST_ISV_ID);
    }

    @Test
    @Transactional
    void getAllCustISVCharsByCustIsvIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get all the custISVCharList where custIsvId not equals to DEFAULT_CUST_ISV_ID
        defaultCustISVCharShouldNotBeFound("custIsvId.notEquals=" + DEFAULT_CUST_ISV_ID);

        // Get all the custISVCharList where custIsvId not equals to UPDATED_CUST_ISV_ID
        defaultCustISVCharShouldBeFound("custIsvId.notEquals=" + UPDATED_CUST_ISV_ID);
    }

    @Test
    @Transactional
    void getAllCustISVCharsByCustIsvIdIsInShouldWork() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get all the custISVCharList where custIsvId in DEFAULT_CUST_ISV_ID or UPDATED_CUST_ISV_ID
        defaultCustISVCharShouldBeFound("custIsvId.in=" + DEFAULT_CUST_ISV_ID + "," + UPDATED_CUST_ISV_ID);

        // Get all the custISVCharList where custIsvId equals to UPDATED_CUST_ISV_ID
        defaultCustISVCharShouldNotBeFound("custIsvId.in=" + UPDATED_CUST_ISV_ID);
    }

    @Test
    @Transactional
    void getAllCustISVCharsByCustIsvIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get all the custISVCharList where custIsvId is not null
        defaultCustISVCharShouldBeFound("custIsvId.specified=true");

        // Get all the custISVCharList where custIsvId is null
        defaultCustISVCharShouldNotBeFound("custIsvId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustISVCharsByCustIsvIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get all the custISVCharList where custIsvId is greater than or equal to DEFAULT_CUST_ISV_ID
        defaultCustISVCharShouldBeFound("custIsvId.greaterThanOrEqual=" + DEFAULT_CUST_ISV_ID);

        // Get all the custISVCharList where custIsvId is greater than or equal to UPDATED_CUST_ISV_ID
        defaultCustISVCharShouldNotBeFound("custIsvId.greaterThanOrEqual=" + UPDATED_CUST_ISV_ID);
    }

    @Test
    @Transactional
    void getAllCustISVCharsByCustIsvIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get all the custISVCharList where custIsvId is less than or equal to DEFAULT_CUST_ISV_ID
        defaultCustISVCharShouldBeFound("custIsvId.lessThanOrEqual=" + DEFAULT_CUST_ISV_ID);

        // Get all the custISVCharList where custIsvId is less than or equal to SMALLER_CUST_ISV_ID
        defaultCustISVCharShouldNotBeFound("custIsvId.lessThanOrEqual=" + SMALLER_CUST_ISV_ID);
    }

    @Test
    @Transactional
    void getAllCustISVCharsByCustIsvIdIsLessThanSomething() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get all the custISVCharList where custIsvId is less than DEFAULT_CUST_ISV_ID
        defaultCustISVCharShouldNotBeFound("custIsvId.lessThan=" + DEFAULT_CUST_ISV_ID);

        // Get all the custISVCharList where custIsvId is less than UPDATED_CUST_ISV_ID
        defaultCustISVCharShouldBeFound("custIsvId.lessThan=" + UPDATED_CUST_ISV_ID);
    }

    @Test
    @Transactional
    void getAllCustISVCharsByCustIsvIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        // Get all the custISVCharList where custIsvId is greater than DEFAULT_CUST_ISV_ID
        defaultCustISVCharShouldNotBeFound("custIsvId.greaterThan=" + DEFAULT_CUST_ISV_ID);

        // Get all the custISVCharList where custIsvId is greater than SMALLER_CUST_ISV_ID
        defaultCustISVCharShouldBeFound("custIsvId.greaterThan=" + SMALLER_CUST_ISV_ID);
    }

    @Test
    @Transactional
    void getAllCustISVCharsByCustISVRefIsEqualToSomething() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);
        CustISVRef custISVRef = CustISVRefResourceIT.createEntity(em);
        em.persist(custISVRef);
        em.flush();
        custISVChar.setCustISVRef(custISVRef);
        custISVCharRepository.saveAndFlush(custISVChar);
        Long custISVRefId = custISVRef.getId();

        // Get all the custISVCharList where custISVRef equals to custISVRefId
        defaultCustISVCharShouldBeFound("custISVRefId.equals=" + custISVRefId);

        // Get all the custISVCharList where custISVRef equals to (custISVRefId + 1)
        defaultCustISVCharShouldNotBeFound("custISVRefId.equals=" + (custISVRefId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustISVCharShouldBeFound(String filter) throws Exception {
        restCustISVCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custISVChar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE)))
            .andExpect(jsonPath("$.[*].custIsvId").value(hasItem(DEFAULT_CUST_ISV_ID)));

        // Check, that the count call also returns 1
        restCustISVCharMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustISVCharShouldNotBeFound(String filter) throws Exception {
        restCustISVCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustISVCharMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustISVChar() throws Exception {
        // Get the custISVChar
        restCustISVCharMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustISVChar() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        int databaseSizeBeforeUpdate = custISVCharRepository.findAll().size();

        // Update the custISVChar
        CustISVChar updatedCustISVChar = custISVCharRepository.findById(custISVChar.getId()).get();
        // Disconnect from session so that the updates on updatedCustISVChar are not directly saved in db
        em.detach(updatedCustISVChar);
        updatedCustISVChar.name(UPDATED_NAME).value(UPDATED_VALUE).valueType(UPDATED_VALUE_TYPE).custIsvId(UPDATED_CUST_ISV_ID);
        CustISVCharDTO custISVCharDTO = custISVCharMapper.toDto(updatedCustISVChar);

        restCustISVCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custISVCharDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custISVCharDTO))
            )
            .andExpect(status().isOk());

        // Validate the CustISVChar in the database
        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeUpdate);
        CustISVChar testCustISVChar = custISVCharList.get(custISVCharList.size() - 1);
        assertThat(testCustISVChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustISVChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustISVChar.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testCustISVChar.getCustIsvId()).isEqualTo(UPDATED_CUST_ISV_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustISVChar() throws Exception {
        int databaseSizeBeforeUpdate = custISVCharRepository.findAll().size();
        custISVChar.setId(count.incrementAndGet());

        // Create the CustISVChar
        CustISVCharDTO custISVCharDTO = custISVCharMapper.toDto(custISVChar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustISVCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custISVCharDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custISVCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustISVChar in the database
        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustISVChar() throws Exception {
        int databaseSizeBeforeUpdate = custISVCharRepository.findAll().size();
        custISVChar.setId(count.incrementAndGet());

        // Create the CustISVChar
        CustISVCharDTO custISVCharDTO = custISVCharMapper.toDto(custISVChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustISVCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custISVCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustISVChar in the database
        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustISVChar() throws Exception {
        int databaseSizeBeforeUpdate = custISVCharRepository.findAll().size();
        custISVChar.setId(count.incrementAndGet());

        // Create the CustISVChar
        CustISVCharDTO custISVCharDTO = custISVCharMapper.toDto(custISVChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustISVCharMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custISVCharDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustISVChar in the database
        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustISVCharWithPatch() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        int databaseSizeBeforeUpdate = custISVCharRepository.findAll().size();

        // Update the custISVChar using partial update
        CustISVChar partialUpdatedCustISVChar = new CustISVChar();
        partialUpdatedCustISVChar.setId(custISVChar.getId());

        partialUpdatedCustISVChar.valueType(UPDATED_VALUE_TYPE).custIsvId(UPDATED_CUST_ISV_ID);

        restCustISVCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustISVChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustISVChar))
            )
            .andExpect(status().isOk());

        // Validate the CustISVChar in the database
        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeUpdate);
        CustISVChar testCustISVChar = custISVCharList.get(custISVCharList.size() - 1);
        assertThat(testCustISVChar.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustISVChar.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCustISVChar.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testCustISVChar.getCustIsvId()).isEqualTo(UPDATED_CUST_ISV_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustISVCharWithPatch() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        int databaseSizeBeforeUpdate = custISVCharRepository.findAll().size();

        // Update the custISVChar using partial update
        CustISVChar partialUpdatedCustISVChar = new CustISVChar();
        partialUpdatedCustISVChar.setId(custISVChar.getId());

        partialUpdatedCustISVChar.name(UPDATED_NAME).value(UPDATED_VALUE).valueType(UPDATED_VALUE_TYPE).custIsvId(UPDATED_CUST_ISV_ID);

        restCustISVCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustISVChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustISVChar))
            )
            .andExpect(status().isOk());

        // Validate the CustISVChar in the database
        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeUpdate);
        CustISVChar testCustISVChar = custISVCharList.get(custISVCharList.size() - 1);
        assertThat(testCustISVChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustISVChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCustISVChar.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testCustISVChar.getCustIsvId()).isEqualTo(UPDATED_CUST_ISV_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustISVChar() throws Exception {
        int databaseSizeBeforeUpdate = custISVCharRepository.findAll().size();
        custISVChar.setId(count.incrementAndGet());

        // Create the CustISVChar
        CustISVCharDTO custISVCharDTO = custISVCharMapper.toDto(custISVChar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustISVCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custISVCharDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custISVCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustISVChar in the database
        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustISVChar() throws Exception {
        int databaseSizeBeforeUpdate = custISVCharRepository.findAll().size();
        custISVChar.setId(count.incrementAndGet());

        // Create the CustISVChar
        CustISVCharDTO custISVCharDTO = custISVCharMapper.toDto(custISVChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustISVCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custISVCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustISVChar in the database
        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustISVChar() throws Exception {
        int databaseSizeBeforeUpdate = custISVCharRepository.findAll().size();
        custISVChar.setId(count.incrementAndGet());

        // Create the CustISVChar
        CustISVCharDTO custISVCharDTO = custISVCharMapper.toDto(custISVChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustISVCharMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(custISVCharDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustISVChar in the database
        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustISVChar() throws Exception {
        // Initialize the database
        custISVCharRepository.saveAndFlush(custISVChar);

        int databaseSizeBeforeDelete = custISVCharRepository.findAll().size();

        // Delete the custISVChar
        restCustISVCharMockMvc
            .perform(delete(ENTITY_API_URL_ID, custISVChar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustISVChar> custISVCharList = custISVCharRepository.findAll();
        assertThat(custISVCharList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
