package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.CustCommunicationRef;
import com.apptium.customer.domain.Customer;
import com.apptium.customer.repository.CustCommunicationRefRepository;
import com.apptium.customer.service.criteria.CustCommunicationRefCriteria;
import com.apptium.customer.service.dto.CustCommunicationRefDTO;
import com.apptium.customer.service.mapper.CustCommunicationRefMapper;
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
 * Integration tests for the {@link CustCommunicationRefResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CustCommunicationRefResourceIT {

    private static final String DEFAULT_CUSTOMER_NOTIFICATION_ID = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_NOTIFICATION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;
    private static final Long SMALLER_CUSTOMER_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/cust-communication-refs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CustCommunicationRefRepository custCommunicationRefRepository;

    @Autowired
    private CustCommunicationRefMapper custCommunicationRefMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustCommunicationRefMockMvc;

    private CustCommunicationRef custCommunicationRef;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustCommunicationRef createEntity(EntityManager em) {
        CustCommunicationRef custCommunicationRef = new CustCommunicationRef()
            .customerNotificationId(DEFAULT_CUSTOMER_NOTIFICATION_ID)
            .role(DEFAULT_ROLE)
            .status(DEFAULT_STATUS)
            .customerId(DEFAULT_CUSTOMER_ID);
        return custCommunicationRef;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustCommunicationRef createUpdatedEntity(EntityManager em) {
        CustCommunicationRef custCommunicationRef = new CustCommunicationRef()
            .customerNotificationId(UPDATED_CUSTOMER_NOTIFICATION_ID)
            .role(UPDATED_ROLE)
            .status(UPDATED_STATUS)
            .customerId(UPDATED_CUSTOMER_ID);
        return custCommunicationRef;
    }

    @BeforeEach
    public void initTest() {
        custCommunicationRef = createEntity(em);
    }

    @Test
    @Transactional
    void createCustCommunicationRef() throws Exception {
        int databaseSizeBeforeCreate = custCommunicationRefRepository.findAll().size();
        // Create the CustCommunicationRef
        CustCommunicationRefDTO custCommunicationRefDTO = custCommunicationRefMapper.toDto(custCommunicationRef);
        restCustCommunicationRefMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCommunicationRefDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CustCommunicationRef in the database
        List<CustCommunicationRef> custCommunicationRefList = custCommunicationRefRepository.findAll();
        assertThat(custCommunicationRefList).hasSize(databaseSizeBeforeCreate + 1);
        CustCommunicationRef testCustCommunicationRef = custCommunicationRefList.get(custCommunicationRefList.size() - 1);
        assertThat(testCustCommunicationRef.getCustomerNotificationId()).isEqualTo(DEFAULT_CUSTOMER_NOTIFICATION_ID);
        assertThat(testCustCommunicationRef.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testCustCommunicationRef.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCustCommunicationRef.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void createCustCommunicationRefWithExistingId() throws Exception {
        // Create the CustCommunicationRef with an existing ID
        custCommunicationRef.setId(1L);
        CustCommunicationRefDTO custCommunicationRefDTO = custCommunicationRefMapper.toDto(custCommunicationRef);

        int databaseSizeBeforeCreate = custCommunicationRefRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustCommunicationRefMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCommunicationRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustCommunicationRef in the database
        List<CustCommunicationRef> custCommunicationRefList = custCommunicationRefRepository.findAll();
        assertThat(custCommunicationRefList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCustomerNotificationIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custCommunicationRefRepository.findAll().size();
        // set the field null
        custCommunicationRef.setCustomerNotificationId(null);

        // Create the CustCommunicationRef, which fails.
        CustCommunicationRefDTO custCommunicationRefDTO = custCommunicationRefMapper.toDto(custCommunicationRef);

        restCustCommunicationRefMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCommunicationRefDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustCommunicationRef> custCommunicationRefList = custCommunicationRefRepository.findAll();
        assertThat(custCommunicationRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRoleIsRequired() throws Exception {
        int databaseSizeBeforeTest = custCommunicationRefRepository.findAll().size();
        // set the field null
        custCommunicationRef.setRole(null);

        // Create the CustCommunicationRef, which fails.
        CustCommunicationRefDTO custCommunicationRefDTO = custCommunicationRefMapper.toDto(custCommunicationRef);

        restCustCommunicationRefMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCommunicationRefDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustCommunicationRef> custCommunicationRefList = custCommunicationRefRepository.findAll();
        assertThat(custCommunicationRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = custCommunicationRefRepository.findAll().size();
        // set the field null
        custCommunicationRef.setCustomerId(null);

        // Create the CustCommunicationRef, which fails.
        CustCommunicationRefDTO custCommunicationRefDTO = custCommunicationRefMapper.toDto(custCommunicationRef);

        restCustCommunicationRefMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCommunicationRefDTO))
            )
            .andExpect(status().isBadRequest());

        List<CustCommunicationRef> custCommunicationRefList = custCommunicationRefRepository.findAll();
        assertThat(custCommunicationRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCustCommunicationRefs() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        // Get all the custCommunicationRefList
        restCustCommunicationRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custCommunicationRef.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerNotificationId").value(hasItem(DEFAULT_CUSTOMER_NOTIFICATION_ID)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())));
    }

    @Test
    @Transactional
    void getCustCommunicationRef() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        // Get the custCommunicationRef
        restCustCommunicationRefMockMvc
            .perform(get(ENTITY_API_URL_ID, custCommunicationRef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(custCommunicationRef.getId().intValue()))
            .andExpect(jsonPath("$.customerNotificationId").value(DEFAULT_CUSTOMER_NOTIFICATION_ID))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()));
    }

    @Test
    @Transactional
    void getCustCommunicationRefsByIdFiltering() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        Long id = custCommunicationRef.getId();

        defaultCustCommunicationRefShouldBeFound("id.equals=" + id);
        defaultCustCommunicationRefShouldNotBeFound("id.notEquals=" + id);

        defaultCustCommunicationRefShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustCommunicationRefShouldNotBeFound("id.greaterThan=" + id);

        defaultCustCommunicationRefShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustCommunicationRefShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCustCommunicationRefsByCustomerNotificationIdIsEqualToSomething() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        // Get all the custCommunicationRefList where customerNotificationId equals to DEFAULT_CUSTOMER_NOTIFICATION_ID
        defaultCustCommunicationRefShouldBeFound("customerNotificationId.equals=" + DEFAULT_CUSTOMER_NOTIFICATION_ID);

        // Get all the custCommunicationRefList where customerNotificationId equals to UPDATED_CUSTOMER_NOTIFICATION_ID
        defaultCustCommunicationRefShouldNotBeFound("customerNotificationId.equals=" + UPDATED_CUSTOMER_NOTIFICATION_ID);
    }

    @Test
    @Transactional
    void getAllCustCommunicationRefsByCustomerNotificationIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        // Get all the custCommunicationRefList where customerNotificationId not equals to DEFAULT_CUSTOMER_NOTIFICATION_ID
        defaultCustCommunicationRefShouldNotBeFound("customerNotificationId.notEquals=" + DEFAULT_CUSTOMER_NOTIFICATION_ID);

        // Get all the custCommunicationRefList where customerNotificationId not equals to UPDATED_CUSTOMER_NOTIFICATION_ID
        defaultCustCommunicationRefShouldBeFound("customerNotificationId.notEquals=" + UPDATED_CUSTOMER_NOTIFICATION_ID);
    }

    @Test
    @Transactional
    void getAllCustCommunicationRefsByCustomerNotificationIdIsInShouldWork() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        // Get all the custCommunicationRefList where customerNotificationId in DEFAULT_CUSTOMER_NOTIFICATION_ID or UPDATED_CUSTOMER_NOTIFICATION_ID
        defaultCustCommunicationRefShouldBeFound(
            "customerNotificationId.in=" + DEFAULT_CUSTOMER_NOTIFICATION_ID + "," + UPDATED_CUSTOMER_NOTIFICATION_ID
        );

        // Get all the custCommunicationRefList where customerNotificationId equals to UPDATED_CUSTOMER_NOTIFICATION_ID
        defaultCustCommunicationRefShouldNotBeFound("customerNotificationId.in=" + UPDATED_CUSTOMER_NOTIFICATION_ID);
    }

    @Test
    @Transactional
    void getAllCustCommunicationRefsByCustomerNotificationIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        // Get all the custCommunicationRefList where customerNotificationId is not null
        defaultCustCommunicationRefShouldBeFound("customerNotificationId.specified=true");

        // Get all the custCommunicationRefList where customerNotificationId is null
        defaultCustCommunicationRefShouldNotBeFound("customerNotificationId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustCommunicationRefsByCustomerNotificationIdContainsSomething() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        // Get all the custCommunicationRefList where customerNotificationId contains DEFAULT_CUSTOMER_NOTIFICATION_ID
        defaultCustCommunicationRefShouldBeFound("customerNotificationId.contains=" + DEFAULT_CUSTOMER_NOTIFICATION_ID);

        // Get all the custCommunicationRefList where customerNotificationId contains UPDATED_CUSTOMER_NOTIFICATION_ID
        defaultCustCommunicationRefShouldNotBeFound("customerNotificationId.contains=" + UPDATED_CUSTOMER_NOTIFICATION_ID);
    }

    @Test
    @Transactional
    void getAllCustCommunicationRefsByCustomerNotificationIdNotContainsSomething() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        // Get all the custCommunicationRefList where customerNotificationId does not contain DEFAULT_CUSTOMER_NOTIFICATION_ID
        defaultCustCommunicationRefShouldNotBeFound("customerNotificationId.doesNotContain=" + DEFAULT_CUSTOMER_NOTIFICATION_ID);

        // Get all the custCommunicationRefList where customerNotificationId does not contain UPDATED_CUSTOMER_NOTIFICATION_ID
        defaultCustCommunicationRefShouldBeFound("customerNotificationId.doesNotContain=" + UPDATED_CUSTOMER_NOTIFICATION_ID);
    }

    @Test
    @Transactional
    void getAllCustCommunicationRefsByRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        // Get all the custCommunicationRefList where role equals to DEFAULT_ROLE
        defaultCustCommunicationRefShouldBeFound("role.equals=" + DEFAULT_ROLE);

        // Get all the custCommunicationRefList where role equals to UPDATED_ROLE
        defaultCustCommunicationRefShouldNotBeFound("role.equals=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllCustCommunicationRefsByRoleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        // Get all the custCommunicationRefList where role not equals to DEFAULT_ROLE
        defaultCustCommunicationRefShouldNotBeFound("role.notEquals=" + DEFAULT_ROLE);

        // Get all the custCommunicationRefList where role not equals to UPDATED_ROLE
        defaultCustCommunicationRefShouldBeFound("role.notEquals=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllCustCommunicationRefsByRoleIsInShouldWork() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        // Get all the custCommunicationRefList where role in DEFAULT_ROLE or UPDATED_ROLE
        defaultCustCommunicationRefShouldBeFound("role.in=" + DEFAULT_ROLE + "," + UPDATED_ROLE);

        // Get all the custCommunicationRefList where role equals to UPDATED_ROLE
        defaultCustCommunicationRefShouldNotBeFound("role.in=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllCustCommunicationRefsByRoleIsNullOrNotNull() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        // Get all the custCommunicationRefList where role is not null
        defaultCustCommunicationRefShouldBeFound("role.specified=true");

        // Get all the custCommunicationRefList where role is null
        defaultCustCommunicationRefShouldNotBeFound("role.specified=false");
    }

    @Test
    @Transactional
    void getAllCustCommunicationRefsByRoleContainsSomething() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        // Get all the custCommunicationRefList where role contains DEFAULT_ROLE
        defaultCustCommunicationRefShouldBeFound("role.contains=" + DEFAULT_ROLE);

        // Get all the custCommunicationRefList where role contains UPDATED_ROLE
        defaultCustCommunicationRefShouldNotBeFound("role.contains=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllCustCommunicationRefsByRoleNotContainsSomething() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        // Get all the custCommunicationRefList where role does not contain DEFAULT_ROLE
        defaultCustCommunicationRefShouldNotBeFound("role.doesNotContain=" + DEFAULT_ROLE);

        // Get all the custCommunicationRefList where role does not contain UPDATED_ROLE
        defaultCustCommunicationRefShouldBeFound("role.doesNotContain=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllCustCommunicationRefsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        // Get all the custCommunicationRefList where status equals to DEFAULT_STATUS
        defaultCustCommunicationRefShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the custCommunicationRefList where status equals to UPDATED_STATUS
        defaultCustCommunicationRefShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustCommunicationRefsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        // Get all the custCommunicationRefList where status not equals to DEFAULT_STATUS
        defaultCustCommunicationRefShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the custCommunicationRefList where status not equals to UPDATED_STATUS
        defaultCustCommunicationRefShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustCommunicationRefsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        // Get all the custCommunicationRefList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultCustCommunicationRefShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the custCommunicationRefList where status equals to UPDATED_STATUS
        defaultCustCommunicationRefShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustCommunicationRefsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        // Get all the custCommunicationRefList where status is not null
        defaultCustCommunicationRefShouldBeFound("status.specified=true");

        // Get all the custCommunicationRefList where status is null
        defaultCustCommunicationRefShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllCustCommunicationRefsByStatusContainsSomething() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        // Get all the custCommunicationRefList where status contains DEFAULT_STATUS
        defaultCustCommunicationRefShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the custCommunicationRefList where status contains UPDATED_STATUS
        defaultCustCommunicationRefShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustCommunicationRefsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        // Get all the custCommunicationRefList where status does not contain DEFAULT_STATUS
        defaultCustCommunicationRefShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the custCommunicationRefList where status does not contain UPDATED_STATUS
        defaultCustCommunicationRefShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllCustCommunicationRefsByCustomerIdIsEqualToSomething() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        // Get all the custCommunicationRefList where customerId equals to DEFAULT_CUSTOMER_ID
        defaultCustCommunicationRefShouldBeFound("customerId.equals=" + DEFAULT_CUSTOMER_ID);

        // Get all the custCommunicationRefList where customerId equals to UPDATED_CUSTOMER_ID
        defaultCustCommunicationRefShouldNotBeFound("customerId.equals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustCommunicationRefsByCustomerIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        // Get all the custCommunicationRefList where customerId not equals to DEFAULT_CUSTOMER_ID
        defaultCustCommunicationRefShouldNotBeFound("customerId.notEquals=" + DEFAULT_CUSTOMER_ID);

        // Get all the custCommunicationRefList where customerId not equals to UPDATED_CUSTOMER_ID
        defaultCustCommunicationRefShouldBeFound("customerId.notEquals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustCommunicationRefsByCustomerIdIsInShouldWork() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        // Get all the custCommunicationRefList where customerId in DEFAULT_CUSTOMER_ID or UPDATED_CUSTOMER_ID
        defaultCustCommunicationRefShouldBeFound("customerId.in=" + DEFAULT_CUSTOMER_ID + "," + UPDATED_CUSTOMER_ID);

        // Get all the custCommunicationRefList where customerId equals to UPDATED_CUSTOMER_ID
        defaultCustCommunicationRefShouldNotBeFound("customerId.in=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustCommunicationRefsByCustomerIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        // Get all the custCommunicationRefList where customerId is not null
        defaultCustCommunicationRefShouldBeFound("customerId.specified=true");

        // Get all the custCommunicationRefList where customerId is null
        defaultCustCommunicationRefShouldNotBeFound("customerId.specified=false");
    }

    @Test
    @Transactional
    void getAllCustCommunicationRefsByCustomerIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        // Get all the custCommunicationRefList where customerId is greater than or equal to DEFAULT_CUSTOMER_ID
        defaultCustCommunicationRefShouldBeFound("customerId.greaterThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the custCommunicationRefList where customerId is greater than or equal to UPDATED_CUSTOMER_ID
        defaultCustCommunicationRefShouldNotBeFound("customerId.greaterThanOrEqual=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustCommunicationRefsByCustomerIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        // Get all the custCommunicationRefList where customerId is less than or equal to DEFAULT_CUSTOMER_ID
        defaultCustCommunicationRefShouldBeFound("customerId.lessThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the custCommunicationRefList where customerId is less than or equal to SMALLER_CUSTOMER_ID
        defaultCustCommunicationRefShouldNotBeFound("customerId.lessThanOrEqual=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustCommunicationRefsByCustomerIdIsLessThanSomething() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        // Get all the custCommunicationRefList where customerId is less than DEFAULT_CUSTOMER_ID
        defaultCustCommunicationRefShouldNotBeFound("customerId.lessThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the custCommunicationRefList where customerId is less than UPDATED_CUSTOMER_ID
        defaultCustCommunicationRefShouldBeFound("customerId.lessThan=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustCommunicationRefsByCustomerIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        // Get all the custCommunicationRefList where customerId is greater than DEFAULT_CUSTOMER_ID
        defaultCustCommunicationRefShouldNotBeFound("customerId.greaterThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the custCommunicationRefList where customerId is greater than SMALLER_CUSTOMER_ID
        defaultCustCommunicationRefShouldBeFound("customerId.greaterThan=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllCustCommunicationRefsByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);
        Customer customer = CustomerResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        custCommunicationRef.setCustomer(customer);
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);
        Long customerId = customer.getId();

        // Get all the custCommunicationRefList where customer equals to customerId
        defaultCustCommunicationRefShouldBeFound("customerId.equals=" + customerId);

        // Get all the custCommunicationRefList where customer equals to (customerId + 1)
        defaultCustCommunicationRefShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustCommunicationRefShouldBeFound(String filter) throws Exception {
        restCustCommunicationRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(custCommunicationRef.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerNotificationId").value(hasItem(DEFAULT_CUSTOMER_NOTIFICATION_ID)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())));

        // Check, that the count call also returns 1
        restCustCommunicationRefMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustCommunicationRefShouldNotBeFound(String filter) throws Exception {
        restCustCommunicationRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustCommunicationRefMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCustCommunicationRef() throws Exception {
        // Get the custCommunicationRef
        restCustCommunicationRefMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCustCommunicationRef() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        int databaseSizeBeforeUpdate = custCommunicationRefRepository.findAll().size();

        // Update the custCommunicationRef
        CustCommunicationRef updatedCustCommunicationRef = custCommunicationRefRepository.findById(custCommunicationRef.getId()).get();
        // Disconnect from session so that the updates on updatedCustCommunicationRef are not directly saved in db
        em.detach(updatedCustCommunicationRef);
        updatedCustCommunicationRef
            .customerNotificationId(UPDATED_CUSTOMER_NOTIFICATION_ID)
            .role(UPDATED_ROLE)
            .status(UPDATED_STATUS)
            .customerId(UPDATED_CUSTOMER_ID);
        CustCommunicationRefDTO custCommunicationRefDTO = custCommunicationRefMapper.toDto(updatedCustCommunicationRef);

        restCustCommunicationRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custCommunicationRefDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCommunicationRefDTO))
            )
            .andExpect(status().isOk());

        // Validate the CustCommunicationRef in the database
        List<CustCommunicationRef> custCommunicationRefList = custCommunicationRefRepository.findAll();
        assertThat(custCommunicationRefList).hasSize(databaseSizeBeforeUpdate);
        CustCommunicationRef testCustCommunicationRef = custCommunicationRefList.get(custCommunicationRefList.size() - 1);
        assertThat(testCustCommunicationRef.getCustomerNotificationId()).isEqualTo(UPDATED_CUSTOMER_NOTIFICATION_ID);
        assertThat(testCustCommunicationRef.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testCustCommunicationRef.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustCommunicationRef.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void putNonExistingCustCommunicationRef() throws Exception {
        int databaseSizeBeforeUpdate = custCommunicationRefRepository.findAll().size();
        custCommunicationRef.setId(count.incrementAndGet());

        // Create the CustCommunicationRef
        CustCommunicationRefDTO custCommunicationRefDTO = custCommunicationRefMapper.toDto(custCommunicationRef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustCommunicationRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, custCommunicationRefDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCommunicationRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustCommunicationRef in the database
        List<CustCommunicationRef> custCommunicationRefList = custCommunicationRefRepository.findAll();
        assertThat(custCommunicationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCustCommunicationRef() throws Exception {
        int databaseSizeBeforeUpdate = custCommunicationRefRepository.findAll().size();
        custCommunicationRef.setId(count.incrementAndGet());

        // Create the CustCommunicationRef
        CustCommunicationRefDTO custCommunicationRefDTO = custCommunicationRefMapper.toDto(custCommunicationRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustCommunicationRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCommunicationRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustCommunicationRef in the database
        List<CustCommunicationRef> custCommunicationRefList = custCommunicationRefRepository.findAll();
        assertThat(custCommunicationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCustCommunicationRef() throws Exception {
        int databaseSizeBeforeUpdate = custCommunicationRefRepository.findAll().size();
        custCommunicationRef.setId(count.incrementAndGet());

        // Create the CustCommunicationRef
        CustCommunicationRefDTO custCommunicationRefDTO = custCommunicationRefMapper.toDto(custCommunicationRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustCommunicationRefMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(custCommunicationRefDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustCommunicationRef in the database
        List<CustCommunicationRef> custCommunicationRefList = custCommunicationRefRepository.findAll();
        assertThat(custCommunicationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCustCommunicationRefWithPatch() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        int databaseSizeBeforeUpdate = custCommunicationRefRepository.findAll().size();

        // Update the custCommunicationRef using partial update
        CustCommunicationRef partialUpdatedCustCommunicationRef = new CustCommunicationRef();
        partialUpdatedCustCommunicationRef.setId(custCommunicationRef.getId());

        partialUpdatedCustCommunicationRef.status(UPDATED_STATUS);

        restCustCommunicationRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustCommunicationRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustCommunicationRef))
            )
            .andExpect(status().isOk());

        // Validate the CustCommunicationRef in the database
        List<CustCommunicationRef> custCommunicationRefList = custCommunicationRefRepository.findAll();
        assertThat(custCommunicationRefList).hasSize(databaseSizeBeforeUpdate);
        CustCommunicationRef testCustCommunicationRef = custCommunicationRefList.get(custCommunicationRefList.size() - 1);
        assertThat(testCustCommunicationRef.getCustomerNotificationId()).isEqualTo(DEFAULT_CUSTOMER_NOTIFICATION_ID);
        assertThat(testCustCommunicationRef.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testCustCommunicationRef.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustCommunicationRef.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void fullUpdateCustCommunicationRefWithPatch() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        int databaseSizeBeforeUpdate = custCommunicationRefRepository.findAll().size();

        // Update the custCommunicationRef using partial update
        CustCommunicationRef partialUpdatedCustCommunicationRef = new CustCommunicationRef();
        partialUpdatedCustCommunicationRef.setId(custCommunicationRef.getId());

        partialUpdatedCustCommunicationRef
            .customerNotificationId(UPDATED_CUSTOMER_NOTIFICATION_ID)
            .role(UPDATED_ROLE)
            .status(UPDATED_STATUS)
            .customerId(UPDATED_CUSTOMER_ID);

        restCustCommunicationRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCustCommunicationRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCustCommunicationRef))
            )
            .andExpect(status().isOk());

        // Validate the CustCommunicationRef in the database
        List<CustCommunicationRef> custCommunicationRefList = custCommunicationRefRepository.findAll();
        assertThat(custCommunicationRefList).hasSize(databaseSizeBeforeUpdate);
        CustCommunicationRef testCustCommunicationRef = custCommunicationRefList.get(custCommunicationRefList.size() - 1);
        assertThat(testCustCommunicationRef.getCustomerNotificationId()).isEqualTo(UPDATED_CUSTOMER_NOTIFICATION_ID);
        assertThat(testCustCommunicationRef.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testCustCommunicationRef.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCustCommunicationRef.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCustCommunicationRef() throws Exception {
        int databaseSizeBeforeUpdate = custCommunicationRefRepository.findAll().size();
        custCommunicationRef.setId(count.incrementAndGet());

        // Create the CustCommunicationRef
        CustCommunicationRefDTO custCommunicationRefDTO = custCommunicationRefMapper.toDto(custCommunicationRef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustCommunicationRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, custCommunicationRefDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custCommunicationRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustCommunicationRef in the database
        List<CustCommunicationRef> custCommunicationRefList = custCommunicationRefRepository.findAll();
        assertThat(custCommunicationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCustCommunicationRef() throws Exception {
        int databaseSizeBeforeUpdate = custCommunicationRefRepository.findAll().size();
        custCommunicationRef.setId(count.incrementAndGet());

        // Create the CustCommunicationRef
        CustCommunicationRefDTO custCommunicationRefDTO = custCommunicationRefMapper.toDto(custCommunicationRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustCommunicationRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custCommunicationRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CustCommunicationRef in the database
        List<CustCommunicationRef> custCommunicationRefList = custCommunicationRefRepository.findAll();
        assertThat(custCommunicationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCustCommunicationRef() throws Exception {
        int databaseSizeBeforeUpdate = custCommunicationRefRepository.findAll().size();
        custCommunicationRef.setId(count.incrementAndGet());

        // Create the CustCommunicationRef
        CustCommunicationRefDTO custCommunicationRefDTO = custCommunicationRefMapper.toDto(custCommunicationRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCustCommunicationRefMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(custCommunicationRefDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CustCommunicationRef in the database
        List<CustCommunicationRef> custCommunicationRefList = custCommunicationRefRepository.findAll();
        assertThat(custCommunicationRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCustCommunicationRef() throws Exception {
        // Initialize the database
        custCommunicationRefRepository.saveAndFlush(custCommunicationRef);

        int databaseSizeBeforeDelete = custCommunicationRefRepository.findAll().size();

        // Delete the custCommunicationRef
        restCustCommunicationRefMockMvc
            .perform(delete(ENTITY_API_URL_ID, custCommunicationRef.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustCommunicationRef> custCommunicationRefList = custCommunicationRefRepository.findAll();
        assertThat(custCommunicationRefList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
