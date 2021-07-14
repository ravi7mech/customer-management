package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.CustCreditProfile;
import com.apptium.customer.domain.Customer;
import com.apptium.customer.repository.CustCreditProfileRepository;
import com.apptium.customer.service.criteria.CustCreditProfileCriteria;
import com.apptium.customer.service.dto.CustCreditProfileDTO;
import com.apptium.customer.service.mapper.CustCreditProfileMapper;
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
 * Integration tests for the {@link CustCreditProfileResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustCreditProfileResourceIT {

    private static final String DEFAULT_CUST_ID_TYPE_ONE = "AAAAAAAAAA";
    private static final String UPDATED_CUST_ID_TYPE_ONE = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_ID_REF_ONE = "AAAAAAAAAA";
    private static final String UPDATED_CUST_ID_REF_ONE = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_ID_TYPE_TWO = "AAAAAAAAAA";
    private static final String UPDATED_CUST_ID_TYPE_TWO = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_ID_REF_TWO = "AAAAAAAAAA";
    private static final String UPDATED_CUST_ID_REF_TWO = "BBBBBBBBBB";

    private static final Long DEFAULT_CREDIT_CARD_NUMBER = 1L;
    private static final Long UPDATED_CREDIT_CARD_NUMBER = 2L;
    private static final Long SMALLER_CREDIT_CARD_NUMBER = 1L - 1L;

    private static final Instant DEFAULT_CREDIT_PROFILE_DATA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREDIT_PROFILE_DATA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREDIT_RISK_RATING = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_RISK_RATING = "BBBBBBBBBB";

    private static final String DEFAULT_CREDIT_RISK_RATING_DESC = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_RISK_RATING_DESC = "BBBBBBBBBB";

    private static final Integer DEFAULT_CREDIT_SCORE = 1;
    private static final Integer UPDATED_CREDIT_SCORE = 2;
    private static final Integer SMALLER_CREDIT_SCORE = 1 - 1;

    private static final Instant DEFAULT_VALID_UNTIL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_UNTIL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;
    private static final Long SMALLER_CUSTOMER_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/cust-credit-profiles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustCreditProfileRepository custCreditProfileRepository;

    @Autowired
    private CustCreditProfileMapper custCreditProfileMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustCreditProfileMockMvc;

    private CustCreditProfile custCreditProfile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustCreditProfile createEntity(EntityManager em) {
        CustCreditProfile custCreditProfile = new CustCreditProfile()
            .custIdTypeOne(DEFAULT_CUST_ID_TYPE_ONE)
            .custIdRefOne(DEFAULT_CUST_ID_REF_ONE)
            .custIdTypeTwo(DEFAULT_CUST_ID_TYPE_TWO)
            .custIdRefTwo(DEFAULT_CUST_ID_REF_TWO)
            .creditCardNumber(DEFAULT_CREDIT_CARD_NUMBER)
            .creditProfileData(DEFAULT_CREDIT_PROFILE_DATA)
            .creditRiskRating(DEFAULT_CREDIT_RISK_RATING)
            .creditRiskRatingDesc(DEFAULT_CREDIT_RISK_RATING_DESC)
            .creditScore(DEFAULT_CREDIT_SCORE)
            .validUntil(DEFAULT_VALID_UNTIL)
            .customerId(DEFAULT_CUSTOMER_ID);
        return custCreditProfile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustCreditProfile createUpdatedEntity(EntityManager em) {
        CustCreditProfile custCreditProfile = new CustCreditProfile()
            .custIdTypeOne(UPDATED_CUST_ID_TYPE_ONE)
            .custIdRefOne(UPDATED_CUST_ID_REF_ONE)
            .custIdTypeTwo(UPDATED_CUST_ID_TYPE_TWO)
            .custIdRefTwo(UPDATED_CUST_ID_REF_TWO)
            .creditCardNumber(UPDATED_CREDIT_CARD_NUMBER)
            .creditProfileData(UPDATED_CREDIT_PROFILE_DATA)
            .creditRiskRating(UPDATED_CREDIT_RISK_RATING)
            .creditRiskRatingDesc(UPDATED_CREDIT_RISK_RATING_DESC)
            .creditScore(UPDATED_CREDIT_SCORE)
            .validUntil(UPDATED_VALID_UNTIL)
            .customerId(UPDATED_CUSTOMER_ID);
        return custCreditProfile;
    }

    @BeforeEach
    public void initTest() {
        custCreditProfile = createEntity(em);
    }

    @Test
    @Transactional
    void createCustCreditProfile() throws Exception {
        int databaseSizeBeforeCreate = custCreditProfileRepository.findAll().size();
        // Create the CustCreditProfile
        CustCreditProfileDTO custCreditProfileDTO = custCreditProfileMapper.toDto(custCreditProfile);
        restCustCreditProfileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCreditProfileDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CustCreditProfile in the database
        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeCreate + 1);
        CustCreditProfile testCustCreditProfile = custCreditProfileList.get(custCreditProfileList.size() - 1);
        assertThat(testCustCreditProfile.getCustIdTypeOne()).isEqualTo(DEFAULT_CUST_ID_TYPE_ONE);
        assertThat(testCustCreditProfile.getCustIdRefOne()).isEqualTo(DEFAULT_CUST_ID_REF_ONE);
        assertThat(testCustCreditProfile.getCustIdTypeTwo()).isEqualTo(DEFAULT_CUST_ID_TYPE_TWO);
        assertThat(testCustCreditProfile.getCustIdRefTwo()).isEqualTo(DEFAULT_CUST_ID_REF_TWO);
        assertThat(testCustCreditProfile.getCreditCardNumber()).isEqualTo(DEFAULT_CREDIT_CARD_NUMBER);
        assertThat(testCustCreditProfile.getCreditProfileData()).isEqualTo(DEFAULT_CREDIT_PROFILE_DATA);
        assertThat(testCustCreditProfile.getCreditRiskRating()).isEqualTo(DEFAULT_CREDIT_RISK_RATING);
        assertThat(testCustCreditProfile.getCreditRiskRatingDesc()).isEqualTo(DEFAULT_CREDIT_RISK_RATING_DESC);
        assertThat(testCustCreditProfile.getCreditScore()).isEqualTo(DEFAULT_CREDIT_SCORE);
        assertThat(testCustCreditProfile.getValidUntil()).isEqualTo(DEFAULT_VALID_UNTIL);
        assertThat(testCustCreditProfile.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void createCustCreditProfileWithExistingId() throws Exception {
        // Create the CustCreditProfile with an existing ID
        custCreditProfile.setId(1L);
        CustCreditProfileDTO custCreditProfileDTO = custCreditProfileMapper.toDto(custCreditProfile);

        int databaseSizeBeforeCreate = custCreditProfileRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustCreditProfileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCreditProfileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustCreditProfile in the database
        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCustIdTypeOneIsRequired() throws Exception {
        int databaseSizeBeforeTest = custCreditProfileRepository.findAll().size();
        // set the field null
        custCreditProfile.setCustIdTypeOne(null);

        // Create the CustCreditProfile, which fails.
        CustCreditProfileDTO custCreditProfileDTO = custCreditProfileMapper.toDto(custCreditProfile);

        restCustCreditProfileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCreditProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustIdRefOneIsRequired() throws Exception {
        int databaseSizeBeforeTest = custCreditProfileRepository.findAll().size();
        // set the field null
        custCreditProfile.setCustIdRefOne(null);

        // Create the CustCreditProfile, which fails.
        CustCreditProfileDTO custCreditProfileDTO = custCreditProfileMapper.toDto(custCreditProfile);

        restCustCreditProfileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCreditProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustIdTypeTwoIsRequired() throws Exception {
        int databaseSizeBeforeTest = custCreditProfileRepository.findAll().size();
        // set the field null
        custCreditProfile.setCustIdTypeTwo(null);

        // Create the CustCreditProfile, which fails.
        CustCreditProfileDTO custCreditProfileDTO = custCreditProfileMapper.toDto(custCreditProfile);

        restCustCreditProfileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCreditProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustIdRefTwoIsRequired() throws Exception {
        int databaseSizeBeforeTest = custCreditProfileRepository.findAll().size();
        // set the field null
        custCreditProfile.setCustIdRefTwo(null);

        // Create the CustCreditProfile, which fails.
        CustCreditProfileDTO custCreditProfileDTO = custCreditProfileMapper.toDto(custCreditProfile);

        restCustCreditProfileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCreditProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreditCardNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = custCreditProfileRepository.findAll().size();
        // set the field null
        custCreditProfile.setCreditCardNumber(null);

        // Create the CustCreditProfile, which fails.
        CustCreditProfileDTO custCreditProfileDTO = custCreditProfileMapper.toDto(custCreditProfile);

        restCustCreditProfileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCreditProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreditProfileDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = custCreditProfileRepository.findAll().size();
        // set the field null
        custCreditProfile.setCreditProfileData(null);

        // Create the CustCreditProfile, which fails.
        CustCreditProfileDTO custCreditProfileDTO = custCreditProfileMapper.toDto(custCreditProfile);

        restCustCreditProfileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCreditProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreditRiskRatingIsRequired() throws Exception {
        int databaseSizeBeforeTest = custCreditProfileRepository.findAll().size();
        // set the field null
        custCreditProfile.setCreditRiskRating(null);

        // Create the CustCreditProfile, which fails.
        CustCreditProfileDTO custCreditProfileDTO = custCreditProfileMapper.toDto(custCreditProfile);

        restCustCreditProfileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCreditProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreditScoreIsRequired() throws Exception {
        int databaseSizeBeforeTest = custCreditProfileRepository.findAll().size();
        // set the field null
        custCreditProfile.setCreditScore(null);

        // Create the CustCreditProfile, which fails.
        CustCreditProfileDTO custCreditProfileDTO = custCreditProfileMapper.toDto(custCreditProfile);

        restCustCreditProfileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCreditProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValidUntilIsRequired() throws Exception {
        int databaseSizeBeforeTest = custCreditProfileRepository.findAll().size();
        // set the field null
        custCreditProfile.setValidUntil(null);

        // Create the CustCreditProfile, which fails.
        CustCreditProfileDTO custCreditProfileDTO = custCreditProfileMapper.toDto(custCreditProfile);

        restCustCreditProfileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCreditProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custCreditProfileRepository.findAll().size();
        // set the field null
        custCreditProfile.setCustomerId(null);

        // Create the CustCreditProfile, which fails.
        CustCreditProfileDTO custCreditProfileDTO = custCreditProfileMapper.toDto(custCreditProfile);

        restCustCreditProfileMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCreditProfileDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustCreditProfiles() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList
        restCustCreditProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custCreditProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].custIdTypeOne").value(hasItem(DEFAULT_CUST_ID_TYPE_ONE)))
            .andExpect(jsonPath("$.[*].custIdRefOne").value(hasItem(DEFAULT_CUST_ID_REF_ONE)))
            .andExpect(jsonPath("$.[*].custIdTypeTwo").value(hasItem(DEFAULT_CUST_ID_TYPE_TWO)))
            .andExpect(jsonPath("$.[*].custIdRefTwo").value(hasItem(DEFAULT_CUST_ID_REF_TWO)))
            .andExpect(jsonPath("$.[*].creditCardNumber").value(hasItem(DEFAULT_CREDIT_CARD_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].creditProfileData").value(hasItem(DEFAULT_CREDIT_PROFILE_DATA.toString())))
            .andExpect(jsonPath("$.[*].creditRiskRating").value(hasItem(DEFAULT_CREDIT_RISK_RATING)))
            .andExpect(jsonPath("$.[*].creditRiskRatingDesc").value(hasItem(DEFAULT_CREDIT_RISK_RATING_DESC)))
            .andExpect(jsonPath("$.[*].creditScore").value(hasItem(DEFAULT_CREDIT_SCORE)))
            .andExpect(jsonPath("$.[*].validUntil").value(hasItem(DEFAULT_VALID_UNTIL.toString())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())));
    }

    @Test
    @Transactional
    void getCustCreditProfile() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get the custCreditProfile
        restCustCreditProfileMockMvc
            .perform(get(ENTITY_API_URL_ID, custCreditProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custCreditProfile.getId().intValue()))
            .andExpect(jsonPath("$.custIdTypeOne").value(DEFAULT_CUST_ID_TYPE_ONE))
            .andExpect(jsonPath("$.custIdRefOne").value(DEFAULT_CUST_ID_REF_ONE))
            .andExpect(jsonPath("$.custIdTypeTwo").value(DEFAULT_CUST_ID_TYPE_TWO))
            .andExpect(jsonPath("$.custIdRefTwo").value(DEFAULT_CUST_ID_REF_TWO))
            .andExpect(jsonPath("$.creditCardNumber").value(DEFAULT_CREDIT_CARD_NUMBER.intValue()))
            .andExpect(jsonPath("$.creditProfileData").value(DEFAULT_CREDIT_PROFILE_DATA.toString()))
            .andExpect(jsonPath("$.creditRiskRating").value(DEFAULT_CREDIT_RISK_RATING))
            .andExpect(jsonPath("$.creditRiskRatingDesc").value(DEFAULT_CREDIT_RISK_RATING_DESC))
            .andExpect(jsonPath("$.creditScore").value(DEFAULT_CREDIT_SCORE))
            .andExpect(jsonPath("$.validUntil").value(DEFAULT_VALID_UNTIL.toString()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()));
    }

    @Test
    @Transactional
    void getCustCreditProfilesByIdFiltering() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        Long id = custCreditProfile.getId();

        defaultCustCreditProfileShouldBeFound("id.equals=" + id);
        defaultCustCreditProfileShouldNotBeFound("id.notEquals=" + id);

        defaultCustCreditProfileShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustCreditProfileShouldNotBeFound("id.greaterThan=" + id);

        defaultCustCreditProfileShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustCreditProfileShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustIdTypeOneIsEqualToSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where custIdTypeOne equals to DEFAULT_CUST_ID_TYPE_ONE
        defaultCustCreditProfileShouldBeFound("custIdTypeOne.equals=" + DEFAULT_CUST_ID_TYPE_ONE);

        // Get all the custCreditProfileList where custIdTypeOne equals to UPDATED_CUST_ID_TYPE_ONE
        defaultCustCreditProfileShouldNotBeFound("custIdTypeOne.equals=" + UPDATED_CUST_ID_TYPE_ONE);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustIdTypeOneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where custIdTypeOne not equals to DEFAULT_CUST_ID_TYPE_ONE
        defaultCustCreditProfileShouldNotBeFound("custIdTypeOne.notEquals=" + DEFAULT_CUST_ID_TYPE_ONE);

        // Get all the custCreditProfileList where custIdTypeOne not equals to UPDATED_CUST_ID_TYPE_ONE
        defaultCustCreditProfileShouldBeFound("custIdTypeOne.notEquals=" + UPDATED_CUST_ID_TYPE_ONE);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustIdTypeOneIsInShouldWork() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where custIdTypeOne in DEFAULT_CUST_ID_TYPE_ONE or UPDATED_CUST_ID_TYPE_ONE
        defaultCustCreditProfileShouldBeFound("custIdTypeOne.in=" + DEFAULT_CUST_ID_TYPE_ONE + "," + UPDATED_CUST_ID_TYPE_ONE);

        // Get all the custCreditProfileList where custIdTypeOne equals to UPDATED_CUST_ID_TYPE_ONE
        defaultCustCreditProfileShouldNotBeFound("custIdTypeOne.in=" + UPDATED_CUST_ID_TYPE_ONE);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustIdTypeOneIsNullOrNotNull() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where custIdTypeOne is not null
        defaultCustCreditProfileShouldBeFound("custIdTypeOne.specified=true");

        // Get all the custCreditProfileList where custIdTypeOne is null
        defaultCustCreditProfileShouldNotBeFound("custIdTypeOne.specified=false");
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustIdTypeOneContainsSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where custIdTypeOne contains DEFAULT_CUST_ID_TYPE_ONE
        defaultCustCreditProfileShouldBeFound("custIdTypeOne.contains=" + DEFAULT_CUST_ID_TYPE_ONE);

        // Get all the custCreditProfileList where custIdTypeOne contains UPDATED_CUST_ID_TYPE_ONE
        defaultCustCreditProfileShouldNotBeFound("custIdTypeOne.contains=" + UPDATED_CUST_ID_TYPE_ONE);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustIdTypeOneNotContainsSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where custIdTypeOne does not contain DEFAULT_CUST_ID_TYPE_ONE
        defaultCustCreditProfileShouldNotBeFound("custIdTypeOne.doesNotContain=" + DEFAULT_CUST_ID_TYPE_ONE);

        // Get all the custCreditProfileList where custIdTypeOne does not contain UPDATED_CUST_ID_TYPE_ONE
        defaultCustCreditProfileShouldBeFound("custIdTypeOne.doesNotContain=" + UPDATED_CUST_ID_TYPE_ONE);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustIdRefOneIsEqualToSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where custIdRefOne equals to DEFAULT_CUST_ID_REF_ONE
        defaultCustCreditProfileShouldBeFound("custIdRefOne.equals=" + DEFAULT_CUST_ID_REF_ONE);

        // Get all the custCreditProfileList where custIdRefOne equals to UPDATED_CUST_ID_REF_ONE
        defaultCustCreditProfileShouldNotBeFound("custIdRefOne.equals=" + UPDATED_CUST_ID_REF_ONE);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustIdRefOneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where custIdRefOne not equals to DEFAULT_CUST_ID_REF_ONE
        defaultCustCreditProfileShouldNotBeFound("custIdRefOne.notEquals=" + DEFAULT_CUST_ID_REF_ONE);

        // Get all the custCreditProfileList where custIdRefOne not equals to UPDATED_CUST_ID_REF_ONE
        defaultCustCreditProfileShouldBeFound("custIdRefOne.notEquals=" + UPDATED_CUST_ID_REF_ONE);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustIdRefOneIsInShouldWork() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where custIdRefOne in DEFAULT_CUST_ID_REF_ONE or UPDATED_CUST_ID_REF_ONE
        defaultCustCreditProfileShouldBeFound("custIdRefOne.in=" + DEFAULT_CUST_ID_REF_ONE + "," + UPDATED_CUST_ID_REF_ONE);

        // Get all the custCreditProfileList where custIdRefOne equals to UPDATED_CUST_ID_REF_ONE
        defaultCustCreditProfileShouldNotBeFound("custIdRefOne.in=" + UPDATED_CUST_ID_REF_ONE);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustIdRefOneIsNullOrNotNull() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where custIdRefOne is not null
        defaultCustCreditProfileShouldBeFound("custIdRefOne.specified=true");

        // Get all the custCreditProfileList where custIdRefOne is null
        defaultCustCreditProfileShouldNotBeFound("custIdRefOne.specified=false");
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustIdRefOneContainsSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where custIdRefOne contains DEFAULT_CUST_ID_REF_ONE
        defaultCustCreditProfileShouldBeFound("custIdRefOne.contains=" + DEFAULT_CUST_ID_REF_ONE);

        // Get all the custCreditProfileList where custIdRefOne contains UPDATED_CUST_ID_REF_ONE
        defaultCustCreditProfileShouldNotBeFound("custIdRefOne.contains=" + UPDATED_CUST_ID_REF_ONE);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustIdRefOneNotContainsSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where custIdRefOne does not contain DEFAULT_CUST_ID_REF_ONE
        defaultCustCreditProfileShouldNotBeFound("custIdRefOne.doesNotContain=" + DEFAULT_CUST_ID_REF_ONE);

        // Get all the custCreditProfileList where custIdRefOne does not contain UPDATED_CUST_ID_REF_ONE
        defaultCustCreditProfileShouldBeFound("custIdRefOne.doesNotContain=" + UPDATED_CUST_ID_REF_ONE);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustIdTypeTwoIsEqualToSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where custIdTypeTwo equals to DEFAULT_CUST_ID_TYPE_TWO
        defaultCustCreditProfileShouldBeFound("custIdTypeTwo.equals=" + DEFAULT_CUST_ID_TYPE_TWO);

        // Get all the custCreditProfileList where custIdTypeTwo equals to UPDATED_CUST_ID_TYPE_TWO
        defaultCustCreditProfileShouldNotBeFound("custIdTypeTwo.equals=" + UPDATED_CUST_ID_TYPE_TWO);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustIdTypeTwoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where custIdTypeTwo not equals to DEFAULT_CUST_ID_TYPE_TWO
        defaultCustCreditProfileShouldNotBeFound("custIdTypeTwo.notEquals=" + DEFAULT_CUST_ID_TYPE_TWO);

        // Get all the custCreditProfileList where custIdTypeTwo not equals to UPDATED_CUST_ID_TYPE_TWO
        defaultCustCreditProfileShouldBeFound("custIdTypeTwo.notEquals=" + UPDATED_CUST_ID_TYPE_TWO);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustIdTypeTwoIsInShouldWork() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where custIdTypeTwo in DEFAULT_CUST_ID_TYPE_TWO or UPDATED_CUST_ID_TYPE_TWO
        defaultCustCreditProfileShouldBeFound("custIdTypeTwo.in=" + DEFAULT_CUST_ID_TYPE_TWO + "," + UPDATED_CUST_ID_TYPE_TWO);

        // Get all the custCreditProfileList where custIdTypeTwo equals to UPDATED_CUST_ID_TYPE_TWO
        defaultCustCreditProfileShouldNotBeFound("custIdTypeTwo.in=" + UPDATED_CUST_ID_TYPE_TWO);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustIdTypeTwoIsNullOrNotNull() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where custIdTypeTwo is not null
        defaultCustCreditProfileShouldBeFound("custIdTypeTwo.specified=true");

        // Get all the custCreditProfileList where custIdTypeTwo is null
        defaultCustCreditProfileShouldNotBeFound("custIdTypeTwo.specified=false");
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustIdTypeTwoContainsSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where custIdTypeTwo contains DEFAULT_CUST_ID_TYPE_TWO
        defaultCustCreditProfileShouldBeFound("custIdTypeTwo.contains=" + DEFAULT_CUST_ID_TYPE_TWO);

        // Get all the custCreditProfileList where custIdTypeTwo contains UPDATED_CUST_ID_TYPE_TWO
        defaultCustCreditProfileShouldNotBeFound("custIdTypeTwo.contains=" + UPDATED_CUST_ID_TYPE_TWO);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustIdTypeTwoNotContainsSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where custIdTypeTwo does not contain DEFAULT_CUST_ID_TYPE_TWO
        defaultCustCreditProfileShouldNotBeFound("custIdTypeTwo.doesNotContain=" + DEFAULT_CUST_ID_TYPE_TWO);

        // Get all the custCreditProfileList where custIdTypeTwo does not contain UPDATED_CUST_ID_TYPE_TWO
        defaultCustCreditProfileShouldBeFound("custIdTypeTwo.doesNotContain=" + UPDATED_CUST_ID_TYPE_TWO);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustIdRefTwoIsEqualToSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where custIdRefTwo equals to DEFAULT_CUST_ID_REF_TWO
        defaultCustCreditProfileShouldBeFound("custIdRefTwo.equals=" + DEFAULT_CUST_ID_REF_TWO);

        // Get all the custCreditProfileList where custIdRefTwo equals to UPDATED_CUST_ID_REF_TWO
        defaultCustCreditProfileShouldNotBeFound("custIdRefTwo.equals=" + UPDATED_CUST_ID_REF_TWO);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustIdRefTwoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where custIdRefTwo not equals to DEFAULT_CUST_ID_REF_TWO
        defaultCustCreditProfileShouldNotBeFound("custIdRefTwo.notEquals=" + DEFAULT_CUST_ID_REF_TWO);

        // Get all the custCreditProfileList where custIdRefTwo not equals to UPDATED_CUST_ID_REF_TWO
        defaultCustCreditProfileShouldBeFound("custIdRefTwo.notEquals=" + UPDATED_CUST_ID_REF_TWO);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustIdRefTwoIsInShouldWork() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where custIdRefTwo in DEFAULT_CUST_ID_REF_TWO or UPDATED_CUST_ID_REF_TWO
        defaultCustCreditProfileShouldBeFound("custIdRefTwo.in=" + DEFAULT_CUST_ID_REF_TWO + "," + UPDATED_CUST_ID_REF_TWO);

        // Get all the custCreditProfileList where custIdRefTwo equals to UPDATED_CUST_ID_REF_TWO
        defaultCustCreditProfileShouldNotBeFound("custIdRefTwo.in=" + UPDATED_CUST_ID_REF_TWO);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustIdRefTwoIsNullOrNotNull() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where custIdRefTwo is not null
        defaultCustCreditProfileShouldBeFound("custIdRefTwo.specified=true");

        // Get all the custCreditProfileList where custIdRefTwo is null
        defaultCustCreditProfileShouldNotBeFound("custIdRefTwo.specified=false");
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustIdRefTwoContainsSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where custIdRefTwo contains DEFAULT_CUST_ID_REF_TWO
        defaultCustCreditProfileShouldBeFound("custIdRefTwo.contains=" + DEFAULT_CUST_ID_REF_TWO);

        // Get all the custCreditProfileList where custIdRefTwo contains UPDATED_CUST_ID_REF_TWO
        defaultCustCreditProfileShouldNotBeFound("custIdRefTwo.contains=" + UPDATED_CUST_ID_REF_TWO);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustIdRefTwoNotContainsSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where custIdRefTwo does not contain DEFAULT_CUST_ID_REF_TWO
        defaultCustCreditProfileShouldNotBeFound("custIdRefTwo.doesNotContain=" + DEFAULT_CUST_ID_REF_TWO);

        // Get all the custCreditProfileList where custIdRefTwo does not contain UPDATED_CUST_ID_REF_TWO
        defaultCustCreditProfileShouldBeFound("custIdRefTwo.doesNotContain=" + UPDATED_CUST_ID_REF_TWO);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditCardNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditCardNumber equals to DEFAULT_CREDIT_CARD_NUMBER
        defaultCustCreditProfileShouldBeFound("creditCardNumber.equals=" + DEFAULT_CREDIT_CARD_NUMBER);

        // Get all the custCreditProfileList where creditCardNumber equals to UPDATED_CREDIT_CARD_NUMBER
        defaultCustCreditProfileShouldNotBeFound("creditCardNumber.equals=" + UPDATED_CREDIT_CARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditCardNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditCardNumber not equals to DEFAULT_CREDIT_CARD_NUMBER
        defaultCustCreditProfileShouldNotBeFound("creditCardNumber.notEquals=" + DEFAULT_CREDIT_CARD_NUMBER);

        // Get all the custCreditProfileList where creditCardNumber not equals to UPDATED_CREDIT_CARD_NUMBER
        defaultCustCreditProfileShouldBeFound("creditCardNumber.notEquals=" + UPDATED_CREDIT_CARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditCardNumberIsInShouldWork() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditCardNumber in DEFAULT_CREDIT_CARD_NUMBER or UPDATED_CREDIT_CARD_NUMBER
        defaultCustCreditProfileShouldBeFound("creditCardNumber.in=" + DEFAULT_CREDIT_CARD_NUMBER + "," + UPDATED_CREDIT_CARD_NUMBER);

        // Get all the custCreditProfileList where creditCardNumber equals to UPDATED_CREDIT_CARD_NUMBER
        defaultCustCreditProfileShouldNotBeFound("creditCardNumber.in=" + UPDATED_CREDIT_CARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditCardNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditCardNumber is not null
        defaultCustCreditProfileShouldBeFound("creditCardNumber.specified=true");

        // Get all the custCreditProfileList where creditCardNumber is null
        defaultCustCreditProfileShouldNotBeFound("creditCardNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditCardNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditCardNumber is greater than or equal to DEFAULT_CREDIT_CARD_NUMBER
        defaultCustCreditProfileShouldBeFound("creditCardNumber.greaterThanOrEqual=" + DEFAULT_CREDIT_CARD_NUMBER);

        // Get all the custCreditProfileList where creditCardNumber is greater than or equal to UPDATED_CREDIT_CARD_NUMBER
        defaultCustCreditProfileShouldNotBeFound("creditCardNumber.greaterThanOrEqual=" + UPDATED_CREDIT_CARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditCardNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditCardNumber is less than or equal to DEFAULT_CREDIT_CARD_NUMBER
        defaultCustCreditProfileShouldBeFound("creditCardNumber.lessThanOrEqual=" + DEFAULT_CREDIT_CARD_NUMBER);

        // Get all the custCreditProfileList where creditCardNumber is less than or equal to SMALLER_CREDIT_CARD_NUMBER
        defaultCustCreditProfileShouldNotBeFound("creditCardNumber.lessThanOrEqual=" + SMALLER_CREDIT_CARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditCardNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditCardNumber is less than DEFAULT_CREDIT_CARD_NUMBER
        defaultCustCreditProfileShouldNotBeFound("creditCardNumber.lessThan=" + DEFAULT_CREDIT_CARD_NUMBER);

        // Get all the custCreditProfileList where creditCardNumber is less than UPDATED_CREDIT_CARD_NUMBER
        defaultCustCreditProfileShouldBeFound("creditCardNumber.lessThan=" + UPDATED_CREDIT_CARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditCardNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditCardNumber is greater than DEFAULT_CREDIT_CARD_NUMBER
        defaultCustCreditProfileShouldNotBeFound("creditCardNumber.greaterThan=" + DEFAULT_CREDIT_CARD_NUMBER);

        // Get all the custCreditProfileList where creditCardNumber is greater than SMALLER_CREDIT_CARD_NUMBER
        defaultCustCreditProfileShouldBeFound("creditCardNumber.greaterThan=" + SMALLER_CREDIT_CARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditProfileDataIsEqualToSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditProfileData equals to DEFAULT_CREDIT_PROFILE_DATA
        defaultCustCreditProfileShouldBeFound("creditProfileData.equals=" + DEFAULT_CREDIT_PROFILE_DATA);

        // Get all the custCreditProfileList where creditProfileData equals to UPDATED_CREDIT_PROFILE_DATA
        defaultCustCreditProfileShouldNotBeFound("creditProfileData.equals=" + UPDATED_CREDIT_PROFILE_DATA);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditProfileDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditProfileData not equals to DEFAULT_CREDIT_PROFILE_DATA
        defaultCustCreditProfileShouldNotBeFound("creditProfileData.notEquals=" + DEFAULT_CREDIT_PROFILE_DATA);

        // Get all the custCreditProfileList where creditProfileData not equals to UPDATED_CREDIT_PROFILE_DATA
        defaultCustCreditProfileShouldBeFound("creditProfileData.notEquals=" + UPDATED_CREDIT_PROFILE_DATA);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditProfileDataIsInShouldWork() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditProfileData in DEFAULT_CREDIT_PROFILE_DATA or UPDATED_CREDIT_PROFILE_DATA
        defaultCustCreditProfileShouldBeFound("creditProfileData.in=" + DEFAULT_CREDIT_PROFILE_DATA + "," + UPDATED_CREDIT_PROFILE_DATA);

        // Get all the custCreditProfileList where creditProfileData equals to UPDATED_CREDIT_PROFILE_DATA
        defaultCustCreditProfileShouldNotBeFound("creditProfileData.in=" + UPDATED_CREDIT_PROFILE_DATA);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditProfileDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditProfileData is not null
        defaultCustCreditProfileShouldBeFound("creditProfileData.specified=true");

        // Get all the custCreditProfileList where creditProfileData is null
        defaultCustCreditProfileShouldNotBeFound("creditProfileData.specified=false");
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditRiskRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditRiskRating equals to DEFAULT_CREDIT_RISK_RATING
        defaultCustCreditProfileShouldBeFound("creditRiskRating.equals=" + DEFAULT_CREDIT_RISK_RATING);

        // Get all the custCreditProfileList where creditRiskRating equals to UPDATED_CREDIT_RISK_RATING
        defaultCustCreditProfileShouldNotBeFound("creditRiskRating.equals=" + UPDATED_CREDIT_RISK_RATING);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditRiskRatingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditRiskRating not equals to DEFAULT_CREDIT_RISK_RATING
        defaultCustCreditProfileShouldNotBeFound("creditRiskRating.notEquals=" + DEFAULT_CREDIT_RISK_RATING);

        // Get all the custCreditProfileList where creditRiskRating not equals to UPDATED_CREDIT_RISK_RATING
        defaultCustCreditProfileShouldBeFound("creditRiskRating.notEquals=" + UPDATED_CREDIT_RISK_RATING);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditRiskRatingIsInShouldWork() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditRiskRating in DEFAULT_CREDIT_RISK_RATING or UPDATED_CREDIT_RISK_RATING
        defaultCustCreditProfileShouldBeFound("creditRiskRating.in=" + DEFAULT_CREDIT_RISK_RATING + "," + UPDATED_CREDIT_RISK_RATING);

        // Get all the custCreditProfileList where creditRiskRating equals to UPDATED_CREDIT_RISK_RATING
        defaultCustCreditProfileShouldNotBeFound("creditRiskRating.in=" + UPDATED_CREDIT_RISK_RATING);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditRiskRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditRiskRating is not null
        defaultCustCreditProfileShouldBeFound("creditRiskRating.specified=true");

        // Get all the custCreditProfileList where creditRiskRating is null
        defaultCustCreditProfileShouldNotBeFound("creditRiskRating.specified=false");
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditRiskRatingContainsSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditRiskRating contains DEFAULT_CREDIT_RISK_RATING
        defaultCustCreditProfileShouldBeFound("creditRiskRating.contains=" + DEFAULT_CREDIT_RISK_RATING);

        // Get all the custCreditProfileList where creditRiskRating contains UPDATED_CREDIT_RISK_RATING
        defaultCustCreditProfileShouldNotBeFound("creditRiskRating.contains=" + UPDATED_CREDIT_RISK_RATING);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditRiskRatingNotContainsSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditRiskRating does not contain DEFAULT_CREDIT_RISK_RATING
        defaultCustCreditProfileShouldNotBeFound("creditRiskRating.doesNotContain=" + DEFAULT_CREDIT_RISK_RATING);

        // Get all the custCreditProfileList where creditRiskRating does not contain UPDATED_CREDIT_RISK_RATING
        defaultCustCreditProfileShouldBeFound("creditRiskRating.doesNotContain=" + UPDATED_CREDIT_RISK_RATING);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditRiskRatingDescIsEqualToSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditRiskRatingDesc equals to DEFAULT_CREDIT_RISK_RATING_DESC
        defaultCustCreditProfileShouldBeFound("creditRiskRatingDesc.equals=" + DEFAULT_CREDIT_RISK_RATING_DESC);

        // Get all the custCreditProfileList where creditRiskRatingDesc equals to UPDATED_CREDIT_RISK_RATING_DESC
        defaultCustCreditProfileShouldNotBeFound("creditRiskRatingDesc.equals=" + UPDATED_CREDIT_RISK_RATING_DESC);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditRiskRatingDescIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditRiskRatingDesc not equals to DEFAULT_CREDIT_RISK_RATING_DESC
        defaultCustCreditProfileShouldNotBeFound("creditRiskRatingDesc.notEquals=" + DEFAULT_CREDIT_RISK_RATING_DESC);

        // Get all the custCreditProfileList where creditRiskRatingDesc not equals to UPDATED_CREDIT_RISK_RATING_DESC
        defaultCustCreditProfileShouldBeFound("creditRiskRatingDesc.notEquals=" + UPDATED_CREDIT_RISK_RATING_DESC);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditRiskRatingDescIsInShouldWork() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditRiskRatingDesc in DEFAULT_CREDIT_RISK_RATING_DESC or UPDATED_CREDIT_RISK_RATING_DESC
        defaultCustCreditProfileShouldBeFound(
            "creditRiskRatingDesc.in=" + DEFAULT_CREDIT_RISK_RATING_DESC + "," + UPDATED_CREDIT_RISK_RATING_DESC
        );

        // Get all the custCreditProfileList where creditRiskRatingDesc equals to UPDATED_CREDIT_RISK_RATING_DESC
        defaultCustCreditProfileShouldNotBeFound("creditRiskRatingDesc.in=" + UPDATED_CREDIT_RISK_RATING_DESC);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditRiskRatingDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditRiskRatingDesc is not null
        defaultCustCreditProfileShouldBeFound("creditRiskRatingDesc.specified=true");

        // Get all the custCreditProfileList where creditRiskRatingDesc is null
        defaultCustCreditProfileShouldNotBeFound("creditRiskRatingDesc.specified=false");
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditRiskRatingDescContainsSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditRiskRatingDesc contains DEFAULT_CREDIT_RISK_RATING_DESC
        defaultCustCreditProfileShouldBeFound("creditRiskRatingDesc.contains=" + DEFAULT_CREDIT_RISK_RATING_DESC);

        // Get all the custCreditProfileList where creditRiskRatingDesc contains UPDATED_CREDIT_RISK_RATING_DESC
        defaultCustCreditProfileShouldNotBeFound("creditRiskRatingDesc.contains=" + UPDATED_CREDIT_RISK_RATING_DESC);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditRiskRatingDescNotContainsSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditRiskRatingDesc does not contain DEFAULT_CREDIT_RISK_RATING_DESC
        defaultCustCreditProfileShouldNotBeFound("creditRiskRatingDesc.doesNotContain=" + DEFAULT_CREDIT_RISK_RATING_DESC);

        // Get all the custCreditProfileList where creditRiskRatingDesc does not contain UPDATED_CREDIT_RISK_RATING_DESC
        defaultCustCreditProfileShouldBeFound("creditRiskRatingDesc.doesNotContain=" + UPDATED_CREDIT_RISK_RATING_DESC);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditScore equals to DEFAULT_CREDIT_SCORE
        defaultCustCreditProfileShouldBeFound("creditScore.equals=" + DEFAULT_CREDIT_SCORE);

        // Get all the custCreditProfileList where creditScore equals to UPDATED_CREDIT_SCORE
        defaultCustCreditProfileShouldNotBeFound("creditScore.equals=" + UPDATED_CREDIT_SCORE);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditScoreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditScore not equals to DEFAULT_CREDIT_SCORE
        defaultCustCreditProfileShouldNotBeFound("creditScore.notEquals=" + DEFAULT_CREDIT_SCORE);

        // Get all the custCreditProfileList where creditScore not equals to UPDATED_CREDIT_SCORE
        defaultCustCreditProfileShouldBeFound("creditScore.notEquals=" + UPDATED_CREDIT_SCORE);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditScoreIsInShouldWork() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditScore in DEFAULT_CREDIT_SCORE or UPDATED_CREDIT_SCORE
        defaultCustCreditProfileShouldBeFound("creditScore.in=" + DEFAULT_CREDIT_SCORE + "," + UPDATED_CREDIT_SCORE);

        // Get all the custCreditProfileList where creditScore equals to UPDATED_CREDIT_SCORE
        defaultCustCreditProfileShouldNotBeFound("creditScore.in=" + UPDATED_CREDIT_SCORE);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditScore is not null
        defaultCustCreditProfileShouldBeFound("creditScore.specified=true");

        // Get all the custCreditProfileList where creditScore is null
        defaultCustCreditProfileShouldNotBeFound("creditScore.specified=false");
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditScoreIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditScore is greater than or equal to DEFAULT_CREDIT_SCORE
        defaultCustCreditProfileShouldBeFound("creditScore.greaterThanOrEqual=" + DEFAULT_CREDIT_SCORE);

        // Get all the custCreditProfileList where creditScore is greater than or equal to UPDATED_CREDIT_SCORE
        defaultCustCreditProfileShouldNotBeFound("creditScore.greaterThanOrEqual=" + UPDATED_CREDIT_SCORE);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditScoreIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditScore is less than or equal to DEFAULT_CREDIT_SCORE
        defaultCustCreditProfileShouldBeFound("creditScore.lessThanOrEqual=" + DEFAULT_CREDIT_SCORE);

        // Get all the custCreditProfileList where creditScore is less than or equal to SMALLER_CREDIT_SCORE
        defaultCustCreditProfileShouldNotBeFound("creditScore.lessThanOrEqual=" + SMALLER_CREDIT_SCORE);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditScoreIsLessThanSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditScore is less than DEFAULT_CREDIT_SCORE
        defaultCustCreditProfileShouldNotBeFound("creditScore.lessThan=" + DEFAULT_CREDIT_SCORE);

        // Get all the custCreditProfileList where creditScore is less than UPDATED_CREDIT_SCORE
        defaultCustCreditProfileShouldBeFound("creditScore.lessThan=" + UPDATED_CREDIT_SCORE);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCreditScoreIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where creditScore is greater than DEFAULT_CREDIT_SCORE
        defaultCustCreditProfileShouldNotBeFound("creditScore.greaterThan=" + DEFAULT_CREDIT_SCORE);

        // Get all the custCreditProfileList where creditScore is greater than SMALLER_CREDIT_SCORE
        defaultCustCreditProfileShouldBeFound("creditScore.greaterThan=" + SMALLER_CREDIT_SCORE);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByValidUntilIsEqualToSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where validUntil equals to DEFAULT_VALID_UNTIL
        defaultCustCreditProfileShouldBeFound("validUntil.equals=" + DEFAULT_VALID_UNTIL);

        // Get all the custCreditProfileList where validUntil equals to UPDATED_VALID_UNTIL
        defaultCustCreditProfileShouldNotBeFound("validUntil.equals=" + UPDATED_VALID_UNTIL);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByValidUntilIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where validUntil not equals to DEFAULT_VALID_UNTIL
        defaultCustCreditProfileShouldNotBeFound("validUntil.notEquals=" + DEFAULT_VALID_UNTIL);

        // Get all the custCreditProfileList where validUntil not equals to UPDATED_VALID_UNTIL
        defaultCustCreditProfileShouldBeFound("validUntil.notEquals=" + UPDATED_VALID_UNTIL);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByValidUntilIsInShouldWork() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where validUntil in DEFAULT_VALID_UNTIL or UPDATED_VALID_UNTIL
        defaultCustCreditProfileShouldBeFound("validUntil.in=" + DEFAULT_VALID_UNTIL + "," + UPDATED_VALID_UNTIL);

        // Get all the custCreditProfileList where validUntil equals to UPDATED_VALID_UNTIL
        defaultCustCreditProfileShouldNotBeFound("validUntil.in=" + UPDATED_VALID_UNTIL);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByValidUntilIsNullOrNotNull() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where validUntil is not null
        defaultCustCreditProfileShouldBeFound("validUntil.specified=true");

        // Get all the custCreditProfileList where validUntil is null
        defaultCustCreditProfileShouldNotBeFound("validUntil.specified=false");
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustomerIdIsEqualToSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where customerId equals to DEFAULT_CUSTOMER_ID
        defaultCustCreditProfileShouldBeFound("customerId.equals=" + DEFAULT_CUSTOMER_ID);

        // Get all the custCreditProfileList where customerId equals to UPDATED_CUSTOMER_ID
        defaultCustCreditProfileShouldNotBeFound("customerId.equals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustomerIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where customerId not equals to DEFAULT_CUSTOMER_ID
        defaultCustCreditProfileShouldNotBeFound("customerId.notEquals=" + DEFAULT_CUSTOMER_ID);

        // Get all the custCreditProfileList where customerId not equals to UPDATED_CUSTOMER_ID
        defaultCustCreditProfileShouldBeFound("customerId.notEquals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustomerIdIsInShouldWork() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where customerId in DEFAULT_CUSTOMER_ID or UPDATED_CUSTOMER_ID
        defaultCustCreditProfileShouldBeFound("customerId.in=" + DEFAULT_CUSTOMER_ID + "," + UPDATED_CUSTOMER_ID);

        // Get all the custCreditProfileList where customerId equals to UPDATED_CUSTOMER_ID
        defaultCustCreditProfileShouldNotBeFound("customerId.in=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustomerIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where customerId is not null
        defaultCustCreditProfileShouldBeFound("customerId.specified=true");

        // Get all the custCreditProfileList where customerId is null
        defaultCustCreditProfileShouldNotBeFound("customerId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustomerIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where customerId is greater than or equal to DEFAULT_CUSTOMER_ID
        defaultCustCreditProfileShouldBeFound("customerId.greaterThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the custCreditProfileList where customerId is greater than or equal to UPDATED_CUSTOMER_ID
        defaultCustCreditProfileShouldNotBeFound("customerId.greaterThanOrEqual=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustomerIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where customerId is less than or equal to DEFAULT_CUSTOMER_ID
        defaultCustCreditProfileShouldBeFound("customerId.lessThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the custCreditProfileList where customerId is less than or equal to SMALLER_CUSTOMER_ID
        defaultCustCreditProfileShouldNotBeFound("customerId.lessThanOrEqual=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustomerIdIsLessThanSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where customerId is less than DEFAULT_CUSTOMER_ID
        defaultCustCreditProfileShouldNotBeFound("customerId.lessThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the custCreditProfileList where customerId is less than UPDATED_CUSTOMER_ID
        defaultCustCreditProfileShouldBeFound("customerId.lessThan=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustomerIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        // Get all the custCreditProfileList where customerId is greater than DEFAULT_CUSTOMER_ID
        defaultCustCreditProfileShouldNotBeFound("customerId.greaterThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the custCreditProfileList where customerId is greater than SMALLER_CUSTOMER_ID
        defaultCustCreditProfileShouldBeFound("customerId.greaterThan=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustCreditProfilesByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);
        Customer customer = CustomerResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        custCreditProfile.setCustomer(customer);
        customer.setCustCreditProfile(custCreditProfile);
        custCreditProfileRepository.saveAndFlush(custCreditProfile);
        Long customerId = customer.getId();

        // Get all the custCreditProfileList where customer equals to customerId
        defaultCustCreditProfileShouldBeFound("customerId.equals=" + customerId);

        // Get all the custCreditProfileList where customer equals to (customerId + 1)
        defaultCustCreditProfileShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustCreditProfileShouldBeFound(String filter) throws Exception {
        restCustCreditProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custCreditProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].custIdTypeOne").value(hasItem(DEFAULT_CUST_ID_TYPE_ONE)))
            .andExpect(jsonPath("$.[*].custIdRefOne").value(hasItem(DEFAULT_CUST_ID_REF_ONE)))
            .andExpect(jsonPath("$.[*].custIdTypeTwo").value(hasItem(DEFAULT_CUST_ID_TYPE_TWO)))
            .andExpect(jsonPath("$.[*].custIdRefTwo").value(hasItem(DEFAULT_CUST_ID_REF_TWO)))
            .andExpect(jsonPath("$.[*].creditCardNumber").value(hasItem(DEFAULT_CREDIT_CARD_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].creditProfileData").value(hasItem(DEFAULT_CREDIT_PROFILE_DATA.toString())))
            .andExpect(jsonPath("$.[*].creditRiskRating").value(hasItem(DEFAULT_CREDIT_RISK_RATING)))
            .andExpect(jsonPath("$.[*].creditRiskRatingDesc").value(hasItem(DEFAULT_CREDIT_RISK_RATING_DESC)))
            .andExpect(jsonPath("$.[*].creditScore").value(hasItem(DEFAULT_CREDIT_SCORE)))
            .andExpect(jsonPath("$.[*].validUntil").value(hasItem(DEFAULT_VALID_UNTIL.toString())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())));

        // Check, that the count call also returns 1
        restCustCreditProfileMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustCreditProfileShouldNotBeFound(String filter) throws Exception {
        restCustCreditProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustCreditProfileMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustCreditProfile() throws Exception {
        // Get the custCreditProfile
        restCustCreditProfileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustCreditProfile() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        int databaseSizeBeforeUpdate = custCreditProfileRepository.findAll().size();

        // Update the custCreditProfile
        CustCreditProfile updatedCustCreditProfile = custCreditProfileRepository.findById(custCreditProfile.getId()).get();
        // Disconnect from session so that the updates on updatedCustCreditProfile are not directly saved in db
        em.detach(updatedCustCreditProfile);
        updatedCustCreditProfile
            .custIdTypeOne(UPDATED_CUST_ID_TYPE_ONE)
            .custIdRefOne(UPDATED_CUST_ID_REF_ONE)
            .custIdTypeTwo(UPDATED_CUST_ID_TYPE_TWO)
            .custIdRefTwo(UPDATED_CUST_ID_REF_TWO)
            .creditCardNumber(UPDATED_CREDIT_CARD_NUMBER)
            .creditProfileData(UPDATED_CREDIT_PROFILE_DATA)
            .creditRiskRating(UPDATED_CREDIT_RISK_RATING)
            .creditRiskRatingDesc(UPDATED_CREDIT_RISK_RATING_DESC)
            .creditScore(UPDATED_CREDIT_SCORE)
            .validUntil(UPDATED_VALID_UNTIL)
            .customerId(UPDATED_CUSTOMER_ID);
        CustCreditProfileDTO custCreditProfileDTO = custCreditProfileMapper.toDto(updatedCustCreditProfile);

        restCustCreditProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custCreditProfileDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCreditProfileDTO))
            )
            .andExpect(status().isOk());

        // Validate the CustCreditProfile in the database
        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeUpdate);
        CustCreditProfile testCustCreditProfile = custCreditProfileList.get(custCreditProfileList.size() - 1);
        assertThat(testCustCreditProfile.getCustIdTypeOne()).isEqualTo(UPDATED_CUST_ID_TYPE_ONE);
        assertThat(testCustCreditProfile.getCustIdRefOne()).isEqualTo(UPDATED_CUST_ID_REF_ONE);
        assertThat(testCustCreditProfile.getCustIdTypeTwo()).isEqualTo(UPDATED_CUST_ID_TYPE_TWO);
        assertThat(testCustCreditProfile.getCustIdRefTwo()).isEqualTo(UPDATED_CUST_ID_REF_TWO);
        assertThat(testCustCreditProfile.getCreditCardNumber()).isEqualTo(UPDATED_CREDIT_CARD_NUMBER);
        assertThat(testCustCreditProfile.getCreditProfileData()).isEqualTo(UPDATED_CREDIT_PROFILE_DATA);
        assertThat(testCustCreditProfile.getCreditRiskRating()).isEqualTo(UPDATED_CREDIT_RISK_RATING);
        assertThat(testCustCreditProfile.getCreditRiskRatingDesc()).isEqualTo(UPDATED_CREDIT_RISK_RATING_DESC);
        assertThat(testCustCreditProfile.getCreditScore()).isEqualTo(UPDATED_CREDIT_SCORE);
        assertThat(testCustCreditProfile.getValidUntil()).isEqualTo(UPDATED_VALID_UNTIL);
        assertThat(testCustCreditProfile.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustCreditProfile() throws Exception {
        int databaseSizeBeforeUpdate = custCreditProfileRepository.findAll().size();
        custCreditProfile.setId(count.incrementAndGet());

        // Create the CustCreditProfile
        CustCreditProfileDTO custCreditProfileDTO = custCreditProfileMapper.toDto(custCreditProfile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustCreditProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custCreditProfileDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCreditProfileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustCreditProfile in the database
        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustCreditProfile() throws Exception {
        int databaseSizeBeforeUpdate = custCreditProfileRepository.findAll().size();
        custCreditProfile.setId(count.incrementAndGet());

        // Create the CustCreditProfile
        CustCreditProfileDTO custCreditProfileDTO = custCreditProfileMapper.toDto(custCreditProfile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustCreditProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCreditProfileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustCreditProfile in the database
        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustCreditProfile() throws Exception {
        int databaseSizeBeforeUpdate = custCreditProfileRepository.findAll().size();
        custCreditProfile.setId(count.incrementAndGet());

        // Create the CustCreditProfile
        CustCreditProfileDTO custCreditProfileDTO = custCreditProfileMapper.toDto(custCreditProfile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustCreditProfileMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custCreditProfileDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustCreditProfile in the database
        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustCreditProfileWithPatch() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        int databaseSizeBeforeUpdate = custCreditProfileRepository.findAll().size();

        // Update the custCreditProfile using partial update
        CustCreditProfile partialUpdatedCustCreditProfile = new CustCreditProfile();
        partialUpdatedCustCreditProfile.setId(custCreditProfile.getId());

        partialUpdatedCustCreditProfile
            .custIdRefOne(UPDATED_CUST_ID_REF_ONE)
            .creditCardNumber(UPDATED_CREDIT_CARD_NUMBER)
            .creditProfileData(UPDATED_CREDIT_PROFILE_DATA)
            .validUntil(UPDATED_VALID_UNTIL);

        restCustCreditProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustCreditProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustCreditProfile))
            )
            .andExpect(status().isOk());

        // Validate the CustCreditProfile in the database
        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeUpdate);
        CustCreditProfile testCustCreditProfile = custCreditProfileList.get(custCreditProfileList.size() - 1);
        assertThat(testCustCreditProfile.getCustIdTypeOne()).isEqualTo(DEFAULT_CUST_ID_TYPE_ONE);
        assertThat(testCustCreditProfile.getCustIdRefOne()).isEqualTo(UPDATED_CUST_ID_REF_ONE);
        assertThat(testCustCreditProfile.getCustIdTypeTwo()).isEqualTo(DEFAULT_CUST_ID_TYPE_TWO);
        assertThat(testCustCreditProfile.getCustIdRefTwo()).isEqualTo(DEFAULT_CUST_ID_REF_TWO);
        assertThat(testCustCreditProfile.getCreditCardNumber()).isEqualTo(UPDATED_CREDIT_CARD_NUMBER);
        assertThat(testCustCreditProfile.getCreditProfileData()).isEqualTo(UPDATED_CREDIT_PROFILE_DATA);
        assertThat(testCustCreditProfile.getCreditRiskRating()).isEqualTo(DEFAULT_CREDIT_RISK_RATING);
        assertThat(testCustCreditProfile.getCreditRiskRatingDesc()).isEqualTo(DEFAULT_CREDIT_RISK_RATING_DESC);
        assertThat(testCustCreditProfile.getCreditScore()).isEqualTo(DEFAULT_CREDIT_SCORE);
        assertThat(testCustCreditProfile.getValidUntil()).isEqualTo(UPDATED_VALID_UNTIL);
        assertThat(testCustCreditProfile.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustCreditProfileWithPatch() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        int databaseSizeBeforeUpdate = custCreditProfileRepository.findAll().size();

        // Update the custCreditProfile using partial update
        CustCreditProfile partialUpdatedCustCreditProfile = new CustCreditProfile();
        partialUpdatedCustCreditProfile.setId(custCreditProfile.getId());

        partialUpdatedCustCreditProfile
            .custIdTypeOne(UPDATED_CUST_ID_TYPE_ONE)
            .custIdRefOne(UPDATED_CUST_ID_REF_ONE)
            .custIdTypeTwo(UPDATED_CUST_ID_TYPE_TWO)
            .custIdRefTwo(UPDATED_CUST_ID_REF_TWO)
            .creditCardNumber(UPDATED_CREDIT_CARD_NUMBER)
            .creditProfileData(UPDATED_CREDIT_PROFILE_DATA)
            .creditRiskRating(UPDATED_CREDIT_RISK_RATING)
            .creditRiskRatingDesc(UPDATED_CREDIT_RISK_RATING_DESC)
            .creditScore(UPDATED_CREDIT_SCORE)
            .validUntil(UPDATED_VALID_UNTIL)
            .customerId(UPDATED_CUSTOMER_ID);

        restCustCreditProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustCreditProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustCreditProfile))
            )
            .andExpect(status().isOk());

        // Validate the CustCreditProfile in the database
        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeUpdate);
        CustCreditProfile testCustCreditProfile = custCreditProfileList.get(custCreditProfileList.size() - 1);
        assertThat(testCustCreditProfile.getCustIdTypeOne()).isEqualTo(UPDATED_CUST_ID_TYPE_ONE);
        assertThat(testCustCreditProfile.getCustIdRefOne()).isEqualTo(UPDATED_CUST_ID_REF_ONE);
        assertThat(testCustCreditProfile.getCustIdTypeTwo()).isEqualTo(UPDATED_CUST_ID_TYPE_TWO);
        assertThat(testCustCreditProfile.getCustIdRefTwo()).isEqualTo(UPDATED_CUST_ID_REF_TWO);
        assertThat(testCustCreditProfile.getCreditCardNumber()).isEqualTo(UPDATED_CREDIT_CARD_NUMBER);
        assertThat(testCustCreditProfile.getCreditProfileData()).isEqualTo(UPDATED_CREDIT_PROFILE_DATA);
        assertThat(testCustCreditProfile.getCreditRiskRating()).isEqualTo(UPDATED_CREDIT_RISK_RATING);
        assertThat(testCustCreditProfile.getCreditRiskRatingDesc()).isEqualTo(UPDATED_CREDIT_RISK_RATING_DESC);
        assertThat(testCustCreditProfile.getCreditScore()).isEqualTo(UPDATED_CREDIT_SCORE);
        assertThat(testCustCreditProfile.getValidUntil()).isEqualTo(UPDATED_VALID_UNTIL);
        assertThat(testCustCreditProfile.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustCreditProfile() throws Exception {
        int databaseSizeBeforeUpdate = custCreditProfileRepository.findAll().size();
        custCreditProfile.setId(count.incrementAndGet());

        // Create the CustCreditProfile
        CustCreditProfileDTO custCreditProfileDTO = custCreditProfileMapper.toDto(custCreditProfile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustCreditProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custCreditProfileDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custCreditProfileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustCreditProfile in the database
        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustCreditProfile() throws Exception {
        int databaseSizeBeforeUpdate = custCreditProfileRepository.findAll().size();
        custCreditProfile.setId(count.incrementAndGet());

        // Create the CustCreditProfile
        CustCreditProfileDTO custCreditProfileDTO = custCreditProfileMapper.toDto(custCreditProfile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustCreditProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custCreditProfileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustCreditProfile in the database
        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustCreditProfile() throws Exception {
        int databaseSizeBeforeUpdate = custCreditProfileRepository.findAll().size();
        custCreditProfile.setId(count.incrementAndGet());

        // Create the CustCreditProfile
        CustCreditProfileDTO custCreditProfileDTO = custCreditProfileMapper.toDto(custCreditProfile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustCreditProfileMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custCreditProfileDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustCreditProfile in the database
        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustCreditProfile() throws Exception {
        // Initialize the database
        custCreditProfileRepository.saveAndFlush(custCreditProfile);

        int databaseSizeBeforeDelete = custCreditProfileRepository.findAll().size();

        // Delete the custCreditProfile
        restCustCreditProfileMockMvc
            .perform(delete(ENTITY_API_URL_ID, custCreditProfile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustCreditProfile> custCreditProfileList = custCreditProfileRepository.findAll();
        assertThat(custCreditProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
