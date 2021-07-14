package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.CustRelParty;
import com.apptium.customer.domain.IndActivation;
import com.apptium.customer.domain.IndAuditTrial;
import com.apptium.customer.domain.IndChar;
import com.apptium.customer.domain.IndContact;
import com.apptium.customer.domain.IndNewsLetterConf;
import com.apptium.customer.domain.Individual;
import com.apptium.customer.domain.ShoppingSessionRef;
import com.apptium.customer.repository.IndividualRepository;
import com.apptium.customer.service.criteria.IndividualCriteria;
import com.apptium.customer.service.dto.IndividualDTO;
import com.apptium.customer.service.mapper.IndividualMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link IndividualResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IndividualResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FORMATTED_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FORMATTED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_OF_BIRTH = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_MARITAL_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_MARITAL_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/individuals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IndividualRepository individualRepository;

    @Autowired
    private IndividualMapper individualMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndividualMockMvc;

    private Individual individual;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Individual createEntity(EntityManager em) {
        Individual individual = new Individual()
            .title(DEFAULT_TITLE)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .formattedName(DEFAULT_FORMATTED_NAME)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .gender(DEFAULT_GENDER)
            .maritalStatus(DEFAULT_MARITAL_STATUS)
            .nationality(DEFAULT_NATIONALITY)
            .status(DEFAULT_STATUS);
        return individual;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Individual createUpdatedEntity(EntityManager em) {
        Individual individual = new Individual()
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .formattedName(UPDATED_FORMATTED_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .gender(UPDATED_GENDER)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .nationality(UPDATED_NATIONALITY)
            .status(UPDATED_STATUS);
        return individual;
    }

    @BeforeEach
    public void initTest() {
        individual = createEntity(em);
    }

    @Test
    @Transactional
    void createIndividual() throws Exception {
        int databaseSizeBeforeCreate = individualRepository.findAll().size();
        // Create the Individual
        IndividualDTO individualDTO = individualMapper.toDto(individual);
        restIndividualMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(individualDTO)))
            .andExpect(status().isCreated());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeCreate + 1);
        Individual testIndividual = individualList.get(individualList.size() - 1);
        assertThat(testIndividual.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testIndividual.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testIndividual.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testIndividual.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testIndividual.getFormattedName()).isEqualTo(DEFAULT_FORMATTED_NAME);
        assertThat(testIndividual.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testIndividual.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testIndividual.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testIndividual.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testIndividual.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createIndividualWithExistingId() throws Exception {
        // Create the Individual with an existing ID
        individual.setId(1L);
        IndividualDTO individualDTO = individualMapper.toDto(individual);

        int databaseSizeBeforeCreate = individualRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndividualMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(individualDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = individualRepository.findAll().size();
        // set the field null
        individual.setTitle(null);

        // Create the Individual, which fails.
        IndividualDTO individualDTO = individualMapper.toDto(individual);

        restIndividualMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(individualDTO)))
            .andExpect(status().isBadRequest());

        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = individualRepository.findAll().size();
        // set the field null
        individual.setFirstName(null);

        // Create the Individual, which fails.
        IndividualDTO individualDTO = individualMapper.toDto(individual);

        restIndividualMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(individualDTO)))
            .andExpect(status().isBadRequest());

        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = individualRepository.findAll().size();
        // set the field null
        individual.setLastName(null);

        // Create the Individual, which fails.
        IndividualDTO individualDTO = individualMapper.toDto(individual);

        restIndividualMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(individualDTO)))
            .andExpect(status().isBadRequest());

        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFormattedNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = individualRepository.findAll().size();
        // set the field null
        individual.setFormattedName(null);

        // Create the Individual, which fails.
        IndividualDTO individualDTO = individualMapper.toDto(individual);

        restIndividualMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(individualDTO)))
            .andExpect(status().isBadRequest());

        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateOfBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = individualRepository.findAll().size();
        // set the field null
        individual.setDateOfBirth(null);

        // Create the Individual, which fails.
        IndividualDTO individualDTO = individualMapper.toDto(individual);

        restIndividualMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(individualDTO)))
            .andExpect(status().isBadRequest());

        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = individualRepository.findAll().size();
        // set the field null
        individual.setGender(null);

        // Create the Individual, which fails.
        IndividualDTO individualDTO = individualMapper.toDto(individual);

        restIndividualMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(individualDTO)))
            .andExpect(status().isBadRequest());

        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNationalityIsRequired() throws Exception {
        int databaseSizeBeforeTest = individualRepository.findAll().size();
        // set the field null
        individual.setNationality(null);

        // Create the Individual, which fails.
        IndividualDTO individualDTO = individualMapper.toDto(individual);

        restIndividualMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(individualDTO)))
            .andExpect(status().isBadRequest());

        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIndividuals() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList
        restIndividualMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(individual.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].formattedName").value(hasItem(DEFAULT_FORMATTED_NAME)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getIndividual() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get the individual
        restIndividualMockMvc
            .perform(get(ENTITY_API_URL_ID, individual.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(individual.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.formattedName").value(DEFAULT_FORMATTED_NAME))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getIndividualsByIdFiltering() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        Long id = individual.getId();

        defaultIndividualShouldBeFound("id.equals=" + id);
        defaultIndividualShouldNotBeFound("id.notEquals=" + id);

        defaultIndividualShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultIndividualShouldNotBeFound("id.greaterThan=" + id);

        defaultIndividualShouldBeFound("id.lessThanOrEqual=" + id);
        defaultIndividualShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllIndividualsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where title equals to DEFAULT_TITLE
        defaultIndividualShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the individualList where title equals to UPDATED_TITLE
        defaultIndividualShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllIndividualsByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where title not equals to DEFAULT_TITLE
        defaultIndividualShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the individualList where title not equals to UPDATED_TITLE
        defaultIndividualShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllIndividualsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultIndividualShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the individualList where title equals to UPDATED_TITLE
        defaultIndividualShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllIndividualsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where title is not null
        defaultIndividualShouldBeFound("title.specified=true");

        // Get all the individualList where title is null
        defaultIndividualShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllIndividualsByTitleContainsSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where title contains DEFAULT_TITLE
        defaultIndividualShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the individualList where title contains UPDATED_TITLE
        defaultIndividualShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllIndividualsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where title does not contain DEFAULT_TITLE
        defaultIndividualShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the individualList where title does not contain UPDATED_TITLE
        defaultIndividualShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllIndividualsByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where firstName equals to DEFAULT_FIRST_NAME
        defaultIndividualShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the individualList where firstName equals to UPDATED_FIRST_NAME
        defaultIndividualShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllIndividualsByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where firstName not equals to DEFAULT_FIRST_NAME
        defaultIndividualShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the individualList where firstName not equals to UPDATED_FIRST_NAME
        defaultIndividualShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllIndividualsByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultIndividualShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the individualList where firstName equals to UPDATED_FIRST_NAME
        defaultIndividualShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllIndividualsByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where firstName is not null
        defaultIndividualShouldBeFound("firstName.specified=true");

        // Get all the individualList where firstName is null
        defaultIndividualShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    void getAllIndividualsByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where firstName contains DEFAULT_FIRST_NAME
        defaultIndividualShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the individualList where firstName contains UPDATED_FIRST_NAME
        defaultIndividualShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllIndividualsByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where firstName does not contain DEFAULT_FIRST_NAME
        defaultIndividualShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the individualList where firstName does not contain UPDATED_FIRST_NAME
        defaultIndividualShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllIndividualsByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where lastName equals to DEFAULT_LAST_NAME
        defaultIndividualShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the individualList where lastName equals to UPDATED_LAST_NAME
        defaultIndividualShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllIndividualsByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where lastName not equals to DEFAULT_LAST_NAME
        defaultIndividualShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the individualList where lastName not equals to UPDATED_LAST_NAME
        defaultIndividualShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllIndividualsByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultIndividualShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the individualList where lastName equals to UPDATED_LAST_NAME
        defaultIndividualShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllIndividualsByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where lastName is not null
        defaultIndividualShouldBeFound("lastName.specified=true");

        // Get all the individualList where lastName is null
        defaultIndividualShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    void getAllIndividualsByLastNameContainsSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where lastName contains DEFAULT_LAST_NAME
        defaultIndividualShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the individualList where lastName contains UPDATED_LAST_NAME
        defaultIndividualShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllIndividualsByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where lastName does not contain DEFAULT_LAST_NAME
        defaultIndividualShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the individualList where lastName does not contain UPDATED_LAST_NAME
        defaultIndividualShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllIndividualsByMiddleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where middleName equals to DEFAULT_MIDDLE_NAME
        defaultIndividualShouldBeFound("middleName.equals=" + DEFAULT_MIDDLE_NAME);

        // Get all the individualList where middleName equals to UPDATED_MIDDLE_NAME
        defaultIndividualShouldNotBeFound("middleName.equals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllIndividualsByMiddleNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where middleName not equals to DEFAULT_MIDDLE_NAME
        defaultIndividualShouldNotBeFound("middleName.notEquals=" + DEFAULT_MIDDLE_NAME);

        // Get all the individualList where middleName not equals to UPDATED_MIDDLE_NAME
        defaultIndividualShouldBeFound("middleName.notEquals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllIndividualsByMiddleNameIsInShouldWork() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where middleName in DEFAULT_MIDDLE_NAME or UPDATED_MIDDLE_NAME
        defaultIndividualShouldBeFound("middleName.in=" + DEFAULT_MIDDLE_NAME + "," + UPDATED_MIDDLE_NAME);

        // Get all the individualList where middleName equals to UPDATED_MIDDLE_NAME
        defaultIndividualShouldNotBeFound("middleName.in=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllIndividualsByMiddleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where middleName is not null
        defaultIndividualShouldBeFound("middleName.specified=true");

        // Get all the individualList where middleName is null
        defaultIndividualShouldNotBeFound("middleName.specified=false");
    }

    @Test
    @Transactional
    void getAllIndividualsByMiddleNameContainsSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where middleName contains DEFAULT_MIDDLE_NAME
        defaultIndividualShouldBeFound("middleName.contains=" + DEFAULT_MIDDLE_NAME);

        // Get all the individualList where middleName contains UPDATED_MIDDLE_NAME
        defaultIndividualShouldNotBeFound("middleName.contains=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllIndividualsByMiddleNameNotContainsSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where middleName does not contain DEFAULT_MIDDLE_NAME
        defaultIndividualShouldNotBeFound("middleName.doesNotContain=" + DEFAULT_MIDDLE_NAME);

        // Get all the individualList where middleName does not contain UPDATED_MIDDLE_NAME
        defaultIndividualShouldBeFound("middleName.doesNotContain=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllIndividualsByFormattedNameIsEqualToSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where formattedName equals to DEFAULT_FORMATTED_NAME
        defaultIndividualShouldBeFound("formattedName.equals=" + DEFAULT_FORMATTED_NAME);

        // Get all the individualList where formattedName equals to UPDATED_FORMATTED_NAME
        defaultIndividualShouldNotBeFound("formattedName.equals=" + UPDATED_FORMATTED_NAME);
    }

    @Test
    @Transactional
    void getAllIndividualsByFormattedNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where formattedName not equals to DEFAULT_FORMATTED_NAME
        defaultIndividualShouldNotBeFound("formattedName.notEquals=" + DEFAULT_FORMATTED_NAME);

        // Get all the individualList where formattedName not equals to UPDATED_FORMATTED_NAME
        defaultIndividualShouldBeFound("formattedName.notEquals=" + UPDATED_FORMATTED_NAME);
    }

    @Test
    @Transactional
    void getAllIndividualsByFormattedNameIsInShouldWork() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where formattedName in DEFAULT_FORMATTED_NAME or UPDATED_FORMATTED_NAME
        defaultIndividualShouldBeFound("formattedName.in=" + DEFAULT_FORMATTED_NAME + "," + UPDATED_FORMATTED_NAME);

        // Get all the individualList where formattedName equals to UPDATED_FORMATTED_NAME
        defaultIndividualShouldNotBeFound("formattedName.in=" + UPDATED_FORMATTED_NAME);
    }

    @Test
    @Transactional
    void getAllIndividualsByFormattedNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where formattedName is not null
        defaultIndividualShouldBeFound("formattedName.specified=true");

        // Get all the individualList where formattedName is null
        defaultIndividualShouldNotBeFound("formattedName.specified=false");
    }

    @Test
    @Transactional
    void getAllIndividualsByFormattedNameContainsSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where formattedName contains DEFAULT_FORMATTED_NAME
        defaultIndividualShouldBeFound("formattedName.contains=" + DEFAULT_FORMATTED_NAME);

        // Get all the individualList where formattedName contains UPDATED_FORMATTED_NAME
        defaultIndividualShouldNotBeFound("formattedName.contains=" + UPDATED_FORMATTED_NAME);
    }

    @Test
    @Transactional
    void getAllIndividualsByFormattedNameNotContainsSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where formattedName does not contain DEFAULT_FORMATTED_NAME
        defaultIndividualShouldNotBeFound("formattedName.doesNotContain=" + DEFAULT_FORMATTED_NAME);

        // Get all the individualList where formattedName does not contain UPDATED_FORMATTED_NAME
        defaultIndividualShouldBeFound("formattedName.doesNotContain=" + UPDATED_FORMATTED_NAME);
    }

    @Test
    @Transactional
    void getAllIndividualsByDateOfBirthIsEqualToSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where dateOfBirth equals to DEFAULT_DATE_OF_BIRTH
        defaultIndividualShouldBeFound("dateOfBirth.equals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the individualList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultIndividualShouldNotBeFound("dateOfBirth.equals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllIndividualsByDateOfBirthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where dateOfBirth not equals to DEFAULT_DATE_OF_BIRTH
        defaultIndividualShouldNotBeFound("dateOfBirth.notEquals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the individualList where dateOfBirth not equals to UPDATED_DATE_OF_BIRTH
        defaultIndividualShouldBeFound("dateOfBirth.notEquals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllIndividualsByDateOfBirthIsInShouldWork() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where dateOfBirth in DEFAULT_DATE_OF_BIRTH or UPDATED_DATE_OF_BIRTH
        defaultIndividualShouldBeFound("dateOfBirth.in=" + DEFAULT_DATE_OF_BIRTH + "," + UPDATED_DATE_OF_BIRTH);

        // Get all the individualList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultIndividualShouldNotBeFound("dateOfBirth.in=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllIndividualsByDateOfBirthIsNullOrNotNull() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where dateOfBirth is not null
        defaultIndividualShouldBeFound("dateOfBirth.specified=true");

        // Get all the individualList where dateOfBirth is null
        defaultIndividualShouldNotBeFound("dateOfBirth.specified=false");
    }

    @Test
    @Transactional
    void getAllIndividualsByDateOfBirthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where dateOfBirth is greater than or equal to DEFAULT_DATE_OF_BIRTH
        defaultIndividualShouldBeFound("dateOfBirth.greaterThanOrEqual=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the individualList where dateOfBirth is greater than or equal to UPDATED_DATE_OF_BIRTH
        defaultIndividualShouldNotBeFound("dateOfBirth.greaterThanOrEqual=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllIndividualsByDateOfBirthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where dateOfBirth is less than or equal to DEFAULT_DATE_OF_BIRTH
        defaultIndividualShouldBeFound("dateOfBirth.lessThanOrEqual=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the individualList where dateOfBirth is less than or equal to SMALLER_DATE_OF_BIRTH
        defaultIndividualShouldNotBeFound("dateOfBirth.lessThanOrEqual=" + SMALLER_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllIndividualsByDateOfBirthIsLessThanSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where dateOfBirth is less than DEFAULT_DATE_OF_BIRTH
        defaultIndividualShouldNotBeFound("dateOfBirth.lessThan=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the individualList where dateOfBirth is less than UPDATED_DATE_OF_BIRTH
        defaultIndividualShouldBeFound("dateOfBirth.lessThan=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllIndividualsByDateOfBirthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where dateOfBirth is greater than DEFAULT_DATE_OF_BIRTH
        defaultIndividualShouldNotBeFound("dateOfBirth.greaterThan=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the individualList where dateOfBirth is greater than SMALLER_DATE_OF_BIRTH
        defaultIndividualShouldBeFound("dateOfBirth.greaterThan=" + SMALLER_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllIndividualsByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where gender equals to DEFAULT_GENDER
        defaultIndividualShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the individualList where gender equals to UPDATED_GENDER
        defaultIndividualShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllIndividualsByGenderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where gender not equals to DEFAULT_GENDER
        defaultIndividualShouldNotBeFound("gender.notEquals=" + DEFAULT_GENDER);

        // Get all the individualList where gender not equals to UPDATED_GENDER
        defaultIndividualShouldBeFound("gender.notEquals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllIndividualsByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultIndividualShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the individualList where gender equals to UPDATED_GENDER
        defaultIndividualShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllIndividualsByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where gender is not null
        defaultIndividualShouldBeFound("gender.specified=true");

        // Get all the individualList where gender is null
        defaultIndividualShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    void getAllIndividualsByGenderContainsSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where gender contains DEFAULT_GENDER
        defaultIndividualShouldBeFound("gender.contains=" + DEFAULT_GENDER);

        // Get all the individualList where gender contains UPDATED_GENDER
        defaultIndividualShouldNotBeFound("gender.contains=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllIndividualsByGenderNotContainsSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where gender does not contain DEFAULT_GENDER
        defaultIndividualShouldNotBeFound("gender.doesNotContain=" + DEFAULT_GENDER);

        // Get all the individualList where gender does not contain UPDATED_GENDER
        defaultIndividualShouldBeFound("gender.doesNotContain=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllIndividualsByMaritalStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where maritalStatus equals to DEFAULT_MARITAL_STATUS
        defaultIndividualShouldBeFound("maritalStatus.equals=" + DEFAULT_MARITAL_STATUS);

        // Get all the individualList where maritalStatus equals to UPDATED_MARITAL_STATUS
        defaultIndividualShouldNotBeFound("maritalStatus.equals=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    void getAllIndividualsByMaritalStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where maritalStatus not equals to DEFAULT_MARITAL_STATUS
        defaultIndividualShouldNotBeFound("maritalStatus.notEquals=" + DEFAULT_MARITAL_STATUS);

        // Get all the individualList where maritalStatus not equals to UPDATED_MARITAL_STATUS
        defaultIndividualShouldBeFound("maritalStatus.notEquals=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    void getAllIndividualsByMaritalStatusIsInShouldWork() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where maritalStatus in DEFAULT_MARITAL_STATUS or UPDATED_MARITAL_STATUS
        defaultIndividualShouldBeFound("maritalStatus.in=" + DEFAULT_MARITAL_STATUS + "," + UPDATED_MARITAL_STATUS);

        // Get all the individualList where maritalStatus equals to UPDATED_MARITAL_STATUS
        defaultIndividualShouldNotBeFound("maritalStatus.in=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    void getAllIndividualsByMaritalStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where maritalStatus is not null
        defaultIndividualShouldBeFound("maritalStatus.specified=true");

        // Get all the individualList where maritalStatus is null
        defaultIndividualShouldNotBeFound("maritalStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllIndividualsByMaritalStatusContainsSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where maritalStatus contains DEFAULT_MARITAL_STATUS
        defaultIndividualShouldBeFound("maritalStatus.contains=" + DEFAULT_MARITAL_STATUS);

        // Get all the individualList where maritalStatus contains UPDATED_MARITAL_STATUS
        defaultIndividualShouldNotBeFound("maritalStatus.contains=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    void getAllIndividualsByMaritalStatusNotContainsSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where maritalStatus does not contain DEFAULT_MARITAL_STATUS
        defaultIndividualShouldNotBeFound("maritalStatus.doesNotContain=" + DEFAULT_MARITAL_STATUS);

        // Get all the individualList where maritalStatus does not contain UPDATED_MARITAL_STATUS
        defaultIndividualShouldBeFound("maritalStatus.doesNotContain=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    void getAllIndividualsByNationalityIsEqualToSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where nationality equals to DEFAULT_NATIONALITY
        defaultIndividualShouldBeFound("nationality.equals=" + DEFAULT_NATIONALITY);

        // Get all the individualList where nationality equals to UPDATED_NATIONALITY
        defaultIndividualShouldNotBeFound("nationality.equals=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllIndividualsByNationalityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where nationality not equals to DEFAULT_NATIONALITY
        defaultIndividualShouldNotBeFound("nationality.notEquals=" + DEFAULT_NATIONALITY);

        // Get all the individualList where nationality not equals to UPDATED_NATIONALITY
        defaultIndividualShouldBeFound("nationality.notEquals=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllIndividualsByNationalityIsInShouldWork() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where nationality in DEFAULT_NATIONALITY or UPDATED_NATIONALITY
        defaultIndividualShouldBeFound("nationality.in=" + DEFAULT_NATIONALITY + "," + UPDATED_NATIONALITY);

        // Get all the individualList where nationality equals to UPDATED_NATIONALITY
        defaultIndividualShouldNotBeFound("nationality.in=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllIndividualsByNationalityIsNullOrNotNull() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where nationality is not null
        defaultIndividualShouldBeFound("nationality.specified=true");

        // Get all the individualList where nationality is null
        defaultIndividualShouldNotBeFound("nationality.specified=false");
    }

    @Test
    @Transactional
    void getAllIndividualsByNationalityContainsSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where nationality contains DEFAULT_NATIONALITY
        defaultIndividualShouldBeFound("nationality.contains=" + DEFAULT_NATIONALITY);

        // Get all the individualList where nationality contains UPDATED_NATIONALITY
        defaultIndividualShouldNotBeFound("nationality.contains=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllIndividualsByNationalityNotContainsSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where nationality does not contain DEFAULT_NATIONALITY
        defaultIndividualShouldNotBeFound("nationality.doesNotContain=" + DEFAULT_NATIONALITY);

        // Get all the individualList where nationality does not contain UPDATED_NATIONALITY
        defaultIndividualShouldBeFound("nationality.doesNotContain=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllIndividualsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where status equals to DEFAULT_STATUS
        defaultIndividualShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the individualList where status equals to UPDATED_STATUS
        defaultIndividualShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllIndividualsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where status not equals to DEFAULT_STATUS
        defaultIndividualShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the individualList where status not equals to UPDATED_STATUS
        defaultIndividualShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllIndividualsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultIndividualShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the individualList where status equals to UPDATED_STATUS
        defaultIndividualShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllIndividualsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where status is not null
        defaultIndividualShouldBeFound("status.specified=true");

        // Get all the individualList where status is null
        defaultIndividualShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllIndividualsByStatusContainsSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where status contains DEFAULT_STATUS
        defaultIndividualShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the individualList where status contains UPDATED_STATUS
        defaultIndividualShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllIndividualsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        // Get all the individualList where status does not contain DEFAULT_STATUS
        defaultIndividualShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the individualList where status does not contain UPDATED_STATUS
        defaultIndividualShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllIndividualsByIndActivationIsEqualToSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);
        IndActivation indActivation = IndActivationResourceIT.createEntity(em);
        em.persist(indActivation);
        em.flush();
        individual.setIndActivation(indActivation);
        individualRepository.saveAndFlush(individual);
        Long indActivationId = indActivation.getId();

        // Get all the individualList where indActivation equals to indActivationId
        defaultIndividualShouldBeFound("indActivationId.equals=" + indActivationId);

        // Get all the individualList where indActivation equals to (indActivationId + 1)
        defaultIndividualShouldNotBeFound("indActivationId.equals=" + (indActivationId + 1));
    }

    @Test
    @Transactional
    void getAllIndividualsByIndNewsLetterConfIsEqualToSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);
        IndNewsLetterConf indNewsLetterConf = IndNewsLetterConfResourceIT.createEntity(em);
        em.persist(indNewsLetterConf);
        em.flush();
        individual.setIndNewsLetterConf(indNewsLetterConf);
        individualRepository.saveAndFlush(individual);
        Long indNewsLetterConfId = indNewsLetterConf.getId();

        // Get all the individualList where indNewsLetterConf equals to indNewsLetterConfId
        defaultIndividualShouldBeFound("indNewsLetterConfId.equals=" + indNewsLetterConfId);

        // Get all the individualList where indNewsLetterConf equals to (indNewsLetterConfId + 1)
        defaultIndividualShouldNotBeFound("indNewsLetterConfId.equals=" + (indNewsLetterConfId + 1));
    }

    @Test
    @Transactional
    void getAllIndividualsByIndContactIsEqualToSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);
        IndContact indContact = IndContactResourceIT.createEntity(em);
        em.persist(indContact);
        em.flush();
        individual.addIndContact(indContact);
        individualRepository.saveAndFlush(individual);
        Long indContactId = indContact.getId();

        // Get all the individualList where indContact equals to indContactId
        defaultIndividualShouldBeFound("indContactId.equals=" + indContactId);

        // Get all the individualList where indContact equals to (indContactId + 1)
        defaultIndividualShouldNotBeFound("indContactId.equals=" + (indContactId + 1));
    }

    @Test
    @Transactional
    void getAllIndividualsByIndCharIsEqualToSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);
        IndChar indChar = IndCharResourceIT.createEntity(em);
        em.persist(indChar);
        em.flush();
        individual.addIndChar(indChar);
        individualRepository.saveAndFlush(individual);
        Long indCharId = indChar.getId();

        // Get all the individualList where indChar equals to indCharId
        defaultIndividualShouldBeFound("indCharId.equals=" + indCharId);

        // Get all the individualList where indChar equals to (indCharId + 1)
        defaultIndividualShouldNotBeFound("indCharId.equals=" + (indCharId + 1));
    }

    @Test
    @Transactional
    void getAllIndividualsByIndAuditTrialIsEqualToSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);
        IndAuditTrial indAuditTrial = IndAuditTrialResourceIT.createEntity(em);
        em.persist(indAuditTrial);
        em.flush();
        individual.addIndAuditTrial(indAuditTrial);
        individualRepository.saveAndFlush(individual);
        Long indAuditTrialId = indAuditTrial.getId();

        // Get all the individualList where indAuditTrial equals to indAuditTrialId
        defaultIndividualShouldBeFound("indAuditTrialId.equals=" + indAuditTrialId);

        // Get all the individualList where indAuditTrial equals to (indAuditTrialId + 1)
        defaultIndividualShouldNotBeFound("indAuditTrialId.equals=" + (indAuditTrialId + 1));
    }

    @Test
    @Transactional
    void getAllIndividualsByCustRelPartyIsEqualToSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);
        CustRelParty custRelParty = CustRelPartyResourceIT.createEntity(em);
        em.persist(custRelParty);
        em.flush();
        individual.addCustRelParty(custRelParty);
        individualRepository.saveAndFlush(individual);
        Long custRelPartyId = custRelParty.getId();

        // Get all the individualList where custRelParty equals to custRelPartyId
        defaultIndividualShouldBeFound("custRelPartyId.equals=" + custRelPartyId);

        // Get all the individualList where custRelParty equals to (custRelPartyId + 1)
        defaultIndividualShouldNotBeFound("custRelPartyId.equals=" + (custRelPartyId + 1));
    }

    @Test
    @Transactional
    void getAllIndividualsByShoppingSessionRefIsEqualToSomething() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);
        ShoppingSessionRef shoppingSessionRef = ShoppingSessionRefResourceIT.createEntity(em);
        em.persist(shoppingSessionRef);
        em.flush();
        individual.addShoppingSessionRef(shoppingSessionRef);
        individualRepository.saveAndFlush(individual);
        Long shoppingSessionRefId = shoppingSessionRef.getId();

        // Get all the individualList where shoppingSessionRef equals to shoppingSessionRefId
        defaultIndividualShouldBeFound("shoppingSessionRefId.equals=" + shoppingSessionRefId);

        // Get all the individualList where shoppingSessionRef equals to (shoppingSessionRefId + 1)
        defaultIndividualShouldNotBeFound("shoppingSessionRefId.equals=" + (shoppingSessionRefId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultIndividualShouldBeFound(String filter) throws Exception {
        restIndividualMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(individual.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].formattedName").value(hasItem(DEFAULT_FORMATTED_NAME)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restIndividualMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultIndividualShouldNotBeFound(String filter) throws Exception {
        restIndividualMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIndividualMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingIndividual() throws Exception {
        // Get the individual
        restIndividualMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIndividual() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        int databaseSizeBeforeUpdate = individualRepository.findAll().size();

        // Update the individual
        Individual updatedIndividual = individualRepository.findById(individual.getId()).get();
        // Disconnect from session so that the updates on updatedIndividual are not directly saved in db
        em.detach(updatedIndividual);
        updatedIndividual
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .formattedName(UPDATED_FORMATTED_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .gender(UPDATED_GENDER)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .nationality(UPDATED_NATIONALITY)
            .status(UPDATED_STATUS);
        IndividualDTO individualDTO = individualMapper.toDto(updatedIndividual);

        restIndividualMockMvc
            .perform(
                put(ENTITY_API_URL_ID, individualDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(individualDTO))
            )
            .andExpect(status().isOk());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeUpdate);
        Individual testIndividual = individualList.get(individualList.size() - 1);
        assertThat(testIndividual.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testIndividual.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testIndividual.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testIndividual.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testIndividual.getFormattedName()).isEqualTo(UPDATED_FORMATTED_NAME);
        assertThat(testIndividual.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testIndividual.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testIndividual.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testIndividual.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testIndividual.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingIndividual() throws Exception {
        int databaseSizeBeforeUpdate = individualRepository.findAll().size();
        individual.setId(count.incrementAndGet());

        // Create the Individual
        IndividualDTO individualDTO = individualMapper.toDto(individual);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndividualMockMvc
            .perform(
                put(ENTITY_API_URL_ID, individualDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(individualDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIndividual() throws Exception {
        int databaseSizeBeforeUpdate = individualRepository.findAll().size();
        individual.setId(count.incrementAndGet());

        // Create the Individual
        IndividualDTO individualDTO = individualMapper.toDto(individual);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndividualMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(individualDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIndividual() throws Exception {
        int databaseSizeBeforeUpdate = individualRepository.findAll().size();
        individual.setId(count.incrementAndGet());

        // Create the Individual
        IndividualDTO individualDTO = individualMapper.toDto(individual);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndividualMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(individualDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIndividualWithPatch() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        int databaseSizeBeforeUpdate = individualRepository.findAll().size();

        // Update the individual using partial update
        Individual partialUpdatedIndividual = new Individual();
        partialUpdatedIndividual.setId(individual.getId());

        partialUpdatedIndividual
            .firstName(UPDATED_FIRST_NAME)
            .formattedName(UPDATED_FORMATTED_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .nationality(UPDATED_NATIONALITY)
            .status(UPDATED_STATUS);

        restIndividualMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndividual.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndividual))
            )
            .andExpect(status().isOk());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeUpdate);
        Individual testIndividual = individualList.get(individualList.size() - 1);
        assertThat(testIndividual.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testIndividual.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testIndividual.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testIndividual.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testIndividual.getFormattedName()).isEqualTo(UPDATED_FORMATTED_NAME);
        assertThat(testIndividual.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testIndividual.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testIndividual.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testIndividual.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testIndividual.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateIndividualWithPatch() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        int databaseSizeBeforeUpdate = individualRepository.findAll().size();

        // Update the individual using partial update
        Individual partialUpdatedIndividual = new Individual();
        partialUpdatedIndividual.setId(individual.getId());

        partialUpdatedIndividual
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .formattedName(UPDATED_FORMATTED_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .gender(UPDATED_GENDER)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .nationality(UPDATED_NATIONALITY)
            .status(UPDATED_STATUS);

        restIndividualMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndividual.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndividual))
            )
            .andExpect(status().isOk());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeUpdate);
        Individual testIndividual = individualList.get(individualList.size() - 1);
        assertThat(testIndividual.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testIndividual.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testIndividual.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testIndividual.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testIndividual.getFormattedName()).isEqualTo(UPDATED_FORMATTED_NAME);
        assertThat(testIndividual.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testIndividual.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testIndividual.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testIndividual.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testIndividual.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingIndividual() throws Exception {
        int databaseSizeBeforeUpdate = individualRepository.findAll().size();
        individual.setId(count.incrementAndGet());

        // Create the Individual
        IndividualDTO individualDTO = individualMapper.toDto(individual);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndividualMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, individualDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(individualDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIndividual() throws Exception {
        int databaseSizeBeforeUpdate = individualRepository.findAll().size();
        individual.setId(count.incrementAndGet());

        // Create the Individual
        IndividualDTO individualDTO = individualMapper.toDto(individual);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndividualMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(individualDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIndividual() throws Exception {
        int databaseSizeBeforeUpdate = individualRepository.findAll().size();
        individual.setId(count.incrementAndGet());

        // Create the Individual
        IndividualDTO individualDTO = individualMapper.toDto(individual);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndividualMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(individualDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Individual in the database
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIndividual() throws Exception {
        // Initialize the database
        individualRepository.saveAndFlush(individual);

        int databaseSizeBeforeDelete = individualRepository.findAll().size();

        // Delete the individual
        restIndividualMockMvc
            .perform(delete(ENTITY_API_URL_ID, individual.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Individual> individualList = individualRepository.findAll();
        assertThat(individualList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
