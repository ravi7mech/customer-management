package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.CustBillingAcc;
import com.apptium.customer.domain.CustBillingRef;
import com.apptium.customer.domain.CustChar;
import com.apptium.customer.domain.CustCommunicationRef;
import com.apptium.customer.domain.CustContact;
import com.apptium.customer.domain.CustCreditProfile;
import com.apptium.customer.domain.CustISVRef;
import com.apptium.customer.domain.CustNewsLetterConfig;
import com.apptium.customer.domain.CustPasswordChar;
import com.apptium.customer.domain.CustRelParty;
import com.apptium.customer.domain.CustSecurityChar;
import com.apptium.customer.domain.CustStatistics;
import com.apptium.customer.domain.Customer;
import com.apptium.customer.domain.Industry;
import com.apptium.customer.domain.ShoppingSessionRef;
import com.apptium.customer.repository.CustomerRepository;
import com.apptium.customer.service.criteria.CustomerCriteria;
import com.apptium.customer.service.dto.CustomerDTO;
import com.apptium.customer.service.mapper.CustomerMapper;
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
 * Integration tests for the {@link CustomerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustomerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FORMATTED_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FORMATTED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TRADING_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TRADING_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CUST_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

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

    private static final String DEFAULT_CUSTOMER_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_ID_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_ID_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;
    private static final Long SMALLER_COMPANY_ID = 1L - 1L;

    private static final Long DEFAULT_PRIMARY_CON_ADMIN_IND_ID = 1L;
    private static final Long UPDATED_PRIMARY_CON_ADMIN_IND_ID = 2L;
    private static final Long SMALLER_PRIMARY_CON_ADMIN_IND_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/customers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerMockMvc;

    private Customer customer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customer createEntity(EntityManager em) {
        Customer customer = new Customer()
            .name(DEFAULT_NAME)
            .formattedName(DEFAULT_FORMATTED_NAME)
            .tradingName(DEFAULT_TRADING_NAME)
            .custType(DEFAULT_CUST_TYPE)
            .title(DEFAULT_TITLE)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .gender(DEFAULT_GENDER)
            .maritalStatus(DEFAULT_MARITAL_STATUS)
            .nationality(DEFAULT_NATIONALITY)
            .status(DEFAULT_STATUS)
            .customerEmail(DEFAULT_CUSTOMER_EMAIL)
            .companyIdType(DEFAULT_COMPANY_ID_TYPE)
            .companyId(DEFAULT_COMPANY_ID)
            .primaryConAdminIndId(DEFAULT_PRIMARY_CON_ADMIN_IND_ID);
        return customer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customer createUpdatedEntity(EntityManager em) {
        Customer customer = new Customer()
            .name(UPDATED_NAME)
            .formattedName(UPDATED_FORMATTED_NAME)
            .tradingName(UPDATED_TRADING_NAME)
            .custType(UPDATED_CUST_TYPE)
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .gender(UPDATED_GENDER)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .nationality(UPDATED_NATIONALITY)
            .status(UPDATED_STATUS)
            .customerEmail(UPDATED_CUSTOMER_EMAIL)
            .companyIdType(UPDATED_COMPANY_ID_TYPE)
            .companyId(UPDATED_COMPANY_ID)
            .primaryConAdminIndId(UPDATED_PRIMARY_CON_ADMIN_IND_ID);
        return customer;
    }

    @BeforeEach
    public void initTest() {
        customer = createEntity(em);
    }

    @Test
    @Transactional
    void createCustomer() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();
        // Create the Customer
        CustomerDTO customerDTO = customerMapper.toDto(customer);
        restCustomerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isCreated());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate + 1);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomer.getFormattedName()).isEqualTo(DEFAULT_FORMATTED_NAME);
        assertThat(testCustomer.getTradingName()).isEqualTo(DEFAULT_TRADING_NAME);
        assertThat(testCustomer.getCustType()).isEqualTo(DEFAULT_CUST_TYPE);
        assertThat(testCustomer.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCustomer.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCustomer.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCustomer.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testCustomer.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testCustomer.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testCustomer.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testCustomer.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testCustomer.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCustomer.getCustomerEmail()).isEqualTo(DEFAULT_CUSTOMER_EMAIL);
        assertThat(testCustomer.getCompanyIdType()).isEqualTo(DEFAULT_COMPANY_ID_TYPE);
        assertThat(testCustomer.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testCustomer.getPrimaryConAdminIndId()).isEqualTo(DEFAULT_PRIMARY_CON_ADMIN_IND_ID);
    }

    @Test
    @Transactional
    void createCustomerWithExistingId() throws Exception {
        // Create the Customer with an existing ID
        customer.setId(1L);
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setName(null);

        // Create the Customer, which fails.
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        restCustomerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFormattedNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setFormattedName(null);

        // Create the Customer, which fails.
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        restCustomerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTradingNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setTradingName(null);

        // Create the Customer, which fails.
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        restCustomerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setCustType(null);

        // Create the Customer, which fails.
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        restCustomerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setTitle(null);

        // Create the Customer, which fails.
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        restCustomerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setFirstName(null);

        // Create the Customer, which fails.
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        restCustomerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setLastName(null);

        // Create the Customer, which fails.
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        restCustomerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateOfBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setDateOfBirth(null);

        // Create the Customer, which fails.
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        restCustomerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNationalityIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setNationality(null);

        // Create the Customer, which fails.
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        restCustomerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setStatus(null);

        // Create the Customer, which fails.
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        restCustomerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setCustomerEmail(null);

        // Create the Customer, which fails.
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        restCustomerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCompanyIdTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setCompanyIdType(null);

        // Create the Customer, which fails.
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        restCustomerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCompanyIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setCompanyId(null);

        // Create the Customer, which fails.
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        restCustomerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrimaryConAdminIndIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerRepository.findAll().size();
        // set the field null
        customer.setPrimaryConAdminIndId(null);

        // Create the Customer, which fails.
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        restCustomerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isBadRequest());

        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustomers() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList
        restCustomerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].formattedName").value(hasItem(DEFAULT_FORMATTED_NAME)))
            .andExpect(jsonPath("$.[*].tradingName").value(hasItem(DEFAULT_TRADING_NAME)))
            .andExpect(jsonPath("$.[*].custType").value(hasItem(DEFAULT_CUST_TYPE)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].customerEmail").value(hasItem(DEFAULT_CUSTOMER_EMAIL)))
            .andExpect(jsonPath("$.[*].companyIdType").value(hasItem(DEFAULT_COMPANY_ID_TYPE)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].primaryConAdminIndId").value(hasItem(DEFAULT_PRIMARY_CON_ADMIN_IND_ID.intValue())));
    }

    @Test
    @Transactional
    void getCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get the customer
        restCustomerMockMvc
            .perform(get(ENTITY_API_URL_ID, customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.formattedName").value(DEFAULT_FORMATTED_NAME))
            .andExpect(jsonPath("$.tradingName").value(DEFAULT_TRADING_NAME))
            .andExpect(jsonPath("$.custType").value(DEFAULT_CUST_TYPE))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.customerEmail").value(DEFAULT_CUSTOMER_EMAIL))
            .andExpect(jsonPath("$.companyIdType").value(DEFAULT_COMPANY_ID_TYPE))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.primaryConAdminIndId").value(DEFAULT_PRIMARY_CON_ADMIN_IND_ID.intValue()));
    }

    @Test
    @Transactional
    void getCustomersByIdFiltering() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        Long id = customer.getId();

        defaultCustomerShouldBeFound("id.equals=" + id);
        defaultCustomerShouldNotBeFound("id.notEquals=" + id);

        defaultCustomerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustomerShouldNotBeFound("id.greaterThan=" + id);

        defaultCustomerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustomerShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustomersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where name equals to DEFAULT_NAME
        defaultCustomerShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the customerList where name equals to UPDATED_NAME
        defaultCustomerShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where name not equals to DEFAULT_NAME
        defaultCustomerShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the customerList where name not equals to UPDATED_NAME
        defaultCustomerShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCustomerShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the customerList where name equals to UPDATED_NAME
        defaultCustomerShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where name is not null
        defaultCustomerShouldBeFound("name.specified=true");

        // Get all the customerList where name is null
        defaultCustomerShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByNameContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where name contains DEFAULT_NAME
        defaultCustomerShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the customerList where name contains UPDATED_NAME
        defaultCustomerShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where name does not contain DEFAULT_NAME
        defaultCustomerShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the customerList where name does not contain UPDATED_NAME
        defaultCustomerShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByFormattedNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where formattedName equals to DEFAULT_FORMATTED_NAME
        defaultCustomerShouldBeFound("formattedName.equals=" + DEFAULT_FORMATTED_NAME);

        // Get all the customerList where formattedName equals to UPDATED_FORMATTED_NAME
        defaultCustomerShouldNotBeFound("formattedName.equals=" + UPDATED_FORMATTED_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByFormattedNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where formattedName not equals to DEFAULT_FORMATTED_NAME
        defaultCustomerShouldNotBeFound("formattedName.notEquals=" + DEFAULT_FORMATTED_NAME);

        // Get all the customerList where formattedName not equals to UPDATED_FORMATTED_NAME
        defaultCustomerShouldBeFound("formattedName.notEquals=" + UPDATED_FORMATTED_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByFormattedNameIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where formattedName in DEFAULT_FORMATTED_NAME or UPDATED_FORMATTED_NAME
        defaultCustomerShouldBeFound("formattedName.in=" + DEFAULT_FORMATTED_NAME + "," + UPDATED_FORMATTED_NAME);

        // Get all the customerList where formattedName equals to UPDATED_FORMATTED_NAME
        defaultCustomerShouldNotBeFound("formattedName.in=" + UPDATED_FORMATTED_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByFormattedNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where formattedName is not null
        defaultCustomerShouldBeFound("formattedName.specified=true");

        // Get all the customerList where formattedName is null
        defaultCustomerShouldNotBeFound("formattedName.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByFormattedNameContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where formattedName contains DEFAULT_FORMATTED_NAME
        defaultCustomerShouldBeFound("formattedName.contains=" + DEFAULT_FORMATTED_NAME);

        // Get all the customerList where formattedName contains UPDATED_FORMATTED_NAME
        defaultCustomerShouldNotBeFound("formattedName.contains=" + UPDATED_FORMATTED_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByFormattedNameNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where formattedName does not contain DEFAULT_FORMATTED_NAME
        defaultCustomerShouldNotBeFound("formattedName.doesNotContain=" + DEFAULT_FORMATTED_NAME);

        // Get all the customerList where formattedName does not contain UPDATED_FORMATTED_NAME
        defaultCustomerShouldBeFound("formattedName.doesNotContain=" + UPDATED_FORMATTED_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByTradingNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where tradingName equals to DEFAULT_TRADING_NAME
        defaultCustomerShouldBeFound("tradingName.equals=" + DEFAULT_TRADING_NAME);

        // Get all the customerList where tradingName equals to UPDATED_TRADING_NAME
        defaultCustomerShouldNotBeFound("tradingName.equals=" + UPDATED_TRADING_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByTradingNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where tradingName not equals to DEFAULT_TRADING_NAME
        defaultCustomerShouldNotBeFound("tradingName.notEquals=" + DEFAULT_TRADING_NAME);

        // Get all the customerList where tradingName not equals to UPDATED_TRADING_NAME
        defaultCustomerShouldBeFound("tradingName.notEquals=" + UPDATED_TRADING_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByTradingNameIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where tradingName in DEFAULT_TRADING_NAME or UPDATED_TRADING_NAME
        defaultCustomerShouldBeFound("tradingName.in=" + DEFAULT_TRADING_NAME + "," + UPDATED_TRADING_NAME);

        // Get all the customerList where tradingName equals to UPDATED_TRADING_NAME
        defaultCustomerShouldNotBeFound("tradingName.in=" + UPDATED_TRADING_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByTradingNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where tradingName is not null
        defaultCustomerShouldBeFound("tradingName.specified=true");

        // Get all the customerList where tradingName is null
        defaultCustomerShouldNotBeFound("tradingName.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByTradingNameContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where tradingName contains DEFAULT_TRADING_NAME
        defaultCustomerShouldBeFound("tradingName.contains=" + DEFAULT_TRADING_NAME);

        // Get all the customerList where tradingName contains UPDATED_TRADING_NAME
        defaultCustomerShouldNotBeFound("tradingName.contains=" + UPDATED_TRADING_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByTradingNameNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where tradingName does not contain DEFAULT_TRADING_NAME
        defaultCustomerShouldNotBeFound("tradingName.doesNotContain=" + DEFAULT_TRADING_NAME);

        // Get all the customerList where tradingName does not contain UPDATED_TRADING_NAME
        defaultCustomerShouldBeFound("tradingName.doesNotContain=" + UPDATED_TRADING_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByCustTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where custType equals to DEFAULT_CUST_TYPE
        defaultCustomerShouldBeFound("custType.equals=" + DEFAULT_CUST_TYPE);

        // Get all the customerList where custType equals to UPDATED_CUST_TYPE
        defaultCustomerShouldNotBeFound("custType.equals=" + UPDATED_CUST_TYPE);
    }

    @Test
    @Transactional
    void getAllCustomersByCustTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where custType not equals to DEFAULT_CUST_TYPE
        defaultCustomerShouldNotBeFound("custType.notEquals=" + DEFAULT_CUST_TYPE);

        // Get all the customerList where custType not equals to UPDATED_CUST_TYPE
        defaultCustomerShouldBeFound("custType.notEquals=" + UPDATED_CUST_TYPE);
    }

    @Test
    @Transactional
    void getAllCustomersByCustTypeIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where custType in DEFAULT_CUST_TYPE or UPDATED_CUST_TYPE
        defaultCustomerShouldBeFound("custType.in=" + DEFAULT_CUST_TYPE + "," + UPDATED_CUST_TYPE);

        // Get all the customerList where custType equals to UPDATED_CUST_TYPE
        defaultCustomerShouldNotBeFound("custType.in=" + UPDATED_CUST_TYPE);
    }

    @Test
    @Transactional
    void getAllCustomersByCustTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where custType is not null
        defaultCustomerShouldBeFound("custType.specified=true");

        // Get all the customerList where custType is null
        defaultCustomerShouldNotBeFound("custType.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByCustTypeContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where custType contains DEFAULT_CUST_TYPE
        defaultCustomerShouldBeFound("custType.contains=" + DEFAULT_CUST_TYPE);

        // Get all the customerList where custType contains UPDATED_CUST_TYPE
        defaultCustomerShouldNotBeFound("custType.contains=" + UPDATED_CUST_TYPE);
    }

    @Test
    @Transactional
    void getAllCustomersByCustTypeNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where custType does not contain DEFAULT_CUST_TYPE
        defaultCustomerShouldNotBeFound("custType.doesNotContain=" + DEFAULT_CUST_TYPE);

        // Get all the customerList where custType does not contain UPDATED_CUST_TYPE
        defaultCustomerShouldBeFound("custType.doesNotContain=" + UPDATED_CUST_TYPE);
    }

    @Test
    @Transactional
    void getAllCustomersByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where title equals to DEFAULT_TITLE
        defaultCustomerShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the customerList where title equals to UPDATED_TITLE
        defaultCustomerShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllCustomersByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where title not equals to DEFAULT_TITLE
        defaultCustomerShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the customerList where title not equals to UPDATED_TITLE
        defaultCustomerShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllCustomersByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultCustomerShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the customerList where title equals to UPDATED_TITLE
        defaultCustomerShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllCustomersByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where title is not null
        defaultCustomerShouldBeFound("title.specified=true");

        // Get all the customerList where title is null
        defaultCustomerShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByTitleContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where title contains DEFAULT_TITLE
        defaultCustomerShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the customerList where title contains UPDATED_TITLE
        defaultCustomerShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllCustomersByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where title does not contain DEFAULT_TITLE
        defaultCustomerShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the customerList where title does not contain UPDATED_TITLE
        defaultCustomerShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllCustomersByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where firstName equals to DEFAULT_FIRST_NAME
        defaultCustomerShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the customerList where firstName equals to UPDATED_FIRST_NAME
        defaultCustomerShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where firstName not equals to DEFAULT_FIRST_NAME
        defaultCustomerShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the customerList where firstName not equals to UPDATED_FIRST_NAME
        defaultCustomerShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultCustomerShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the customerList where firstName equals to UPDATED_FIRST_NAME
        defaultCustomerShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where firstName is not null
        defaultCustomerShouldBeFound("firstName.specified=true");

        // Get all the customerList where firstName is null
        defaultCustomerShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where firstName contains DEFAULT_FIRST_NAME
        defaultCustomerShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the customerList where firstName contains UPDATED_FIRST_NAME
        defaultCustomerShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where firstName does not contain DEFAULT_FIRST_NAME
        defaultCustomerShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the customerList where firstName does not contain UPDATED_FIRST_NAME
        defaultCustomerShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where lastName equals to DEFAULT_LAST_NAME
        defaultCustomerShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the customerList where lastName equals to UPDATED_LAST_NAME
        defaultCustomerShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where lastName not equals to DEFAULT_LAST_NAME
        defaultCustomerShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the customerList where lastName not equals to UPDATED_LAST_NAME
        defaultCustomerShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultCustomerShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the customerList where lastName equals to UPDATED_LAST_NAME
        defaultCustomerShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where lastName is not null
        defaultCustomerShouldBeFound("lastName.specified=true");

        // Get all the customerList where lastName is null
        defaultCustomerShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByLastNameContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where lastName contains DEFAULT_LAST_NAME
        defaultCustomerShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the customerList where lastName contains UPDATED_LAST_NAME
        defaultCustomerShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where lastName does not contain DEFAULT_LAST_NAME
        defaultCustomerShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the customerList where lastName does not contain UPDATED_LAST_NAME
        defaultCustomerShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByMiddleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where middleName equals to DEFAULT_MIDDLE_NAME
        defaultCustomerShouldBeFound("middleName.equals=" + DEFAULT_MIDDLE_NAME);

        // Get all the customerList where middleName equals to UPDATED_MIDDLE_NAME
        defaultCustomerShouldNotBeFound("middleName.equals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByMiddleNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where middleName not equals to DEFAULT_MIDDLE_NAME
        defaultCustomerShouldNotBeFound("middleName.notEquals=" + DEFAULT_MIDDLE_NAME);

        // Get all the customerList where middleName not equals to UPDATED_MIDDLE_NAME
        defaultCustomerShouldBeFound("middleName.notEquals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByMiddleNameIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where middleName in DEFAULT_MIDDLE_NAME or UPDATED_MIDDLE_NAME
        defaultCustomerShouldBeFound("middleName.in=" + DEFAULT_MIDDLE_NAME + "," + UPDATED_MIDDLE_NAME);

        // Get all the customerList where middleName equals to UPDATED_MIDDLE_NAME
        defaultCustomerShouldNotBeFound("middleName.in=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByMiddleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where middleName is not null
        defaultCustomerShouldBeFound("middleName.specified=true");

        // Get all the customerList where middleName is null
        defaultCustomerShouldNotBeFound("middleName.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByMiddleNameContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where middleName contains DEFAULT_MIDDLE_NAME
        defaultCustomerShouldBeFound("middleName.contains=" + DEFAULT_MIDDLE_NAME);

        // Get all the customerList where middleName contains UPDATED_MIDDLE_NAME
        defaultCustomerShouldNotBeFound("middleName.contains=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByMiddleNameNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where middleName does not contain DEFAULT_MIDDLE_NAME
        defaultCustomerShouldNotBeFound("middleName.doesNotContain=" + DEFAULT_MIDDLE_NAME);

        // Get all the customerList where middleName does not contain UPDATED_MIDDLE_NAME
        defaultCustomerShouldBeFound("middleName.doesNotContain=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllCustomersByDateOfBirthIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where dateOfBirth equals to DEFAULT_DATE_OF_BIRTH
        defaultCustomerShouldBeFound("dateOfBirth.equals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the customerList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultCustomerShouldNotBeFound("dateOfBirth.equals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllCustomersByDateOfBirthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where dateOfBirth not equals to DEFAULT_DATE_OF_BIRTH
        defaultCustomerShouldNotBeFound("dateOfBirth.notEquals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the customerList where dateOfBirth not equals to UPDATED_DATE_OF_BIRTH
        defaultCustomerShouldBeFound("dateOfBirth.notEquals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllCustomersByDateOfBirthIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where dateOfBirth in DEFAULT_DATE_OF_BIRTH or UPDATED_DATE_OF_BIRTH
        defaultCustomerShouldBeFound("dateOfBirth.in=" + DEFAULT_DATE_OF_BIRTH + "," + UPDATED_DATE_OF_BIRTH);

        // Get all the customerList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultCustomerShouldNotBeFound("dateOfBirth.in=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllCustomersByDateOfBirthIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where dateOfBirth is not null
        defaultCustomerShouldBeFound("dateOfBirth.specified=true");

        // Get all the customerList where dateOfBirth is null
        defaultCustomerShouldNotBeFound("dateOfBirth.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByDateOfBirthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where dateOfBirth is greater than or equal to DEFAULT_DATE_OF_BIRTH
        defaultCustomerShouldBeFound("dateOfBirth.greaterThanOrEqual=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the customerList where dateOfBirth is greater than or equal to UPDATED_DATE_OF_BIRTH
        defaultCustomerShouldNotBeFound("dateOfBirth.greaterThanOrEqual=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllCustomersByDateOfBirthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where dateOfBirth is less than or equal to DEFAULT_DATE_OF_BIRTH
        defaultCustomerShouldBeFound("dateOfBirth.lessThanOrEqual=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the customerList where dateOfBirth is less than or equal to SMALLER_DATE_OF_BIRTH
        defaultCustomerShouldNotBeFound("dateOfBirth.lessThanOrEqual=" + SMALLER_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllCustomersByDateOfBirthIsLessThanSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where dateOfBirth is less than DEFAULT_DATE_OF_BIRTH
        defaultCustomerShouldNotBeFound("dateOfBirth.lessThan=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the customerList where dateOfBirth is less than UPDATED_DATE_OF_BIRTH
        defaultCustomerShouldBeFound("dateOfBirth.lessThan=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllCustomersByDateOfBirthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where dateOfBirth is greater than DEFAULT_DATE_OF_BIRTH
        defaultCustomerShouldNotBeFound("dateOfBirth.greaterThan=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the customerList where dateOfBirth is greater than SMALLER_DATE_OF_BIRTH
        defaultCustomerShouldBeFound("dateOfBirth.greaterThan=" + SMALLER_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllCustomersByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where gender equals to DEFAULT_GENDER
        defaultCustomerShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the customerList where gender equals to UPDATED_GENDER
        defaultCustomerShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllCustomersByGenderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where gender not equals to DEFAULT_GENDER
        defaultCustomerShouldNotBeFound("gender.notEquals=" + DEFAULT_GENDER);

        // Get all the customerList where gender not equals to UPDATED_GENDER
        defaultCustomerShouldBeFound("gender.notEquals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllCustomersByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultCustomerShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the customerList where gender equals to UPDATED_GENDER
        defaultCustomerShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllCustomersByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where gender is not null
        defaultCustomerShouldBeFound("gender.specified=true");

        // Get all the customerList where gender is null
        defaultCustomerShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByGenderContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where gender contains DEFAULT_GENDER
        defaultCustomerShouldBeFound("gender.contains=" + DEFAULT_GENDER);

        // Get all the customerList where gender contains UPDATED_GENDER
        defaultCustomerShouldNotBeFound("gender.contains=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllCustomersByGenderNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where gender does not contain DEFAULT_GENDER
        defaultCustomerShouldNotBeFound("gender.doesNotContain=" + DEFAULT_GENDER);

        // Get all the customerList where gender does not contain UPDATED_GENDER
        defaultCustomerShouldBeFound("gender.doesNotContain=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllCustomersByMaritalStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where maritalStatus equals to DEFAULT_MARITAL_STATUS
        defaultCustomerShouldBeFound("maritalStatus.equals=" + DEFAULT_MARITAL_STATUS);

        // Get all the customerList where maritalStatus equals to UPDATED_MARITAL_STATUS
        defaultCustomerShouldNotBeFound("maritalStatus.equals=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    void getAllCustomersByMaritalStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where maritalStatus not equals to DEFAULT_MARITAL_STATUS
        defaultCustomerShouldNotBeFound("maritalStatus.notEquals=" + DEFAULT_MARITAL_STATUS);

        // Get all the customerList where maritalStatus not equals to UPDATED_MARITAL_STATUS
        defaultCustomerShouldBeFound("maritalStatus.notEquals=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    void getAllCustomersByMaritalStatusIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where maritalStatus in DEFAULT_MARITAL_STATUS or UPDATED_MARITAL_STATUS
        defaultCustomerShouldBeFound("maritalStatus.in=" + DEFAULT_MARITAL_STATUS + "," + UPDATED_MARITAL_STATUS);

        // Get all the customerList where maritalStatus equals to UPDATED_MARITAL_STATUS
        defaultCustomerShouldNotBeFound("maritalStatus.in=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    void getAllCustomersByMaritalStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where maritalStatus is not null
        defaultCustomerShouldBeFound("maritalStatus.specified=true");

        // Get all the customerList where maritalStatus is null
        defaultCustomerShouldNotBeFound("maritalStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByMaritalStatusContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where maritalStatus contains DEFAULT_MARITAL_STATUS
        defaultCustomerShouldBeFound("maritalStatus.contains=" + DEFAULT_MARITAL_STATUS);

        // Get all the customerList where maritalStatus contains UPDATED_MARITAL_STATUS
        defaultCustomerShouldNotBeFound("maritalStatus.contains=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    void getAllCustomersByMaritalStatusNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where maritalStatus does not contain DEFAULT_MARITAL_STATUS
        defaultCustomerShouldNotBeFound("maritalStatus.doesNotContain=" + DEFAULT_MARITAL_STATUS);

        // Get all the customerList where maritalStatus does not contain UPDATED_MARITAL_STATUS
        defaultCustomerShouldBeFound("maritalStatus.doesNotContain=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    void getAllCustomersByNationalityIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where nationality equals to DEFAULT_NATIONALITY
        defaultCustomerShouldBeFound("nationality.equals=" + DEFAULT_NATIONALITY);

        // Get all the customerList where nationality equals to UPDATED_NATIONALITY
        defaultCustomerShouldNotBeFound("nationality.equals=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllCustomersByNationalityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where nationality not equals to DEFAULT_NATIONALITY
        defaultCustomerShouldNotBeFound("nationality.notEquals=" + DEFAULT_NATIONALITY);

        // Get all the customerList where nationality not equals to UPDATED_NATIONALITY
        defaultCustomerShouldBeFound("nationality.notEquals=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllCustomersByNationalityIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where nationality in DEFAULT_NATIONALITY or UPDATED_NATIONALITY
        defaultCustomerShouldBeFound("nationality.in=" + DEFAULT_NATIONALITY + "," + UPDATED_NATIONALITY);

        // Get all the customerList where nationality equals to UPDATED_NATIONALITY
        defaultCustomerShouldNotBeFound("nationality.in=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllCustomersByNationalityIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where nationality is not null
        defaultCustomerShouldBeFound("nationality.specified=true");

        // Get all the customerList where nationality is null
        defaultCustomerShouldNotBeFound("nationality.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByNationalityContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where nationality contains DEFAULT_NATIONALITY
        defaultCustomerShouldBeFound("nationality.contains=" + DEFAULT_NATIONALITY);

        // Get all the customerList where nationality contains UPDATED_NATIONALITY
        defaultCustomerShouldNotBeFound("nationality.contains=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllCustomersByNationalityNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where nationality does not contain DEFAULT_NATIONALITY
        defaultCustomerShouldNotBeFound("nationality.doesNotContain=" + DEFAULT_NATIONALITY);

        // Get all the customerList where nationality does not contain UPDATED_NATIONALITY
        defaultCustomerShouldBeFound("nationality.doesNotContain=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllCustomersByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where status equals to DEFAULT_STATUS
        defaultCustomerShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the customerList where status equals to UPDATED_STATUS
        defaultCustomerShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustomersByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where status not equals to DEFAULT_STATUS
        defaultCustomerShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the customerList where status not equals to UPDATED_STATUS
        defaultCustomerShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustomersByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultCustomerShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the customerList where status equals to UPDATED_STATUS
        defaultCustomerShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustomersByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where status is not null
        defaultCustomerShouldBeFound("status.specified=true");

        // Get all the customerList where status is null
        defaultCustomerShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByStatusContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where status contains DEFAULT_STATUS
        defaultCustomerShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the customerList where status contains UPDATED_STATUS
        defaultCustomerShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustomersByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where status does not contain DEFAULT_STATUS
        defaultCustomerShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the customerList where status does not contain UPDATED_STATUS
        defaultCustomerShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where customerEmail equals to DEFAULT_CUSTOMER_EMAIL
        defaultCustomerShouldBeFound("customerEmail.equals=" + DEFAULT_CUSTOMER_EMAIL);

        // Get all the customerList where customerEmail equals to UPDATED_CUSTOMER_EMAIL
        defaultCustomerShouldNotBeFound("customerEmail.equals=" + UPDATED_CUSTOMER_EMAIL);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where customerEmail not equals to DEFAULT_CUSTOMER_EMAIL
        defaultCustomerShouldNotBeFound("customerEmail.notEquals=" + DEFAULT_CUSTOMER_EMAIL);

        // Get all the customerList where customerEmail not equals to UPDATED_CUSTOMER_EMAIL
        defaultCustomerShouldBeFound("customerEmail.notEquals=" + UPDATED_CUSTOMER_EMAIL);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerEmailIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where customerEmail in DEFAULT_CUSTOMER_EMAIL or UPDATED_CUSTOMER_EMAIL
        defaultCustomerShouldBeFound("customerEmail.in=" + DEFAULT_CUSTOMER_EMAIL + "," + UPDATED_CUSTOMER_EMAIL);

        // Get all the customerList where customerEmail equals to UPDATED_CUSTOMER_EMAIL
        defaultCustomerShouldNotBeFound("customerEmail.in=" + UPDATED_CUSTOMER_EMAIL);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where customerEmail is not null
        defaultCustomerShouldBeFound("customerEmail.specified=true");

        // Get all the customerList where customerEmail is null
        defaultCustomerShouldNotBeFound("customerEmail.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerEmailContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where customerEmail contains DEFAULT_CUSTOMER_EMAIL
        defaultCustomerShouldBeFound("customerEmail.contains=" + DEFAULT_CUSTOMER_EMAIL);

        // Get all the customerList where customerEmail contains UPDATED_CUSTOMER_EMAIL
        defaultCustomerShouldNotBeFound("customerEmail.contains=" + UPDATED_CUSTOMER_EMAIL);
    }

    @Test
    @Transactional
    void getAllCustomersByCustomerEmailNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where customerEmail does not contain DEFAULT_CUSTOMER_EMAIL
        defaultCustomerShouldNotBeFound("customerEmail.doesNotContain=" + DEFAULT_CUSTOMER_EMAIL);

        // Get all the customerList where customerEmail does not contain UPDATED_CUSTOMER_EMAIL
        defaultCustomerShouldBeFound("customerEmail.doesNotContain=" + UPDATED_CUSTOMER_EMAIL);
    }

    @Test
    @Transactional
    void getAllCustomersByCompanyIdTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where companyIdType equals to DEFAULT_COMPANY_ID_TYPE
        defaultCustomerShouldBeFound("companyIdType.equals=" + DEFAULT_COMPANY_ID_TYPE);

        // Get all the customerList where companyIdType equals to UPDATED_COMPANY_ID_TYPE
        defaultCustomerShouldNotBeFound("companyIdType.equals=" + UPDATED_COMPANY_ID_TYPE);
    }

    @Test
    @Transactional
    void getAllCustomersByCompanyIdTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where companyIdType not equals to DEFAULT_COMPANY_ID_TYPE
        defaultCustomerShouldNotBeFound("companyIdType.notEquals=" + DEFAULT_COMPANY_ID_TYPE);

        // Get all the customerList where companyIdType not equals to UPDATED_COMPANY_ID_TYPE
        defaultCustomerShouldBeFound("companyIdType.notEquals=" + UPDATED_COMPANY_ID_TYPE);
    }

    @Test
    @Transactional
    void getAllCustomersByCompanyIdTypeIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where companyIdType in DEFAULT_COMPANY_ID_TYPE or UPDATED_COMPANY_ID_TYPE
        defaultCustomerShouldBeFound("companyIdType.in=" + DEFAULT_COMPANY_ID_TYPE + "," + UPDATED_COMPANY_ID_TYPE);

        // Get all the customerList where companyIdType equals to UPDATED_COMPANY_ID_TYPE
        defaultCustomerShouldNotBeFound("companyIdType.in=" + UPDATED_COMPANY_ID_TYPE);
    }

    @Test
    @Transactional
    void getAllCustomersByCompanyIdTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where companyIdType is not null
        defaultCustomerShouldBeFound("companyIdType.specified=true");

        // Get all the customerList where companyIdType is null
        defaultCustomerShouldNotBeFound("companyIdType.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByCompanyIdTypeContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where companyIdType contains DEFAULT_COMPANY_ID_TYPE
        defaultCustomerShouldBeFound("companyIdType.contains=" + DEFAULT_COMPANY_ID_TYPE);

        // Get all the customerList where companyIdType contains UPDATED_COMPANY_ID_TYPE
        defaultCustomerShouldNotBeFound("companyIdType.contains=" + UPDATED_COMPANY_ID_TYPE);
    }

    @Test
    @Transactional
    void getAllCustomersByCompanyIdTypeNotContainsSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where companyIdType does not contain DEFAULT_COMPANY_ID_TYPE
        defaultCustomerShouldNotBeFound("companyIdType.doesNotContain=" + DEFAULT_COMPANY_ID_TYPE);

        // Get all the customerList where companyIdType does not contain UPDATED_COMPANY_ID_TYPE
        defaultCustomerShouldBeFound("companyIdType.doesNotContain=" + UPDATED_COMPANY_ID_TYPE);
    }

    @Test
    @Transactional
    void getAllCustomersByCompanyIdIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where companyId equals to DEFAULT_COMPANY_ID
        defaultCustomerShouldBeFound("companyId.equals=" + DEFAULT_COMPANY_ID);

        // Get all the customerList where companyId equals to UPDATED_COMPANY_ID
        defaultCustomerShouldNotBeFound("companyId.equals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllCustomersByCompanyIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where companyId not equals to DEFAULT_COMPANY_ID
        defaultCustomerShouldNotBeFound("companyId.notEquals=" + DEFAULT_COMPANY_ID);

        // Get all the customerList where companyId not equals to UPDATED_COMPANY_ID
        defaultCustomerShouldBeFound("companyId.notEquals=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllCustomersByCompanyIdIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where companyId in DEFAULT_COMPANY_ID or UPDATED_COMPANY_ID
        defaultCustomerShouldBeFound("companyId.in=" + DEFAULT_COMPANY_ID + "," + UPDATED_COMPANY_ID);

        // Get all the customerList where companyId equals to UPDATED_COMPANY_ID
        defaultCustomerShouldNotBeFound("companyId.in=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllCustomersByCompanyIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where companyId is not null
        defaultCustomerShouldBeFound("companyId.specified=true");

        // Get all the customerList where companyId is null
        defaultCustomerShouldNotBeFound("companyId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByCompanyIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where companyId is greater than or equal to DEFAULT_COMPANY_ID
        defaultCustomerShouldBeFound("companyId.greaterThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the customerList where companyId is greater than or equal to UPDATED_COMPANY_ID
        defaultCustomerShouldNotBeFound("companyId.greaterThanOrEqual=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllCustomersByCompanyIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where companyId is less than or equal to DEFAULT_COMPANY_ID
        defaultCustomerShouldBeFound("companyId.lessThanOrEqual=" + DEFAULT_COMPANY_ID);

        // Get all the customerList where companyId is less than or equal to SMALLER_COMPANY_ID
        defaultCustomerShouldNotBeFound("companyId.lessThanOrEqual=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllCustomersByCompanyIdIsLessThanSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where companyId is less than DEFAULT_COMPANY_ID
        defaultCustomerShouldNotBeFound("companyId.lessThan=" + DEFAULT_COMPANY_ID);

        // Get all the customerList where companyId is less than UPDATED_COMPANY_ID
        defaultCustomerShouldBeFound("companyId.lessThan=" + UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllCustomersByCompanyIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where companyId is greater than DEFAULT_COMPANY_ID
        defaultCustomerShouldNotBeFound("companyId.greaterThan=" + DEFAULT_COMPANY_ID);

        // Get all the customerList where companyId is greater than SMALLER_COMPANY_ID
        defaultCustomerShouldBeFound("companyId.greaterThan=" + SMALLER_COMPANY_ID);
    }

    @Test
    @Transactional
    void getAllCustomersByPrimaryConAdminIndIdIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where primaryConAdminIndId equals to DEFAULT_PRIMARY_CON_ADMIN_IND_ID
        defaultCustomerShouldBeFound("primaryConAdminIndId.equals=" + DEFAULT_PRIMARY_CON_ADMIN_IND_ID);

        // Get all the customerList where primaryConAdminIndId equals to UPDATED_PRIMARY_CON_ADMIN_IND_ID
        defaultCustomerShouldNotBeFound("primaryConAdminIndId.equals=" + UPDATED_PRIMARY_CON_ADMIN_IND_ID);
    }

    @Test
    @Transactional
    void getAllCustomersByPrimaryConAdminIndIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where primaryConAdminIndId not equals to DEFAULT_PRIMARY_CON_ADMIN_IND_ID
        defaultCustomerShouldNotBeFound("primaryConAdminIndId.notEquals=" + DEFAULT_PRIMARY_CON_ADMIN_IND_ID);

        // Get all the customerList where primaryConAdminIndId not equals to UPDATED_PRIMARY_CON_ADMIN_IND_ID
        defaultCustomerShouldBeFound("primaryConAdminIndId.notEquals=" + UPDATED_PRIMARY_CON_ADMIN_IND_ID);
    }

    @Test
    @Transactional
    void getAllCustomersByPrimaryConAdminIndIdIsInShouldWork() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where primaryConAdminIndId in DEFAULT_PRIMARY_CON_ADMIN_IND_ID or UPDATED_PRIMARY_CON_ADMIN_IND_ID
        defaultCustomerShouldBeFound(
            "primaryConAdminIndId.in=" + DEFAULT_PRIMARY_CON_ADMIN_IND_ID + "," + UPDATED_PRIMARY_CON_ADMIN_IND_ID
        );

        // Get all the customerList where primaryConAdminIndId equals to UPDATED_PRIMARY_CON_ADMIN_IND_ID
        defaultCustomerShouldNotBeFound("primaryConAdminIndId.in=" + UPDATED_PRIMARY_CON_ADMIN_IND_ID);
    }

    @Test
    @Transactional
    void getAllCustomersByPrimaryConAdminIndIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where primaryConAdminIndId is not null
        defaultCustomerShouldBeFound("primaryConAdminIndId.specified=true");

        // Get all the customerList where primaryConAdminIndId is null
        defaultCustomerShouldNotBeFound("primaryConAdminIndId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustomersByPrimaryConAdminIndIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where primaryConAdminIndId is greater than or equal to DEFAULT_PRIMARY_CON_ADMIN_IND_ID
        defaultCustomerShouldBeFound("primaryConAdminIndId.greaterThanOrEqual=" + DEFAULT_PRIMARY_CON_ADMIN_IND_ID);

        // Get all the customerList where primaryConAdminIndId is greater than or equal to UPDATED_PRIMARY_CON_ADMIN_IND_ID
        defaultCustomerShouldNotBeFound("primaryConAdminIndId.greaterThanOrEqual=" + UPDATED_PRIMARY_CON_ADMIN_IND_ID);
    }

    @Test
    @Transactional
    void getAllCustomersByPrimaryConAdminIndIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where primaryConAdminIndId is less than or equal to DEFAULT_PRIMARY_CON_ADMIN_IND_ID
        defaultCustomerShouldBeFound("primaryConAdminIndId.lessThanOrEqual=" + DEFAULT_PRIMARY_CON_ADMIN_IND_ID);

        // Get all the customerList where primaryConAdminIndId is less than or equal to SMALLER_PRIMARY_CON_ADMIN_IND_ID
        defaultCustomerShouldNotBeFound("primaryConAdminIndId.lessThanOrEqual=" + SMALLER_PRIMARY_CON_ADMIN_IND_ID);
    }

    @Test
    @Transactional
    void getAllCustomersByPrimaryConAdminIndIdIsLessThanSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where primaryConAdminIndId is less than DEFAULT_PRIMARY_CON_ADMIN_IND_ID
        defaultCustomerShouldNotBeFound("primaryConAdminIndId.lessThan=" + DEFAULT_PRIMARY_CON_ADMIN_IND_ID);

        // Get all the customerList where primaryConAdminIndId is less than UPDATED_PRIMARY_CON_ADMIN_IND_ID
        defaultCustomerShouldBeFound("primaryConAdminIndId.lessThan=" + UPDATED_PRIMARY_CON_ADMIN_IND_ID);
    }

    @Test
    @Transactional
    void getAllCustomersByPrimaryConAdminIndIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList where primaryConAdminIndId is greater than DEFAULT_PRIMARY_CON_ADMIN_IND_ID
        defaultCustomerShouldNotBeFound("primaryConAdminIndId.greaterThan=" + DEFAULT_PRIMARY_CON_ADMIN_IND_ID);

        // Get all the customerList where primaryConAdminIndId is greater than SMALLER_PRIMARY_CON_ADMIN_IND_ID
        defaultCustomerShouldBeFound("primaryConAdminIndId.greaterThan=" + SMALLER_PRIMARY_CON_ADMIN_IND_ID);
    }

    @Test
    @Transactional
    void getAllCustomersByCustBillingAccIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);
        CustBillingAcc custBillingAcc = CustBillingAccResourceIT.createEntity(em);
        em.persist(custBillingAcc);
        em.flush();
        customer.setCustBillingAcc(custBillingAcc);
        customerRepository.saveAndFlush(customer);
        Long custBillingAccId = custBillingAcc.getId();

        // Get all the customerList where custBillingAcc equals to custBillingAccId
        defaultCustomerShouldBeFound("custBillingAccId.equals=" + custBillingAccId);

        // Get all the customerList where custBillingAcc equals to (custBillingAccId + 1)
        defaultCustomerShouldNotBeFound("custBillingAccId.equals=" + (custBillingAccId + 1));
    }

    @Test
    @Transactional
    void getAllCustomersByCustCreditProfileIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);
        CustCreditProfile custCreditProfile = CustCreditProfileResourceIT.createEntity(em);
        em.persist(custCreditProfile);
        em.flush();
        customer.setCustCreditProfile(custCreditProfile);
        customerRepository.saveAndFlush(customer);
        Long custCreditProfileId = custCreditProfile.getId();

        // Get all the customerList where custCreditProfile equals to custCreditProfileId
        defaultCustomerShouldBeFound("custCreditProfileId.equals=" + custCreditProfileId);

        // Get all the customerList where custCreditProfile equals to (custCreditProfileId + 1)
        defaultCustomerShouldNotBeFound("custCreditProfileId.equals=" + (custCreditProfileId + 1));
    }

    @Test
    @Transactional
    void getAllCustomersByCustBillingRefIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);
        CustBillingRef custBillingRef = CustBillingRefResourceIT.createEntity(em);
        em.persist(custBillingRef);
        em.flush();
        customer.setCustBillingRef(custBillingRef);
        customerRepository.saveAndFlush(customer);
        Long custBillingRefId = custBillingRef.getId();

        // Get all the customerList where custBillingRef equals to custBillingRefId
        defaultCustomerShouldBeFound("custBillingRefId.equals=" + custBillingRefId);

        // Get all the customerList where custBillingRef equals to (custBillingRefId + 1)
        defaultCustomerShouldNotBeFound("custBillingRefId.equals=" + (custBillingRefId + 1));
    }

    @Test
    @Transactional
    void getAllCustomersByCustContactIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);
        CustContact custContact = CustContactResourceIT.createEntity(em);
        em.persist(custContact);
        em.flush();
        customer.addCustContact(custContact);
        customerRepository.saveAndFlush(customer);
        Long custContactId = custContact.getId();

        // Get all the customerList where custContact equals to custContactId
        defaultCustomerShouldBeFound("custContactId.equals=" + custContactId);

        // Get all the customerList where custContact equals to (custContactId + 1)
        defaultCustomerShouldNotBeFound("custContactId.equals=" + (custContactId + 1));
    }

    @Test
    @Transactional
    void getAllCustomersByCustStatisticsIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);
        CustStatistics custStatistics = CustStatisticsResourceIT.createEntity(em);
        em.persist(custStatistics);
        em.flush();
        customer.addCustStatistics(custStatistics);
        customerRepository.saveAndFlush(customer);
        Long custStatisticsId = custStatistics.getId();

        // Get all the customerList where custStatistics equals to custStatisticsId
        defaultCustomerShouldBeFound("custStatisticsId.equals=" + custStatisticsId);

        // Get all the customerList where custStatistics equals to (custStatisticsId + 1)
        defaultCustomerShouldNotBeFound("custStatisticsId.equals=" + (custStatisticsId + 1));
    }

    @Test
    @Transactional
    void getAllCustomersByCustCharIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);
        CustChar custChar = CustCharResourceIT.createEntity(em);
        em.persist(custChar);
        em.flush();
        customer.addCustChar(custChar);
        customerRepository.saveAndFlush(customer);
        Long custCharId = custChar.getId();

        // Get all the customerList where custChar equals to custCharId
        defaultCustomerShouldBeFound("custCharId.equals=" + custCharId);

        // Get all the customerList where custChar equals to (custCharId + 1)
        defaultCustomerShouldNotBeFound("custCharId.equals=" + (custCharId + 1));
    }

    @Test
    @Transactional
    void getAllCustomersByCustCommunicationRefIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);
        CustCommunicationRef custCommunicationRef = CustCommunicationRefResourceIT.createEntity(em);
        em.persist(custCommunicationRef);
        em.flush();
        customer.addCustCommunicationRef(custCommunicationRef);
        customerRepository.saveAndFlush(customer);
        Long custCommunicationRefId = custCommunicationRef.getId();

        // Get all the customerList where custCommunicationRef equals to custCommunicationRefId
        defaultCustomerShouldBeFound("custCommunicationRefId.equals=" + custCommunicationRefId);

        // Get all the customerList where custCommunicationRef equals to (custCommunicationRefId + 1)
        defaultCustomerShouldNotBeFound("custCommunicationRefId.equals=" + (custCommunicationRefId + 1));
    }

    @Test
    @Transactional
    void getAllCustomersByCustPasswordCharIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);
        CustPasswordChar custPasswordChar = CustPasswordCharResourceIT.createEntity(em);
        em.persist(custPasswordChar);
        em.flush();
        customer.addCustPasswordChar(custPasswordChar);
        customerRepository.saveAndFlush(customer);
        Long custPasswordCharId = custPasswordChar.getId();

        // Get all the customerList where custPasswordChar equals to custPasswordCharId
        defaultCustomerShouldBeFound("custPasswordCharId.equals=" + custPasswordCharId);

        // Get all the customerList where custPasswordChar equals to (custPasswordCharId + 1)
        defaultCustomerShouldNotBeFound("custPasswordCharId.equals=" + (custPasswordCharId + 1));
    }

    @Test
    @Transactional
    void getAllCustomersByCustNewsLetterConfigIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);
        CustNewsLetterConfig custNewsLetterConfig = CustNewsLetterConfigResourceIT.createEntity(em);
        em.persist(custNewsLetterConfig);
        em.flush();
        customer.addCustNewsLetterConfig(custNewsLetterConfig);
        customerRepository.saveAndFlush(customer);
        Long custNewsLetterConfigId = custNewsLetterConfig.getId();

        // Get all the customerList where custNewsLetterConfig equals to custNewsLetterConfigId
        defaultCustomerShouldBeFound("custNewsLetterConfigId.equals=" + custNewsLetterConfigId);

        // Get all the customerList where custNewsLetterConfig equals to (custNewsLetterConfigId + 1)
        defaultCustomerShouldNotBeFound("custNewsLetterConfigId.equals=" + (custNewsLetterConfigId + 1));
    }

    @Test
    @Transactional
    void getAllCustomersByCustSecurityCharIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);
        CustSecurityChar custSecurityChar = CustSecurityCharResourceIT.createEntity(em);
        em.persist(custSecurityChar);
        em.flush();
        customer.addCustSecurityChar(custSecurityChar);
        customerRepository.saveAndFlush(customer);
        Long custSecurityCharId = custSecurityChar.getId();

        // Get all the customerList where custSecurityChar equals to custSecurityCharId
        defaultCustomerShouldBeFound("custSecurityCharId.equals=" + custSecurityCharId);

        // Get all the customerList where custSecurityChar equals to (custSecurityCharId + 1)
        defaultCustomerShouldNotBeFound("custSecurityCharId.equals=" + (custSecurityCharId + 1));
    }

    @Test
    @Transactional
    void getAllCustomersByCustRelPartyIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);
        CustRelParty custRelParty = CustRelPartyResourceIT.createEntity(em);
        em.persist(custRelParty);
        em.flush();
        customer.addCustRelParty(custRelParty);
        customerRepository.saveAndFlush(customer);
        Long custRelPartyId = custRelParty.getId();

        // Get all the customerList where custRelParty equals to custRelPartyId
        defaultCustomerShouldBeFound("custRelPartyId.equals=" + custRelPartyId);

        // Get all the customerList where custRelParty equals to (custRelPartyId + 1)
        defaultCustomerShouldNotBeFound("custRelPartyId.equals=" + (custRelPartyId + 1));
    }

    @Test
    @Transactional
    void getAllCustomersByCustISVRefIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);
        CustISVRef custISVRef = CustISVRefResourceIT.createEntity(em);
        em.persist(custISVRef);
        em.flush();
        customer.addCustISVRef(custISVRef);
        customerRepository.saveAndFlush(customer);
        Long custISVRefId = custISVRef.getId();

        // Get all the customerList where custISVRef equals to custISVRefId
        defaultCustomerShouldBeFound("custISVRefId.equals=" + custISVRefId);

        // Get all the customerList where custISVRef equals to (custISVRefId + 1)
        defaultCustomerShouldNotBeFound("custISVRefId.equals=" + (custISVRefId + 1));
    }

    @Test
    @Transactional
    void getAllCustomersByShoppingSessionRefIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);
        ShoppingSessionRef shoppingSessionRef = ShoppingSessionRefResourceIT.createEntity(em);
        em.persist(shoppingSessionRef);
        em.flush();
        customer.addShoppingSessionRef(shoppingSessionRef);
        customerRepository.saveAndFlush(customer);
        Long shoppingSessionRefId = shoppingSessionRef.getId();

        // Get all the customerList where shoppingSessionRef equals to shoppingSessionRefId
        defaultCustomerShouldBeFound("shoppingSessionRefId.equals=" + shoppingSessionRefId);

        // Get all the customerList where shoppingSessionRef equals to (shoppingSessionRefId + 1)
        defaultCustomerShouldNotBeFound("shoppingSessionRefId.equals=" + (shoppingSessionRefId + 1));
    }

    @Test
    @Transactional
    void getAllCustomersByIndustryIsEqualToSomething() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);
        Industry industry = IndustryResourceIT.createEntity(em);
        em.persist(industry);
        em.flush();
        customer.setIndustry(industry);
        industry.setCustomer(customer);
        customerRepository.saveAndFlush(customer);
        Long industryId = industry.getId();

        // Get all the customerList where industry equals to industryId
        defaultCustomerShouldBeFound("industryId.equals=" + industryId);

        // Get all the customerList where industry equals to (industryId + 1)
        defaultCustomerShouldNotBeFound("industryId.equals=" + (industryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustomerShouldBeFound(String filter) throws Exception {
        restCustomerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].formattedName").value(hasItem(DEFAULT_FORMATTED_NAME)))
            .andExpect(jsonPath("$.[*].tradingName").value(hasItem(DEFAULT_TRADING_NAME)))
            .andExpect(jsonPath("$.[*].custType").value(hasItem(DEFAULT_CUST_TYPE)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].customerEmail").value(hasItem(DEFAULT_CUSTOMER_EMAIL)))
            .andExpect(jsonPath("$.[*].companyIdType").value(hasItem(DEFAULT_COMPANY_ID_TYPE)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].primaryConAdminIndId").value(hasItem(DEFAULT_PRIMARY_CON_ADMIN_IND_ID.intValue())));

        // Check, that the count call also returns 1
        restCustomerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustomerShouldNotBeFound(String filter) throws Exception {
        restCustomerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustomerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustomer() throws Exception {
        // Get the customer
        restCustomerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Update the customer
        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        // Disconnect from session so that the updates on updatedCustomer are not directly saved in db
        em.detach(updatedCustomer);
        updatedCustomer
            .name(UPDATED_NAME)
            .formattedName(UPDATED_FORMATTED_NAME)
            .tradingName(UPDATED_TRADING_NAME)
            .custType(UPDATED_CUST_TYPE)
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .gender(UPDATED_GENDER)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .nationality(UPDATED_NATIONALITY)
            .status(UPDATED_STATUS)
            .customerEmail(UPDATED_CUSTOMER_EMAIL)
            .companyIdType(UPDATED_COMPANY_ID_TYPE)
            .companyId(UPDATED_COMPANY_ID)
            .primaryConAdminIndId(UPDATED_PRIMARY_CON_ADMIN_IND_ID);
        CustomerDTO customerDTO = customerMapper.toDto(updatedCustomer);

        restCustomerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomer.getFormattedName()).isEqualTo(UPDATED_FORMATTED_NAME);
        assertThat(testCustomer.getTradingName()).isEqualTo(UPDATED_TRADING_NAME);
        assertThat(testCustomer.getCustType()).isEqualTo(UPDATED_CUST_TYPE);
        assertThat(testCustomer.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCustomer.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCustomer.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCustomer.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testCustomer.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testCustomer.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testCustomer.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testCustomer.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testCustomer.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustomer.getCustomerEmail()).isEqualTo(UPDATED_CUSTOMER_EMAIL);
        assertThat(testCustomer.getCompanyIdType()).isEqualTo(UPDATED_COMPANY_ID_TYPE);
        assertThat(testCustomer.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testCustomer.getPrimaryConAdminIndId()).isEqualTo(UPDATED_PRIMARY_CON_ADMIN_IND_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();
        customer.setId(count.incrementAndGet());

        // Create the Customer
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, customerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();
        customer.setId(count.incrementAndGet());

        // Create the Customer
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(customerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();
        customer.setId(count.incrementAndGet());

        // Create the Customer
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(customerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustomerWithPatch() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Update the customer using partial update
        Customer partialUpdatedCustomer = new Customer();
        partialUpdatedCustomer.setId(customer.getId());

        partialUpdatedCustomer
            .name(UPDATED_NAME)
            .title(UPDATED_TITLE)
            .middleName(UPDATED_MIDDLE_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .status(UPDATED_STATUS)
            .companyId(UPDATED_COMPANY_ID);

        restCustomerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomer))
            )
            .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomer.getFormattedName()).isEqualTo(DEFAULT_FORMATTED_NAME);
        assertThat(testCustomer.getTradingName()).isEqualTo(DEFAULT_TRADING_NAME);
        assertThat(testCustomer.getCustType()).isEqualTo(DEFAULT_CUST_TYPE);
        assertThat(testCustomer.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCustomer.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCustomer.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCustomer.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testCustomer.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testCustomer.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testCustomer.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testCustomer.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testCustomer.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustomer.getCustomerEmail()).isEqualTo(DEFAULT_CUSTOMER_EMAIL);
        assertThat(testCustomer.getCompanyIdType()).isEqualTo(DEFAULT_COMPANY_ID_TYPE);
        assertThat(testCustomer.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testCustomer.getPrimaryConAdminIndId()).isEqualTo(DEFAULT_PRIMARY_CON_ADMIN_IND_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustomerWithPatch() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Update the customer using partial update
        Customer partialUpdatedCustomer = new Customer();
        partialUpdatedCustomer.setId(customer.getId());

        partialUpdatedCustomer
            .name(UPDATED_NAME)
            .formattedName(UPDATED_FORMATTED_NAME)
            .tradingName(UPDATED_TRADING_NAME)
            .custType(UPDATED_CUST_TYPE)
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .gender(UPDATED_GENDER)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .nationality(UPDATED_NATIONALITY)
            .status(UPDATED_STATUS)
            .customerEmail(UPDATED_CUSTOMER_EMAIL)
            .companyIdType(UPDATED_COMPANY_ID_TYPE)
            .companyId(UPDATED_COMPANY_ID)
            .primaryConAdminIndId(UPDATED_PRIMARY_CON_ADMIN_IND_ID);

        restCustomerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustomer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustomer))
            )
            .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomer.getFormattedName()).isEqualTo(UPDATED_FORMATTED_NAME);
        assertThat(testCustomer.getTradingName()).isEqualTo(UPDATED_TRADING_NAME);
        assertThat(testCustomer.getCustType()).isEqualTo(UPDATED_CUST_TYPE);
        assertThat(testCustomer.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCustomer.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCustomer.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCustomer.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testCustomer.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testCustomer.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testCustomer.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testCustomer.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testCustomer.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustomer.getCustomerEmail()).isEqualTo(UPDATED_CUSTOMER_EMAIL);
        assertThat(testCustomer.getCompanyIdType()).isEqualTo(UPDATED_COMPANY_ID_TYPE);
        assertThat(testCustomer.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testCustomer.getPrimaryConAdminIndId()).isEqualTo(UPDATED_PRIMARY_CON_ADMIN_IND_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();
        customer.setId(count.incrementAndGet());

        // Create the Customer
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, customerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();
        customer.setId(count.incrementAndGet());

        // Create the Customer
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(customerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();
        customer.setId(count.incrementAndGet());

        // Create the Customer
        CustomerDTO customerDTO = customerMapper.toDto(customer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustomerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(customerDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        int databaseSizeBeforeDelete = customerRepository.findAll().size();

        // Delete the customer
        restCustomerMockMvc
            .perform(delete(ENTITY_API_URL_ID, customer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
