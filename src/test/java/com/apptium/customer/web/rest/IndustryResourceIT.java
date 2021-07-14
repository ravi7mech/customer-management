package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.Customer;
import com.apptium.customer.domain.Industry;
import com.apptium.customer.repository.IndustryRepository;
import com.apptium.customer.service.criteria.IndustryCriteria;
import com.apptium.customer.service.dto.IndustryDTO;
import com.apptium.customer.service.mapper.IndustryMapper;
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
 * Integration tests for the {@link IndustryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IndustryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/industries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IndustryRepository industryRepository;

    @Autowired
    private IndustryMapper industryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIndustryMockMvc;

    private Industry industry;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Industry createEntity(EntityManager em) {
        Industry industry = new Industry().name(DEFAULT_NAME).code(DEFAULT_CODE).description(DEFAULT_DESCRIPTION);
        return industry;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Industry createUpdatedEntity(EntityManager em) {
        Industry industry = new Industry().name(UPDATED_NAME).code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        return industry;
    }

    @BeforeEach
    public void initTest() {
        industry = createEntity(em);
    }

    @Test
    @Transactional
    void createIndustry() throws Exception {
        int databaseSizeBeforeCreate = industryRepository.findAll().size();
        // Create the Industry
        IndustryDTO industryDTO = industryMapper.toDto(industry);
        restIndustryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(industryDTO)))
            .andExpect(status().isCreated());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeCreate + 1);
        Industry testIndustry = industryList.get(industryList.size() - 1);
        assertThat(testIndustry.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIndustry.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testIndustry.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createIndustryWithExistingId() throws Exception {
        // Create the Industry with an existing ID
        industry.setId(1L);
        IndustryDTO industryDTO = industryMapper.toDto(industry);

        int databaseSizeBeforeCreate = industryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndustryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(industryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = industryRepository.findAll().size();
        // set the field null
        industry.setName(null);

        // Create the Industry, which fails.
        IndustryDTO industryDTO = industryMapper.toDto(industry);

        restIndustryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(industryDTO)))
            .andExpect(status().isBadRequest());

        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = industryRepository.findAll().size();
        // set the field null
        industry.setCode(null);

        // Create the Industry, which fails.
        IndustryDTO industryDTO = industryMapper.toDto(industry);

        restIndustryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(industryDTO)))
            .andExpect(status().isBadRequest());

        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIndustries() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        // Get all the industryList
        restIndustryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(industry.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getIndustry() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        // Get the industry
        restIndustryMockMvc
            .perform(get(ENTITY_API_URL_ID, industry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(industry.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getIndustriesByIdFiltering() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        Long id = industry.getId();

        defaultIndustryShouldBeFound("id.equals=" + id);
        defaultIndustryShouldNotBeFound("id.notEquals=" + id);

        defaultIndustryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultIndustryShouldNotBeFound("id.greaterThan=" + id);

        defaultIndustryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultIndustryShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllIndustriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        // Get all the industryList where name equals to DEFAULT_NAME
        defaultIndustryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the industryList where name equals to UPDATED_NAME
        defaultIndustryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllIndustriesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        // Get all the industryList where name not equals to DEFAULT_NAME
        defaultIndustryShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the industryList where name not equals to UPDATED_NAME
        defaultIndustryShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllIndustriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        // Get all the industryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultIndustryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the industryList where name equals to UPDATED_NAME
        defaultIndustryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllIndustriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        // Get all the industryList where name is not null
        defaultIndustryShouldBeFound("name.specified=true");

        // Get all the industryList where name is null
        defaultIndustryShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllIndustriesByNameContainsSomething() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        // Get all the industryList where name contains DEFAULT_NAME
        defaultIndustryShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the industryList where name contains UPDATED_NAME
        defaultIndustryShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllIndustriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        // Get all the industryList where name does not contain DEFAULT_NAME
        defaultIndustryShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the industryList where name does not contain UPDATED_NAME
        defaultIndustryShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllIndustriesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        // Get all the industryList where code equals to DEFAULT_CODE
        defaultIndustryShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the industryList where code equals to UPDATED_CODE
        defaultIndustryShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllIndustriesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        // Get all the industryList where code not equals to DEFAULT_CODE
        defaultIndustryShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the industryList where code not equals to UPDATED_CODE
        defaultIndustryShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllIndustriesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        // Get all the industryList where code in DEFAULT_CODE or UPDATED_CODE
        defaultIndustryShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the industryList where code equals to UPDATED_CODE
        defaultIndustryShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllIndustriesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        // Get all the industryList where code is not null
        defaultIndustryShouldBeFound("code.specified=true");

        // Get all the industryList where code is null
        defaultIndustryShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllIndustriesByCodeContainsSomething() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        // Get all the industryList where code contains DEFAULT_CODE
        defaultIndustryShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the industryList where code contains UPDATED_CODE
        defaultIndustryShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllIndustriesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        // Get all the industryList where code does not contain DEFAULT_CODE
        defaultIndustryShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the industryList where code does not contain UPDATED_CODE
        defaultIndustryShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllIndustriesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        // Get all the industryList where description equals to DEFAULT_DESCRIPTION
        defaultIndustryShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the industryList where description equals to UPDATED_DESCRIPTION
        defaultIndustryShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllIndustriesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        // Get all the industryList where description not equals to DEFAULT_DESCRIPTION
        defaultIndustryShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the industryList where description not equals to UPDATED_DESCRIPTION
        defaultIndustryShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllIndustriesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        // Get all the industryList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultIndustryShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the industryList where description equals to UPDATED_DESCRIPTION
        defaultIndustryShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllIndustriesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        // Get all the industryList where description is not null
        defaultIndustryShouldBeFound("description.specified=true");

        // Get all the industryList where description is null
        defaultIndustryShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllIndustriesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        // Get all the industryList where description contains DEFAULT_DESCRIPTION
        defaultIndustryShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the industryList where description contains UPDATED_DESCRIPTION
        defaultIndustryShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllIndustriesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        // Get all the industryList where description does not contain DEFAULT_DESCRIPTION
        defaultIndustryShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the industryList where description does not contain UPDATED_DESCRIPTION
        defaultIndustryShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllIndustriesByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);
        Customer customer = CustomerResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        industry.setCustomer(customer);
        industryRepository.saveAndFlush(industry);
        Long customerId = customer.getId();

        // Get all the industryList where customer equals to customerId
        defaultIndustryShouldBeFound("customerId.equals=" + customerId);

        // Get all the industryList where customer equals to (customerId + 1)
        defaultIndustryShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultIndustryShouldBeFound(String filter) throws Exception {
        restIndustryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(industry.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restIndustryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultIndustryShouldNotBeFound(String filter) throws Exception {
        restIndustryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIndustryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingIndustry() throws Exception {
        // Get the industry
        restIndustryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIndustry() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        int databaseSizeBeforeUpdate = industryRepository.findAll().size();

        // Update the industry
        Industry updatedIndustry = industryRepository.findById(industry.getId()).get();
        // Disconnect from session so that the updates on updatedIndustry are not directly saved in db
        em.detach(updatedIndustry);
        updatedIndustry.name(UPDATED_NAME).code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        IndustryDTO industryDTO = industryMapper.toDto(updatedIndustry);

        restIndustryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, industryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(industryDTO))
            )
            .andExpect(status().isOk());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeUpdate);
        Industry testIndustry = industryList.get(industryList.size() - 1);
        assertThat(testIndustry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIndustry.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testIndustry.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingIndustry() throws Exception {
        int databaseSizeBeforeUpdate = industryRepository.findAll().size();
        industry.setId(count.incrementAndGet());

        // Create the Industry
        IndustryDTO industryDTO = industryMapper.toDto(industry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndustryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, industryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(industryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIndustry() throws Exception {
        int databaseSizeBeforeUpdate = industryRepository.findAll().size();
        industry.setId(count.incrementAndGet());

        // Create the Industry
        IndustryDTO industryDTO = industryMapper.toDto(industry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndustryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(industryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIndustry() throws Exception {
        int databaseSizeBeforeUpdate = industryRepository.findAll().size();
        industry.setId(count.incrementAndGet());

        // Create the Industry
        IndustryDTO industryDTO = industryMapper.toDto(industry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndustryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(industryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIndustryWithPatch() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        int databaseSizeBeforeUpdate = industryRepository.findAll().size();

        // Update the industry using partial update
        Industry partialUpdatedIndustry = new Industry();
        partialUpdatedIndustry.setId(industry.getId());

        partialUpdatedIndustry.name(UPDATED_NAME).code(UPDATED_CODE);

        restIndustryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndustry.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndustry))
            )
            .andExpect(status().isOk());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeUpdate);
        Industry testIndustry = industryList.get(industryList.size() - 1);
        assertThat(testIndustry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIndustry.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testIndustry.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateIndustryWithPatch() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        int databaseSizeBeforeUpdate = industryRepository.findAll().size();

        // Update the industry using partial update
        Industry partialUpdatedIndustry = new Industry();
        partialUpdatedIndustry.setId(industry.getId());

        partialUpdatedIndustry.name(UPDATED_NAME).code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        restIndustryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIndustry.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIndustry))
            )
            .andExpect(status().isOk());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeUpdate);
        Industry testIndustry = industryList.get(industryList.size() - 1);
        assertThat(testIndustry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIndustry.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testIndustry.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingIndustry() throws Exception {
        int databaseSizeBeforeUpdate = industryRepository.findAll().size();
        industry.setId(count.incrementAndGet());

        // Create the Industry
        IndustryDTO industryDTO = industryMapper.toDto(industry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIndustryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, industryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(industryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIndustry() throws Exception {
        int databaseSizeBeforeUpdate = industryRepository.findAll().size();
        industry.setId(count.incrementAndGet());

        // Create the Industry
        IndustryDTO industryDTO = industryMapper.toDto(industry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndustryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(industryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIndustry() throws Exception {
        int databaseSizeBeforeUpdate = industryRepository.findAll().size();
        industry.setId(count.incrementAndGet());

        // Create the Industry
        IndustryDTO industryDTO = industryMapper.toDto(industry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIndustryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(industryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Industry in the database
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIndustry() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        int databaseSizeBeforeDelete = industryRepository.findAll().size();

        // Delete the industry
        restIndustryMockMvc
            .perform(delete(ENTITY_API_URL_ID, industry.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Industry> industryList = industryRepository.findAll();
        assertThat(industryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
