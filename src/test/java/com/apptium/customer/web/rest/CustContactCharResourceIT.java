package com.apptium.customer.web.rest;

import static com.apptium.customer.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.CustContact;
import com.apptium.customer.domain.CustContactChar;
import com.apptium.customer.repository.CustContactCharRepository;
import com.apptium.customer.service.criteria.CustContactCharCriteria;
import com.apptium.customer.service.dto.CustContactCharDTO;
import com.apptium.customer.service.mapper.CustContactCharMapper;
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
 * Integration tests for the {@link CustContactCharResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustContactCharResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_ONE = "AAAAAAAAAA";
    private static final String UPDATED_STREET_ONE = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_TWO = "AAAAAAAAAA";
    private static final String UPDATED_STREET_TWO = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_OR_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_OR_PROVINCE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final Long DEFAULT_POST_CODE = 1L;
    private static final Long UPDATED_POST_CODE = 2L;
    private static final Long SMALLER_POST_CODE = 1L - 1L;

    private static final Long DEFAULT_PHONE_NUMBER = 1L;
    private static final Long UPDATED_PHONE_NUMBER = 2L;
    private static final Long SMALLER_PHONE_NUMBER = 1L - 1L;

    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final Long DEFAULT_FAX_NUMBER = 1L;
    private static final Long UPDATED_FAX_NUMBER = 2L;
    private static final Long SMALLER_FAX_NUMBER = 1L - 1L;

    private static final BigDecimal DEFAULT_LATITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LATITUDE = new BigDecimal(2);
    private static final BigDecimal SMALLER_LATITUDE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_LONGITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LONGITUDE = new BigDecimal(2);
    private static final BigDecimal SMALLER_LONGITUDE = new BigDecimal(1 - 1);

    private static final Integer DEFAULT_SV_CONTACT_ID = 1;
    private static final Integer UPDATED_SV_CONTACT_ID = 2;
    private static final Integer SMALLER_SV_CONTACT_ID = 1 - 1;

    private static final Boolean DEFAULT_IS_EMAIL_VALID = false;
    private static final Boolean UPDATED_IS_EMAIL_VALID = true;

    private static final Boolean DEFAULT_IS_ADDRESS_VALID = false;
    private static final Boolean UPDATED_IS_ADDRESS_VALID = true;

    private static final Integer DEFAULT_CUST_CON_MEDIUM_ID = 1;
    private static final Integer UPDATED_CUST_CON_MEDIUM_ID = 2;
    private static final Integer SMALLER_CUST_CON_MEDIUM_ID = 1 - 1;

    private static final String ENTITY_API_URL = "/api/cust-contact-chars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustContactCharRepository custContactCharRepository;

    @Autowired
    private CustContactCharMapper custContactCharMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustContactCharMockMvc;

    private CustContactChar custContactChar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustContactChar createEntity(EntityManager em) {
        CustContactChar custContactChar = new CustContactChar()
            .type(DEFAULT_TYPE)
            .streetOne(DEFAULT_STREET_ONE)
            .streetTwo(DEFAULT_STREET_TWO)
            .city(DEFAULT_CITY)
            .stateOrProvince(DEFAULT_STATE_OR_PROVINCE)
            .country(DEFAULT_COUNTRY)
            .postCode(DEFAULT_POST_CODE)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .emailAddress(DEFAULT_EMAIL_ADDRESS)
            .faxNumber(DEFAULT_FAX_NUMBER)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .svContactId(DEFAULT_SV_CONTACT_ID)
            .isEmailValid(DEFAULT_IS_EMAIL_VALID)
            .isAddressValid(DEFAULT_IS_ADDRESS_VALID)
            .custConMediumId(DEFAULT_CUST_CON_MEDIUM_ID);
        return custContactChar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustContactChar createUpdatedEntity(EntityManager em) {
        CustContactChar custContactChar = new CustContactChar()
            .type(UPDATED_TYPE)
            .streetOne(UPDATED_STREET_ONE)
            .streetTwo(UPDATED_STREET_TWO)
            .city(UPDATED_CITY)
            .stateOrProvince(UPDATED_STATE_OR_PROVINCE)
            .country(UPDATED_COUNTRY)
            .postCode(UPDATED_POST_CODE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .faxNumber(UPDATED_FAX_NUMBER)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .svContactId(UPDATED_SV_CONTACT_ID)
            .isEmailValid(UPDATED_IS_EMAIL_VALID)
            .isAddressValid(UPDATED_IS_ADDRESS_VALID)
            .custConMediumId(UPDATED_CUST_CON_MEDIUM_ID);
        return custContactChar;
    }

    @BeforeEach
    public void initTest() {
        custContactChar = createEntity(em);
    }

    @Test
    @Transactional
    void createCustContactChar() throws Exception {
        int databaseSizeBeforeCreate = custContactCharRepository.findAll().size();
        // Create the CustContactChar
        CustContactCharDTO custContactCharDTO = custContactCharMapper.toDto(custContactChar);
        restCustContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactCharDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CustContactChar in the database
        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeCreate + 1);
        CustContactChar testCustContactChar = custContactCharList.get(custContactCharList.size() - 1);
        assertThat(testCustContactChar.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCustContactChar.getStreetOne()).isEqualTo(DEFAULT_STREET_ONE);
        assertThat(testCustContactChar.getStreetTwo()).isEqualTo(DEFAULT_STREET_TWO);
        assertThat(testCustContactChar.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCustContactChar.getStateOrProvince()).isEqualTo(DEFAULT_STATE_OR_PROVINCE);
        assertThat(testCustContactChar.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testCustContactChar.getPostCode()).isEqualTo(DEFAULT_POST_CODE);
        assertThat(testCustContactChar.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testCustContactChar.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testCustContactChar.getFaxNumber()).isEqualTo(DEFAULT_FAX_NUMBER);
        assertThat(testCustContactChar.getLatitude()).isEqualByComparingTo(DEFAULT_LATITUDE);
        assertThat(testCustContactChar.getLongitude()).isEqualByComparingTo(DEFAULT_LONGITUDE);
        assertThat(testCustContactChar.getSvContactId()).isEqualTo(DEFAULT_SV_CONTACT_ID);
        assertThat(testCustContactChar.getIsEmailValid()).isEqualTo(DEFAULT_IS_EMAIL_VALID);
        assertThat(testCustContactChar.getIsAddressValid()).isEqualTo(DEFAULT_IS_ADDRESS_VALID);
        assertThat(testCustContactChar.getCustConMediumId()).isEqualTo(DEFAULT_CUST_CON_MEDIUM_ID);
    }

    @Test
    @Transactional
    void createCustContactCharWithExistingId() throws Exception {
        // Create the CustContactChar with an existing ID
        custContactChar.setId(1L);
        CustContactCharDTO custContactCharDTO = custContactCharMapper.toDto(custContactChar);

        int databaseSizeBeforeCreate = custContactCharRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustContactChar in the database
        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactCharRepository.findAll().size();
        // set the field null
        custContactChar.setType(null);

        // Create the CustContactChar, which fails.
        CustContactCharDTO custContactCharDTO = custContactCharMapper.toDto(custContactChar);

        restCustContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactCharRepository.findAll().size();
        // set the field null
        custContactChar.setCity(null);

        // Create the CustContactChar, which fails.
        CustContactCharDTO custContactCharDTO = custContactCharMapper.toDto(custContactChar);

        restCustContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStateOrProvinceIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactCharRepository.findAll().size();
        // set the field null
        custContactChar.setStateOrProvince(null);

        // Create the CustContactChar, which fails.
        CustContactCharDTO custContactCharDTO = custContactCharMapper.toDto(custContactChar);

        restCustContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactCharRepository.findAll().size();
        // set the field null
        custContactChar.setCountry(null);

        // Create the CustContactChar, which fails.
        CustContactCharDTO custContactCharDTO = custContactCharMapper.toDto(custContactChar);

        restCustContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPostCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactCharRepository.findAll().size();
        // set the field null
        custContactChar.setPostCode(null);

        // Create the CustContactChar, which fails.
        CustContactCharDTO custContactCharDTO = custContactCharMapper.toDto(custContactChar);

        restCustContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactCharRepository.findAll().size();
        // set the field null
        custContactChar.setPhoneNumber(null);

        // Create the CustContactChar, which fails.
        CustContactCharDTO custContactCharDTO = custContactCharMapper.toDto(custContactChar);

        restCustContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactCharRepository.findAll().size();
        // set the field null
        custContactChar.setEmailAddress(null);

        // Create the CustContactChar, which fails.
        CustContactCharDTO custContactCharDTO = custContactCharMapper.toDto(custContactChar);

        restCustContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFaxNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactCharRepository.findAll().size();
        // set the field null
        custContactChar.setFaxNumber(null);

        // Create the CustContactChar, which fails.
        CustContactCharDTO custContactCharDTO = custContactCharMapper.toDto(custContactChar);

        restCustContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSvContactIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custContactCharRepository.findAll().size();
        // set the field null
        custContactChar.setSvContactId(null);

        // Create the CustContactChar, which fails.
        CustContactCharDTO custContactCharDTO = custContactCharMapper.toDto(custContactChar);

        restCustContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustContactChars() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList
        restCustContactCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custContactChar.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].streetOne").value(hasItem(DEFAULT_STREET_ONE)))
            .andExpect(jsonPath("$.[*].streetTwo").value(hasItem(DEFAULT_STREET_TWO)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].stateOrProvince").value(hasItem(DEFAULT_STATE_OR_PROVINCE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].postCode").value(hasItem(DEFAULT_POST_CODE.intValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].faxNumber").value(hasItem(DEFAULT_FAX_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(sameNumber(DEFAULT_LATITUDE))))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(sameNumber(DEFAULT_LONGITUDE))))
            .andExpect(jsonPath("$.[*].svContactId").value(hasItem(DEFAULT_SV_CONTACT_ID)))
            .andExpect(jsonPath("$.[*].isEmailValid").value(hasItem(DEFAULT_IS_EMAIL_VALID.booleanValue())))
            .andExpect(jsonPath("$.[*].isAddressValid").value(hasItem(DEFAULT_IS_ADDRESS_VALID.booleanValue())))
            .andExpect(jsonPath("$.[*].custConMediumId").value(hasItem(DEFAULT_CUST_CON_MEDIUM_ID)));
    }

    @Test
    @Transactional
    void getCustContactChar() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get the custContactChar
        restCustContactCharMockMvc
            .perform(get(ENTITY_API_URL_ID, custContactChar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custContactChar.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.streetOne").value(DEFAULT_STREET_ONE))
            .andExpect(jsonPath("$.streetTwo").value(DEFAULT_STREET_TWO))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.stateOrProvince").value(DEFAULT_STATE_OR_PROVINCE))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.postCode").value(DEFAULT_POST_CODE.intValue()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.intValue()))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS))
            .andExpect(jsonPath("$.faxNumber").value(DEFAULT_FAX_NUMBER.intValue()))
            .andExpect(jsonPath("$.latitude").value(sameNumber(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.longitude").value(sameNumber(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.svContactId").value(DEFAULT_SV_CONTACT_ID))
            .andExpect(jsonPath("$.isEmailValid").value(DEFAULT_IS_EMAIL_VALID.booleanValue()))
            .andExpect(jsonPath("$.isAddressValid").value(DEFAULT_IS_ADDRESS_VALID.booleanValue()))
            .andExpect(jsonPath("$.custConMediumId").value(DEFAULT_CUST_CON_MEDIUM_ID));
    }

    @Test
    @Transactional
    void getCustContactCharsByIdFiltering() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        Long id = custContactChar.getId();

        defaultCustContactCharShouldBeFound("id.equals=" + id);
        defaultCustContactCharShouldNotBeFound("id.notEquals=" + id);

        defaultCustContactCharShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustContactCharShouldNotBeFound("id.greaterThan=" + id);

        defaultCustContactCharShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustContactCharShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where type equals to DEFAULT_TYPE
        defaultCustContactCharShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the custContactCharList where type equals to UPDATED_TYPE
        defaultCustContactCharShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where type not equals to DEFAULT_TYPE
        defaultCustContactCharShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the custContactCharList where type not equals to UPDATED_TYPE
        defaultCustContactCharShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultCustContactCharShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the custContactCharList where type equals to UPDATED_TYPE
        defaultCustContactCharShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where type is not null
        defaultCustContactCharShouldBeFound("type.specified=true");

        // Get all the custContactCharList where type is null
        defaultCustContactCharShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllCustContactCharsByTypeContainsSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where type contains DEFAULT_TYPE
        defaultCustContactCharShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the custContactCharList where type contains UPDATED_TYPE
        defaultCustContactCharShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where type does not contain DEFAULT_TYPE
        defaultCustContactCharShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the custContactCharList where type does not contain UPDATED_TYPE
        defaultCustContactCharShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByStreetOneIsEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where streetOne equals to DEFAULT_STREET_ONE
        defaultCustContactCharShouldBeFound("streetOne.equals=" + DEFAULT_STREET_ONE);

        // Get all the custContactCharList where streetOne equals to UPDATED_STREET_ONE
        defaultCustContactCharShouldNotBeFound("streetOne.equals=" + UPDATED_STREET_ONE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByStreetOneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where streetOne not equals to DEFAULT_STREET_ONE
        defaultCustContactCharShouldNotBeFound("streetOne.notEquals=" + DEFAULT_STREET_ONE);

        // Get all the custContactCharList where streetOne not equals to UPDATED_STREET_ONE
        defaultCustContactCharShouldBeFound("streetOne.notEquals=" + UPDATED_STREET_ONE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByStreetOneIsInShouldWork() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where streetOne in DEFAULT_STREET_ONE or UPDATED_STREET_ONE
        defaultCustContactCharShouldBeFound("streetOne.in=" + DEFAULT_STREET_ONE + "," + UPDATED_STREET_ONE);

        // Get all the custContactCharList where streetOne equals to UPDATED_STREET_ONE
        defaultCustContactCharShouldNotBeFound("streetOne.in=" + UPDATED_STREET_ONE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByStreetOneIsNullOrNotNull() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where streetOne is not null
        defaultCustContactCharShouldBeFound("streetOne.specified=true");

        // Get all the custContactCharList where streetOne is null
        defaultCustContactCharShouldNotBeFound("streetOne.specified=false");
    }

    @Test
    @Transactional
    void getAllCustContactCharsByStreetOneContainsSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where streetOne contains DEFAULT_STREET_ONE
        defaultCustContactCharShouldBeFound("streetOne.contains=" + DEFAULT_STREET_ONE);

        // Get all the custContactCharList where streetOne contains UPDATED_STREET_ONE
        defaultCustContactCharShouldNotBeFound("streetOne.contains=" + UPDATED_STREET_ONE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByStreetOneNotContainsSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where streetOne does not contain DEFAULT_STREET_ONE
        defaultCustContactCharShouldNotBeFound("streetOne.doesNotContain=" + DEFAULT_STREET_ONE);

        // Get all the custContactCharList where streetOne does not contain UPDATED_STREET_ONE
        defaultCustContactCharShouldBeFound("streetOne.doesNotContain=" + UPDATED_STREET_ONE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByStreetTwoIsEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where streetTwo equals to DEFAULT_STREET_TWO
        defaultCustContactCharShouldBeFound("streetTwo.equals=" + DEFAULT_STREET_TWO);

        // Get all the custContactCharList where streetTwo equals to UPDATED_STREET_TWO
        defaultCustContactCharShouldNotBeFound("streetTwo.equals=" + UPDATED_STREET_TWO);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByStreetTwoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where streetTwo not equals to DEFAULT_STREET_TWO
        defaultCustContactCharShouldNotBeFound("streetTwo.notEquals=" + DEFAULT_STREET_TWO);

        // Get all the custContactCharList where streetTwo not equals to UPDATED_STREET_TWO
        defaultCustContactCharShouldBeFound("streetTwo.notEquals=" + UPDATED_STREET_TWO);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByStreetTwoIsInShouldWork() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where streetTwo in DEFAULT_STREET_TWO or UPDATED_STREET_TWO
        defaultCustContactCharShouldBeFound("streetTwo.in=" + DEFAULT_STREET_TWO + "," + UPDATED_STREET_TWO);

        // Get all the custContactCharList where streetTwo equals to UPDATED_STREET_TWO
        defaultCustContactCharShouldNotBeFound("streetTwo.in=" + UPDATED_STREET_TWO);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByStreetTwoIsNullOrNotNull() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where streetTwo is not null
        defaultCustContactCharShouldBeFound("streetTwo.specified=true");

        // Get all the custContactCharList where streetTwo is null
        defaultCustContactCharShouldNotBeFound("streetTwo.specified=false");
    }

    @Test
    @Transactional
    void getAllCustContactCharsByStreetTwoContainsSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where streetTwo contains DEFAULT_STREET_TWO
        defaultCustContactCharShouldBeFound("streetTwo.contains=" + DEFAULT_STREET_TWO);

        // Get all the custContactCharList where streetTwo contains UPDATED_STREET_TWO
        defaultCustContactCharShouldNotBeFound("streetTwo.contains=" + UPDATED_STREET_TWO);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByStreetTwoNotContainsSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where streetTwo does not contain DEFAULT_STREET_TWO
        defaultCustContactCharShouldNotBeFound("streetTwo.doesNotContain=" + DEFAULT_STREET_TWO);

        // Get all the custContactCharList where streetTwo does not contain UPDATED_STREET_TWO
        defaultCustContactCharShouldBeFound("streetTwo.doesNotContain=" + UPDATED_STREET_TWO);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where city equals to DEFAULT_CITY
        defaultCustContactCharShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the custContactCharList where city equals to UPDATED_CITY
        defaultCustContactCharShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByCityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where city not equals to DEFAULT_CITY
        defaultCustContactCharShouldNotBeFound("city.notEquals=" + DEFAULT_CITY);

        // Get all the custContactCharList where city not equals to UPDATED_CITY
        defaultCustContactCharShouldBeFound("city.notEquals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByCityIsInShouldWork() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where city in DEFAULT_CITY or UPDATED_CITY
        defaultCustContactCharShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the custContactCharList where city equals to UPDATED_CITY
        defaultCustContactCharShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where city is not null
        defaultCustContactCharShouldBeFound("city.specified=true");

        // Get all the custContactCharList where city is null
        defaultCustContactCharShouldNotBeFound("city.specified=false");
    }

    @Test
    @Transactional
    void getAllCustContactCharsByCityContainsSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where city contains DEFAULT_CITY
        defaultCustContactCharShouldBeFound("city.contains=" + DEFAULT_CITY);

        // Get all the custContactCharList where city contains UPDATED_CITY
        defaultCustContactCharShouldNotBeFound("city.contains=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByCityNotContainsSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where city does not contain DEFAULT_CITY
        defaultCustContactCharShouldNotBeFound("city.doesNotContain=" + DEFAULT_CITY);

        // Get all the custContactCharList where city does not contain UPDATED_CITY
        defaultCustContactCharShouldBeFound("city.doesNotContain=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByStateOrProvinceIsEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where stateOrProvince equals to DEFAULT_STATE_OR_PROVINCE
        defaultCustContactCharShouldBeFound("stateOrProvince.equals=" + DEFAULT_STATE_OR_PROVINCE);

        // Get all the custContactCharList where stateOrProvince equals to UPDATED_STATE_OR_PROVINCE
        defaultCustContactCharShouldNotBeFound("stateOrProvince.equals=" + UPDATED_STATE_OR_PROVINCE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByStateOrProvinceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where stateOrProvince not equals to DEFAULT_STATE_OR_PROVINCE
        defaultCustContactCharShouldNotBeFound("stateOrProvince.notEquals=" + DEFAULT_STATE_OR_PROVINCE);

        // Get all the custContactCharList where stateOrProvince not equals to UPDATED_STATE_OR_PROVINCE
        defaultCustContactCharShouldBeFound("stateOrProvince.notEquals=" + UPDATED_STATE_OR_PROVINCE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByStateOrProvinceIsInShouldWork() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where stateOrProvince in DEFAULT_STATE_OR_PROVINCE or UPDATED_STATE_OR_PROVINCE
        defaultCustContactCharShouldBeFound("stateOrProvince.in=" + DEFAULT_STATE_OR_PROVINCE + "," + UPDATED_STATE_OR_PROVINCE);

        // Get all the custContactCharList where stateOrProvince equals to UPDATED_STATE_OR_PROVINCE
        defaultCustContactCharShouldNotBeFound("stateOrProvince.in=" + UPDATED_STATE_OR_PROVINCE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByStateOrProvinceIsNullOrNotNull() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where stateOrProvince is not null
        defaultCustContactCharShouldBeFound("stateOrProvince.specified=true");

        // Get all the custContactCharList where stateOrProvince is null
        defaultCustContactCharShouldNotBeFound("stateOrProvince.specified=false");
    }

    @Test
    @Transactional
    void getAllCustContactCharsByStateOrProvinceContainsSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where stateOrProvince contains DEFAULT_STATE_OR_PROVINCE
        defaultCustContactCharShouldBeFound("stateOrProvince.contains=" + DEFAULT_STATE_OR_PROVINCE);

        // Get all the custContactCharList where stateOrProvince contains UPDATED_STATE_OR_PROVINCE
        defaultCustContactCharShouldNotBeFound("stateOrProvince.contains=" + UPDATED_STATE_OR_PROVINCE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByStateOrProvinceNotContainsSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where stateOrProvince does not contain DEFAULT_STATE_OR_PROVINCE
        defaultCustContactCharShouldNotBeFound("stateOrProvince.doesNotContain=" + DEFAULT_STATE_OR_PROVINCE);

        // Get all the custContactCharList where stateOrProvince does not contain UPDATED_STATE_OR_PROVINCE
        defaultCustContactCharShouldBeFound("stateOrProvince.doesNotContain=" + UPDATED_STATE_OR_PROVINCE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where country equals to DEFAULT_COUNTRY
        defaultCustContactCharShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the custContactCharList where country equals to UPDATED_COUNTRY
        defaultCustContactCharShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByCountryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where country not equals to DEFAULT_COUNTRY
        defaultCustContactCharShouldNotBeFound("country.notEquals=" + DEFAULT_COUNTRY);

        // Get all the custContactCharList where country not equals to UPDATED_COUNTRY
        defaultCustContactCharShouldBeFound("country.notEquals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultCustContactCharShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the custContactCharList where country equals to UPDATED_COUNTRY
        defaultCustContactCharShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where country is not null
        defaultCustContactCharShouldBeFound("country.specified=true");

        // Get all the custContactCharList where country is null
        defaultCustContactCharShouldNotBeFound("country.specified=false");
    }

    @Test
    @Transactional
    void getAllCustContactCharsByCountryContainsSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where country contains DEFAULT_COUNTRY
        defaultCustContactCharShouldBeFound("country.contains=" + DEFAULT_COUNTRY);

        // Get all the custContactCharList where country contains UPDATED_COUNTRY
        defaultCustContactCharShouldNotBeFound("country.contains=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByCountryNotContainsSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where country does not contain DEFAULT_COUNTRY
        defaultCustContactCharShouldNotBeFound("country.doesNotContain=" + DEFAULT_COUNTRY);

        // Get all the custContactCharList where country does not contain UPDATED_COUNTRY
        defaultCustContactCharShouldBeFound("country.doesNotContain=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByPostCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where postCode equals to DEFAULT_POST_CODE
        defaultCustContactCharShouldBeFound("postCode.equals=" + DEFAULT_POST_CODE);

        // Get all the custContactCharList where postCode equals to UPDATED_POST_CODE
        defaultCustContactCharShouldNotBeFound("postCode.equals=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByPostCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where postCode not equals to DEFAULT_POST_CODE
        defaultCustContactCharShouldNotBeFound("postCode.notEquals=" + DEFAULT_POST_CODE);

        // Get all the custContactCharList where postCode not equals to UPDATED_POST_CODE
        defaultCustContactCharShouldBeFound("postCode.notEquals=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByPostCodeIsInShouldWork() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where postCode in DEFAULT_POST_CODE or UPDATED_POST_CODE
        defaultCustContactCharShouldBeFound("postCode.in=" + DEFAULT_POST_CODE + "," + UPDATED_POST_CODE);

        // Get all the custContactCharList where postCode equals to UPDATED_POST_CODE
        defaultCustContactCharShouldNotBeFound("postCode.in=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByPostCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where postCode is not null
        defaultCustContactCharShouldBeFound("postCode.specified=true");

        // Get all the custContactCharList where postCode is null
        defaultCustContactCharShouldNotBeFound("postCode.specified=false");
    }

    @Test
    @Transactional
    void getAllCustContactCharsByPostCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where postCode is greater than or equal to DEFAULT_POST_CODE
        defaultCustContactCharShouldBeFound("postCode.greaterThanOrEqual=" + DEFAULT_POST_CODE);

        // Get all the custContactCharList where postCode is greater than or equal to UPDATED_POST_CODE
        defaultCustContactCharShouldNotBeFound("postCode.greaterThanOrEqual=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByPostCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where postCode is less than or equal to DEFAULT_POST_CODE
        defaultCustContactCharShouldBeFound("postCode.lessThanOrEqual=" + DEFAULT_POST_CODE);

        // Get all the custContactCharList where postCode is less than or equal to SMALLER_POST_CODE
        defaultCustContactCharShouldNotBeFound("postCode.lessThanOrEqual=" + SMALLER_POST_CODE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByPostCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where postCode is less than DEFAULT_POST_CODE
        defaultCustContactCharShouldNotBeFound("postCode.lessThan=" + DEFAULT_POST_CODE);

        // Get all the custContactCharList where postCode is less than UPDATED_POST_CODE
        defaultCustContactCharShouldBeFound("postCode.lessThan=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByPostCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where postCode is greater than DEFAULT_POST_CODE
        defaultCustContactCharShouldNotBeFound("postCode.greaterThan=" + DEFAULT_POST_CODE);

        // Get all the custContactCharList where postCode is greater than SMALLER_POST_CODE
        defaultCustContactCharShouldBeFound("postCode.greaterThan=" + SMALLER_POST_CODE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultCustContactCharShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the custContactCharList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultCustContactCharShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByPhoneNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where phoneNumber not equals to DEFAULT_PHONE_NUMBER
        defaultCustContactCharShouldNotBeFound("phoneNumber.notEquals=" + DEFAULT_PHONE_NUMBER);

        // Get all the custContactCharList where phoneNumber not equals to UPDATED_PHONE_NUMBER
        defaultCustContactCharShouldBeFound("phoneNumber.notEquals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultCustContactCharShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the custContactCharList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultCustContactCharShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where phoneNumber is not null
        defaultCustContactCharShouldBeFound("phoneNumber.specified=true");

        // Get all the custContactCharList where phoneNumber is null
        defaultCustContactCharShouldNotBeFound("phoneNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllCustContactCharsByPhoneNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where phoneNumber is greater than or equal to DEFAULT_PHONE_NUMBER
        defaultCustContactCharShouldBeFound("phoneNumber.greaterThanOrEqual=" + DEFAULT_PHONE_NUMBER);

        // Get all the custContactCharList where phoneNumber is greater than or equal to UPDATED_PHONE_NUMBER
        defaultCustContactCharShouldNotBeFound("phoneNumber.greaterThanOrEqual=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByPhoneNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where phoneNumber is less than or equal to DEFAULT_PHONE_NUMBER
        defaultCustContactCharShouldBeFound("phoneNumber.lessThanOrEqual=" + DEFAULT_PHONE_NUMBER);

        // Get all the custContactCharList where phoneNumber is less than or equal to SMALLER_PHONE_NUMBER
        defaultCustContactCharShouldNotBeFound("phoneNumber.lessThanOrEqual=" + SMALLER_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByPhoneNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where phoneNumber is less than DEFAULT_PHONE_NUMBER
        defaultCustContactCharShouldNotBeFound("phoneNumber.lessThan=" + DEFAULT_PHONE_NUMBER);

        // Get all the custContactCharList where phoneNumber is less than UPDATED_PHONE_NUMBER
        defaultCustContactCharShouldBeFound("phoneNumber.lessThan=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByPhoneNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where phoneNumber is greater than DEFAULT_PHONE_NUMBER
        defaultCustContactCharShouldNotBeFound("phoneNumber.greaterThan=" + DEFAULT_PHONE_NUMBER);

        // Get all the custContactCharList where phoneNumber is greater than SMALLER_PHONE_NUMBER
        defaultCustContactCharShouldBeFound("phoneNumber.greaterThan=" + SMALLER_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByEmailAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where emailAddress equals to DEFAULT_EMAIL_ADDRESS
        defaultCustContactCharShouldBeFound("emailAddress.equals=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the custContactCharList where emailAddress equals to UPDATED_EMAIL_ADDRESS
        defaultCustContactCharShouldNotBeFound("emailAddress.equals=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByEmailAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where emailAddress not equals to DEFAULT_EMAIL_ADDRESS
        defaultCustContactCharShouldNotBeFound("emailAddress.notEquals=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the custContactCharList where emailAddress not equals to UPDATED_EMAIL_ADDRESS
        defaultCustContactCharShouldBeFound("emailAddress.notEquals=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByEmailAddressIsInShouldWork() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where emailAddress in DEFAULT_EMAIL_ADDRESS or UPDATED_EMAIL_ADDRESS
        defaultCustContactCharShouldBeFound("emailAddress.in=" + DEFAULT_EMAIL_ADDRESS + "," + UPDATED_EMAIL_ADDRESS);

        // Get all the custContactCharList where emailAddress equals to UPDATED_EMAIL_ADDRESS
        defaultCustContactCharShouldNotBeFound("emailAddress.in=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByEmailAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where emailAddress is not null
        defaultCustContactCharShouldBeFound("emailAddress.specified=true");

        // Get all the custContactCharList where emailAddress is null
        defaultCustContactCharShouldNotBeFound("emailAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllCustContactCharsByEmailAddressContainsSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where emailAddress contains DEFAULT_EMAIL_ADDRESS
        defaultCustContactCharShouldBeFound("emailAddress.contains=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the custContactCharList where emailAddress contains UPDATED_EMAIL_ADDRESS
        defaultCustContactCharShouldNotBeFound("emailAddress.contains=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByEmailAddressNotContainsSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where emailAddress does not contain DEFAULT_EMAIL_ADDRESS
        defaultCustContactCharShouldNotBeFound("emailAddress.doesNotContain=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the custContactCharList where emailAddress does not contain UPDATED_EMAIL_ADDRESS
        defaultCustContactCharShouldBeFound("emailAddress.doesNotContain=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByFaxNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where faxNumber equals to DEFAULT_FAX_NUMBER
        defaultCustContactCharShouldBeFound("faxNumber.equals=" + DEFAULT_FAX_NUMBER);

        // Get all the custContactCharList where faxNumber equals to UPDATED_FAX_NUMBER
        defaultCustContactCharShouldNotBeFound("faxNumber.equals=" + UPDATED_FAX_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByFaxNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where faxNumber not equals to DEFAULT_FAX_NUMBER
        defaultCustContactCharShouldNotBeFound("faxNumber.notEquals=" + DEFAULT_FAX_NUMBER);

        // Get all the custContactCharList where faxNumber not equals to UPDATED_FAX_NUMBER
        defaultCustContactCharShouldBeFound("faxNumber.notEquals=" + UPDATED_FAX_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByFaxNumberIsInShouldWork() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where faxNumber in DEFAULT_FAX_NUMBER or UPDATED_FAX_NUMBER
        defaultCustContactCharShouldBeFound("faxNumber.in=" + DEFAULT_FAX_NUMBER + "," + UPDATED_FAX_NUMBER);

        // Get all the custContactCharList where faxNumber equals to UPDATED_FAX_NUMBER
        defaultCustContactCharShouldNotBeFound("faxNumber.in=" + UPDATED_FAX_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByFaxNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where faxNumber is not null
        defaultCustContactCharShouldBeFound("faxNumber.specified=true");

        // Get all the custContactCharList where faxNumber is null
        defaultCustContactCharShouldNotBeFound("faxNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllCustContactCharsByFaxNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where faxNumber is greater than or equal to DEFAULT_FAX_NUMBER
        defaultCustContactCharShouldBeFound("faxNumber.greaterThanOrEqual=" + DEFAULT_FAX_NUMBER);

        // Get all the custContactCharList where faxNumber is greater than or equal to UPDATED_FAX_NUMBER
        defaultCustContactCharShouldNotBeFound("faxNumber.greaterThanOrEqual=" + UPDATED_FAX_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByFaxNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where faxNumber is less than or equal to DEFAULT_FAX_NUMBER
        defaultCustContactCharShouldBeFound("faxNumber.lessThanOrEqual=" + DEFAULT_FAX_NUMBER);

        // Get all the custContactCharList where faxNumber is less than or equal to SMALLER_FAX_NUMBER
        defaultCustContactCharShouldNotBeFound("faxNumber.lessThanOrEqual=" + SMALLER_FAX_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByFaxNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where faxNumber is less than DEFAULT_FAX_NUMBER
        defaultCustContactCharShouldNotBeFound("faxNumber.lessThan=" + DEFAULT_FAX_NUMBER);

        // Get all the custContactCharList where faxNumber is less than UPDATED_FAX_NUMBER
        defaultCustContactCharShouldBeFound("faxNumber.lessThan=" + UPDATED_FAX_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByFaxNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where faxNumber is greater than DEFAULT_FAX_NUMBER
        defaultCustContactCharShouldNotBeFound("faxNumber.greaterThan=" + DEFAULT_FAX_NUMBER);

        // Get all the custContactCharList where faxNumber is greater than SMALLER_FAX_NUMBER
        defaultCustContactCharShouldBeFound("faxNumber.greaterThan=" + SMALLER_FAX_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByLatitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where latitude equals to DEFAULT_LATITUDE
        defaultCustContactCharShouldBeFound("latitude.equals=" + DEFAULT_LATITUDE);

        // Get all the custContactCharList where latitude equals to UPDATED_LATITUDE
        defaultCustContactCharShouldNotBeFound("latitude.equals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByLatitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where latitude not equals to DEFAULT_LATITUDE
        defaultCustContactCharShouldNotBeFound("latitude.notEquals=" + DEFAULT_LATITUDE);

        // Get all the custContactCharList where latitude not equals to UPDATED_LATITUDE
        defaultCustContactCharShouldBeFound("latitude.notEquals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByLatitudeIsInShouldWork() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where latitude in DEFAULT_LATITUDE or UPDATED_LATITUDE
        defaultCustContactCharShouldBeFound("latitude.in=" + DEFAULT_LATITUDE + "," + UPDATED_LATITUDE);

        // Get all the custContactCharList where latitude equals to UPDATED_LATITUDE
        defaultCustContactCharShouldNotBeFound("latitude.in=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByLatitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where latitude is not null
        defaultCustContactCharShouldBeFound("latitude.specified=true");

        // Get all the custContactCharList where latitude is null
        defaultCustContactCharShouldNotBeFound("latitude.specified=false");
    }

    @Test
    @Transactional
    void getAllCustContactCharsByLatitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where latitude is greater than or equal to DEFAULT_LATITUDE
        defaultCustContactCharShouldBeFound("latitude.greaterThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the custContactCharList where latitude is greater than or equal to UPDATED_LATITUDE
        defaultCustContactCharShouldNotBeFound("latitude.greaterThanOrEqual=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByLatitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where latitude is less than or equal to DEFAULT_LATITUDE
        defaultCustContactCharShouldBeFound("latitude.lessThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the custContactCharList where latitude is less than or equal to SMALLER_LATITUDE
        defaultCustContactCharShouldNotBeFound("latitude.lessThanOrEqual=" + SMALLER_LATITUDE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByLatitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where latitude is less than DEFAULT_LATITUDE
        defaultCustContactCharShouldNotBeFound("latitude.lessThan=" + DEFAULT_LATITUDE);

        // Get all the custContactCharList where latitude is less than UPDATED_LATITUDE
        defaultCustContactCharShouldBeFound("latitude.lessThan=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByLatitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where latitude is greater than DEFAULT_LATITUDE
        defaultCustContactCharShouldNotBeFound("latitude.greaterThan=" + DEFAULT_LATITUDE);

        // Get all the custContactCharList where latitude is greater than SMALLER_LATITUDE
        defaultCustContactCharShouldBeFound("latitude.greaterThan=" + SMALLER_LATITUDE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where longitude equals to DEFAULT_LONGITUDE
        defaultCustContactCharShouldBeFound("longitude.equals=" + DEFAULT_LONGITUDE);

        // Get all the custContactCharList where longitude equals to UPDATED_LONGITUDE
        defaultCustContactCharShouldNotBeFound("longitude.equals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByLongitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where longitude not equals to DEFAULT_LONGITUDE
        defaultCustContactCharShouldNotBeFound("longitude.notEquals=" + DEFAULT_LONGITUDE);

        // Get all the custContactCharList where longitude not equals to UPDATED_LONGITUDE
        defaultCustContactCharShouldBeFound("longitude.notEquals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where longitude in DEFAULT_LONGITUDE or UPDATED_LONGITUDE
        defaultCustContactCharShouldBeFound("longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE);

        // Get all the custContactCharList where longitude equals to UPDATED_LONGITUDE
        defaultCustContactCharShouldNotBeFound("longitude.in=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where longitude is not null
        defaultCustContactCharShouldBeFound("longitude.specified=true");

        // Get all the custContactCharList where longitude is null
        defaultCustContactCharShouldNotBeFound("longitude.specified=false");
    }

    @Test
    @Transactional
    void getAllCustContactCharsByLongitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where longitude is greater than or equal to DEFAULT_LONGITUDE
        defaultCustContactCharShouldBeFound("longitude.greaterThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the custContactCharList where longitude is greater than or equal to UPDATED_LONGITUDE
        defaultCustContactCharShouldNotBeFound("longitude.greaterThanOrEqual=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByLongitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where longitude is less than or equal to DEFAULT_LONGITUDE
        defaultCustContactCharShouldBeFound("longitude.lessThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the custContactCharList where longitude is less than or equal to SMALLER_LONGITUDE
        defaultCustContactCharShouldNotBeFound("longitude.lessThanOrEqual=" + SMALLER_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByLongitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where longitude is less than DEFAULT_LONGITUDE
        defaultCustContactCharShouldNotBeFound("longitude.lessThan=" + DEFAULT_LONGITUDE);

        // Get all the custContactCharList where longitude is less than UPDATED_LONGITUDE
        defaultCustContactCharShouldBeFound("longitude.lessThan=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByLongitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where longitude is greater than DEFAULT_LONGITUDE
        defaultCustContactCharShouldNotBeFound("longitude.greaterThan=" + DEFAULT_LONGITUDE);

        // Get all the custContactCharList where longitude is greater than SMALLER_LONGITUDE
        defaultCustContactCharShouldBeFound("longitude.greaterThan=" + SMALLER_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllCustContactCharsBySvContactIdIsEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where svContactId equals to DEFAULT_SV_CONTACT_ID
        defaultCustContactCharShouldBeFound("svContactId.equals=" + DEFAULT_SV_CONTACT_ID);

        // Get all the custContactCharList where svContactId equals to UPDATED_SV_CONTACT_ID
        defaultCustContactCharShouldNotBeFound("svContactId.equals=" + UPDATED_SV_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllCustContactCharsBySvContactIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where svContactId not equals to DEFAULT_SV_CONTACT_ID
        defaultCustContactCharShouldNotBeFound("svContactId.notEquals=" + DEFAULT_SV_CONTACT_ID);

        // Get all the custContactCharList where svContactId not equals to UPDATED_SV_CONTACT_ID
        defaultCustContactCharShouldBeFound("svContactId.notEquals=" + UPDATED_SV_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllCustContactCharsBySvContactIdIsInShouldWork() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where svContactId in DEFAULT_SV_CONTACT_ID or UPDATED_SV_CONTACT_ID
        defaultCustContactCharShouldBeFound("svContactId.in=" + DEFAULT_SV_CONTACT_ID + "," + UPDATED_SV_CONTACT_ID);

        // Get all the custContactCharList where svContactId equals to UPDATED_SV_CONTACT_ID
        defaultCustContactCharShouldNotBeFound("svContactId.in=" + UPDATED_SV_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllCustContactCharsBySvContactIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where svContactId is not null
        defaultCustContactCharShouldBeFound("svContactId.specified=true");

        // Get all the custContactCharList where svContactId is null
        defaultCustContactCharShouldNotBeFound("svContactId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustContactCharsBySvContactIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where svContactId is greater than or equal to DEFAULT_SV_CONTACT_ID
        defaultCustContactCharShouldBeFound("svContactId.greaterThanOrEqual=" + DEFAULT_SV_CONTACT_ID);

        // Get all the custContactCharList where svContactId is greater than or equal to UPDATED_SV_CONTACT_ID
        defaultCustContactCharShouldNotBeFound("svContactId.greaterThanOrEqual=" + UPDATED_SV_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllCustContactCharsBySvContactIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where svContactId is less than or equal to DEFAULT_SV_CONTACT_ID
        defaultCustContactCharShouldBeFound("svContactId.lessThanOrEqual=" + DEFAULT_SV_CONTACT_ID);

        // Get all the custContactCharList where svContactId is less than or equal to SMALLER_SV_CONTACT_ID
        defaultCustContactCharShouldNotBeFound("svContactId.lessThanOrEqual=" + SMALLER_SV_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllCustContactCharsBySvContactIdIsLessThanSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where svContactId is less than DEFAULT_SV_CONTACT_ID
        defaultCustContactCharShouldNotBeFound("svContactId.lessThan=" + DEFAULT_SV_CONTACT_ID);

        // Get all the custContactCharList where svContactId is less than UPDATED_SV_CONTACT_ID
        defaultCustContactCharShouldBeFound("svContactId.lessThan=" + UPDATED_SV_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllCustContactCharsBySvContactIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where svContactId is greater than DEFAULT_SV_CONTACT_ID
        defaultCustContactCharShouldNotBeFound("svContactId.greaterThan=" + DEFAULT_SV_CONTACT_ID);

        // Get all the custContactCharList where svContactId is greater than SMALLER_SV_CONTACT_ID
        defaultCustContactCharShouldBeFound("svContactId.greaterThan=" + SMALLER_SV_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByIsEmailValidIsEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where isEmailValid equals to DEFAULT_IS_EMAIL_VALID
        defaultCustContactCharShouldBeFound("isEmailValid.equals=" + DEFAULT_IS_EMAIL_VALID);

        // Get all the custContactCharList where isEmailValid equals to UPDATED_IS_EMAIL_VALID
        defaultCustContactCharShouldNotBeFound("isEmailValid.equals=" + UPDATED_IS_EMAIL_VALID);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByIsEmailValidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where isEmailValid not equals to DEFAULT_IS_EMAIL_VALID
        defaultCustContactCharShouldNotBeFound("isEmailValid.notEquals=" + DEFAULT_IS_EMAIL_VALID);

        // Get all the custContactCharList where isEmailValid not equals to UPDATED_IS_EMAIL_VALID
        defaultCustContactCharShouldBeFound("isEmailValid.notEquals=" + UPDATED_IS_EMAIL_VALID);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByIsEmailValidIsInShouldWork() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where isEmailValid in DEFAULT_IS_EMAIL_VALID or UPDATED_IS_EMAIL_VALID
        defaultCustContactCharShouldBeFound("isEmailValid.in=" + DEFAULT_IS_EMAIL_VALID + "," + UPDATED_IS_EMAIL_VALID);

        // Get all the custContactCharList where isEmailValid equals to UPDATED_IS_EMAIL_VALID
        defaultCustContactCharShouldNotBeFound("isEmailValid.in=" + UPDATED_IS_EMAIL_VALID);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByIsEmailValidIsNullOrNotNull() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where isEmailValid is not null
        defaultCustContactCharShouldBeFound("isEmailValid.specified=true");

        // Get all the custContactCharList where isEmailValid is null
        defaultCustContactCharShouldNotBeFound("isEmailValid.specified=false");
    }

    @Test
    @Transactional
    void getAllCustContactCharsByIsAddressValidIsEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where isAddressValid equals to DEFAULT_IS_ADDRESS_VALID
        defaultCustContactCharShouldBeFound("isAddressValid.equals=" + DEFAULT_IS_ADDRESS_VALID);

        // Get all the custContactCharList where isAddressValid equals to UPDATED_IS_ADDRESS_VALID
        defaultCustContactCharShouldNotBeFound("isAddressValid.equals=" + UPDATED_IS_ADDRESS_VALID);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByIsAddressValidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where isAddressValid not equals to DEFAULT_IS_ADDRESS_VALID
        defaultCustContactCharShouldNotBeFound("isAddressValid.notEquals=" + DEFAULT_IS_ADDRESS_VALID);

        // Get all the custContactCharList where isAddressValid not equals to UPDATED_IS_ADDRESS_VALID
        defaultCustContactCharShouldBeFound("isAddressValid.notEquals=" + UPDATED_IS_ADDRESS_VALID);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByIsAddressValidIsInShouldWork() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where isAddressValid in DEFAULT_IS_ADDRESS_VALID or UPDATED_IS_ADDRESS_VALID
        defaultCustContactCharShouldBeFound("isAddressValid.in=" + DEFAULT_IS_ADDRESS_VALID + "," + UPDATED_IS_ADDRESS_VALID);

        // Get all the custContactCharList where isAddressValid equals to UPDATED_IS_ADDRESS_VALID
        defaultCustContactCharShouldNotBeFound("isAddressValid.in=" + UPDATED_IS_ADDRESS_VALID);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByIsAddressValidIsNullOrNotNull() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where isAddressValid is not null
        defaultCustContactCharShouldBeFound("isAddressValid.specified=true");

        // Get all the custContactCharList where isAddressValid is null
        defaultCustContactCharShouldNotBeFound("isAddressValid.specified=false");
    }

    @Test
    @Transactional
    void getAllCustContactCharsByCustConMediumIdIsEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where custConMediumId equals to DEFAULT_CUST_CON_MEDIUM_ID
        defaultCustContactCharShouldBeFound("custConMediumId.equals=" + DEFAULT_CUST_CON_MEDIUM_ID);

        // Get all the custContactCharList where custConMediumId equals to UPDATED_CUST_CON_MEDIUM_ID
        defaultCustContactCharShouldNotBeFound("custConMediumId.equals=" + UPDATED_CUST_CON_MEDIUM_ID);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByCustConMediumIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where custConMediumId not equals to DEFAULT_CUST_CON_MEDIUM_ID
        defaultCustContactCharShouldNotBeFound("custConMediumId.notEquals=" + DEFAULT_CUST_CON_MEDIUM_ID);

        // Get all the custContactCharList where custConMediumId not equals to UPDATED_CUST_CON_MEDIUM_ID
        defaultCustContactCharShouldBeFound("custConMediumId.notEquals=" + UPDATED_CUST_CON_MEDIUM_ID);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByCustConMediumIdIsInShouldWork() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where custConMediumId in DEFAULT_CUST_CON_MEDIUM_ID or UPDATED_CUST_CON_MEDIUM_ID
        defaultCustContactCharShouldBeFound("custConMediumId.in=" + DEFAULT_CUST_CON_MEDIUM_ID + "," + UPDATED_CUST_CON_MEDIUM_ID);

        // Get all the custContactCharList where custConMediumId equals to UPDATED_CUST_CON_MEDIUM_ID
        defaultCustContactCharShouldNotBeFound("custConMediumId.in=" + UPDATED_CUST_CON_MEDIUM_ID);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByCustConMediumIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where custConMediumId is not null
        defaultCustContactCharShouldBeFound("custConMediumId.specified=true");

        // Get all the custContactCharList where custConMediumId is null
        defaultCustContactCharShouldNotBeFound("custConMediumId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustContactCharsByCustConMediumIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where custConMediumId is greater than or equal to DEFAULT_CUST_CON_MEDIUM_ID
        defaultCustContactCharShouldBeFound("custConMediumId.greaterThanOrEqual=" + DEFAULT_CUST_CON_MEDIUM_ID);

        // Get all the custContactCharList where custConMediumId is greater than or equal to UPDATED_CUST_CON_MEDIUM_ID
        defaultCustContactCharShouldNotBeFound("custConMediumId.greaterThanOrEqual=" + UPDATED_CUST_CON_MEDIUM_ID);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByCustConMediumIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where custConMediumId is less than or equal to DEFAULT_CUST_CON_MEDIUM_ID
        defaultCustContactCharShouldBeFound("custConMediumId.lessThanOrEqual=" + DEFAULT_CUST_CON_MEDIUM_ID);

        // Get all the custContactCharList where custConMediumId is less than or equal to SMALLER_CUST_CON_MEDIUM_ID
        defaultCustContactCharShouldNotBeFound("custConMediumId.lessThanOrEqual=" + SMALLER_CUST_CON_MEDIUM_ID);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByCustConMediumIdIsLessThanSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where custConMediumId is less than DEFAULT_CUST_CON_MEDIUM_ID
        defaultCustContactCharShouldNotBeFound("custConMediumId.lessThan=" + DEFAULT_CUST_CON_MEDIUM_ID);

        // Get all the custContactCharList where custConMediumId is less than UPDATED_CUST_CON_MEDIUM_ID
        defaultCustContactCharShouldBeFound("custConMediumId.lessThan=" + UPDATED_CUST_CON_MEDIUM_ID);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByCustConMediumIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        // Get all the custContactCharList where custConMediumId is greater than DEFAULT_CUST_CON_MEDIUM_ID
        defaultCustContactCharShouldNotBeFound("custConMediumId.greaterThan=" + DEFAULT_CUST_CON_MEDIUM_ID);

        // Get all the custContactCharList where custConMediumId is greater than SMALLER_CUST_CON_MEDIUM_ID
        defaultCustContactCharShouldBeFound("custConMediumId.greaterThan=" + SMALLER_CUST_CON_MEDIUM_ID);
    }

    @Test
    @Transactional
    void getAllCustContactCharsByCustContactIsEqualToSomething() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);
        CustContact custContact = CustContactResourceIT.createEntity(em);
        em.persist(custContact);
        em.flush();
        custContactChar.setCustContact(custContact);
        custContactCharRepository.saveAndFlush(custContactChar);
        Long custContactId = custContact.getId();

        // Get all the custContactCharList where custContact equals to custContactId
        defaultCustContactCharShouldBeFound("custContactId.equals=" + custContactId);

        // Get all the custContactCharList where custContact equals to (custContactId + 1)
        defaultCustContactCharShouldNotBeFound("custContactId.equals=" + (custContactId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustContactCharShouldBeFound(String filter) throws Exception {
        restCustContactCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custContactChar.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].streetOne").value(hasItem(DEFAULT_STREET_ONE)))
            .andExpect(jsonPath("$.[*].streetTwo").value(hasItem(DEFAULT_STREET_TWO)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].stateOrProvince").value(hasItem(DEFAULT_STATE_OR_PROVINCE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].postCode").value(hasItem(DEFAULT_POST_CODE.intValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].faxNumber").value(hasItem(DEFAULT_FAX_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(sameNumber(DEFAULT_LATITUDE))))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(sameNumber(DEFAULT_LONGITUDE))))
            .andExpect(jsonPath("$.[*].svContactId").value(hasItem(DEFAULT_SV_CONTACT_ID)))
            .andExpect(jsonPath("$.[*].isEmailValid").value(hasItem(DEFAULT_IS_EMAIL_VALID.booleanValue())))
            .andExpect(jsonPath("$.[*].isAddressValid").value(hasItem(DEFAULT_IS_ADDRESS_VALID.booleanValue())))
            .andExpect(jsonPath("$.[*].custConMediumId").value(hasItem(DEFAULT_CUST_CON_MEDIUM_ID)));

        // Check, that the count call also returns 1
        restCustContactCharMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustContactCharShouldNotBeFound(String filter) throws Exception {
        restCustContactCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustContactCharMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustContactChar() throws Exception {
        // Get the custContactChar
        restCustContactCharMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustContactChar() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        int databaseSizeBeforeUpdate = custContactCharRepository.findAll().size();

        // Update the custContactChar
        CustContactChar updatedCustContactChar = custContactCharRepository.findById(custContactChar.getId()).get();
        // Disconnect from session so that the updates on updatedCustContactChar are not directly saved in db
        em.detach(updatedCustContactChar);
        updatedCustContactChar
            .type(UPDATED_TYPE)
            .streetOne(UPDATED_STREET_ONE)
            .streetTwo(UPDATED_STREET_TWO)
            .city(UPDATED_CITY)
            .stateOrProvince(UPDATED_STATE_OR_PROVINCE)
            .country(UPDATED_COUNTRY)
            .postCode(UPDATED_POST_CODE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .faxNumber(UPDATED_FAX_NUMBER)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .svContactId(UPDATED_SV_CONTACT_ID)
            .isEmailValid(UPDATED_IS_EMAIL_VALID)
            .isAddressValid(UPDATED_IS_ADDRESS_VALID)
            .custConMediumId(UPDATED_CUST_CON_MEDIUM_ID);
        CustContactCharDTO custContactCharDTO = custContactCharMapper.toDto(updatedCustContactChar);

        restCustContactCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custContactCharDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custContactCharDTO))
            )
            .andExpect(status().isOk());

        // Validate the CustContactChar in the database
        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeUpdate);
        CustContactChar testCustContactChar = custContactCharList.get(custContactCharList.size() - 1);
        assertThat(testCustContactChar.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCustContactChar.getStreetOne()).isEqualTo(UPDATED_STREET_ONE);
        assertThat(testCustContactChar.getStreetTwo()).isEqualTo(UPDATED_STREET_TWO);
        assertThat(testCustContactChar.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCustContactChar.getStateOrProvince()).isEqualTo(UPDATED_STATE_OR_PROVINCE);
        assertThat(testCustContactChar.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testCustContactChar.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testCustContactChar.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testCustContactChar.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testCustContactChar.getFaxNumber()).isEqualTo(UPDATED_FAX_NUMBER);
        assertThat(testCustContactChar.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testCustContactChar.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testCustContactChar.getSvContactId()).isEqualTo(UPDATED_SV_CONTACT_ID);
        assertThat(testCustContactChar.getIsEmailValid()).isEqualTo(UPDATED_IS_EMAIL_VALID);
        assertThat(testCustContactChar.getIsAddressValid()).isEqualTo(UPDATED_IS_ADDRESS_VALID);
        assertThat(testCustContactChar.getCustConMediumId()).isEqualTo(UPDATED_CUST_CON_MEDIUM_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustContactChar() throws Exception {
        int databaseSizeBeforeUpdate = custContactCharRepository.findAll().size();
        custContactChar.setId(count.incrementAndGet());

        // Create the CustContactChar
        CustContactCharDTO custContactCharDTO = custContactCharMapper.toDto(custContactChar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustContactCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custContactCharDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custContactCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustContactChar in the database
        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustContactChar() throws Exception {
        int databaseSizeBeforeUpdate = custContactCharRepository.findAll().size();
        custContactChar.setId(count.incrementAndGet());

        // Create the CustContactChar
        CustContactCharDTO custContactCharDTO = custContactCharMapper.toDto(custContactChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustContactCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custContactCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustContactChar in the database
        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustContactChar() throws Exception {
        int databaseSizeBeforeUpdate = custContactCharRepository.findAll().size();
        custContactChar.setId(count.incrementAndGet());

        // Create the CustContactChar
        CustContactCharDTO custContactCharDTO = custContactCharMapper.toDto(custContactChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustContactCharMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custContactCharDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustContactChar in the database
        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustContactCharWithPatch() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        int databaseSizeBeforeUpdate = custContactCharRepository.findAll().size();

        // Update the custContactChar using partial update
        CustContactChar partialUpdatedCustContactChar = new CustContactChar();
        partialUpdatedCustContactChar.setId(custContactChar.getId());

        partialUpdatedCustContactChar
            .streetOne(UPDATED_STREET_ONE)
            .postCode(UPDATED_POST_CODE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .faxNumber(UPDATED_FAX_NUMBER)
            .custConMediumId(UPDATED_CUST_CON_MEDIUM_ID);

        restCustContactCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustContactChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustContactChar))
            )
            .andExpect(status().isOk());

        // Validate the CustContactChar in the database
        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeUpdate);
        CustContactChar testCustContactChar = custContactCharList.get(custContactCharList.size() - 1);
        assertThat(testCustContactChar.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCustContactChar.getStreetOne()).isEqualTo(UPDATED_STREET_ONE);
        assertThat(testCustContactChar.getStreetTwo()).isEqualTo(DEFAULT_STREET_TWO);
        assertThat(testCustContactChar.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testCustContactChar.getStateOrProvince()).isEqualTo(DEFAULT_STATE_OR_PROVINCE);
        assertThat(testCustContactChar.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testCustContactChar.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testCustContactChar.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testCustContactChar.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testCustContactChar.getFaxNumber()).isEqualTo(UPDATED_FAX_NUMBER);
        assertThat(testCustContactChar.getLatitude()).isEqualByComparingTo(DEFAULT_LATITUDE);
        assertThat(testCustContactChar.getLongitude()).isEqualByComparingTo(DEFAULT_LONGITUDE);
        assertThat(testCustContactChar.getSvContactId()).isEqualTo(DEFAULT_SV_CONTACT_ID);
        assertThat(testCustContactChar.getIsEmailValid()).isEqualTo(DEFAULT_IS_EMAIL_VALID);
        assertThat(testCustContactChar.getIsAddressValid()).isEqualTo(DEFAULT_IS_ADDRESS_VALID);
        assertThat(testCustContactChar.getCustConMediumId()).isEqualTo(UPDATED_CUST_CON_MEDIUM_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustContactCharWithPatch() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        int databaseSizeBeforeUpdate = custContactCharRepository.findAll().size();

        // Update the custContactChar using partial update
        CustContactChar partialUpdatedCustContactChar = new CustContactChar();
        partialUpdatedCustContactChar.setId(custContactChar.getId());

        partialUpdatedCustContactChar
            .type(UPDATED_TYPE)
            .streetOne(UPDATED_STREET_ONE)
            .streetTwo(UPDATED_STREET_TWO)
            .city(UPDATED_CITY)
            .stateOrProvince(UPDATED_STATE_OR_PROVINCE)
            .country(UPDATED_COUNTRY)
            .postCode(UPDATED_POST_CODE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .faxNumber(UPDATED_FAX_NUMBER)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .svContactId(UPDATED_SV_CONTACT_ID)
            .isEmailValid(UPDATED_IS_EMAIL_VALID)
            .isAddressValid(UPDATED_IS_ADDRESS_VALID)
            .custConMediumId(UPDATED_CUST_CON_MEDIUM_ID);

        restCustContactCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustContactChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustContactChar))
            )
            .andExpect(status().isOk());

        // Validate the CustContactChar in the database
        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeUpdate);
        CustContactChar testCustContactChar = custContactCharList.get(custContactCharList.size() - 1);
        assertThat(testCustContactChar.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCustContactChar.getStreetOne()).isEqualTo(UPDATED_STREET_ONE);
        assertThat(testCustContactChar.getStreetTwo()).isEqualTo(UPDATED_STREET_TWO);
        assertThat(testCustContactChar.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testCustContactChar.getStateOrProvince()).isEqualTo(UPDATED_STATE_OR_PROVINCE);
        assertThat(testCustContactChar.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testCustContactChar.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testCustContactChar.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testCustContactChar.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testCustContactChar.getFaxNumber()).isEqualTo(UPDATED_FAX_NUMBER);
        assertThat(testCustContactChar.getLatitude()).isEqualByComparingTo(UPDATED_LATITUDE);
        assertThat(testCustContactChar.getLongitude()).isEqualByComparingTo(UPDATED_LONGITUDE);
        assertThat(testCustContactChar.getSvContactId()).isEqualTo(UPDATED_SV_CONTACT_ID);
        assertThat(testCustContactChar.getIsEmailValid()).isEqualTo(UPDATED_IS_EMAIL_VALID);
        assertThat(testCustContactChar.getIsAddressValid()).isEqualTo(UPDATED_IS_ADDRESS_VALID);
        assertThat(testCustContactChar.getCustConMediumId()).isEqualTo(UPDATED_CUST_CON_MEDIUM_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustContactChar() throws Exception {
        int databaseSizeBeforeUpdate = custContactCharRepository.findAll().size();
        custContactChar.setId(count.incrementAndGet());

        // Create the CustContactChar
        CustContactCharDTO custContactCharDTO = custContactCharMapper.toDto(custContactChar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustContactCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custContactCharDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custContactCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustContactChar in the database
        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustContactChar() throws Exception {
        int databaseSizeBeforeUpdate = custContactCharRepository.findAll().size();
        custContactChar.setId(count.incrementAndGet());

        // Create the CustContactChar
        CustContactCharDTO custContactCharDTO = custContactCharMapper.toDto(custContactChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustContactCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custContactCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustContactChar in the database
        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustContactChar() throws Exception {
        int databaseSizeBeforeUpdate = custContactCharRepository.findAll().size();
        custContactChar.setId(count.incrementAndGet());

        // Create the CustContactChar
        CustContactCharDTO custContactCharDTO = custContactCharMapper.toDto(custContactChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustContactCharMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custContactCharDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustContactChar in the database
        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustContactChar() throws Exception {
        // Initialize the database
        custContactCharRepository.saveAndFlush(custContactChar);

        int databaseSizeBeforeDelete = custContactCharRepository.findAll().size();

        // Delete the custContactChar
        restCustContactCharMockMvc
            .perform(delete(ENTITY_API_URL_ID, custContactChar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustContactChar> custContactCharList = custContactCharRepository.findAll();
        assertThat(custContactCharList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
