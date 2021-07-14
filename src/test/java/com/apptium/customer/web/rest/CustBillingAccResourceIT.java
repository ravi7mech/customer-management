package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.CustBillingAcc;
import com.apptium.customer.domain.Customer;
import com.apptium.customer.repository.CustBillingAccRepository;
import com.apptium.customer.service.criteria.CustBillingAccCriteria;
import com.apptium.customer.service.dto.CustBillingAccDTO;
import com.apptium.customer.service.mapper.CustBillingAccMapper;
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
 * Integration tests for the {@link CustBillingAccResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustBillingAccResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HREF = "AAAAAAAAAA";
    private static final String UPDATED_HREF = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_BILLING_ACCOUNT_NUMBER = 1L;
    private static final Long UPDATED_BILLING_ACCOUNT_NUMBER = 2L;
    private static final Long SMALLER_BILLING_ACCOUNT_NUMBER = 1L - 1L;

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;
    private static final Long SMALLER_CUSTOMER_ID = 1L - 1L;

    private static final String DEFAULT_CURRENCY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cust-billing-accs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustBillingAccRepository custBillingAccRepository;

    @Autowired
    private CustBillingAccMapper custBillingAccMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustBillingAccMockMvc;

    private CustBillingAcc custBillingAcc;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustBillingAcc createEntity(EntityManager em) {
        CustBillingAcc custBillingAcc = new CustBillingAcc()
            .name(DEFAULT_NAME)
            .href(DEFAULT_HREF)
            .status(DEFAULT_STATUS)
            .description(DEFAULT_DESCRIPTION)
            .billingAccountNumber(DEFAULT_BILLING_ACCOUNT_NUMBER)
            .customerId(DEFAULT_CUSTOMER_ID)
            .currencyCode(DEFAULT_CURRENCY_CODE);
        return custBillingAcc;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustBillingAcc createUpdatedEntity(EntityManager em) {
        CustBillingAcc custBillingAcc = new CustBillingAcc()
            .name(UPDATED_NAME)
            .href(UPDATED_HREF)
            .status(UPDATED_STATUS)
            .description(UPDATED_DESCRIPTION)
            .billingAccountNumber(UPDATED_BILLING_ACCOUNT_NUMBER)
            .customerId(UPDATED_CUSTOMER_ID)
            .currencyCode(UPDATED_CURRENCY_CODE);
        return custBillingAcc;
    }

    @BeforeEach
    public void initTest() {
        custBillingAcc = createEntity(em);
    }

    @Test
    @Transactional
    void createCustBillingAcc() throws Exception {
        int databaseSizeBeforeCreate = custBillingAccRepository.findAll().size();
        // Create the CustBillingAcc
        CustBillingAccDTO custBillingAccDTO = custBillingAccMapper.toDto(custBillingAcc);
        restCustBillingAccMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingAccDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CustBillingAcc in the database
        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeCreate + 1);
        CustBillingAcc testCustBillingAcc = custBillingAccList.get(custBillingAccList.size() - 1);
        assertThat(testCustBillingAcc.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustBillingAcc.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testCustBillingAcc.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCustBillingAcc.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCustBillingAcc.getBillingAccountNumber()).isEqualTo(DEFAULT_BILLING_ACCOUNT_NUMBER);
        assertThat(testCustBillingAcc.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testCustBillingAcc.getCurrencyCode()).isEqualTo(DEFAULT_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void createCustBillingAccWithExistingId() throws Exception {
        // Create the CustBillingAcc with an existing ID
        custBillingAcc.setId(1L);
        CustBillingAccDTO custBillingAccDTO = custBillingAccMapper.toDto(custBillingAcc);

        int databaseSizeBeforeCreate = custBillingAccRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustBillingAccMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingAccDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustBillingAcc in the database
        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingAccRepository.findAll().size();
        // set the field null
        custBillingAcc.setName(null);

        // Create the CustBillingAcc, which fails.
        CustBillingAccDTO custBillingAccDTO = custBillingAccMapper.toDto(custBillingAcc);

        restCustBillingAccMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingAccDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHrefIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingAccRepository.findAll().size();
        // set the field null
        custBillingAcc.setHref(null);

        // Create the CustBillingAcc, which fails.
        CustBillingAccDTO custBillingAccDTO = custBillingAccMapper.toDto(custBillingAcc);

        restCustBillingAccMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingAccDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingAccRepository.findAll().size();
        // set the field null
        custBillingAcc.setStatus(null);

        // Create the CustBillingAcc, which fails.
        CustBillingAccDTO custBillingAccDTO = custBillingAccMapper.toDto(custBillingAcc);

        restCustBillingAccMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingAccDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBillingAccountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingAccRepository.findAll().size();
        // set the field null
        custBillingAcc.setBillingAccountNumber(null);

        // Create the CustBillingAcc, which fails.
        CustBillingAccDTO custBillingAccDTO = custBillingAccMapper.toDto(custBillingAcc);

        restCustBillingAccMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingAccDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingAccRepository.findAll().size();
        // set the field null
        custBillingAcc.setCustomerId(null);

        // Create the CustBillingAcc, which fails.
        CustBillingAccDTO custBillingAccDTO = custBillingAccMapper.toDto(custBillingAcc);

        restCustBillingAccMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingAccDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCurrencyCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = custBillingAccRepository.findAll().size();
        // set the field null
        custBillingAcc.setCurrencyCode(null);

        // Create the CustBillingAcc, which fails.
        CustBillingAccDTO custBillingAccDTO = custBillingAccMapper.toDto(custBillingAcc);

        restCustBillingAccMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingAccDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustBillingAccs() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList
        restCustBillingAccMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custBillingAcc.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].billingAccountNumber").value(hasItem(DEFAULT_BILLING_ACCOUNT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE)));
    }

    @Test
    @Transactional
    void getCustBillingAcc() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get the custBillingAcc
        restCustBillingAccMockMvc
            .perform(get(ENTITY_API_URL_ID, custBillingAcc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custBillingAcc.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.href").value(DEFAULT_HREF))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.billingAccountNumber").value(DEFAULT_BILLING_ACCOUNT_NUMBER.intValue()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()))
            .andExpect(jsonPath("$.currencyCode").value(DEFAULT_CURRENCY_CODE));
    }

    @Test
    @Transactional
    void getCustBillingAccsByIdFiltering() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        Long id = custBillingAcc.getId();

        defaultCustBillingAccShouldBeFound("id.equals=" + id);
        defaultCustBillingAccShouldNotBeFound("id.notEquals=" + id);

        defaultCustBillingAccShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustBillingAccShouldNotBeFound("id.greaterThan=" + id);

        defaultCustBillingAccShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustBillingAccShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where name equals to DEFAULT_NAME
        defaultCustBillingAccShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the custBillingAccList where name equals to UPDATED_NAME
        defaultCustBillingAccShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where name not equals to DEFAULT_NAME
        defaultCustBillingAccShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the custBillingAccList where name not equals to UPDATED_NAME
        defaultCustBillingAccShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCustBillingAccShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the custBillingAccList where name equals to UPDATED_NAME
        defaultCustBillingAccShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where name is not null
        defaultCustBillingAccShouldBeFound("name.specified=true");

        // Get all the custBillingAccList where name is null
        defaultCustBillingAccShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByNameContainsSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where name contains DEFAULT_NAME
        defaultCustBillingAccShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the custBillingAccList where name contains UPDATED_NAME
        defaultCustBillingAccShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where name does not contain DEFAULT_NAME
        defaultCustBillingAccShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the custBillingAccList where name does not contain UPDATED_NAME
        defaultCustBillingAccShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByHrefIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where href equals to DEFAULT_HREF
        defaultCustBillingAccShouldBeFound("href.equals=" + DEFAULT_HREF);

        // Get all the custBillingAccList where href equals to UPDATED_HREF
        defaultCustBillingAccShouldNotBeFound("href.equals=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByHrefIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where href not equals to DEFAULT_HREF
        defaultCustBillingAccShouldNotBeFound("href.notEquals=" + DEFAULT_HREF);

        // Get all the custBillingAccList where href not equals to UPDATED_HREF
        defaultCustBillingAccShouldBeFound("href.notEquals=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByHrefIsInShouldWork() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where href in DEFAULT_HREF or UPDATED_HREF
        defaultCustBillingAccShouldBeFound("href.in=" + DEFAULT_HREF + "," + UPDATED_HREF);

        // Get all the custBillingAccList where href equals to UPDATED_HREF
        defaultCustBillingAccShouldNotBeFound("href.in=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByHrefIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where href is not null
        defaultCustBillingAccShouldBeFound("href.specified=true");

        // Get all the custBillingAccList where href is null
        defaultCustBillingAccShouldNotBeFound("href.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByHrefContainsSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where href contains DEFAULT_HREF
        defaultCustBillingAccShouldBeFound("href.contains=" + DEFAULT_HREF);

        // Get all the custBillingAccList where href contains UPDATED_HREF
        defaultCustBillingAccShouldNotBeFound("href.contains=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByHrefNotContainsSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where href does not contain DEFAULT_HREF
        defaultCustBillingAccShouldNotBeFound("href.doesNotContain=" + DEFAULT_HREF);

        // Get all the custBillingAccList where href does not contain UPDATED_HREF
        defaultCustBillingAccShouldBeFound("href.doesNotContain=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where status equals to DEFAULT_STATUS
        defaultCustBillingAccShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the custBillingAccList where status equals to UPDATED_STATUS
        defaultCustBillingAccShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where status not equals to DEFAULT_STATUS
        defaultCustBillingAccShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the custBillingAccList where status not equals to UPDATED_STATUS
        defaultCustBillingAccShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultCustBillingAccShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the custBillingAccList where status equals to UPDATED_STATUS
        defaultCustBillingAccShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where status is not null
        defaultCustBillingAccShouldBeFound("status.specified=true");

        // Get all the custBillingAccList where status is null
        defaultCustBillingAccShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByStatusContainsSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where status contains DEFAULT_STATUS
        defaultCustBillingAccShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the custBillingAccList where status contains UPDATED_STATUS
        defaultCustBillingAccShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where status does not contain DEFAULT_STATUS
        defaultCustBillingAccShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the custBillingAccList where status does not contain UPDATED_STATUS
        defaultCustBillingAccShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where description equals to DEFAULT_DESCRIPTION
        defaultCustBillingAccShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the custBillingAccList where description equals to UPDATED_DESCRIPTION
        defaultCustBillingAccShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where description not equals to DEFAULT_DESCRIPTION
        defaultCustBillingAccShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the custBillingAccList where description not equals to UPDATED_DESCRIPTION
        defaultCustBillingAccShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCustBillingAccShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the custBillingAccList where description equals to UPDATED_DESCRIPTION
        defaultCustBillingAccShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where description is not null
        defaultCustBillingAccShouldBeFound("description.specified=true");

        // Get all the custBillingAccList where description is null
        defaultCustBillingAccShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where description contains DEFAULT_DESCRIPTION
        defaultCustBillingAccShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the custBillingAccList where description contains UPDATED_DESCRIPTION
        defaultCustBillingAccShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where description does not contain DEFAULT_DESCRIPTION
        defaultCustBillingAccShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the custBillingAccList where description does not contain UPDATED_DESCRIPTION
        defaultCustBillingAccShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByBillingAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where billingAccountNumber equals to DEFAULT_BILLING_ACCOUNT_NUMBER
        defaultCustBillingAccShouldBeFound("billingAccountNumber.equals=" + DEFAULT_BILLING_ACCOUNT_NUMBER);

        // Get all the custBillingAccList where billingAccountNumber equals to UPDATED_BILLING_ACCOUNT_NUMBER
        defaultCustBillingAccShouldNotBeFound("billingAccountNumber.equals=" + UPDATED_BILLING_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByBillingAccountNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where billingAccountNumber not equals to DEFAULT_BILLING_ACCOUNT_NUMBER
        defaultCustBillingAccShouldNotBeFound("billingAccountNumber.notEquals=" + DEFAULT_BILLING_ACCOUNT_NUMBER);

        // Get all the custBillingAccList where billingAccountNumber not equals to UPDATED_BILLING_ACCOUNT_NUMBER
        defaultCustBillingAccShouldBeFound("billingAccountNumber.notEquals=" + UPDATED_BILLING_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByBillingAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where billingAccountNumber in DEFAULT_BILLING_ACCOUNT_NUMBER or UPDATED_BILLING_ACCOUNT_NUMBER
        defaultCustBillingAccShouldBeFound(
            "billingAccountNumber.in=" + DEFAULT_BILLING_ACCOUNT_NUMBER + "," + UPDATED_BILLING_ACCOUNT_NUMBER
        );

        // Get all the custBillingAccList where billingAccountNumber equals to UPDATED_BILLING_ACCOUNT_NUMBER
        defaultCustBillingAccShouldNotBeFound("billingAccountNumber.in=" + UPDATED_BILLING_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByBillingAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where billingAccountNumber is not null
        defaultCustBillingAccShouldBeFound("billingAccountNumber.specified=true");

        // Get all the custBillingAccList where billingAccountNumber is null
        defaultCustBillingAccShouldNotBeFound("billingAccountNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByBillingAccountNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where billingAccountNumber is greater than or equal to DEFAULT_BILLING_ACCOUNT_NUMBER
        defaultCustBillingAccShouldBeFound("billingAccountNumber.greaterThanOrEqual=" + DEFAULT_BILLING_ACCOUNT_NUMBER);

        // Get all the custBillingAccList where billingAccountNumber is greater than or equal to UPDATED_BILLING_ACCOUNT_NUMBER
        defaultCustBillingAccShouldNotBeFound("billingAccountNumber.greaterThanOrEqual=" + UPDATED_BILLING_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByBillingAccountNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where billingAccountNumber is less than or equal to DEFAULT_BILLING_ACCOUNT_NUMBER
        defaultCustBillingAccShouldBeFound("billingAccountNumber.lessThanOrEqual=" + DEFAULT_BILLING_ACCOUNT_NUMBER);

        // Get all the custBillingAccList where billingAccountNumber is less than or equal to SMALLER_BILLING_ACCOUNT_NUMBER
        defaultCustBillingAccShouldNotBeFound("billingAccountNumber.lessThanOrEqual=" + SMALLER_BILLING_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByBillingAccountNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where billingAccountNumber is less than DEFAULT_BILLING_ACCOUNT_NUMBER
        defaultCustBillingAccShouldNotBeFound("billingAccountNumber.lessThan=" + DEFAULT_BILLING_ACCOUNT_NUMBER);

        // Get all the custBillingAccList where billingAccountNumber is less than UPDATED_BILLING_ACCOUNT_NUMBER
        defaultCustBillingAccShouldBeFound("billingAccountNumber.lessThan=" + UPDATED_BILLING_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByBillingAccountNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where billingAccountNumber is greater than DEFAULT_BILLING_ACCOUNT_NUMBER
        defaultCustBillingAccShouldNotBeFound("billingAccountNumber.greaterThan=" + DEFAULT_BILLING_ACCOUNT_NUMBER);

        // Get all the custBillingAccList where billingAccountNumber is greater than SMALLER_BILLING_ACCOUNT_NUMBER
        defaultCustBillingAccShouldBeFound("billingAccountNumber.greaterThan=" + SMALLER_BILLING_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByCustomerIdIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where customerId equals to DEFAULT_CUSTOMER_ID
        defaultCustBillingAccShouldBeFound("customerId.equals=" + DEFAULT_CUSTOMER_ID);

        // Get all the custBillingAccList where customerId equals to UPDATED_CUSTOMER_ID
        defaultCustBillingAccShouldNotBeFound("customerId.equals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByCustomerIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where customerId not equals to DEFAULT_CUSTOMER_ID
        defaultCustBillingAccShouldNotBeFound("customerId.notEquals=" + DEFAULT_CUSTOMER_ID);

        // Get all the custBillingAccList where customerId not equals to UPDATED_CUSTOMER_ID
        defaultCustBillingAccShouldBeFound("customerId.notEquals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByCustomerIdIsInShouldWork() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where customerId in DEFAULT_CUSTOMER_ID or UPDATED_CUSTOMER_ID
        defaultCustBillingAccShouldBeFound("customerId.in=" + DEFAULT_CUSTOMER_ID + "," + UPDATED_CUSTOMER_ID);

        // Get all the custBillingAccList where customerId equals to UPDATED_CUSTOMER_ID
        defaultCustBillingAccShouldNotBeFound("customerId.in=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByCustomerIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where customerId is not null
        defaultCustBillingAccShouldBeFound("customerId.specified=true");

        // Get all the custBillingAccList where customerId is null
        defaultCustBillingAccShouldNotBeFound("customerId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByCustomerIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where customerId is greater than or equal to DEFAULT_CUSTOMER_ID
        defaultCustBillingAccShouldBeFound("customerId.greaterThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the custBillingAccList where customerId is greater than or equal to UPDATED_CUSTOMER_ID
        defaultCustBillingAccShouldNotBeFound("customerId.greaterThanOrEqual=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByCustomerIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where customerId is less than or equal to DEFAULT_CUSTOMER_ID
        defaultCustBillingAccShouldBeFound("customerId.lessThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the custBillingAccList where customerId is less than or equal to SMALLER_CUSTOMER_ID
        defaultCustBillingAccShouldNotBeFound("customerId.lessThanOrEqual=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByCustomerIdIsLessThanSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where customerId is less than DEFAULT_CUSTOMER_ID
        defaultCustBillingAccShouldNotBeFound("customerId.lessThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the custBillingAccList where customerId is less than UPDATED_CUSTOMER_ID
        defaultCustBillingAccShouldBeFound("customerId.lessThan=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByCustomerIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where customerId is greater than DEFAULT_CUSTOMER_ID
        defaultCustBillingAccShouldNotBeFound("customerId.greaterThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the custBillingAccList where customerId is greater than SMALLER_CUSTOMER_ID
        defaultCustBillingAccShouldBeFound("customerId.greaterThan=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByCurrencyCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where currencyCode equals to DEFAULT_CURRENCY_CODE
        defaultCustBillingAccShouldBeFound("currencyCode.equals=" + DEFAULT_CURRENCY_CODE);

        // Get all the custBillingAccList where currencyCode equals to UPDATED_CURRENCY_CODE
        defaultCustBillingAccShouldNotBeFound("currencyCode.equals=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByCurrencyCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where currencyCode not equals to DEFAULT_CURRENCY_CODE
        defaultCustBillingAccShouldNotBeFound("currencyCode.notEquals=" + DEFAULT_CURRENCY_CODE);

        // Get all the custBillingAccList where currencyCode not equals to UPDATED_CURRENCY_CODE
        defaultCustBillingAccShouldBeFound("currencyCode.notEquals=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByCurrencyCodeIsInShouldWork() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where currencyCode in DEFAULT_CURRENCY_CODE or UPDATED_CURRENCY_CODE
        defaultCustBillingAccShouldBeFound("currencyCode.in=" + DEFAULT_CURRENCY_CODE + "," + UPDATED_CURRENCY_CODE);

        // Get all the custBillingAccList where currencyCode equals to UPDATED_CURRENCY_CODE
        defaultCustBillingAccShouldNotBeFound("currencyCode.in=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByCurrencyCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where currencyCode is not null
        defaultCustBillingAccShouldBeFound("currencyCode.specified=true");

        // Get all the custBillingAccList where currencyCode is null
        defaultCustBillingAccShouldNotBeFound("currencyCode.specified=false");
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByCurrencyCodeContainsSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where currencyCode contains DEFAULT_CURRENCY_CODE
        defaultCustBillingAccShouldBeFound("currencyCode.contains=" + DEFAULT_CURRENCY_CODE);

        // Get all the custBillingAccList where currencyCode contains UPDATED_CURRENCY_CODE
        defaultCustBillingAccShouldNotBeFound("currencyCode.contains=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByCurrencyCodeNotContainsSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        // Get all the custBillingAccList where currencyCode does not contain DEFAULT_CURRENCY_CODE
        defaultCustBillingAccShouldNotBeFound("currencyCode.doesNotContain=" + DEFAULT_CURRENCY_CODE);

        // Get all the custBillingAccList where currencyCode does not contain UPDATED_CURRENCY_CODE
        defaultCustBillingAccShouldBeFound("currencyCode.doesNotContain=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void getAllCustBillingAccsByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);
        Customer customer = CustomerResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        custBillingAcc.setCustomer(customer);
        customer.setCustBillingAcc(custBillingAcc);
        custBillingAccRepository.saveAndFlush(custBillingAcc);
        Long customerId = customer.getId();

        // Get all the custBillingAccList where customer equals to customerId
        defaultCustBillingAccShouldBeFound("customerId.equals=" + customerId);

        // Get all the custBillingAccList where customer equals to (customerId + 1)
        defaultCustBillingAccShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustBillingAccShouldBeFound(String filter) throws Exception {
        restCustBillingAccMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custBillingAcc.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].billingAccountNumber").value(hasItem(DEFAULT_BILLING_ACCOUNT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE)));

        // Check, that the count call also returns 1
        restCustBillingAccMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustBillingAccShouldNotBeFound(String filter) throws Exception {
        restCustBillingAccMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustBillingAccMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustBillingAcc() throws Exception {
        // Get the custBillingAcc
        restCustBillingAccMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustBillingAcc() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        int databaseSizeBeforeUpdate = custBillingAccRepository.findAll().size();

        // Update the custBillingAcc
        CustBillingAcc updatedCustBillingAcc = custBillingAccRepository.findById(custBillingAcc.getId()).get();
        // Disconnect from session so that the updates on updatedCustBillingAcc are not directly saved in db
        em.detach(updatedCustBillingAcc);
        updatedCustBillingAcc
            .name(UPDATED_NAME)
            .href(UPDATED_HREF)
            .status(UPDATED_STATUS)
            .description(UPDATED_DESCRIPTION)
            .billingAccountNumber(UPDATED_BILLING_ACCOUNT_NUMBER)
            .customerId(UPDATED_CUSTOMER_ID)
            .currencyCode(UPDATED_CURRENCY_CODE);
        CustBillingAccDTO custBillingAccDTO = custBillingAccMapper.toDto(updatedCustBillingAcc);

        restCustBillingAccMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custBillingAccDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custBillingAccDTO))
            )
            .andExpect(status().isOk());

        // Validate the CustBillingAcc in the database
        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeUpdate);
        CustBillingAcc testCustBillingAcc = custBillingAccList.get(custBillingAccList.size() - 1);
        assertThat(testCustBillingAcc.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustBillingAcc.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testCustBillingAcc.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustBillingAcc.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCustBillingAcc.getBillingAccountNumber()).isEqualTo(UPDATED_BILLING_ACCOUNT_NUMBER);
        assertThat(testCustBillingAcc.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testCustBillingAcc.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void putNonExistingCustBillingAcc() throws Exception {
        int databaseSizeBeforeUpdate = custBillingAccRepository.findAll().size();
        custBillingAcc.setId(count.incrementAndGet());

        // Create the CustBillingAcc
        CustBillingAccDTO custBillingAccDTO = custBillingAccMapper.toDto(custBillingAcc);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustBillingAccMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custBillingAccDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custBillingAccDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustBillingAcc in the database
        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustBillingAcc() throws Exception {
        int databaseSizeBeforeUpdate = custBillingAccRepository.findAll().size();
        custBillingAcc.setId(count.incrementAndGet());

        // Create the CustBillingAcc
        CustBillingAccDTO custBillingAccDTO = custBillingAccMapper.toDto(custBillingAcc);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustBillingAccMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custBillingAccDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustBillingAcc in the database
        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustBillingAcc() throws Exception {
        int databaseSizeBeforeUpdate = custBillingAccRepository.findAll().size();
        custBillingAcc.setId(count.incrementAndGet());

        // Create the CustBillingAcc
        CustBillingAccDTO custBillingAccDTO = custBillingAccMapper.toDto(custBillingAcc);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustBillingAccMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(custBillingAccDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustBillingAcc in the database
        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustBillingAccWithPatch() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        int databaseSizeBeforeUpdate = custBillingAccRepository.findAll().size();

        // Update the custBillingAcc using partial update
        CustBillingAcc partialUpdatedCustBillingAcc = new CustBillingAcc();
        partialUpdatedCustBillingAcc.setId(custBillingAcc.getId());

        partialUpdatedCustBillingAcc.description(UPDATED_DESCRIPTION).customerId(UPDATED_CUSTOMER_ID).currencyCode(UPDATED_CURRENCY_CODE);

        restCustBillingAccMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustBillingAcc.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustBillingAcc))
            )
            .andExpect(status().isOk());

        // Validate the CustBillingAcc in the database
        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeUpdate);
        CustBillingAcc testCustBillingAcc = custBillingAccList.get(custBillingAccList.size() - 1);
        assertThat(testCustBillingAcc.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustBillingAcc.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testCustBillingAcc.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCustBillingAcc.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCustBillingAcc.getBillingAccountNumber()).isEqualTo(DEFAULT_BILLING_ACCOUNT_NUMBER);
        assertThat(testCustBillingAcc.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testCustBillingAcc.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void fullUpdateCustBillingAccWithPatch() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        int databaseSizeBeforeUpdate = custBillingAccRepository.findAll().size();

        // Update the custBillingAcc using partial update
        CustBillingAcc partialUpdatedCustBillingAcc = new CustBillingAcc();
        partialUpdatedCustBillingAcc.setId(custBillingAcc.getId());

        partialUpdatedCustBillingAcc
            .name(UPDATED_NAME)
            .href(UPDATED_HREF)
            .status(UPDATED_STATUS)
            .description(UPDATED_DESCRIPTION)
            .billingAccountNumber(UPDATED_BILLING_ACCOUNT_NUMBER)
            .customerId(UPDATED_CUSTOMER_ID)
            .currencyCode(UPDATED_CURRENCY_CODE);

        restCustBillingAccMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustBillingAcc.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustBillingAcc))
            )
            .andExpect(status().isOk());

        // Validate the CustBillingAcc in the database
        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeUpdate);
        CustBillingAcc testCustBillingAcc = custBillingAccList.get(custBillingAccList.size() - 1);
        assertThat(testCustBillingAcc.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustBillingAcc.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testCustBillingAcc.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustBillingAcc.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCustBillingAcc.getBillingAccountNumber()).isEqualTo(UPDATED_BILLING_ACCOUNT_NUMBER);
        assertThat(testCustBillingAcc.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testCustBillingAcc.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingCustBillingAcc() throws Exception {
        int databaseSizeBeforeUpdate = custBillingAccRepository.findAll().size();
        custBillingAcc.setId(count.incrementAndGet());

        // Create the CustBillingAcc
        CustBillingAccDTO custBillingAccDTO = custBillingAccMapper.toDto(custBillingAcc);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustBillingAccMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custBillingAccDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custBillingAccDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustBillingAcc in the database
        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustBillingAcc() throws Exception {
        int databaseSizeBeforeUpdate = custBillingAccRepository.findAll().size();
        custBillingAcc.setId(count.incrementAndGet());

        // Create the CustBillingAcc
        CustBillingAccDTO custBillingAccDTO = custBillingAccMapper.toDto(custBillingAcc);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustBillingAccMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custBillingAccDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustBillingAcc in the database
        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustBillingAcc() throws Exception {
        int databaseSizeBeforeUpdate = custBillingAccRepository.findAll().size();
        custBillingAcc.setId(count.incrementAndGet());

        // Create the CustBillingAcc
        CustBillingAccDTO custBillingAccDTO = custBillingAccMapper.toDto(custBillingAcc);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustBillingAccMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custBillingAccDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustBillingAcc in the database
        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustBillingAcc() throws Exception {
        // Initialize the database
        custBillingAccRepository.saveAndFlush(custBillingAcc);

        int databaseSizeBeforeDelete = custBillingAccRepository.findAll().size();

        // Delete the custBillingAcc
        restCustBillingAccMockMvc
            .perform(delete(ENTITY_API_URL_ID, custBillingAcc.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustBillingAcc> custBillingAccList = custBillingAccRepository.findAll();
        assertThat(custBillingAccList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
