package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.IndAuditTrial;
import com.apptium.customer.domain.Individual;
import com.apptium.customer.repository.IndAuditTrialRepository;
import com.apptium.customer.service.criteria.IndAuditTrialCriteria;
import com.apptium.customer.service.dto.IndAuditTrialDTO;
import com.apptium.customer.service.mapper.IndAuditTrialMapper;
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
 * Integration tests for the {@link IndAuditTrialResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IndAuditTrialResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVITY = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY = "BBBBBBBBBB";

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;
    private static final Long SMALLER_CUSTOMER_ID = 1L - 1L;

    private static final Long DEFAULT_INDIVIDUAL_ID = 1L;
    private static final Long UPDATED_INDIVIDUAL_ID = 2L;
    private static final Long SMALLER_INDIVIDUAL_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/ind-audit-trials";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IndAuditTrialRepository indAuditTrialRepository;

    @Autowired
    private IndAuditTrialMapper indAuditTrialMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndAuditTrialMockMvc;

    private IndAuditTrial indAuditTrial;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndAuditTrial createEntity(EntityManager em) {
        IndAuditTrial indAuditTrial = new IndAuditTrial()
            .name(DEFAULT_NAME)
            .activity(DEFAULT_ACTIVITY)
            .customerId(DEFAULT_CUSTOMER_ID)
            .individualId(DEFAULT_INDIVIDUAL_ID);
        return indAuditTrial;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndAuditTrial createUpdatedEntity(EntityManager em) {
        IndAuditTrial indAuditTrial = new IndAuditTrial()
            .name(UPDATED_NAME)
            .activity(UPDATED_ACTIVITY)
            .customerId(UPDATED_CUSTOMER_ID)
            .individualId(UPDATED_INDIVIDUAL_ID);
        return indAuditTrial;
    }

    @BeforeEach
    public void initTest() {
        indAuditTrial = createEntity(em);
    }

    @Test
    @Transactional
    void createIndAuditTrial() throws Exception {
        int databaseSizeBeforeCreate = indAuditTrialRepository.findAll().size();
        // Create the IndAuditTrial
        IndAuditTrialDTO indAuditTrialDTO = indAuditTrialMapper.toDto(indAuditTrial);
        restIndAuditTrialMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indAuditTrialDTO))
            )
            .andExpect(status().isCreated());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeCreate + 1);
        IndAuditTrial testIndAuditTrial = indAuditTrialList.get(indAuditTrialList.size() - 1);
        assertThat(testIndAuditTrial.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIndAuditTrial.getActivity()).isEqualTo(DEFAULT_ACTIVITY);
        assertThat(testIndAuditTrial.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testIndAuditTrial.getIndividualId()).isEqualTo(DEFAULT_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void createIndAuditTrialWithExistingId() throws Exception {
        // Create the IndAuditTrial with an existing ID
        indAuditTrial.setId(1L);
        IndAuditTrialDTO indAuditTrialDTO = indAuditTrialMapper.toDto(indAuditTrial);

        int databaseSizeBeforeCreate = indAuditTrialRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndAuditTrialMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indAuditTrialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = indAuditTrialRepository.findAll().size();
        // set the field null
        indAuditTrial.setName(null);

        // Create the IndAuditTrial, which fails.
        IndAuditTrialDTO indAuditTrialDTO = indAuditTrialMapper.toDto(indAuditTrial);

        restIndAuditTrialMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indAuditTrialDTO))
            )
            .andExpect(status().isBadRequest());

        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivityIsRequired() throws Exception {
        int databaseSizeBeforeTest = indAuditTrialRepository.findAll().size();
        // set the field null
        indAuditTrial.setActivity(null);

        // Create the IndAuditTrial, which fails.
        IndAuditTrialDTO indAuditTrialDTO = indAuditTrialMapper.toDto(indAuditTrial);

        restIndAuditTrialMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indAuditTrialDTO))
            )
            .andExpect(status().isBadRequest());

        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = indAuditTrialRepository.findAll().size();
        // set the field null
        indAuditTrial.setCustomerId(null);

        // Create the IndAuditTrial, which fails.
        IndAuditTrialDTO indAuditTrialDTO = indAuditTrialMapper.toDto(indAuditTrial);

        restIndAuditTrialMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indAuditTrialDTO))
            )
            .andExpect(status().isBadRequest());

        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIndividualIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = indAuditTrialRepository.findAll().size();
        // set the field null
        indAuditTrial.setIndividualId(null);

        // Create the IndAuditTrial, which fails.
        IndAuditTrialDTO indAuditTrialDTO = indAuditTrialMapper.toDto(indAuditTrial);

        restIndAuditTrialMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indAuditTrialDTO))
            )
            .andExpect(status().isBadRequest());

        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIndAuditTrials() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList
        restIndAuditTrialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indAuditTrial.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].activity").value(hasItem(DEFAULT_ACTIVITY)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].individualId").value(hasItem(DEFAULT_INDIVIDUAL_ID.intValue())));
    }

    @Test
    @Transactional
    void getIndAuditTrial() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get the indAuditTrial
        restIndAuditTrialMockMvc
            .perform(get(ENTITY_API_URL_ID, indAuditTrial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(indAuditTrial.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.activity").value(DEFAULT_ACTIVITY))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()))
            .andExpect(jsonPath("$.individualId").value(DEFAULT_INDIVIDUAL_ID.intValue()));
    }

    @Test
    @Transactional
    void getIndAuditTrialsByIdFiltering() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        Long id = indAuditTrial.getId();

        defaultIndAuditTrialShouldBeFound("id.equals=" + id);
        defaultIndAuditTrialShouldNotBeFound("id.notEquals=" + id);

        defaultIndAuditTrialShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultIndAuditTrialShouldNotBeFound("id.greaterThan=" + id);

        defaultIndAuditTrialShouldBeFound("id.lessThanOrEqual=" + id);
        defaultIndAuditTrialShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where name equals to DEFAULT_NAME
        defaultIndAuditTrialShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the indAuditTrialList where name equals to UPDATED_NAME
        defaultIndAuditTrialShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where name not equals to DEFAULT_NAME
        defaultIndAuditTrialShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the indAuditTrialList where name not equals to UPDATED_NAME
        defaultIndAuditTrialShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where name in DEFAULT_NAME or UPDATED_NAME
        defaultIndAuditTrialShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the indAuditTrialList where name equals to UPDATED_NAME
        defaultIndAuditTrialShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where name is not null
        defaultIndAuditTrialShouldBeFound("name.specified=true");

        // Get all the indAuditTrialList where name is null
        defaultIndAuditTrialShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByNameContainsSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where name contains DEFAULT_NAME
        defaultIndAuditTrialShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the indAuditTrialList where name contains UPDATED_NAME
        defaultIndAuditTrialShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where name does not contain DEFAULT_NAME
        defaultIndAuditTrialShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the indAuditTrialList where name does not contain UPDATED_NAME
        defaultIndAuditTrialShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByActivityIsEqualToSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where activity equals to DEFAULT_ACTIVITY
        defaultIndAuditTrialShouldBeFound("activity.equals=" + DEFAULT_ACTIVITY);

        // Get all the indAuditTrialList where activity equals to UPDATED_ACTIVITY
        defaultIndAuditTrialShouldNotBeFound("activity.equals=" + UPDATED_ACTIVITY);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByActivityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where activity not equals to DEFAULT_ACTIVITY
        defaultIndAuditTrialShouldNotBeFound("activity.notEquals=" + DEFAULT_ACTIVITY);

        // Get all the indAuditTrialList where activity not equals to UPDATED_ACTIVITY
        defaultIndAuditTrialShouldBeFound("activity.notEquals=" + UPDATED_ACTIVITY);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByActivityIsInShouldWork() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where activity in DEFAULT_ACTIVITY or UPDATED_ACTIVITY
        defaultIndAuditTrialShouldBeFound("activity.in=" + DEFAULT_ACTIVITY + "," + UPDATED_ACTIVITY);

        // Get all the indAuditTrialList where activity equals to UPDATED_ACTIVITY
        defaultIndAuditTrialShouldNotBeFound("activity.in=" + UPDATED_ACTIVITY);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByActivityIsNullOrNotNull() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where activity is not null
        defaultIndAuditTrialShouldBeFound("activity.specified=true");

        // Get all the indAuditTrialList where activity is null
        defaultIndAuditTrialShouldNotBeFound("activity.specified=false");
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByActivityContainsSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where activity contains DEFAULT_ACTIVITY
        defaultIndAuditTrialShouldBeFound("activity.contains=" + DEFAULT_ACTIVITY);

        // Get all the indAuditTrialList where activity contains UPDATED_ACTIVITY
        defaultIndAuditTrialShouldNotBeFound("activity.contains=" + UPDATED_ACTIVITY);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByActivityNotContainsSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where activity does not contain DEFAULT_ACTIVITY
        defaultIndAuditTrialShouldNotBeFound("activity.doesNotContain=" + DEFAULT_ACTIVITY);

        // Get all the indAuditTrialList where activity does not contain UPDATED_ACTIVITY
        defaultIndAuditTrialShouldBeFound("activity.doesNotContain=" + UPDATED_ACTIVITY);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByCustomerIdIsEqualToSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where customerId equals to DEFAULT_CUSTOMER_ID
        defaultIndAuditTrialShouldBeFound("customerId.equals=" + DEFAULT_CUSTOMER_ID);

        // Get all the indAuditTrialList where customerId equals to UPDATED_CUSTOMER_ID
        defaultIndAuditTrialShouldNotBeFound("customerId.equals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByCustomerIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where customerId not equals to DEFAULT_CUSTOMER_ID
        defaultIndAuditTrialShouldNotBeFound("customerId.notEquals=" + DEFAULT_CUSTOMER_ID);

        // Get all the indAuditTrialList where customerId not equals to UPDATED_CUSTOMER_ID
        defaultIndAuditTrialShouldBeFound("customerId.notEquals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByCustomerIdIsInShouldWork() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where customerId in DEFAULT_CUSTOMER_ID or UPDATED_CUSTOMER_ID
        defaultIndAuditTrialShouldBeFound("customerId.in=" + DEFAULT_CUSTOMER_ID + "," + UPDATED_CUSTOMER_ID);

        // Get all the indAuditTrialList where customerId equals to UPDATED_CUSTOMER_ID
        defaultIndAuditTrialShouldNotBeFound("customerId.in=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByCustomerIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where customerId is not null
        defaultIndAuditTrialShouldBeFound("customerId.specified=true");

        // Get all the indAuditTrialList where customerId is null
        defaultIndAuditTrialShouldNotBeFound("customerId.specified=false");
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByCustomerIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where customerId is greater than or equal to DEFAULT_CUSTOMER_ID
        defaultIndAuditTrialShouldBeFound("customerId.greaterThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the indAuditTrialList where customerId is greater than or equal to UPDATED_CUSTOMER_ID
        defaultIndAuditTrialShouldNotBeFound("customerId.greaterThanOrEqual=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByCustomerIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where customerId is less than or equal to DEFAULT_CUSTOMER_ID
        defaultIndAuditTrialShouldBeFound("customerId.lessThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the indAuditTrialList where customerId is less than or equal to SMALLER_CUSTOMER_ID
        defaultIndAuditTrialShouldNotBeFound("customerId.lessThanOrEqual=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByCustomerIdIsLessThanSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where customerId is less than DEFAULT_CUSTOMER_ID
        defaultIndAuditTrialShouldNotBeFound("customerId.lessThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the indAuditTrialList where customerId is less than UPDATED_CUSTOMER_ID
        defaultIndAuditTrialShouldBeFound("customerId.lessThan=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByCustomerIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where customerId is greater than DEFAULT_CUSTOMER_ID
        defaultIndAuditTrialShouldNotBeFound("customerId.greaterThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the indAuditTrialList where customerId is greater than SMALLER_CUSTOMER_ID
        defaultIndAuditTrialShouldBeFound("customerId.greaterThan=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByIndividualIdIsEqualToSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where individualId equals to DEFAULT_INDIVIDUAL_ID
        defaultIndAuditTrialShouldBeFound("individualId.equals=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the indAuditTrialList where individualId equals to UPDATED_INDIVIDUAL_ID
        defaultIndAuditTrialShouldNotBeFound("individualId.equals=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByIndividualIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where individualId not equals to DEFAULT_INDIVIDUAL_ID
        defaultIndAuditTrialShouldNotBeFound("individualId.notEquals=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the indAuditTrialList where individualId not equals to UPDATED_INDIVIDUAL_ID
        defaultIndAuditTrialShouldBeFound("individualId.notEquals=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByIndividualIdIsInShouldWork() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where individualId in DEFAULT_INDIVIDUAL_ID or UPDATED_INDIVIDUAL_ID
        defaultIndAuditTrialShouldBeFound("individualId.in=" + DEFAULT_INDIVIDUAL_ID + "," + UPDATED_INDIVIDUAL_ID);

        // Get all the indAuditTrialList where individualId equals to UPDATED_INDIVIDUAL_ID
        defaultIndAuditTrialShouldNotBeFound("individualId.in=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByIndividualIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where individualId is not null
        defaultIndAuditTrialShouldBeFound("individualId.specified=true");

        // Get all the indAuditTrialList where individualId is null
        defaultIndAuditTrialShouldNotBeFound("individualId.specified=false");
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByIndividualIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where individualId is greater than or equal to DEFAULT_INDIVIDUAL_ID
        defaultIndAuditTrialShouldBeFound("individualId.greaterThanOrEqual=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the indAuditTrialList where individualId is greater than or equal to UPDATED_INDIVIDUAL_ID
        defaultIndAuditTrialShouldNotBeFound("individualId.greaterThanOrEqual=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByIndividualIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where individualId is less than or equal to DEFAULT_INDIVIDUAL_ID
        defaultIndAuditTrialShouldBeFound("individualId.lessThanOrEqual=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the indAuditTrialList where individualId is less than or equal to SMALLER_INDIVIDUAL_ID
        defaultIndAuditTrialShouldNotBeFound("individualId.lessThanOrEqual=" + SMALLER_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByIndividualIdIsLessThanSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where individualId is less than DEFAULT_INDIVIDUAL_ID
        defaultIndAuditTrialShouldNotBeFound("individualId.lessThan=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the indAuditTrialList where individualId is less than UPDATED_INDIVIDUAL_ID
        defaultIndAuditTrialShouldBeFound("individualId.lessThan=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByIndividualIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        // Get all the indAuditTrialList where individualId is greater than DEFAULT_INDIVIDUAL_ID
        defaultIndAuditTrialShouldNotBeFound("individualId.greaterThan=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the indAuditTrialList where individualId is greater than SMALLER_INDIVIDUAL_ID
        defaultIndAuditTrialShouldBeFound("individualId.greaterThan=" + SMALLER_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndAuditTrialsByIndividualIsEqualToSomething() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);
        Individual individual = IndividualResourceIT.createEntity(em);
        em.persist(individual);
        em.flush();
        indAuditTrial.setIndividual(individual);
        indAuditTrialRepository.saveAndFlush(indAuditTrial);
        Long individualId = individual.getId();

        // Get all the indAuditTrialList where individual equals to individualId
        defaultIndAuditTrialShouldBeFound("individualId.equals=" + individualId);

        // Get all the indAuditTrialList where individual equals to (individualId + 1)
        defaultIndAuditTrialShouldNotBeFound("individualId.equals=" + (individualId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultIndAuditTrialShouldBeFound(String filter) throws Exception {
        restIndAuditTrialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indAuditTrial.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].activity").value(hasItem(DEFAULT_ACTIVITY)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].individualId").value(hasItem(DEFAULT_INDIVIDUAL_ID.intValue())));

        // Check, that the count call also returns 1
        restIndAuditTrialMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultIndAuditTrialShouldNotBeFound(String filter) throws Exception {
        restIndAuditTrialMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIndAuditTrialMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingIndAuditTrial() throws Exception {
        // Get the indAuditTrial
        restIndAuditTrialMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIndAuditTrial() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        int databaseSizeBeforeUpdate = indAuditTrialRepository.findAll().size();

        // Update the indAuditTrial
        IndAuditTrial updatedIndAuditTrial = indAuditTrialRepository.findById(indAuditTrial.getId()).get();
        // Disconnect from session so that the updates on updatedIndAuditTrial are not directly saved in db
        em.detach(updatedIndAuditTrial);
        updatedIndAuditTrial
            .name(UPDATED_NAME)
            .activity(UPDATED_ACTIVITY)
            .customerId(UPDATED_CUSTOMER_ID)
            .individualId(UPDATED_INDIVIDUAL_ID);
        IndAuditTrialDTO indAuditTrialDTO = indAuditTrialMapper.toDto(updatedIndAuditTrial);

        restIndAuditTrialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indAuditTrialDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indAuditTrialDTO))
            )
            .andExpect(status().isOk());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeUpdate);
        IndAuditTrial testIndAuditTrial = indAuditTrialList.get(indAuditTrialList.size() - 1);
        assertThat(testIndAuditTrial.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIndAuditTrial.getActivity()).isEqualTo(UPDATED_ACTIVITY);
        assertThat(testIndAuditTrial.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testIndAuditTrial.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void putNonExistingIndAuditTrial() throws Exception {
        int databaseSizeBeforeUpdate = indAuditTrialRepository.findAll().size();
        indAuditTrial.setId(count.incrementAndGet());

        // Create the IndAuditTrial
        IndAuditTrialDTO indAuditTrialDTO = indAuditTrialMapper.toDto(indAuditTrial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndAuditTrialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indAuditTrialDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indAuditTrialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIndAuditTrial() throws Exception {
        int databaseSizeBeforeUpdate = indAuditTrialRepository.findAll().size();
        indAuditTrial.setId(count.incrementAndGet());

        // Create the IndAuditTrial
        IndAuditTrialDTO indAuditTrialDTO = indAuditTrialMapper.toDto(indAuditTrial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndAuditTrialMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indAuditTrialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIndAuditTrial() throws Exception {
        int databaseSizeBeforeUpdate = indAuditTrialRepository.findAll().size();
        indAuditTrial.setId(count.incrementAndGet());

        // Create the IndAuditTrial
        IndAuditTrialDTO indAuditTrialDTO = indAuditTrialMapper.toDto(indAuditTrial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndAuditTrialMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indAuditTrialDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIndAuditTrialWithPatch() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        int databaseSizeBeforeUpdate = indAuditTrialRepository.findAll().size();

        // Update the indAuditTrial using partial update
        IndAuditTrial partialUpdatedIndAuditTrial = new IndAuditTrial();
        partialUpdatedIndAuditTrial.setId(indAuditTrial.getId());

        partialUpdatedIndAuditTrial.activity(UPDATED_ACTIVITY).individualId(UPDATED_INDIVIDUAL_ID);

        restIndAuditTrialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndAuditTrial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndAuditTrial))
            )
            .andExpect(status().isOk());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeUpdate);
        IndAuditTrial testIndAuditTrial = indAuditTrialList.get(indAuditTrialList.size() - 1);
        assertThat(testIndAuditTrial.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIndAuditTrial.getActivity()).isEqualTo(UPDATED_ACTIVITY);
        assertThat(testIndAuditTrial.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testIndAuditTrial.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void fullUpdateIndAuditTrialWithPatch() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        int databaseSizeBeforeUpdate = indAuditTrialRepository.findAll().size();

        // Update the indAuditTrial using partial update
        IndAuditTrial partialUpdatedIndAuditTrial = new IndAuditTrial();
        partialUpdatedIndAuditTrial.setId(indAuditTrial.getId());

        partialUpdatedIndAuditTrial
            .name(UPDATED_NAME)
            .activity(UPDATED_ACTIVITY)
            .customerId(UPDATED_CUSTOMER_ID)
            .individualId(UPDATED_INDIVIDUAL_ID);

        restIndAuditTrialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndAuditTrial.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndAuditTrial))
            )
            .andExpect(status().isOk());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeUpdate);
        IndAuditTrial testIndAuditTrial = indAuditTrialList.get(indAuditTrialList.size() - 1);
        assertThat(testIndAuditTrial.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIndAuditTrial.getActivity()).isEqualTo(UPDATED_ACTIVITY);
        assertThat(testIndAuditTrial.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testIndAuditTrial.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void patchNonExistingIndAuditTrial() throws Exception {
        int databaseSizeBeforeUpdate = indAuditTrialRepository.findAll().size();
        indAuditTrial.setId(count.incrementAndGet());

        // Create the IndAuditTrial
        IndAuditTrialDTO indAuditTrialDTO = indAuditTrialMapper.toDto(indAuditTrial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndAuditTrialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, indAuditTrialDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indAuditTrialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIndAuditTrial() throws Exception {
        int databaseSizeBeforeUpdate = indAuditTrialRepository.findAll().size();
        indAuditTrial.setId(count.incrementAndGet());

        // Create the IndAuditTrial
        IndAuditTrialDTO indAuditTrialDTO = indAuditTrialMapper.toDto(indAuditTrial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndAuditTrialMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indAuditTrialDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIndAuditTrial() throws Exception {
        int databaseSizeBeforeUpdate = indAuditTrialRepository.findAll().size();
        indAuditTrial.setId(count.incrementAndGet());

        // Create the IndAuditTrial
        IndAuditTrialDTO indAuditTrialDTO = indAuditTrialMapper.toDto(indAuditTrial);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndAuditTrialMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indAuditTrialDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IndAuditTrial in the database
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIndAuditTrial() throws Exception {
        // Initialize the database
        indAuditTrialRepository.saveAndFlush(indAuditTrial);

        int databaseSizeBeforeDelete = indAuditTrialRepository.findAll().size();

        // Delete the indAuditTrial
        restIndAuditTrialMockMvc
            .perform(delete(ENTITY_API_URL_ID, indAuditTrial.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IndAuditTrial> indAuditTrialList = indAuditTrialRepository.findAll();
        assertThat(indAuditTrialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
