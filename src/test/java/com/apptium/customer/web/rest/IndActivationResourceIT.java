package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.IndActivation;
import com.apptium.customer.domain.Individual;
import com.apptium.customer.repository.IndActivationRepository;
import com.apptium.customer.service.criteria.IndActivationCriteria;
import com.apptium.customer.service.dto.IndActivationDTO;
import com.apptium.customer.service.mapper.IndActivationMapper;
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
 * Integration tests for the {@link IndActivationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IndActivationResourceIT {

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

    private static final String ENTITY_API_URL = "/api/ind-activations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IndActivationRepository indActivationRepository;

    @Autowired
    private IndActivationMapper indActivationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndActivationMockMvc;

    private IndActivation indActivation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndActivation createEntity(EntityManager em) {
        IndActivation indActivation = new IndActivation()
            .name(DEFAULT_NAME)
            .activity(DEFAULT_ACTIVITY)
            .customerId(DEFAULT_CUSTOMER_ID)
            .individualId(DEFAULT_INDIVIDUAL_ID);
        return indActivation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndActivation createUpdatedEntity(EntityManager em) {
        IndActivation indActivation = new IndActivation()
            .name(UPDATED_NAME)
            .activity(UPDATED_ACTIVITY)
            .customerId(UPDATED_CUSTOMER_ID)
            .individualId(UPDATED_INDIVIDUAL_ID);
        return indActivation;
    }

    @BeforeEach
    public void initTest() {
        indActivation = createEntity(em);
    }

    @Test
    @Transactional
    void createIndActivation() throws Exception {
        int databaseSizeBeforeCreate = indActivationRepository.findAll().size();
        // Create the IndActivation
        IndActivationDTO indActivationDTO = indActivationMapper.toDto(indActivation);
        restIndActivationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indActivationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the IndActivation in the database
        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeCreate + 1);
        IndActivation testIndActivation = indActivationList.get(indActivationList.size() - 1);
        assertThat(testIndActivation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIndActivation.getActivity()).isEqualTo(DEFAULT_ACTIVITY);
        assertThat(testIndActivation.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testIndActivation.getIndividualId()).isEqualTo(DEFAULT_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void createIndActivationWithExistingId() throws Exception {
        // Create the IndActivation with an existing ID
        indActivation.setId(1L);
        IndActivationDTO indActivationDTO = indActivationMapper.toDto(indActivation);

        int databaseSizeBeforeCreate = indActivationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndActivationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indActivationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndActivation in the database
        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = indActivationRepository.findAll().size();
        // set the field null
        indActivation.setName(null);

        // Create the IndActivation, which fails.
        IndActivationDTO indActivationDTO = indActivationMapper.toDto(indActivation);

        restIndActivationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indActivationDTO))
            )
            .andExpect(status().isBadRequest());

        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivityIsRequired() throws Exception {
        int databaseSizeBeforeTest = indActivationRepository.findAll().size();
        // set the field null
        indActivation.setActivity(null);

        // Create the IndActivation, which fails.
        IndActivationDTO indActivationDTO = indActivationMapper.toDto(indActivation);

        restIndActivationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indActivationDTO))
            )
            .andExpect(status().isBadRequest());

        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = indActivationRepository.findAll().size();
        // set the field null
        indActivation.setCustomerId(null);

        // Create the IndActivation, which fails.
        IndActivationDTO indActivationDTO = indActivationMapper.toDto(indActivation);

        restIndActivationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indActivationDTO))
            )
            .andExpect(status().isBadRequest());

        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIndividualIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = indActivationRepository.findAll().size();
        // set the field null
        indActivation.setIndividualId(null);

        // Create the IndActivation, which fails.
        IndActivationDTO indActivationDTO = indActivationMapper.toDto(indActivation);

        restIndActivationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indActivationDTO))
            )
            .andExpect(status().isBadRequest());

        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIndActivations() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get all the indActivationList
        restIndActivationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indActivation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].activity").value(hasItem(DEFAULT_ACTIVITY)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].individualId").value(hasItem(DEFAULT_INDIVIDUAL_ID.intValue())));
    }

    @Test
    @Transactional
    void getIndActivation() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get the indActivation
        restIndActivationMockMvc
            .perform(get(ENTITY_API_URL_ID, indActivation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(indActivation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.activity").value(DEFAULT_ACTIVITY))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()))
            .andExpect(jsonPath("$.individualId").value(DEFAULT_INDIVIDUAL_ID.intValue()));
    }

    @Test
    @Transactional
    void getIndActivationsByIdFiltering() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        Long id = indActivation.getId();

        defaultIndActivationShouldBeFound("id.equals=" + id);
        defaultIndActivationShouldNotBeFound("id.notEquals=" + id);

        defaultIndActivationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultIndActivationShouldNotBeFound("id.greaterThan=" + id);

        defaultIndActivationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultIndActivationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllIndActivationsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get all the indActivationList where name equals to DEFAULT_NAME
        defaultIndActivationShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the indActivationList where name equals to UPDATED_NAME
        defaultIndActivationShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllIndActivationsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get all the indActivationList where name not equals to DEFAULT_NAME
        defaultIndActivationShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the indActivationList where name not equals to UPDATED_NAME
        defaultIndActivationShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllIndActivationsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get all the indActivationList where name in DEFAULT_NAME or UPDATED_NAME
        defaultIndActivationShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the indActivationList where name equals to UPDATED_NAME
        defaultIndActivationShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllIndActivationsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get all the indActivationList where name is not null
        defaultIndActivationShouldBeFound("name.specified=true");

        // Get all the indActivationList where name is null
        defaultIndActivationShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllIndActivationsByNameContainsSomething() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get all the indActivationList where name contains DEFAULT_NAME
        defaultIndActivationShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the indActivationList where name contains UPDATED_NAME
        defaultIndActivationShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllIndActivationsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get all the indActivationList where name does not contain DEFAULT_NAME
        defaultIndActivationShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the indActivationList where name does not contain UPDATED_NAME
        defaultIndActivationShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllIndActivationsByActivityIsEqualToSomething() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get all the indActivationList where activity equals to DEFAULT_ACTIVITY
        defaultIndActivationShouldBeFound("activity.equals=" + DEFAULT_ACTIVITY);

        // Get all the indActivationList where activity equals to UPDATED_ACTIVITY
        defaultIndActivationShouldNotBeFound("activity.equals=" + UPDATED_ACTIVITY);
    }

    @Test
    @Transactional
    void getAllIndActivationsByActivityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get all the indActivationList where activity not equals to DEFAULT_ACTIVITY
        defaultIndActivationShouldNotBeFound("activity.notEquals=" + DEFAULT_ACTIVITY);

        // Get all the indActivationList where activity not equals to UPDATED_ACTIVITY
        defaultIndActivationShouldBeFound("activity.notEquals=" + UPDATED_ACTIVITY);
    }

    @Test
    @Transactional
    void getAllIndActivationsByActivityIsInShouldWork() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get all the indActivationList where activity in DEFAULT_ACTIVITY or UPDATED_ACTIVITY
        defaultIndActivationShouldBeFound("activity.in=" + DEFAULT_ACTIVITY + "," + UPDATED_ACTIVITY);

        // Get all the indActivationList where activity equals to UPDATED_ACTIVITY
        defaultIndActivationShouldNotBeFound("activity.in=" + UPDATED_ACTIVITY);
    }

    @Test
    @Transactional
    void getAllIndActivationsByActivityIsNullOrNotNull() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get all the indActivationList where activity is not null
        defaultIndActivationShouldBeFound("activity.specified=true");

        // Get all the indActivationList where activity is null
        defaultIndActivationShouldNotBeFound("activity.specified=false");
    }

    @Test
    @Transactional
    void getAllIndActivationsByActivityContainsSomething() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get all the indActivationList where activity contains DEFAULT_ACTIVITY
        defaultIndActivationShouldBeFound("activity.contains=" + DEFAULT_ACTIVITY);

        // Get all the indActivationList where activity contains UPDATED_ACTIVITY
        defaultIndActivationShouldNotBeFound("activity.contains=" + UPDATED_ACTIVITY);
    }

    @Test
    @Transactional
    void getAllIndActivationsByActivityNotContainsSomething() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get all the indActivationList where activity does not contain DEFAULT_ACTIVITY
        defaultIndActivationShouldNotBeFound("activity.doesNotContain=" + DEFAULT_ACTIVITY);

        // Get all the indActivationList where activity does not contain UPDATED_ACTIVITY
        defaultIndActivationShouldBeFound("activity.doesNotContain=" + UPDATED_ACTIVITY);
    }

    @Test
    @Transactional
    void getAllIndActivationsByCustomerIdIsEqualToSomething() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get all the indActivationList where customerId equals to DEFAULT_CUSTOMER_ID
        defaultIndActivationShouldBeFound("customerId.equals=" + DEFAULT_CUSTOMER_ID);

        // Get all the indActivationList where customerId equals to UPDATED_CUSTOMER_ID
        defaultIndActivationShouldNotBeFound("customerId.equals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllIndActivationsByCustomerIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get all the indActivationList where customerId not equals to DEFAULT_CUSTOMER_ID
        defaultIndActivationShouldNotBeFound("customerId.notEquals=" + DEFAULT_CUSTOMER_ID);

        // Get all the indActivationList where customerId not equals to UPDATED_CUSTOMER_ID
        defaultIndActivationShouldBeFound("customerId.notEquals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllIndActivationsByCustomerIdIsInShouldWork() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get all the indActivationList where customerId in DEFAULT_CUSTOMER_ID or UPDATED_CUSTOMER_ID
        defaultIndActivationShouldBeFound("customerId.in=" + DEFAULT_CUSTOMER_ID + "," + UPDATED_CUSTOMER_ID);

        // Get all the indActivationList where customerId equals to UPDATED_CUSTOMER_ID
        defaultIndActivationShouldNotBeFound("customerId.in=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllIndActivationsByCustomerIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get all the indActivationList where customerId is not null
        defaultIndActivationShouldBeFound("customerId.specified=true");

        // Get all the indActivationList where customerId is null
        defaultIndActivationShouldNotBeFound("customerId.specified=false");
    }

    @Test
    @Transactional
    void getAllIndActivationsByCustomerIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get all the indActivationList where customerId is greater than or equal to DEFAULT_CUSTOMER_ID
        defaultIndActivationShouldBeFound("customerId.greaterThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the indActivationList where customerId is greater than or equal to UPDATED_CUSTOMER_ID
        defaultIndActivationShouldNotBeFound("customerId.greaterThanOrEqual=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllIndActivationsByCustomerIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get all the indActivationList where customerId is less than or equal to DEFAULT_CUSTOMER_ID
        defaultIndActivationShouldBeFound("customerId.lessThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the indActivationList where customerId is less than or equal to SMALLER_CUSTOMER_ID
        defaultIndActivationShouldNotBeFound("customerId.lessThanOrEqual=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllIndActivationsByCustomerIdIsLessThanSomething() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get all the indActivationList where customerId is less than DEFAULT_CUSTOMER_ID
        defaultIndActivationShouldNotBeFound("customerId.lessThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the indActivationList where customerId is less than UPDATED_CUSTOMER_ID
        defaultIndActivationShouldBeFound("customerId.lessThan=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllIndActivationsByCustomerIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get all the indActivationList where customerId is greater than DEFAULT_CUSTOMER_ID
        defaultIndActivationShouldNotBeFound("customerId.greaterThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the indActivationList where customerId is greater than SMALLER_CUSTOMER_ID
        defaultIndActivationShouldBeFound("customerId.greaterThan=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllIndActivationsByIndividualIdIsEqualToSomething() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get all the indActivationList where individualId equals to DEFAULT_INDIVIDUAL_ID
        defaultIndActivationShouldBeFound("individualId.equals=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the indActivationList where individualId equals to UPDATED_INDIVIDUAL_ID
        defaultIndActivationShouldNotBeFound("individualId.equals=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndActivationsByIndividualIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get all the indActivationList where individualId not equals to DEFAULT_INDIVIDUAL_ID
        defaultIndActivationShouldNotBeFound("individualId.notEquals=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the indActivationList where individualId not equals to UPDATED_INDIVIDUAL_ID
        defaultIndActivationShouldBeFound("individualId.notEquals=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndActivationsByIndividualIdIsInShouldWork() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get all the indActivationList where individualId in DEFAULT_INDIVIDUAL_ID or UPDATED_INDIVIDUAL_ID
        defaultIndActivationShouldBeFound("individualId.in=" + DEFAULT_INDIVIDUAL_ID + "," + UPDATED_INDIVIDUAL_ID);

        // Get all the indActivationList where individualId equals to UPDATED_INDIVIDUAL_ID
        defaultIndActivationShouldNotBeFound("individualId.in=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndActivationsByIndividualIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get all the indActivationList where individualId is not null
        defaultIndActivationShouldBeFound("individualId.specified=true");

        // Get all the indActivationList where individualId is null
        defaultIndActivationShouldNotBeFound("individualId.specified=false");
    }

    @Test
    @Transactional
    void getAllIndActivationsByIndividualIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get all the indActivationList where individualId is greater than or equal to DEFAULT_INDIVIDUAL_ID
        defaultIndActivationShouldBeFound("individualId.greaterThanOrEqual=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the indActivationList where individualId is greater than or equal to UPDATED_INDIVIDUAL_ID
        defaultIndActivationShouldNotBeFound("individualId.greaterThanOrEqual=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndActivationsByIndividualIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get all the indActivationList where individualId is less than or equal to DEFAULT_INDIVIDUAL_ID
        defaultIndActivationShouldBeFound("individualId.lessThanOrEqual=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the indActivationList where individualId is less than or equal to SMALLER_INDIVIDUAL_ID
        defaultIndActivationShouldNotBeFound("individualId.lessThanOrEqual=" + SMALLER_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndActivationsByIndividualIdIsLessThanSomething() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get all the indActivationList where individualId is less than DEFAULT_INDIVIDUAL_ID
        defaultIndActivationShouldNotBeFound("individualId.lessThan=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the indActivationList where individualId is less than UPDATED_INDIVIDUAL_ID
        defaultIndActivationShouldBeFound("individualId.lessThan=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndActivationsByIndividualIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        // Get all the indActivationList where individualId is greater than DEFAULT_INDIVIDUAL_ID
        defaultIndActivationShouldNotBeFound("individualId.greaterThan=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the indActivationList where individualId is greater than SMALLER_INDIVIDUAL_ID
        defaultIndActivationShouldBeFound("individualId.greaterThan=" + SMALLER_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndActivationsByIndividualIsEqualToSomething() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);
        Individual individual = IndividualResourceIT.createEntity(em);
        em.persist(individual);
        em.flush();
        indActivation.setIndividual(individual);
        individual.setIndActivation(indActivation);
        indActivationRepository.saveAndFlush(indActivation);
        Long individualId = individual.getId();

        // Get all the indActivationList where individual equals to individualId
        defaultIndActivationShouldBeFound("individualId.equals=" + individualId);

        // Get all the indActivationList where individual equals to (individualId + 1)
        defaultIndActivationShouldNotBeFound("individualId.equals=" + (individualId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultIndActivationShouldBeFound(String filter) throws Exception {
        restIndActivationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indActivation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].activity").value(hasItem(DEFAULT_ACTIVITY)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].individualId").value(hasItem(DEFAULT_INDIVIDUAL_ID.intValue())));

        // Check, that the count call also returns 1
        restIndActivationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultIndActivationShouldNotBeFound(String filter) throws Exception {
        restIndActivationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIndActivationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingIndActivation() throws Exception {
        // Get the indActivation
        restIndActivationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIndActivation() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        int databaseSizeBeforeUpdate = indActivationRepository.findAll().size();

        // Update the indActivation
        IndActivation updatedIndActivation = indActivationRepository.findById(indActivation.getId()).get();
        // Disconnect from session so that the updates on updatedIndActivation are not directly saved in db
        em.detach(updatedIndActivation);
        updatedIndActivation
            .name(UPDATED_NAME)
            .activity(UPDATED_ACTIVITY)
            .customerId(UPDATED_CUSTOMER_ID)
            .individualId(UPDATED_INDIVIDUAL_ID);
        IndActivationDTO indActivationDTO = indActivationMapper.toDto(updatedIndActivation);

        restIndActivationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indActivationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indActivationDTO))
            )
            .andExpect(status().isOk());

        // Validate the IndActivation in the database
        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeUpdate);
        IndActivation testIndActivation = indActivationList.get(indActivationList.size() - 1);
        assertThat(testIndActivation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIndActivation.getActivity()).isEqualTo(UPDATED_ACTIVITY);
        assertThat(testIndActivation.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testIndActivation.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void putNonExistingIndActivation() throws Exception {
        int databaseSizeBeforeUpdate = indActivationRepository.findAll().size();
        indActivation.setId(count.incrementAndGet());

        // Create the IndActivation
        IndActivationDTO indActivationDTO = indActivationMapper.toDto(indActivation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndActivationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indActivationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indActivationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndActivation in the database
        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIndActivation() throws Exception {
        int databaseSizeBeforeUpdate = indActivationRepository.findAll().size();
        indActivation.setId(count.incrementAndGet());

        // Create the IndActivation
        IndActivationDTO indActivationDTO = indActivationMapper.toDto(indActivation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndActivationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indActivationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndActivation in the database
        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIndActivation() throws Exception {
        int databaseSizeBeforeUpdate = indActivationRepository.findAll().size();
        indActivation.setId(count.incrementAndGet());

        // Create the IndActivation
        IndActivationDTO indActivationDTO = indActivationMapper.toDto(indActivation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndActivationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indActivationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IndActivation in the database
        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIndActivationWithPatch() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        int databaseSizeBeforeUpdate = indActivationRepository.findAll().size();

        // Update the indActivation using partial update
        IndActivation partialUpdatedIndActivation = new IndActivation();
        partialUpdatedIndActivation.setId(indActivation.getId());

        partialUpdatedIndActivation.name(UPDATED_NAME);

        restIndActivationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndActivation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndActivation))
            )
            .andExpect(status().isOk());

        // Validate the IndActivation in the database
        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeUpdate);
        IndActivation testIndActivation = indActivationList.get(indActivationList.size() - 1);
        assertThat(testIndActivation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIndActivation.getActivity()).isEqualTo(DEFAULT_ACTIVITY);
        assertThat(testIndActivation.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testIndActivation.getIndividualId()).isEqualTo(DEFAULT_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void fullUpdateIndActivationWithPatch() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        int databaseSizeBeforeUpdate = indActivationRepository.findAll().size();

        // Update the indActivation using partial update
        IndActivation partialUpdatedIndActivation = new IndActivation();
        partialUpdatedIndActivation.setId(indActivation.getId());

        partialUpdatedIndActivation
            .name(UPDATED_NAME)
            .activity(UPDATED_ACTIVITY)
            .customerId(UPDATED_CUSTOMER_ID)
            .individualId(UPDATED_INDIVIDUAL_ID);

        restIndActivationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndActivation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndActivation))
            )
            .andExpect(status().isOk());

        // Validate the IndActivation in the database
        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeUpdate);
        IndActivation testIndActivation = indActivationList.get(indActivationList.size() - 1);
        assertThat(testIndActivation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIndActivation.getActivity()).isEqualTo(UPDATED_ACTIVITY);
        assertThat(testIndActivation.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testIndActivation.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void patchNonExistingIndActivation() throws Exception {
        int databaseSizeBeforeUpdate = indActivationRepository.findAll().size();
        indActivation.setId(count.incrementAndGet());

        // Create the IndActivation
        IndActivationDTO indActivationDTO = indActivationMapper.toDto(indActivation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndActivationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, indActivationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indActivationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndActivation in the database
        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIndActivation() throws Exception {
        int databaseSizeBeforeUpdate = indActivationRepository.findAll().size();
        indActivation.setId(count.incrementAndGet());

        // Create the IndActivation
        IndActivationDTO indActivationDTO = indActivationMapper.toDto(indActivation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndActivationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indActivationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndActivation in the database
        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIndActivation() throws Exception {
        int databaseSizeBeforeUpdate = indActivationRepository.findAll().size();
        indActivation.setId(count.incrementAndGet());

        // Create the IndActivation
        IndActivationDTO indActivationDTO = indActivationMapper.toDto(indActivation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndActivationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indActivationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IndActivation in the database
        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIndActivation() throws Exception {
        // Initialize the database
        indActivationRepository.saveAndFlush(indActivation);

        int databaseSizeBeforeDelete = indActivationRepository.findAll().size();

        // Delete the indActivation
        restIndActivationMockMvc
            .perform(delete(ENTITY_API_URL_ID, indActivation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IndActivation> indActivationList = indActivationRepository.findAll();
        assertThat(indActivationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
