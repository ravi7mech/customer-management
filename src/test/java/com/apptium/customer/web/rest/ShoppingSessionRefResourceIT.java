package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.Customer;
import com.apptium.customer.domain.Individual;
import com.apptium.customer.domain.ShoppingSessionRef;
import com.apptium.customer.repository.ShoppingSessionRefRepository;
import com.apptium.customer.service.criteria.ShoppingSessionRefCriteria;
import com.apptium.customer.service.dto.ShoppingSessionRefDTO;
import com.apptium.customer.service.mapper.ShoppingSessionRefMapper;
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
 * Integration tests for the {@link ShoppingSessionRefResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ShoppingSessionRefResourceIT {

    private static final String DEFAULT_HREF = "AAAAAAAAAA";
    private static final String UPDATED_HREF = "BBBBBBBBBB";

    private static final String DEFAULT_SHOPPING_SESSION_ID = "AAAAAAAAAA";
    private static final String UPDATED_SHOPPING_SESSION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SESSION_ABONDONED = false;
    private static final Boolean UPDATED_SESSION_ABONDONED = true;

    private static final Long DEFAULT_CUSTOMER_ID = 1L;
    private static final Long UPDATED_CUSTOMER_ID = 2L;
    private static final Long SMALLER_CUSTOMER_ID = 1L - 1L;

    private static final String DEFAULT_CHANNEL = "AAAAAAAAAA";
    private static final String UPDATED_CHANNEL = "BBBBBBBBBB";

    private static final Long DEFAULT_INDIVIDUAL_ID = 1L;
    private static final Long UPDATED_INDIVIDUAL_ID = 2L;
    private static final Long SMALLER_INDIVIDUAL_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/shopping-session-refs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ShoppingSessionRefRepository shoppingSessionRefRepository;

    @Autowired
    private ShoppingSessionRefMapper shoppingSessionRefMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShoppingSessionRefMockMvc;

    private ShoppingSessionRef shoppingSessionRef;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShoppingSessionRef createEntity(EntityManager em) {
        ShoppingSessionRef shoppingSessionRef = new ShoppingSessionRef()
            .href(DEFAULT_HREF)
            .shoppingSessionId(DEFAULT_SHOPPING_SESSION_ID)
            .status(DEFAULT_STATUS)
            .sessionAbondoned(DEFAULT_SESSION_ABONDONED)
            .customerId(DEFAULT_CUSTOMER_ID)
            .channel(DEFAULT_CHANNEL)
            .individualId(DEFAULT_INDIVIDUAL_ID);
        return shoppingSessionRef;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShoppingSessionRef createUpdatedEntity(EntityManager em) {
        ShoppingSessionRef shoppingSessionRef = new ShoppingSessionRef()
            .href(UPDATED_HREF)
            .shoppingSessionId(UPDATED_SHOPPING_SESSION_ID)
            .status(UPDATED_STATUS)
            .sessionAbondoned(UPDATED_SESSION_ABONDONED)
            .customerId(UPDATED_CUSTOMER_ID)
            .channel(UPDATED_CHANNEL)
            .individualId(UPDATED_INDIVIDUAL_ID);
        return shoppingSessionRef;
    }

    @BeforeEach
    public void initTest() {
        shoppingSessionRef = createEntity(em);
    }

    @Test
    @Transactional
    void createShoppingSessionRef() throws Exception {
        int databaseSizeBeforeCreate = shoppingSessionRefRepository.findAll().size();
        // Create the ShoppingSessionRef
        ShoppingSessionRefDTO shoppingSessionRefDTO = shoppingSessionRefMapper.toDto(shoppingSessionRef);
        restShoppingSessionRefMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shoppingSessionRefDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ShoppingSessionRef in the database
        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeCreate + 1);
        ShoppingSessionRef testShoppingSessionRef = shoppingSessionRefList.get(shoppingSessionRefList.size() - 1);
        assertThat(testShoppingSessionRef.getHref()).isEqualTo(DEFAULT_HREF);
        assertThat(testShoppingSessionRef.getShoppingSessionId()).isEqualTo(DEFAULT_SHOPPING_SESSION_ID);
        assertThat(testShoppingSessionRef.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testShoppingSessionRef.getSessionAbondoned()).isEqualTo(DEFAULT_SESSION_ABONDONED);
        assertThat(testShoppingSessionRef.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testShoppingSessionRef.getChannel()).isEqualTo(DEFAULT_CHANNEL);
        assertThat(testShoppingSessionRef.getIndividualId()).isEqualTo(DEFAULT_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void createShoppingSessionRefWithExistingId() throws Exception {
        // Create the ShoppingSessionRef with an existing ID
        shoppingSessionRef.setId(1L);
        ShoppingSessionRefDTO shoppingSessionRefDTO = shoppingSessionRefMapper.toDto(shoppingSessionRef);

        int databaseSizeBeforeCreate = shoppingSessionRefRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restShoppingSessionRefMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shoppingSessionRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShoppingSessionRef in the database
        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkShoppingSessionIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = shoppingSessionRefRepository.findAll().size();
        // set the field null
        shoppingSessionRef.setShoppingSessionId(null);

        // Create the ShoppingSessionRef, which fails.
        ShoppingSessionRefDTO shoppingSessionRefDTO = shoppingSessionRefMapper.toDto(shoppingSessionRef);

        restShoppingSessionRefMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shoppingSessionRefDTO))
            )
            .andExpect(status().isBadRequest());

        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = shoppingSessionRefRepository.findAll().size();
        // set the field null
        shoppingSessionRef.setStatus(null);

        // Create the ShoppingSessionRef, which fails.
        ShoppingSessionRefDTO shoppingSessionRefDTO = shoppingSessionRefMapper.toDto(shoppingSessionRef);

        restShoppingSessionRefMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shoppingSessionRefDTO))
            )
            .andExpect(status().isBadRequest());

        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSessionAbondonedIsRequired() throws Exception {
        int databaseSizeBeforeTest = shoppingSessionRefRepository.findAll().size();
        // set the field null
        shoppingSessionRef.setSessionAbondoned(null);

        // Create the ShoppingSessionRef, which fails.
        ShoppingSessionRefDTO shoppingSessionRefDTO = shoppingSessionRefMapper.toDto(shoppingSessionRef);

        restShoppingSessionRefMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shoppingSessionRefDTO))
            )
            .andExpect(status().isBadRequest());

        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustomerIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = shoppingSessionRefRepository.findAll().size();
        // set the field null
        shoppingSessionRef.setCustomerId(null);

        // Create the ShoppingSessionRef, which fails.
        ShoppingSessionRefDTO shoppingSessionRefDTO = shoppingSessionRefMapper.toDto(shoppingSessionRef);

        restShoppingSessionRefMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shoppingSessionRefDTO))
            )
            .andExpect(status().isBadRequest());

        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkChannelIsRequired() throws Exception {
        int databaseSizeBeforeTest = shoppingSessionRefRepository.findAll().size();
        // set the field null
        shoppingSessionRef.setChannel(null);

        // Create the ShoppingSessionRef, which fails.
        ShoppingSessionRefDTO shoppingSessionRefDTO = shoppingSessionRefMapper.toDto(shoppingSessionRef);

        restShoppingSessionRefMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shoppingSessionRefDTO))
            )
            .andExpect(status().isBadRequest());

        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIndividualIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = shoppingSessionRefRepository.findAll().size();
        // set the field null
        shoppingSessionRef.setIndividualId(null);

        // Create the ShoppingSessionRef, which fails.
        ShoppingSessionRefDTO shoppingSessionRefDTO = shoppingSessionRefMapper.toDto(shoppingSessionRef);

        restShoppingSessionRefMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shoppingSessionRefDTO))
            )
            .andExpect(status().isBadRequest());

        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefs() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList
        restShoppingSessionRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shoppingSessionRef.getId().intValue())))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].shoppingSessionId").value(hasItem(DEFAULT_SHOPPING_SESSION_ID)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].sessionAbondoned").value(hasItem(DEFAULT_SESSION_ABONDONED.booleanValue())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].channel").value(hasItem(DEFAULT_CHANNEL)))
            .andExpect(jsonPath("$.[*].individualId").value(hasItem(DEFAULT_INDIVIDUAL_ID.intValue())));
    }

    @Test
    @Transactional
    void getShoppingSessionRef() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get the shoppingSessionRef
        restShoppingSessionRefMockMvc
            .perform(get(ENTITY_API_URL_ID, shoppingSessionRef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shoppingSessionRef.getId().intValue()))
            .andExpect(jsonPath("$.href").value(DEFAULT_HREF))
            .andExpect(jsonPath("$.shoppingSessionId").value(DEFAULT_SHOPPING_SESSION_ID))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.sessionAbondoned").value(DEFAULT_SESSION_ABONDONED.booleanValue()))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID.intValue()))
            .andExpect(jsonPath("$.channel").value(DEFAULT_CHANNEL))
            .andExpect(jsonPath("$.individualId").value(DEFAULT_INDIVIDUAL_ID.intValue()));
    }

    @Test
    @Transactional
    void getShoppingSessionRefsByIdFiltering() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        Long id = shoppingSessionRef.getId();

        defaultShoppingSessionRefShouldBeFound("id.equals=" + id);
        defaultShoppingSessionRefShouldNotBeFound("id.notEquals=" + id);

        defaultShoppingSessionRefShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultShoppingSessionRefShouldNotBeFound("id.greaterThan=" + id);

        defaultShoppingSessionRefShouldBeFound("id.lessThanOrEqual=" + id);
        defaultShoppingSessionRefShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByHrefIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where href equals to DEFAULT_HREF
        defaultShoppingSessionRefShouldBeFound("href.equals=" + DEFAULT_HREF);

        // Get all the shoppingSessionRefList where href equals to UPDATED_HREF
        defaultShoppingSessionRefShouldNotBeFound("href.equals=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByHrefIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where href not equals to DEFAULT_HREF
        defaultShoppingSessionRefShouldNotBeFound("href.notEquals=" + DEFAULT_HREF);

        // Get all the shoppingSessionRefList where href not equals to UPDATED_HREF
        defaultShoppingSessionRefShouldBeFound("href.notEquals=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByHrefIsInShouldWork() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where href in DEFAULT_HREF or UPDATED_HREF
        defaultShoppingSessionRefShouldBeFound("href.in=" + DEFAULT_HREF + "," + UPDATED_HREF);

        // Get all the shoppingSessionRefList where href equals to UPDATED_HREF
        defaultShoppingSessionRefShouldNotBeFound("href.in=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByHrefIsNullOrNotNull() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where href is not null
        defaultShoppingSessionRefShouldBeFound("href.specified=true");

        // Get all the shoppingSessionRefList where href is null
        defaultShoppingSessionRefShouldNotBeFound("href.specified=false");
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByHrefContainsSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where href contains DEFAULT_HREF
        defaultShoppingSessionRefShouldBeFound("href.contains=" + DEFAULT_HREF);

        // Get all the shoppingSessionRefList where href contains UPDATED_HREF
        defaultShoppingSessionRefShouldNotBeFound("href.contains=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByHrefNotContainsSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where href does not contain DEFAULT_HREF
        defaultShoppingSessionRefShouldNotBeFound("href.doesNotContain=" + DEFAULT_HREF);

        // Get all the shoppingSessionRefList where href does not contain UPDATED_HREF
        defaultShoppingSessionRefShouldBeFound("href.doesNotContain=" + UPDATED_HREF);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByShoppingSessionIdIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where shoppingSessionId equals to DEFAULT_SHOPPING_SESSION_ID
        defaultShoppingSessionRefShouldBeFound("shoppingSessionId.equals=" + DEFAULT_SHOPPING_SESSION_ID);

        // Get all the shoppingSessionRefList where shoppingSessionId equals to UPDATED_SHOPPING_SESSION_ID
        defaultShoppingSessionRefShouldNotBeFound("shoppingSessionId.equals=" + UPDATED_SHOPPING_SESSION_ID);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByShoppingSessionIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where shoppingSessionId not equals to DEFAULT_SHOPPING_SESSION_ID
        defaultShoppingSessionRefShouldNotBeFound("shoppingSessionId.notEquals=" + DEFAULT_SHOPPING_SESSION_ID);

        // Get all the shoppingSessionRefList where shoppingSessionId not equals to UPDATED_SHOPPING_SESSION_ID
        defaultShoppingSessionRefShouldBeFound("shoppingSessionId.notEquals=" + UPDATED_SHOPPING_SESSION_ID);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByShoppingSessionIdIsInShouldWork() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where shoppingSessionId in DEFAULT_SHOPPING_SESSION_ID or UPDATED_SHOPPING_SESSION_ID
        defaultShoppingSessionRefShouldBeFound("shoppingSessionId.in=" + DEFAULT_SHOPPING_SESSION_ID + "," + UPDATED_SHOPPING_SESSION_ID);

        // Get all the shoppingSessionRefList where shoppingSessionId equals to UPDATED_SHOPPING_SESSION_ID
        defaultShoppingSessionRefShouldNotBeFound("shoppingSessionId.in=" + UPDATED_SHOPPING_SESSION_ID);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByShoppingSessionIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where shoppingSessionId is not null
        defaultShoppingSessionRefShouldBeFound("shoppingSessionId.specified=true");

        // Get all the shoppingSessionRefList where shoppingSessionId is null
        defaultShoppingSessionRefShouldNotBeFound("shoppingSessionId.specified=false");
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByShoppingSessionIdContainsSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where shoppingSessionId contains DEFAULT_SHOPPING_SESSION_ID
        defaultShoppingSessionRefShouldBeFound("shoppingSessionId.contains=" + DEFAULT_SHOPPING_SESSION_ID);

        // Get all the shoppingSessionRefList where shoppingSessionId contains UPDATED_SHOPPING_SESSION_ID
        defaultShoppingSessionRefShouldNotBeFound("shoppingSessionId.contains=" + UPDATED_SHOPPING_SESSION_ID);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByShoppingSessionIdNotContainsSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where shoppingSessionId does not contain DEFAULT_SHOPPING_SESSION_ID
        defaultShoppingSessionRefShouldNotBeFound("shoppingSessionId.doesNotContain=" + DEFAULT_SHOPPING_SESSION_ID);

        // Get all the shoppingSessionRefList where shoppingSessionId does not contain UPDATED_SHOPPING_SESSION_ID
        defaultShoppingSessionRefShouldBeFound("shoppingSessionId.doesNotContain=" + UPDATED_SHOPPING_SESSION_ID);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where status equals to DEFAULT_STATUS
        defaultShoppingSessionRefShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the shoppingSessionRefList where status equals to UPDATED_STATUS
        defaultShoppingSessionRefShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where status not equals to DEFAULT_STATUS
        defaultShoppingSessionRefShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the shoppingSessionRefList where status not equals to UPDATED_STATUS
        defaultShoppingSessionRefShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultShoppingSessionRefShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the shoppingSessionRefList where status equals to UPDATED_STATUS
        defaultShoppingSessionRefShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where status is not null
        defaultShoppingSessionRefShouldBeFound("status.specified=true");

        // Get all the shoppingSessionRefList where status is null
        defaultShoppingSessionRefShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByStatusContainsSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where status contains DEFAULT_STATUS
        defaultShoppingSessionRefShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the shoppingSessionRefList where status contains UPDATED_STATUS
        defaultShoppingSessionRefShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where status does not contain DEFAULT_STATUS
        defaultShoppingSessionRefShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the shoppingSessionRefList where status does not contain UPDATED_STATUS
        defaultShoppingSessionRefShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsBySessionAbondonedIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where sessionAbondoned equals to DEFAULT_SESSION_ABONDONED
        defaultShoppingSessionRefShouldBeFound("sessionAbondoned.equals=" + DEFAULT_SESSION_ABONDONED);

        // Get all the shoppingSessionRefList where sessionAbondoned equals to UPDATED_SESSION_ABONDONED
        defaultShoppingSessionRefShouldNotBeFound("sessionAbondoned.equals=" + UPDATED_SESSION_ABONDONED);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsBySessionAbondonedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where sessionAbondoned not equals to DEFAULT_SESSION_ABONDONED
        defaultShoppingSessionRefShouldNotBeFound("sessionAbondoned.notEquals=" + DEFAULT_SESSION_ABONDONED);

        // Get all the shoppingSessionRefList where sessionAbondoned not equals to UPDATED_SESSION_ABONDONED
        defaultShoppingSessionRefShouldBeFound("sessionAbondoned.notEquals=" + UPDATED_SESSION_ABONDONED);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsBySessionAbondonedIsInShouldWork() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where sessionAbondoned in DEFAULT_SESSION_ABONDONED or UPDATED_SESSION_ABONDONED
        defaultShoppingSessionRefShouldBeFound("sessionAbondoned.in=" + DEFAULT_SESSION_ABONDONED + "," + UPDATED_SESSION_ABONDONED);

        // Get all the shoppingSessionRefList where sessionAbondoned equals to UPDATED_SESSION_ABONDONED
        defaultShoppingSessionRefShouldNotBeFound("sessionAbondoned.in=" + UPDATED_SESSION_ABONDONED);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsBySessionAbondonedIsNullOrNotNull() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where sessionAbondoned is not null
        defaultShoppingSessionRefShouldBeFound("sessionAbondoned.specified=true");

        // Get all the shoppingSessionRefList where sessionAbondoned is null
        defaultShoppingSessionRefShouldNotBeFound("sessionAbondoned.specified=false");
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByCustomerIdIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where customerId equals to DEFAULT_CUSTOMER_ID
        defaultShoppingSessionRefShouldBeFound("customerId.equals=" + DEFAULT_CUSTOMER_ID);

        // Get all the shoppingSessionRefList where customerId equals to UPDATED_CUSTOMER_ID
        defaultShoppingSessionRefShouldNotBeFound("customerId.equals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByCustomerIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where customerId not equals to DEFAULT_CUSTOMER_ID
        defaultShoppingSessionRefShouldNotBeFound("customerId.notEquals=" + DEFAULT_CUSTOMER_ID);

        // Get all the shoppingSessionRefList where customerId not equals to UPDATED_CUSTOMER_ID
        defaultShoppingSessionRefShouldBeFound("customerId.notEquals=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByCustomerIdIsInShouldWork() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where customerId in DEFAULT_CUSTOMER_ID or UPDATED_CUSTOMER_ID
        defaultShoppingSessionRefShouldBeFound("customerId.in=" + DEFAULT_CUSTOMER_ID + "," + UPDATED_CUSTOMER_ID);

        // Get all the shoppingSessionRefList where customerId equals to UPDATED_CUSTOMER_ID
        defaultShoppingSessionRefShouldNotBeFound("customerId.in=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByCustomerIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where customerId is not null
        defaultShoppingSessionRefShouldBeFound("customerId.specified=true");

        // Get all the shoppingSessionRefList where customerId is null
        defaultShoppingSessionRefShouldNotBeFound("customerId.specified=false");
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByCustomerIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where customerId is greater than or equal to DEFAULT_CUSTOMER_ID
        defaultShoppingSessionRefShouldBeFound("customerId.greaterThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the shoppingSessionRefList where customerId is greater than or equal to UPDATED_CUSTOMER_ID
        defaultShoppingSessionRefShouldNotBeFound("customerId.greaterThanOrEqual=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByCustomerIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where customerId is less than or equal to DEFAULT_CUSTOMER_ID
        defaultShoppingSessionRefShouldBeFound("customerId.lessThanOrEqual=" + DEFAULT_CUSTOMER_ID);

        // Get all the shoppingSessionRefList where customerId is less than or equal to SMALLER_CUSTOMER_ID
        defaultShoppingSessionRefShouldNotBeFound("customerId.lessThanOrEqual=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByCustomerIdIsLessThanSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where customerId is less than DEFAULT_CUSTOMER_ID
        defaultShoppingSessionRefShouldNotBeFound("customerId.lessThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the shoppingSessionRefList where customerId is less than UPDATED_CUSTOMER_ID
        defaultShoppingSessionRefShouldBeFound("customerId.lessThan=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByCustomerIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where customerId is greater than DEFAULT_CUSTOMER_ID
        defaultShoppingSessionRefShouldNotBeFound("customerId.greaterThan=" + DEFAULT_CUSTOMER_ID);

        // Get all the shoppingSessionRefList where customerId is greater than SMALLER_CUSTOMER_ID
        defaultShoppingSessionRefShouldBeFound("customerId.greaterThan=" + SMALLER_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByChannelIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where channel equals to DEFAULT_CHANNEL
        defaultShoppingSessionRefShouldBeFound("channel.equals=" + DEFAULT_CHANNEL);

        // Get all the shoppingSessionRefList where channel equals to UPDATED_CHANNEL
        defaultShoppingSessionRefShouldNotBeFound("channel.equals=" + UPDATED_CHANNEL);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByChannelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where channel not equals to DEFAULT_CHANNEL
        defaultShoppingSessionRefShouldNotBeFound("channel.notEquals=" + DEFAULT_CHANNEL);

        // Get all the shoppingSessionRefList where channel not equals to UPDATED_CHANNEL
        defaultShoppingSessionRefShouldBeFound("channel.notEquals=" + UPDATED_CHANNEL);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByChannelIsInShouldWork() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where channel in DEFAULT_CHANNEL or UPDATED_CHANNEL
        defaultShoppingSessionRefShouldBeFound("channel.in=" + DEFAULT_CHANNEL + "," + UPDATED_CHANNEL);

        // Get all the shoppingSessionRefList where channel equals to UPDATED_CHANNEL
        defaultShoppingSessionRefShouldNotBeFound("channel.in=" + UPDATED_CHANNEL);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByChannelIsNullOrNotNull() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where channel is not null
        defaultShoppingSessionRefShouldBeFound("channel.specified=true");

        // Get all the shoppingSessionRefList where channel is null
        defaultShoppingSessionRefShouldNotBeFound("channel.specified=false");
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByChannelContainsSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where channel contains DEFAULT_CHANNEL
        defaultShoppingSessionRefShouldBeFound("channel.contains=" + DEFAULT_CHANNEL);

        // Get all the shoppingSessionRefList where channel contains UPDATED_CHANNEL
        defaultShoppingSessionRefShouldNotBeFound("channel.contains=" + UPDATED_CHANNEL);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByChannelNotContainsSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where channel does not contain DEFAULT_CHANNEL
        defaultShoppingSessionRefShouldNotBeFound("channel.doesNotContain=" + DEFAULT_CHANNEL);

        // Get all the shoppingSessionRefList where channel does not contain UPDATED_CHANNEL
        defaultShoppingSessionRefShouldBeFound("channel.doesNotContain=" + UPDATED_CHANNEL);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByIndividualIdIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where individualId equals to DEFAULT_INDIVIDUAL_ID
        defaultShoppingSessionRefShouldBeFound("individualId.equals=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the shoppingSessionRefList where individualId equals to UPDATED_INDIVIDUAL_ID
        defaultShoppingSessionRefShouldNotBeFound("individualId.equals=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByIndividualIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where individualId not equals to DEFAULT_INDIVIDUAL_ID
        defaultShoppingSessionRefShouldNotBeFound("individualId.notEquals=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the shoppingSessionRefList where individualId not equals to UPDATED_INDIVIDUAL_ID
        defaultShoppingSessionRefShouldBeFound("individualId.notEquals=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByIndividualIdIsInShouldWork() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where individualId in DEFAULT_INDIVIDUAL_ID or UPDATED_INDIVIDUAL_ID
        defaultShoppingSessionRefShouldBeFound("individualId.in=" + DEFAULT_INDIVIDUAL_ID + "," + UPDATED_INDIVIDUAL_ID);

        // Get all the shoppingSessionRefList where individualId equals to UPDATED_INDIVIDUAL_ID
        defaultShoppingSessionRefShouldNotBeFound("individualId.in=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByIndividualIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where individualId is not null
        defaultShoppingSessionRefShouldBeFound("individualId.specified=true");

        // Get all the shoppingSessionRefList where individualId is null
        defaultShoppingSessionRefShouldNotBeFound("individualId.specified=false");
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByIndividualIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where individualId is greater than or equal to DEFAULT_INDIVIDUAL_ID
        defaultShoppingSessionRefShouldBeFound("individualId.greaterThanOrEqual=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the shoppingSessionRefList where individualId is greater than or equal to UPDATED_INDIVIDUAL_ID
        defaultShoppingSessionRefShouldNotBeFound("individualId.greaterThanOrEqual=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByIndividualIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where individualId is less than or equal to DEFAULT_INDIVIDUAL_ID
        defaultShoppingSessionRefShouldBeFound("individualId.lessThanOrEqual=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the shoppingSessionRefList where individualId is less than or equal to SMALLER_INDIVIDUAL_ID
        defaultShoppingSessionRefShouldNotBeFound("individualId.lessThanOrEqual=" + SMALLER_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByIndividualIdIsLessThanSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where individualId is less than DEFAULT_INDIVIDUAL_ID
        defaultShoppingSessionRefShouldNotBeFound("individualId.lessThan=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the shoppingSessionRefList where individualId is less than UPDATED_INDIVIDUAL_ID
        defaultShoppingSessionRefShouldBeFound("individualId.lessThan=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByIndividualIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        // Get all the shoppingSessionRefList where individualId is greater than DEFAULT_INDIVIDUAL_ID
        defaultShoppingSessionRefShouldNotBeFound("individualId.greaterThan=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the shoppingSessionRefList where individualId is greater than SMALLER_INDIVIDUAL_ID
        defaultShoppingSessionRefShouldBeFound("individualId.greaterThan=" + SMALLER_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);
        Customer customer = CustomerResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        shoppingSessionRef.setCustomer(customer);
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);
        Long customerId = customer.getId();

        // Get all the shoppingSessionRefList where customer equals to customerId
        defaultShoppingSessionRefShouldBeFound("customerId.equals=" + customerId);

        // Get all the shoppingSessionRefList where customer equals to (customerId + 1)
        defaultShoppingSessionRefShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }

    @Test
    @Transactional
    void getAllShoppingSessionRefsByIndividualIsEqualToSomething() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);
        Individual individual = IndividualResourceIT.createEntity(em);
        em.persist(individual);
        em.flush();
        shoppingSessionRef.setIndividual(individual);
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);
        Long individualId = individual.getId();

        // Get all the shoppingSessionRefList where individual equals to individualId
        defaultShoppingSessionRefShouldBeFound("individualId.equals=" + individualId);

        // Get all the shoppingSessionRefList where individual equals to (individualId + 1)
        defaultShoppingSessionRefShouldNotBeFound("individualId.equals=" + (individualId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultShoppingSessionRefShouldBeFound(String filter) throws Exception {
        restShoppingSessionRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shoppingSessionRef.getId().intValue())))
            .andExpect(jsonPath("$.[*].href").value(hasItem(DEFAULT_HREF)))
            .andExpect(jsonPath("$.[*].shoppingSessionId").value(hasItem(DEFAULT_SHOPPING_SESSION_ID)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].sessionAbondoned").value(hasItem(DEFAULT_SESSION_ABONDONED.booleanValue())))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID.intValue())))
            .andExpect(jsonPath("$.[*].channel").value(hasItem(DEFAULT_CHANNEL)))
            .andExpect(jsonPath("$.[*].individualId").value(hasItem(DEFAULT_INDIVIDUAL_ID.intValue())));

        // Check, that the count call also returns 1
        restShoppingSessionRefMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultShoppingSessionRefShouldNotBeFound(String filter) throws Exception {
        restShoppingSessionRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restShoppingSessionRefMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingShoppingSessionRef() throws Exception {
        // Get the shoppingSessionRef
        restShoppingSessionRefMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewShoppingSessionRef() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        int databaseSizeBeforeUpdate = shoppingSessionRefRepository.findAll().size();

        // Update the shoppingSessionRef
        ShoppingSessionRef updatedShoppingSessionRef = shoppingSessionRefRepository.findById(shoppingSessionRef.getId()).get();
        // Disconnect from session so that the updates on updatedShoppingSessionRef are not directly saved in db
        em.detach(updatedShoppingSessionRef);
        updatedShoppingSessionRef
            .href(UPDATED_HREF)
            .shoppingSessionId(UPDATED_SHOPPING_SESSION_ID)
            .status(UPDATED_STATUS)
            .sessionAbondoned(UPDATED_SESSION_ABONDONED)
            .customerId(UPDATED_CUSTOMER_ID)
            .channel(UPDATED_CHANNEL)
            .individualId(UPDATED_INDIVIDUAL_ID);
        ShoppingSessionRefDTO shoppingSessionRefDTO = shoppingSessionRefMapper.toDto(updatedShoppingSessionRef);

        restShoppingSessionRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shoppingSessionRefDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shoppingSessionRefDTO))
            )
            .andExpect(status().isOk());

        // Validate the ShoppingSessionRef in the database
        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeUpdate);
        ShoppingSessionRef testShoppingSessionRef = shoppingSessionRefList.get(shoppingSessionRefList.size() - 1);
        assertThat(testShoppingSessionRef.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testShoppingSessionRef.getShoppingSessionId()).isEqualTo(UPDATED_SHOPPING_SESSION_ID);
        assertThat(testShoppingSessionRef.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testShoppingSessionRef.getSessionAbondoned()).isEqualTo(UPDATED_SESSION_ABONDONED);
        assertThat(testShoppingSessionRef.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testShoppingSessionRef.getChannel()).isEqualTo(UPDATED_CHANNEL);
        assertThat(testShoppingSessionRef.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void putNonExistingShoppingSessionRef() throws Exception {
        int databaseSizeBeforeUpdate = shoppingSessionRefRepository.findAll().size();
        shoppingSessionRef.setId(count.incrementAndGet());

        // Create the ShoppingSessionRef
        ShoppingSessionRefDTO shoppingSessionRefDTO = shoppingSessionRefMapper.toDto(shoppingSessionRef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShoppingSessionRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shoppingSessionRefDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shoppingSessionRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShoppingSessionRef in the database
        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchShoppingSessionRef() throws Exception {
        int databaseSizeBeforeUpdate = shoppingSessionRefRepository.findAll().size();
        shoppingSessionRef.setId(count.incrementAndGet());

        // Create the ShoppingSessionRef
        ShoppingSessionRefDTO shoppingSessionRefDTO = shoppingSessionRefMapper.toDto(shoppingSessionRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShoppingSessionRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shoppingSessionRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShoppingSessionRef in the database
        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamShoppingSessionRef() throws Exception {
        int databaseSizeBeforeUpdate = shoppingSessionRefRepository.findAll().size();
        shoppingSessionRef.setId(count.incrementAndGet());

        // Create the ShoppingSessionRef
        ShoppingSessionRefDTO shoppingSessionRefDTO = shoppingSessionRefMapper.toDto(shoppingSessionRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShoppingSessionRefMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shoppingSessionRefDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShoppingSessionRef in the database
        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateShoppingSessionRefWithPatch() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        int databaseSizeBeforeUpdate = shoppingSessionRefRepository.findAll().size();

        // Update the shoppingSessionRef using partial update
        ShoppingSessionRef partialUpdatedShoppingSessionRef = new ShoppingSessionRef();
        partialUpdatedShoppingSessionRef.setId(shoppingSessionRef.getId());

        partialUpdatedShoppingSessionRef
            .href(UPDATED_HREF)
            .shoppingSessionId(UPDATED_SHOPPING_SESSION_ID)
            .sessionAbondoned(UPDATED_SESSION_ABONDONED)
            .individualId(UPDATED_INDIVIDUAL_ID);

        restShoppingSessionRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShoppingSessionRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShoppingSessionRef))
            )
            .andExpect(status().isOk());

        // Validate the ShoppingSessionRef in the database
        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeUpdate);
        ShoppingSessionRef testShoppingSessionRef = shoppingSessionRefList.get(shoppingSessionRefList.size() - 1);
        assertThat(testShoppingSessionRef.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testShoppingSessionRef.getShoppingSessionId()).isEqualTo(UPDATED_SHOPPING_SESSION_ID);
        assertThat(testShoppingSessionRef.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testShoppingSessionRef.getSessionAbondoned()).isEqualTo(UPDATED_SESSION_ABONDONED);
        assertThat(testShoppingSessionRef.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testShoppingSessionRef.getChannel()).isEqualTo(DEFAULT_CHANNEL);
        assertThat(testShoppingSessionRef.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void fullUpdateShoppingSessionRefWithPatch() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        int databaseSizeBeforeUpdate = shoppingSessionRefRepository.findAll().size();

        // Update the shoppingSessionRef using partial update
        ShoppingSessionRef partialUpdatedShoppingSessionRef = new ShoppingSessionRef();
        partialUpdatedShoppingSessionRef.setId(shoppingSessionRef.getId());

        partialUpdatedShoppingSessionRef
            .href(UPDATED_HREF)
            .shoppingSessionId(UPDATED_SHOPPING_SESSION_ID)
            .status(UPDATED_STATUS)
            .sessionAbondoned(UPDATED_SESSION_ABONDONED)
            .customerId(UPDATED_CUSTOMER_ID)
            .channel(UPDATED_CHANNEL)
            .individualId(UPDATED_INDIVIDUAL_ID);

        restShoppingSessionRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShoppingSessionRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShoppingSessionRef))
            )
            .andExpect(status().isOk());

        // Validate the ShoppingSessionRef in the database
        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeUpdate);
        ShoppingSessionRef testShoppingSessionRef = shoppingSessionRefList.get(shoppingSessionRefList.size() - 1);
        assertThat(testShoppingSessionRef.getHref()).isEqualTo(UPDATED_HREF);
        assertThat(testShoppingSessionRef.getShoppingSessionId()).isEqualTo(UPDATED_SHOPPING_SESSION_ID);
        assertThat(testShoppingSessionRef.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testShoppingSessionRef.getSessionAbondoned()).isEqualTo(UPDATED_SESSION_ABONDONED);
        assertThat(testShoppingSessionRef.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testShoppingSessionRef.getChannel()).isEqualTo(UPDATED_CHANNEL);
        assertThat(testShoppingSessionRef.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void patchNonExistingShoppingSessionRef() throws Exception {
        int databaseSizeBeforeUpdate = shoppingSessionRefRepository.findAll().size();
        shoppingSessionRef.setId(count.incrementAndGet());

        // Create the ShoppingSessionRef
        ShoppingSessionRefDTO shoppingSessionRefDTO = shoppingSessionRefMapper.toDto(shoppingSessionRef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShoppingSessionRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, shoppingSessionRefDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shoppingSessionRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShoppingSessionRef in the database
        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchShoppingSessionRef() throws Exception {
        int databaseSizeBeforeUpdate = shoppingSessionRefRepository.findAll().size();
        shoppingSessionRef.setId(count.incrementAndGet());

        // Create the ShoppingSessionRef
        ShoppingSessionRefDTO shoppingSessionRefDTO = shoppingSessionRefMapper.toDto(shoppingSessionRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShoppingSessionRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shoppingSessionRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShoppingSessionRef in the database
        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamShoppingSessionRef() throws Exception {
        int databaseSizeBeforeUpdate = shoppingSessionRefRepository.findAll().size();
        shoppingSessionRef.setId(count.incrementAndGet());

        // Create the ShoppingSessionRef
        ShoppingSessionRefDTO shoppingSessionRefDTO = shoppingSessionRefMapper.toDto(shoppingSessionRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShoppingSessionRefMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shoppingSessionRefDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShoppingSessionRef in the database
        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteShoppingSessionRef() throws Exception {
        // Initialize the database
        shoppingSessionRefRepository.saveAndFlush(shoppingSessionRef);

        int databaseSizeBeforeDelete = shoppingSessionRefRepository.findAll().size();

        // Delete the shoppingSessionRef
        restShoppingSessionRefMockMvc
            .perform(delete(ENTITY_API_URL_ID, shoppingSessionRef.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ShoppingSessionRef> shoppingSessionRefList = shoppingSessionRefRepository.findAll();
        assertThat(shoppingSessionRefList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
