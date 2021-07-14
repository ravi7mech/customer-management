package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.AutoPay;
import com.apptium.customer.repository.AutoPayRepository;
import com.apptium.customer.service.criteria.AutoPayCriteria;
import com.apptium.customer.service.dto.AutoPayDTO;
import com.apptium.customer.service.mapper.AutoPayMapper;
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
 * Integration tests for the {@link AutoPayResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AutoPayResourceIT {

    private static final String DEFAULT_CHANNEL = "AAAAAAAAAA";
    private static final String UPDATED_CHANNEL = "BBBBBBBBBB";

    private static final Long DEFAULT_AUTO_PAY_ID = 1L;
    private static final Long UPDATED_AUTO_PAY_ID = 2L;
    private static final Long SMALLER_AUTO_PAY_ID = 1L - 1L;

    private static final LocalDate DEFAULT_DEBIT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEBIT_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DEBIT_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;
    private static final Long SMALLER_CUSTOMER_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/auto-pays";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AutoPayRepository autoPayRepository;

    @Autowired
    private AutoPayMapper autoPayMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAutoPayMockMvc;

    private AutoPay autoPay;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AutoPay createEntity(EntityManager em) {
        AutoPay autoPay = new AutoPay()
            .channel(DEFAULT_CHANNEL)
            .autoPayId(DEFAULT_AUTO_PAY_ID)
            .debitDate(DEFAULT_DEBIT_DATE)
            .status(DEFAULT_STATUS)
            .customerId(DEFAULT_CUSTOMER_ID);
        return autoPay;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AutoPay createUpdatedEntity(EntityManager em) {
        AutoPay autoPay = new AutoPay()
            .channel(UPDATED_CHANNEL)
            .autoPayId(UPDATED_AUTO_PAY_ID)
            .debitDate(UPDATED_DEBIT_DATE)
            .status(UPDATED_STATUS)
            .customerId(UPDATED_CUSTOMER_ID);
        return autoPay;
    }

    @BeforeEach
    public void initTest() {
        autoPay = createEntity(em);
    }

    @Test
    @Transactional
    void createAutoPay() throws Exception {
        int databaseSizeBeforeCreate = autoPayRepository.findAll().size();
        // Create the AutoPay
        AutoPayDTO autoPayDTO = autoPayMapper.toDto(autoPay);
        restAutoPayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autoPayDTO)))
            .andExpect(status().isCreated());

        // Validate the AutoPay in the database
        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeCreate + 1);
        AutoPay testAutoPay = autoPayList.get(autoPayList.size() - 1);
        assertThat(testAutoPay.getChannel()).isEqualTo(DEFAULT_CHANNEL);
        assertThat(testAutoPay.getAutoPayId()).isEqualTo(DEFAULT_AUTO_PAY_ID);
        assertThat(testAutoPay.getDebitDate()).isEqualTo(DEFAULT_DEBIT_DATE);
        assertThat(testAutoPay.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAutoPay.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void createAutoPayWithExistingId() throws Exception {
        // Create the AutoPay with an existing ID
        autoPay.setId(1L);
        AutoPayDTO autoPayDTO = autoPayMapper.toDto(autoPay);

        int databaseSizeBeforeCreate = autoPayRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutoPayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autoPayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AutoPay in the database
        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkChannelIsRequired() throws Exception {
        int databaseSizeBeforeTest = autoPayRepository.findAll().size();
        // set the field null
        autoPay.setChannel(null);

        // Create the AutoPay, which fails.
        AutoPayDTO autoPayDTO = autoPayMapper.toDto(autoPay);

        restAutoPayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autoPayDTO)))
            .andExpect(status().isBadRequest());

        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAutoPayIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = autoPayRepository.findAll().size();
        // set the field null
        autoPay.setAutoPayId(null);

        // Create the AutoPay, which fails.
        AutoPayDTO autoPayDTO = autoPayMapper.toDto(autoPay);

        restAutoPayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autoPayDTO)))
            .andExpect(status().isBadRequest());

        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDebitDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = autoPayRepository.findAll().size();
        // set the field null
        autoPay.setDebitDate(null);

        // Create the AutoPay, which fails.
        AutoPayDTO autoPayDTO = autoPayMapper.toDto(autoPay);

        restAutoPayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autoPayDTO)))
            .andExpect(status().isBadRequest());

        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = autoPayRepository.findAll().size();
        // set the field null
        autoPay.setStatus(null);

        // Create the AutoPay, which fails.
        AutoPayDTO autoPayDTO = autoPayMapper.toDto(autoPay);

        restAutoPayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autoPayDTO)))
            .andExpect(status().isBadRequest());

        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = autoPayRepository.findAll().size();
        // set the field null
        autoPay.setCustomerId(null);

        // Create the AutoPay, which fails.
        AutoPayDTO autoPayDTO = autoPayMapper.toDto(autoPay);

        restAutoPayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autoPayDTO)))
            .andExpect(status().isBadRequest());

        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAutoPays() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList
        restAutoPayMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autoPay.getId().intValue())))
            .andExpect(jsonPath("$.[*].channel").value(hasItem(DEFAULT_CHANNEL)))
            .andExpect(jsonPath("$.[*].autoPayId").value(hasItem(DEFAULT_AUTO_PAY_ID.intValue())))
            .andExpect(jsonPath("$.[*].debitDate").value(hasItem(DEFAULT_DEBIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())));
    }

    @Test
    @Transactional
    void getAutoPay() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get the autoPay
        restAutoPayMockMvc
            .perform(get(ENTITY_API_URL_ID, autoPay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(autoPay.getId().intValue()))
            .andExpect(jsonPath("$.channel").value(DEFAULT_CHANNEL))
            .andExpect(jsonPath("$.autoPayId").value(DEFAULT_AUTO_PAY_ID.intValue()))
            .andExpect(jsonPath("$.debitDate").value(DEFAULT_DEBIT_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()));
    }

    @Test
    @Transactional
    void getAutoPaysByIdFiltering() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        Long id = autoPay.getId();

        defaultAutoPayShouldBeFound("id.equals=" + id);
        defaultAutoPayShouldNotBeFound("id.notEquals=" + id);

        defaultAutoPayShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAutoPayShouldNotBeFound("id.greaterThan=" + id);

        defaultAutoPayShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAutoPayShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAutoPaysByChannelIsEqualToSomething() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where channel equals to DEFAULT_CHANNEL
        defaultAutoPayShouldBeFound("channel.equals=" + DEFAULT_CHANNEL);

        // Get all the autoPayList where channel equals to UPDATED_CHANNEL
        defaultAutoPayShouldNotBeFound("channel.equals=" + UPDATED_CHANNEL);
    }

    @Test
    @Transactional
    void getAllAutoPaysByChannelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where channel not equals to DEFAULT_CHANNEL
        defaultAutoPayShouldNotBeFound("channel.notEquals=" + DEFAULT_CHANNEL);

        // Get all the autoPayList where channel not equals to UPDATED_CHANNEL
        defaultAutoPayShouldBeFound("channel.notEquals=" + UPDATED_CHANNEL);
    }

    @Test
    @Transactional
    void getAllAutoPaysByChannelIsInShouldWork() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where channel in DEFAULT_CHANNEL or UPDATED_CHANNEL
        defaultAutoPayShouldBeFound("channel.in=" + DEFAULT_CHANNEL + "," + UPDATED_CHANNEL);

        // Get all the autoPayList where channel equals to UPDATED_CHANNEL
        defaultAutoPayShouldNotBeFound("channel.in=" + UPDATED_CHANNEL);
    }

    @Test
    @Transactional
    void getAllAutoPaysByChannelIsNullOrNotNull() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where channel is not null
        defaultAutoPayShouldBeFound("channel.specified=true");

        // Get all the autoPayList where channel is null
        defaultAutoPayShouldNotBeFound("channel.specified=false");
    }

    @Test
    @Transactional
    void getAllAutoPaysByChannelContainsSomething() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where channel contains DEFAULT_CHANNEL
        defaultAutoPayShouldBeFound("channel.contains=" + DEFAULT_CHANNEL);

        // Get all the autoPayList where channel contains UPDATED_CHANNEL
        defaultAutoPayShouldNotBeFound("channel.contains=" + UPDATED_CHANNEL);
    }

    @Test
    @Transactional
    void getAllAutoPaysByChannelNotContainsSomething() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where channel does not contain DEFAULT_CHANNEL
        defaultAutoPayShouldNotBeFound("channel.doesNotContain=" + DEFAULT_CHANNEL);

        // Get all the autoPayList where channel does not contain UPDATED_CHANNEL
        defaultAutoPayShouldBeFound("channel.doesNotContain=" + UPDATED_CHANNEL);
    }

    @Test
    @Transactional
    void getAllAutoPaysByAutoPayIdIsEqualToSomething() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where autoPayId equals to DEFAULT_AUTO_PAY_ID
        defaultAutoPayShouldBeFound("autoPayId.equals=" + DEFAULT_AUTO_PAY_ID);

        // Get all the autoPayList where autoPayId equals to UPDATED_AUTO_PAY_ID
        defaultAutoPayShouldNotBeFound("autoPayId.equals=" + UPDATED_AUTO_PAY_ID);
    }

    @Test
    @Transactional
    void getAllAutoPaysByAutoPayIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where autoPayId not equals to DEFAULT_AUTO_PAY_ID
        defaultAutoPayShouldNotBeFound("autoPayId.notEquals=" + DEFAULT_AUTO_PAY_ID);

        // Get all the autoPayList where autoPayId not equals to UPDATED_AUTO_PAY_ID
        defaultAutoPayShouldBeFound("autoPayId.notEquals=" + UPDATED_AUTO_PAY_ID);
    }

    @Test
    @Transactional
    void getAllAutoPaysByAutoPayIdIsInShouldWork() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where autoPayId in DEFAULT_AUTO_PAY_ID or UPDATED_AUTO_PAY_ID
        defaultAutoPayShouldBeFound("autoPayId.in=" + DEFAULT_AUTO_PAY_ID + "," + UPDATED_AUTO_PAY_ID);

        // Get all the autoPayList where autoPayId equals to UPDATED_AUTO_PAY_ID
        defaultAutoPayShouldNotBeFound("autoPayId.in=" + UPDATED_AUTO_PAY_ID);
    }

    @Test
    @Transactional
    void getAllAutoPaysByAutoPayIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where autoPayId is not null
        defaultAutoPayShouldBeFound("autoPayId.specified=true");

        // Get all the autoPayList where autoPayId is null
        defaultAutoPayShouldNotBeFound("autoPayId.specified=false");
    }

    @Test
    @Transactional
    void getAllAutoPaysByAutoPayIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where autoPayId is greater than or equal to DEFAULT_AUTO_PAY_ID
        defaultAutoPayShouldBeFound("autoPayId.greaterThanOrEqual=" + DEFAULT_AUTO_PAY_ID);

        // Get all the autoPayList where autoPayId is greater than or equal to UPDATED_AUTO_PAY_ID
        defaultAutoPayShouldNotBeFound("autoPayId.greaterThanOrEqual=" + UPDATED_AUTO_PAY_ID);
    }

    @Test
    @Transactional
    void getAllAutoPaysByAutoPayIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where autoPayId is less than or equal to DEFAULT_AUTO_PAY_ID
        defaultAutoPayShouldBeFound("autoPayId.lessThanOrEqual=" + DEFAULT_AUTO_PAY_ID);

        // Get all the autoPayList where autoPayId is less than or equal to SMALLER_AUTO_PAY_ID
        defaultAutoPayShouldNotBeFound("autoPayId.lessThanOrEqual=" + SMALLER_AUTO_PAY_ID);
    }

    @Test
    @Transactional
    void getAllAutoPaysByAutoPayIdIsLessThanSomething() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where autoPayId is less than DEFAULT_AUTO_PAY_ID
        defaultAutoPayShouldNotBeFound("autoPayId.lessThan=" + DEFAULT_AUTO_PAY_ID);

        // Get all the autoPayList where autoPayId is less than UPDATED_AUTO_PAY_ID
        defaultAutoPayShouldBeFound("autoPayId.lessThan=" + UPDATED_AUTO_PAY_ID);
    }

    @Test
    @Transactional
    void getAllAutoPaysByAutoPayIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where autoPayId is greater than DEFAULT_AUTO_PAY_ID
        defaultAutoPayShouldNotBeFound("autoPayId.greaterThan=" + DEFAULT_AUTO_PAY_ID);

        // Get all the autoPayList where autoPayId is greater than SMALLER_AUTO_PAY_ID
        defaultAutoPayShouldBeFound("autoPayId.greaterThan=" + SMALLER_AUTO_PAY_ID);
    }

    @Test
    @Transactional
    void getAllAutoPaysByDebitDateIsEqualToSomething() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where debitDate equals to DEFAULT_DEBIT_DATE
        defaultAutoPayShouldBeFound("debitDate.equals=" + DEFAULT_DEBIT_DATE);

        // Get all the autoPayList where debitDate equals to UPDATED_DEBIT_DATE
        defaultAutoPayShouldNotBeFound("debitDate.equals=" + UPDATED_DEBIT_DATE);
    }

    @Test
    @Transactional
    void getAllAutoPaysByDebitDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where debitDate not equals to DEFAULT_DEBIT_DATE
        defaultAutoPayShouldNotBeFound("debitDate.notEquals=" + DEFAULT_DEBIT_DATE);

        // Get all the autoPayList where debitDate not equals to UPDATED_DEBIT_DATE
        defaultAutoPayShouldBeFound("debitDate.notEquals=" + UPDATED_DEBIT_DATE);
    }

    @Test
    @Transactional
    void getAllAutoPaysByDebitDateIsInShouldWork() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where debitDate in DEFAULT_DEBIT_DATE or UPDATED_DEBIT_DATE
        defaultAutoPayShouldBeFound("debitDate.in=" + DEFAULT_DEBIT_DATE + "," + UPDATED_DEBIT_DATE);

        // Get all the autoPayList where debitDate equals to UPDATED_DEBIT_DATE
        defaultAutoPayShouldNotBeFound("debitDate.in=" + UPDATED_DEBIT_DATE);
    }

    @Test
    @Transactional
    void getAllAutoPaysByDebitDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where debitDate is not null
        defaultAutoPayShouldBeFound("debitDate.specified=true");

        // Get all the autoPayList where debitDate is null
        defaultAutoPayShouldNotBeFound("debitDate.specified=false");
    }

    @Test
    @Transactional
    void getAllAutoPaysByDebitDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where debitDate is greater than or equal to DEFAULT_DEBIT_DATE
        defaultAutoPayShouldBeFound("debitDate.greaterThanOrEqual=" + DEFAULT_DEBIT_DATE);

        // Get all the autoPayList where debitDate is greater than or equal to UPDATED_DEBIT_DATE
        defaultAutoPayShouldNotBeFound("debitDate.greaterThanOrEqual=" + UPDATED_DEBIT_DATE);
    }

    @Test
    @Transactional
    void getAllAutoPaysByDebitDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where debitDate is less than or equal to DEFAULT_DEBIT_DATE
        defaultAutoPayShouldBeFound("debitDate.lessThanOrEqual=" + DEFAULT_DEBIT_DATE);

        // Get all the autoPayList where debitDate is less than or equal to SMALLER_DEBIT_DATE
        defaultAutoPayShouldNotBeFound("debitDate.lessThanOrEqual=" + SMALLER_DEBIT_DATE);
    }

    @Test
    @Transactional
    void getAllAutoPaysByDebitDateIsLessThanSomething() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where debitDate is less than DEFAULT_DEBIT_DATE
        defaultAutoPayShouldNotBeFound("debitDate.lessThan=" + DEFAULT_DEBIT_DATE);

        // Get all the autoPayList where debitDate is less than UPDATED_DEBIT_DATE
        defaultAutoPayShouldBeFound("debitDate.lessThan=" + UPDATED_DEBIT_DATE);
    }

    @Test
    @Transactional
    void getAllAutoPaysByDebitDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where debitDate is greater than DEFAULT_DEBIT_DATE
        defaultAutoPayShouldNotBeFound("debitDate.greaterThan=" + DEFAULT_DEBIT_DATE);

        // Get all the autoPayList where debitDate is greater than SMALLER_DEBIT_DATE
        defaultAutoPayShouldBeFound("debitDate.greaterThan=" + SMALLER_DEBIT_DATE);
    }

    @Test
    @Transactional
    void getAllAutoPaysByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where status equals to DEFAULT_STATUS
        defaultAutoPayShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the autoPayList where status equals to UPDATED_STATUS
        defaultAutoPayShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAutoPaysByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where status not equals to DEFAULT_STATUS
        defaultAutoPayShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the autoPayList where status not equals to UPDATED_STATUS
        defaultAutoPayShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAutoPaysByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultAutoPayShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the autoPayList where status equals to UPDATED_STATUS
        defaultAutoPayShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAutoPaysByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where status is not null
        defaultAutoPayShouldBeFound("status.specified=true");

        // Get all the autoPayList where status is null
        defaultAutoPayShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllAutoPaysByStatusContainsSomething() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where status contains DEFAULT_STATUS
        defaultAutoPayShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the autoPayList where status contains UPDATED_STATUS
        defaultAutoPayShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAutoPaysByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where status does not contain DEFAULT_STATUS
        defaultAutoPayShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the autoPayList where status does not contain UPDATED_STATUS
        defaultAutoPayShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAutoPaysByCustomerIdIsEqualToSomething() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where customerId equals to DEFAULT_CUSTOMER_ID
        defaultAutoPayShouldBeFound("customerId.equals=" + DEFAULT_CUSTOMER_ID);

        // Get all the autoPayList where customerId equals to UPDATED_CUSTOMER_ID
        defaultAutoPayShouldNotBeFound("customerId.equals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllAutoPaysByCustomerIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where customerId not equals to DEFAULT_CUSTOMER_ID
        defaultAutoPayShouldNotBeFound("customerId.notEquals=" + DEFAULT_CUSTOMER_ID);

        // Get all the autoPayList where customerId not equals to UPDATED_CUSTOMER_ID
        defaultAutoPayShouldBeFound("customerId.notEquals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllAutoPaysByCustomerIdIsInShouldWork() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where customerId in DEFAULT_CUSTOMER_ID or UPDATED_CUSTOMER_ID
        defaultAutoPayShouldBeFound("customerId.in=" + DEFAULT_CUSTOMER_ID + "," + UPDATED_CUSTOMER_ID);

        // Get all the autoPayList where customerId equals to UPDATED_CUSTOMER_ID
        defaultAutoPayShouldNotBeFound("customerId.in=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllAutoPaysByCustomerIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where customerId is not null
        defaultAutoPayShouldBeFound("customerId.specified=true");

        // Get all the autoPayList where customerId is null
        defaultAutoPayShouldNotBeFound("customerId.specified=false");
    }

    @Test
    @Transactional
    void getAllAutoPaysByCustomerIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where customerId is greater than or equal to DEFAULT_CUSTOMER_ID
        defaultAutoPayShouldBeFound("customerId.greaterThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the autoPayList where customerId is greater than or equal to UPDATED_CUSTOMER_ID
        defaultAutoPayShouldNotBeFound("customerId.greaterThanOrEqual=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllAutoPaysByCustomerIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where customerId is less than or equal to DEFAULT_CUSTOMER_ID
        defaultAutoPayShouldBeFound("customerId.lessThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the autoPayList where customerId is less than or equal to SMALLER_CUSTOMER_ID
        defaultAutoPayShouldNotBeFound("customerId.lessThanOrEqual=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllAutoPaysByCustomerIdIsLessThanSomething() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where customerId is less than DEFAULT_CUSTOMER_ID
        defaultAutoPayShouldNotBeFound("customerId.lessThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the autoPayList where customerId is less than UPDATED_CUSTOMER_ID
        defaultAutoPayShouldBeFound("customerId.lessThan=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllAutoPaysByCustomerIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        // Get all the autoPayList where customerId is greater than DEFAULT_CUSTOMER_ID
        defaultAutoPayShouldNotBeFound("customerId.greaterThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the autoPayList where customerId is greater than SMALLER_CUSTOMER_ID
        defaultAutoPayShouldBeFound("customerId.greaterThan=" + SMALLER_CUSTOMER_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAutoPayShouldBeFound(String filter) throws Exception {
        restAutoPayMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autoPay.getId().intValue())))
            .andExpect(jsonPath("$.[*].channel").value(hasItem(DEFAULT_CHANNEL)))
            .andExpect(jsonPath("$.[*].autoPayId").value(hasItem(DEFAULT_AUTO_PAY_ID.intValue())))
            .andExpect(jsonPath("$.[*].debitDate").value(hasItem(DEFAULT_DEBIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())));

        // Check, that the count call also returns 1
        restAutoPayMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAutoPayShouldNotBeFound(String filter) throws Exception {
        restAutoPayMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAutoPayMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAutoPay() throws Exception {
        // Get the autoPay
        restAutoPayMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAutoPay() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        int databaseSizeBeforeUpdate = autoPayRepository.findAll().size();

        // Update the autoPay
        AutoPay updatedAutoPay = autoPayRepository.findById(autoPay.getId()).get();
        // Disconnect from session so that the updates on updatedAutoPay are not directly saved in db
        em.detach(updatedAutoPay);
        updatedAutoPay
            .channel(UPDATED_CHANNEL)
            .autoPayId(UPDATED_AUTO_PAY_ID)
            .debitDate(UPDATED_DEBIT_DATE)
            .status(UPDATED_STATUS)
            .customerId(UPDATED_CUSTOMER_ID);
        AutoPayDTO autoPayDTO = autoPayMapper.toDto(updatedAutoPay);

        restAutoPayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, autoPayDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(autoPayDTO))
            )
            .andExpect(status().isOk());

        // Validate the AutoPay in the database
        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeUpdate);
        AutoPay testAutoPay = autoPayList.get(autoPayList.size() - 1);
        assertThat(testAutoPay.getChannel()).isEqualTo(UPDATED_CHANNEL);
        assertThat(testAutoPay.getAutoPayId()).isEqualTo(UPDATED_AUTO_PAY_ID);
        assertThat(testAutoPay.getDebitDate()).isEqualTo(UPDATED_DEBIT_DATE);
        assertThat(testAutoPay.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAutoPay.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void putNonExistingAutoPay() throws Exception {
        int databaseSizeBeforeUpdate = autoPayRepository.findAll().size();
        autoPay.setId(count.incrementAndGet());

        // Create the AutoPay
        AutoPayDTO autoPayDTO = autoPayMapper.toDto(autoPay);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutoPayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, autoPayDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(autoPayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AutoPay in the database
        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAutoPay() throws Exception {
        int databaseSizeBeforeUpdate = autoPayRepository.findAll().size();
        autoPay.setId(count.incrementAndGet());

        // Create the AutoPay
        AutoPayDTO autoPayDTO = autoPayMapper.toDto(autoPay);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutoPayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(autoPayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AutoPay in the database
        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAutoPay() throws Exception {
        int databaseSizeBeforeUpdate = autoPayRepository.findAll().size();
        autoPay.setId(count.incrementAndGet());

        // Create the AutoPay
        AutoPayDTO autoPayDTO = autoPayMapper.toDto(autoPay);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutoPayMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autoPayDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AutoPay in the database
        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAutoPayWithPatch() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        int databaseSizeBeforeUpdate = autoPayRepository.findAll().size();

        // Update the autoPay using partial update
        AutoPay partialUpdatedAutoPay = new AutoPay();
        partialUpdatedAutoPay.setId(autoPay.getId());

        partialUpdatedAutoPay.autoPayId(UPDATED_AUTO_PAY_ID).debitDate(UPDATED_DEBIT_DATE).customerId(UPDATED_CUSTOMER_ID);

        restAutoPayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutoPay.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAutoPay))
            )
            .andExpect(status().isOk());

        // Validate the AutoPay in the database
        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeUpdate);
        AutoPay testAutoPay = autoPayList.get(autoPayList.size() - 1);
        assertThat(testAutoPay.getChannel()).isEqualTo(DEFAULT_CHANNEL);
        assertThat(testAutoPay.getAutoPayId()).isEqualTo(UPDATED_AUTO_PAY_ID);
        assertThat(testAutoPay.getDebitDate()).isEqualTo(UPDATED_DEBIT_DATE);
        assertThat(testAutoPay.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAutoPay.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void fullUpdateAutoPayWithPatch() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        int databaseSizeBeforeUpdate = autoPayRepository.findAll().size();

        // Update the autoPay using partial update
        AutoPay partialUpdatedAutoPay = new AutoPay();
        partialUpdatedAutoPay.setId(autoPay.getId());

        partialUpdatedAutoPay
            .channel(UPDATED_CHANNEL)
            .autoPayId(UPDATED_AUTO_PAY_ID)
            .debitDate(UPDATED_DEBIT_DATE)
            .status(UPDATED_STATUS)
            .customerId(UPDATED_CUSTOMER_ID);

        restAutoPayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutoPay.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAutoPay))
            )
            .andExpect(status().isOk());

        // Validate the AutoPay in the database
        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeUpdate);
        AutoPay testAutoPay = autoPayList.get(autoPayList.size() - 1);
        assertThat(testAutoPay.getChannel()).isEqualTo(UPDATED_CHANNEL);
        assertThat(testAutoPay.getAutoPayId()).isEqualTo(UPDATED_AUTO_PAY_ID);
        assertThat(testAutoPay.getDebitDate()).isEqualTo(UPDATED_DEBIT_DATE);
        assertThat(testAutoPay.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAutoPay.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingAutoPay() throws Exception {
        int databaseSizeBeforeUpdate = autoPayRepository.findAll().size();
        autoPay.setId(count.incrementAndGet());

        // Create the AutoPay
        AutoPayDTO autoPayDTO = autoPayMapper.toDto(autoPay);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutoPayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, autoPayDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(autoPayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AutoPay in the database
        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAutoPay() throws Exception {
        int databaseSizeBeforeUpdate = autoPayRepository.findAll().size();
        autoPay.setId(count.incrementAndGet());

        // Create the AutoPay
        AutoPayDTO autoPayDTO = autoPayMapper.toDto(autoPay);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutoPayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(autoPayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AutoPay in the database
        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAutoPay() throws Exception {
        int databaseSizeBeforeUpdate = autoPayRepository.findAll().size();
        autoPay.setId(count.incrementAndGet());

        // Create the AutoPay
        AutoPayDTO autoPayDTO = autoPayMapper.toDto(autoPay);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutoPayMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(autoPayDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AutoPay in the database
        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAutoPay() throws Exception {
        // Initialize the database
        autoPayRepository.saveAndFlush(autoPay);

        int databaseSizeBeforeDelete = autoPayRepository.findAll().size();

        // Delete the autoPay
        restAutoPayMockMvc
            .perform(delete(ENTITY_API_URL_ID, autoPay.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AutoPay> autoPayList = autoPayRepository.findAll();
        assertThat(autoPayList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
