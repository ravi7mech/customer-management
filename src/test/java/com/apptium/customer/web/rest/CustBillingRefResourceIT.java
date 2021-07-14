package com.apptium.customer.web.rest;

import static com.apptium.customer.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.CustBillingRef;
import com.apptium.customer.domain.CustPaymentMethod;
import com.apptium.customer.domain.Customer;
import com.apptium.customer.repository.CustBillingRefRepository;
import com.apptium.customer.service.criteria.CustBillingRefCriteria;
import com.apptium.customer.service.dto.CustBillingRefDTO;
import com.apptium.customer.service.mapper.CustBillingRefMapper;
import java.math.BigDecimal;
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
 * Integration tests for the {@link CustBillingRefResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustBillingRefResourceIT {

    private static final BigDecimal DEFAULT_AMOUNT_DUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT_DUE = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT_DUE = new BigDecimal(1 - 1);

    private static final Instant DEFAULT_BILL_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BILL_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_BILL_NO = 1L;
    private static final Long UPDATED_BILL_NO = 2L;
    private static final Long SMALLER_BILL_NO = 1L - 1L;

    private static final Instant DEFAULT_BILLING_PERIOD = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BILLING_PERIOD = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_HREF = "AAAAAAAAAA";
    private static final String UPDATED_HREF = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_NEXT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NEXT_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_PAYMENT_DUE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PAYMENT_DUE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TAX_EXCLUDED_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX_EXCLUDED_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_TAX_EXCLUDED_AMOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TAX_INCLUDED_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX_INCLUDED_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_TAX_INCLUDED_AMOUNT = new BigDecimal(1 - 1);

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;
    private static final Long SMALLER_CUSTOMER_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/cust-billing-refs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustBillingRefRepository custBillingRefRepository;

    @Autowired
    private CustBillingRefMapper custBillingRefMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustBillingRefMockMvc;

    private CustBillingRef custBillingRef;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustBillingRef createEntity(EntityManager em) {
        CustBillingRef custBillingRef = new CustBillingRef()
            .amountDue(DEFAULT_AMOUNT_DUE)
            .billDate(DEFAULT_BILL_DATE)
            .billNo(DEFAULT_BILL_NO)
            .billingPeriod(DEFAULT_BILLING_PERIOD)
            .category(DEFAULT_CATEGORY)
            .href(DEFAULT_HREF)
            .lastUpdatedDate(DEFAULT_LAST_UPDATED_DATE)
            .nextUpdatedDate(DEFAULT_NEXT_UPDATED_DATE)
            .paymentDueDate(DEFAULT_PAYMENT_DUE_DATE)
            .state(DEFAULT_STATE)
            .taxExcludedAmount(DEFAULT_TAX_EXCLUDED_AMOUNT)
            .taxIncludedAmount(DEFAULT_TAX_INCLUDED_AMOUNT)
            .customerId(DEFAULT_CUSTOMER_ID);
        return custBillingRef;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustBillingRef createUpdatedEntity(EntityManager em) {
        CustBillingRef custBillingRef = new CustBillingRef()
            .amountDue(UPDATED_AMOUNT_DUE)
            .billDate(UPDATED_BILL_DATE)
            .billNo(UPDATED_BILL_NO)
            .billingPeriod(UPDATED_BILLING_PERIOD)
            .category(UPDATED_CATEGORY)
            .href(UPDATED_HREF)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .nextUpdatedDate(UPDATED_NEXT_UPDATED_DATE)
            .paymentDueDate(UPDATED_PAYMENT_DUE_DATE)
            .state(UPDATED_STATE)
            .taxExcludedAmount(UPDATED_TAX_EXCLUDED_AMOUNT)
            .taxIncludedAmount(UPDATED_TAX_INCLUDED_AMOUNT)
            .customerId(UPDATED_CUSTOMER_ID);
        return custBillingRef;
    }

    @BeforeEach
    public void initTest() {
        custBillingRef = createEntity(em);
    }

    @Test
    @Transactional
    void createCustBillingRef() throws Exception {
        int databaseSizeBeforeCreate = custBillingRefRepository.findAll().size();
        // Create the CustBillingRef
        CustBillingRefDTO custBillingRefDTO = custBillingRefMapper.toDto(custBillingRef);
        restCustBillingRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRefDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CustBillingRef in the database
        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeCreate + 1);
        CustBillingRef testCustBillingRef = custBillingRefList.get(custBillingRefList.size() - 1);
        assertThat(testCustBillingRef.getAmountDue()).isEqualByComparingTo(DEFAULT_AMOUNT_DUE);
        assertThat(testCustBillingRef.getBillDate()).isEqualTo(DEFAULT_BILL_DATE);
        assertThat(testCustBillingRef.getBillNo()).isEqualTo(DEFAULT_BILL_NO);
        assertThat(testCustBillingRef.getBillingPeriod()).isEqualTo(DEFAULT_BILLING_PERIOD);
        assertThat(testCustBillingRef.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testCustBillingRef.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testCustBillingRef.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testCustBillingRef.getNextUpdatedDate()).isEqualTo(DEFAULT_NEXT_UPDATED_DATE);
        assertThat(testCustBillingRef.getPaymentDueDate()).isEqualTo(DEFAULT_PAYMENT_DUE_DATE);
        assertThat(testCustBillingRef.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testCustBillingRef.getTaxExcludedAmount()).isEqualByComparingTo(DEFAULT_TAX_EXCLUDED_AMOUNT);
        assertThat(testCustBillingRef.getTaxIncludedAmount()).isEqualByComparingTo(DEFAULT_TAX_INCLUDED_AMOUNT);
        assertThat(testCustBillingRef.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void createCustBillingRefWithExistingId() throws Exception {
        // Create the CustBillingRef with an existing ID
        custBillingRef.setId(1L);
        CustBillingRefDTO custBillingRefDTO = custBillingRefMapper.toDto(custBillingRef);

        int databaseSizeBeforeCreate = custBillingRefRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustBillingRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustBillingRef in the database
        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAmountDueIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingRefRepository.findAll().size();
        // set the field null
        custBillingRef.setAmountDue(null);

        // Create the CustBillingRef, which fails.
        CustBillingRefDTO custBillingRefDTO = custBillingRefMapper.toDto(custBillingRef);

        restCustBillingRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRefDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBillDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingRefRepository.findAll().size();
        // set the field null
        custBillingRef.setBillDate(null);

        // Create the CustBillingRef, which fails.
        CustBillingRefDTO custBillingRefDTO = custBillingRefMapper.toDto(custBillingRef);

        restCustBillingRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRefDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBillNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingRefRepository.findAll().size();
        // set the field null
        custBillingRef.setBillNo(null);

        // Create the CustBillingRef, which fails.
        CustBillingRefDTO custBillingRefDTO = custBillingRefMapper.toDto(custBillingRef);

        restCustBillingRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRefDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBillingPeriodIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingRefRepository.findAll().size();
        // set the field null
        custBillingRef.setBillingPeriod(null);

        // Create the CustBillingRef, which fails.
        CustBillingRefDTO custBillingRefDTO = custBillingRefMapper.toDto(custBillingRef);

        restCustBillingRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRefDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingRefRepository.findAll().size();
        // set the field null
        custBillingRef.setCategory(null);

        // Create the CustBillingRef, which fails.
        CustBillingRefDTO custBillingRefDTO = custBillingRefMapper.toDto(custBillingRef);

        restCustBillingRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRefDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHrefIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingRefRepository.findAll().size();
        // set the field null
        custBillingRef.setHref(null);

        // Create the CustBillingRef, which fails.
        CustBillingRefDTO custBillingRefDTO = custBillingRefMapper.toDto(custBillingRef);

        restCustBillingRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRefDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastUpdatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingRefRepository.findAll().size();
        // set the field null
        custBillingRef.setLastUpdatedDate(null);

        // Create the CustBillingRef, which fails.
        CustBillingRefDTO custBillingRefDTO = custBillingRefMapper.toDto(custBillingRef);

        restCustBillingRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRefDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNextUpdatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingRefRepository.findAll().size();
        // set the field null
        custBillingRef.setNextUpdatedDate(null);

        // Create the CustBillingRef, which fails.
        CustBillingRefDTO custBillingRefDTO = custBillingRefMapper.toDto(custBillingRef);

        restCustBillingRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRefDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaymentDueDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingRefRepository.findAll().size();
        // set the field null
        custBillingRef.setPaymentDueDate(null);

        // Create the CustBillingRef, which fails.
        CustBillingRefDTO custBillingRefDTO = custBillingRefMapper.toDto(custBillingRef);

        restCustBillingRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRefDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingRefRepository.findAll().size();
        // set the field null
        custBillingRef.setState(null);

        // Create the CustBillingRef, which fails.
        CustBillingRefDTO custBillingRefDTO = custBillingRefMapper.toDto(custBillingRef);

        restCustBillingRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRefDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTaxExcludedAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingRefRepository.findAll().size();
        // set the field null
        custBillingRef.setTaxExcludedAmount(null);

        // Create the CustBillingRef, which fails.
        CustBillingRefDTO custBillingRefDTO = custBillingRefMapper.toDto(custBillingRef);

        restCustBillingRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRefDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTaxIncludedAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingRefRepository.findAll().size();
        // set the field null
        custBillingRef.setTaxIncludedAmount(null);

        // Create the CustBillingRef, which fails.
        CustBillingRefDTO custBillingRefDTO = custBillingRefMapper.toDto(custBillingRef);

        restCustBillingRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRefDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingRefRepository.findAll().size();
        // set the field null
        custBillingRef.setCustomerId(null);

        // Create the CustBillingRef, which fails.
        CustBillingRefDTO custBillingRefDTO = custBillingRefMapper.toDto(custBillingRef);

        restCustBillingRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRefDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustBillingRefs() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList
        restCustBillingRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custBillingRef.getId().intValue())))
            .andExpect(jsonPath("$.[*].amountDue").value(hasItem(sameNumber(DEFAULT_AMOUNT_DUE))))
            .andExpect(jsonPath("$.[*].billDate").value(hasItem(DEFAULT_BILL_DATE.toString())))
            .andExpect(jsonPath("$.[*].billNo").value(hasItem(DEFAULT_BILL_NO.intValue())))
            .andExpect(jsonPath("$.[*].billingPeriod").value(hasItem(DEFAULT_BILLING_PERIOD.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(DEFAULT_LAST_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].nextUpdatedDate").value(hasItem(DEFAULT_NEXT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].paymentDueDate").value(hasItem(DEFAULT_PAYMENT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].taxExcludedAmount").value(hasItem(sameNumber(DEFAULT_TAX_EXCLUDED_AMOUNT))))
            .andExpect(jsonPath("$.[*].taxIncludedAmount").value(hasItem(sameNumber(DEFAULT_TAX_INCLUDED_AMOUNT))))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())));
    }

    @Test
    @Transactional
    void getCustBillingRef() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get the custBillingRef
        restCustBillingRefMockMvc
            .perform(get(ENTITY_API_URL_ID, custBillingRef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custBillingRef.getId().intValue()))
            .andExpect(jsonPath("$.amountDue").value(sameNumber(DEFAULT_AMOUNT_DUE)))
            .andExpect(jsonPath("$.billDate").value(DEFAULT_BILL_DATE.toString()))
            .andExpect(jsonPath("$.billNo").value(DEFAULT_BILL_NO.intValue()))
            .andExpect(jsonPath("$.billingPeriod").value(DEFAULT_BILLING_PERIOD.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.href").value(DEFAULT_HREF))
            .andExpect(jsonPath("$.lastUpdatedDate").value(DEFAULT_LAST_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.nextUpdatedDate").value(DEFAULT_NEXT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.paymentDueDate").value(DEFAULT_PAYMENT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.taxExcludedAmount").value(sameNumber(DEFAULT_TAX_EXCLUDED_AMOUNT)))
            .andExpect(jsonPath("$.taxIncludedAmount").value(sameNumber(DEFAULT_TAX_INCLUDED_AMOUNT)))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()));
    }

    @Test
    @Transactional
    void getCustBillingRefsByIdFiltering() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        Long id = custBillingRef.getId();

        defaultCustBillingRefShouldBeFound("id.equals=" + id);
        defaultCustBillingRefShouldNotBeFound("id.notEquals=" + id);

        defaultCustBillingRefShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustBillingRefShouldNotBeFound("id.greaterThan=" + id);

        defaultCustBillingRefShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustBillingRefShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByAmountDueIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where amountDue equals to DEFAULT_AMOUNT_DUE
        defaultCustBillingRefShouldBeFound("amountDue.equals=" + DEFAULT_AMOUNT_DUE);

        // Get all the custBillingRefList where amountDue equals to UPDATED_AMOUNT_DUE
        defaultCustBillingRefShouldNotBeFound("amountDue.equals=" + UPDATED_AMOUNT_DUE);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByAmountDueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where amountDue not equals to DEFAULT_AMOUNT_DUE
        defaultCustBillingRefShouldNotBeFound("amountDue.notEquals=" + DEFAULT_AMOUNT_DUE);

        // Get all the custBillingRefList where amountDue not equals to UPDATED_AMOUNT_DUE
        defaultCustBillingRefShouldBeFound("amountDue.notEquals=" + UPDATED_AMOUNT_DUE);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByAmountDueIsInShouldWork() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where amountDue in DEFAULT_AMOUNT_DUE or UPDATED_AMOUNT_DUE
        defaultCustBillingRefShouldBeFound("amountDue.in=" + DEFAULT_AMOUNT_DUE + "," + UPDATED_AMOUNT_DUE);

        // Get all the custBillingRefList where amountDue equals to UPDATED_AMOUNT_DUE
        defaultCustBillingRefShouldNotBeFound("amountDue.in=" + UPDATED_AMOUNT_DUE);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByAmountDueIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where amountDue is not null
        defaultCustBillingRefShouldBeFound("amountDue.specified=true");

        // Get all the custBillingRefList where amountDue is null
        defaultCustBillingRefShouldNotBeFound("amountDue.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByAmountDueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where amountDue is greater than or equal to DEFAULT_AMOUNT_DUE
        defaultCustBillingRefShouldBeFound("amountDue.greaterThanOrEqual=" + DEFAULT_AMOUNT_DUE);

        // Get all the custBillingRefList where amountDue is greater than or equal to UPDATED_AMOUNT_DUE
        defaultCustBillingRefShouldNotBeFound("amountDue.greaterThanOrEqual=" + UPDATED_AMOUNT_DUE);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByAmountDueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where amountDue is less than or equal to DEFAULT_AMOUNT_DUE
        defaultCustBillingRefShouldBeFound("amountDue.lessThanOrEqual=" + DEFAULT_AMOUNT_DUE);

        // Get all the custBillingRefList where amountDue is less than or equal to SMALLER_AMOUNT_DUE
        defaultCustBillingRefShouldNotBeFound("amountDue.lessThanOrEqual=" + SMALLER_AMOUNT_DUE);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByAmountDueIsLessThanSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where amountDue is less than DEFAULT_AMOUNT_DUE
        defaultCustBillingRefShouldNotBeFound("amountDue.lessThan=" + DEFAULT_AMOUNT_DUE);

        // Get all the custBillingRefList where amountDue is less than UPDATED_AMOUNT_DUE
        defaultCustBillingRefShouldBeFound("amountDue.lessThan=" + UPDATED_AMOUNT_DUE);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByAmountDueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where amountDue is greater than DEFAULT_AMOUNT_DUE
        defaultCustBillingRefShouldNotBeFound("amountDue.greaterThan=" + DEFAULT_AMOUNT_DUE);

        // Get all the custBillingRefList where amountDue is greater than SMALLER_AMOUNT_DUE
        defaultCustBillingRefShouldBeFound("amountDue.greaterThan=" + SMALLER_AMOUNT_DUE);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByBillDateIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where billDate equals to DEFAULT_BILL_DATE
        defaultCustBillingRefShouldBeFound("billDate.equals=" + DEFAULT_BILL_DATE);

        // Get all the custBillingRefList where billDate equals to UPDATED_BILL_DATE
        defaultCustBillingRefShouldNotBeFound("billDate.equals=" + UPDATED_BILL_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByBillDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where billDate not equals to DEFAULT_BILL_DATE
        defaultCustBillingRefShouldNotBeFound("billDate.notEquals=" + DEFAULT_BILL_DATE);

        // Get all the custBillingRefList where billDate not equals to UPDATED_BILL_DATE
        defaultCustBillingRefShouldBeFound("billDate.notEquals=" + UPDATED_BILL_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByBillDateIsInShouldWork() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where billDate in DEFAULT_BILL_DATE or UPDATED_BILL_DATE
        defaultCustBillingRefShouldBeFound("billDate.in=" + DEFAULT_BILL_DATE + "," + UPDATED_BILL_DATE);

        // Get all the custBillingRefList where billDate equals to UPDATED_BILL_DATE
        defaultCustBillingRefShouldNotBeFound("billDate.in=" + UPDATED_BILL_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByBillDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where billDate is not null
        defaultCustBillingRefShouldBeFound("billDate.specified=true");

        // Get all the custBillingRefList where billDate is null
        defaultCustBillingRefShouldNotBeFound("billDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByBillNoIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where billNo equals to DEFAULT_BILL_NO
        defaultCustBillingRefShouldBeFound("billNo.equals=" + DEFAULT_BILL_NO);

        // Get all the custBillingRefList where billNo equals to UPDATED_BILL_NO
        defaultCustBillingRefShouldNotBeFound("billNo.equals=" + UPDATED_BILL_NO);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByBillNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where billNo not equals to DEFAULT_BILL_NO
        defaultCustBillingRefShouldNotBeFound("billNo.notEquals=" + DEFAULT_BILL_NO);

        // Get all the custBillingRefList where billNo not equals to UPDATED_BILL_NO
        defaultCustBillingRefShouldBeFound("billNo.notEquals=" + UPDATED_BILL_NO);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByBillNoIsInShouldWork() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where billNo in DEFAULT_BILL_NO or UPDATED_BILL_NO
        defaultCustBillingRefShouldBeFound("billNo.in=" + DEFAULT_BILL_NO + "," + UPDATED_BILL_NO);

        // Get all the custBillingRefList where billNo equals to UPDATED_BILL_NO
        defaultCustBillingRefShouldNotBeFound("billNo.in=" + UPDATED_BILL_NO);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByBillNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where billNo is not null
        defaultCustBillingRefShouldBeFound("billNo.specified=true");

        // Get all the custBillingRefList where billNo is null
        defaultCustBillingRefShouldNotBeFound("billNo.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByBillNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where billNo is greater than or equal to DEFAULT_BILL_NO
        defaultCustBillingRefShouldBeFound("billNo.greaterThanOrEqual=" + DEFAULT_BILL_NO);

        // Get all the custBillingRefList where billNo is greater than or equal to UPDATED_BILL_NO
        defaultCustBillingRefShouldNotBeFound("billNo.greaterThanOrEqual=" + UPDATED_BILL_NO);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByBillNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where billNo is less than or equal to DEFAULT_BILL_NO
        defaultCustBillingRefShouldBeFound("billNo.lessThanOrEqual=" + DEFAULT_BILL_NO);

        // Get all the custBillingRefList where billNo is less than or equal to SMALLER_BILL_NO
        defaultCustBillingRefShouldNotBeFound("billNo.lessThanOrEqual=" + SMALLER_BILL_NO);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByBillNoIsLessThanSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where billNo is less than DEFAULT_BILL_NO
        defaultCustBillingRefShouldNotBeFound("billNo.lessThan=" + DEFAULT_BILL_NO);

        // Get all the custBillingRefList where billNo is less than UPDATED_BILL_NO
        defaultCustBillingRefShouldBeFound("billNo.lessThan=" + UPDATED_BILL_NO);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByBillNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where billNo is greater than DEFAULT_BILL_NO
        defaultCustBillingRefShouldNotBeFound("billNo.greaterThan=" + DEFAULT_BILL_NO);

        // Get all the custBillingRefList where billNo is greater than SMALLER_BILL_NO
        defaultCustBillingRefShouldBeFound("billNo.greaterThan=" + SMALLER_BILL_NO);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByBillingPeriodIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where billingPeriod equals to DEFAULT_BILLING_PERIOD
        defaultCustBillingRefShouldBeFound("billingPeriod.equals=" + DEFAULT_BILLING_PERIOD);

        // Get all the custBillingRefList where billingPeriod equals to UPDATED_BILLING_PERIOD
        defaultCustBillingRefShouldNotBeFound("billingPeriod.equals=" + UPDATED_BILLING_PERIOD);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByBillingPeriodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where billingPeriod not equals to DEFAULT_BILLING_PERIOD
        defaultCustBillingRefShouldNotBeFound("billingPeriod.notEquals=" + DEFAULT_BILLING_PERIOD);

        // Get all the custBillingRefList where billingPeriod not equals to UPDATED_BILLING_PERIOD
        defaultCustBillingRefShouldBeFound("billingPeriod.notEquals=" + UPDATED_BILLING_PERIOD);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByBillingPeriodIsInShouldWork() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where billingPeriod in DEFAULT_BILLING_PERIOD or UPDATED_BILLING_PERIOD
        defaultCustBillingRefShouldBeFound("billingPeriod.in=" + DEFAULT_BILLING_PERIOD + "," + UPDATED_BILLING_PERIOD);

        // Get all the custBillingRefList where billingPeriod equals to UPDATED_BILLING_PERIOD
        defaultCustBillingRefShouldNotBeFound("billingPeriod.in=" + UPDATED_BILLING_PERIOD);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByBillingPeriodIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where billingPeriod is not null
        defaultCustBillingRefShouldBeFound("billingPeriod.specified=true");

        // Get all the custBillingRefList where billingPeriod is null
        defaultCustBillingRefShouldNotBeFound("billingPeriod.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where category equals to DEFAULT_CATEGORY
        defaultCustBillingRefShouldBeFound("category.equals=" + DEFAULT_CATEGORY);

        // Get all the custBillingRefList where category equals to UPDATED_CATEGORY
        defaultCustBillingRefShouldNotBeFound("category.equals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByCategoryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where category not equals to DEFAULT_CATEGORY
        defaultCustBillingRefShouldNotBeFound("category.notEquals=" + DEFAULT_CATEGORY);

        // Get all the custBillingRefList where category not equals to UPDATED_CATEGORY
        defaultCustBillingRefShouldBeFound("category.notEquals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where category in DEFAULT_CATEGORY or UPDATED_CATEGORY
        defaultCustBillingRefShouldBeFound("category.in=" + DEFAULT_CATEGORY + "," + UPDATED_CATEGORY);

        // Get all the custBillingRefList where category equals to UPDATED_CATEGORY
        defaultCustBillingRefShouldNotBeFound("category.in=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where category is not null
        defaultCustBillingRefShouldBeFound("category.specified=true");

        // Get all the custBillingRefList where category is null
        defaultCustBillingRefShouldNotBeFound("category.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByCategoryContainsSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where category contains DEFAULT_CATEGORY
        defaultCustBillingRefShouldBeFound("category.contains=" + DEFAULT_CATEGORY);

        // Get all the custBillingRefList where category contains UPDATED_CATEGORY
        defaultCustBillingRefShouldNotBeFound("category.contains=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByCategoryNotContainsSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where category does not contain DEFAULT_CATEGORY
        defaultCustBillingRefShouldNotBeFound("category.doesNotContain=" + DEFAULT_CATEGORY);

        // Get all the custBillingRefList where category does not contain UPDATED_CATEGORY
        defaultCustBillingRefShouldBeFound("category.doesNotContain=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByHrefIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where href equals to DEFAULT_HREF
        defaultCustBillingRefShouldBeFound("href.equals=" + DEFAULT_HREF);

        // Get all the custBillingRefList where href equals to UPDATED_HREF
        defaultCustBillingRefShouldNotBeFound("href.equals=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByHrefIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where href not equals to DEFAULT_HREF
        defaultCustBillingRefShouldNotBeFound("href.notEquals=" + DEFAULT_HREF);

        // Get all the custBillingRefList where href not equals to UPDATED_HREF
        defaultCustBillingRefShouldBeFound("href.notEquals=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByHrefIsInShouldWork() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where href in DEFAULT_HREF or UPDATED_HREF
        defaultCustBillingRefShouldBeFound("href.in=" + DEFAULT_HREF + "," + UPDATED_HREF);

        // Get all the custBillingRefList where href equals to UPDATED_HREF
        defaultCustBillingRefShouldNotBeFound("href.in=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByHrefIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where href is not null
        defaultCustBillingRefShouldBeFound("href.specified=true");

        // Get all the custBillingRefList where href is null
        defaultCustBillingRefShouldNotBeFound("href.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByHrefContainsSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where href contains DEFAULT_HREF
        defaultCustBillingRefShouldBeFound("href.contains=" + DEFAULT_HREF);

        // Get all the custBillingRefList where href contains UPDATED_HREF
        defaultCustBillingRefShouldNotBeFound("href.contains=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByHrefNotContainsSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where href does not contain DEFAULT_HREF
        defaultCustBillingRefShouldNotBeFound("href.doesNotContain=" + DEFAULT_HREF);

        // Get all the custBillingRefList where href does not contain UPDATED_HREF
        defaultCustBillingRefShouldBeFound("href.doesNotContain=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByLastUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where lastUpdatedDate equals to DEFAULT_LAST_UPDATED_DATE
        defaultCustBillingRefShouldBeFound("lastUpdatedDate.equals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the custBillingRefList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultCustBillingRefShouldNotBeFound("lastUpdatedDate.equals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByLastUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where lastUpdatedDate not equals to DEFAULT_LAST_UPDATED_DATE
        defaultCustBillingRefShouldNotBeFound("lastUpdatedDate.notEquals=" + DEFAULT_LAST_UPDATED_DATE);

        // Get all the custBillingRefList where lastUpdatedDate not equals to UPDATED_LAST_UPDATED_DATE
        defaultCustBillingRefShouldBeFound("lastUpdatedDate.notEquals=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByLastUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where lastUpdatedDate in DEFAULT_LAST_UPDATED_DATE or UPDATED_LAST_UPDATED_DATE
        defaultCustBillingRefShouldBeFound("lastUpdatedDate.in=" + DEFAULT_LAST_UPDATED_DATE + "," + UPDATED_LAST_UPDATED_DATE);

        // Get all the custBillingRefList where lastUpdatedDate equals to UPDATED_LAST_UPDATED_DATE
        defaultCustBillingRefShouldNotBeFound("lastUpdatedDate.in=" + UPDATED_LAST_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByLastUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where lastUpdatedDate is not null
        defaultCustBillingRefShouldBeFound("lastUpdatedDate.specified=true");

        // Get all the custBillingRefList where lastUpdatedDate is null
        defaultCustBillingRefShouldNotBeFound("lastUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByNextUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where nextUpdatedDate equals to DEFAULT_NEXT_UPDATED_DATE
        defaultCustBillingRefShouldBeFound("nextUpdatedDate.equals=" + DEFAULT_NEXT_UPDATED_DATE);

        // Get all the custBillingRefList where nextUpdatedDate equals to UPDATED_NEXT_UPDATED_DATE
        defaultCustBillingRefShouldNotBeFound("nextUpdatedDate.equals=" + UPDATED_NEXT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByNextUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where nextUpdatedDate not equals to DEFAULT_NEXT_UPDATED_DATE
        defaultCustBillingRefShouldNotBeFound("nextUpdatedDate.notEquals=" + DEFAULT_NEXT_UPDATED_DATE);

        // Get all the custBillingRefList where nextUpdatedDate not equals to UPDATED_NEXT_UPDATED_DATE
        defaultCustBillingRefShouldBeFound("nextUpdatedDate.notEquals=" + UPDATED_NEXT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByNextUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where nextUpdatedDate in DEFAULT_NEXT_UPDATED_DATE or UPDATED_NEXT_UPDATED_DATE
        defaultCustBillingRefShouldBeFound("nextUpdatedDate.in=" + DEFAULT_NEXT_UPDATED_DATE + "," + UPDATED_NEXT_UPDATED_DATE);

        // Get all the custBillingRefList where nextUpdatedDate equals to UPDATED_NEXT_UPDATED_DATE
        defaultCustBillingRefShouldNotBeFound("nextUpdatedDate.in=" + UPDATED_NEXT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByNextUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where nextUpdatedDate is not null
        defaultCustBillingRefShouldBeFound("nextUpdatedDate.specified=true");

        // Get all the custBillingRefList where nextUpdatedDate is null
        defaultCustBillingRefShouldNotBeFound("nextUpdatedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByPaymentDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where paymentDueDate equals to DEFAULT_PAYMENT_DUE_DATE
        defaultCustBillingRefShouldBeFound("paymentDueDate.equals=" + DEFAULT_PAYMENT_DUE_DATE);

        // Get all the custBillingRefList where paymentDueDate equals to UPDATED_PAYMENT_DUE_DATE
        defaultCustBillingRefShouldNotBeFound("paymentDueDate.equals=" + UPDATED_PAYMENT_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByPaymentDueDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where paymentDueDate not equals to DEFAULT_PAYMENT_DUE_DATE
        defaultCustBillingRefShouldNotBeFound("paymentDueDate.notEquals=" + DEFAULT_PAYMENT_DUE_DATE);

        // Get all the custBillingRefList where paymentDueDate not equals to UPDATED_PAYMENT_DUE_DATE
        defaultCustBillingRefShouldBeFound("paymentDueDate.notEquals=" + UPDATED_PAYMENT_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByPaymentDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where paymentDueDate in DEFAULT_PAYMENT_DUE_DATE or UPDATED_PAYMENT_DUE_DATE
        defaultCustBillingRefShouldBeFound("paymentDueDate.in=" + DEFAULT_PAYMENT_DUE_DATE + "," + UPDATED_PAYMENT_DUE_DATE);

        // Get all the custBillingRefList where paymentDueDate equals to UPDATED_PAYMENT_DUE_DATE
        defaultCustBillingRefShouldNotBeFound("paymentDueDate.in=" + UPDATED_PAYMENT_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByPaymentDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where paymentDueDate is not null
        defaultCustBillingRefShouldBeFound("paymentDueDate.specified=true");

        // Get all the custBillingRefList where paymentDueDate is null
        defaultCustBillingRefShouldNotBeFound("paymentDueDate.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where state equals to DEFAULT_STATE
        defaultCustBillingRefShouldBeFound("state.equals=" + DEFAULT_STATE);

        // Get all the custBillingRefList where state equals to UPDATED_STATE
        defaultCustBillingRefShouldNotBeFound("state.equals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByStateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where state not equals to DEFAULT_STATE
        defaultCustBillingRefShouldNotBeFound("state.notEquals=" + DEFAULT_STATE);

        // Get all the custBillingRefList where state not equals to UPDATED_STATE
        defaultCustBillingRefShouldBeFound("state.notEquals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByStateIsInShouldWork() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where state in DEFAULT_STATE or UPDATED_STATE
        defaultCustBillingRefShouldBeFound("state.in=" + DEFAULT_STATE + "," + UPDATED_STATE);

        // Get all the custBillingRefList where state equals to UPDATED_STATE
        defaultCustBillingRefShouldNotBeFound("state.in=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where state is not null
        defaultCustBillingRefShouldBeFound("state.specified=true");

        // Get all the custBillingRefList where state is null
        defaultCustBillingRefShouldNotBeFound("state.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByStateContainsSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where state contains DEFAULT_STATE
        defaultCustBillingRefShouldBeFound("state.contains=" + DEFAULT_STATE);

        // Get all the custBillingRefList where state contains UPDATED_STATE
        defaultCustBillingRefShouldNotBeFound("state.contains=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByStateNotContainsSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where state does not contain DEFAULT_STATE
        defaultCustBillingRefShouldNotBeFound("state.doesNotContain=" + DEFAULT_STATE);

        // Get all the custBillingRefList where state does not contain UPDATED_STATE
        defaultCustBillingRefShouldBeFound("state.doesNotContain=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByTaxExcludedAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where taxExcludedAmount equals to DEFAULT_TAX_EXCLUDED_AMOUNT
        defaultCustBillingRefShouldBeFound("taxExcludedAmount.equals=" + DEFAULT_TAX_EXCLUDED_AMOUNT);

        // Get all the custBillingRefList where taxExcludedAmount equals to UPDATED_TAX_EXCLUDED_AMOUNT
        defaultCustBillingRefShouldNotBeFound("taxExcludedAmount.equals=" + UPDATED_TAX_EXCLUDED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByTaxExcludedAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where taxExcludedAmount not equals to DEFAULT_TAX_EXCLUDED_AMOUNT
        defaultCustBillingRefShouldNotBeFound("taxExcludedAmount.notEquals=" + DEFAULT_TAX_EXCLUDED_AMOUNT);

        // Get all the custBillingRefList where taxExcludedAmount not equals to UPDATED_TAX_EXCLUDED_AMOUNT
        defaultCustBillingRefShouldBeFound("taxExcludedAmount.notEquals=" + UPDATED_TAX_EXCLUDED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByTaxExcludedAmountIsInShouldWork() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where taxExcludedAmount in DEFAULT_TAX_EXCLUDED_AMOUNT or UPDATED_TAX_EXCLUDED_AMOUNT
        defaultCustBillingRefShouldBeFound("taxExcludedAmount.in=" + DEFAULT_TAX_EXCLUDED_AMOUNT + "," + UPDATED_TAX_EXCLUDED_AMOUNT);

        // Get all the custBillingRefList where taxExcludedAmount equals to UPDATED_TAX_EXCLUDED_AMOUNT
        defaultCustBillingRefShouldNotBeFound("taxExcludedAmount.in=" + UPDATED_TAX_EXCLUDED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByTaxExcludedAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where taxExcludedAmount is not null
        defaultCustBillingRefShouldBeFound("taxExcludedAmount.specified=true");

        // Get all the custBillingRefList where taxExcludedAmount is null
        defaultCustBillingRefShouldNotBeFound("taxExcludedAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByTaxExcludedAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where taxExcludedAmount is greater than or equal to DEFAULT_TAX_EXCLUDED_AMOUNT
        defaultCustBillingRefShouldBeFound("taxExcludedAmount.greaterThanOrEqual=" + DEFAULT_TAX_EXCLUDED_AMOUNT);

        // Get all the custBillingRefList where taxExcludedAmount is greater than or equal to UPDATED_TAX_EXCLUDED_AMOUNT
        defaultCustBillingRefShouldNotBeFound("taxExcludedAmount.greaterThanOrEqual=" + UPDATED_TAX_EXCLUDED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByTaxExcludedAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where taxExcludedAmount is less than or equal to DEFAULT_TAX_EXCLUDED_AMOUNT
        defaultCustBillingRefShouldBeFound("taxExcludedAmount.lessThanOrEqual=" + DEFAULT_TAX_EXCLUDED_AMOUNT);

        // Get all the custBillingRefList where taxExcludedAmount is less than or equal to SMALLER_TAX_EXCLUDED_AMOUNT
        defaultCustBillingRefShouldNotBeFound("taxExcludedAmount.lessThanOrEqual=" + SMALLER_TAX_EXCLUDED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByTaxExcludedAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where taxExcludedAmount is less than DEFAULT_TAX_EXCLUDED_AMOUNT
        defaultCustBillingRefShouldNotBeFound("taxExcludedAmount.lessThan=" + DEFAULT_TAX_EXCLUDED_AMOUNT);

        // Get all the custBillingRefList where taxExcludedAmount is less than UPDATED_TAX_EXCLUDED_AMOUNT
        defaultCustBillingRefShouldBeFound("taxExcludedAmount.lessThan=" + UPDATED_TAX_EXCLUDED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByTaxExcludedAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where taxExcludedAmount is greater than DEFAULT_TAX_EXCLUDED_AMOUNT
        defaultCustBillingRefShouldNotBeFound("taxExcludedAmount.greaterThan=" + DEFAULT_TAX_EXCLUDED_AMOUNT);

        // Get all the custBillingRefList where taxExcludedAmount is greater than SMALLER_TAX_EXCLUDED_AMOUNT
        defaultCustBillingRefShouldBeFound("taxExcludedAmount.greaterThan=" + SMALLER_TAX_EXCLUDED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByTaxIncludedAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where taxIncludedAmount equals to DEFAULT_TAX_INCLUDED_AMOUNT
        defaultCustBillingRefShouldBeFound("taxIncludedAmount.equals=" + DEFAULT_TAX_INCLUDED_AMOUNT);

        // Get all the custBillingRefList where taxIncludedAmount equals to UPDATED_TAX_INCLUDED_AMOUNT
        defaultCustBillingRefShouldNotBeFound("taxIncludedAmount.equals=" + UPDATED_TAX_INCLUDED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByTaxIncludedAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where taxIncludedAmount not equals to DEFAULT_TAX_INCLUDED_AMOUNT
        defaultCustBillingRefShouldNotBeFound("taxIncludedAmount.notEquals=" + DEFAULT_TAX_INCLUDED_AMOUNT);

        // Get all the custBillingRefList where taxIncludedAmount not equals to UPDATED_TAX_INCLUDED_AMOUNT
        defaultCustBillingRefShouldBeFound("taxIncludedAmount.notEquals=" + UPDATED_TAX_INCLUDED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByTaxIncludedAmountIsInShouldWork() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where taxIncludedAmount in DEFAULT_TAX_INCLUDED_AMOUNT or UPDATED_TAX_INCLUDED_AMOUNT
        defaultCustBillingRefShouldBeFound("taxIncludedAmount.in=" + DEFAULT_TAX_INCLUDED_AMOUNT + "," + UPDATED_TAX_INCLUDED_AMOUNT);

        // Get all the custBillingRefList where taxIncludedAmount equals to UPDATED_TAX_INCLUDED_AMOUNT
        defaultCustBillingRefShouldNotBeFound("taxIncludedAmount.in=" + UPDATED_TAX_INCLUDED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByTaxIncludedAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where taxIncludedAmount is not null
        defaultCustBillingRefShouldBeFound("taxIncludedAmount.specified=true");

        // Get all the custBillingRefList where taxIncludedAmount is null
        defaultCustBillingRefShouldNotBeFound("taxIncludedAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByTaxIncludedAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where taxIncludedAmount is greater than or equal to DEFAULT_TAX_INCLUDED_AMOUNT
        defaultCustBillingRefShouldBeFound("taxIncludedAmount.greaterThanOrEqual=" + DEFAULT_TAX_INCLUDED_AMOUNT);

        // Get all the custBillingRefList where taxIncludedAmount is greater than or equal to UPDATED_TAX_INCLUDED_AMOUNT
        defaultCustBillingRefShouldNotBeFound("taxIncludedAmount.greaterThanOrEqual=" + UPDATED_TAX_INCLUDED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByTaxIncludedAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where taxIncludedAmount is less than or equal to DEFAULT_TAX_INCLUDED_AMOUNT
        defaultCustBillingRefShouldBeFound("taxIncludedAmount.lessThanOrEqual=" + DEFAULT_TAX_INCLUDED_AMOUNT);

        // Get all the custBillingRefList where taxIncludedAmount is less than or equal to SMALLER_TAX_INCLUDED_AMOUNT
        defaultCustBillingRefShouldNotBeFound("taxIncludedAmount.lessThanOrEqual=" + SMALLER_TAX_INCLUDED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByTaxIncludedAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where taxIncludedAmount is less than DEFAULT_TAX_INCLUDED_AMOUNT
        defaultCustBillingRefShouldNotBeFound("taxIncludedAmount.lessThan=" + DEFAULT_TAX_INCLUDED_AMOUNT);

        // Get all the custBillingRefList where taxIncludedAmount is less than UPDATED_TAX_INCLUDED_AMOUNT
        defaultCustBillingRefShouldBeFound("taxIncludedAmount.lessThan=" + UPDATED_TAX_INCLUDED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByTaxIncludedAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where taxIncludedAmount is greater than DEFAULT_TAX_INCLUDED_AMOUNT
        defaultCustBillingRefShouldNotBeFound("taxIncludedAmount.greaterThan=" + DEFAULT_TAX_INCLUDED_AMOUNT);

        // Get all the custBillingRefList where taxIncludedAmount is greater than SMALLER_TAX_INCLUDED_AMOUNT
        defaultCustBillingRefShouldBeFound("taxIncludedAmount.greaterThan=" + SMALLER_TAX_INCLUDED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByCustomerIdIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where customerId equals to DEFAULT_CUSTOMER_ID
        defaultCustBillingRefShouldBeFound("customerId.equals=" + DEFAULT_CUSTOMER_ID);

        // Get all the custBillingRefList where customerId equals to UPDATED_CUSTOMER_ID
        defaultCustBillingRefShouldNotBeFound("customerId.equals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByCustomerIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where customerId not equals to DEFAULT_CUSTOMER_ID
        defaultCustBillingRefShouldNotBeFound("customerId.notEquals=" + DEFAULT_CUSTOMER_ID);

        // Get all the custBillingRefList where customerId not equals to UPDATED_CUSTOMER_ID
        defaultCustBillingRefShouldBeFound("customerId.notEquals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByCustomerIdIsInShouldWork() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where customerId in DEFAULT_CUSTOMER_ID or UPDATED_CUSTOMER_ID
        defaultCustBillingRefShouldBeFound("customerId.in=" + DEFAULT_CUSTOMER_ID + "," + UPDATED_CUSTOMER_ID);

        // Get all the custBillingRefList where customerId equals to UPDATED_CUSTOMER_ID
        defaultCustBillingRefShouldNotBeFound("customerId.in=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByCustomerIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where customerId is not null
        defaultCustBillingRefShouldBeFound("customerId.specified=true");

        // Get all the custBillingRefList where customerId is null
        defaultCustBillingRefShouldNotBeFound("customerId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByCustomerIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where customerId is greater than or equal to DEFAULT_CUSTOMER_ID
        defaultCustBillingRefShouldBeFound("customerId.greaterThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the custBillingRefList where customerId is greater than or equal to UPDATED_CUSTOMER_ID
        defaultCustBillingRefShouldNotBeFound("customerId.greaterThanOrEqual=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByCustomerIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where customerId is less than or equal to DEFAULT_CUSTOMER_ID
        defaultCustBillingRefShouldBeFound("customerId.lessThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the custBillingRefList where customerId is less than or equal to SMALLER_CUSTOMER_ID
        defaultCustBillingRefShouldNotBeFound("customerId.lessThanOrEqual=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByCustomerIdIsLessThanSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where customerId is less than DEFAULT_CUSTOMER_ID
        defaultCustBillingRefShouldNotBeFound("customerId.lessThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the custBillingRefList where customerId is less than UPDATED_CUSTOMER_ID
        defaultCustBillingRefShouldBeFound("customerId.lessThan=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByCustomerIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        // Get all the custBillingRefList where customerId is greater than DEFAULT_CUSTOMER_ID
        defaultCustBillingRefShouldNotBeFound("customerId.greaterThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the custBillingRefList where customerId is greater than SMALLER_CUSTOMER_ID
        defaultCustBillingRefShouldBeFound("customerId.greaterThan=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByCustPaymentMethodIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);
        CustPaymentMethod custPaymentMethod = CustPaymentMethodResourceIT.createEntity(em);
        em.persist(custPaymentMethod);
        em.flush();
        custBillingRef.addCustPaymentMethod(custPaymentMethod);
        custBillingRefRepository.saveAndFlush(custBillingRef);
        Long custPaymentMethodId = custPaymentMethod.getId();

        // Get all the custBillingRefList where custPaymentMethod equals to custPaymentMethodId
        defaultCustBillingRefShouldBeFound("custPaymentMethodId.equals=" + custPaymentMethodId);

        // Get all the custBillingRefList where custPaymentMethod equals to (custPaymentMethodId + 1)
        defaultCustBillingRefShouldNotBeFound("custPaymentMethodId.equals=" + (custPaymentMethodId + 1));
    }

    @Test
    @Transactional
    void getAllCustBillingRefsByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);
        Customer customer = CustomerResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        custBillingRef.setCustomer(customer);
        customer.setCustBillingRef(custBillingRef);
        custBillingRefRepository.saveAndFlush(custBillingRef);
        Long customerId = customer.getId();

        // Get all the custBillingRefList where customer equals to customerId
        defaultCustBillingRefShouldBeFound("customerId.equals=" + customerId);

        // Get all the custBillingRefList where customer equals to (customerId + 1)
        defaultCustBillingRefShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustBillingRefShouldBeFound(String filter) throws Exception {
        restCustBillingRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custBillingRef.getId().intValue())))
            .andExpect(jsonPath("$.[*].amountDue").value(hasItem(sameNumber(DEFAULT_AMOUNT_DUE))))
            .andExpect(jsonPath("$.[*].billDate").value(hasItem(DEFAULT_BILL_DATE.toString())))
            .andExpect(jsonPath("$.[*].billNo").value(hasItem(DEFAULT_BILL_NO.intValue())))
            .andExpect(jsonPath("$.[*].billingPeriod").value(hasItem(DEFAULT_BILLING_PERIOD.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].lastUpdatedDate").value(hasItem(DEFAULT_LAST_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].nextUpdatedDate").value(hasItem(DEFAULT_NEXT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].paymentDueDate").value(hasItem(DEFAULT_PAYMENT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].taxExcludedAmount").value(hasItem(sameNumber(DEFAULT_TAX_EXCLUDED_AMOUNT))))
            .andExpect(jsonPath("$.[*].taxIncludedAmount").value(hasItem(sameNumber(DEFAULT_TAX_INCLUDED_AMOUNT))))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())));

        // Check, that the count call also returns 1
        restCustBillingRefMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustBillingRefShouldNotBeFound(String filter) throws Exception {
        restCustBillingRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustBillingRefMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustBillingRef() throws Exception {
        // Get the custBillingRef
        restCustBillingRefMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustBillingRef() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        int databaseSizeBeforeUpdate = custBillingRefRepository.findAll().size();

        // Update the custBillingRef
        CustBillingRef updatedCustBillingRef = custBillingRefRepository.findById(custBillingRef.getId()).get();
        // Disconnect from session so that the updates on updatedCustBillingRef are not directly saved in db
        em.detach(updatedCustBillingRef);
        updatedCustBillingRef
            .amountDue(UPDATED_AMOUNT_DUE)
            .billDate(UPDATED_BILL_DATE)
            .billNo(UPDATED_BILL_NO)
            .billingPeriod(UPDATED_BILLING_PERIOD)
            .category(UPDATED_CATEGORY)
            .href(UPDATED_HREF)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .nextUpdatedDate(UPDATED_NEXT_UPDATED_DATE)
            .paymentDueDate(UPDATED_PAYMENT_DUE_DATE)
            .state(UPDATED_STATE)
            .taxExcludedAmount(UPDATED_TAX_EXCLUDED_AMOUNT)
            .taxIncludedAmount(UPDATED_TAX_INCLUDED_AMOUNT)
            .customerId(UPDATED_CUSTOMER_ID);
        CustBillingRefDTO custBillingRefDTO = custBillingRefMapper.toDto(updatedCustBillingRef);

        restCustBillingRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custBillingRefDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custBillingRefDTO))
            )
            .andExpect(status().isOk());

        // Validate the CustBillingRef in the database
        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeUpdate);
        CustBillingRef testCustBillingRef = custBillingRefList.get(custBillingRefList.size() - 1);
        assertThat(testCustBillingRef.getAmountDue()).isEqualTo(UPDATED_AMOUNT_DUE);
        assertThat(testCustBillingRef.getBillDate()).isEqualTo(UPDATED_BILL_DATE);
        assertThat(testCustBillingRef.getBillNo()).isEqualTo(UPDATED_BILL_NO);
        assertThat(testCustBillingRef.getBillingPeriod()).isEqualTo(UPDATED_BILLING_PERIOD);
        assertThat(testCustBillingRef.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testCustBillingRef.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testCustBillingRef.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testCustBillingRef.getNextUpdatedDate()).isEqualTo(UPDATED_NEXT_UPDATED_DATE);
        assertThat(testCustBillingRef.getPaymentDueDate()).isEqualTo(UPDATED_PAYMENT_DUE_DATE);
        assertThat(testCustBillingRef.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCustBillingRef.getTaxExcludedAmount()).isEqualTo(UPDATED_TAX_EXCLUDED_AMOUNT);
        assertThat(testCustBillingRef.getTaxIncludedAmount()).isEqualTo(UPDATED_TAX_INCLUDED_AMOUNT);
        assertThat(testCustBillingRef.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustBillingRef() throws Exception {
        int databaseSizeBeforeUpdate = custBillingRefRepository.findAll().size();
        custBillingRef.setId(count.incrementAndGet());

        // Create the CustBillingRef
        CustBillingRefDTO custBillingRefDTO = custBillingRefMapper.toDto(custBillingRef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustBillingRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custBillingRefDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custBillingRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustBillingRef in the database
        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustBillingRef() throws Exception {
        int databaseSizeBeforeUpdate = custBillingRefRepository.findAll().size();
        custBillingRef.setId(count.incrementAndGet());

        // Create the CustBillingRef
        CustBillingRefDTO custBillingRefDTO = custBillingRefMapper.toDto(custBillingRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustBillingRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custBillingRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustBillingRef in the database
        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustBillingRef() throws Exception {
        int databaseSizeBeforeUpdate = custBillingRefRepository.findAll().size();
        custBillingRef.setId(count.incrementAndGet());

        // Create the CustBillingRef
        CustBillingRefDTO custBillingRefDTO = custBillingRefMapper.toDto(custBillingRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustBillingRefMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingRefDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustBillingRef in the database
        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustBillingRefWithPatch() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        int databaseSizeBeforeUpdate = custBillingRefRepository.findAll().size();

        // Update the custBillingRef using partial update
        CustBillingRef partialUpdatedCustBillingRef = new CustBillingRef();
        partialUpdatedCustBillingRef.setId(custBillingRef.getId());

        partialUpdatedCustBillingRef
            .amountDue(UPDATED_AMOUNT_DUE)
            .billDate(UPDATED_BILL_DATE)
            .billNo(UPDATED_BILL_NO)
            .category(UPDATED_CATEGORY)
            .paymentDueDate(UPDATED_PAYMENT_DUE_DATE)
            .state(UPDATED_STATE);

        restCustBillingRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustBillingRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustBillingRef))
            )
            .andExpect(status().isOk());

        // Validate the CustBillingRef in the database
        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeUpdate);
        CustBillingRef testCustBillingRef = custBillingRefList.get(custBillingRefList.size() - 1);
        assertThat(testCustBillingRef.getAmountDue()).isEqualByComparingTo(UPDATED_AMOUNT_DUE);
        assertThat(testCustBillingRef.getBillDate()).isEqualTo(UPDATED_BILL_DATE);
        assertThat(testCustBillingRef.getBillNo()).isEqualTo(UPDATED_BILL_NO);
        assertThat(testCustBillingRef.getBillingPeriod()).isEqualTo(DEFAULT_BILLING_PERIOD);
        assertThat(testCustBillingRef.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testCustBillingRef.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testCustBillingRef.getLastUpdatedDate()).isEqualTo(DEFAULT_LAST_UPDATED_DATE);
        assertThat(testCustBillingRef.getNextUpdatedDate()).isEqualTo(DEFAULT_NEXT_UPDATED_DATE);
        assertThat(testCustBillingRef.getPaymentDueDate()).isEqualTo(UPDATED_PAYMENT_DUE_DATE);
        assertThat(testCustBillingRef.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCustBillingRef.getTaxExcludedAmount()).isEqualByComparingTo(DEFAULT_TAX_EXCLUDED_AMOUNT);
        assertThat(testCustBillingRef.getTaxIncludedAmount()).isEqualByComparingTo(DEFAULT_TAX_INCLUDED_AMOUNT);
        assertThat(testCustBillingRef.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustBillingRefWithPatch() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        int databaseSizeBeforeUpdate = custBillingRefRepository.findAll().size();

        // Update the custBillingRef using partial update
        CustBillingRef partialUpdatedCustBillingRef = new CustBillingRef();
        partialUpdatedCustBillingRef.setId(custBillingRef.getId());

        partialUpdatedCustBillingRef
            .amountDue(UPDATED_AMOUNT_DUE)
            .billDate(UPDATED_BILL_DATE)
            .billNo(UPDATED_BILL_NO)
            .billingPeriod(UPDATED_BILLING_PERIOD)
            .category(UPDATED_CATEGORY)
            .href(UPDATED_HREF)
            .lastUpdatedDate(UPDATED_LAST_UPDATED_DATE)
            .nextUpdatedDate(UPDATED_NEXT_UPDATED_DATE)
            .paymentDueDate(UPDATED_PAYMENT_DUE_DATE)
            .state(UPDATED_STATE)
            .taxExcludedAmount(UPDATED_TAX_EXCLUDED_AMOUNT)
            .taxIncludedAmount(UPDATED_TAX_INCLUDED_AMOUNT)
            .customerId(UPDATED_CUSTOMER_ID);

        restCustBillingRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustBillingRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustBillingRef))
            )
            .andExpect(status().isOk());

        // Validate the CustBillingRef in the database
        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeUpdate);
        CustBillingRef testCustBillingRef = custBillingRefList.get(custBillingRefList.size() - 1);
        assertThat(testCustBillingRef.getAmountDue()).isEqualByComparingTo(UPDATED_AMOUNT_DUE);
        assertThat(testCustBillingRef.getBillDate()).isEqualTo(UPDATED_BILL_DATE);
        assertThat(testCustBillingRef.getBillNo()).isEqualTo(UPDATED_BILL_NO);
        assertThat(testCustBillingRef.getBillingPeriod()).isEqualTo(UPDATED_BILLING_PERIOD);
        assertThat(testCustBillingRef.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testCustBillingRef.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testCustBillingRef.getLastUpdatedDate()).isEqualTo(UPDATED_LAST_UPDATED_DATE);
        assertThat(testCustBillingRef.getNextUpdatedDate()).isEqualTo(UPDATED_NEXT_UPDATED_DATE);
        assertThat(testCustBillingRef.getPaymentDueDate()).isEqualTo(UPDATED_PAYMENT_DUE_DATE);
        assertThat(testCustBillingRef.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCustBillingRef.getTaxExcludedAmount()).isEqualByComparingTo(UPDATED_TAX_EXCLUDED_AMOUNT);
        assertThat(testCustBillingRef.getTaxIncludedAmount()).isEqualByComparingTo(UPDATED_TAX_INCLUDED_AMOUNT);
        assertThat(testCustBillingRef.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustBillingRef() throws Exception {
        int databaseSizeBeforeUpdate = custBillingRefRepository.findAll().size();
        custBillingRef.setId(count.incrementAndGet());

        // Create the CustBillingRef
        CustBillingRefDTO custBillingRefDTO = custBillingRefMapper.toDto(custBillingRef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustBillingRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custBillingRefDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custBillingRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustBillingRef in the database
        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustBillingRef() throws Exception {
        int databaseSizeBeforeUpdate = custBillingRefRepository.findAll().size();
        custBillingRef.setId(count.incrementAndGet());

        // Create the CustBillingRef
        CustBillingRefDTO custBillingRefDTO = custBillingRefMapper.toDto(custBillingRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustBillingRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custBillingRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustBillingRef in the database
        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustBillingRef() throws Exception {
        int databaseSizeBeforeUpdate = custBillingRefRepository.findAll().size();
        custBillingRef.setId(count.incrementAndGet());

        // Create the CustBillingRef
        CustBillingRefDTO custBillingRefDTO = custBillingRefMapper.toDto(custBillingRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustBillingRefMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custBillingRefDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustBillingRef in the database
        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustBillingRef() throws Exception {
        // Initialize the database
        custBillingRefRepository.saveAndFlush(custBillingRef);

        int databaseSizeBeforeDelete = custBillingRefRepository.findAll().size();

        // Delete the custBillingRef
        restCustBillingRefMockMvc
            .perform(delete(ENTITY_API_URL_ID, custBillingRef.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustBillingRef> custBillingRefList = custBillingRefRepository.findAll();
        assertThat(custBillingRefList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
