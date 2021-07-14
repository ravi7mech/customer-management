package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.IndChar;
import com.apptium.customer.domain.Individual;
import com.apptium.customer.repository.IndCharRepository;
import com.apptium.customer.service.criteria.IndCharCriteria;
import com.apptium.customer.service.dto.IndCharDTO;
import com.apptium.customer.service.mapper.IndCharMapper;
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
 * Integration tests for the {@link IndCharResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IndCharResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VALUE = false;
    private static final Boolean UPDATED_VALUE = true;

    private static final String DEFAULT_VALUE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_INDIVIDUAL_ID = 1L;
    private static final Long UPDATED_INDIVIDUAL_ID = 2L;
    private static final Long SMALLER_INDIVIDUAL_ID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/ind-chars";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IndCharRepository indCharRepository;

    @Autowired
    private IndCharMapper indCharMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndCharMockMvc;

    private IndChar indChar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndChar createEntity(EntityManager em) {
        IndChar indChar = new IndChar()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE)
            .valueType(DEFAULT_VALUE_TYPE)
            .individualId(DEFAULT_INDIVIDUAL_ID);
        return indChar;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IndChar createUpdatedEntity(EntityManager em) {
        IndChar indChar = new IndChar()
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE)
            .valueType(UPDATED_VALUE_TYPE)
            .individualId(UPDATED_INDIVIDUAL_ID);
        return indChar;
    }

    @BeforeEach
    public void initTest() {
        indChar = createEntity(em);
    }

    @Test
    @Transactional
    void createIndChar() throws Exception {
        int databaseSizeBeforeCreate = indCharRepository.findAll().size();
        // Create the IndChar
        IndCharDTO indCharDTO = indCharMapper.toDto(indChar);
        restIndCharMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indCharDTO)))
            .andExpect(status().isCreated());

        // Validate the IndChar in the database
        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeCreate + 1);
        IndChar testIndChar = indCharList.get(indCharList.size() - 1);
        assertThat(testIndChar.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIndChar.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testIndChar.getValueType()).isEqualTo(DEFAULT_VALUE_TYPE);
        assertThat(testIndChar.getIndividualId()).isEqualTo(DEFAULT_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void createIndCharWithExistingId() throws Exception {
        // Create the IndChar with an existing ID
        indChar.setId(1L);
        IndCharDTO indCharDTO = indCharMapper.toDto(indChar);

        int databaseSizeBeforeCreate = indCharRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndCharMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indCharDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IndChar in the database
        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = indCharRepository.findAll().size();
        // set the field null
        indChar.setName(null);

        // Create the IndChar, which fails.
        IndCharDTO indCharDTO = indCharMapper.toDto(indChar);

        restIndCharMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indCharDTO)))
            .andExpect(status().isBadRequest());

        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = indCharRepository.findAll().size();
        // set the field null
        indChar.setValue(null);

        // Create the IndChar, which fails.
        IndCharDTO indCharDTO = indCharMapper.toDto(indChar);

        restIndCharMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indCharDTO)))
            .andExpect(status().isBadRequest());

        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = indCharRepository.findAll().size();
        // set the field null
        indChar.setValueType(null);

        // Create the IndChar, which fails.
        IndCharDTO indCharDTO = indCharMapper.toDto(indChar);

        restIndCharMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indCharDTO)))
            .andExpect(status().isBadRequest());

        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIndividualIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = indCharRepository.findAll().size();
        // set the field null
        indChar.setIndividualId(null);

        // Create the IndChar, which fails.
        IndCharDTO indCharDTO = indCharMapper.toDto(indChar);

        restIndCharMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indCharDTO)))
            .andExpect(status().isBadRequest());

        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIndChars() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        // Get all the indCharList
        restIndCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indChar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.booleanValue())))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE)))
            .andExpect(jsonPath("$.[*].individualId").value(hasItem(DEFAULT_INDIVIDUAL_ID.intValue())));
    }

    @Test
    @Transactional
    void getIndChar() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        // Get the indChar
        restIndCharMockMvc
            .perform(get(ENTITY_API_URL_ID, indChar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(indChar.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.booleanValue()))
            .andExpect(jsonPath("$.valueType").value(DEFAULT_VALUE_TYPE))
            .andExpect(jsonPath("$.individualId").value(DEFAULT_INDIVIDUAL_ID.intValue()));
    }

    @Test
    @Transactional
    void getIndCharsByIdFiltering() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        Long id = indChar.getId();

        defaultIndCharShouldBeFound("id.equals=" + id);
        defaultIndCharShouldNotBeFound("id.notEquals=" + id);

        defaultIndCharShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultIndCharShouldNotBeFound("id.greaterThan=" + id);

        defaultIndCharShouldBeFound("id.lessThanOrEqual=" + id);
        defaultIndCharShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllIndCharsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        // Get all the indCharList where name equals to DEFAULT_NAME
        defaultIndCharShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the indCharList where name equals to UPDATED_NAME
        defaultIndCharShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllIndCharsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        // Get all the indCharList where name not equals to DEFAULT_NAME
        defaultIndCharShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the indCharList where name not equals to UPDATED_NAME
        defaultIndCharShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllIndCharsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        // Get all the indCharList where name in DEFAULT_NAME or UPDATED_NAME
        defaultIndCharShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the indCharList where name equals to UPDATED_NAME
        defaultIndCharShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllIndCharsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        // Get all the indCharList where name is not null
        defaultIndCharShouldBeFound("name.specified=true");

        // Get all the indCharList where name is null
        defaultIndCharShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllIndCharsByNameContainsSomething() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        // Get all the indCharList where name contains DEFAULT_NAME
        defaultIndCharShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the indCharList where name contains UPDATED_NAME
        defaultIndCharShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllIndCharsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        // Get all the indCharList where name does not contain DEFAULT_NAME
        defaultIndCharShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the indCharList where name does not contain UPDATED_NAME
        defaultIndCharShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllIndCharsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        // Get all the indCharList where value equals to DEFAULT_VALUE
        defaultIndCharShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the indCharList where value equals to UPDATED_VALUE
        defaultIndCharShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllIndCharsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        // Get all the indCharList where value not equals to DEFAULT_VALUE
        defaultIndCharShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the indCharList where value not equals to UPDATED_VALUE
        defaultIndCharShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllIndCharsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        // Get all the indCharList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultIndCharShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the indCharList where value equals to UPDATED_VALUE
        defaultIndCharShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllIndCharsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        // Get all the indCharList where value is not null
        defaultIndCharShouldBeFound("value.specified=true");

        // Get all the indCharList where value is null
        defaultIndCharShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    void getAllIndCharsByValueTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        // Get all the indCharList where valueType equals to DEFAULT_VALUE_TYPE
        defaultIndCharShouldBeFound("valueType.equals=" + DEFAULT_VALUE_TYPE);

        // Get all the indCharList where valueType equals to UPDATED_VALUE_TYPE
        defaultIndCharShouldNotBeFound("valueType.equals=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllIndCharsByValueTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        // Get all the indCharList where valueType not equals to DEFAULT_VALUE_TYPE
        defaultIndCharShouldNotBeFound("valueType.notEquals=" + DEFAULT_VALUE_TYPE);

        // Get all the indCharList where valueType not equals to UPDATED_VALUE_TYPE
        defaultIndCharShouldBeFound("valueType.notEquals=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllIndCharsByValueTypeIsInShouldWork() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        // Get all the indCharList where valueType in DEFAULT_VALUE_TYPE or UPDATED_VALUE_TYPE
        defaultIndCharShouldBeFound("valueType.in=" + DEFAULT_VALUE_TYPE + "," + UPDATED_VALUE_TYPE);

        // Get all the indCharList where valueType equals to UPDATED_VALUE_TYPE
        defaultIndCharShouldNotBeFound("valueType.in=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllIndCharsByValueTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        // Get all the indCharList where valueType is not null
        defaultIndCharShouldBeFound("valueType.specified=true");

        // Get all the indCharList where valueType is null
        defaultIndCharShouldNotBeFound("valueType.specified=false");
    }

    @Test
    @Transactional
    void getAllIndCharsByValueTypeContainsSomething() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        // Get all the indCharList where valueType contains DEFAULT_VALUE_TYPE
        defaultIndCharShouldBeFound("valueType.contains=" + DEFAULT_VALUE_TYPE);

        // Get all the indCharList where valueType contains UPDATED_VALUE_TYPE
        defaultIndCharShouldNotBeFound("valueType.contains=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllIndCharsByValueTypeNotContainsSomething() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        // Get all the indCharList where valueType does not contain DEFAULT_VALUE_TYPE
        defaultIndCharShouldNotBeFound("valueType.doesNotContain=" + DEFAULT_VALUE_TYPE);

        // Get all the indCharList where valueType does not contain UPDATED_VALUE_TYPE
        defaultIndCharShouldBeFound("valueType.doesNotContain=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    void getAllIndCharsByIndividualIdIsEqualToSomething() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        // Get all the indCharList where individualId equals to DEFAULT_INDIVIDUAL_ID
        defaultIndCharShouldBeFound("individualId.equals=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the indCharList where individualId equals to UPDATED_INDIVIDUAL_ID
        defaultIndCharShouldNotBeFound("individualId.equals=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndCharsByIndividualIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        // Get all the indCharList where individualId not equals to DEFAULT_INDIVIDUAL_ID
        defaultIndCharShouldNotBeFound("individualId.notEquals=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the indCharList where individualId not equals to UPDATED_INDIVIDUAL_ID
        defaultIndCharShouldBeFound("individualId.notEquals=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndCharsByIndividualIdIsInShouldWork() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        // Get all the indCharList where individualId in DEFAULT_INDIVIDUAL_ID or UPDATED_INDIVIDUAL_ID
        defaultIndCharShouldBeFound("individualId.in=" + DEFAULT_INDIVIDUAL_ID + "," + UPDATED_INDIVIDUAL_ID);

        // Get all the indCharList where individualId equals to UPDATED_INDIVIDUAL_ID
        defaultIndCharShouldNotBeFound("individualId.in=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndCharsByIndividualIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        // Get all the indCharList where individualId is not null
        defaultIndCharShouldBeFound("individualId.specified=true");

        // Get all the indCharList where individualId is null
        defaultIndCharShouldNotBeFound("individualId.specified=false");
    }

    @Test
    @Transactional
    void getAllIndCharsByIndividualIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        // Get all the indCharList where individualId is greater than or equal to DEFAULT_INDIVIDUAL_ID
        defaultIndCharShouldBeFound("individualId.greaterThanOrEqual=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the indCharList where individualId is greater than or equal to UPDATED_INDIVIDUAL_ID
        defaultIndCharShouldNotBeFound("individualId.greaterThanOrEqual=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndCharsByIndividualIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        // Get all the indCharList where individualId is less than or equal to DEFAULT_INDIVIDUAL_ID
        defaultIndCharShouldBeFound("individualId.lessThanOrEqual=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the indCharList where individualId is less than or equal to SMALLER_INDIVIDUAL_ID
        defaultIndCharShouldNotBeFound("individualId.lessThanOrEqual=" + SMALLER_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndCharsByIndividualIdIsLessThanSomething() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        // Get all the indCharList where individualId is less than DEFAULT_INDIVIDUAL_ID
        defaultIndCharShouldNotBeFound("individualId.lessThan=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the indCharList where individualId is less than UPDATED_INDIVIDUAL_ID
        defaultIndCharShouldBeFound("individualId.lessThan=" + UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndCharsByIndividualIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        // Get all the indCharList where individualId is greater than DEFAULT_INDIVIDUAL_ID
        defaultIndCharShouldNotBeFound("individualId.greaterThan=" + DEFAULT_INDIVIDUAL_ID);

        // Get all the indCharList where individualId is greater than SMALLER_INDIVIDUAL_ID
        defaultIndCharShouldBeFound("individualId.greaterThan=" + SMALLER_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void getAllIndCharsByIndividualIsEqualToSomething() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);
        Individual individual = IndividualResourceIT.createEntity(em);
        em.persist(individual);
        em.flush();
        indChar.setIndividual(individual);
        indCharRepository.saveAndFlush(indChar);
        Long individualId = individual.getId();

        // Get all the indCharList where individual equals to individualId
        defaultIndCharShouldBeFound("individualId.equals=" + individualId);

        // Get all the indCharList where individual equals to (individualId + 1)
        defaultIndCharShouldNotBeFound("individualId.equals=" + (individualId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultIndCharShouldBeFound(String filter) throws Exception {
        restIndCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indChar.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.booleanValue())))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE)))
            .andExpect(jsonPath("$.[*].individualId").value(hasItem(DEFAULT_INDIVIDUAL_ID.intValue())));

        // Check, that the count call also returns 1
        restIndCharMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultIndCharShouldNotBeFound(String filter) throws Exception {
        restIndCharMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIndCharMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingIndChar() throws Exception {
        // Get the indChar
        restIndCharMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIndChar() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        int databaseSizeBeforeUpdate = indCharRepository.findAll().size();

        // Update the indChar
        IndChar updatedIndChar = indCharRepository.findById(indChar.getId()).get();
        // Disconnect from session so that the updates on updatedIndChar are not directly saved in db
        em.detach(updatedIndChar);
        updatedIndChar.name(UPDATED_NAME).value(UPDATED_VALUE).valueType(UPDATED_VALUE_TYPE).individualId(UPDATED_INDIVIDUAL_ID);
        IndCharDTO indCharDTO = indCharMapper.toDto(updatedIndChar);

        restIndCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indCharDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indCharDTO))
            )
            .andExpect(status().isOk());

        // Validate the IndChar in the database
        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeUpdate);
        IndChar testIndChar = indCharList.get(indCharList.size() - 1);
        assertThat(testIndChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIndChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testIndChar.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testIndChar.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void putNonExistingIndChar() throws Exception {
        int databaseSizeBeforeUpdate = indCharRepository.findAll().size();
        indChar.setId(count.incrementAndGet());

        // Create the IndChar
        IndCharDTO indCharDTO = indCharMapper.toDto(indChar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, indCharDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndChar in the database
        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIndChar() throws Exception {
        int databaseSizeBeforeUpdate = indCharRepository.findAll().size();
        indChar.setId(count.incrementAndGet());

        // Create the IndChar
        IndCharDTO indCharDTO = indCharMapper.toDto(indChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndCharMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(indCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndChar in the database
        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIndChar() throws Exception {
        int databaseSizeBeforeUpdate = indCharRepository.findAll().size();
        indChar.setId(count.incrementAndGet());

        // Create the IndChar
        IndCharDTO indCharDTO = indCharMapper.toDto(indChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndCharMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(indCharDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the IndChar in the database
        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIndCharWithPatch() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        int databaseSizeBeforeUpdate = indCharRepository.findAll().size();

        // Update the indChar using partial update
        IndChar partialUpdatedIndChar = new IndChar();
        partialUpdatedIndChar.setId(indChar.getId());

        restIndCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndChar))
            )
            .andExpect(status().isOk());

        // Validate the IndChar in the database
        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeUpdate);
        IndChar testIndChar = indCharList.get(indCharList.size() - 1);
        assertThat(testIndChar.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIndChar.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testIndChar.getValueType()).isEqualTo(DEFAULT_VALUE_TYPE);
        assertThat(testIndChar.getIndividualId()).isEqualTo(DEFAULT_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void fullUpdateIndCharWithPatch() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        int databaseSizeBeforeUpdate = indCharRepository.findAll().size();

        // Update the indChar using partial update
        IndChar partialUpdatedIndChar = new IndChar();
        partialUpdatedIndChar.setId(indChar.getId());

        partialUpdatedIndChar.name(UPDATED_NAME).value(UPDATED_VALUE).valueType(UPDATED_VALUE_TYPE).individualId(UPDATED_INDIVIDUAL_ID);

        restIndCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndChar.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndChar))
            )
            .andExpect(status().isOk());

        // Validate the IndChar in the database
        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeUpdate);
        IndChar testIndChar = indCharList.get(indCharList.size() - 1);
        assertThat(testIndChar.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIndChar.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testIndChar.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testIndChar.getIndividualId()).isEqualTo(UPDATED_INDIVIDUAL_ID);
    }

    @Test
    @Transactional
    void patchNonExistingIndChar() throws Exception {
        int databaseSizeBeforeUpdate = indCharRepository.findAll().size();
        indChar.setId(count.incrementAndGet());

        // Create the IndChar
        IndCharDTO indCharDTO = indCharMapper.toDto(indChar);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, indCharDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndChar in the database
        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIndChar() throws Exception {
        int databaseSizeBeforeUpdate = indCharRepository.findAll().size();
        indChar.setId(count.incrementAndGet());

        // Create the IndChar
        IndCharDTO indCharDTO = indCharMapper.toDto(indChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndCharMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(indCharDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IndChar in the database
        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIndChar() throws Exception {
        int databaseSizeBeforeUpdate = indCharRepository.findAll().size();
        indChar.setId(count.incrementAndGet());

        // Create the IndChar
        IndCharDTO indCharDTO = indCharMapper.toDto(indChar);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndCharMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(indCharDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IndChar in the database
        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIndChar() throws Exception {
        // Initialize the database
        indCharRepository.saveAndFlush(indChar);

        int databaseSizeBeforeDelete = indCharRepository.findAll().size();

        // Delete the indChar
        restIndCharMockMvc
            .perform(delete(ENTITY_API_URL_ID, indChar.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IndChar> indCharList = indCharRepository.findAll();
        assertThat(indCharList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
