package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.CreditCheckVerification;
import com.apptium.customer.repository.CreditCheckVerificationRepository;
import com.apptium.customer.service.criteria.CreditCheckVerificationCriteria;
import com.apptium.customer.service.dto.CreditCheckVerificationDTO;
import com.apptium.customer.service.mapper.CreditCheckVerificationMapper;
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
 * Integration tests for the {@link CreditCheckVerificationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CreditCheckVerificationResourceIT {

    private static final String DEFAULT_VER_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_VER_QUESTION = "BBBBBBBBBB";

    private static final String DEFAULT_VER_QUESTION_CHOICE = "AAAAAAAAAA";
    private static final String UPDATED_VER_QUESTION_CHOICE = "BBBBBBBBBB";

    private static final String DEFAULT_VER_ANSWER = "AAAAAAAAAA";
    private static final String UPDATED_VER_ANSWER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/credit-check-verifications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CreditCheckVerificationRepository creditCheckVerificationRepository;

    @Autowired
    private CreditCheckVerificationMapper creditCheckVerificationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCreditCheckVerificationMockMvc;

    private CreditCheckVerification creditCheckVerification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CreditCheckVerification createEntity(EntityManager em) {
        CreditCheckVerification creditCheckVerification = new CreditCheckVerification()
            .verQuestion(DEFAULT_VER_QUESTION)
            .verQuestionChoice(DEFAULT_VER_QUESTION_CHOICE)
            .verAnswer(DEFAULT_VER_ANSWER);
        return creditCheckVerification;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CreditCheckVerification createUpdatedEntity(EntityManager em) {
        CreditCheckVerification creditCheckVerification = new CreditCheckVerification()
            .verQuestion(UPDATED_VER_QUESTION)
            .verQuestionChoice(UPDATED_VER_QUESTION_CHOICE)
            .verAnswer(UPDATED_VER_ANSWER);
        return creditCheckVerification;
    }

    @BeforeEach
    public void initTest() {
        creditCheckVerification = createEntity(em);
    }

    @Test
    @Transactional
    void createCreditCheckVerification() throws Exception {
        int databaseSizeBeforeCreate = creditCheckVerificationRepository.findAll().size();
        // Create the CreditCheckVerification
        CreditCheckVerificationDTO creditCheckVerificationDTO = creditCheckVerificationMapper.toDto(creditCheckVerification);
        restCreditCheckVerificationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCheckVerificationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CreditCheckVerification in the database
        List<CreditCheckVerification> creditCheckVerificationList = creditCheckVerificationRepository.findAll();
        assertThat(creditCheckVerificationList).hasSize(databaseSizeBeforeCreate + 1);
        CreditCheckVerification testCreditCheckVerification = creditCheckVerificationList.get(creditCheckVerificationList.size() - 1);
        assertThat(testCreditCheckVerification.getVerQuestion()).isEqualTo(DEFAULT_VER_QUESTION);
        assertThat(testCreditCheckVerification.getVerQuestionChoice()).isEqualTo(DEFAULT_VER_QUESTION_CHOICE);
        assertThat(testCreditCheckVerification.getVerAnswer()).isEqualTo(DEFAULT_VER_ANSWER);
    }

    @Test
    @Transactional
    void createCreditCheckVerificationWithExistingId() throws Exception {
        // Create the CreditCheckVerification with an existing ID
        creditCheckVerification.setId(1L);
        CreditCheckVerificationDTO creditCheckVerificationDTO = creditCheckVerificationMapper.toDto(creditCheckVerification);

        int databaseSizeBeforeCreate = creditCheckVerificationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCreditCheckVerificationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCheckVerificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditCheckVerification in the database
        List<CreditCheckVerification> creditCheckVerificationList = creditCheckVerificationRepository.findAll();
        assertThat(creditCheckVerificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkVerQuestionIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditCheckVerificationRepository.findAll().size();
        // set the field null
        creditCheckVerification.setVerQuestion(null);

        // Create the CreditCheckVerification, which fails.
        CreditCheckVerificationDTO creditCheckVerificationDTO = creditCheckVerificationMapper.toDto(creditCheckVerification);

        restCreditCheckVerificationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCheckVerificationDTO))
            )
            .andExpect(status().isBadRequest());

        List<CreditCheckVerification> creditCheckVerificationList = creditCheckVerificationRepository.findAll();
        assertThat(creditCheckVerificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVerQuestionChoiceIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditCheckVerificationRepository.findAll().size();
        // set the field null
        creditCheckVerification.setVerQuestionChoice(null);

        // Create the CreditCheckVerification, which fails.
        CreditCheckVerificationDTO creditCheckVerificationDTO = creditCheckVerificationMapper.toDto(creditCheckVerification);

        restCreditCheckVerificationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCheckVerificationDTO))
            )
            .andExpect(status().isBadRequest());

        List<CreditCheckVerification> creditCheckVerificationList = creditCheckVerificationRepository.findAll();
        assertThat(creditCheckVerificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVerAnswerIsRequired() throws Exception {
        int databaseSizeBeforeTest = creditCheckVerificationRepository.findAll().size();
        // set the field null
        creditCheckVerification.setVerAnswer(null);

        // Create the CreditCheckVerification, which fails.
        CreditCheckVerificationDTO creditCheckVerificationDTO = creditCheckVerificationMapper.toDto(creditCheckVerification);

        restCreditCheckVerificationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCheckVerificationDTO))
            )
            .andExpect(status().isBadRequest());

        List<CreditCheckVerification> creditCheckVerificationList = creditCheckVerificationRepository.findAll();
        assertThat(creditCheckVerificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCreditCheckVerifications() throws Exception {
        // Initialize the database
        creditCheckVerificationRepository.saveAndFlush(creditCheckVerification);

        // Get all the creditCheckVerificationList
        restCreditCheckVerificationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(creditCheckVerification.getId().intValue())))
            .andExpect(jsonPath("$.[*].verQuestion").value(hasItem(DEFAULT_VER_QUESTION)))
            .andExpect(jsonPath("$.[*].verQuestionChoice").value(hasItem(DEFAULT_VER_QUESTION_CHOICE)))
            .andExpect(jsonPath("$.[*].verAnswer").value(hasItem(DEFAULT_VER_ANSWER)));
    }

    @Test
    @Transactional
    void getCreditCheckVerification() throws Exception {
        // Initialize the database
        creditCheckVerificationRepository.saveAndFlush(creditCheckVerification);

        // Get the creditCheckVerification
        restCreditCheckVerificationMockMvc
            .perform(get(ENTITY_API_URL_ID, creditCheckVerification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(creditCheckVerification.getId().intValue()))
            .andExpect(jsonPath("$.verQuestion").value(DEFAULT_VER_QUESTION))
            .andExpect(jsonPath("$.verQuestionChoice").value(DEFAULT_VER_QUESTION_CHOICE))
            .andExpect(jsonPath("$.verAnswer").value(DEFAULT_VER_ANSWER));
    }

    @Test
    @Transactional
    void getCreditCheckVerificationsByIdFiltering() throws Exception {
        // Initialize the database
        creditCheckVerificationRepository.saveAndFlush(creditCheckVerification);

        Long id = creditCheckVerification.getId();

        defaultCreditCheckVerificationShouldBeFound("id.equals=" + id);
        defaultCreditCheckVerificationShouldNotBeFound("id.notEquals=" + id);

        defaultCreditCheckVerificationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCreditCheckVerificationShouldNotBeFound("id.greaterThan=" + id);

        defaultCreditCheckVerificationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCreditCheckVerificationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCreditCheckVerificationsByVerQuestionIsEqualToSomething() throws Exception {
        // Initialize the database
        creditCheckVerificationRepository.saveAndFlush(creditCheckVerification);

        // Get all the creditCheckVerificationList where verQuestion equals to DEFAULT_VER_QUESTION
        defaultCreditCheckVerificationShouldBeFound("verQuestion.equals=" + DEFAULT_VER_QUESTION);

        // Get all the creditCheckVerificationList where verQuestion equals to UPDATED_VER_QUESTION
        defaultCreditCheckVerificationShouldNotBeFound("verQuestion.equals=" + UPDATED_VER_QUESTION);
    }

    @Test
    @Transactional
    void getAllCreditCheckVerificationsByVerQuestionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        creditCheckVerificationRepository.saveAndFlush(creditCheckVerification);

        // Get all the creditCheckVerificationList where verQuestion not equals to DEFAULT_VER_QUESTION
        defaultCreditCheckVerificationShouldNotBeFound("verQuestion.notEquals=" + DEFAULT_VER_QUESTION);

        // Get all the creditCheckVerificationList where verQuestion not equals to UPDATED_VER_QUESTION
        defaultCreditCheckVerificationShouldBeFound("verQuestion.notEquals=" + UPDATED_VER_QUESTION);
    }

    @Test
    @Transactional
    void getAllCreditCheckVerificationsByVerQuestionIsInShouldWork() throws Exception {
        // Initialize the database
        creditCheckVerificationRepository.saveAndFlush(creditCheckVerification);

        // Get all the creditCheckVerificationList where verQuestion in DEFAULT_VER_QUESTION or UPDATED_VER_QUESTION
        defaultCreditCheckVerificationShouldBeFound("verQuestion.in=" + DEFAULT_VER_QUESTION + "," + UPDATED_VER_QUESTION);

        // Get all the creditCheckVerificationList where verQuestion equals to UPDATED_VER_QUESTION
        defaultCreditCheckVerificationShouldNotBeFound("verQuestion.in=" + UPDATED_VER_QUESTION);
    }

    @Test
    @Transactional
    void getAllCreditCheckVerificationsByVerQuestionIsNullOrNotNull() throws Exception {
        // Initialize the database
        creditCheckVerificationRepository.saveAndFlush(creditCheckVerification);

        // Get all the creditCheckVerificationList where verQuestion is not null
        defaultCreditCheckVerificationShouldBeFound("verQuestion.specified=true");

        // Get all the creditCheckVerificationList where verQuestion is null
        defaultCreditCheckVerificationShouldNotBeFound("verQuestion.specified=false");
    }

    @Test
    @Transactional
    void getAllCreditCheckVerificationsByVerQuestionContainsSomething() throws Exception {
        // Initialize the database
        creditCheckVerificationRepository.saveAndFlush(creditCheckVerification);

        // Get all the creditCheckVerificationList where verQuestion contains DEFAULT_VER_QUESTION
        defaultCreditCheckVerificationShouldBeFound("verQuestion.contains=" + DEFAULT_VER_QUESTION);

        // Get all the creditCheckVerificationList where verQuestion contains UPDATED_VER_QUESTION
        defaultCreditCheckVerificationShouldNotBeFound("verQuestion.contains=" + UPDATED_VER_QUESTION);
    }

    @Test
    @Transactional
    void getAllCreditCheckVerificationsByVerQuestionNotContainsSomething() throws Exception {
        // Initialize the database
        creditCheckVerificationRepository.saveAndFlush(creditCheckVerification);

        // Get all the creditCheckVerificationList where verQuestion does not contain DEFAULT_VER_QUESTION
        defaultCreditCheckVerificationShouldNotBeFound("verQuestion.doesNotContain=" + DEFAULT_VER_QUESTION);

        // Get all the creditCheckVerificationList where verQuestion does not contain UPDATED_VER_QUESTION
        defaultCreditCheckVerificationShouldBeFound("verQuestion.doesNotContain=" + UPDATED_VER_QUESTION);
    }

    @Test
    @Transactional
    void getAllCreditCheckVerificationsByVerQuestionChoiceIsEqualToSomething() throws Exception {
        // Initialize the database
        creditCheckVerificationRepository.saveAndFlush(creditCheckVerification);

        // Get all the creditCheckVerificationList where verQuestionChoice equals to DEFAULT_VER_QUESTION_CHOICE
        defaultCreditCheckVerificationShouldBeFound("verQuestionChoice.equals=" + DEFAULT_VER_QUESTION_CHOICE);

        // Get all the creditCheckVerificationList where verQuestionChoice equals to UPDATED_VER_QUESTION_CHOICE
        defaultCreditCheckVerificationShouldNotBeFound("verQuestionChoice.equals=" + UPDATED_VER_QUESTION_CHOICE);
    }

    @Test
    @Transactional
    void getAllCreditCheckVerificationsByVerQuestionChoiceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        creditCheckVerificationRepository.saveAndFlush(creditCheckVerification);

        // Get all the creditCheckVerificationList where verQuestionChoice not equals to DEFAULT_VER_QUESTION_CHOICE
        defaultCreditCheckVerificationShouldNotBeFound("verQuestionChoice.notEquals=" + DEFAULT_VER_QUESTION_CHOICE);

        // Get all the creditCheckVerificationList where verQuestionChoice not equals to UPDATED_VER_QUESTION_CHOICE
        defaultCreditCheckVerificationShouldBeFound("verQuestionChoice.notEquals=" + UPDATED_VER_QUESTION_CHOICE);
    }

    @Test
    @Transactional
    void getAllCreditCheckVerificationsByVerQuestionChoiceIsInShouldWork() throws Exception {
        // Initialize the database
        creditCheckVerificationRepository.saveAndFlush(creditCheckVerification);

        // Get all the creditCheckVerificationList where verQuestionChoice in DEFAULT_VER_QUESTION_CHOICE or UPDATED_VER_QUESTION_CHOICE
        defaultCreditCheckVerificationShouldBeFound(
            "verQuestionChoice.in=" + DEFAULT_VER_QUESTION_CHOICE + "," + UPDATED_VER_QUESTION_CHOICE
        );

        // Get all the creditCheckVerificationList where verQuestionChoice equals to UPDATED_VER_QUESTION_CHOICE
        defaultCreditCheckVerificationShouldNotBeFound("verQuestionChoice.in=" + UPDATED_VER_QUESTION_CHOICE);
    }

    @Test
    @Transactional
    void getAllCreditCheckVerificationsByVerQuestionChoiceIsNullOrNotNull() throws Exception {
        // Initialize the database
        creditCheckVerificationRepository.saveAndFlush(creditCheckVerification);

        // Get all the creditCheckVerificationList where verQuestionChoice is not null
        defaultCreditCheckVerificationShouldBeFound("verQuestionChoice.specified=true");

        // Get all the creditCheckVerificationList where verQuestionChoice is null
        defaultCreditCheckVerificationShouldNotBeFound("verQuestionChoice.specified=false");
    }

    @Test
    @Transactional
    void getAllCreditCheckVerificationsByVerQuestionChoiceContainsSomething() throws Exception {
        // Initialize the database
        creditCheckVerificationRepository.saveAndFlush(creditCheckVerification);

        // Get all the creditCheckVerificationList where verQuestionChoice contains DEFAULT_VER_QUESTION_CHOICE
        defaultCreditCheckVerificationShouldBeFound("verQuestionChoice.contains=" + DEFAULT_VER_QUESTION_CHOICE);

        // Get all the creditCheckVerificationList where verQuestionChoice contains UPDATED_VER_QUESTION_CHOICE
        defaultCreditCheckVerificationShouldNotBeFound("verQuestionChoice.contains=" + UPDATED_VER_QUESTION_CHOICE);
    }

    @Test
    @Transactional
    void getAllCreditCheckVerificationsByVerQuestionChoiceNotContainsSomething() throws Exception {
        // Initialize the database
        creditCheckVerificationRepository.saveAndFlush(creditCheckVerification);

        // Get all the creditCheckVerificationList where verQuestionChoice does not contain DEFAULT_VER_QUESTION_CHOICE
        defaultCreditCheckVerificationShouldNotBeFound("verQuestionChoice.doesNotContain=" + DEFAULT_VER_QUESTION_CHOICE);

        // Get all the creditCheckVerificationList where verQuestionChoice does not contain UPDATED_VER_QUESTION_CHOICE
        defaultCreditCheckVerificationShouldBeFound("verQuestionChoice.doesNotContain=" + UPDATED_VER_QUESTION_CHOICE);
    }

    @Test
    @Transactional
    void getAllCreditCheckVerificationsByVerAnswerIsEqualToSomething() throws Exception {
        // Initialize the database
        creditCheckVerificationRepository.saveAndFlush(creditCheckVerification);

        // Get all the creditCheckVerificationList where verAnswer equals to DEFAULT_VER_ANSWER
        defaultCreditCheckVerificationShouldBeFound("verAnswer.equals=" + DEFAULT_VER_ANSWER);

        // Get all the creditCheckVerificationList where verAnswer equals to UPDATED_VER_ANSWER
        defaultCreditCheckVerificationShouldNotBeFound("verAnswer.equals=" + UPDATED_VER_ANSWER);
    }

    @Test
    @Transactional
    void getAllCreditCheckVerificationsByVerAnswerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        creditCheckVerificationRepository.saveAndFlush(creditCheckVerification);

        // Get all the creditCheckVerificationList where verAnswer not equals to DEFAULT_VER_ANSWER
        defaultCreditCheckVerificationShouldNotBeFound("verAnswer.notEquals=" + DEFAULT_VER_ANSWER);

        // Get all the creditCheckVerificationList where verAnswer not equals to UPDATED_VER_ANSWER
        defaultCreditCheckVerificationShouldBeFound("verAnswer.notEquals=" + UPDATED_VER_ANSWER);
    }

    @Test
    @Transactional
    void getAllCreditCheckVerificationsByVerAnswerIsInShouldWork() throws Exception {
        // Initialize the database
        creditCheckVerificationRepository.saveAndFlush(creditCheckVerification);

        // Get all the creditCheckVerificationList where verAnswer in DEFAULT_VER_ANSWER or UPDATED_VER_ANSWER
        defaultCreditCheckVerificationShouldBeFound("verAnswer.in=" + DEFAULT_VER_ANSWER + "," + UPDATED_VER_ANSWER);

        // Get all the creditCheckVerificationList where verAnswer equals to UPDATED_VER_ANSWER
        defaultCreditCheckVerificationShouldNotBeFound("verAnswer.in=" + UPDATED_VER_ANSWER);
    }

    @Test
    @Transactional
    void getAllCreditCheckVerificationsByVerAnswerIsNullOrNotNull() throws Exception {
        // Initialize the database
        creditCheckVerificationRepository.saveAndFlush(creditCheckVerification);

        // Get all the creditCheckVerificationList where verAnswer is not null
        defaultCreditCheckVerificationShouldBeFound("verAnswer.specified=true");

        // Get all the creditCheckVerificationList where verAnswer is null
        defaultCreditCheckVerificationShouldNotBeFound("verAnswer.specified=false");
    }

    @Test
    @Transactional
    void getAllCreditCheckVerificationsByVerAnswerContainsSomething() throws Exception {
        // Initialize the database
        creditCheckVerificationRepository.saveAndFlush(creditCheckVerification);

        // Get all the creditCheckVerificationList where verAnswer contains DEFAULT_VER_ANSWER
        defaultCreditCheckVerificationShouldBeFound("verAnswer.contains=" + DEFAULT_VER_ANSWER);

        // Get all the creditCheckVerificationList where verAnswer contains UPDATED_VER_ANSWER
        defaultCreditCheckVerificationShouldNotBeFound("verAnswer.contains=" + UPDATED_VER_ANSWER);
    }

    @Test
    @Transactional
    void getAllCreditCheckVerificationsByVerAnswerNotContainsSomething() throws Exception {
        // Initialize the database
        creditCheckVerificationRepository.saveAndFlush(creditCheckVerification);

        // Get all the creditCheckVerificationList where verAnswer does not contain DEFAULT_VER_ANSWER
        defaultCreditCheckVerificationShouldNotBeFound("verAnswer.doesNotContain=" + DEFAULT_VER_ANSWER);

        // Get all the creditCheckVerificationList where verAnswer does not contain UPDATED_VER_ANSWER
        defaultCreditCheckVerificationShouldBeFound("verAnswer.doesNotContain=" + UPDATED_VER_ANSWER);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCreditCheckVerificationShouldBeFound(String filter) throws Exception {
        restCreditCheckVerificationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(creditCheckVerification.getId().intValue())))
            .andExpect(jsonPath("$.[*].verQuestion").value(hasItem(DEFAULT_VER_QUESTION)))
            .andExpect(jsonPath("$.[*].verQuestionChoice").value(hasItem(DEFAULT_VER_QUESTION_CHOICE)))
            .andExpect(jsonPath("$.[*].verAnswer").value(hasItem(DEFAULT_VER_ANSWER)));

        // Check, that the count call also returns 1
        restCreditCheckVerificationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCreditCheckVerificationShouldNotBeFound(String filter) throws Exception {
        restCreditCheckVerificationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCreditCheckVerificationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCreditCheckVerification() throws Exception {
        // Get the creditCheckVerification
        restCreditCheckVerificationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCreditCheckVerification() throws Exception {
        // Initialize the database
        creditCheckVerificationRepository.saveAndFlush(creditCheckVerification);

        int databaseSizeBeforeUpdate = creditCheckVerificationRepository.findAll().size();

        // Update the creditCheckVerification
        CreditCheckVerification updatedCreditCheckVerification = creditCheckVerificationRepository
            .findById(creditCheckVerification.getId())
            .get();
        // Disconnect from session so that the updates on updatedCreditCheckVerification are not directly saved in db
        em.detach(updatedCreditCheckVerification);
        updatedCreditCheckVerification
            .verQuestion(UPDATED_VER_QUESTION)
            .verQuestionChoice(UPDATED_VER_QUESTION_CHOICE)
            .verAnswer(UPDATED_VER_ANSWER);
        CreditCheckVerificationDTO creditCheckVerificationDTO = creditCheckVerificationMapper.toDto(updatedCreditCheckVerification);

        restCreditCheckVerificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, creditCheckVerificationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCheckVerificationDTO))
            )
            .andExpect(status().isOk());

        // Validate the CreditCheckVerification in the database
        List<CreditCheckVerification> creditCheckVerificationList = creditCheckVerificationRepository.findAll();
        assertThat(creditCheckVerificationList).hasSize(databaseSizeBeforeUpdate);
        CreditCheckVerification testCreditCheckVerification = creditCheckVerificationList.get(creditCheckVerificationList.size() - 1);
        assertThat(testCreditCheckVerification.getVerQuestion()).isEqualTo(UPDATED_VER_QUESTION);
        assertThat(testCreditCheckVerification.getVerQuestionChoice()).isEqualTo(UPDATED_VER_QUESTION_CHOICE);
        assertThat(testCreditCheckVerification.getVerAnswer()).isEqualTo(UPDATED_VER_ANSWER);
    }

    @Test
    @Transactional
    void putNonExistingCreditCheckVerification() throws Exception {
        int databaseSizeBeforeUpdate = creditCheckVerificationRepository.findAll().size();
        creditCheckVerification.setId(count.incrementAndGet());

        // Create the CreditCheckVerification
        CreditCheckVerificationDTO creditCheckVerificationDTO = creditCheckVerificationMapper.toDto(creditCheckVerification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreditCheckVerificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, creditCheckVerificationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCheckVerificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditCheckVerification in the database
        List<CreditCheckVerification> creditCheckVerificationList = creditCheckVerificationRepository.findAll();
        assertThat(creditCheckVerificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCreditCheckVerification() throws Exception {
        int databaseSizeBeforeUpdate = creditCheckVerificationRepository.findAll().size();
        creditCheckVerification.setId(count.incrementAndGet());

        // Create the CreditCheckVerification
        CreditCheckVerificationDTO creditCheckVerificationDTO = creditCheckVerificationMapper.toDto(creditCheckVerification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditCheckVerificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCheckVerificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditCheckVerification in the database
        List<CreditCheckVerification> creditCheckVerificationList = creditCheckVerificationRepository.findAll();
        assertThat(creditCheckVerificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCreditCheckVerification() throws Exception {
        int databaseSizeBeforeUpdate = creditCheckVerificationRepository.findAll().size();
        creditCheckVerification.setId(count.incrementAndGet());

        // Create the CreditCheckVerification
        CreditCheckVerificationDTO creditCheckVerificationDTO = creditCheckVerificationMapper.toDto(creditCheckVerification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditCheckVerificationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditCheckVerificationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CreditCheckVerification in the database
        List<CreditCheckVerification> creditCheckVerificationList = creditCheckVerificationRepository.findAll();
        assertThat(creditCheckVerificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCreditCheckVerificationWithPatch() throws Exception {
        // Initialize the database
        creditCheckVerificationRepository.saveAndFlush(creditCheckVerification);

        int databaseSizeBeforeUpdate = creditCheckVerificationRepository.findAll().size();

        // Update the creditCheckVerification using partial update
        CreditCheckVerification partialUpdatedCreditCheckVerification = new CreditCheckVerification();
        partialUpdatedCreditCheckVerification.setId(creditCheckVerification.getId());

        partialUpdatedCreditCheckVerification.verQuestion(UPDATED_VER_QUESTION);

        restCreditCheckVerificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCreditCheckVerification.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCreditCheckVerification))
            )
            .andExpect(status().isOk());

        // Validate the CreditCheckVerification in the database
        List<CreditCheckVerification> creditCheckVerificationList = creditCheckVerificationRepository.findAll();
        assertThat(creditCheckVerificationList).hasSize(databaseSizeBeforeUpdate);
        CreditCheckVerification testCreditCheckVerification = creditCheckVerificationList.get(creditCheckVerificationList.size() - 1);
        assertThat(testCreditCheckVerification.getVerQuestion()).isEqualTo(UPDATED_VER_QUESTION);
        assertThat(testCreditCheckVerification.getVerQuestionChoice()).isEqualTo(DEFAULT_VER_QUESTION_CHOICE);
        assertThat(testCreditCheckVerification.getVerAnswer()).isEqualTo(DEFAULT_VER_ANSWER);
    }

    @Test
    @Transactional
    void fullUpdateCreditCheckVerificationWithPatch() throws Exception {
        // Initialize the database
        creditCheckVerificationRepository.saveAndFlush(creditCheckVerification);

        int databaseSizeBeforeUpdate = creditCheckVerificationRepository.findAll().size();

        // Update the creditCheckVerification using partial update
        CreditCheckVerification partialUpdatedCreditCheckVerification = new CreditCheckVerification();
        partialUpdatedCreditCheckVerification.setId(creditCheckVerification.getId());

        partialUpdatedCreditCheckVerification
            .verQuestion(UPDATED_VER_QUESTION)
            .verQuestionChoice(UPDATED_VER_QUESTION_CHOICE)
            .verAnswer(UPDATED_VER_ANSWER);

        restCreditCheckVerificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCreditCheckVerification.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCreditCheckVerification))
            )
            .andExpect(status().isOk());

        // Validate the CreditCheckVerification in the database
        List<CreditCheckVerification> creditCheckVerificationList = creditCheckVerificationRepository.findAll();
        assertThat(creditCheckVerificationList).hasSize(databaseSizeBeforeUpdate);
        CreditCheckVerification testCreditCheckVerification = creditCheckVerificationList.get(creditCheckVerificationList.size() - 1);
        assertThat(testCreditCheckVerification.getVerQuestion()).isEqualTo(UPDATED_VER_QUESTION);
        assertThat(testCreditCheckVerification.getVerQuestionChoice()).isEqualTo(UPDATED_VER_QUESTION_CHOICE);
        assertThat(testCreditCheckVerification.getVerAnswer()).isEqualTo(UPDATED_VER_ANSWER);
    }

    @Test
    @Transactional
    void patchNonExistingCreditCheckVerification() throws Exception {
        int databaseSizeBeforeUpdate = creditCheckVerificationRepository.findAll().size();
        creditCheckVerification.setId(count.incrementAndGet());

        // Create the CreditCheckVerification
        CreditCheckVerificationDTO creditCheckVerificationDTO = creditCheckVerificationMapper.toDto(creditCheckVerification);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreditCheckVerificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, creditCheckVerificationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(creditCheckVerificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditCheckVerification in the database
        List<CreditCheckVerification> creditCheckVerificationList = creditCheckVerificationRepository.findAll();
        assertThat(creditCheckVerificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCreditCheckVerification() throws Exception {
        int databaseSizeBeforeUpdate = creditCheckVerificationRepository.findAll().size();
        creditCheckVerification.setId(count.incrementAndGet());

        // Create the CreditCheckVerification
        CreditCheckVerificationDTO creditCheckVerificationDTO = creditCheckVerificationMapper.toDto(creditCheckVerification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditCheckVerificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(creditCheckVerificationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditCheckVerification in the database
        List<CreditCheckVerification> creditCheckVerificationList = creditCheckVerificationRepository.findAll();
        assertThat(creditCheckVerificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCreditCheckVerification() throws Exception {
        int databaseSizeBeforeUpdate = creditCheckVerificationRepository.findAll().size();
        creditCheckVerification.setId(count.incrementAndGet());

        // Create the CreditCheckVerification
        CreditCheckVerificationDTO creditCheckVerificationDTO = creditCheckVerificationMapper.toDto(creditCheckVerification);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditCheckVerificationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(creditCheckVerificationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CreditCheckVerification in the database
        List<CreditCheckVerification> creditCheckVerificationList = creditCheckVerificationRepository.findAll();
        assertThat(creditCheckVerificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCreditCheckVerification() throws Exception {
        // Initialize the database
        creditCheckVerificationRepository.saveAndFlush(creditCheckVerification);

        int databaseSizeBeforeDelete = creditCheckVerificationRepository.findAll().size();

        // Delete the creditCheckVerification
        restCreditCheckVerificationMockMvc
            .perform(delete(ENTITY_API_URL_ID, creditCheckVerification.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CreditCheckVerification> creditCheckVerificationList = creditCheckVerificationRepository.findAll();
        assertThat(creditCheckVerificationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
