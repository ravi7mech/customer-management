package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.CustContact;
import com.apptium.customer.domain.GeographicSiteRef;
import com.apptium.customer.repository.GeographicSiteRefRepository;
import com.apptium.customer.service.criteria.GeographicSiteRefCriteria;
import com.apptium.customer.service.dto.GeographicSiteRefDTO;
import com.apptium.customer.service.mapper.GeographicSiteRefMapper;
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
 * Integration tests for the {@link GeographicSiteRefResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GeographicSiteRefResourceIT {

    private static final String DEFAULT_SITE_REF = "AAAAAAAAAA";
    private static final String UPDATED_SITE_REF = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final Long DEFAULT_CUST_CON_ID = 1L;
    private static final Long UPDATED_CUST_CON_ID = 2L;
    private static final Long SMALLER_CUST_CON_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/geographic-site-refs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GeographicSiteRefRepository geographicSiteRefRepository;

    @Autowired
    private GeographicSiteRefMapper geographicSiteRefMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGeographicSiteRefMockMvc;

    private GeographicSiteRef geographicSiteRef;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GeographicSiteRef createEntity(EntityManager em) {
        GeographicSiteRef geographicSiteRef = new GeographicSiteRef()
            .siteRef(DEFAULT_SITE_REF)
            .location(DEFAULT_LOCATION)
            .custConId(DEFAULT_CUST_CON_ID);
        return geographicSiteRef;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GeographicSiteRef createUpdatedEntity(EntityManager em) {
        GeographicSiteRef geographicSiteRef = new GeographicSiteRef()
            .siteRef(UPDATED_SITE_REF)
            .location(UPDATED_LOCATION)
            .custConId(UPDATED_CUST_CON_ID);
        return geographicSiteRef;
    }

    @BeforeEach
    public void initTest() {
        geographicSiteRef = createEntity(em);
    }

    @Test
    @Transactional
    void createGeographicSiteRef() throws Exception {
        int databaseSizeBeforeCreate = geographicSiteRefRepository.findAll().size();
        // Create the GeographicSiteRef
        GeographicSiteRefDTO geographicSiteRefDTO = geographicSiteRefMapper.toDto(geographicSiteRef);
        restGeographicSiteRefMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(geographicSiteRefDTO))
            )
            .andExpect(status().isCreated());

        // Validate the GeographicSiteRef in the database
        List<GeographicSiteRef> geographicSiteRefList = geographicSiteRefRepository.findAll();
        assertThat(geographicSiteRefList).hasSize(databaseSizeBeforeCreate + 1);
        GeographicSiteRef testGeographicSiteRef = geographicSiteRefList.get(geographicSiteRefList.size() - 1);
        assertThat(testGeographicSiteRef.getSiteRef()).isEqualTo(DEFAULT_SITE_REF);
        assertThat(testGeographicSiteRef.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testGeographicSiteRef.getCustConId()).isEqualTo(DEFAULT_CUST_CON_ID);
    }

    @Test
    @Transactional
    void createGeographicSiteRefWithExistingId() throws Exception {
        // Create the GeographicSiteRef with an existing ID
        geographicSiteRef.setId(1L);
        GeographicSiteRefDTO geographicSiteRefDTO = geographicSiteRefMapper.toDto(geographicSiteRef);

        int databaseSizeBeforeCreate = geographicSiteRefRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGeographicSiteRefMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(geographicSiteRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GeographicSiteRef in the database
        List<GeographicSiteRef> geographicSiteRefList = geographicSiteRefRepository.findAll();
        assertThat(geographicSiteRefList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSiteRefIsRequired() throws Exception {
        int databaseSizeBeforeTest = geographicSiteRefRepository.findAll().size();
        // set the field null
        geographicSiteRef.setSiteRef(null);

        // Create the GeographicSiteRef, which fails.
        GeographicSiteRefDTO geographicSiteRefDTO = geographicSiteRefMapper.toDto(geographicSiteRef);

        restGeographicSiteRefMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(geographicSiteRefDTO))
            )
            .andExpect(status().isBadRequest());

        List<GeographicSiteRef> geographicSiteRefList = geographicSiteRefRepository.findAll();
        assertThat(geographicSiteRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = geographicSiteRefRepository.findAll().size();
        // set the field null
        geographicSiteRef.setLocation(null);

        // Create the GeographicSiteRef, which fails.
        GeographicSiteRefDTO geographicSiteRefDTO = geographicSiteRefMapper.toDto(geographicSiteRef);

        restGeographicSiteRefMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(geographicSiteRefDTO))
            )
            .andExpect(status().isBadRequest());

        List<GeographicSiteRef> geographicSiteRefList = geographicSiteRefRepository.findAll();
        assertThat(geographicSiteRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCustConIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = geographicSiteRefRepository.findAll().size();
        // set the field null
        geographicSiteRef.setCustConId(null);

        // Create the GeographicSiteRef, which fails.
        GeographicSiteRefDTO geographicSiteRefDTO = geographicSiteRefMapper.toDto(geographicSiteRef);

        restGeographicSiteRefMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(geographicSiteRefDTO))
            )
            .andExpect(status().isBadRequest());

        List<GeographicSiteRef> geographicSiteRefList = geographicSiteRefRepository.findAll();
        assertThat(geographicSiteRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGeographicSiteRefs() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        // Get all the geographicSiteRefList
        restGeographicSiteRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(geographicSiteRef.getId().intValue())))
            .andExpect(jsonPath("$.[*].siteRef").value(hasItem(DEFAULT_SITE_REF)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].custConId").value(hasItem(DEFAULT_CUST_CON_ID.intValue())));
    }

    @Test
    @Transactional
    void getGeographicSiteRef() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        // Get the geographicSiteRef
        restGeographicSiteRefMockMvc
            .perform(get(ENTITY_API_URL_ID, geographicSiteRef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(geographicSiteRef.getId().intValue()))
            .andExpect(jsonPath("$.siteRef").value(DEFAULT_SITE_REF))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.custConId").value(DEFAULT_CUST_CON_ID.intValue()));
    }

    @Test
    @Transactional
    void getGeographicSiteRefsByIdFiltering() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        Long id = geographicSiteRef.getId();

        defaultGeographicSiteRefShouldBeFound("id.equals=" + id);
        defaultGeographicSiteRefShouldNotBeFound("id.notEquals=" + id);

        defaultGeographicSiteRefShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGeographicSiteRefShouldNotBeFound("id.greaterThan=" + id);

        defaultGeographicSiteRefShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGeographicSiteRefShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllGeographicSiteRefsBySiteRefIsEqualToSomething() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        // Get all the geographicSiteRefList where siteRef equals to DEFAULT_SITE_REF
        defaultGeographicSiteRefShouldBeFound("siteRef.equals=" + DEFAULT_SITE_REF);

        // Get all the geographicSiteRefList where siteRef equals to UPDATED_SITE_REF
        defaultGeographicSiteRefShouldNotBeFound("siteRef.equals=" + UPDATED_SITE_REF);
    }

    @Test
    @Transactional
    void getAllGeographicSiteRefsBySiteRefIsNotEqualToSomething() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        // Get all the geographicSiteRefList where siteRef not equals to DEFAULT_SITE_REF
        defaultGeographicSiteRefShouldNotBeFound("siteRef.notEquals=" + DEFAULT_SITE_REF);

        // Get all the geographicSiteRefList where siteRef not equals to UPDATED_SITE_REF
        defaultGeographicSiteRefShouldBeFound("siteRef.notEquals=" + UPDATED_SITE_REF);
    }

    @Test
    @Transactional
    void getAllGeographicSiteRefsBySiteRefIsInShouldWork() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        // Get all the geographicSiteRefList where siteRef in DEFAULT_SITE_REF or UPDATED_SITE_REF
        defaultGeographicSiteRefShouldBeFound("siteRef.in=" + DEFAULT_SITE_REF + "," + UPDATED_SITE_REF);

        // Get all the geographicSiteRefList where siteRef equals to UPDATED_SITE_REF
        defaultGeographicSiteRefShouldNotBeFound("siteRef.in=" + UPDATED_SITE_REF);
    }

    @Test
    @Transactional
    void getAllGeographicSiteRefsBySiteRefIsNullOrNotNull() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        // Get all the geographicSiteRefList where siteRef is not null
        defaultGeographicSiteRefShouldBeFound("siteRef.specified=true");

        // Get all the geographicSiteRefList where siteRef is null
        defaultGeographicSiteRefShouldNotBeFound("siteRef.specified=false");
    }

    @Test
    @Transactional
    void getAllGeographicSiteRefsBySiteRefContainsSomething() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        // Get all the geographicSiteRefList where siteRef contains DEFAULT_SITE_REF
        defaultGeographicSiteRefShouldBeFound("siteRef.contains=" + DEFAULT_SITE_REF);

        // Get all the geographicSiteRefList where siteRef contains UPDATED_SITE_REF
        defaultGeographicSiteRefShouldNotBeFound("siteRef.contains=" + UPDATED_SITE_REF);
    }

    @Test
    @Transactional
    void getAllGeographicSiteRefsBySiteRefNotContainsSomething() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        // Get all the geographicSiteRefList where siteRef does not contain DEFAULT_SITE_REF
        defaultGeographicSiteRefShouldNotBeFound("siteRef.doesNotContain=" + DEFAULT_SITE_REF);

        // Get all the geographicSiteRefList where siteRef does not contain UPDATED_SITE_REF
        defaultGeographicSiteRefShouldBeFound("siteRef.doesNotContain=" + UPDATED_SITE_REF);
    }

    @Test
    @Transactional
    void getAllGeographicSiteRefsByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        // Get all the geographicSiteRefList where location equals to DEFAULT_LOCATION
        defaultGeographicSiteRefShouldBeFound("location.equals=" + DEFAULT_LOCATION);

        // Get all the geographicSiteRefList where location equals to UPDATED_LOCATION
        defaultGeographicSiteRefShouldNotBeFound("location.equals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllGeographicSiteRefsByLocationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        // Get all the geographicSiteRefList where location not equals to DEFAULT_LOCATION
        defaultGeographicSiteRefShouldNotBeFound("location.notEquals=" + DEFAULT_LOCATION);

        // Get all the geographicSiteRefList where location not equals to UPDATED_LOCATION
        defaultGeographicSiteRefShouldBeFound("location.notEquals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllGeographicSiteRefsByLocationIsInShouldWork() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        // Get all the geographicSiteRefList where location in DEFAULT_LOCATION or UPDATED_LOCATION
        defaultGeographicSiteRefShouldBeFound("location.in=" + DEFAULT_LOCATION + "," + UPDATED_LOCATION);

        // Get all the geographicSiteRefList where location equals to UPDATED_LOCATION
        defaultGeographicSiteRefShouldNotBeFound("location.in=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllGeographicSiteRefsByLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        // Get all the geographicSiteRefList where location is not null
        defaultGeographicSiteRefShouldBeFound("location.specified=true");

        // Get all the geographicSiteRefList where location is null
        defaultGeographicSiteRefShouldNotBeFound("location.specified=false");
    }

    @Test
    @Transactional
    void getAllGeographicSiteRefsByLocationContainsSomething() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        // Get all the geographicSiteRefList where location contains DEFAULT_LOCATION
        defaultGeographicSiteRefShouldBeFound("location.contains=" + DEFAULT_LOCATION);

        // Get all the geographicSiteRefList where location contains UPDATED_LOCATION
        defaultGeographicSiteRefShouldNotBeFound("location.contains=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllGeographicSiteRefsByLocationNotContainsSomething() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        // Get all the geographicSiteRefList where location does not contain DEFAULT_LOCATION
        defaultGeographicSiteRefShouldNotBeFound("location.doesNotContain=" + DEFAULT_LOCATION);

        // Get all the geographicSiteRefList where location does not contain UPDATED_LOCATION
        defaultGeographicSiteRefShouldBeFound("location.doesNotContain=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    void getAllGeographicSiteRefsByCustConIdIsEqualToSomething() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        // Get all the geographicSiteRefList where custConId equals to DEFAULT_CUST_CON_ID
        defaultGeographicSiteRefShouldBeFound("custConId.equals=" + DEFAULT_CUST_CON_ID);

        // Get all the geographicSiteRefList where custConId equals to UPDATED_CUST_CON_ID
        defaultGeographicSiteRefShouldNotBeFound("custConId.equals=" + UPDATED_CUST_CON_ID);
    }

    @Test
    @Transactional
    void getAllGeographicSiteRefsByCustConIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        // Get all the geographicSiteRefList where custConId not equals to DEFAULT_CUST_CON_ID
        defaultGeographicSiteRefShouldNotBeFound("custConId.notEquals=" + DEFAULT_CUST_CON_ID);

        // Get all the geographicSiteRefList where custConId not equals to UPDATED_CUST_CON_ID
        defaultGeographicSiteRefShouldBeFound("custConId.notEquals=" + UPDATED_CUST_CON_ID);
    }

    @Test
    @Transactional
    void getAllGeographicSiteRefsByCustConIdIsInShouldWork() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        // Get all the geographicSiteRefList where custConId in DEFAULT_CUST_CON_ID or UPDATED_CUST_CON_ID
        defaultGeographicSiteRefShouldBeFound("custConId.in=" + DEFAULT_CUST_CON_ID + "," + UPDATED_CUST_CON_ID);

        // Get all the geographicSiteRefList where custConId equals to UPDATED_CUST_CON_ID
        defaultGeographicSiteRefShouldNotBeFound("custConId.in=" + UPDATED_CUST_CON_ID);
    }

    @Test
    @Transactional
    void getAllGeographicSiteRefsByCustConIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        // Get all the geographicSiteRefList where custConId is not null
        defaultGeographicSiteRefShouldBeFound("custConId.specified=true");

        // Get all the geographicSiteRefList where custConId is null
        defaultGeographicSiteRefShouldNotBeFound("custConId.specified=false");
    }

    @Test
    @Transactional
    void getAllGeographicSiteRefsByCustConIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        // Get all the geographicSiteRefList where custConId is greater than or equal to DEFAULT_CUST_CON_ID
        defaultGeographicSiteRefShouldBeFound("custConId.greaterThanOrEqual=" + DEFAULT_CUST_CON_ID);

        // Get all the geographicSiteRefList where custConId is greater than or equal to UPDATED_CUST_CON_ID
        defaultGeographicSiteRefShouldNotBeFound("custConId.greaterThanOrEqual=" + UPDATED_CUST_CON_ID);
    }

    @Test
    @Transactional
    void getAllGeographicSiteRefsByCustConIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        // Get all the geographicSiteRefList where custConId is less than or equal to DEFAULT_CUST_CON_ID
        defaultGeographicSiteRefShouldBeFound("custConId.lessThanOrEqual=" + DEFAULT_CUST_CON_ID);

        // Get all the geographicSiteRefList where custConId is less than or equal to SMALLER_CUST_CON_ID
        defaultGeographicSiteRefShouldNotBeFound("custConId.lessThanOrEqual=" + SMALLER_CUST_CON_ID);
    }

    @Test
    @Transactional
    void getAllGeographicSiteRefsByCustConIdIsLessThanSomething() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        // Get all the geographicSiteRefList where custConId is less than DEFAULT_CUST_CON_ID
        defaultGeographicSiteRefShouldNotBeFound("custConId.lessThan=" + DEFAULT_CUST_CON_ID);

        // Get all the geographicSiteRefList where custConId is less than UPDATED_CUST_CON_ID
        defaultGeographicSiteRefShouldBeFound("custConId.lessThan=" + UPDATED_CUST_CON_ID);
    }

    @Test
    @Transactional
    void getAllGeographicSiteRefsByCustConIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        // Get all the geographicSiteRefList where custConId is greater than DEFAULT_CUST_CON_ID
        defaultGeographicSiteRefShouldNotBeFound("custConId.greaterThan=" + DEFAULT_CUST_CON_ID);

        // Get all the geographicSiteRefList where custConId is greater than SMALLER_CUST_CON_ID
        defaultGeographicSiteRefShouldBeFound("custConId.greaterThan=" + SMALLER_CUST_CON_ID);
    }

    @Test
    @Transactional
    void getAllGeographicSiteRefsByCustContactIsEqualToSomething() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);
        CustContact custContact = CustContactResourceIT.createEntity(em);
        em.persist(custContact);
        em.flush();
        geographicSiteRef.setCustContact(custContact);
        custContact.setGeographicSiteRef(geographicSiteRef);
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);
        Long custContactId = custContact.getId();

        // Get all the geographicSiteRefList where custContact equals to custContactId
        defaultGeographicSiteRefShouldBeFound("custContactId.equals=" + custContactId);

        // Get all the geographicSiteRefList where custContact equals to (custContactId + 1)
        defaultGeographicSiteRefShouldNotBeFound("custContactId.equals=" + (custContactId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGeographicSiteRefShouldBeFound(String filter) throws Exception {
        restGeographicSiteRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(geographicSiteRef.getId().intValue())))
            .andExpect(jsonPath("$.[*].siteRef").value(hasItem(DEFAULT_SITE_REF)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].custConId").value(hasItem(DEFAULT_CUST_CON_ID.intValue())));

        // Check, that the count call also returns 1
        restGeographicSiteRefMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGeographicSiteRefShouldNotBeFound(String filter) throws Exception {
        restGeographicSiteRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGeographicSiteRefMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingGeographicSiteRef() throws Exception {
        // Get the geographicSiteRef
        restGeographicSiteRefMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGeographicSiteRef() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        int databaseSizeBeforeUpdate = geographicSiteRefRepository.findAll().size();

        // Update the geographicSiteRef
        GeographicSiteRef updatedGeographicSiteRef = geographicSiteRefRepository.findById(geographicSiteRef.getId()).get();
        // Disconnect from session so that the updates on updatedGeographicSiteRef are not directly saved in db
        em.detach(updatedGeographicSiteRef);
        updatedGeographicSiteRef.siteRef(UPDATED_SITE_REF).location(UPDATED_LOCATION).custConId(UPDATED_CUST_CON_ID);
        GeographicSiteRefDTO geographicSiteRefDTO = geographicSiteRefMapper.toDto(updatedGeographicSiteRef);

        restGeographicSiteRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, geographicSiteRefDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(geographicSiteRefDTO))
            )
            .andExpect(status().isOk());

        // Validate the GeographicSiteRef in the database
        List<GeographicSiteRef> geographicSiteRefList = geographicSiteRefRepository.findAll();
        assertThat(geographicSiteRefList).hasSize(databaseSizeBeforeUpdate);
        GeographicSiteRef testGeographicSiteRef = geographicSiteRefList.get(geographicSiteRefList.size() - 1);
        assertThat(testGeographicSiteRef.getSiteRef()).isEqualTo(UPDATED_SITE_REF);
        assertThat(testGeographicSiteRef.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testGeographicSiteRef.getCustConId()).isEqualTo(UPDATED_CUST_CON_ID);
    }

    @Test
    @Transactional
    void putNonExistingGeographicSiteRef() throws Exception {
        int databaseSizeBeforeUpdate = geographicSiteRefRepository.findAll().size();
        geographicSiteRef.setId(count.incrementAndGet());

        // Create the GeographicSiteRef
        GeographicSiteRefDTO geographicSiteRefDTO = geographicSiteRefMapper.toDto(geographicSiteRef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeographicSiteRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, geographicSiteRefDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(geographicSiteRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GeographicSiteRef in the database
        List<GeographicSiteRef> geographicSiteRefList = geographicSiteRefRepository.findAll();
        assertThat(geographicSiteRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGeographicSiteRef() throws Exception {
        int databaseSizeBeforeUpdate = geographicSiteRefRepository.findAll().size();
        geographicSiteRef.setId(count.incrementAndGet());

        // Create the GeographicSiteRef
        GeographicSiteRefDTO geographicSiteRefDTO = geographicSiteRefMapper.toDto(geographicSiteRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeographicSiteRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(geographicSiteRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GeographicSiteRef in the database
        List<GeographicSiteRef> geographicSiteRefList = geographicSiteRefRepository.findAll();
        assertThat(geographicSiteRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGeographicSiteRef() throws Exception {
        int databaseSizeBeforeUpdate = geographicSiteRefRepository.findAll().size();
        geographicSiteRef.setId(count.incrementAndGet());

        // Create the GeographicSiteRef
        GeographicSiteRefDTO geographicSiteRefDTO = geographicSiteRefMapper.toDto(geographicSiteRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeographicSiteRefMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(geographicSiteRefDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GeographicSiteRef in the database
        List<GeographicSiteRef> geographicSiteRefList = geographicSiteRefRepository.findAll();
        assertThat(geographicSiteRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGeographicSiteRefWithPatch() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        int databaseSizeBeforeUpdate = geographicSiteRefRepository.findAll().size();

        // Update the geographicSiteRef using partial update
        GeographicSiteRef partialUpdatedGeographicSiteRef = new GeographicSiteRef();
        partialUpdatedGeographicSiteRef.setId(geographicSiteRef.getId());

        partialUpdatedGeographicSiteRef.siteRef(UPDATED_SITE_REF).location(UPDATED_LOCATION);

        restGeographicSiteRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGeographicSiteRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGeographicSiteRef))
            )
            .andExpect(status().isOk());

        // Validate the GeographicSiteRef in the database
        List<GeographicSiteRef> geographicSiteRefList = geographicSiteRefRepository.findAll();
        assertThat(geographicSiteRefList).hasSize(databaseSizeBeforeUpdate);
        GeographicSiteRef testGeographicSiteRef = geographicSiteRefList.get(geographicSiteRefList.size() - 1);
        assertThat(testGeographicSiteRef.getSiteRef()).isEqualTo(UPDATED_SITE_REF);
        assertThat(testGeographicSiteRef.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testGeographicSiteRef.getCustConId()).isEqualTo(DEFAULT_CUST_CON_ID);
    }

    @Test
    @Transactional
    void fullUpdateGeographicSiteRefWithPatch() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        int databaseSizeBeforeUpdate = geographicSiteRefRepository.findAll().size();

        // Update the geographicSiteRef using partial update
        GeographicSiteRef partialUpdatedGeographicSiteRef = new GeographicSiteRef();
        partialUpdatedGeographicSiteRef.setId(geographicSiteRef.getId());

        partialUpdatedGeographicSiteRef.siteRef(UPDATED_SITE_REF).location(UPDATED_LOCATION).custConId(UPDATED_CUST_CON_ID);

        restGeographicSiteRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGeographicSiteRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGeographicSiteRef))
            )
            .andExpect(status().isOk());

        // Validate the GeographicSiteRef in the database
        List<GeographicSiteRef> geographicSiteRefList = geographicSiteRefRepository.findAll();
        assertThat(geographicSiteRefList).hasSize(databaseSizeBeforeUpdate);
        GeographicSiteRef testGeographicSiteRef = geographicSiteRefList.get(geographicSiteRefList.size() - 1);
        assertThat(testGeographicSiteRef.getSiteRef()).isEqualTo(UPDATED_SITE_REF);
        assertThat(testGeographicSiteRef.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testGeographicSiteRef.getCustConId()).isEqualTo(UPDATED_CUST_CON_ID);
    }

    @Test
    @Transactional
    void patchNonExistingGeographicSiteRef() throws Exception {
        int databaseSizeBeforeUpdate = geographicSiteRefRepository.findAll().size();
        geographicSiteRef.setId(count.incrementAndGet());

        // Create the GeographicSiteRef
        GeographicSiteRefDTO geographicSiteRefDTO = geographicSiteRefMapper.toDto(geographicSiteRef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeographicSiteRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, geographicSiteRefDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(geographicSiteRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GeographicSiteRef in the database
        List<GeographicSiteRef> geographicSiteRefList = geographicSiteRefRepository.findAll();
        assertThat(geographicSiteRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGeographicSiteRef() throws Exception {
        int databaseSizeBeforeUpdate = geographicSiteRefRepository.findAll().size();
        geographicSiteRef.setId(count.incrementAndGet());

        // Create the GeographicSiteRef
        GeographicSiteRefDTO geographicSiteRefDTO = geographicSiteRefMapper.toDto(geographicSiteRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeographicSiteRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(geographicSiteRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GeographicSiteRef in the database
        List<GeographicSiteRef> geographicSiteRefList = geographicSiteRefRepository.findAll();
        assertThat(geographicSiteRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGeographicSiteRef() throws Exception {
        int databaseSizeBeforeUpdate = geographicSiteRefRepository.findAll().size();
        geographicSiteRef.setId(count.incrementAndGet());

        // Create the GeographicSiteRef
        GeographicSiteRefDTO geographicSiteRefDTO = geographicSiteRefMapper.toDto(geographicSiteRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeographicSiteRefMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(geographicSiteRefDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GeographicSiteRef in the database
        List<GeographicSiteRef> geographicSiteRefList = geographicSiteRefRepository.findAll();
        assertThat(geographicSiteRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGeographicSiteRef() throws Exception {
        // Initialize the database
        geographicSiteRefRepository.saveAndFlush(geographicSiteRef);

        int databaseSizeBeforeDelete = geographicSiteRefRepository.findAll().size();

        // Delete the geographicSiteRef
        restGeographicSiteRefMockMvc
            .perform(delete(ENTITY_API_URL_ID, geographicSiteRef.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GeographicSiteRef> geographicSiteRefList = geographicSiteRefRepository.findAll();
        assertThat(geographicSiteRefList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
