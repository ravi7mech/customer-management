package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.BankCardType;
import com.apptium.customer.domain.CustPaymentMethod;
import com.apptium.customer.repository.BankCardTypeRepository;
import com.apptium.customer.service.criteria.BankCardTypeCriteria;
import com.apptium.customer.service.dto.BankCardTypeDTO;
import com.apptium.customer.service.mapper.BankCardTypeMapper;
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
 * Integration tests for the {@link BankCardTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankCardTypeResourceIT {

    private static final String DEFAULT_BRAND = "AAAAAAAAAA";
    private static final String UPDATED_BRAND = "BBBBBBBBBB";

    private static final String DEFAULT_CARD_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CARD_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_CARD_NUMBER = 1L;
    private static final Long UPDATED_CARD_NUMBER = 2L;
    private static final Long SMALLER_CARD_NUMBER = 1L - 1L;

    private static final Instant DEFAULT_EXPIRATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIRATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_CVV = 1;
    private static final Integer UPDATED_CVV = 2;
    private static final Integer SMALLER_CVV = 1 - 1;

    private static final Integer DEFAULT_LAST_FOUR_DIGITS = 1;
    private static final Integer UPDATED_LAST_FOUR_DIGITS = 2;
    private static final Integer SMALLER_LAST_FOUR_DIGITS = 1 - 1;

    private static final String DEFAULT_BANK = "AAAAAAAAAA";
    private static final String UPDATED_BANK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bank-card-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BankCardTypeRepository bankCardTypeRepository;

    @Autowired
    private BankCardTypeMapper bankCardTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankCardTypeMockMvc;

    private BankCardType bankCardType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankCardType createEntity(EntityManager em) {
        BankCardType bankCardType = new BankCardType()
            .brand(DEFAULT_BRAND)
            .cardType(DEFAULT_CARD_TYPE)
            .cardNumber(DEFAULT_CARD_NUMBER)
            .expirationDate(DEFAULT_EXPIRATION_DATE)
            .cvv(DEFAULT_CVV)
            .lastFourDigits(DEFAULT_LAST_FOUR_DIGITS)
            .bank(DEFAULT_BANK);
        return bankCardType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankCardType createUpdatedEntity(EntityManager em) {
        BankCardType bankCardType = new BankCardType()
            .brand(UPDATED_BRAND)
            .cardType(UPDATED_CARD_TYPE)
            .cardNumber(UPDATED_CARD_NUMBER)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .cvv(UPDATED_CVV)
            .lastFourDigits(UPDATED_LAST_FOUR_DIGITS)
            .bank(UPDATED_BANK);
        return bankCardType;
    }

    @BeforeEach
    public void initTest() {
        bankCardType = createEntity(em);
    }

    @Test
    @Transactional
    void createBankCardType() throws Exception {
        int databaseSizeBeforeCreate = bankCardTypeRepository.findAll().size();
        // Create the BankCardType
        BankCardTypeDTO bankCardTypeDTO = bankCardTypeMapper.toDto(bankCardType);
        restBankCardTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankCardTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BankCardType in the database
        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeCreate + 1);
        BankCardType testBankCardType = bankCardTypeList.get(bankCardTypeList.size() - 1);
        assertThat(testBankCardType.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testBankCardType.getCardType()).isEqualTo(DEFAULT_CARD_TYPE);
        assertThat(testBankCardType.getCardNumber()).isEqualTo(DEFAULT_CARD_NUMBER);
        assertThat(testBankCardType.getExpirationDate()).isEqualTo(DEFAULT_EXPIRATION_DATE);
        assertThat(testBankCardType.getCvv()).isEqualTo(DEFAULT_CVV);
        assertThat(testBankCardType.getLastFourDigits()).isEqualTo(DEFAULT_LAST_FOUR_DIGITS);
        assertThat(testBankCardType.getBank()).isEqualTo(DEFAULT_BANK);
    }

    @Test
    @Transactional
    void createBankCardTypeWithExistingId() throws Exception {
        // Create the BankCardType with an existing ID
        bankCardType.setId(1L);
        BankCardTypeDTO bankCardTypeDTO = bankCardTypeMapper.toDto(bankCardType);

        int databaseSizeBeforeCreate = bankCardTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankCardTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankCardTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankCardType in the database
        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkBrandIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankCardTypeRepository.findAll().size();
        // set the field null
        bankCardType.setBrand(null);

        // Create the BankCardType, which fails.
        BankCardTypeDTO bankCardTypeDTO = bankCardTypeMapper.toDto(bankCardType);

        restBankCardTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankCardTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCardTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankCardTypeRepository.findAll().size();
        // set the field null
        bankCardType.setCardType(null);

        // Create the BankCardType, which fails.
        BankCardTypeDTO bankCardTypeDTO = bankCardTypeMapper.toDto(bankCardType);

        restBankCardTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankCardTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCardNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankCardTypeRepository.findAll().size();
        // set the field null
        bankCardType.setCardNumber(null);

        // Create the BankCardType, which fails.
        BankCardTypeDTO bankCardTypeDTO = bankCardTypeMapper.toDto(bankCardType);

        restBankCardTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankCardTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkExpirationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankCardTypeRepository.findAll().size();
        // set the field null
        bankCardType.setExpirationDate(null);

        // Create the BankCardType, which fails.
        BankCardTypeDTO bankCardTypeDTO = bankCardTypeMapper.toDto(bankCardType);

        restBankCardTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankCardTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCvvIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankCardTypeRepository.findAll().size();
        // set the field null
        bankCardType.setCvv(null);

        // Create the BankCardType, which fails.
        BankCardTypeDTO bankCardTypeDTO = bankCardTypeMapper.toDto(bankCardType);

        restBankCardTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankCardTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastFourDigitsIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankCardTypeRepository.findAll().size();
        // set the field null
        bankCardType.setLastFourDigits(null);

        // Create the BankCardType, which fails.
        BankCardTypeDTO bankCardTypeDTO = bankCardTypeMapper.toDto(bankCardType);

        restBankCardTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankCardTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBankIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankCardTypeRepository.findAll().size();
        // set the field null
        bankCardType.setBank(null);

        // Create the BankCardType, which fails.
        BankCardTypeDTO bankCardTypeDTO = bankCardTypeMapper.toDto(bankCardType);

        restBankCardTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankCardTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBankCardTypes() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList
        restBankCardTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankCardType.getId().intValue())))
            .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND)))
            .andExpect(jsonPath("$.[*].cardType").value(hasItem(DEFAULT_CARD_TYPE)))
            .andExpect(jsonPath("$.[*].cardNumber").value(hasItem(DEFAULT_CARD_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].expirationDate").value(hasItem(DEFAULT_EXPIRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].cvv").value(hasItem(DEFAULT_CVV)))
            .andExpect(jsonPath("$.[*].lastFourDigits").value(hasItem(DEFAULT_LAST_FOUR_DIGITS)))
            .andExpect(jsonPath("$.[*].bank").value(hasItem(DEFAULT_BANK)));
    }

    @Test
    @Transactional
    void getBankCardType() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get the bankCardType
        restBankCardTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, bankCardType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankCardType.getId().intValue()))
            .andExpect(jsonPath("$.brand").value(DEFAULT_BRAND))
            .andExpect(jsonPath("$.cardType").value(DEFAULT_CARD_TYPE))
            .andExpect(jsonPath("$.cardNumber").value(DEFAULT_CARD_NUMBER.intValue()))
            .andExpect(jsonPath("$.expirationDate").value(DEFAULT_EXPIRATION_DATE.toString()))
            .andExpect(jsonPath("$.cvv").value(DEFAULT_CVV))
            .andExpect(jsonPath("$.lastFourDigits").value(DEFAULT_LAST_FOUR_DIGITS))
            .andExpect(jsonPath("$.bank").value(DEFAULT_BANK));
    }

    @Test
    @Transactional
    void getBankCardTypesByIdFiltering() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        Long id = bankCardType.getId();

        defaultBankCardTypeShouldBeFound("id.equals=" + id);
        defaultBankCardTypeShouldNotBeFound("id.notEquals=" + id);

        defaultBankCardTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBankCardTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultBankCardTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBankCardTypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByBrandIsEqualToSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where brand equals to DEFAULT_BRAND
        defaultBankCardTypeShouldBeFound("brand.equals=" + DEFAULT_BRAND);

        // Get all the bankCardTypeList where brand equals to UPDATED_BRAND
        defaultBankCardTypeShouldNotBeFound("brand.equals=" + UPDATED_BRAND);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByBrandIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where brand not equals to DEFAULT_BRAND
        defaultBankCardTypeShouldNotBeFound("brand.notEquals=" + DEFAULT_BRAND);

        // Get all the bankCardTypeList where brand not equals to UPDATED_BRAND
        defaultBankCardTypeShouldBeFound("brand.notEquals=" + UPDATED_BRAND);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByBrandIsInShouldWork() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where brand in DEFAULT_BRAND or UPDATED_BRAND
        defaultBankCardTypeShouldBeFound("brand.in=" + DEFAULT_BRAND + "," + UPDATED_BRAND);

        // Get all the bankCardTypeList where brand equals to UPDATED_BRAND
        defaultBankCardTypeShouldNotBeFound("brand.in=" + UPDATED_BRAND);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByBrandIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where brand is not null
        defaultBankCardTypeShouldBeFound("brand.specified=true");

        // Get all the bankCardTypeList where brand is null
        defaultBankCardTypeShouldNotBeFound("brand.specified=false");
    }

    @Test
    @Transactional
    void getAllBankCardTypesByBrandContainsSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where brand contains DEFAULT_BRAND
        defaultBankCardTypeShouldBeFound("brand.contains=" + DEFAULT_BRAND);

        // Get all the bankCardTypeList where brand contains UPDATED_BRAND
        defaultBankCardTypeShouldNotBeFound("brand.contains=" + UPDATED_BRAND);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByBrandNotContainsSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where brand does not contain DEFAULT_BRAND
        defaultBankCardTypeShouldNotBeFound("brand.doesNotContain=" + DEFAULT_BRAND);

        // Get all the bankCardTypeList where brand does not contain UPDATED_BRAND
        defaultBankCardTypeShouldBeFound("brand.doesNotContain=" + UPDATED_BRAND);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByCardTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where cardType equals to DEFAULT_CARD_TYPE
        defaultBankCardTypeShouldBeFound("cardType.equals=" + DEFAULT_CARD_TYPE);

        // Get all the bankCardTypeList where cardType equals to UPDATED_CARD_TYPE
        defaultBankCardTypeShouldNotBeFound("cardType.equals=" + UPDATED_CARD_TYPE);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByCardTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where cardType not equals to DEFAULT_CARD_TYPE
        defaultBankCardTypeShouldNotBeFound("cardType.notEquals=" + DEFAULT_CARD_TYPE);

        // Get all the bankCardTypeList where cardType not equals to UPDATED_CARD_TYPE
        defaultBankCardTypeShouldBeFound("cardType.notEquals=" + UPDATED_CARD_TYPE);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByCardTypeIsInShouldWork() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where cardType in DEFAULT_CARD_TYPE or UPDATED_CARD_TYPE
        defaultBankCardTypeShouldBeFound("cardType.in=" + DEFAULT_CARD_TYPE + "," + UPDATED_CARD_TYPE);

        // Get all the bankCardTypeList where cardType equals to UPDATED_CARD_TYPE
        defaultBankCardTypeShouldNotBeFound("cardType.in=" + UPDATED_CARD_TYPE);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByCardTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where cardType is not null
        defaultBankCardTypeShouldBeFound("cardType.specified=true");

        // Get all the bankCardTypeList where cardType is null
        defaultBankCardTypeShouldNotBeFound("cardType.specified=false");
    }

    @Test
    @Transactional
    void getAllBankCardTypesByCardTypeContainsSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where cardType contains DEFAULT_CARD_TYPE
        defaultBankCardTypeShouldBeFound("cardType.contains=" + DEFAULT_CARD_TYPE);

        // Get all the bankCardTypeList where cardType contains UPDATED_CARD_TYPE
        defaultBankCardTypeShouldNotBeFound("cardType.contains=" + UPDATED_CARD_TYPE);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByCardTypeNotContainsSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where cardType does not contain DEFAULT_CARD_TYPE
        defaultBankCardTypeShouldNotBeFound("cardType.doesNotContain=" + DEFAULT_CARD_TYPE);

        // Get all the bankCardTypeList where cardType does not contain UPDATED_CARD_TYPE
        defaultBankCardTypeShouldBeFound("cardType.doesNotContain=" + UPDATED_CARD_TYPE);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByCardNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where cardNumber equals to DEFAULT_CARD_NUMBER
        defaultBankCardTypeShouldBeFound("cardNumber.equals=" + DEFAULT_CARD_NUMBER);

        // Get all the bankCardTypeList where cardNumber equals to UPDATED_CARD_NUMBER
        defaultBankCardTypeShouldNotBeFound("cardNumber.equals=" + UPDATED_CARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByCardNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where cardNumber not equals to DEFAULT_CARD_NUMBER
        defaultBankCardTypeShouldNotBeFound("cardNumber.notEquals=" + DEFAULT_CARD_NUMBER);

        // Get all the bankCardTypeList where cardNumber not equals to UPDATED_CARD_NUMBER
        defaultBankCardTypeShouldBeFound("cardNumber.notEquals=" + UPDATED_CARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByCardNumberIsInShouldWork() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where cardNumber in DEFAULT_CARD_NUMBER or UPDATED_CARD_NUMBER
        defaultBankCardTypeShouldBeFound("cardNumber.in=" + DEFAULT_CARD_NUMBER + "," + UPDATED_CARD_NUMBER);

        // Get all the bankCardTypeList where cardNumber equals to UPDATED_CARD_NUMBER
        defaultBankCardTypeShouldNotBeFound("cardNumber.in=" + UPDATED_CARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByCardNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where cardNumber is not null
        defaultBankCardTypeShouldBeFound("cardNumber.specified=true");

        // Get all the bankCardTypeList where cardNumber is null
        defaultBankCardTypeShouldNotBeFound("cardNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllBankCardTypesByCardNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where cardNumber is greater than or equal to DEFAULT_CARD_NUMBER
        defaultBankCardTypeShouldBeFound("cardNumber.greaterThanOrEqual=" + DEFAULT_CARD_NUMBER);

        // Get all the bankCardTypeList where cardNumber is greater than or equal to UPDATED_CARD_NUMBER
        defaultBankCardTypeShouldNotBeFound("cardNumber.greaterThanOrEqual=" + UPDATED_CARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByCardNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where cardNumber is less than or equal to DEFAULT_CARD_NUMBER
        defaultBankCardTypeShouldBeFound("cardNumber.lessThanOrEqual=" + DEFAULT_CARD_NUMBER);

        // Get all the bankCardTypeList where cardNumber is less than or equal to SMALLER_CARD_NUMBER
        defaultBankCardTypeShouldNotBeFound("cardNumber.lessThanOrEqual=" + SMALLER_CARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByCardNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where cardNumber is less than DEFAULT_CARD_NUMBER
        defaultBankCardTypeShouldNotBeFound("cardNumber.lessThan=" + DEFAULT_CARD_NUMBER);

        // Get all the bankCardTypeList where cardNumber is less than UPDATED_CARD_NUMBER
        defaultBankCardTypeShouldBeFound("cardNumber.lessThan=" + UPDATED_CARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByCardNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where cardNumber is greater than DEFAULT_CARD_NUMBER
        defaultBankCardTypeShouldNotBeFound("cardNumber.greaterThan=" + DEFAULT_CARD_NUMBER);

        // Get all the bankCardTypeList where cardNumber is greater than SMALLER_CARD_NUMBER
        defaultBankCardTypeShouldBeFound("cardNumber.greaterThan=" + SMALLER_CARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByExpirationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where expirationDate equals to DEFAULT_EXPIRATION_DATE
        defaultBankCardTypeShouldBeFound("expirationDate.equals=" + DEFAULT_EXPIRATION_DATE);

        // Get all the bankCardTypeList where expirationDate equals to UPDATED_EXPIRATION_DATE
        defaultBankCardTypeShouldNotBeFound("expirationDate.equals=" + UPDATED_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByExpirationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where expirationDate not equals to DEFAULT_EXPIRATION_DATE
        defaultBankCardTypeShouldNotBeFound("expirationDate.notEquals=" + DEFAULT_EXPIRATION_DATE);

        // Get all the bankCardTypeList where expirationDate not equals to UPDATED_EXPIRATION_DATE
        defaultBankCardTypeShouldBeFound("expirationDate.notEquals=" + UPDATED_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByExpirationDateIsInShouldWork() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where expirationDate in DEFAULT_EXPIRATION_DATE or UPDATED_EXPIRATION_DATE
        defaultBankCardTypeShouldBeFound("expirationDate.in=" + DEFAULT_EXPIRATION_DATE + "," + UPDATED_EXPIRATION_DATE);

        // Get all the bankCardTypeList where expirationDate equals to UPDATED_EXPIRATION_DATE
        defaultBankCardTypeShouldNotBeFound("expirationDate.in=" + UPDATED_EXPIRATION_DATE);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByExpirationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where expirationDate is not null
        defaultBankCardTypeShouldBeFound("expirationDate.specified=true");

        // Get all the bankCardTypeList where expirationDate is null
        defaultBankCardTypeShouldNotBeFound("expirationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllBankCardTypesByCvvIsEqualToSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where cvv equals to DEFAULT_CVV
        defaultBankCardTypeShouldBeFound("cvv.equals=" + DEFAULT_CVV);

        // Get all the bankCardTypeList where cvv equals to UPDATED_CVV
        defaultBankCardTypeShouldNotBeFound("cvv.equals=" + UPDATED_CVV);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByCvvIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where cvv not equals to DEFAULT_CVV
        defaultBankCardTypeShouldNotBeFound("cvv.notEquals=" + DEFAULT_CVV);

        // Get all the bankCardTypeList where cvv not equals to UPDATED_CVV
        defaultBankCardTypeShouldBeFound("cvv.notEquals=" + UPDATED_CVV);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByCvvIsInShouldWork() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where cvv in DEFAULT_CVV or UPDATED_CVV
        defaultBankCardTypeShouldBeFound("cvv.in=" + DEFAULT_CVV + "," + UPDATED_CVV);

        // Get all the bankCardTypeList where cvv equals to UPDATED_CVV
        defaultBankCardTypeShouldNotBeFound("cvv.in=" + UPDATED_CVV);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByCvvIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where cvv is not null
        defaultBankCardTypeShouldBeFound("cvv.specified=true");

        // Get all the bankCardTypeList where cvv is null
        defaultBankCardTypeShouldNotBeFound("cvv.specified=false");
    }

    @Test
    @Transactional
    void getAllBankCardTypesByCvvIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where cvv is greater than or equal to DEFAULT_CVV
        defaultBankCardTypeShouldBeFound("cvv.greaterThanOrEqual=" + DEFAULT_CVV);

        // Get all the bankCardTypeList where cvv is greater than or equal to UPDATED_CVV
        defaultBankCardTypeShouldNotBeFound("cvv.greaterThanOrEqual=" + UPDATED_CVV);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByCvvIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where cvv is less than or equal to DEFAULT_CVV
        defaultBankCardTypeShouldBeFound("cvv.lessThanOrEqual=" + DEFAULT_CVV);

        // Get all the bankCardTypeList where cvv is less than or equal to SMALLER_CVV
        defaultBankCardTypeShouldNotBeFound("cvv.lessThanOrEqual=" + SMALLER_CVV);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByCvvIsLessThanSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where cvv is less than DEFAULT_CVV
        defaultBankCardTypeShouldNotBeFound("cvv.lessThan=" + DEFAULT_CVV);

        // Get all the bankCardTypeList where cvv is less than UPDATED_CVV
        defaultBankCardTypeShouldBeFound("cvv.lessThan=" + UPDATED_CVV);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByCvvIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where cvv is greater than DEFAULT_CVV
        defaultBankCardTypeShouldNotBeFound("cvv.greaterThan=" + DEFAULT_CVV);

        // Get all the bankCardTypeList where cvv is greater than SMALLER_CVV
        defaultBankCardTypeShouldBeFound("cvv.greaterThan=" + SMALLER_CVV);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByLastFourDigitsIsEqualToSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where lastFourDigits equals to DEFAULT_LAST_FOUR_DIGITS
        defaultBankCardTypeShouldBeFound("lastFourDigits.equals=" + DEFAULT_LAST_FOUR_DIGITS);

        // Get all the bankCardTypeList where lastFourDigits equals to UPDATED_LAST_FOUR_DIGITS
        defaultBankCardTypeShouldNotBeFound("lastFourDigits.equals=" + UPDATED_LAST_FOUR_DIGITS);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByLastFourDigitsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where lastFourDigits not equals to DEFAULT_LAST_FOUR_DIGITS
        defaultBankCardTypeShouldNotBeFound("lastFourDigits.notEquals=" + DEFAULT_LAST_FOUR_DIGITS);

        // Get all the bankCardTypeList where lastFourDigits not equals to UPDATED_LAST_FOUR_DIGITS
        defaultBankCardTypeShouldBeFound("lastFourDigits.notEquals=" + UPDATED_LAST_FOUR_DIGITS);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByLastFourDigitsIsInShouldWork() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where lastFourDigits in DEFAULT_LAST_FOUR_DIGITS or UPDATED_LAST_FOUR_DIGITS
        defaultBankCardTypeShouldBeFound("lastFourDigits.in=" + DEFAULT_LAST_FOUR_DIGITS + "," + UPDATED_LAST_FOUR_DIGITS);

        // Get all the bankCardTypeList where lastFourDigits equals to UPDATED_LAST_FOUR_DIGITS
        defaultBankCardTypeShouldNotBeFound("lastFourDigits.in=" + UPDATED_LAST_FOUR_DIGITS);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByLastFourDigitsIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where lastFourDigits is not null
        defaultBankCardTypeShouldBeFound("lastFourDigits.specified=true");

        // Get all the bankCardTypeList where lastFourDigits is null
        defaultBankCardTypeShouldNotBeFound("lastFourDigits.specified=false");
    }

    @Test
    @Transactional
    void getAllBankCardTypesByLastFourDigitsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where lastFourDigits is greater than or equal to DEFAULT_LAST_FOUR_DIGITS
        defaultBankCardTypeShouldBeFound("lastFourDigits.greaterThanOrEqual=" + DEFAULT_LAST_FOUR_DIGITS);

        // Get all the bankCardTypeList where lastFourDigits is greater than or equal to UPDATED_LAST_FOUR_DIGITS
        defaultBankCardTypeShouldNotBeFound("lastFourDigits.greaterThanOrEqual=" + UPDATED_LAST_FOUR_DIGITS);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByLastFourDigitsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where lastFourDigits is less than or equal to DEFAULT_LAST_FOUR_DIGITS
        defaultBankCardTypeShouldBeFound("lastFourDigits.lessThanOrEqual=" + DEFAULT_LAST_FOUR_DIGITS);

        // Get all the bankCardTypeList where lastFourDigits is less than or equal to SMALLER_LAST_FOUR_DIGITS
        defaultBankCardTypeShouldNotBeFound("lastFourDigits.lessThanOrEqual=" + SMALLER_LAST_FOUR_DIGITS);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByLastFourDigitsIsLessThanSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where lastFourDigits is less than DEFAULT_LAST_FOUR_DIGITS
        defaultBankCardTypeShouldNotBeFound("lastFourDigits.lessThan=" + DEFAULT_LAST_FOUR_DIGITS);

        // Get all the bankCardTypeList where lastFourDigits is less than UPDATED_LAST_FOUR_DIGITS
        defaultBankCardTypeShouldBeFound("lastFourDigits.lessThan=" + UPDATED_LAST_FOUR_DIGITS);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByLastFourDigitsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where lastFourDigits is greater than DEFAULT_LAST_FOUR_DIGITS
        defaultBankCardTypeShouldNotBeFound("lastFourDigits.greaterThan=" + DEFAULT_LAST_FOUR_DIGITS);

        // Get all the bankCardTypeList where lastFourDigits is greater than SMALLER_LAST_FOUR_DIGITS
        defaultBankCardTypeShouldBeFound("lastFourDigits.greaterThan=" + SMALLER_LAST_FOUR_DIGITS);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByBankIsEqualToSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where bank equals to DEFAULT_BANK
        defaultBankCardTypeShouldBeFound("bank.equals=" + DEFAULT_BANK);

        // Get all the bankCardTypeList where bank equals to UPDATED_BANK
        defaultBankCardTypeShouldNotBeFound("bank.equals=" + UPDATED_BANK);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByBankIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where bank not equals to DEFAULT_BANK
        defaultBankCardTypeShouldNotBeFound("bank.notEquals=" + DEFAULT_BANK);

        // Get all the bankCardTypeList where bank not equals to UPDATED_BANK
        defaultBankCardTypeShouldBeFound("bank.notEquals=" + UPDATED_BANK);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByBankIsInShouldWork() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where bank in DEFAULT_BANK or UPDATED_BANK
        defaultBankCardTypeShouldBeFound("bank.in=" + DEFAULT_BANK + "," + UPDATED_BANK);

        // Get all the bankCardTypeList where bank equals to UPDATED_BANK
        defaultBankCardTypeShouldNotBeFound("bank.in=" + UPDATED_BANK);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByBankIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where bank is not null
        defaultBankCardTypeShouldBeFound("bank.specified=true");

        // Get all the bankCardTypeList where bank is null
        defaultBankCardTypeShouldNotBeFound("bank.specified=false");
    }

    @Test
    @Transactional
    void getAllBankCardTypesByBankContainsSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where bank contains DEFAULT_BANK
        defaultBankCardTypeShouldBeFound("bank.contains=" + DEFAULT_BANK);

        // Get all the bankCardTypeList where bank contains UPDATED_BANK
        defaultBankCardTypeShouldNotBeFound("bank.contains=" + UPDATED_BANK);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByBankNotContainsSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        // Get all the bankCardTypeList where bank does not contain DEFAULT_BANK
        defaultBankCardTypeShouldNotBeFound("bank.doesNotContain=" + DEFAULT_BANK);

        // Get all the bankCardTypeList where bank does not contain UPDATED_BANK
        defaultBankCardTypeShouldBeFound("bank.doesNotContain=" + UPDATED_BANK);
    }

    @Test
    @Transactional
    void getAllBankCardTypesByCustPaymentMethodIsEqualToSomething() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);
        CustPaymentMethod custPaymentMethod = CustPaymentMethodResourceIT.createEntity(em);
        em.persist(custPaymentMethod);
        em.flush();
        bankCardType.setCustPaymentMethod(custPaymentMethod);
        bankCardTypeRepository.saveAndFlush(bankCardType);
        Long custPaymentMethodId = custPaymentMethod.getId();

        // Get all the bankCardTypeList where custPaymentMethod equals to custPaymentMethodId
        defaultBankCardTypeShouldBeFound("custPaymentMethodId.equals=" + custPaymentMethodId);

        // Get all the bankCardTypeList where custPaymentMethod equals to (custPaymentMethodId + 1)
        defaultBankCardTypeShouldNotBeFound("custPaymentMethodId.equals=" + (custPaymentMethodId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBankCardTypeShouldBeFound(String filter) throws Exception {
        restBankCardTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankCardType.getId().intValue())))
            .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND)))
            .andExpect(jsonPath("$.[*].cardType").value(hasItem(DEFAULT_CARD_TYPE)))
            .andExpect(jsonPath("$.[*].cardNumber").value(hasItem(DEFAULT_CARD_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].expirationDate").value(hasItem(DEFAULT_EXPIRATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].cvv").value(hasItem(DEFAULT_CVV)))
            .andExpect(jsonPath("$.[*].lastFourDigits").value(hasItem(DEFAULT_LAST_FOUR_DIGITS)))
            .andExpect(jsonPath("$.[*].bank").value(hasItem(DEFAULT_BANK)));

        // Check, that the count call also returns 1
        restBankCardTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBankCardTypeShouldNotBeFound(String filter) throws Exception {
        restBankCardTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBankCardTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBankCardType() throws Exception {
        // Get the bankCardType
        restBankCardTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBankCardType() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        int databaseSizeBeforeUpdate = bankCardTypeRepository.findAll().size();

        // Update the bankCardType
        BankCardType updatedBankCardType = bankCardTypeRepository.findById(bankCardType.getId()).get();
        // Disconnect from session so that the updates on updatedBankCardType are not directly saved in db
        em.detach(updatedBankCardType);
        updatedBankCardType
            .brand(UPDATED_BRAND)
            .cardType(UPDATED_CARD_TYPE)
            .cardNumber(UPDATED_CARD_NUMBER)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .cvv(UPDATED_CVV)
            .lastFourDigits(UPDATED_LAST_FOUR_DIGITS)
            .bank(UPDATED_BANK);
        BankCardTypeDTO bankCardTypeDTO = bankCardTypeMapper.toDto(updatedBankCardType);

        restBankCardTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankCardTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankCardTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the BankCardType in the database
        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeUpdate);
        BankCardType testBankCardType = bankCardTypeList.get(bankCardTypeList.size() - 1);
        assertThat(testBankCardType.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testBankCardType.getCardType()).isEqualTo(UPDATED_CARD_TYPE);
        assertThat(testBankCardType.getCardNumber()).isEqualTo(UPDATED_CARD_NUMBER);
        assertThat(testBankCardType.getExpirationDate()).isEqualTo(UPDATED_EXPIRATION_DATE);
        assertThat(testBankCardType.getCvv()).isEqualTo(UPDATED_CVV);
        assertThat(testBankCardType.getLastFourDigits()).isEqualTo(UPDATED_LAST_FOUR_DIGITS);
        assertThat(testBankCardType.getBank()).isEqualTo(UPDATED_BANK);
    }

    @Test
    @Transactional
    void putNonExistingBankCardType() throws Exception {
        int databaseSizeBeforeUpdate = bankCardTypeRepository.findAll().size();
        bankCardType.setId(count.incrementAndGet());

        // Create the BankCardType
        BankCardTypeDTO bankCardTypeDTO = bankCardTypeMapper.toDto(bankCardType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankCardTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankCardTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankCardTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankCardType in the database
        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankCardType() throws Exception {
        int databaseSizeBeforeUpdate = bankCardTypeRepository.findAll().size();
        bankCardType.setId(count.incrementAndGet());

        // Create the BankCardType
        BankCardTypeDTO bankCardTypeDTO = bankCardTypeMapper.toDto(bankCardType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankCardTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankCardTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankCardType in the database
        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankCardType() throws Exception {
        int databaseSizeBeforeUpdate = bankCardTypeRepository.findAll().size();
        bankCardType.setId(count.incrementAndGet());

        // Create the BankCardType
        BankCardTypeDTO bankCardTypeDTO = bankCardTypeMapper.toDto(bankCardType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankCardTypeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankCardTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankCardType in the database
        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankCardTypeWithPatch() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        int databaseSizeBeforeUpdate = bankCardTypeRepository.findAll().size();

        // Update the bankCardType using partial update
        BankCardType partialUpdatedBankCardType = new BankCardType();
        partialUpdatedBankCardType.setId(bankCardType.getId());

        partialUpdatedBankCardType.brand(UPDATED_BRAND).cardNumber(UPDATED_CARD_NUMBER).bank(UPDATED_BANK);

        restBankCardTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankCardType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankCardType))
            )
            .andExpect(status().isOk());

        // Validate the BankCardType in the database
        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeUpdate);
        BankCardType testBankCardType = bankCardTypeList.get(bankCardTypeList.size() - 1);
        assertThat(testBankCardType.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testBankCardType.getCardType()).isEqualTo(DEFAULT_CARD_TYPE);
        assertThat(testBankCardType.getCardNumber()).isEqualTo(UPDATED_CARD_NUMBER);
        assertThat(testBankCardType.getExpirationDate()).isEqualTo(DEFAULT_EXPIRATION_DATE);
        assertThat(testBankCardType.getCvv()).isEqualTo(DEFAULT_CVV);
        assertThat(testBankCardType.getLastFourDigits()).isEqualTo(DEFAULT_LAST_FOUR_DIGITS);
        assertThat(testBankCardType.getBank()).isEqualTo(UPDATED_BANK);
    }

    @Test
    @Transactional
    void fullUpdateBankCardTypeWithPatch() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        int databaseSizeBeforeUpdate = bankCardTypeRepository.findAll().size();

        // Update the bankCardType using partial update
        BankCardType partialUpdatedBankCardType = new BankCardType();
        partialUpdatedBankCardType.setId(bankCardType.getId());

        partialUpdatedBankCardType
            .brand(UPDATED_BRAND)
            .cardType(UPDATED_CARD_TYPE)
            .cardNumber(UPDATED_CARD_NUMBER)
            .expirationDate(UPDATED_EXPIRATION_DATE)
            .cvv(UPDATED_CVV)
            .lastFourDigits(UPDATED_LAST_FOUR_DIGITS)
            .bank(UPDATED_BANK);

        restBankCardTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankCardType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankCardType))
            )
            .andExpect(status().isOk());

        // Validate the BankCardType in the database
        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeUpdate);
        BankCardType testBankCardType = bankCardTypeList.get(bankCardTypeList.size() - 1);
        assertThat(testBankCardType.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testBankCardType.getCardType()).isEqualTo(UPDATED_CARD_TYPE);
        assertThat(testBankCardType.getCardNumber()).isEqualTo(UPDATED_CARD_NUMBER);
        assertThat(testBankCardType.getExpirationDate()).isEqualTo(UPDATED_EXPIRATION_DATE);
        assertThat(testBankCardType.getCvv()).isEqualTo(UPDATED_CVV);
        assertThat(testBankCardType.getLastFourDigits()).isEqualTo(UPDATED_LAST_FOUR_DIGITS);
        assertThat(testBankCardType.getBank()).isEqualTo(UPDATED_BANK);
    }

    @Test
    @Transactional
    void patchNonExistingBankCardType() throws Exception {
        int databaseSizeBeforeUpdate = bankCardTypeRepository.findAll().size();
        bankCardType.setId(count.incrementAndGet());

        // Create the BankCardType
        BankCardTypeDTO bankCardTypeDTO = bankCardTypeMapper.toDto(bankCardType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankCardTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankCardTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankCardTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankCardType in the database
        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankCardType() throws Exception {
        int databaseSizeBeforeUpdate = bankCardTypeRepository.findAll().size();
        bankCardType.setId(count.incrementAndGet());

        // Create the BankCardType
        BankCardTypeDTO bankCardTypeDTO = bankCardTypeMapper.toDto(bankCardType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankCardTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankCardTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankCardType in the database
        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankCardType() throws Exception {
        int databaseSizeBeforeUpdate = bankCardTypeRepository.findAll().size();
        bankCardType.setId(count.incrementAndGet());

        // Create the BankCardType
        BankCardTypeDTO bankCardTypeDTO = bankCardTypeMapper.toDto(bankCardType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankCardTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankCardTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankCardType in the database
        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankCardType() throws Exception {
        // Initialize the database
        bankCardTypeRepository.saveAndFlush(bankCardType);

        int databaseSizeBeforeDelete = bankCardTypeRepository.findAll().size();

        // Delete the bankCardType
        restBankCardTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankCardType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankCardType> bankCardTypeList = bankCardTypeRepository.findAll();
        assertThat(bankCardTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
