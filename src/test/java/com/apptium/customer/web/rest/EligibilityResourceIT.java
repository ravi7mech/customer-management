package com.apptium.customer.web.rest;

import static com.apptium.customer.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.Eligibility;
import com.apptium.customer.repository.EligibilityRepository;
import com.apptium.customer.service.criteria.EligibilityCriteria;
import com.apptium.customer.service.dto.EligibilityDTO;
import com.apptium.customer.service.mapper.EligibilityMapper;
import java.math.BigDecimal;
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
 * Integration tests for the {@link EligibilityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EligibilityResourceIT {

    private static final Integer DEFAULT_NO_OF_LINES = 1;
    private static final Integer UPDATED_NO_OF_LINES = 2;
    private static final Integer SMALLER_NO_OF_LINES = 1 - 1;

    private static final BigDecimal DEFAULT_CREDIT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_CREDIT_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_CREDIT_AMOUNT = new BigDecimal(1 - 1);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ELIGIBLE_PAYLATER = false;
    private static final Boolean UPDATED_IS_ELIGIBLE_PAYLATER = true;

    private static final String ENTITY_API_URL = "/api/eligibilities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EligibilityRepository eligibilityRepository;

    @Autowired
    private EligibilityMapper eligibilityMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEligibilityMockMvc;

    private Eligibility eligibility;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eligibility createEntity(EntityManager em) {
        Eligibility eligibility = new Eligibility()
            .noOfLines(DEFAULT_NO_OF_LINES)
            .creditAmount(DEFAULT_CREDIT_AMOUNT)
            .description(DEFAULT_DESCRIPTION)
            .isEligiblePaylater(DEFAULT_IS_ELIGIBLE_PAYLATER);
        return eligibility;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eligibility createUpdatedEntity(EntityManager em) {
        Eligibility eligibility = new Eligibility()
            .noOfLines(UPDATED_NO_OF_LINES)
            .creditAmount(UPDATED_CREDIT_AMOUNT)
            .description(UPDATED_DESCRIPTION)
            .isEligiblePaylater(UPDATED_IS_ELIGIBLE_PAYLATER);
        return eligibility;
    }

    @BeforeEach
    public void initTest() {
        eligibility = createEntity(em);
    }

    @Test
    @Transactional
    void createEligibility() throws Exception {
        int databaseSizeBeforeCreate = eligibilityRepository.findAll().size();
        // Create the Eligibility
        EligibilityDTO eligibilityDTO = eligibilityMapper.toDto(eligibility);
        restEligibilityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eligibilityDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Eligibility in the database
        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeCreate + 1);
        Eligibility testEligibility = eligibilityList.get(eligibilityList.size() - 1);
        assertThat(testEligibility.getNoOfLines()).isEqualTo(DEFAULT_NO_OF_LINES);
        assertThat(testEligibility.getCreditAmount()).isEqualByComparingTo(DEFAULT_CREDIT_AMOUNT);
        assertThat(testEligibility.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEligibility.getIsEligiblePaylater()).isEqualTo(DEFAULT_IS_ELIGIBLE_PAYLATER);
    }

    @Test
    @Transactional
    void createEligibilityWithExistingId() throws Exception {
        // Create the Eligibility with an existing ID
        eligibility.setId(1L);
        EligibilityDTO eligibilityDTO = eligibilityMapper.toDto(eligibility);

        int databaseSizeBeforeCreate = eligibilityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEligibilityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eligibilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eligibility in the database
        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNoOfLinesIsRequired() throws Exception {
        int databaseSizeBeforeTest = eligibilityRepository.findAll().size();
        // set the field null
        eligibility.setNoOfLines(null);

        // Create the Eligibility, which fails.
        EligibilityDTO eligibilityDTO = eligibilityMapper.toDto(eligibility);

        restEligibilityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eligibilityDTO))
            )
            .andExpect(status().isBadRequest());

        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreditAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = eligibilityRepository.findAll().size();
        // set the field null
        eligibility.setCreditAmount(null);

        // Create the Eligibility, which fails.
        EligibilityDTO eligibilityDTO = eligibilityMapper.toDto(eligibility);

        restEligibilityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eligibilityDTO))
            )
            .andExpect(status().isBadRequest());

        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsEligiblePaylaterIsRequired() throws Exception {
        int databaseSizeBeforeTest = eligibilityRepository.findAll().size();
        // set the field null
        eligibility.setIsEligiblePaylater(null);

        // Create the Eligibility, which fails.
        EligibilityDTO eligibilityDTO = eligibilityMapper.toDto(eligibility);

        restEligibilityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eligibilityDTO))
            )
            .andExpect(status().isBadRequest());

        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEligibilities() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList
        restEligibilityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eligibility.getId().intValue())))
            .andExpect(jsonPath("$.[*].noOfLines").value(hasItem(DEFAULT_NO_OF_LINES)))
            .andExpect(jsonPath("$.[*].creditAmount").value(hasItem(sameNumber(DEFAULT_CREDIT_AMOUNT))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isEligiblePaylater").value(hasItem(DEFAULT_IS_ELIGIBLE_PAYLATER.booleanValue())));
    }

    @Test
    @Transactional
    void getEligibility() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get the eligibility
        restEligibilityMockMvc
            .perform(get(ENTITY_API_URL_ID, eligibility.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eligibility.getId().intValue()))
            .andExpect(jsonPath("$.noOfLines").value(DEFAULT_NO_OF_LINES))
            .andExpect(jsonPath("$.creditAmount").value(sameNumber(DEFAULT_CREDIT_AMOUNT)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isEligiblePaylater").value(DEFAULT_IS_ELIGIBLE_PAYLATER.booleanValue()));
    }

    @Test
    @Transactional
    void getEligibilitiesByIdFiltering() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        Long id = eligibility.getId();

        defaultEligibilityShouldBeFound("id.equals=" + id);
        defaultEligibilityShouldNotBeFound("id.notEquals=" + id);

        defaultEligibilityShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEligibilityShouldNotBeFound("id.greaterThan=" + id);

        defaultEligibilityShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEligibilityShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEligibilitiesByNoOfLinesIsEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where noOfLines equals to DEFAULT_NO_OF_LINES
        defaultEligibilityShouldBeFound("noOfLines.equals=" + DEFAULT_NO_OF_LINES);

        // Get all the eligibilityList where noOfLines equals to UPDATED_NO_OF_LINES
        defaultEligibilityShouldNotBeFound("noOfLines.equals=" + UPDATED_NO_OF_LINES);
    }

    @Test
    @Transactional
    void getAllEligibilitiesByNoOfLinesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where noOfLines not equals to DEFAULT_NO_OF_LINES
        defaultEligibilityShouldNotBeFound("noOfLines.notEquals=" + DEFAULT_NO_OF_LINES);

        // Get all the eligibilityList where noOfLines not equals to UPDATED_NO_OF_LINES
        defaultEligibilityShouldBeFound("noOfLines.notEquals=" + UPDATED_NO_OF_LINES);
    }

    @Test
    @Transactional
    void getAllEligibilitiesByNoOfLinesIsInShouldWork() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where noOfLines in DEFAULT_NO_OF_LINES or UPDATED_NO_OF_LINES
        defaultEligibilityShouldBeFound("noOfLines.in=" + DEFAULT_NO_OF_LINES + "," + UPDATED_NO_OF_LINES);

        // Get all the eligibilityList where noOfLines equals to UPDATED_NO_OF_LINES
        defaultEligibilityShouldNotBeFound("noOfLines.in=" + UPDATED_NO_OF_LINES);
    }

    @Test
    @Transactional
    void getAllEligibilitiesByNoOfLinesIsNullOrNotNull() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where noOfLines is not null
        defaultEligibilityShouldBeFound("noOfLines.specified=true");

        // Get all the eligibilityList where noOfLines is null
        defaultEligibilityShouldNotBeFound("noOfLines.specified=false");
    }

    @Test
    @Transactional
    void getAllEligibilitiesByNoOfLinesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where noOfLines is greater than or equal to DEFAULT_NO_OF_LINES
        defaultEligibilityShouldBeFound("noOfLines.greaterThanOrEqual=" + DEFAULT_NO_OF_LINES);

        // Get all the eligibilityList where noOfLines is greater than or equal to UPDATED_NO_OF_LINES
        defaultEligibilityShouldNotBeFound("noOfLines.greaterThanOrEqual=" + UPDATED_NO_OF_LINES);
    }

    @Test
    @Transactional
    void getAllEligibilitiesByNoOfLinesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where noOfLines is less than or equal to DEFAULT_NO_OF_LINES
        defaultEligibilityShouldBeFound("noOfLines.lessThanOrEqual=" + DEFAULT_NO_OF_LINES);

        // Get all the eligibilityList where noOfLines is less than or equal to SMALLER_NO_OF_LINES
        defaultEligibilityShouldNotBeFound("noOfLines.lessThanOrEqual=" + SMALLER_NO_OF_LINES);
    }

    @Test
    @Transactional
    void getAllEligibilitiesByNoOfLinesIsLessThanSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where noOfLines is less than DEFAULT_NO_OF_LINES
        defaultEligibilityShouldNotBeFound("noOfLines.lessThan=" + DEFAULT_NO_OF_LINES);

        // Get all the eligibilityList where noOfLines is less than UPDATED_NO_OF_LINES
        defaultEligibilityShouldBeFound("noOfLines.lessThan=" + UPDATED_NO_OF_LINES);
    }

    @Test
    @Transactional
    void getAllEligibilitiesByNoOfLinesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where noOfLines is greater than DEFAULT_NO_OF_LINES
        defaultEligibilityShouldNotBeFound("noOfLines.greaterThan=" + DEFAULT_NO_OF_LINES);

        // Get all the eligibilityList where noOfLines is greater than SMALLER_NO_OF_LINES
        defaultEligibilityShouldBeFound("noOfLines.greaterThan=" + SMALLER_NO_OF_LINES);
    }

    @Test
    @Transactional
    void getAllEligibilitiesByCreditAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where creditAmount equals to DEFAULT_CREDIT_AMOUNT
        defaultEligibilityShouldBeFound("creditAmount.equals=" + DEFAULT_CREDIT_AMOUNT);

        // Get all the eligibilityList where creditAmount equals to UPDATED_CREDIT_AMOUNT
        defaultEligibilityShouldNotBeFound("creditAmount.equals=" + UPDATED_CREDIT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEligibilitiesByCreditAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where creditAmount not equals to DEFAULT_CREDIT_AMOUNT
        defaultEligibilityShouldNotBeFound("creditAmount.notEquals=" + DEFAULT_CREDIT_AMOUNT);

        // Get all the eligibilityList where creditAmount not equals to UPDATED_CREDIT_AMOUNT
        defaultEligibilityShouldBeFound("creditAmount.notEquals=" + UPDATED_CREDIT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEligibilitiesByCreditAmountIsInShouldWork() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where creditAmount in DEFAULT_CREDIT_AMOUNT or UPDATED_CREDIT_AMOUNT
        defaultEligibilityShouldBeFound("creditAmount.in=" + DEFAULT_CREDIT_AMOUNT + "," + UPDATED_CREDIT_AMOUNT);

        // Get all the eligibilityList where creditAmount equals to UPDATED_CREDIT_AMOUNT
        defaultEligibilityShouldNotBeFound("creditAmount.in=" + UPDATED_CREDIT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEligibilitiesByCreditAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where creditAmount is not null
        defaultEligibilityShouldBeFound("creditAmount.specified=true");

        // Get all the eligibilityList where creditAmount is null
        defaultEligibilityShouldNotBeFound("creditAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllEligibilitiesByCreditAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where creditAmount is greater than or equal to DEFAULT_CREDIT_AMOUNT
        defaultEligibilityShouldBeFound("creditAmount.greaterThanOrEqual=" + DEFAULT_CREDIT_AMOUNT);

        // Get all the eligibilityList where creditAmount is greater than or equal to UPDATED_CREDIT_AMOUNT
        defaultEligibilityShouldNotBeFound("creditAmount.greaterThanOrEqual=" + UPDATED_CREDIT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEligibilitiesByCreditAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where creditAmount is less than or equal to DEFAULT_CREDIT_AMOUNT
        defaultEligibilityShouldBeFound("creditAmount.lessThanOrEqual=" + DEFAULT_CREDIT_AMOUNT);

        // Get all the eligibilityList where creditAmount is less than or equal to SMALLER_CREDIT_AMOUNT
        defaultEligibilityShouldNotBeFound("creditAmount.lessThanOrEqual=" + SMALLER_CREDIT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEligibilitiesByCreditAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where creditAmount is less than DEFAULT_CREDIT_AMOUNT
        defaultEligibilityShouldNotBeFound("creditAmount.lessThan=" + DEFAULT_CREDIT_AMOUNT);

        // Get all the eligibilityList where creditAmount is less than UPDATED_CREDIT_AMOUNT
        defaultEligibilityShouldBeFound("creditAmount.lessThan=" + UPDATED_CREDIT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEligibilitiesByCreditAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where creditAmount is greater than DEFAULT_CREDIT_AMOUNT
        defaultEligibilityShouldNotBeFound("creditAmount.greaterThan=" + DEFAULT_CREDIT_AMOUNT);

        // Get all the eligibilityList where creditAmount is greater than SMALLER_CREDIT_AMOUNT
        defaultEligibilityShouldBeFound("creditAmount.greaterThan=" + SMALLER_CREDIT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEligibilitiesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where description equals to DEFAULT_DESCRIPTION
        defaultEligibilityShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the eligibilityList where description equals to UPDATED_DESCRIPTION
        defaultEligibilityShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEligibilitiesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where description not equals to DEFAULT_DESCRIPTION
        defaultEligibilityShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the eligibilityList where description not equals to UPDATED_DESCRIPTION
        defaultEligibilityShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEligibilitiesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultEligibilityShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the eligibilityList where description equals to UPDATED_DESCRIPTION
        defaultEligibilityShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEligibilitiesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where description is not null
        defaultEligibilityShouldBeFound("description.specified=true");

        // Get all the eligibilityList where description is null
        defaultEligibilityShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllEligibilitiesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where description contains DEFAULT_DESCRIPTION
        defaultEligibilityShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the eligibilityList where description contains UPDATED_DESCRIPTION
        defaultEligibilityShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEligibilitiesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where description does not contain DEFAULT_DESCRIPTION
        defaultEligibilityShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the eligibilityList where description does not contain UPDATED_DESCRIPTION
        defaultEligibilityShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEligibilitiesByIsEligiblePaylaterIsEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where isEligiblePaylater equals to DEFAULT_IS_ELIGIBLE_PAYLATER
        defaultEligibilityShouldBeFound("isEligiblePaylater.equals=" + DEFAULT_IS_ELIGIBLE_PAYLATER);

        // Get all the eligibilityList where isEligiblePaylater equals to UPDATED_IS_ELIGIBLE_PAYLATER
        defaultEligibilityShouldNotBeFound("isEligiblePaylater.equals=" + UPDATED_IS_ELIGIBLE_PAYLATER);
    }

    @Test
    @Transactional
    void getAllEligibilitiesByIsEligiblePaylaterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where isEligiblePaylater not equals to DEFAULT_IS_ELIGIBLE_PAYLATER
        defaultEligibilityShouldNotBeFound("isEligiblePaylater.notEquals=" + DEFAULT_IS_ELIGIBLE_PAYLATER);

        // Get all the eligibilityList where isEligiblePaylater not equals to UPDATED_IS_ELIGIBLE_PAYLATER
        defaultEligibilityShouldBeFound("isEligiblePaylater.notEquals=" + UPDATED_IS_ELIGIBLE_PAYLATER);
    }

    @Test
    @Transactional
    void getAllEligibilitiesByIsEligiblePaylaterIsInShouldWork() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where isEligiblePaylater in DEFAULT_IS_ELIGIBLE_PAYLATER or UPDATED_IS_ELIGIBLE_PAYLATER
        defaultEligibilityShouldBeFound("isEligiblePaylater.in=" + DEFAULT_IS_ELIGIBLE_PAYLATER + "," + UPDATED_IS_ELIGIBLE_PAYLATER);

        // Get all the eligibilityList where isEligiblePaylater equals to UPDATED_IS_ELIGIBLE_PAYLATER
        defaultEligibilityShouldNotBeFound("isEligiblePaylater.in=" + UPDATED_IS_ELIGIBLE_PAYLATER);
    }

    @Test
    @Transactional
    void getAllEligibilitiesByIsEligiblePaylaterIsNullOrNotNull() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        // Get all the eligibilityList where isEligiblePaylater is not null
        defaultEligibilityShouldBeFound("isEligiblePaylater.specified=true");

        // Get all the eligibilityList where isEligiblePaylater is null
        defaultEligibilityShouldNotBeFound("isEligiblePaylater.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEligibilityShouldBeFound(String filter) throws Exception {
        restEligibilityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eligibility.getId().intValue())))
            .andExpect(jsonPath("$.[*].noOfLines").value(hasItem(DEFAULT_NO_OF_LINES)))
            .andExpect(jsonPath("$.[*].creditAmount").value(hasItem(sameNumber(DEFAULT_CREDIT_AMOUNT))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isEligiblePaylater").value(hasItem(DEFAULT_IS_ELIGIBLE_PAYLATER.booleanValue())));

        // Check, that the count call also returns 1
        restEligibilityMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEligibilityShouldNotBeFound(String filter) throws Exception {
        restEligibilityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEligibilityMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEligibility() throws Exception {
        // Get the eligibility
        restEligibilityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEligibility() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        int databaseSizeBeforeUpdate = eligibilityRepository.findAll().size();

        // Update the eligibility
        Eligibility updatedEligibility = eligibilityRepository.findById(eligibility.getId()).get();
        // Disconnect from session so that the updates on updatedEligibility are not directly saved in db
        em.detach(updatedEligibility);
        updatedEligibility
            .noOfLines(UPDATED_NO_OF_LINES)
            .creditAmount(UPDATED_CREDIT_AMOUNT)
            .description(UPDATED_DESCRIPTION)
            .isEligiblePaylater(UPDATED_IS_ELIGIBLE_PAYLATER);
        EligibilityDTO eligibilityDTO = eligibilityMapper.toDto(updatedEligibility);

        restEligibilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eligibilityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eligibilityDTO))
            )
            .andExpect(status().isOk());

        // Validate the Eligibility in the database
        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeUpdate);
        Eligibility testEligibility = eligibilityList.get(eligibilityList.size() - 1);
        assertThat(testEligibility.getNoOfLines()).isEqualTo(UPDATED_NO_OF_LINES);
        assertThat(testEligibility.getCreditAmount()).isEqualTo(UPDATED_CREDIT_AMOUNT);
        assertThat(testEligibility.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEligibility.getIsEligiblePaylater()).isEqualTo(UPDATED_IS_ELIGIBLE_PAYLATER);
    }

    @Test
    @Transactional
    void putNonExistingEligibility() throws Exception {
        int databaseSizeBeforeUpdate = eligibilityRepository.findAll().size();
        eligibility.setId(count.incrementAndGet());

        // Create the Eligibility
        EligibilityDTO eligibilityDTO = eligibilityMapper.toDto(eligibility);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEligibilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eligibilityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eligibilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eligibility in the database
        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEligibility() throws Exception {
        int databaseSizeBeforeUpdate = eligibilityRepository.findAll().size();
        eligibility.setId(count.incrementAndGet());

        // Create the Eligibility
        EligibilityDTO eligibilityDTO = eligibilityMapper.toDto(eligibility);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEligibilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eligibilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eligibility in the database
        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEligibility() throws Exception {
        int databaseSizeBeforeUpdate = eligibilityRepository.findAll().size();
        eligibility.setId(count.incrementAndGet());

        // Create the Eligibility
        EligibilityDTO eligibilityDTO = eligibilityMapper.toDto(eligibility);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEligibilityMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eligibilityDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Eligibility in the database
        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEligibilityWithPatch() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        int databaseSizeBeforeUpdate = eligibilityRepository.findAll().size();

        // Update the eligibility using partial update
        Eligibility partialUpdatedEligibility = new Eligibility();
        partialUpdatedEligibility.setId(eligibility.getId());

        partialUpdatedEligibility.creditAmount(UPDATED_CREDIT_AMOUNT);

        restEligibilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEligibility.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEligibility))
            )
            .andExpect(status().isOk());

        // Validate the Eligibility in the database
        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeUpdate);
        Eligibility testEligibility = eligibilityList.get(eligibilityList.size() - 1);
        assertThat(testEligibility.getNoOfLines()).isEqualTo(DEFAULT_NO_OF_LINES);
        assertThat(testEligibility.getCreditAmount()).isEqualByComparingTo(UPDATED_CREDIT_AMOUNT);
        assertThat(testEligibility.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEligibility.getIsEligiblePaylater()).isEqualTo(DEFAULT_IS_ELIGIBLE_PAYLATER);
    }

    @Test
    @Transactional
    void fullUpdateEligibilityWithPatch() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        int databaseSizeBeforeUpdate = eligibilityRepository.findAll().size();

        // Update the eligibility using partial update
        Eligibility partialUpdatedEligibility = new Eligibility();
        partialUpdatedEligibility.setId(eligibility.getId());

        partialUpdatedEligibility
            .noOfLines(UPDATED_NO_OF_LINES)
            .creditAmount(UPDATED_CREDIT_AMOUNT)
            .description(UPDATED_DESCRIPTION)
            .isEligiblePaylater(UPDATED_IS_ELIGIBLE_PAYLATER);

        restEligibilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEligibility.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEligibility))
            )
            .andExpect(status().isOk());

        // Validate the Eligibility in the database
        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeUpdate);
        Eligibility testEligibility = eligibilityList.get(eligibilityList.size() - 1);
        assertThat(testEligibility.getNoOfLines()).isEqualTo(UPDATED_NO_OF_LINES);
        assertThat(testEligibility.getCreditAmount()).isEqualByComparingTo(UPDATED_CREDIT_AMOUNT);
        assertThat(testEligibility.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEligibility.getIsEligiblePaylater()).isEqualTo(UPDATED_IS_ELIGIBLE_PAYLATER);
    }

    @Test
    @Transactional
    void patchNonExistingEligibility() throws Exception {
        int databaseSizeBeforeUpdate = eligibilityRepository.findAll().size();
        eligibility.setId(count.incrementAndGet());

        // Create the Eligibility
        EligibilityDTO eligibilityDTO = eligibilityMapper.toDto(eligibility);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEligibilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eligibilityDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eligibilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eligibility in the database
        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEligibility() throws Exception {
        int databaseSizeBeforeUpdate = eligibilityRepository.findAll().size();
        eligibility.setId(count.incrementAndGet());

        // Create the Eligibility
        EligibilityDTO eligibilityDTO = eligibilityMapper.toDto(eligibility);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEligibilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eligibilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eligibility in the database
        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEligibility() throws Exception {
        int databaseSizeBeforeUpdate = eligibilityRepository.findAll().size();
        eligibility.setId(count.incrementAndGet());

        // Create the Eligibility
        EligibilityDTO eligibilityDTO = eligibilityMapper.toDto(eligibility);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEligibilityMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(eligibilityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Eligibility in the database
        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEligibility() throws Exception {
        // Initialize the database
        eligibilityRepository.saveAndFlush(eligibility);

        int databaseSizeBeforeDelete = eligibilityRepository.findAll().size();

        // Delete the eligibility
        restEligibilityMockMvc
            .perform(delete(ENTITY_API_URL_ID, eligibility.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Eligibility> eligibilityList = eligibilityRepository.findAll();
        assertThat(eligibilityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
