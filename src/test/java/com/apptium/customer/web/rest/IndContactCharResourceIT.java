package com.apptium.customer.web.rest;

import static com.apptium.customer.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.IndContact;
import com.apptium.customer.domain.IndContactChar;
import com.apptium.customer.repository.IndContactCharRepository;
import com.apptium.customer.service.criteria.IndContactCharCriteria;
import com.apptium.customer.service.dto.IndContactCharDTO;
import com.apptium.customer.service.mapper.IndContactCharMapper;
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
 * Integration tests for the {@link IndContactCharResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IndContactCharResourceIT {

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

    private static final Long DEFAULT_SV_CONTACT_ID = 1L;
    private static final Long UPDATED_SV_CONTACT_ID = 2L;
    private static final Long SMALLER_SV_CONTACT_ID = 1L - 1L;

    private static final Boolean DEFAULT_IS_EMAIL_VALID = false;
    private static final Boolean UPDATED_IS_EMAIL_VALID = true;

    private static final Boolean DEFAULT_IS_ADDRESS_VALID = false;
    private static final Boolean UPDATED_IS_ADDRESS_VALID = true;

    private static final Long DEFAULT_IND_CON_ID = 1L;
    private static final Long UPDATED_IND_CON_ID = 2L;
    private static final Long SMALLER_IND_CON_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/ind-contact-chars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IndContactCharRepository indContactCharRepository;

    @Autowired
    private IndContactCharMapper indContactCharMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndContactCharMockMvc;

    private IndContactChar indContactChar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndContactChar createEntity(EntityManager em) {
        IndContactChar indContactChar = new IndContactChar()
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
            .indConId(DEFAULT_IND_CON_ID);
        return indContactChar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndContactChar createUpdatedEntity(EntityManager em) {
        IndContactChar indContactChar = new IndContactChar()
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
            .indConId(UPDATED_IND_CON_ID);
        return indContactChar;
    }

    @BeforeEach
    public void initTest() {
        indContactChar = createEntity(em);
    }

    @Test
    @Transactional
    void createIndContactChar() throws Exception {
        int databaseSizeBeforeCreate = indContactCharRepository.findAll().size();
        // Create the IndContactChar
        IndContactCharDTO indContactCharDTO = indContactCharMapper.toDto(indContactChar);
        restIndContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactCharDTO))
            )
            .andExpect(status().isCreated());

        // Validate the IndContactChar in the database
        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeCreate + 1);
        IndContactChar testIndContactChar = indContactCharList.get(indContactCharList.size() - 1);
        assertThat(testIndContactChar.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testIndContactChar.getStreetOne()).isEqualTo(DEFAULT_STREET_ONE);
        assertThat(testIndContactChar.getStreetTwo()).isEqualTo(DEFAULT_STREET_TWO);
        assertThat(testIndContactChar.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testIndContactChar.getStateOrProvince()).isEqualTo(DEFAULT_STATE_OR_PROVINCE);
        assertThat(testIndContactChar.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testIndContactChar.getPostCode()).isEqualTo(DEFAULT_POST_CODE);
        assertThat(testIndContactChar.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testIndContactChar.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testIndContactChar.getFaxNumber()).isEqualTo(DEFAULT_FAX_NUMBER);
        assertThat(testIndContactChar.getLatitude()).isEqualByComparingTo(DEFAULT_LATITUDE);
        assertThat(testIndContactChar.getLongitude()).isEqualByComparingTo(DEFAULT_LONGITUDE);
        assertThat(testIndContactChar.getSvContactId()).isEqualTo(DEFAULT_SV_CONTACT_ID);
        assertThat(testIndContactChar.getIsEmailValid()).isEqualTo(DEFAULT_IS_EMAIL_VALID);
        assertThat(testIndContactChar.getIsAddressValid()).isEqualTo(DEFAULT_IS_ADDRESS_VALID);
        assertThat(testIndContactChar.getIndConId()).isEqualTo(DEFAULT_IND_CON_ID);
    }

    @Test
    @Transactional
    void createIndContactCharWithExistingId() throws Exception {
        // Create the IndContactChar with an existing ID
        indContactChar.setId(1L);
        IndContactCharDTO indContactCharDTO = indContactCharMapper.toDto(indContactChar);

        int databaseSizeBeforeCreate = indContactCharRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndContactChar in the database
        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactCharRepository.findAll().size();
        // set the field null
        indContactChar.setType(null);

        // Create the IndContactChar, which fails.
        IndContactCharDTO indContactCharDTO = indContactCharMapper.toDto(indContactChar);

        restIndContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactCharRepository.findAll().size();
        // set the field null
        indContactChar.setCity(null);

        // Create the IndContactChar, which fails.
        IndContactCharDTO indContactCharDTO = indContactCharMapper.toDto(indContactChar);

        restIndContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStateOrProvinceIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactCharRepository.findAll().size();
        // set the field null
        indContactChar.setStateOrProvince(null);

        // Create the IndContactChar, which fails.
        IndContactCharDTO indContactCharDTO = indContactCharMapper.toDto(indContactChar);

        restIndContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactCharRepository.findAll().size();
        // set the field null
        indContactChar.setCountry(null);

        // Create the IndContactChar, which fails.
        IndContactCharDTO indContactCharDTO = indContactCharMapper.toDto(indContactChar);

        restIndContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPostCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactCharRepository.findAll().size();
        // set the field null
        indContactChar.setPostCode(null);

        // Create the IndContactChar, which fails.
        IndContactCharDTO indContactCharDTO = indContactCharMapper.toDto(indContactChar);

        restIndContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactCharRepository.findAll().size();
        // set the field null
        indContactChar.setPhoneNumber(null);

        // Create the IndContactChar, which fails.
        IndContactCharDTO indContactCharDTO = indContactCharMapper.toDto(indContactChar);

        restIndContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFaxNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactCharRepository.findAll().size();
        // set the field null
        indContactChar.setFaxNumber(null);

        // Create the IndContactChar, which fails.
        IndContactCharDTO indContactCharDTO = indContactCharMapper.toDto(indContactChar);

        restIndContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactCharRepository.findAll().size();
        // set the field null
        indContactChar.setLatitude(null);

        // Create the IndContactChar, which fails.
        IndContactCharDTO indContactCharDTO = indContactCharMapper.toDto(indContactChar);

        restIndContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactCharRepository.findAll().size();
        // set the field null
        indContactChar.setLongitude(null);

        // Create the IndContactChar, which fails.
        IndContactCharDTO indContactCharDTO = indContactCharMapper.toDto(indContactChar);

        restIndContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSvContactIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactCharRepository.findAll().size();
        // set the field null
        indContactChar.setSvContactId(null);

        // Create the IndContactChar, which fails.
        IndContactCharDTO indContactCharDTO = indContactCharMapper.toDto(indContactChar);

        restIndContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIndConIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = indContactCharRepository.findAll().size();
        // set the field null
        indContactChar.setIndConId(null);

        // Create the IndContactChar, which fails.
        IndContactCharDTO indContactCharDTO = indContactCharMapper.toDto(indContactChar);

        restIndContactCharMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactCharDTO))
            )
            .andExpect(status().isBadRequest());

        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIndContactChars() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList
        restIndContactCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indContactChar.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].svContactId").value(hasItem(DEFAULT_SV_CONTACT_ID.intValue())))
            .andExpect(jsonPath("$.[*].isEmailValid").value(hasItem(DEFAULT_IS_EMAIL_VALID.booleanValue())))
            .andExpect(jsonPath("$.[*].isAddressValid").value(hasItem(DEFAULT_IS_ADDRESS_VALID.booleanValue())))
            .andExpect(jsonPath("$.[*].indConId").value(hasItem(DEFAULT_IND_CON_ID.intValue())));
    }

    @Test
    @Transactional
    void getIndContactChar() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get the indContactChar
        restIndContactCharMockMvc
            .perform(get(ENTITY_API_URL_ID, indContactChar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(indContactChar.getId().intValue()))
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
            .andExpect(jsonPath("$.svContactId").value(DEFAULT_SV_CONTACT_ID.intValue()))
            .andExpect(jsonPath("$.isEmailValid").value(DEFAULT_IS_EMAIL_VALID.booleanValue()))
            .andExpect(jsonPath("$.isAddressValid").value(DEFAULT_IS_ADDRESS_VALID.booleanValue()))
            .andExpect(jsonPath("$.indConId").value(DEFAULT_IND_CON_ID.intValue()));
    }

    @Test
    @Transactional
    void getIndContactCharsByIdFiltering() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        Long id = indContactChar.getId();

        defaultIndContactCharShouldBeFound("id.equals=" + id);
        defaultIndContactCharShouldNotBeFound("id.notEquals=" + id);

        defaultIndContactCharShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultIndContactCharShouldNotBeFound("id.greaterThan=" + id);

        defaultIndContactCharShouldBeFound("id.lessThanOrEqual=" + id);
        defaultIndContactCharShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where type equals to DEFAULT_TYPE
        defaultIndContactCharShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the indContactCharList where type equals to UPDATED_TYPE
        defaultIndContactCharShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where type not equals to DEFAULT_TYPE
        defaultIndContactCharShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the indContactCharList where type not equals to UPDATED_TYPE
        defaultIndContactCharShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultIndContactCharShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the indContactCharList where type equals to UPDATED_TYPE
        defaultIndContactCharShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where type is not null
        defaultIndContactCharShouldBeFound("type.specified=true");

        // Get all the indContactCharList where type is null
        defaultIndContactCharShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllIndContactCharsByTypeContainsSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where type contains DEFAULT_TYPE
        defaultIndContactCharShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the indContactCharList where type contains UPDATED_TYPE
        defaultIndContactCharShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where type does not contain DEFAULT_TYPE
        defaultIndContactCharShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the indContactCharList where type does not contain UPDATED_TYPE
        defaultIndContactCharShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByStreetOneIsEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where streetOne equals to DEFAULT_STREET_ONE
        defaultIndContactCharShouldBeFound("streetOne.equals=" + DEFAULT_STREET_ONE);

        // Get all the indContactCharList where streetOne equals to UPDATED_STREET_ONE
        defaultIndContactCharShouldNotBeFound("streetOne.equals=" + UPDATED_STREET_ONE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByStreetOneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where streetOne not equals to DEFAULT_STREET_ONE
        defaultIndContactCharShouldNotBeFound("streetOne.notEquals=" + DEFAULT_STREET_ONE);

        // Get all the indContactCharList where streetOne not equals to UPDATED_STREET_ONE
        defaultIndContactCharShouldBeFound("streetOne.notEquals=" + UPDATED_STREET_ONE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByStreetOneIsInShouldWork() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where streetOne in DEFAULT_STREET_ONE or UPDATED_STREET_ONE
        defaultIndContactCharShouldBeFound("streetOne.in=" + DEFAULT_STREET_ONE + "," + UPDATED_STREET_ONE);

        // Get all the indContactCharList where streetOne equals to UPDATED_STREET_ONE
        defaultIndContactCharShouldNotBeFound("streetOne.in=" + UPDATED_STREET_ONE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByStreetOneIsNullOrNotNull() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where streetOne is not null
        defaultIndContactCharShouldBeFound("streetOne.specified=true");

        // Get all the indContactCharList where streetOne is null
        defaultIndContactCharShouldNotBeFound("streetOne.specified=false");
    }

    @Test
    @Transactional
    void getAllIndContactCharsByStreetOneContainsSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where streetOne contains DEFAULT_STREET_ONE
        defaultIndContactCharShouldBeFound("streetOne.contains=" + DEFAULT_STREET_ONE);

        // Get all the indContactCharList where streetOne contains UPDATED_STREET_ONE
        defaultIndContactCharShouldNotBeFound("streetOne.contains=" + UPDATED_STREET_ONE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByStreetOneNotContainsSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where streetOne does not contain DEFAULT_STREET_ONE
        defaultIndContactCharShouldNotBeFound("streetOne.doesNotContain=" + DEFAULT_STREET_ONE);

        // Get all the indContactCharList where streetOne does not contain UPDATED_STREET_ONE
        defaultIndContactCharShouldBeFound("streetOne.doesNotContain=" + UPDATED_STREET_ONE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByStreetTwoIsEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where streetTwo equals to DEFAULT_STREET_TWO
        defaultIndContactCharShouldBeFound("streetTwo.equals=" + DEFAULT_STREET_TWO);

        // Get all the indContactCharList where streetTwo equals to UPDATED_STREET_TWO
        defaultIndContactCharShouldNotBeFound("streetTwo.equals=" + UPDATED_STREET_TWO);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByStreetTwoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where streetTwo not equals to DEFAULT_STREET_TWO
        defaultIndContactCharShouldNotBeFound("streetTwo.notEquals=" + DEFAULT_STREET_TWO);

        // Get all the indContactCharList where streetTwo not equals to UPDATED_STREET_TWO
        defaultIndContactCharShouldBeFound("streetTwo.notEquals=" + UPDATED_STREET_TWO);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByStreetTwoIsInShouldWork() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where streetTwo in DEFAULT_STREET_TWO or UPDATED_STREET_TWO
        defaultIndContactCharShouldBeFound("streetTwo.in=" + DEFAULT_STREET_TWO + "," + UPDATED_STREET_TWO);

        // Get all the indContactCharList where streetTwo equals to UPDATED_STREET_TWO
        defaultIndContactCharShouldNotBeFound("streetTwo.in=" + UPDATED_STREET_TWO);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByStreetTwoIsNullOrNotNull() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where streetTwo is not null
        defaultIndContactCharShouldBeFound("streetTwo.specified=true");

        // Get all the indContactCharList where streetTwo is null
        defaultIndContactCharShouldNotBeFound("streetTwo.specified=false");
    }

    @Test
    @Transactional
    void getAllIndContactCharsByStreetTwoContainsSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where streetTwo contains DEFAULT_STREET_TWO
        defaultIndContactCharShouldBeFound("streetTwo.contains=" + DEFAULT_STREET_TWO);

        // Get all the indContactCharList where streetTwo contains UPDATED_STREET_TWO
        defaultIndContactCharShouldNotBeFound("streetTwo.contains=" + UPDATED_STREET_TWO);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByStreetTwoNotContainsSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where streetTwo does not contain DEFAULT_STREET_TWO
        defaultIndContactCharShouldNotBeFound("streetTwo.doesNotContain=" + DEFAULT_STREET_TWO);

        // Get all the indContactCharList where streetTwo does not contain UPDATED_STREET_TWO
        defaultIndContactCharShouldBeFound("streetTwo.doesNotContain=" + UPDATED_STREET_TWO);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where city equals to DEFAULT_CITY
        defaultIndContactCharShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the indContactCharList where city equals to UPDATED_CITY
        defaultIndContactCharShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByCityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where city not equals to DEFAULT_CITY
        defaultIndContactCharShouldNotBeFound("city.notEquals=" + DEFAULT_CITY);

        // Get all the indContactCharList where city not equals to UPDATED_CITY
        defaultIndContactCharShouldBeFound("city.notEquals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByCityIsInShouldWork() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where city in DEFAULT_CITY or UPDATED_CITY
        defaultIndContactCharShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the indContactCharList where city equals to UPDATED_CITY
        defaultIndContactCharShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where city is not null
        defaultIndContactCharShouldBeFound("city.specified=true");

        // Get all the indContactCharList where city is null
        defaultIndContactCharShouldNotBeFound("city.specified=false");
    }

    @Test
    @Transactional
    void getAllIndContactCharsByCityContainsSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where city contains DEFAULT_CITY
        defaultIndContactCharShouldBeFound("city.contains=" + DEFAULT_CITY);

        // Get all the indContactCharList where city contains UPDATED_CITY
        defaultIndContactCharShouldNotBeFound("city.contains=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByCityNotContainsSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where city does not contain DEFAULT_CITY
        defaultIndContactCharShouldNotBeFound("city.doesNotContain=" + DEFAULT_CITY);

        // Get all the indContactCharList where city does not contain UPDATED_CITY
        defaultIndContactCharShouldBeFound("city.doesNotContain=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByStateOrProvinceIsEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where stateOrProvince equals to DEFAULT_STATE_OR_PROVINCE
        defaultIndContactCharShouldBeFound("stateOrProvince.equals=" + DEFAULT_STATE_OR_PROVINCE);

        // Get all the indContactCharList where stateOrProvince equals to UPDATED_STATE_OR_PROVINCE
        defaultIndContactCharShouldNotBeFound("stateOrProvince.equals=" + UPDATED_STATE_OR_PROVINCE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByStateOrProvinceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where stateOrProvince not equals to DEFAULT_STATE_OR_PROVINCE
        defaultIndContactCharShouldNotBeFound("stateOrProvince.notEquals=" + DEFAULT_STATE_OR_PROVINCE);

        // Get all the indContactCharList where stateOrProvince not equals to UPDATED_STATE_OR_PROVINCE
        defaultIndContactCharShouldBeFound("stateOrProvince.notEquals=" + UPDATED_STATE_OR_PROVINCE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByStateOrProvinceIsInShouldWork() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where stateOrProvince in DEFAULT_STATE_OR_PROVINCE or UPDATED_STATE_OR_PROVINCE
        defaultIndContactCharShouldBeFound("stateOrProvince.in=" + DEFAULT_STATE_OR_PROVINCE + "," + UPDATED_STATE_OR_PROVINCE);

        // Get all the indContactCharList where stateOrProvince equals to UPDATED_STATE_OR_PROVINCE
        defaultIndContactCharShouldNotBeFound("stateOrProvince.in=" + UPDATED_STATE_OR_PROVINCE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByStateOrProvinceIsNullOrNotNull() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where stateOrProvince is not null
        defaultIndContactCharShouldBeFound("stateOrProvince.specified=true");

        // Get all the indContactCharList where stateOrProvince is null
        defaultIndContactCharShouldNotBeFound("stateOrProvince.specified=false");
    }

    @Test
    @Transactional
    void getAllIndContactCharsByStateOrProvinceContainsSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where stateOrProvince contains DEFAULT_STATE_OR_PROVINCE
        defaultIndContactCharShouldBeFound("stateOrProvince.contains=" + DEFAULT_STATE_OR_PROVINCE);

        // Get all the indContactCharList where stateOrProvince contains UPDATED_STATE_OR_PROVINCE
        defaultIndContactCharShouldNotBeFound("stateOrProvince.contains=" + UPDATED_STATE_OR_PROVINCE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByStateOrProvinceNotContainsSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where stateOrProvince does not contain DEFAULT_STATE_OR_PROVINCE
        defaultIndContactCharShouldNotBeFound("stateOrProvince.doesNotContain=" + DEFAULT_STATE_OR_PROVINCE);

        // Get all the indContactCharList where stateOrProvince does not contain UPDATED_STATE_OR_PROVINCE
        defaultIndContactCharShouldBeFound("stateOrProvince.doesNotContain=" + UPDATED_STATE_OR_PROVINCE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where country equals to DEFAULT_COUNTRY
        defaultIndContactCharShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the indContactCharList where country equals to UPDATED_COUNTRY
        defaultIndContactCharShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByCountryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where country not equals to DEFAULT_COUNTRY
        defaultIndContactCharShouldNotBeFound("country.notEquals=" + DEFAULT_COUNTRY);

        // Get all the indContactCharList where country not equals to UPDATED_COUNTRY
        defaultIndContactCharShouldBeFound("country.notEquals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultIndContactCharShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the indContactCharList where country equals to UPDATED_COUNTRY
        defaultIndContactCharShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where country is not null
        defaultIndContactCharShouldBeFound("country.specified=true");

        // Get all the indContactCharList where country is null
        defaultIndContactCharShouldNotBeFound("country.specified=false");
    }

    @Test
    @Transactional
    void getAllIndContactCharsByCountryContainsSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where country contains DEFAULT_COUNTRY
        defaultIndContactCharShouldBeFound("country.contains=" + DEFAULT_COUNTRY);

        // Get all the indContactCharList where country contains UPDATED_COUNTRY
        defaultIndContactCharShouldNotBeFound("country.contains=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByCountryNotContainsSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where country does not contain DEFAULT_COUNTRY
        defaultIndContactCharShouldNotBeFound("country.doesNotContain=" + DEFAULT_COUNTRY);

        // Get all the indContactCharList where country does not contain UPDATED_COUNTRY
        defaultIndContactCharShouldBeFound("country.doesNotContain=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByPostCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where postCode equals to DEFAULT_POST_CODE
        defaultIndContactCharShouldBeFound("postCode.equals=" + DEFAULT_POST_CODE);

        // Get all the indContactCharList where postCode equals to UPDATED_POST_CODE
        defaultIndContactCharShouldNotBeFound("postCode.equals=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByPostCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where postCode not equals to DEFAULT_POST_CODE
        defaultIndContactCharShouldNotBeFound("postCode.notEquals=" + DEFAULT_POST_CODE);

        // Get all the indContactCharList where postCode not equals to UPDATED_POST_CODE
        defaultIndContactCharShouldBeFound("postCode.notEquals=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByPostCodeIsInShouldWork() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where postCode in DEFAULT_POST_CODE or UPDATED_POST_CODE
        defaultIndContactCharShouldBeFound("postCode.in=" + DEFAULT_POST_CODE + "," + UPDATED_POST_CODE);

        // Get all the indContactCharList where postCode equals to UPDATED_POST_CODE
        defaultIndContactCharShouldNotBeFound("postCode.in=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByPostCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where postCode is not null
        defaultIndContactCharShouldBeFound("postCode.specified=true");

        // Get all the indContactCharList where postCode is null
        defaultIndContactCharShouldNotBeFound("postCode.specified=false");
    }

    @Test
    @Transactional
    void getAllIndContactCharsByPostCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where postCode is greater than or equal to DEFAULT_POST_CODE
        defaultIndContactCharShouldBeFound("postCode.greaterThanOrEqual=" + DEFAULT_POST_CODE);

        // Get all the indContactCharList where postCode is greater than or equal to UPDATED_POST_CODE
        defaultIndContactCharShouldNotBeFound("postCode.greaterThanOrEqual=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByPostCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where postCode is less than or equal to DEFAULT_POST_CODE
        defaultIndContactCharShouldBeFound("postCode.lessThanOrEqual=" + DEFAULT_POST_CODE);

        // Get all the indContactCharList where postCode is less than or equal to SMALLER_POST_CODE
        defaultIndContactCharShouldNotBeFound("postCode.lessThanOrEqual=" + SMALLER_POST_CODE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByPostCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where postCode is less than DEFAULT_POST_CODE
        defaultIndContactCharShouldNotBeFound("postCode.lessThan=" + DEFAULT_POST_CODE);

        // Get all the indContactCharList where postCode is less than UPDATED_POST_CODE
        defaultIndContactCharShouldBeFound("postCode.lessThan=" + UPDATED_POST_CODE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByPostCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where postCode is greater than DEFAULT_POST_CODE
        defaultIndContactCharShouldNotBeFound("postCode.greaterThan=" + DEFAULT_POST_CODE);

        // Get all the indContactCharList where postCode is greater than SMALLER_POST_CODE
        defaultIndContactCharShouldBeFound("postCode.greaterThan=" + SMALLER_POST_CODE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultIndContactCharShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the indContactCharList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultIndContactCharShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByPhoneNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where phoneNumber not equals to DEFAULT_PHONE_NUMBER
        defaultIndContactCharShouldNotBeFound("phoneNumber.notEquals=" + DEFAULT_PHONE_NUMBER);

        // Get all the indContactCharList where phoneNumber not equals to UPDATED_PHONE_NUMBER
        defaultIndContactCharShouldBeFound("phoneNumber.notEquals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultIndContactCharShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the indContactCharList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultIndContactCharShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where phoneNumber is not null
        defaultIndContactCharShouldBeFound("phoneNumber.specified=true");

        // Get all the indContactCharList where phoneNumber is null
        defaultIndContactCharShouldNotBeFound("phoneNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllIndContactCharsByPhoneNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where phoneNumber is greater than or equal to DEFAULT_PHONE_NUMBER
        defaultIndContactCharShouldBeFound("phoneNumber.greaterThanOrEqual=" + DEFAULT_PHONE_NUMBER);

        // Get all the indContactCharList where phoneNumber is greater than or equal to UPDATED_PHONE_NUMBER
        defaultIndContactCharShouldNotBeFound("phoneNumber.greaterThanOrEqual=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByPhoneNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where phoneNumber is less than or equal to DEFAULT_PHONE_NUMBER
        defaultIndContactCharShouldBeFound("phoneNumber.lessThanOrEqual=" + DEFAULT_PHONE_NUMBER);

        // Get all the indContactCharList where phoneNumber is less than or equal to SMALLER_PHONE_NUMBER
        defaultIndContactCharShouldNotBeFound("phoneNumber.lessThanOrEqual=" + SMALLER_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByPhoneNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where phoneNumber is less than DEFAULT_PHONE_NUMBER
        defaultIndContactCharShouldNotBeFound("phoneNumber.lessThan=" + DEFAULT_PHONE_NUMBER);

        // Get all the indContactCharList where phoneNumber is less than UPDATED_PHONE_NUMBER
        defaultIndContactCharShouldBeFound("phoneNumber.lessThan=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByPhoneNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where phoneNumber is greater than DEFAULT_PHONE_NUMBER
        defaultIndContactCharShouldNotBeFound("phoneNumber.greaterThan=" + DEFAULT_PHONE_NUMBER);

        // Get all the indContactCharList where phoneNumber is greater than SMALLER_PHONE_NUMBER
        defaultIndContactCharShouldBeFound("phoneNumber.greaterThan=" + SMALLER_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByEmailAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where emailAddress equals to DEFAULT_EMAIL_ADDRESS
        defaultIndContactCharShouldBeFound("emailAddress.equals=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the indContactCharList where emailAddress equals to UPDATED_EMAIL_ADDRESS
        defaultIndContactCharShouldNotBeFound("emailAddress.equals=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByEmailAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where emailAddress not equals to DEFAULT_EMAIL_ADDRESS
        defaultIndContactCharShouldNotBeFound("emailAddress.notEquals=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the indContactCharList where emailAddress not equals to UPDATED_EMAIL_ADDRESS
        defaultIndContactCharShouldBeFound("emailAddress.notEquals=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByEmailAddressIsInShouldWork() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where emailAddress in DEFAULT_EMAIL_ADDRESS or UPDATED_EMAIL_ADDRESS
        defaultIndContactCharShouldBeFound("emailAddress.in=" + DEFAULT_EMAIL_ADDRESS + "," + UPDATED_EMAIL_ADDRESS);

        // Get all the indContactCharList where emailAddress equals to UPDATED_EMAIL_ADDRESS
        defaultIndContactCharShouldNotBeFound("emailAddress.in=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByEmailAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where emailAddress is not null
        defaultIndContactCharShouldBeFound("emailAddress.specified=true");

        // Get all the indContactCharList where emailAddress is null
        defaultIndContactCharShouldNotBeFound("emailAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllIndContactCharsByEmailAddressContainsSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where emailAddress contains DEFAULT_EMAIL_ADDRESS
        defaultIndContactCharShouldBeFound("emailAddress.contains=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the indContactCharList where emailAddress contains UPDATED_EMAIL_ADDRESS
        defaultIndContactCharShouldNotBeFound("emailAddress.contains=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByEmailAddressNotContainsSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where emailAddress does not contain DEFAULT_EMAIL_ADDRESS
        defaultIndContactCharShouldNotBeFound("emailAddress.doesNotContain=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the indContactCharList where emailAddress does not contain UPDATED_EMAIL_ADDRESS
        defaultIndContactCharShouldBeFound("emailAddress.doesNotContain=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByFaxNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where faxNumber equals to DEFAULT_FAX_NUMBER
        defaultIndContactCharShouldBeFound("faxNumber.equals=" + DEFAULT_FAX_NUMBER);

        // Get all the indContactCharList where faxNumber equals to UPDATED_FAX_NUMBER
        defaultIndContactCharShouldNotBeFound("faxNumber.equals=" + UPDATED_FAX_NUMBER);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByFaxNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where faxNumber not equals to DEFAULT_FAX_NUMBER
        defaultIndContactCharShouldNotBeFound("faxNumber.notEquals=" + DEFAULT_FAX_NUMBER);

        // Get all the indContactCharList where faxNumber not equals to UPDATED_FAX_NUMBER
        defaultIndContactCharShouldBeFound("faxNumber.notEquals=" + UPDATED_FAX_NUMBER);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByFaxNumberIsInShouldWork() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where faxNumber in DEFAULT_FAX_NUMBER or UPDATED_FAX_NUMBER
        defaultIndContactCharShouldBeFound("faxNumber.in=" + DEFAULT_FAX_NUMBER + "," + UPDATED_FAX_NUMBER);

        // Get all the indContactCharList where faxNumber equals to UPDATED_FAX_NUMBER
        defaultIndContactCharShouldNotBeFound("faxNumber.in=" + UPDATED_FAX_NUMBER);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByFaxNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where faxNumber is not null
        defaultIndContactCharShouldBeFound("faxNumber.specified=true");

        // Get all the indContactCharList where faxNumber is null
        defaultIndContactCharShouldNotBeFound("faxNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllIndContactCharsByFaxNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where faxNumber is greater than or equal to DEFAULT_FAX_NUMBER
        defaultIndContactCharShouldBeFound("faxNumber.greaterThanOrEqual=" + DEFAULT_FAX_NUMBER);

        // Get all the indContactCharList where faxNumber is greater than or equal to UPDATED_FAX_NUMBER
        defaultIndContactCharShouldNotBeFound("faxNumber.greaterThanOrEqual=" + UPDATED_FAX_NUMBER);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByFaxNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where faxNumber is less than or equal to DEFAULT_FAX_NUMBER
        defaultIndContactCharShouldBeFound("faxNumber.lessThanOrEqual=" + DEFAULT_FAX_NUMBER);

        // Get all the indContactCharList where faxNumber is less than or equal to SMALLER_FAX_NUMBER
        defaultIndContactCharShouldNotBeFound("faxNumber.lessThanOrEqual=" + SMALLER_FAX_NUMBER);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByFaxNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where faxNumber is less than DEFAULT_FAX_NUMBER
        defaultIndContactCharShouldNotBeFound("faxNumber.lessThan=" + DEFAULT_FAX_NUMBER);

        // Get all the indContactCharList where faxNumber is less than UPDATED_FAX_NUMBER
        defaultIndContactCharShouldBeFound("faxNumber.lessThan=" + UPDATED_FAX_NUMBER);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByFaxNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where faxNumber is greater than DEFAULT_FAX_NUMBER
        defaultIndContactCharShouldNotBeFound("faxNumber.greaterThan=" + DEFAULT_FAX_NUMBER);

        // Get all the indContactCharList where faxNumber is greater than SMALLER_FAX_NUMBER
        defaultIndContactCharShouldBeFound("faxNumber.greaterThan=" + SMALLER_FAX_NUMBER);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByLatitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where latitude equals to DEFAULT_LATITUDE
        defaultIndContactCharShouldBeFound("latitude.equals=" + DEFAULT_LATITUDE);

        // Get all the indContactCharList where latitude equals to UPDATED_LATITUDE
        defaultIndContactCharShouldNotBeFound("latitude.equals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByLatitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where latitude not equals to DEFAULT_LATITUDE
        defaultIndContactCharShouldNotBeFound("latitude.notEquals=" + DEFAULT_LATITUDE);

        // Get all the indContactCharList where latitude not equals to UPDATED_LATITUDE
        defaultIndContactCharShouldBeFound("latitude.notEquals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByLatitudeIsInShouldWork() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where latitude in DEFAULT_LATITUDE or UPDATED_LATITUDE
        defaultIndContactCharShouldBeFound("latitude.in=" + DEFAULT_LATITUDE + "," + UPDATED_LATITUDE);

        // Get all the indContactCharList where latitude equals to UPDATED_LATITUDE
        defaultIndContactCharShouldNotBeFound("latitude.in=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByLatitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where latitude is not null
        defaultIndContactCharShouldBeFound("latitude.specified=true");

        // Get all the indContactCharList where latitude is null
        defaultIndContactCharShouldNotBeFound("latitude.specified=false");
    }

    @Test
    @Transactional
    void getAllIndContactCharsByLatitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where latitude is greater than or equal to DEFAULT_LATITUDE
        defaultIndContactCharShouldBeFound("latitude.greaterThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the indContactCharList where latitude is greater than or equal to UPDATED_LATITUDE
        defaultIndContactCharShouldNotBeFound("latitude.greaterThanOrEqual=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByLatitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where latitude is less than or equal to DEFAULT_LATITUDE
        defaultIndContactCharShouldBeFound("latitude.lessThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the indContactCharList where latitude is less than or equal to SMALLER_LATITUDE
        defaultIndContactCharShouldNotBeFound("latitude.lessThanOrEqual=" + SMALLER_LATITUDE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByLatitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where latitude is less than DEFAULT_LATITUDE
        defaultIndContactCharShouldNotBeFound("latitude.lessThan=" + DEFAULT_LATITUDE);

        // Get all the indContactCharList where latitude is less than UPDATED_LATITUDE
        defaultIndContactCharShouldBeFound("latitude.lessThan=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByLatitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where latitude is greater than DEFAULT_LATITUDE
        defaultIndContactCharShouldNotBeFound("latitude.greaterThan=" + DEFAULT_LATITUDE);

        // Get all the indContactCharList where latitude is greater than SMALLER_LATITUDE
        defaultIndContactCharShouldBeFound("latitude.greaterThan=" + SMALLER_LATITUDE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where longitude equals to DEFAULT_LONGITUDE
        defaultIndContactCharShouldBeFound("longitude.equals=" + DEFAULT_LONGITUDE);

        // Get all the indContactCharList where longitude equals to UPDATED_LONGITUDE
        defaultIndContactCharShouldNotBeFound("longitude.equals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByLongitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where longitude not equals to DEFAULT_LONGITUDE
        defaultIndContactCharShouldNotBeFound("longitude.notEquals=" + DEFAULT_LONGITUDE);

        // Get all the indContactCharList where longitude not equals to UPDATED_LONGITUDE
        defaultIndContactCharShouldBeFound("longitude.notEquals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where longitude in DEFAULT_LONGITUDE or UPDATED_LONGITUDE
        defaultIndContactCharShouldBeFound("longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE);

        // Get all the indContactCharList where longitude equals to UPDATED_LONGITUDE
        defaultIndContactCharShouldNotBeFound("longitude.in=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where longitude is not null
        defaultIndContactCharShouldBeFound("longitude.specified=true");

        // Get all the indContactCharList where longitude is null
        defaultIndContactCharShouldNotBeFound("longitude.specified=false");
    }

    @Test
    @Transactional
    void getAllIndContactCharsByLongitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where longitude is greater than or equal to DEFAULT_LONGITUDE
        defaultIndContactCharShouldBeFound("longitude.greaterThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the indContactCharList where longitude is greater than or equal to UPDATED_LONGITUDE
        defaultIndContactCharShouldNotBeFound("longitude.greaterThanOrEqual=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByLongitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where longitude is less than or equal to DEFAULT_LONGITUDE
        defaultIndContactCharShouldBeFound("longitude.lessThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the indContactCharList where longitude is less than or equal to SMALLER_LONGITUDE
        defaultIndContactCharShouldNotBeFound("longitude.lessThanOrEqual=" + SMALLER_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByLongitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where longitude is less than DEFAULT_LONGITUDE
        defaultIndContactCharShouldNotBeFound("longitude.lessThan=" + DEFAULT_LONGITUDE);

        // Get all the indContactCharList where longitude is less than UPDATED_LONGITUDE
        defaultIndContactCharShouldBeFound("longitude.lessThan=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByLongitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where longitude is greater than DEFAULT_LONGITUDE
        defaultIndContactCharShouldNotBeFound("longitude.greaterThan=" + DEFAULT_LONGITUDE);

        // Get all the indContactCharList where longitude is greater than SMALLER_LONGITUDE
        defaultIndContactCharShouldBeFound("longitude.greaterThan=" + SMALLER_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllIndContactCharsBySvContactIdIsEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where svContactId equals to DEFAULT_SV_CONTACT_ID
        defaultIndContactCharShouldBeFound("svContactId.equals=" + DEFAULT_SV_CONTACT_ID);

        // Get all the indContactCharList where svContactId equals to UPDATED_SV_CONTACT_ID
        defaultIndContactCharShouldNotBeFound("svContactId.equals=" + UPDATED_SV_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllIndContactCharsBySvContactIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where svContactId not equals to DEFAULT_SV_CONTACT_ID
        defaultIndContactCharShouldNotBeFound("svContactId.notEquals=" + DEFAULT_SV_CONTACT_ID);

        // Get all the indContactCharList where svContactId not equals to UPDATED_SV_CONTACT_ID
        defaultIndContactCharShouldBeFound("svContactId.notEquals=" + UPDATED_SV_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllIndContactCharsBySvContactIdIsInShouldWork() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where svContactId in DEFAULT_SV_CONTACT_ID or UPDATED_SV_CONTACT_ID
        defaultIndContactCharShouldBeFound("svContactId.in=" + DEFAULT_SV_CONTACT_ID + "," + UPDATED_SV_CONTACT_ID);

        // Get all the indContactCharList where svContactId equals to UPDATED_SV_CONTACT_ID
        defaultIndContactCharShouldNotBeFound("svContactId.in=" + UPDATED_SV_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllIndContactCharsBySvContactIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where svContactId is not null
        defaultIndContactCharShouldBeFound("svContactId.specified=true");

        // Get all the indContactCharList where svContactId is null
        defaultIndContactCharShouldNotBeFound("svContactId.specified=false");
    }

    @Test
    @Transactional
    void getAllIndContactCharsBySvContactIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where svContactId is greater than or equal to DEFAULT_SV_CONTACT_ID
        defaultIndContactCharShouldBeFound("svContactId.greaterThanOrEqual=" + DEFAULT_SV_CONTACT_ID);

        // Get all the indContactCharList where svContactId is greater than or equal to UPDATED_SV_CONTACT_ID
        defaultIndContactCharShouldNotBeFound("svContactId.greaterThanOrEqual=" + UPDATED_SV_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllIndContactCharsBySvContactIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where svContactId is less than or equal to DEFAULT_SV_CONTACT_ID
        defaultIndContactCharShouldBeFound("svContactId.lessThanOrEqual=" + DEFAULT_SV_CONTACT_ID);

        // Get all the indContactCharList where svContactId is less than or equal to SMALLER_SV_CONTACT_ID
        defaultIndContactCharShouldNotBeFound("svContactId.lessThanOrEqual=" + SMALLER_SV_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllIndContactCharsBySvContactIdIsLessThanSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where svContactId is less than DEFAULT_SV_CONTACT_ID
        defaultIndContactCharShouldNotBeFound("svContactId.lessThan=" + DEFAULT_SV_CONTACT_ID);

        // Get all the indContactCharList where svContactId is less than UPDATED_SV_CONTACT_ID
        defaultIndContactCharShouldBeFound("svContactId.lessThan=" + UPDATED_SV_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllIndContactCharsBySvContactIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where svContactId is greater than DEFAULT_SV_CONTACT_ID
        defaultIndContactCharShouldNotBeFound("svContactId.greaterThan=" + DEFAULT_SV_CONTACT_ID);

        // Get all the indContactCharList where svContactId is greater than SMALLER_SV_CONTACT_ID
        defaultIndContactCharShouldBeFound("svContactId.greaterThan=" + SMALLER_SV_CONTACT_ID);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByIsEmailValidIsEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where isEmailValid equals to DEFAULT_IS_EMAIL_VALID
        defaultIndContactCharShouldBeFound("isEmailValid.equals=" + DEFAULT_IS_EMAIL_VALID);

        // Get all the indContactCharList where isEmailValid equals to UPDATED_IS_EMAIL_VALID
        defaultIndContactCharShouldNotBeFound("isEmailValid.equals=" + UPDATED_IS_EMAIL_VALID);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByIsEmailValidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where isEmailValid not equals to DEFAULT_IS_EMAIL_VALID
        defaultIndContactCharShouldNotBeFound("isEmailValid.notEquals=" + DEFAULT_IS_EMAIL_VALID);

        // Get all the indContactCharList where isEmailValid not equals to UPDATED_IS_EMAIL_VALID
        defaultIndContactCharShouldBeFound("isEmailValid.notEquals=" + UPDATED_IS_EMAIL_VALID);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByIsEmailValidIsInShouldWork() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where isEmailValid in DEFAULT_IS_EMAIL_VALID or UPDATED_IS_EMAIL_VALID
        defaultIndContactCharShouldBeFound("isEmailValid.in=" + DEFAULT_IS_EMAIL_VALID + "," + UPDATED_IS_EMAIL_VALID);

        // Get all the indContactCharList where isEmailValid equals to UPDATED_IS_EMAIL_VALID
        defaultIndContactCharShouldNotBeFound("isEmailValid.in=" + UPDATED_IS_EMAIL_VALID);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByIsEmailValidIsNullOrNotNull() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where isEmailValid is not null
        defaultIndContactCharShouldBeFound("isEmailValid.specified=true");

        // Get all the indContactCharList where isEmailValid is null
        defaultIndContactCharShouldNotBeFound("isEmailValid.specified=false");
    }

    @Test
    @Transactional
    void getAllIndContactCharsByIsAddressValidIsEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where isAddressValid equals to DEFAULT_IS_ADDRESS_VALID
        defaultIndContactCharShouldBeFound("isAddressValid.equals=" + DEFAULT_IS_ADDRESS_VALID);

        // Get all the indContactCharList where isAddressValid equals to UPDATED_IS_ADDRESS_VALID
        defaultIndContactCharShouldNotBeFound("isAddressValid.equals=" + UPDATED_IS_ADDRESS_VALID);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByIsAddressValidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where isAddressValid not equals to DEFAULT_IS_ADDRESS_VALID
        defaultIndContactCharShouldNotBeFound("isAddressValid.notEquals=" + DEFAULT_IS_ADDRESS_VALID);

        // Get all the indContactCharList where isAddressValid not equals to UPDATED_IS_ADDRESS_VALID
        defaultIndContactCharShouldBeFound("isAddressValid.notEquals=" + UPDATED_IS_ADDRESS_VALID);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByIsAddressValidIsInShouldWork() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where isAddressValid in DEFAULT_IS_ADDRESS_VALID or UPDATED_IS_ADDRESS_VALID
        defaultIndContactCharShouldBeFound("isAddressValid.in=" + DEFAULT_IS_ADDRESS_VALID + "," + UPDATED_IS_ADDRESS_VALID);

        // Get all the indContactCharList where isAddressValid equals to UPDATED_IS_ADDRESS_VALID
        defaultIndContactCharShouldNotBeFound("isAddressValid.in=" + UPDATED_IS_ADDRESS_VALID);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByIsAddressValidIsNullOrNotNull() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where isAddressValid is not null
        defaultIndContactCharShouldBeFound("isAddressValid.specified=true");

        // Get all the indContactCharList where isAddressValid is null
        defaultIndContactCharShouldNotBeFound("isAddressValid.specified=false");
    }

    @Test
    @Transactional
    void getAllIndContactCharsByIndConIdIsEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where indConId equals to DEFAULT_IND_CON_ID
        defaultIndContactCharShouldBeFound("indConId.equals=" + DEFAULT_IND_CON_ID);

        // Get all the indContactCharList where indConId equals to UPDATED_IND_CON_ID
        defaultIndContactCharShouldNotBeFound("indConId.equals=" + UPDATED_IND_CON_ID);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByIndConIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where indConId not equals to DEFAULT_IND_CON_ID
        defaultIndContactCharShouldNotBeFound("indConId.notEquals=" + DEFAULT_IND_CON_ID);

        // Get all the indContactCharList where indConId not equals to UPDATED_IND_CON_ID
        defaultIndContactCharShouldBeFound("indConId.notEquals=" + UPDATED_IND_CON_ID);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByIndConIdIsInShouldWork() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where indConId in DEFAULT_IND_CON_ID or UPDATED_IND_CON_ID
        defaultIndContactCharShouldBeFound("indConId.in=" + DEFAULT_IND_CON_ID + "," + UPDATED_IND_CON_ID);

        // Get all the indContactCharList where indConId equals to UPDATED_IND_CON_ID
        defaultIndContactCharShouldNotBeFound("indConId.in=" + UPDATED_IND_CON_ID);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByIndConIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where indConId is not null
        defaultIndContactCharShouldBeFound("indConId.specified=true");

        // Get all the indContactCharList where indConId is null
        defaultIndContactCharShouldNotBeFound("indConId.specified=false");
    }

    @Test
    @Transactional
    void getAllIndContactCharsByIndConIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where indConId is greater than or equal to DEFAULT_IND_CON_ID
        defaultIndContactCharShouldBeFound("indConId.greaterThanOrEqual=" + DEFAULT_IND_CON_ID);

        // Get all the indContactCharList where indConId is greater than or equal to UPDATED_IND_CON_ID
        defaultIndContactCharShouldNotBeFound("indConId.greaterThanOrEqual=" + UPDATED_IND_CON_ID);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByIndConIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where indConId is less than or equal to DEFAULT_IND_CON_ID
        defaultIndContactCharShouldBeFound("indConId.lessThanOrEqual=" + DEFAULT_IND_CON_ID);

        // Get all the indContactCharList where indConId is less than or equal to SMALLER_IND_CON_ID
        defaultIndContactCharShouldNotBeFound("indConId.lessThanOrEqual=" + SMALLER_IND_CON_ID);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByIndConIdIsLessThanSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where indConId is less than DEFAULT_IND_CON_ID
        defaultIndContactCharShouldNotBeFound("indConId.lessThan=" + DEFAULT_IND_CON_ID);

        // Get all the indContactCharList where indConId is less than UPDATED_IND_CON_ID
        defaultIndContactCharShouldBeFound("indConId.lessThan=" + UPDATED_IND_CON_ID);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByIndConIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        // Get all the indContactCharList where indConId is greater than DEFAULT_IND_CON_ID
        defaultIndContactCharShouldNotBeFound("indConId.greaterThan=" + DEFAULT_IND_CON_ID);

        // Get all the indContactCharList where indConId is greater than SMALLER_IND_CON_ID
        defaultIndContactCharShouldBeFound("indConId.greaterThan=" + SMALLER_IND_CON_ID);
    }

    @Test
    @Transactional
    void getAllIndContactCharsByIndContactIsEqualToSomething() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);
        IndContact indContact = IndContactResourceIT.createEntity(em);
        em.persist(indContact);
        em.flush();
        indContactChar.setIndContact(indContact);
        indContactCharRepository.saveAndFlush(indContactChar);
        Long indContactId = indContact.getId();

        // Get all the indContactCharList where indContact equals to indContactId
        defaultIndContactCharShouldBeFound("indContactId.equals=" + indContactId);

        // Get all the indContactCharList where indContact equals to (indContactId + 1)
        defaultIndContactCharShouldNotBeFound("indContactId.equals=" + (indContactId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultIndContactCharShouldBeFound(String filter) throws Exception {
        restIndContactCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indContactChar.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].svContactId").value(hasItem(DEFAULT_SV_CONTACT_ID.intValue())))
            .andExpect(jsonPath("$.[*].isEmailValid").value(hasItem(DEFAULT_IS_EMAIL_VALID.booleanValue())))
            .andExpect(jsonPath("$.[*].isAddressValid").value(hasItem(DEFAULT_IS_ADDRESS_VALID.booleanValue())))
            .andExpect(jsonPath("$.[*].indConId").value(hasItem(DEFAULT_IND_CON_ID.intValue())));

        // Check, that the count call also returns 1
        restIndContactCharMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultIndContactCharShouldNotBeFound(String filter) throws Exception {
        restIndContactCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIndContactCharMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingIndContactChar() throws Exception {
        // Get the indContactChar
        restIndContactCharMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIndContactChar() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        int databaseSizeBeforeUpdate = indContactCharRepository.findAll().size();

        // Update the indContactChar
        IndContactChar updatedIndContactChar = indContactCharRepository.findById(indContactChar.getId()).get();
        // Disconnect from session so that the updates on updatedIndContactChar are not directly saved in db
        em.detach(updatedIndContactChar);
        updatedIndContactChar
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
            .indConId(UPDATED_IND_CON_ID);
        IndContactCharDTO indContactCharDTO = indContactCharMapper.toDto(updatedIndContactChar);

        restIndContactCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indContactCharDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indContactCharDTO))
            )
            .andExpect(status().isOk());

        // Validate the IndContactChar in the database
        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeUpdate);
        IndContactChar testIndContactChar = indContactCharList.get(indContactCharList.size() - 1);
        assertThat(testIndContactChar.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testIndContactChar.getStreetOne()).isEqualTo(UPDATED_STREET_ONE);
        assertThat(testIndContactChar.getStreetTwo()).isEqualTo(UPDATED_STREET_TWO);
        assertThat(testIndContactChar.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testIndContactChar.getStateOrProvince()).isEqualTo(UPDATED_STATE_OR_PROVINCE);
        assertThat(testIndContactChar.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testIndContactChar.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testIndContactChar.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testIndContactChar.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testIndContactChar.getFaxNumber()).isEqualTo(UPDATED_FAX_NUMBER);
        assertThat(testIndContactChar.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testIndContactChar.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testIndContactChar.getSvContactId()).isEqualTo(UPDATED_SV_CONTACT_ID);
        assertThat(testIndContactChar.getIsEmailValid()).isEqualTo(UPDATED_IS_EMAIL_VALID);
        assertThat(testIndContactChar.getIsAddressValid()).isEqualTo(UPDATED_IS_ADDRESS_VALID);
        assertThat(testIndContactChar.getIndConId()).isEqualTo(UPDATED_IND_CON_ID);
    }

    @Test
    @Transactional
    void putNonExistingIndContactChar() throws Exception {
        int databaseSizeBeforeUpdate = indContactCharRepository.findAll().size();
        indContactChar.setId(count.incrementAndGet());

        // Create the IndContactChar
        IndContactCharDTO indContactCharDTO = indContactCharMapper.toDto(indContactChar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndContactCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indContactCharDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indContactCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndContactChar in the database
        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIndContactChar() throws Exception {
        int databaseSizeBeforeUpdate = indContactCharRepository.findAll().size();
        indContactChar.setId(count.incrementAndGet());

        // Create the IndContactChar
        IndContactCharDTO indContactCharDTO = indContactCharMapper.toDto(indContactChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndContactCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indContactCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndContactChar in the database
        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIndContactChar() throws Exception {
        int databaseSizeBeforeUpdate = indContactCharRepository.findAll().size();
        indContactChar.setId(count.incrementAndGet());

        // Create the IndContactChar
        IndContactCharDTO indContactCharDTO = indContactCharMapper.toDto(indContactChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndContactCharMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indContactCharDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IndContactChar in the database
        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIndContactCharWithPatch() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        int databaseSizeBeforeUpdate = indContactCharRepository.findAll().size();

        // Update the indContactChar using partial update
        IndContactChar partialUpdatedIndContactChar = new IndContactChar();
        partialUpdatedIndContactChar.setId(indContactChar.getId());

        partialUpdatedIndContactChar
            .type(UPDATED_TYPE)
            .streetTwo(UPDATED_STREET_TWO)
            .city(UPDATED_CITY)
            .stateOrProvince(UPDATED_STATE_OR_PROVINCE)
            .postCode(UPDATED_POST_CODE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .longitude(UPDATED_LONGITUDE)
            .isEmailValid(UPDATED_IS_EMAIL_VALID)
            .isAddressValid(UPDATED_IS_ADDRESS_VALID)
            .indConId(UPDATED_IND_CON_ID);

        restIndContactCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndContactChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndContactChar))
            )
            .andExpect(status().isOk());

        // Validate the IndContactChar in the database
        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeUpdate);
        IndContactChar testIndContactChar = indContactCharList.get(indContactCharList.size() - 1);
        assertThat(testIndContactChar.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testIndContactChar.getStreetOne()).isEqualTo(DEFAULT_STREET_ONE);
        assertThat(testIndContactChar.getStreetTwo()).isEqualTo(UPDATED_STREET_TWO);
        assertThat(testIndContactChar.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testIndContactChar.getStateOrProvince()).isEqualTo(UPDATED_STATE_OR_PROVINCE);
        assertThat(testIndContactChar.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testIndContactChar.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testIndContactChar.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testIndContactChar.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testIndContactChar.getFaxNumber()).isEqualTo(DEFAULT_FAX_NUMBER);
        assertThat(testIndContactChar.getLatitude()).isEqualByComparingTo(DEFAULT_LATITUDE);
        assertThat(testIndContactChar.getLongitude()).isEqualByComparingTo(UPDATED_LONGITUDE);
        assertThat(testIndContactChar.getSvContactId()).isEqualTo(DEFAULT_SV_CONTACT_ID);
        assertThat(testIndContactChar.getIsEmailValid()).isEqualTo(UPDATED_IS_EMAIL_VALID);
        assertThat(testIndContactChar.getIsAddressValid()).isEqualTo(UPDATED_IS_ADDRESS_VALID);
        assertThat(testIndContactChar.getIndConId()).isEqualTo(UPDATED_IND_CON_ID);
    }

    @Test
    @Transactional
    void fullUpdateIndContactCharWithPatch() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        int databaseSizeBeforeUpdate = indContactCharRepository.findAll().size();

        // Update the indContactChar using partial update
        IndContactChar partialUpdatedIndContactChar = new IndContactChar();
        partialUpdatedIndContactChar.setId(indContactChar.getId());

        partialUpdatedIndContactChar
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
            .indConId(UPDATED_IND_CON_ID);

        restIndContactCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndContactChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndContactChar))
            )
            .andExpect(status().isOk());

        // Validate the IndContactChar in the database
        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeUpdate);
        IndContactChar testIndContactChar = indContactCharList.get(indContactCharList.size() - 1);
        assertThat(testIndContactChar.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testIndContactChar.getStreetOne()).isEqualTo(UPDATED_STREET_ONE);
        assertThat(testIndContactChar.getStreetTwo()).isEqualTo(UPDATED_STREET_TWO);
        assertThat(testIndContactChar.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testIndContactChar.getStateOrProvince()).isEqualTo(UPDATED_STATE_OR_PROVINCE);
        assertThat(testIndContactChar.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testIndContactChar.getPostCode()).isEqualTo(UPDATED_POST_CODE);
        assertThat(testIndContactChar.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testIndContactChar.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testIndContactChar.getFaxNumber()).isEqualTo(UPDATED_FAX_NUMBER);
        assertThat(testIndContactChar.getLatitude()).isEqualByComparingTo(UPDATED_LATITUDE);
        assertThat(testIndContactChar.getLongitude()).isEqualByComparingTo(UPDATED_LONGITUDE);
        assertThat(testIndContactChar.getSvContactId()).isEqualTo(UPDATED_SV_CONTACT_ID);
        assertThat(testIndContactChar.getIsEmailValid()).isEqualTo(UPDATED_IS_EMAIL_VALID);
        assertThat(testIndContactChar.getIsAddressValid()).isEqualTo(UPDATED_IS_ADDRESS_VALID);
        assertThat(testIndContactChar.getIndConId()).isEqualTo(UPDATED_IND_CON_ID);
    }

    @Test
    @Transactional
    void patchNonExistingIndContactChar() throws Exception {
        int databaseSizeBeforeUpdate = indContactCharRepository.findAll().size();
        indContactChar.setId(count.incrementAndGet());

        // Create the IndContactChar
        IndContactCharDTO indContactCharDTO = indContactCharMapper.toDto(indContactChar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndContactCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, indContactCharDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indContactCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndContactChar in the database
        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIndContactChar() throws Exception {
        int databaseSizeBeforeUpdate = indContactCharRepository.findAll().size();
        indContactChar.setId(count.incrementAndGet());

        // Create the IndContactChar
        IndContactCharDTO indContactCharDTO = indContactCharMapper.toDto(indContactChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndContactCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indContactCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndContactChar in the database
        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIndContactChar() throws Exception {
        int databaseSizeBeforeUpdate = indContactCharRepository.findAll().size();
        indContactChar.setId(count.incrementAndGet());

        // Create the IndContactChar
        IndContactCharDTO indContactCharDTO = indContactCharMapper.toDto(indContactChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndContactCharMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indContactCharDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IndContactChar in the database
        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIndContactChar() throws Exception {
        // Initialize the database
        indContactCharRepository.saveAndFlush(indContactChar);

        int databaseSizeBeforeDelete = indContactCharRepository.findAll().size();

        // Delete the indContactChar
        restIndContactCharMockMvc
            .perform(delete(ENTITY_API_URL_ID, indContactChar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IndContactChar> indContactCharList = indContactCharRepository.findAll();
        assertThat(indContactCharList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
