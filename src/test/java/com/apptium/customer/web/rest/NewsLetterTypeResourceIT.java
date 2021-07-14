package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.CustNewsLetterConfig;
import com.apptium.customer.domain.IndNewsLetterConf;
import com.apptium.customer.domain.NewsLetterType;
import com.apptium.customer.repository.NewsLetterTypeRepository;
import com.apptium.customer.service.criteria.NewsLetterTypeCriteria;
import com.apptium.customer.service.dto.NewsLetterTypeDTO;
import com.apptium.customer.service.mapper.NewsLetterTypeMapper;
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
 * Integration tests for the {@link NewsLetterTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NewsLetterTypeResourceIT {

    private static final String DEFAULT_NEW_LETTER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_NEW_LETTER_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/news-letter-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NewsLetterTypeRepository newsLetterTypeRepository;

    @Autowired
    private NewsLetterTypeMapper newsLetterTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNewsLetterTypeMockMvc;

    private NewsLetterType newsLetterType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NewsLetterType createEntity(EntityManager em) {
        NewsLetterType newsLetterType = new NewsLetterType()
            .newLetterType(DEFAULT_NEW_LETTER_TYPE)
            .displayValue(DEFAULT_DISPLAY_VALUE)
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS);
        return newsLetterType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NewsLetterType createUpdatedEntity(EntityManager em) {
        NewsLetterType newsLetterType = new NewsLetterType()
            .newLetterType(UPDATED_NEW_LETTER_TYPE)
            .displayValue(UPDATED_DISPLAY_VALUE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS);
        return newsLetterType;
    }

    @BeforeEach
    public void initTest() {
        newsLetterType = createEntity(em);
    }

    @Test
    @Transactional
    void createNewsLetterType() throws Exception {
        int databaseSizeBeforeCreate = newsLetterTypeRepository.findAll().size();
        // Create the NewsLetterType
        NewsLetterTypeDTO newsLetterTypeDTO = newsLetterTypeMapper.toDto(newsLetterType);
        restNewsLetterTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsLetterTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the NewsLetterType in the database
        List<NewsLetterType> newsLetterTypeList = newsLetterTypeRepository.findAll();
        assertThat(newsLetterTypeList).hasSize(databaseSizeBeforeCreate + 1);
        NewsLetterType testNewsLetterType = newsLetterTypeList.get(newsLetterTypeList.size() - 1);
        assertThat(testNewsLetterType.getNewLetterType()).isEqualTo(DEFAULT_NEW_LETTER_TYPE);
        assertThat(testNewsLetterType.getDisplayValue()).isEqualTo(DEFAULT_DISPLAY_VALUE);
        assertThat(testNewsLetterType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testNewsLetterType.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createNewsLetterTypeWithExistingId() throws Exception {
        // Create the NewsLetterType with an existing ID
        newsLetterType.setId(1L);
        NewsLetterTypeDTO newsLetterTypeDTO = newsLetterTypeMapper.toDto(newsLetterType);

        int databaseSizeBeforeCreate = newsLetterTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNewsLetterTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsLetterTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NewsLetterType in the database
        List<NewsLetterType> newsLetterTypeList = newsLetterTypeRepository.findAll();
        assertThat(newsLetterTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNewLetterTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = newsLetterTypeRepository.findAll().size();
        // set the field null
        newsLetterType.setNewLetterType(null);

        // Create the NewsLetterType, which fails.
        NewsLetterTypeDTO newsLetterTypeDTO = newsLetterTypeMapper.toDto(newsLetterType);

        restNewsLetterTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsLetterTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<NewsLetterType> newsLetterTypeList = newsLetterTypeRepository.findAll();
        assertThat(newsLetterTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDisplayValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = newsLetterTypeRepository.findAll().size();
        // set the field null
        newsLetterType.setDisplayValue(null);

        // Create the NewsLetterType, which fails.
        NewsLetterTypeDTO newsLetterTypeDTO = newsLetterTypeMapper.toDto(newsLetterType);

        restNewsLetterTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsLetterTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<NewsLetterType> newsLetterTypeList = newsLetterTypeRepository.findAll();
        assertThat(newsLetterTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = newsLetterTypeRepository.findAll().size();
        // set the field null
        newsLetterType.setStatus(null);

        // Create the NewsLetterType, which fails.
        NewsLetterTypeDTO newsLetterTypeDTO = newsLetterTypeMapper.toDto(newsLetterType);

        restNewsLetterTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsLetterTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<NewsLetterType> newsLetterTypeList = newsLetterTypeRepository.findAll();
        assertThat(newsLetterTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNewsLetterTypes() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        // Get all the newsLetterTypeList
        restNewsLetterTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(newsLetterType.getId().intValue())))
            .andExpect(jsonPath("$.[*].newLetterType").value(hasItem(DEFAULT_NEW_LETTER_TYPE)))
            .andExpect(jsonPath("$.[*].displayValue").value(hasItem(DEFAULT_DISPLAY_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }

    @Test
    @Transactional
    void getNewsLetterType() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        // Get the newsLetterType
        restNewsLetterTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, newsLetterType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(newsLetterType.getId().intValue()))
            .andExpect(jsonPath("$.newLetterType").value(DEFAULT_NEW_LETTER_TYPE))
            .andExpect(jsonPath("$.displayValue").value(DEFAULT_DISPLAY_VALUE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    void getNewsLetterTypesByIdFiltering() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        Long id = newsLetterType.getId();

        defaultNewsLetterTypeShouldBeFound("id.equals=" + id);
        defaultNewsLetterTypeShouldNotBeFound("id.notEquals=" + id);

        defaultNewsLetterTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNewsLetterTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultNewsLetterTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNewsLetterTypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNewsLetterTypesByNewLetterTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        // Get all the newsLetterTypeList where newLetterType equals to DEFAULT_NEW_LETTER_TYPE
        defaultNewsLetterTypeShouldBeFound("newLetterType.equals=" + DEFAULT_NEW_LETTER_TYPE);

        // Get all the newsLetterTypeList where newLetterType equals to UPDATED_NEW_LETTER_TYPE
        defaultNewsLetterTypeShouldNotBeFound("newLetterType.equals=" + UPDATED_NEW_LETTER_TYPE);
    }

    @Test
    @Transactional
    void getAllNewsLetterTypesByNewLetterTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        // Get all the newsLetterTypeList where newLetterType not equals to DEFAULT_NEW_LETTER_TYPE
        defaultNewsLetterTypeShouldNotBeFound("newLetterType.notEquals=" + DEFAULT_NEW_LETTER_TYPE);

        // Get all the newsLetterTypeList where newLetterType not equals to UPDATED_NEW_LETTER_TYPE
        defaultNewsLetterTypeShouldBeFound("newLetterType.notEquals=" + UPDATED_NEW_LETTER_TYPE);
    }

    @Test
    @Transactional
    void getAllNewsLetterTypesByNewLetterTypeIsInShouldWork() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        // Get all the newsLetterTypeList where newLetterType in DEFAULT_NEW_LETTER_TYPE or UPDATED_NEW_LETTER_TYPE
        defaultNewsLetterTypeShouldBeFound("newLetterType.in=" + DEFAULT_NEW_LETTER_TYPE + "," + UPDATED_NEW_LETTER_TYPE);

        // Get all the newsLetterTypeList where newLetterType equals to UPDATED_NEW_LETTER_TYPE
        defaultNewsLetterTypeShouldNotBeFound("newLetterType.in=" + UPDATED_NEW_LETTER_TYPE);
    }

    @Test
    @Transactional
    void getAllNewsLetterTypesByNewLetterTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        // Get all the newsLetterTypeList where newLetterType is not null
        defaultNewsLetterTypeShouldBeFound("newLetterType.specified=true");

        // Get all the newsLetterTypeList where newLetterType is null
        defaultNewsLetterTypeShouldNotBeFound("newLetterType.specified=false");
    }

    @Test
    @Transactional
    void getAllNewsLetterTypesByNewLetterTypeContainsSomething() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        // Get all the newsLetterTypeList where newLetterType contains DEFAULT_NEW_LETTER_TYPE
        defaultNewsLetterTypeShouldBeFound("newLetterType.contains=" + DEFAULT_NEW_LETTER_TYPE);

        // Get all the newsLetterTypeList where newLetterType contains UPDATED_NEW_LETTER_TYPE
        defaultNewsLetterTypeShouldNotBeFound("newLetterType.contains=" + UPDATED_NEW_LETTER_TYPE);
    }

    @Test
    @Transactional
    void getAllNewsLetterTypesByNewLetterTypeNotContainsSomething() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        // Get all the newsLetterTypeList where newLetterType does not contain DEFAULT_NEW_LETTER_TYPE
        defaultNewsLetterTypeShouldNotBeFound("newLetterType.doesNotContain=" + DEFAULT_NEW_LETTER_TYPE);

        // Get all the newsLetterTypeList where newLetterType does not contain UPDATED_NEW_LETTER_TYPE
        defaultNewsLetterTypeShouldBeFound("newLetterType.doesNotContain=" + UPDATED_NEW_LETTER_TYPE);
    }

    @Test
    @Transactional
    void getAllNewsLetterTypesByDisplayValueIsEqualToSomething() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        // Get all the newsLetterTypeList where displayValue equals to DEFAULT_DISPLAY_VALUE
        defaultNewsLetterTypeShouldBeFound("displayValue.equals=" + DEFAULT_DISPLAY_VALUE);

        // Get all the newsLetterTypeList where displayValue equals to UPDATED_DISPLAY_VALUE
        defaultNewsLetterTypeShouldNotBeFound("displayValue.equals=" + UPDATED_DISPLAY_VALUE);
    }

    @Test
    @Transactional
    void getAllNewsLetterTypesByDisplayValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        // Get all the newsLetterTypeList where displayValue not equals to DEFAULT_DISPLAY_VALUE
        defaultNewsLetterTypeShouldNotBeFound("displayValue.notEquals=" + DEFAULT_DISPLAY_VALUE);

        // Get all the newsLetterTypeList where displayValue not equals to UPDATED_DISPLAY_VALUE
        defaultNewsLetterTypeShouldBeFound("displayValue.notEquals=" + UPDATED_DISPLAY_VALUE);
    }

    @Test
    @Transactional
    void getAllNewsLetterTypesByDisplayValueIsInShouldWork() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        // Get all the newsLetterTypeList where displayValue in DEFAULT_DISPLAY_VALUE or UPDATED_DISPLAY_VALUE
        defaultNewsLetterTypeShouldBeFound("displayValue.in=" + DEFAULT_DISPLAY_VALUE + "," + UPDATED_DISPLAY_VALUE);

        // Get all the newsLetterTypeList where displayValue equals to UPDATED_DISPLAY_VALUE
        defaultNewsLetterTypeShouldNotBeFound("displayValue.in=" + UPDATED_DISPLAY_VALUE);
    }

    @Test
    @Transactional
    void getAllNewsLetterTypesByDisplayValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        // Get all the newsLetterTypeList where displayValue is not null
        defaultNewsLetterTypeShouldBeFound("displayValue.specified=true");

        // Get all the newsLetterTypeList where displayValue is null
        defaultNewsLetterTypeShouldNotBeFound("displayValue.specified=false");
    }

    @Test
    @Transactional
    void getAllNewsLetterTypesByDisplayValueContainsSomething() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        // Get all the newsLetterTypeList where displayValue contains DEFAULT_DISPLAY_VALUE
        defaultNewsLetterTypeShouldBeFound("displayValue.contains=" + DEFAULT_DISPLAY_VALUE);

        // Get all the newsLetterTypeList where displayValue contains UPDATED_DISPLAY_VALUE
        defaultNewsLetterTypeShouldNotBeFound("displayValue.contains=" + UPDATED_DISPLAY_VALUE);
    }

    @Test
    @Transactional
    void getAllNewsLetterTypesByDisplayValueNotContainsSomething() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        // Get all the newsLetterTypeList where displayValue does not contain DEFAULT_DISPLAY_VALUE
        defaultNewsLetterTypeShouldNotBeFound("displayValue.doesNotContain=" + DEFAULT_DISPLAY_VALUE);

        // Get all the newsLetterTypeList where displayValue does not contain UPDATED_DISPLAY_VALUE
        defaultNewsLetterTypeShouldBeFound("displayValue.doesNotContain=" + UPDATED_DISPLAY_VALUE);
    }

    @Test
    @Transactional
    void getAllNewsLetterTypesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        // Get all the newsLetterTypeList where description equals to DEFAULT_DESCRIPTION
        defaultNewsLetterTypeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the newsLetterTypeList where description equals to UPDATED_DESCRIPTION
        defaultNewsLetterTypeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllNewsLetterTypesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        // Get all the newsLetterTypeList where description not equals to DEFAULT_DESCRIPTION
        defaultNewsLetterTypeShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the newsLetterTypeList where description not equals to UPDATED_DESCRIPTION
        defaultNewsLetterTypeShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllNewsLetterTypesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        // Get all the newsLetterTypeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultNewsLetterTypeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the newsLetterTypeList where description equals to UPDATED_DESCRIPTION
        defaultNewsLetterTypeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllNewsLetterTypesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        // Get all the newsLetterTypeList where description is not null
        defaultNewsLetterTypeShouldBeFound("description.specified=true");

        // Get all the newsLetterTypeList where description is null
        defaultNewsLetterTypeShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllNewsLetterTypesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        // Get all the newsLetterTypeList where description contains DEFAULT_DESCRIPTION
        defaultNewsLetterTypeShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the newsLetterTypeList where description contains UPDATED_DESCRIPTION
        defaultNewsLetterTypeShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllNewsLetterTypesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        // Get all the newsLetterTypeList where description does not contain DEFAULT_DESCRIPTION
        defaultNewsLetterTypeShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the newsLetterTypeList where description does not contain UPDATED_DESCRIPTION
        defaultNewsLetterTypeShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllNewsLetterTypesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        // Get all the newsLetterTypeList where status equals to DEFAULT_STATUS
        defaultNewsLetterTypeShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the newsLetterTypeList where status equals to UPDATED_STATUS
        defaultNewsLetterTypeShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllNewsLetterTypesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        // Get all the newsLetterTypeList where status not equals to DEFAULT_STATUS
        defaultNewsLetterTypeShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the newsLetterTypeList where status not equals to UPDATED_STATUS
        defaultNewsLetterTypeShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllNewsLetterTypesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        // Get all the newsLetterTypeList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultNewsLetterTypeShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the newsLetterTypeList where status equals to UPDATED_STATUS
        defaultNewsLetterTypeShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllNewsLetterTypesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        // Get all the newsLetterTypeList where status is not null
        defaultNewsLetterTypeShouldBeFound("status.specified=true");

        // Get all the newsLetterTypeList where status is null
        defaultNewsLetterTypeShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllNewsLetterTypesByStatusContainsSomething() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        // Get all the newsLetterTypeList where status contains DEFAULT_STATUS
        defaultNewsLetterTypeShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the newsLetterTypeList where status contains UPDATED_STATUS
        defaultNewsLetterTypeShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllNewsLetterTypesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        // Get all the newsLetterTypeList where status does not contain DEFAULT_STATUS
        defaultNewsLetterTypeShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the newsLetterTypeList where status does not contain UPDATED_STATUS
        defaultNewsLetterTypeShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllNewsLetterTypesByCustNewsLetterConfigIsEqualToSomething() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);
        CustNewsLetterConfig custNewsLetterConfig = CustNewsLetterConfigResourceIT.createEntity(em);
        em.persist(custNewsLetterConfig);
        em.flush();
        newsLetterType.addCustNewsLetterConfig(custNewsLetterConfig);
        newsLetterTypeRepository.saveAndFlush(newsLetterType);
        Long custNewsLetterConfigId = custNewsLetterConfig.getId();

        // Get all the newsLetterTypeList where custNewsLetterConfig equals to custNewsLetterConfigId
        defaultNewsLetterTypeShouldBeFound("custNewsLetterConfigId.equals=" + custNewsLetterConfigId);

        // Get all the newsLetterTypeList where custNewsLetterConfig equals to (custNewsLetterConfigId + 1)
        defaultNewsLetterTypeShouldNotBeFound("custNewsLetterConfigId.equals=" + (custNewsLetterConfigId + 1));
    }

    @Test
    @Transactional
    void getAllNewsLetterTypesByIndNewsLetterConfIsEqualToSomething() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);
        IndNewsLetterConf indNewsLetterConf = IndNewsLetterConfResourceIT.createEntity(em);
        em.persist(indNewsLetterConf);
        em.flush();
        newsLetterType.addIndNewsLetterConf(indNewsLetterConf);
        newsLetterTypeRepository.saveAndFlush(newsLetterType);
        Long indNewsLetterConfId = indNewsLetterConf.getId();

        // Get all the newsLetterTypeList where indNewsLetterConf equals to indNewsLetterConfId
        defaultNewsLetterTypeShouldBeFound("indNewsLetterConfId.equals=" + indNewsLetterConfId);

        // Get all the newsLetterTypeList where indNewsLetterConf equals to (indNewsLetterConfId + 1)
        defaultNewsLetterTypeShouldNotBeFound("indNewsLetterConfId.equals=" + (indNewsLetterConfId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNewsLetterTypeShouldBeFound(String filter) throws Exception {
        restNewsLetterTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(newsLetterType.getId().intValue())))
            .andExpect(jsonPath("$.[*].newLetterType").value(hasItem(DEFAULT_NEW_LETTER_TYPE)))
            .andExpect(jsonPath("$.[*].displayValue").value(hasItem(DEFAULT_DISPLAY_VALUE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restNewsLetterTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNewsLetterTypeShouldNotBeFound(String filter) throws Exception {
        restNewsLetterTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNewsLetterTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNewsLetterType() throws Exception {
        // Get the newsLetterType
        restNewsLetterTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNewsLetterType() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        int databaseSizeBeforeUpdate = newsLetterTypeRepository.findAll().size();

        // Update the newsLetterType
        NewsLetterType updatedNewsLetterType = newsLetterTypeRepository.findById(newsLetterType.getId()).get();
        // Disconnect from session so that the updates on updatedNewsLetterType are not directly saved in db
        em.detach(updatedNewsLetterType);
        updatedNewsLetterType
            .newLetterType(UPDATED_NEW_LETTER_TYPE)
            .displayValue(UPDATED_DISPLAY_VALUE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS);
        NewsLetterTypeDTO newsLetterTypeDTO = newsLetterTypeMapper.toDto(updatedNewsLetterType);

        restNewsLetterTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, newsLetterTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(newsLetterTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the NewsLetterType in the database
        List<NewsLetterType> newsLetterTypeList = newsLetterTypeRepository.findAll();
        assertThat(newsLetterTypeList).hasSize(databaseSizeBeforeUpdate);
        NewsLetterType testNewsLetterType = newsLetterTypeList.get(newsLetterTypeList.size() - 1);
        assertThat(testNewsLetterType.getNewLetterType()).isEqualTo(UPDATED_NEW_LETTER_TYPE);
        assertThat(testNewsLetterType.getDisplayValue()).isEqualTo(UPDATED_DISPLAY_VALUE);
        assertThat(testNewsLetterType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testNewsLetterType.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingNewsLetterType() throws Exception {
        int databaseSizeBeforeUpdate = newsLetterTypeRepository.findAll().size();
        newsLetterType.setId(count.incrementAndGet());

        // Create the NewsLetterType
        NewsLetterTypeDTO newsLetterTypeDTO = newsLetterTypeMapper.toDto(newsLetterType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNewsLetterTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, newsLetterTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(newsLetterTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NewsLetterType in the database
        List<NewsLetterType> newsLetterTypeList = newsLetterTypeRepository.findAll();
        assertThat(newsLetterTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNewsLetterType() throws Exception {
        int databaseSizeBeforeUpdate = newsLetterTypeRepository.findAll().size();
        newsLetterType.setId(count.incrementAndGet());

        // Create the NewsLetterType
        NewsLetterTypeDTO newsLetterTypeDTO = newsLetterTypeMapper.toDto(newsLetterType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewsLetterTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(newsLetterTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NewsLetterType in the database
        List<NewsLetterType> newsLetterTypeList = newsLetterTypeRepository.findAll();
        assertThat(newsLetterTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNewsLetterType() throws Exception {
        int databaseSizeBeforeUpdate = newsLetterTypeRepository.findAll().size();
        newsLetterType.setId(count.incrementAndGet());

        // Create the NewsLetterType
        NewsLetterTypeDTO newsLetterTypeDTO = newsLetterTypeMapper.toDto(newsLetterType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewsLetterTypeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(newsLetterTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NewsLetterType in the database
        List<NewsLetterType> newsLetterTypeList = newsLetterTypeRepository.findAll();
        assertThat(newsLetterTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNewsLetterTypeWithPatch() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        int databaseSizeBeforeUpdate = newsLetterTypeRepository.findAll().size();

        // Update the newsLetterType using partial update
        NewsLetterType partialUpdatedNewsLetterType = new NewsLetterType();
        partialUpdatedNewsLetterType.setId(newsLetterType.getId());

        restNewsLetterTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNewsLetterType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNewsLetterType))
            )
            .andExpect(status().isOk());

        // Validate the NewsLetterType in the database
        List<NewsLetterType> newsLetterTypeList = newsLetterTypeRepository.findAll();
        assertThat(newsLetterTypeList).hasSize(databaseSizeBeforeUpdate);
        NewsLetterType testNewsLetterType = newsLetterTypeList.get(newsLetterTypeList.size() - 1);
        assertThat(testNewsLetterType.getNewLetterType()).isEqualTo(DEFAULT_NEW_LETTER_TYPE);
        assertThat(testNewsLetterType.getDisplayValue()).isEqualTo(DEFAULT_DISPLAY_VALUE);
        assertThat(testNewsLetterType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testNewsLetterType.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateNewsLetterTypeWithPatch() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        int databaseSizeBeforeUpdate = newsLetterTypeRepository.findAll().size();

        // Update the newsLetterType using partial update
        NewsLetterType partialUpdatedNewsLetterType = new NewsLetterType();
        partialUpdatedNewsLetterType.setId(newsLetterType.getId());

        partialUpdatedNewsLetterType
            .newLetterType(UPDATED_NEW_LETTER_TYPE)
            .displayValue(UPDATED_DISPLAY_VALUE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS);

        restNewsLetterTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNewsLetterType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNewsLetterType))
            )
            .andExpect(status().isOk());

        // Validate the NewsLetterType in the database
        List<NewsLetterType> newsLetterTypeList = newsLetterTypeRepository.findAll();
        assertThat(newsLetterTypeList).hasSize(databaseSizeBeforeUpdate);
        NewsLetterType testNewsLetterType = newsLetterTypeList.get(newsLetterTypeList.size() - 1);
        assertThat(testNewsLetterType.getNewLetterType()).isEqualTo(UPDATED_NEW_LETTER_TYPE);
        assertThat(testNewsLetterType.getDisplayValue()).isEqualTo(UPDATED_DISPLAY_VALUE);
        assertThat(testNewsLetterType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testNewsLetterType.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingNewsLetterType() throws Exception {
        int databaseSizeBeforeUpdate = newsLetterTypeRepository.findAll().size();
        newsLetterType.setId(count.incrementAndGet());

        // Create the NewsLetterType
        NewsLetterTypeDTO newsLetterTypeDTO = newsLetterTypeMapper.toDto(newsLetterType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNewsLetterTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, newsLetterTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(newsLetterTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NewsLetterType in the database
        List<NewsLetterType> newsLetterTypeList = newsLetterTypeRepository.findAll();
        assertThat(newsLetterTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNewsLetterType() throws Exception {
        int databaseSizeBeforeUpdate = newsLetterTypeRepository.findAll().size();
        newsLetterType.setId(count.incrementAndGet());

        // Create the NewsLetterType
        NewsLetterTypeDTO newsLetterTypeDTO = newsLetterTypeMapper.toDto(newsLetterType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewsLetterTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(newsLetterTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NewsLetterType in the database
        List<NewsLetterType> newsLetterTypeList = newsLetterTypeRepository.findAll();
        assertThat(newsLetterTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNewsLetterType() throws Exception {
        int databaseSizeBeforeUpdate = newsLetterTypeRepository.findAll().size();
        newsLetterType.setId(count.incrementAndGet());

        // Create the NewsLetterType
        NewsLetterTypeDTO newsLetterTypeDTO = newsLetterTypeMapper.toDto(newsLetterType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNewsLetterTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(newsLetterTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NewsLetterType in the database
        List<NewsLetterType> newsLetterTypeList = newsLetterTypeRepository.findAll();
        assertThat(newsLetterTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNewsLetterType() throws Exception {
        // Initialize the database
        newsLetterTypeRepository.saveAndFlush(newsLetterType);

        int databaseSizeBeforeDelete = newsLetterTypeRepository.findAll().size();

        // Delete the newsLetterType
        restNewsLetterTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, newsLetterType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NewsLetterType> newsLetterTypeList = newsLetterTypeRepository.findAll();
        assertThat(newsLetterTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
