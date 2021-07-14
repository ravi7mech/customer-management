package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.IndNewsLetterConf;
import com.apptium.customer.domain.Individual;
import com.apptium.customer.domain.NewsLetterType;
import com.apptium.customer.repository.IndNewsLetterConfRepository;
import com.apptium.customer.service.criteria.IndNewsLetterConfCriteria;
import com.apptium.customer.service.dto.IndNewsLetterConfDTO;
import com.apptium.customer.service.mapper.IndNewsLetterConfMapper;
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
 * Integration tests for the {@link IndNewsLetterConfResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IndNewsLetterConfResourceIT {

    private static final Long DEFAULT_NEW_LETTER_TYPE_ID = 1L;
    private static final Long UPDATED_NEW_LETTER_TYPE_ID = 2L;
    private static final Long SMALLER_NEW_LETTER_TYPE_ID = 1L - 1L;

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final Long DEFAULT_INDIVIDUAL_ID = 1L;
    private static final Long UPDATED_INDIVIDUAL_ID = 2L;
    private static final Long SMALLER_INDIVIDUAL_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/ind-news-letter-confs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IndNewsLetterConfRepository indNewsLetterConfRepository;

    @Autowired
    private IndNewsLetterConfMapper indNewsLetterConfMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndNewsLetterConfMockMvc;

    private IndNewsLetterConf indNewsLetterConf;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndNewsLetterConf createEntity(EntityManager em) {
        IndNewsLetterConf indNewsLetterConf = new IndNewsLetterConf()
            .newLetterTypeId(DEFAULT_NEW_LETTER_TYPE_ID)
            .value(DEFAULT_VALUE)
            .individualId(DEFAULT_INDIVIDUAL_ID);
        return indNewsLetterConf;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndNewsLetterConf createUpdatedEntity(EntityManager em) {
        IndNewsLetterConf indNewsLetterConf = new IndNewsLetterConf()
            .newLetterTypeId(UPDATED_NEW_LETTER_TYPE_ID)
            .value(UPDATED_VALUE)
            .individualId(UPDATED_INDIVIDUAL_ID);
        return indNewsLetterConf;
    }

    @BeforeEach
    public void initTest() {
        indNewsLetterConf = createEntity(em);
    }

    @Test
    @Transactional
    void createIndNewsLetterConf() throws Exception {
        int databaseSizeBeforeCreate = indNewsLetterConfRepository.findAll().size();
        // Create the IndNewsLetterConf
        IndNewsLetterConfDTO indNewsLetterConfDTO = indNewsLetterConfMapper.toDto(indNewsLetterConf);
        restIndNewsLetterConfMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indNewsLetterConfDTO))
            )
            .andExpect(status().isCreated());

        // Validate the IndNewsLetterConf in the database
        List<IndNewsLetterConf> indNewsLetterConfList = indNewsLetterConfRepository.findAll();
        assertThat(indNewsLetterConfList).hasSize(databaseSizeBeforeCreate + 1);
        IndNewsLetterConf testIndNewsLetterConf = indNewsLetterConfList.get(indNewsLetterConfList.size() - 1);
        assertThat(testIndNewsLetterConf.getNewLetterTypeId()).isEqualTo(DEFAULT_NEW_LETTER_TYPE_ID);
        assertThat(testIndNewsLetterConf.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testIndNewsLetterConf.getIndividualId()).isEqualTo(DEFAULT_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void createIndNewsLetterConfWithExistingId() throws Exception {
        // Create the IndNewsLetterConf with an existing ID
        indNewsLetterConf.setId(1L);
        IndNewsLetterConfDTO indNewsLetterConfDTO = indNewsLetterConfMapper.toDto(indNewsLetterConf);

        int databaseSizeBeforeCreate = indNewsLetterConfRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndNewsLetterConfMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indNewsLetterConfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndNewsLetterConf in the database
        List<IndNewsLetterConf> indNewsLetterConfList = indNewsLetterConfRepository.findAll();
        assertThat(indNewsLetterConfList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNewLetterTypeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = indNewsLetterConfRepository.findAll().size();
        // set the field null
        indNewsLetterConf.setNewLetterTypeId(null);

        // Create the IndNewsLetterConf, which fails.
        IndNewsLetterConfDTO indNewsLetterConfDTO = indNewsLetterConfMapper.toDto(indNewsLetterConf);

        restIndNewsLetterConfMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indNewsLetterConfDTO))
            )
            .andExpect(status().isBadRequest());

        List<IndNewsLetterConf> indNewsLetterConfList = indNewsLetterConfRepository.findAll();
        assertThat(indNewsLetterConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = indNewsLetterConfRepository.findAll().size();
        // set the field null
        indNewsLetterConf.setValue(null);

        // Create the IndNewsLetterConf, which fails.
        IndNewsLetterConfDTO indNewsLetterConfDTO = indNewsLetterConfMapper.toDto(indNewsLetterConf);

        restIndNewsLetterConfMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indNewsLetterConfDTO))
            )
            .andExpect(status().isBadRequest());

        List<IndNewsLetterConf> indNewsLetterConfList = indNewsLetterConfRepository.findAll();
        assertThat(indNewsLetterConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIndividualIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = indNewsLetterConfRepository.findAll().size();
        // set the field null
        indNewsLetterConf.setIndividualId(null);

        // Create the IndNewsLetterConf, which fails.
        IndNewsLetterConfDTO indNewsLetterConfDTO = indNewsLetterConfMapper.toDto(indNewsLetterConf);

        restIndNewsLetterConfMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indNewsLetterConfDTO))
            )
            .andExpect(status().isBadRequest());

        List<IndNewsLetterConf> indNewsLetterConfList = indNewsLetterConfRepository.findAll();
        assertThat(indNewsLetterConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIndNewsLetterConfs() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        // Get all the indNewsLetterConfList
        restIndNewsLetterConfMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indNewsLetterConf.getId().intValue())))
            .andExpect(jsonPath("$.[*].newLetterTypeId").value(hasItem(DEFAULT_NEW_LETTER_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].individualId").value(hasItem(DEFAULT_INDIVIDUAL_ID.intValue())));
    }

    @Test
    @Transactional
    void getIndNewsLetterConf() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        // Get the indNewsLetterConf
        restIndNewsLetterConfMockMvc
            .perform(get(ENTITY_API_URL_ID, indNewsLetterConf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(indNewsLetterConf.getId().intValue()))
            .andExpect(jsonPath("$.newLetterTypeId").value(DEFAULT_NEW_LETTER_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.individualId").value(DEFAULT_INDIVIDUAL_ID.intValue()));
    }

    @Test
    @Transactional
    void getIndNewsLetterConfsByIdFiltering() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        Long id = indNewsLetterConf.getId();

        defaultIndNewsLetterConfShouldBeFound("id.equals=" + id);
        defaultIndNewsLetterConfShouldNotBeFound("id.notEquals=" + id);

        defaultIndNewsLetterConfShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultIndNewsLetterConfShouldNotBeFound("id.greaterThan=" + id);

        defaultIndNewsLetterConfShouldBeFound("id.lessThanOrEqual=" + id);
        defaultIndNewsLetterConfShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllIndNewsLetterConfsByNewLetterTypeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        // Get all the indNewsLetterConfList where newLetterTypeId equals to DEFAULT_NEW_LETTER_TYPE_ID
        defaultIndNewsLetterConfShouldBeFound("newLetterTypeId.equals=" + DEFAULT_NEW_LETTER_TYPE_ID);

        // Get all the indNewsLetterConfList where newLetterTypeId equals to UPDATED_NEW_LETTER_TYPE_ID
        defaultIndNewsLetterConfShouldNotBeFound("newLetterTypeId.equals=" + UPDATED_NEW_LETTER_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllIndNewsLetterConfsByNewLetterTypeIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        // Get all the indNewsLetterConfList where newLetterTypeId not equals to DEFAULT_NEW_LETTER_TYPE_ID
        defaultIndNewsLetterConfShouldNotBeFound("newLetterTypeId.notEquals=" + DEFAULT_NEW_LETTER_TYPE_ID);

        // Get all the indNewsLetterConfList where newLetterTypeId not equals to UPDATED_NEW_LETTER_TYPE_ID
        defaultIndNewsLetterConfShouldBeFound("newLetterTypeId.notEquals=" + UPDATED_NEW_LETTER_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllIndNewsLetterConfsByNewLetterTypeIdIsInShouldWork() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        // Get all the indNewsLetterConfList where newLetterTypeId in DEFAULT_NEW_LETTER_TYPE_ID or UPDATED_NEW_LETTER_TYPE_ID
        defaultIndNewsLetterConfShouldBeFound("newLetterTypeId.in=" + DEFAULT_NEW_LETTER_TYPE_ID + "," + UPDATED_NEW_LETTER_TYPE_ID);

        // Get all the indNewsLetterConfList where newLetterTypeId equals to UPDATED_NEW_LETTER_TYPE_ID
        defaultIndNewsLetterConfShouldNotBeFound("newLetterTypeId.in=" + UPDATED_NEW_LETTER_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllIndNewsLetterConfsByNewLetterTypeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        // Get all the indNewsLetterConfList where newLetterTypeId is not null
        defaultIndNewsLetterConfShouldBeFound("newLetterTypeId.specified=true");

        // Get all the indNewsLetterConfList where newLetterTypeId is null
        defaultIndNewsLetterConfShouldNotBeFound("newLetterTypeId.specified=false");
    }

    @Test
    @Transactional
    void getAllIndNewsLetterConfsByNewLetterTypeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        // Get all the indNewsLetterConfList where newLetterTypeId is greater than or equal to DEFAULT_NEW_LETTER_TYPE_ID
        defaultIndNewsLetterConfShouldBeFound("newLetterTypeId.greaterThanOrEqual=" + DEFAULT_NEW_LETTER_TYPE_ID);

        // Get all the indNewsLetterConfList where newLetterTypeId is greater than or equal to UPDATED_NEW_LETTER_TYPE_ID
        defaultIndNewsLetterConfShouldNotBeFound("newLetterTypeId.greaterThanOrEqual=" + UPDATED_NEW_LETTER_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllIndNewsLetterConfsByNewLetterTypeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        // Get all the indNewsLetterConfList where newLetterTypeId is less than or equal to DEFAULT_NEW_LETTER_TYPE_ID
        defaultIndNewsLetterConfShouldBeFound("newLetterTypeId.lessThanOrEqual=" + DEFAULT_NEW_LETTER_TYPE_ID);

        // Get all the indNewsLetterConfList where newLetterTypeId is less than or equal to SMALLER_NEW_LETTER_TYPE_ID
        defaultIndNewsLetterConfShouldNotBeFound("newLetterTypeId.lessThanOrEqual=" + SMALLER_NEW_LETTER_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllIndNewsLetterConfsByNewLetterTypeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        // Get all the indNewsLetterConfList where newLetterTypeId is less than DEFAULT_NEW_LETTER_TYPE_ID
        defaultIndNewsLetterConfShouldNotBeFound("newLetterTypeId.lessThan=" + DEFAULT_NEW_LETTER_TYPE_ID);

        // Get all the indNewsLetterConfList where newLetterTypeId is less than UPDATED_NEW_LETTER_TYPE_ID
        defaultIndNewsLetterConfShouldBeFound("newLetterTypeId.lessThan=" + UPDATED_NEW_LETTER_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllIndNewsLetterConfsByNewLetterTypeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        // Get all the indNewsLetterConfList where newLetterTypeId is greater than DEFAULT_NEW_LETTER_TYPE_ID
        defaultIndNewsLetterConfShouldNotBeFound("newLetterTypeId.greaterThan=" + DEFAULT_NEW_LETTER_TYPE_ID);

        // Get all the indNewsLetterConfList where newLetterTypeId is greater than SMALLER_NEW_LETTER_TYPE_ID
        defaultIndNewsLetterConfShouldBeFound("newLetterTypeId.greaterThan=" + SMALLER_NEW_LETTER_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllIndNewsLetterConfsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        // Get all the indNewsLetterConfList where value equals to DEFAULT_VALUE
        defaultIndNewsLetterConfShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the indNewsLetterConfList where value equals to UPDATED_VALUE
        defaultIndNewsLetterConfShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllIndNewsLetterConfsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        // Get all the indNewsLetterConfList where value not equals to DEFAULT_VALUE
        defaultIndNewsLetterConfShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the indNewsLetterConfList where value not equals to UPDATED_VALUE
        defaultIndNewsLetterConfShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllIndNewsLetterConfsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        // Get all the indNewsLetterConfList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultIndNewsLetterConfShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the indNewsLetterConfList where value equals to UPDATED_VALUE
        defaultIndNewsLetterConfShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllIndNewsLetterConfsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        // Get all the indNewsLetterConfList where value is not null
        defaultIndNewsLetterConfShouldBeFound("value.specified=true");

        // Get all the indNewsLetterConfList where value is null
        defaultIndNewsLetterConfShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    void getAllIndNewsLetterConfsByValueContainsSomething() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        // Get all the indNewsLetterConfList where value contains DEFAULT_VALUE
        defaultIndNewsLetterConfShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the indNewsLetterConfList where value contains UPDATED_VALUE
        defaultIndNewsLetterConfShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllIndNewsLetterConfsByValueNotContainsSomething() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        // Get all the indNewsLetterConfList where value does not contain DEFAULT_VALUE
        defaultIndNewsLetterConfShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the indNewsLetterConfList where value does not contain UPDATED_VALUE
        defaultIndNewsLetterConfShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllIndNewsLetterConfsByIndividualIdIsEqualToSomething() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        // Get all the indNewsLetterConfList where individualId equals to DEFAULT_INDIVIDUAL_ID
        defaultIndNewsLetterConfShouldBeFound("individualId.equals=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the indNewsLetterConfList where individualId equals to UPDATED_INDIVIDUAL_ID
        defaultIndNewsLetterConfShouldNotBeFound("individualId.equals=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndNewsLetterConfsByIndividualIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        // Get all the indNewsLetterConfList where individualId not equals to DEFAULT_INDIVIDUAL_ID
        defaultIndNewsLetterConfShouldNotBeFound("individualId.notEquals=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the indNewsLetterConfList where individualId not equals to UPDATED_INDIVIDUAL_ID
        defaultIndNewsLetterConfShouldBeFound("individualId.notEquals=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndNewsLetterConfsByIndividualIdIsInShouldWork() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        // Get all the indNewsLetterConfList where individualId in DEFAULT_INDIVIDUAL_ID or UPDATED_INDIVIDUAL_ID
        defaultIndNewsLetterConfShouldBeFound("individualId.in=" + DEFAULT_INDIVIDUAL_ID + "," + UPDATED_INDIVIDUAL_ID);

        // Get all the indNewsLetterConfList where individualId equals to UPDATED_INDIVIDUAL_ID
        defaultIndNewsLetterConfShouldNotBeFound("individualId.in=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndNewsLetterConfsByIndividualIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        // Get all the indNewsLetterConfList where individualId is not null
        defaultIndNewsLetterConfShouldBeFound("individualId.specified=true");

        // Get all the indNewsLetterConfList where individualId is null
        defaultIndNewsLetterConfShouldNotBeFound("individualId.specified=false");
    }

    @Test
    @Transactional
    void getAllIndNewsLetterConfsByIndividualIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        // Get all the indNewsLetterConfList where individualId is greater than or equal to DEFAULT_INDIVIDUAL_ID
        defaultIndNewsLetterConfShouldBeFound("individualId.greaterThanOrEqual=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the indNewsLetterConfList where individualId is greater than or equal to UPDATED_INDIVIDUAL_ID
        defaultIndNewsLetterConfShouldNotBeFound("individualId.greaterThanOrEqual=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndNewsLetterConfsByIndividualIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        // Get all the indNewsLetterConfList where individualId is less than or equal to DEFAULT_INDIVIDUAL_ID
        defaultIndNewsLetterConfShouldBeFound("individualId.lessThanOrEqual=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the indNewsLetterConfList where individualId is less than or equal to SMALLER_INDIVIDUAL_ID
        defaultIndNewsLetterConfShouldNotBeFound("individualId.lessThanOrEqual=" + SMALLER_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndNewsLetterConfsByIndividualIdIsLessThanSomething() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        // Get all the indNewsLetterConfList where individualId is less than DEFAULT_INDIVIDUAL_ID
        defaultIndNewsLetterConfShouldNotBeFound("individualId.lessThan=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the indNewsLetterConfList where individualId is less than UPDATED_INDIVIDUAL_ID
        defaultIndNewsLetterConfShouldBeFound("individualId.lessThan=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndNewsLetterConfsByIndividualIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        // Get all the indNewsLetterConfList where individualId is greater than DEFAULT_INDIVIDUAL_ID
        defaultIndNewsLetterConfShouldNotBeFound("individualId.greaterThan=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the indNewsLetterConfList where individualId is greater than SMALLER_INDIVIDUAL_ID
        defaultIndNewsLetterConfShouldBeFound("individualId.greaterThan=" + SMALLER_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndNewsLetterConfsByIndividualIsEqualToSomething() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);
        Individual individual = IndividualResourceIT.createEntity(em);
        em.persist(individual);
        em.flush();
        indNewsLetterConf.setIndividual(individual);
        individual.setIndNewsLetterConf(indNewsLetterConf);
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);
        Long individualId = individual.getId();

        // Get all the indNewsLetterConfList where individual equals to individualId
        defaultIndNewsLetterConfShouldBeFound("individualId.equals=" + individualId);

        // Get all the indNewsLetterConfList where individual equals to (individualId + 1)
        defaultIndNewsLetterConfShouldNotBeFound("individualId.equals=" + (individualId + 1));
    }

    @Test
    @Transactional
    void getAllIndNewsLetterConfsByNewsLetterTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);
        NewsLetterType newsLetterType = NewsLetterTypeResourceIT.createEntity(em);
        em.persist(newsLetterType);
        em.flush();
        indNewsLetterConf.setNewsLetterType(newsLetterType);
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);
        Long newsLetterTypeId = newsLetterType.getId();

        // Get all the indNewsLetterConfList where newsLetterType equals to newsLetterTypeId
        defaultIndNewsLetterConfShouldBeFound("newsLetterTypeId.equals=" + newsLetterTypeId);

        // Get all the indNewsLetterConfList where newsLetterType equals to (newsLetterTypeId + 1)
        defaultIndNewsLetterConfShouldNotBeFound("newsLetterTypeId.equals=" + (newsLetterTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultIndNewsLetterConfShouldBeFound(String filter) throws Exception {
        restIndNewsLetterConfMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indNewsLetterConf.getId().intValue())))
            .andExpect(jsonPath("$.[*].newLetterTypeId").value(hasItem(DEFAULT_NEW_LETTER_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].individualId").value(hasItem(DEFAULT_INDIVIDUAL_ID.intValue())));

        // Check, that the count call also returns 1
        restIndNewsLetterConfMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultIndNewsLetterConfShouldNotBeFound(String filter) throws Exception {
        restIndNewsLetterConfMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIndNewsLetterConfMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingIndNewsLetterConf() throws Exception {
        // Get the indNewsLetterConf
        restIndNewsLetterConfMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIndNewsLetterConf() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        int databaseSizeBeforeUpdate = indNewsLetterConfRepository.findAll().size();

        // Update the indNewsLetterConf
        IndNewsLetterConf updatedIndNewsLetterConf = indNewsLetterConfRepository.findById(indNewsLetterConf.getId()).get();
        // Disconnect from session so that the updates on updatedIndNewsLetterConf are not directly saved in db
        em.detach(updatedIndNewsLetterConf);
        updatedIndNewsLetterConf.newLetterTypeId(UPDATED_NEW_LETTER_TYPE_ID).value(UPDATED_VALUE).individualId(UPDATED_INDIVIDUAL_ID);
        IndNewsLetterConfDTO indNewsLetterConfDTO = indNewsLetterConfMapper.toDto(updatedIndNewsLetterConf);

        restIndNewsLetterConfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indNewsLetterConfDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indNewsLetterConfDTO))
            )
            .andExpect(status().isOk());

        // Validate the IndNewsLetterConf in the database
        List<IndNewsLetterConf> indNewsLetterConfList = indNewsLetterConfRepository.findAll();
        assertThat(indNewsLetterConfList).hasSize(databaseSizeBeforeUpdate);
        IndNewsLetterConf testIndNewsLetterConf = indNewsLetterConfList.get(indNewsLetterConfList.size() - 1);
        assertThat(testIndNewsLetterConf.getNewLetterTypeId()).isEqualTo(UPDATED_NEW_LETTER_TYPE_ID);
        assertThat(testIndNewsLetterConf.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testIndNewsLetterConf.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void putNonExistingIndNewsLetterConf() throws Exception {
        int databaseSizeBeforeUpdate = indNewsLetterConfRepository.findAll().size();
        indNewsLetterConf.setId(count.incrementAndGet());

        // Create the IndNewsLetterConf
        IndNewsLetterConfDTO indNewsLetterConfDTO = indNewsLetterConfMapper.toDto(indNewsLetterConf);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndNewsLetterConfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indNewsLetterConfDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indNewsLetterConfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndNewsLetterConf in the database
        List<IndNewsLetterConf> indNewsLetterConfList = indNewsLetterConfRepository.findAll();
        assertThat(indNewsLetterConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIndNewsLetterConf() throws Exception {
        int databaseSizeBeforeUpdate = indNewsLetterConfRepository.findAll().size();
        indNewsLetterConf.setId(count.incrementAndGet());

        // Create the IndNewsLetterConf
        IndNewsLetterConfDTO indNewsLetterConfDTO = indNewsLetterConfMapper.toDto(indNewsLetterConf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndNewsLetterConfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indNewsLetterConfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndNewsLetterConf in the database
        List<IndNewsLetterConf> indNewsLetterConfList = indNewsLetterConfRepository.findAll();
        assertThat(indNewsLetterConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIndNewsLetterConf() throws Exception {
        int databaseSizeBeforeUpdate = indNewsLetterConfRepository.findAll().size();
        indNewsLetterConf.setId(count.incrementAndGet());

        // Create the IndNewsLetterConf
        IndNewsLetterConfDTO indNewsLetterConfDTO = indNewsLetterConfMapper.toDto(indNewsLetterConf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndNewsLetterConfMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indNewsLetterConfDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IndNewsLetterConf in the database
        List<IndNewsLetterConf> indNewsLetterConfList = indNewsLetterConfRepository.findAll();
        assertThat(indNewsLetterConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIndNewsLetterConfWithPatch() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        int databaseSizeBeforeUpdate = indNewsLetterConfRepository.findAll().size();

        // Update the indNewsLetterConf using partial update
        IndNewsLetterConf partialUpdatedIndNewsLetterConf = new IndNewsLetterConf();
        partialUpdatedIndNewsLetterConf.setId(indNewsLetterConf.getId());

        partialUpdatedIndNewsLetterConf
            .newLetterTypeId(UPDATED_NEW_LETTER_TYPE_ID)
            .value(UPDATED_VALUE)
            .individualId(UPDATED_INDIVIDUAL_ID);

        restIndNewsLetterConfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndNewsLetterConf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndNewsLetterConf))
            )
            .andExpect(status().isOk());

        // Validate the IndNewsLetterConf in the database
        List<IndNewsLetterConf> indNewsLetterConfList = indNewsLetterConfRepository.findAll();
        assertThat(indNewsLetterConfList).hasSize(databaseSizeBeforeUpdate);
        IndNewsLetterConf testIndNewsLetterConf = indNewsLetterConfList.get(indNewsLetterConfList.size() - 1);
        assertThat(testIndNewsLetterConf.getNewLetterTypeId()).isEqualTo(UPDATED_NEW_LETTER_TYPE_ID);
        assertThat(testIndNewsLetterConf.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testIndNewsLetterConf.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void fullUpdateIndNewsLetterConfWithPatch() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        int databaseSizeBeforeUpdate = indNewsLetterConfRepository.findAll().size();

        // Update the indNewsLetterConf using partial update
        IndNewsLetterConf partialUpdatedIndNewsLetterConf = new IndNewsLetterConf();
        partialUpdatedIndNewsLetterConf.setId(indNewsLetterConf.getId());

        partialUpdatedIndNewsLetterConf
            .newLetterTypeId(UPDATED_NEW_LETTER_TYPE_ID)
            .value(UPDATED_VALUE)
            .individualId(UPDATED_INDIVIDUAL_ID);

        restIndNewsLetterConfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndNewsLetterConf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndNewsLetterConf))
            )
            .andExpect(status().isOk());

        // Validate the IndNewsLetterConf in the database
        List<IndNewsLetterConf> indNewsLetterConfList = indNewsLetterConfRepository.findAll();
        assertThat(indNewsLetterConfList).hasSize(databaseSizeBeforeUpdate);
        IndNewsLetterConf testIndNewsLetterConf = indNewsLetterConfList.get(indNewsLetterConfList.size() - 1);
        assertThat(testIndNewsLetterConf.getNewLetterTypeId()).isEqualTo(UPDATED_NEW_LETTER_TYPE_ID);
        assertThat(testIndNewsLetterConf.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testIndNewsLetterConf.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void patchNonExistingIndNewsLetterConf() throws Exception {
        int databaseSizeBeforeUpdate = indNewsLetterConfRepository.findAll().size();
        indNewsLetterConf.setId(count.incrementAndGet());

        // Create the IndNewsLetterConf
        IndNewsLetterConfDTO indNewsLetterConfDTO = indNewsLetterConfMapper.toDto(indNewsLetterConf);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndNewsLetterConfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, indNewsLetterConfDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indNewsLetterConfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndNewsLetterConf in the database
        List<IndNewsLetterConf> indNewsLetterConfList = indNewsLetterConfRepository.findAll();
        assertThat(indNewsLetterConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIndNewsLetterConf() throws Exception {
        int databaseSizeBeforeUpdate = indNewsLetterConfRepository.findAll().size();
        indNewsLetterConf.setId(count.incrementAndGet());

        // Create the IndNewsLetterConf
        IndNewsLetterConfDTO indNewsLetterConfDTO = indNewsLetterConfMapper.toDto(indNewsLetterConf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndNewsLetterConfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indNewsLetterConfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndNewsLetterConf in the database
        List<IndNewsLetterConf> indNewsLetterConfList = indNewsLetterConfRepository.findAll();
        assertThat(indNewsLetterConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIndNewsLetterConf() throws Exception {
        int databaseSizeBeforeUpdate = indNewsLetterConfRepository.findAll().size();
        indNewsLetterConf.setId(count.incrementAndGet());

        // Create the IndNewsLetterConf
        IndNewsLetterConfDTO indNewsLetterConfDTO = indNewsLetterConfMapper.toDto(indNewsLetterConf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndNewsLetterConfMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indNewsLetterConfDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IndNewsLetterConf in the database
        List<IndNewsLetterConf> indNewsLetterConfList = indNewsLetterConfRepository.findAll();
        assertThat(indNewsLetterConfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIndNewsLetterConf() throws Exception {
        // Initialize the database
        indNewsLetterConfRepository.saveAndFlush(indNewsLetterConf);

        int databaseSizeBeforeDelete = indNewsLetterConfRepository.findAll().size();

        // Delete the indNewsLetterConf
        restIndNewsLetterConfMockMvc
            .perform(delete(ENTITY_API_URL_ID, indNewsLetterConf.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IndNewsLetterConf> indNewsLetterConfList = indNewsLetterConfRepository.findAll();
        assertThat(indNewsLetterConfList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
