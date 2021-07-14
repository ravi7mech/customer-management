package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.CustRelParty;
import com.apptium.customer.domain.Customer;
import com.apptium.customer.domain.Department;
import com.apptium.customer.domain.Individual;
import com.apptium.customer.domain.RoleTypeRef;
import com.apptium.customer.repository.CustRelPartyRepository;
import com.apptium.customer.service.criteria.CustRelPartyCriteria;
import com.apptium.customer.service.dto.CustRelPartyDTO;
import com.apptium.customer.service.mapper.CustRelPartyMapper;
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
 * Integration tests for the {@link CustRelPartyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustRelPartyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_ROLE_ID = 1L;
    private static final Long UPDATED_ROLE_ID = 2L;
    private static final Long SMALLER_ROLE_ID = 1L - 1L;

    private static final Long DEFAULT_INDIVIDUAL_ID = 1L;
    private static final Long UPDATED_INDIVIDUAL_ID = 2L;
    private static final Long SMALLER_INDIVIDUAL_ID = 1L - 1L;

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;
    private static final Long SMALLER_CUSTOMER_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/cust-rel-parties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustRelPartyRepository custRelPartyRepository;

    @Autowired
    private CustRelPartyMapper custRelPartyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustRelPartyMockMvc;

    private CustRelParty custRelParty;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustRelParty createEntity(EntityManager em) {
        CustRelParty custRelParty = new CustRelParty()
            .name(DEFAULT_NAME)
            .roleId(DEFAULT_ROLE_ID)
            .individualId(DEFAULT_INDIVIDUAL_ID)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO)
            .customerId(DEFAULT_CUSTOMER_ID);
        return custRelParty;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustRelParty createUpdatedEntity(EntityManager em) {
        CustRelParty custRelParty = new CustRelParty()
            .name(UPDATED_NAME)
            .roleId(UPDATED_ROLE_ID)
            .individualId(UPDATED_INDIVIDUAL_ID)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .customerId(UPDATED_CUSTOMER_ID);
        return custRelParty;
    }

    @BeforeEach
    public void initTest() {
        custRelParty = createEntity(em);
    }

    @Test
    @Transactional
    void createCustRelParty() throws Exception {
        int databaseSizeBeforeCreate = custRelPartyRepository.findAll().size();
        // Create the CustRelParty
        CustRelPartyDTO custRelPartyDTO = custRelPartyMapper.toDto(custRelParty);
        restCustRelPartyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custRelPartyDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CustRelParty in the database
        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeCreate + 1);
        CustRelParty testCustRelParty = custRelPartyList.get(custRelPartyList.size() - 1);
        assertThat(testCustRelParty.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustRelParty.getRoleId()).isEqualTo(DEFAULT_ROLE_ID);
        assertThat(testCustRelParty.getIndividualId()).isEqualTo(DEFAULT_INDIVIDUAL_ID);
        assertThat(testCustRelParty.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testCustRelParty.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
        assertThat(testCustRelParty.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void createCustRelPartyWithExistingId() throws Exception {
        // Create the CustRelParty with an existing ID
        custRelParty.setId(1L);
        CustRelPartyDTO custRelPartyDTO = custRelPartyMapper.toDto(custRelParty);

        int databaseSizeBeforeCreate = custRelPartyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustRelPartyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custRelPartyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustRelParty in the database
        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = custRelPartyRepository.findAll().size();
        // set the field null
        custRelParty.setName(null);

        // Create the CustRelParty, which fails.
        CustRelPartyDTO custRelPartyDTO = custRelPartyMapper.toDto(custRelParty);

        restCustRelPartyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custRelPartyDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRoleIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custRelPartyRepository.findAll().size();
        // set the field null
        custRelParty.setRoleId(null);

        // Create the CustRelParty, which fails.
        CustRelPartyDTO custRelPartyDTO = custRelPartyMapper.toDto(custRelParty);

        restCustRelPartyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custRelPartyDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIndividualIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custRelPartyRepository.findAll().size();
        // set the field null
        custRelParty.setIndividualId(null);

        // Create the CustRelParty, which fails.
        CustRelPartyDTO custRelPartyDTO = custRelPartyMapper.toDto(custRelParty);

        restCustRelPartyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custRelPartyDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = custRelPartyRepository.findAll().size();
        // set the field null
        custRelParty.setValidFrom(null);

        // Create the CustRelParty, which fails.
        CustRelPartyDTO custRelPartyDTO = custRelPartyMapper.toDto(custRelParty);

        restCustRelPartyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custRelPartyDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = custRelPartyRepository.findAll().size();
        // set the field null
        custRelParty.setValidTo(null);

        // Create the CustRelParty, which fails.
        CustRelPartyDTO custRelPartyDTO = custRelPartyMapper.toDto(custRelParty);

        restCustRelPartyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custRelPartyDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custRelPartyRepository.findAll().size();
        // set the field null
        custRelParty.setCustomerId(null);

        // Create the CustRelParty, which fails.
        CustRelPartyDTO custRelPartyDTO = custRelPartyMapper.toDto(custRelParty);

        restCustRelPartyMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custRelPartyDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustRelParties() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList
        restCustRelPartyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custRelParty.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].roleId").value(hasItem(DEFAULT_ROLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].individualId").value(hasItem(DEFAULT_INDIVIDUAL_ID.intValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())));
    }

    @Test
    @Transactional
    void getCustRelParty() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get the custRelParty
        restCustRelPartyMockMvc
            .perform(get(ENTITY_API_URL_ID, custRelParty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custRelParty.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.roleId").value(DEFAULT_ROLE_ID.intValue()))
            .andExpect(jsonPath("$.individualId").value(DEFAULT_INDIVIDUAL_ID.intValue()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()));
    }

    @Test
    @Transactional
    void getCustRelPartiesByIdFiltering() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        Long id = custRelParty.getId();

        defaultCustRelPartyShouldBeFound("id.equals=" + id);
        defaultCustRelPartyShouldNotBeFound("id.notEquals=" + id);

        defaultCustRelPartyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustRelPartyShouldNotBeFound("id.greaterThan=" + id);

        defaultCustRelPartyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustRelPartyShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where name equals to DEFAULT_NAME
        defaultCustRelPartyShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the custRelPartyList where name equals to UPDATED_NAME
        defaultCustRelPartyShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where name not equals to DEFAULT_NAME
        defaultCustRelPartyShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the custRelPartyList where name not equals to UPDATED_NAME
        defaultCustRelPartyShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCustRelPartyShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the custRelPartyList where name equals to UPDATED_NAME
        defaultCustRelPartyShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where name is not null
        defaultCustRelPartyShouldBeFound("name.specified=true");

        // Get all the custRelPartyList where name is null
        defaultCustRelPartyShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByNameContainsSomething() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where name contains DEFAULT_NAME
        defaultCustRelPartyShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the custRelPartyList where name contains UPDATED_NAME
        defaultCustRelPartyShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where name does not contain DEFAULT_NAME
        defaultCustRelPartyShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the custRelPartyList where name does not contain UPDATED_NAME
        defaultCustRelPartyShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByRoleIdIsEqualToSomething() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where roleId equals to DEFAULT_ROLE_ID
        defaultCustRelPartyShouldBeFound("roleId.equals=" + DEFAULT_ROLE_ID);

        // Get all the custRelPartyList where roleId equals to UPDATED_ROLE_ID
        defaultCustRelPartyShouldNotBeFound("roleId.equals=" + UPDATED_ROLE_ID);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByRoleIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where roleId not equals to DEFAULT_ROLE_ID
        defaultCustRelPartyShouldNotBeFound("roleId.notEquals=" + DEFAULT_ROLE_ID);

        // Get all the custRelPartyList where roleId not equals to UPDATED_ROLE_ID
        defaultCustRelPartyShouldBeFound("roleId.notEquals=" + UPDATED_ROLE_ID);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByRoleIdIsInShouldWork() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where roleId in DEFAULT_ROLE_ID or UPDATED_ROLE_ID
        defaultCustRelPartyShouldBeFound("roleId.in=" + DEFAULT_ROLE_ID + "," + UPDATED_ROLE_ID);

        // Get all the custRelPartyList where roleId equals to UPDATED_ROLE_ID
        defaultCustRelPartyShouldNotBeFound("roleId.in=" + UPDATED_ROLE_ID);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByRoleIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where roleId is not null
        defaultCustRelPartyShouldBeFound("roleId.specified=true");

        // Get all the custRelPartyList where roleId is null
        defaultCustRelPartyShouldNotBeFound("roleId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByRoleIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where roleId is greater than or equal to DEFAULT_ROLE_ID
        defaultCustRelPartyShouldBeFound("roleId.greaterThanOrEqual=" + DEFAULT_ROLE_ID);

        // Get all the custRelPartyList where roleId is greater than or equal to UPDATED_ROLE_ID
        defaultCustRelPartyShouldNotBeFound("roleId.greaterThanOrEqual=" + UPDATED_ROLE_ID);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByRoleIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where roleId is less than or equal to DEFAULT_ROLE_ID
        defaultCustRelPartyShouldBeFound("roleId.lessThanOrEqual=" + DEFAULT_ROLE_ID);

        // Get all the custRelPartyList where roleId is less than or equal to SMALLER_ROLE_ID
        defaultCustRelPartyShouldNotBeFound("roleId.lessThanOrEqual=" + SMALLER_ROLE_ID);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByRoleIdIsLessThanSomething() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where roleId is less than DEFAULT_ROLE_ID
        defaultCustRelPartyShouldNotBeFound("roleId.lessThan=" + DEFAULT_ROLE_ID);

        // Get all the custRelPartyList where roleId is less than UPDATED_ROLE_ID
        defaultCustRelPartyShouldBeFound("roleId.lessThan=" + UPDATED_ROLE_ID);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByRoleIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where roleId is greater than DEFAULT_ROLE_ID
        defaultCustRelPartyShouldNotBeFound("roleId.greaterThan=" + DEFAULT_ROLE_ID);

        // Get all the custRelPartyList where roleId is greater than SMALLER_ROLE_ID
        defaultCustRelPartyShouldBeFound("roleId.greaterThan=" + SMALLER_ROLE_ID);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByIndividualIdIsEqualToSomething() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where individualId equals to DEFAULT_INDIVIDUAL_ID
        defaultCustRelPartyShouldBeFound("individualId.equals=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the custRelPartyList where individualId equals to UPDATED_INDIVIDUAL_ID
        defaultCustRelPartyShouldNotBeFound("individualId.equals=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByIndividualIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where individualId not equals to DEFAULT_INDIVIDUAL_ID
        defaultCustRelPartyShouldNotBeFound("individualId.notEquals=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the custRelPartyList where individualId not equals to UPDATED_INDIVIDUAL_ID
        defaultCustRelPartyShouldBeFound("individualId.notEquals=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByIndividualIdIsInShouldWork() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where individualId in DEFAULT_INDIVIDUAL_ID or UPDATED_INDIVIDUAL_ID
        defaultCustRelPartyShouldBeFound("individualId.in=" + DEFAULT_INDIVIDUAL_ID + "," + UPDATED_INDIVIDUAL_ID);

        // Get all the custRelPartyList where individualId equals to UPDATED_INDIVIDUAL_ID
        defaultCustRelPartyShouldNotBeFound("individualId.in=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByIndividualIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where individualId is not null
        defaultCustRelPartyShouldBeFound("individualId.specified=true");

        // Get all the custRelPartyList where individualId is null
        defaultCustRelPartyShouldNotBeFound("individualId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByIndividualIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where individualId is greater than or equal to DEFAULT_INDIVIDUAL_ID
        defaultCustRelPartyShouldBeFound("individualId.greaterThanOrEqual=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the custRelPartyList where individualId is greater than or equal to UPDATED_INDIVIDUAL_ID
        defaultCustRelPartyShouldNotBeFound("individualId.greaterThanOrEqual=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByIndividualIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where individualId is less than or equal to DEFAULT_INDIVIDUAL_ID
        defaultCustRelPartyShouldBeFound("individualId.lessThanOrEqual=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the custRelPartyList where individualId is less than or equal to SMALLER_INDIVIDUAL_ID
        defaultCustRelPartyShouldNotBeFound("individualId.lessThanOrEqual=" + SMALLER_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByIndividualIdIsLessThanSomething() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where individualId is less than DEFAULT_INDIVIDUAL_ID
        defaultCustRelPartyShouldNotBeFound("individualId.lessThan=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the custRelPartyList where individualId is less than UPDATED_INDIVIDUAL_ID
        defaultCustRelPartyShouldBeFound("individualId.lessThan=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByIndividualIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where individualId is greater than DEFAULT_INDIVIDUAL_ID
        defaultCustRelPartyShouldNotBeFound("individualId.greaterThan=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the custRelPartyList where individualId is greater than SMALLER_INDIVIDUAL_ID
        defaultCustRelPartyShouldBeFound("individualId.greaterThan=" + SMALLER_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where validFrom equals to DEFAULT_VALID_FROM
        defaultCustRelPartyShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the custRelPartyList where validFrom equals to UPDATED_VALID_FROM
        defaultCustRelPartyShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where validFrom not equals to DEFAULT_VALID_FROM
        defaultCustRelPartyShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the custRelPartyList where validFrom not equals to UPDATED_VALID_FROM
        defaultCustRelPartyShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultCustRelPartyShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the custRelPartyList where validFrom equals to UPDATED_VALID_FROM
        defaultCustRelPartyShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where validFrom is not null
        defaultCustRelPartyShouldBeFound("validFrom.specified=true");

        // Get all the custRelPartyList where validFrom is null
        defaultCustRelPartyShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where validTo equals to DEFAULT_VALID_TO
        defaultCustRelPartyShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the custRelPartyList where validTo equals to UPDATED_VALID_TO
        defaultCustRelPartyShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where validTo not equals to DEFAULT_VALID_TO
        defaultCustRelPartyShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the custRelPartyList where validTo not equals to UPDATED_VALID_TO
        defaultCustRelPartyShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultCustRelPartyShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the custRelPartyList where validTo equals to UPDATED_VALID_TO
        defaultCustRelPartyShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where validTo is not null
        defaultCustRelPartyShouldBeFound("validTo.specified=true");

        // Get all the custRelPartyList where validTo is null
        defaultCustRelPartyShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByCustomerIdIsEqualToSomething() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where customerId equals to DEFAULT_CUSTOMER_ID
        defaultCustRelPartyShouldBeFound("customerId.equals=" + DEFAULT_CUSTOMER_ID);

        // Get all the custRelPartyList where customerId equals to UPDATED_CUSTOMER_ID
        defaultCustRelPartyShouldNotBeFound("customerId.equals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByCustomerIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where customerId not equals to DEFAULT_CUSTOMER_ID
        defaultCustRelPartyShouldNotBeFound("customerId.notEquals=" + DEFAULT_CUSTOMER_ID);

        // Get all the custRelPartyList where customerId not equals to UPDATED_CUSTOMER_ID
        defaultCustRelPartyShouldBeFound("customerId.notEquals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByCustomerIdIsInShouldWork() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where customerId in DEFAULT_CUSTOMER_ID or UPDATED_CUSTOMER_ID
        defaultCustRelPartyShouldBeFound("customerId.in=" + DEFAULT_CUSTOMER_ID + "," + UPDATED_CUSTOMER_ID);

        // Get all the custRelPartyList where customerId equals to UPDATED_CUSTOMER_ID
        defaultCustRelPartyShouldNotBeFound("customerId.in=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByCustomerIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where customerId is not null
        defaultCustRelPartyShouldBeFound("customerId.specified=true");

        // Get all the custRelPartyList where customerId is null
        defaultCustRelPartyShouldNotBeFound("customerId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByCustomerIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where customerId is greater than or equal to DEFAULT_CUSTOMER_ID
        defaultCustRelPartyShouldBeFound("customerId.greaterThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the custRelPartyList where customerId is greater than or equal to UPDATED_CUSTOMER_ID
        defaultCustRelPartyShouldNotBeFound("customerId.greaterThanOrEqual=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByCustomerIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where customerId is less than or equal to DEFAULT_CUSTOMER_ID
        defaultCustRelPartyShouldBeFound("customerId.lessThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the custRelPartyList where customerId is less than or equal to SMALLER_CUSTOMER_ID
        defaultCustRelPartyShouldNotBeFound("customerId.lessThanOrEqual=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByCustomerIdIsLessThanSomething() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where customerId is less than DEFAULT_CUSTOMER_ID
        defaultCustRelPartyShouldNotBeFound("customerId.lessThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the custRelPartyList where customerId is less than UPDATED_CUSTOMER_ID
        defaultCustRelPartyShouldBeFound("customerId.lessThan=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByCustomerIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        // Get all the custRelPartyList where customerId is greater than DEFAULT_CUSTOMER_ID
        defaultCustRelPartyShouldNotBeFound("customerId.greaterThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the custRelPartyList where customerId is greater than SMALLER_CUSTOMER_ID
        defaultCustRelPartyShouldBeFound("customerId.greaterThan=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);
        Customer customer = CustomerResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        custRelParty.setCustomer(customer);
        custRelPartyRepository.saveAndFlush(custRelParty);
        Long customerId = customer.getId();

        // Get all the custRelPartyList where customer equals to customerId
        defaultCustRelPartyShouldBeFound("customerId.equals=" + customerId);

        // Get all the custRelPartyList where customer equals to (customerId + 1)
        defaultCustRelPartyShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByDepartmentIsEqualToSomething() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);
        Department department = DepartmentResourceIT.createEntity(em);
        em.persist(department);
        em.flush();
        custRelParty.setDepartment(department);
        custRelPartyRepository.saveAndFlush(custRelParty);
        Long departmentId = department.getId();

        // Get all the custRelPartyList where department equals to departmentId
        defaultCustRelPartyShouldBeFound("departmentId.equals=" + departmentId);

        // Get all the custRelPartyList where department equals to (departmentId + 1)
        defaultCustRelPartyShouldNotBeFound("departmentId.equals=" + (departmentId + 1));
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByRoleTypeRefIsEqualToSomething() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);
        RoleTypeRef roleTypeRef = RoleTypeRefResourceIT.createEntity(em);
        em.persist(roleTypeRef);
        em.flush();
        custRelParty.setRoleTypeRef(roleTypeRef);
        custRelPartyRepository.saveAndFlush(custRelParty);
        Long roleTypeRefId = roleTypeRef.getId();

        // Get all the custRelPartyList where roleTypeRef equals to roleTypeRefId
        defaultCustRelPartyShouldBeFound("roleTypeRefId.equals=" + roleTypeRefId);

        // Get all the custRelPartyList where roleTypeRef equals to (roleTypeRefId + 1)
        defaultCustRelPartyShouldNotBeFound("roleTypeRefId.equals=" + (roleTypeRefId + 1));
    }

    @Test
    @Transactional
    void getAllCustRelPartiesByIndividualIsEqualToSomething() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);
        Individual individual = IndividualResourceIT.createEntity(em);
        em.persist(individual);
        em.flush();
        custRelParty.setIndividual(individual);
        custRelPartyRepository.saveAndFlush(custRelParty);
        Long individualId = individual.getId();

        // Get all the custRelPartyList where individual equals to individualId
        defaultCustRelPartyShouldBeFound("individualId.equals=" + individualId);

        // Get all the custRelPartyList where individual equals to (individualId + 1)
        defaultCustRelPartyShouldNotBeFound("individualId.equals=" + (individualId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustRelPartyShouldBeFound(String filter) throws Exception {
        restCustRelPartyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custRelParty.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].roleId").value(hasItem(DEFAULT_ROLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].individualId").value(hasItem(DEFAULT_INDIVIDUAL_ID.intValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())));

        // Check, that the count call also returns 1
        restCustRelPartyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustRelPartyShouldNotBeFound(String filter) throws Exception {
        restCustRelPartyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustRelPartyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustRelParty() throws Exception {
        // Get the custRelParty
        restCustRelPartyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustRelParty() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        int databaseSizeBeforeUpdate = custRelPartyRepository.findAll().size();

        // Update the custRelParty
        CustRelParty updatedCustRelParty = custRelPartyRepository.findById(custRelParty.getId()).get();
        // Disconnect from session so that the updates on updatedCustRelParty are not directly saved in db
        em.detach(updatedCustRelParty);
        updatedCustRelParty
            .name(UPDATED_NAME)
            .roleId(UPDATED_ROLE_ID)
            .individualId(UPDATED_INDIVIDUAL_ID)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .customerId(UPDATED_CUSTOMER_ID);
        CustRelPartyDTO custRelPartyDTO = custRelPartyMapper.toDto(updatedCustRelParty);

        restCustRelPartyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custRelPartyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custRelPartyDTO))
            )
            .andExpect(status().isOk());

        // Validate the CustRelParty in the database
        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeUpdate);
        CustRelParty testCustRelParty = custRelPartyList.get(custRelPartyList.size() - 1);
        assertThat(testCustRelParty.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustRelParty.getRoleId()).isEqualTo(UPDATED_ROLE_ID);
        assertThat(testCustRelParty.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
        assertThat(testCustRelParty.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testCustRelParty.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testCustRelParty.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustRelParty() throws Exception {
        int databaseSizeBeforeUpdate = custRelPartyRepository.findAll().size();
        custRelParty.setId(count.incrementAndGet());

        // Create the CustRelParty
        CustRelPartyDTO custRelPartyDTO = custRelPartyMapper.toDto(custRelParty);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustRelPartyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custRelPartyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custRelPartyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustRelParty in the database
        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustRelParty() throws Exception {
        int databaseSizeBeforeUpdate = custRelPartyRepository.findAll().size();
        custRelParty.setId(count.incrementAndGet());

        // Create the CustRelParty
        CustRelPartyDTO custRelPartyDTO = custRelPartyMapper.toDto(custRelParty);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustRelPartyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custRelPartyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustRelParty in the database
        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustRelParty() throws Exception {
        int databaseSizeBeforeUpdate = custRelPartyRepository.findAll().size();
        custRelParty.setId(count.incrementAndGet());

        // Create the CustRelParty
        CustRelPartyDTO custRelPartyDTO = custRelPartyMapper.toDto(custRelParty);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustRelPartyMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custRelPartyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustRelParty in the database
        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustRelPartyWithPatch() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        int databaseSizeBeforeUpdate = custRelPartyRepository.findAll().size();

        // Update the custRelParty using partial update
        CustRelParty partialUpdatedCustRelParty = new CustRelParty();
        partialUpdatedCustRelParty.setId(custRelParty.getId());

        partialUpdatedCustRelParty.roleId(UPDATED_ROLE_ID).validFrom(UPDATED_VALID_FROM).validTo(UPDATED_VALID_TO);

        restCustRelPartyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustRelParty.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustRelParty))
            )
            .andExpect(status().isOk());

        // Validate the CustRelParty in the database
        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeUpdate);
        CustRelParty testCustRelParty = custRelPartyList.get(custRelPartyList.size() - 1);
        assertThat(testCustRelParty.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustRelParty.getRoleId()).isEqualTo(UPDATED_ROLE_ID);
        assertThat(testCustRelParty.getIndividualId()).isEqualTo(DEFAULT_INDIVIDUAL_ID);
        assertThat(testCustRelParty.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testCustRelParty.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testCustRelParty.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustRelPartyWithPatch() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        int databaseSizeBeforeUpdate = custRelPartyRepository.findAll().size();

        // Update the custRelParty using partial update
        CustRelParty partialUpdatedCustRelParty = new CustRelParty();
        partialUpdatedCustRelParty.setId(custRelParty.getId());

        partialUpdatedCustRelParty
            .name(UPDATED_NAME)
            .roleId(UPDATED_ROLE_ID)
            .individualId(UPDATED_INDIVIDUAL_ID)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .customerId(UPDATED_CUSTOMER_ID);

        restCustRelPartyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustRelParty.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustRelParty))
            )
            .andExpect(status().isOk());

        // Validate the CustRelParty in the database
        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeUpdate);
        CustRelParty testCustRelParty = custRelPartyList.get(custRelPartyList.size() - 1);
        assertThat(testCustRelParty.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustRelParty.getRoleId()).isEqualTo(UPDATED_ROLE_ID);
        assertThat(testCustRelParty.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
        assertThat(testCustRelParty.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testCustRelParty.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testCustRelParty.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustRelParty() throws Exception {
        int databaseSizeBeforeUpdate = custRelPartyRepository.findAll().size();
        custRelParty.setId(count.incrementAndGet());

        // Create the CustRelParty
        CustRelPartyDTO custRelPartyDTO = custRelPartyMapper.toDto(custRelParty);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustRelPartyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custRelPartyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custRelPartyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustRelParty in the database
        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustRelParty() throws Exception {
        int databaseSizeBeforeUpdate = custRelPartyRepository.findAll().size();
        custRelParty.setId(count.incrementAndGet());

        // Create the CustRelParty
        CustRelPartyDTO custRelPartyDTO = custRelPartyMapper.toDto(custRelParty);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustRelPartyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custRelPartyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustRelParty in the database
        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustRelParty() throws Exception {
        int databaseSizeBeforeUpdate = custRelPartyRepository.findAll().size();
        custRelParty.setId(count.incrementAndGet());

        // Create the CustRelParty
        CustRelPartyDTO custRelPartyDTO = custRelPartyMapper.toDto(custRelParty);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustRelPartyMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custRelPartyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustRelParty in the database
        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustRelParty() throws Exception {
        // Initialize the database
        custRelPartyRepository.saveAndFlush(custRelParty);

        int databaseSizeBeforeDelete = custRelPartyRepository.findAll().size();

        // Delete the custRelParty
        restCustRelPartyMockMvc
            .perform(delete(ENTITY_API_URL_ID, custRelParty.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustRelParty> custRelPartyList = custRelPartyRepository.findAll();
        assertThat(custRelPartyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
