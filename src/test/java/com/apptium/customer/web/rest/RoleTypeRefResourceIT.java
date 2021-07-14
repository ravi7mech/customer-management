package com.apptium.customer.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.apptium.customer.IntegrationTest;
import com.apptium.customer.domain.CustRelParty;
import com.apptium.customer.domain.RoleTypeRef;
import com.apptium.customer.repository.RoleTypeRefRepository;
import com.apptium.customer.service.criteria.RoleTypeRefCriteria;
import com.apptium.customer.service.dto.RoleTypeRefDTO;
import com.apptium.customer.service.mapper.RoleTypeRefMapper;
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
 * Integration tests for the {@link RoleTypeRefResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RoleTypeRefResourceIT {

    private static final String DEFAULT_ROLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ROLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ROLE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/role-type-refs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RoleTypeRefRepository roleTypeRefRepository;

    @Autowired
    private RoleTypeRefMapper roleTypeRefMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoleTypeRefMockMvc;

    private RoleTypeRef roleTypeRef;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoleTypeRef createEntity(EntityManager em) {
        RoleTypeRef roleTypeRef = new RoleTypeRef().roleName(DEFAULT_ROLE_NAME).roleType(DEFAULT_ROLE_TYPE);
        return roleTypeRef;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RoleTypeRef createUpdatedEntity(EntityManager em) {
        RoleTypeRef roleTypeRef = new RoleTypeRef().roleName(UPDATED_ROLE_NAME).roleType(UPDATED_ROLE_TYPE);
        return roleTypeRef;
    }

    @BeforeEach
    public void initTest() {
        roleTypeRef = createEntity(em);
    }

    @Test
    @Transactional
    void createRoleTypeRef() throws Exception {
        int databaseSizeBeforeCreate = roleTypeRefRepository.findAll().size();
        // Create the RoleTypeRef
        RoleTypeRefDTO roleTypeRefDTO = roleTypeRefMapper.toDto(roleTypeRef);
        restRoleTypeRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roleTypeRefDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RoleTypeRef in the database
        List<RoleTypeRef> roleTypeRefList = roleTypeRefRepository.findAll();
        assertThat(roleTypeRefList).hasSize(databaseSizeBeforeCreate + 1);
        RoleTypeRef testRoleTypeRef = roleTypeRefList.get(roleTypeRefList.size() - 1);
        assertThat(testRoleTypeRef.getRoleName()).isEqualTo(DEFAULT_ROLE_NAME);
        assertThat(testRoleTypeRef.getRoleType()).isEqualTo(DEFAULT_ROLE_TYPE);
    }

    @Test
    @Transactional
    void createRoleTypeRefWithExistingId() throws Exception {
        // Create the RoleTypeRef with an existing ID
        roleTypeRef.setId(1L);
        RoleTypeRefDTO roleTypeRefDTO = roleTypeRefMapper.toDto(roleTypeRef);

        int databaseSizeBeforeCreate = roleTypeRefRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoleTypeRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roleTypeRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleTypeRef in the database
        List<RoleTypeRef> roleTypeRefList = roleTypeRefRepository.findAll();
        assertThat(roleTypeRefList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRoleNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleTypeRefRepository.findAll().size();
        // set the field null
        roleTypeRef.setRoleName(null);

        // Create the RoleTypeRef, which fails.
        RoleTypeRefDTO roleTypeRefDTO = roleTypeRefMapper.toDto(roleTypeRef);

        restRoleTypeRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roleTypeRefDTO))
            )
            .andExpect(status().isBadRequest());

        List<RoleTypeRef> roleTypeRefList = roleTypeRefRepository.findAll();
        assertThat(roleTypeRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRoleTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = roleTypeRefRepository.findAll().size();
        // set the field null
        roleTypeRef.setRoleType(null);

        // Create the RoleTypeRef, which fails.
        RoleTypeRefDTO roleTypeRefDTO = roleTypeRefMapper.toDto(roleTypeRef);

        restRoleTypeRefMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roleTypeRefDTO))
            )
            .andExpect(status().isBadRequest());

        List<RoleTypeRef> roleTypeRefList = roleTypeRefRepository.findAll();
        assertThat(roleTypeRefList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRoleTypeRefs() throws Exception {
        // Initialize the database
        roleTypeRefRepository.saveAndFlush(roleTypeRef);

        // Get all the roleTypeRefList
        restRoleTypeRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roleTypeRef.getId().intValue())))
            .andExpect(jsonPath("$.[*].roleName").value(hasItem(DEFAULT_ROLE_NAME)))
            .andExpect(jsonPath("$.[*].roleType").value(hasItem(DEFAULT_ROLE_TYPE)));
    }

    @Test
    @Transactional
    void getRoleTypeRef() throws Exception {
        // Initialize the database
        roleTypeRefRepository.saveAndFlush(roleTypeRef);

        // Get the roleTypeRef
        restRoleTypeRefMockMvc
            .perform(get(ENTITY_API_URL_ID, roleTypeRef.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(roleTypeRef.getId().intValue()))
            .andExpect(jsonPath("$.roleName").value(DEFAULT_ROLE_NAME))
            .andExpect(jsonPath("$.roleType").value(DEFAULT_ROLE_TYPE));
    }

    @Test
    @Transactional
    void getRoleTypeRefsByIdFiltering() throws Exception {
        // Initialize the database
        roleTypeRefRepository.saveAndFlush(roleTypeRef);

        Long id = roleTypeRef.getId();

        defaultRoleTypeRefShouldBeFound("id.equals=" + id);
        defaultRoleTypeRefShouldNotBeFound("id.notEquals=" + id);

        defaultRoleTypeRefShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRoleTypeRefShouldNotBeFound("id.greaterThan=" + id);

        defaultRoleTypeRefShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRoleTypeRefShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRoleTypeRefsByRoleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        roleTypeRefRepository.saveAndFlush(roleTypeRef);

        // Get all the roleTypeRefList where roleName equals to DEFAULT_ROLE_NAME
        defaultRoleTypeRefShouldBeFound("roleName.equals=" + DEFAULT_ROLE_NAME);

        // Get all the roleTypeRefList where roleName equals to UPDATED_ROLE_NAME
        defaultRoleTypeRefShouldNotBeFound("roleName.equals=" + UPDATED_ROLE_NAME);
    }

    @Test
    @Transactional
    void getAllRoleTypeRefsByRoleNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roleTypeRefRepository.saveAndFlush(roleTypeRef);

        // Get all the roleTypeRefList where roleName not equals to DEFAULT_ROLE_NAME
        defaultRoleTypeRefShouldNotBeFound("roleName.notEquals=" + DEFAULT_ROLE_NAME);

        // Get all the roleTypeRefList where roleName not equals to UPDATED_ROLE_NAME
        defaultRoleTypeRefShouldBeFound("roleName.notEquals=" + UPDATED_ROLE_NAME);
    }

    @Test
    @Transactional
    void getAllRoleTypeRefsByRoleNameIsInShouldWork() throws Exception {
        // Initialize the database
        roleTypeRefRepository.saveAndFlush(roleTypeRef);

        // Get all the roleTypeRefList where roleName in DEFAULT_ROLE_NAME or UPDATED_ROLE_NAME
        defaultRoleTypeRefShouldBeFound("roleName.in=" + DEFAULT_ROLE_NAME + "," + UPDATED_ROLE_NAME);

        // Get all the roleTypeRefList where roleName equals to UPDATED_ROLE_NAME
        defaultRoleTypeRefShouldNotBeFound("roleName.in=" + UPDATED_ROLE_NAME);
    }

    @Test
    @Transactional
    void getAllRoleTypeRefsByRoleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        roleTypeRefRepository.saveAndFlush(roleTypeRef);

        // Get all the roleTypeRefList where roleName is not null
        defaultRoleTypeRefShouldBeFound("roleName.specified=true");

        // Get all the roleTypeRefList where roleName is null
        defaultRoleTypeRefShouldNotBeFound("roleName.specified=false");
    }

    @Test
    @Transactional
    void getAllRoleTypeRefsByRoleNameContainsSomething() throws Exception {
        // Initialize the database
        roleTypeRefRepository.saveAndFlush(roleTypeRef);

        // Get all the roleTypeRefList where roleName contains DEFAULT_ROLE_NAME
        defaultRoleTypeRefShouldBeFound("roleName.contains=" + DEFAULT_ROLE_NAME);

        // Get all the roleTypeRefList where roleName contains UPDATED_ROLE_NAME
        defaultRoleTypeRefShouldNotBeFound("roleName.contains=" + UPDATED_ROLE_NAME);
    }

    @Test
    @Transactional
    void getAllRoleTypeRefsByRoleNameNotContainsSomething() throws Exception {
        // Initialize the database
        roleTypeRefRepository.saveAndFlush(roleTypeRef);

        // Get all the roleTypeRefList where roleName does not contain DEFAULT_ROLE_NAME
        defaultRoleTypeRefShouldNotBeFound("roleName.doesNotContain=" + DEFAULT_ROLE_NAME);

        // Get all the roleTypeRefList where roleName does not contain UPDATED_ROLE_NAME
        defaultRoleTypeRefShouldBeFound("roleName.doesNotContain=" + UPDATED_ROLE_NAME);
    }

    @Test
    @Transactional
    void getAllRoleTypeRefsByRoleTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        roleTypeRefRepository.saveAndFlush(roleTypeRef);

        // Get all the roleTypeRefList where roleType equals to DEFAULT_ROLE_TYPE
        defaultRoleTypeRefShouldBeFound("roleType.equals=" + DEFAULT_ROLE_TYPE);

        // Get all the roleTypeRefList where roleType equals to UPDATED_ROLE_TYPE
        defaultRoleTypeRefShouldNotBeFound("roleType.equals=" + UPDATED_ROLE_TYPE);
    }

    @Test
    @Transactional
    void getAllRoleTypeRefsByRoleTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roleTypeRefRepository.saveAndFlush(roleTypeRef);

        // Get all the roleTypeRefList where roleType not equals to DEFAULT_ROLE_TYPE
        defaultRoleTypeRefShouldNotBeFound("roleType.notEquals=" + DEFAULT_ROLE_TYPE);

        // Get all the roleTypeRefList where roleType not equals to UPDATED_ROLE_TYPE
        defaultRoleTypeRefShouldBeFound("roleType.notEquals=" + UPDATED_ROLE_TYPE);
    }

    @Test
    @Transactional
    void getAllRoleTypeRefsByRoleTypeIsInShouldWork() throws Exception {
        // Initialize the database
        roleTypeRefRepository.saveAndFlush(roleTypeRef);

        // Get all the roleTypeRefList where roleType in DEFAULT_ROLE_TYPE or UPDATED_ROLE_TYPE
        defaultRoleTypeRefShouldBeFound("roleType.in=" + DEFAULT_ROLE_TYPE + "," + UPDATED_ROLE_TYPE);

        // Get all the roleTypeRefList where roleType equals to UPDATED_ROLE_TYPE
        defaultRoleTypeRefShouldNotBeFound("roleType.in=" + UPDATED_ROLE_TYPE);
    }

    @Test
    @Transactional
    void getAllRoleTypeRefsByRoleTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        roleTypeRefRepository.saveAndFlush(roleTypeRef);

        // Get all the roleTypeRefList where roleType is not null
        defaultRoleTypeRefShouldBeFound("roleType.specified=true");

        // Get all the roleTypeRefList where roleType is null
        defaultRoleTypeRefShouldNotBeFound("roleType.specified=false");
    }

    @Test
    @Transactional
    void getAllRoleTypeRefsByRoleTypeContainsSomething() throws Exception {
        // Initialize the database
        roleTypeRefRepository.saveAndFlush(roleTypeRef);

        // Get all the roleTypeRefList where roleType contains DEFAULT_ROLE_TYPE
        defaultRoleTypeRefShouldBeFound("roleType.contains=" + DEFAULT_ROLE_TYPE);

        // Get all the roleTypeRefList where roleType contains UPDATED_ROLE_TYPE
        defaultRoleTypeRefShouldNotBeFound("roleType.contains=" + UPDATED_ROLE_TYPE);
    }

    @Test
    @Transactional
    void getAllRoleTypeRefsByRoleTypeNotContainsSomething() throws Exception {
        // Initialize the database
        roleTypeRefRepository.saveAndFlush(roleTypeRef);

        // Get all the roleTypeRefList where roleType does not contain DEFAULT_ROLE_TYPE
        defaultRoleTypeRefShouldNotBeFound("roleType.doesNotContain=" + DEFAULT_ROLE_TYPE);

        // Get all the roleTypeRefList where roleType does not contain UPDATED_ROLE_TYPE
        defaultRoleTypeRefShouldBeFound("roleType.doesNotContain=" + UPDATED_ROLE_TYPE);
    }

    @Test
    @Transactional
    void getAllRoleTypeRefsByCustRelPartyIsEqualToSomething() throws Exception {
        // Initialize the database
        roleTypeRefRepository.saveAndFlush(roleTypeRef);
        CustRelParty custRelParty = CustRelPartyResourceIT.createEntity(em);
        em.persist(custRelParty);
        em.flush();
        roleTypeRef.addCustRelParty(custRelParty);
        roleTypeRefRepository.saveAndFlush(roleTypeRef);
        Long custRelPartyId = custRelParty.getId();

        // Get all the roleTypeRefList where custRelParty equals to custRelPartyId
        defaultRoleTypeRefShouldBeFound("custRelPartyId.equals=" + custRelPartyId);

        // Get all the roleTypeRefList where custRelParty equals to (custRelPartyId + 1)
        defaultRoleTypeRefShouldNotBeFound("custRelPartyId.equals=" + (custRelPartyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRoleTypeRefShouldBeFound(String filter) throws Exception {
        restRoleTypeRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roleTypeRef.getId().intValue())))
            .andExpect(jsonPath("$.[*].roleName").value(hasItem(DEFAULT_ROLE_NAME)))
            .andExpect(jsonPath("$.[*].roleType").value(hasItem(DEFAULT_ROLE_TYPE)));

        // Check, that the count call also returns 1
        restRoleTypeRefMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRoleTypeRefShouldNotBeFound(String filter) throws Exception {
        restRoleTypeRefMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRoleTypeRefMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRoleTypeRef() throws Exception {
        // Get the roleTypeRef
        restRoleTypeRefMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRoleTypeRef() throws Exception {
        // Initialize the database
        roleTypeRefRepository.saveAndFlush(roleTypeRef);

        int databaseSizeBeforeUpdate = roleTypeRefRepository.findAll().size();

        // Update the roleTypeRef
        RoleTypeRef updatedRoleTypeRef = roleTypeRefRepository.findById(roleTypeRef.getId()).get();
        // Disconnect from session so that the updates on updatedRoleTypeRef are not directly saved in db
        em.detach(updatedRoleTypeRef);
        updatedRoleTypeRef.roleName(UPDATED_ROLE_NAME).roleType(UPDATED_ROLE_TYPE);
        RoleTypeRefDTO roleTypeRefDTO = roleTypeRefMapper.toDto(updatedRoleTypeRef);

        restRoleTypeRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roleTypeRefDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roleTypeRefDTO))
            )
            .andExpect(status().isOk());

        // Validate the RoleTypeRef in the database
        List<RoleTypeRef> roleTypeRefList = roleTypeRefRepository.findAll();
        assertThat(roleTypeRefList).hasSize(databaseSizeBeforeUpdate);
        RoleTypeRef testRoleTypeRef = roleTypeRefList.get(roleTypeRefList.size() - 1);
        assertThat(testRoleTypeRef.getRoleName()).isEqualTo(UPDATED_ROLE_NAME);
        assertThat(testRoleTypeRef.getRoleType()).isEqualTo(UPDATED_ROLE_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingRoleTypeRef() throws Exception {
        int databaseSizeBeforeUpdate = roleTypeRefRepository.findAll().size();
        roleTypeRef.setId(count.incrementAndGet());

        // Create the RoleTypeRef
        RoleTypeRefDTO roleTypeRefDTO = roleTypeRefMapper.toDto(roleTypeRef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoleTypeRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, roleTypeRefDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roleTypeRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleTypeRef in the database
        List<RoleTypeRef> roleTypeRefList = roleTypeRefRepository.findAll();
        assertThat(roleTypeRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRoleTypeRef() throws Exception {
        int databaseSizeBeforeUpdate = roleTypeRefRepository.findAll().size();
        roleTypeRef.setId(count.incrementAndGet());

        // Create the RoleTypeRef
        RoleTypeRefDTO roleTypeRefDTO = roleTypeRefMapper.toDto(roleTypeRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleTypeRefMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(roleTypeRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleTypeRef in the database
        List<RoleTypeRef> roleTypeRefList = roleTypeRefRepository.findAll();
        assertThat(roleTypeRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRoleTypeRef() throws Exception {
        int databaseSizeBeforeUpdate = roleTypeRefRepository.findAll().size();
        roleTypeRef.setId(count.incrementAndGet());

        // Create the RoleTypeRef
        RoleTypeRefDTO roleTypeRefDTO = roleTypeRefMapper.toDto(roleTypeRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleTypeRefMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(roleTypeRefDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoleTypeRef in the database
        List<RoleTypeRef> roleTypeRefList = roleTypeRefRepository.findAll();
        assertThat(roleTypeRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRoleTypeRefWithPatch() throws Exception {
        // Initialize the database
        roleTypeRefRepository.saveAndFlush(roleTypeRef);

        int databaseSizeBeforeUpdate = roleTypeRefRepository.findAll().size();

        // Update the roleTypeRef using partial update
        RoleTypeRef partialUpdatedRoleTypeRef = new RoleTypeRef();
        partialUpdatedRoleTypeRef.setId(roleTypeRef.getId());

        restRoleTypeRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoleTypeRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoleTypeRef))
            )
            .andExpect(status().isOk());

        // Validate the RoleTypeRef in the database
        List<RoleTypeRef> roleTypeRefList = roleTypeRefRepository.findAll();
        assertThat(roleTypeRefList).hasSize(databaseSizeBeforeUpdate);
        RoleTypeRef testRoleTypeRef = roleTypeRefList.get(roleTypeRefList.size() - 1);
        assertThat(testRoleTypeRef.getRoleName()).isEqualTo(DEFAULT_ROLE_NAME);
        assertThat(testRoleTypeRef.getRoleType()).isEqualTo(DEFAULT_ROLE_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateRoleTypeRefWithPatch() throws Exception {
        // Initialize the database
        roleTypeRefRepository.saveAndFlush(roleTypeRef);

        int databaseSizeBeforeUpdate = roleTypeRefRepository.findAll().size();

        // Update the roleTypeRef using partial update
        RoleTypeRef partialUpdatedRoleTypeRef = new RoleTypeRef();
        partialUpdatedRoleTypeRef.setId(roleTypeRef.getId());

        partialUpdatedRoleTypeRef.roleName(UPDATED_ROLE_NAME).roleType(UPDATED_ROLE_TYPE);

        restRoleTypeRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRoleTypeRef.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRoleTypeRef))
            )
            .andExpect(status().isOk());

        // Validate the RoleTypeRef in the database
        List<RoleTypeRef> roleTypeRefList = roleTypeRefRepository.findAll();
        assertThat(roleTypeRefList).hasSize(databaseSizeBeforeUpdate);
        RoleTypeRef testRoleTypeRef = roleTypeRefList.get(roleTypeRefList.size() - 1);
        assertThat(testRoleTypeRef.getRoleName()).isEqualTo(UPDATED_ROLE_NAME);
        assertThat(testRoleTypeRef.getRoleType()).isEqualTo(UPDATED_ROLE_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingRoleTypeRef() throws Exception {
        int databaseSizeBeforeUpdate = roleTypeRefRepository.findAll().size();
        roleTypeRef.setId(count.incrementAndGet());

        // Create the RoleTypeRef
        RoleTypeRefDTO roleTypeRefDTO = roleTypeRefMapper.toDto(roleTypeRef);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRoleTypeRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, roleTypeRefDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roleTypeRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleTypeRef in the database
        List<RoleTypeRef> roleTypeRefList = roleTypeRefRepository.findAll();
        assertThat(roleTypeRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRoleTypeRef() throws Exception {
        int databaseSizeBeforeUpdate = roleTypeRefRepository.findAll().size();
        roleTypeRef.setId(count.incrementAndGet());

        // Create the RoleTypeRef
        RoleTypeRefDTO roleTypeRefDTO = roleTypeRefMapper.toDto(roleTypeRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleTypeRefMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(roleTypeRefDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RoleTypeRef in the database
        List<RoleTypeRef> roleTypeRefList = roleTypeRefRepository.findAll();
        assertThat(roleTypeRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRoleTypeRef() throws Exception {
        int databaseSizeBeforeUpdate = roleTypeRefRepository.findAll().size();
        roleTypeRef.setId(count.incrementAndGet());

        // Create the RoleTypeRef
        RoleTypeRefDTO roleTypeRefDTO = roleTypeRefMapper.toDto(roleTypeRef);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRoleTypeRefMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(roleTypeRefDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RoleTypeRef in the database
        List<RoleTypeRef> roleTypeRefList = roleTypeRefRepository.findAll();
        assertThat(roleTypeRefList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRoleTypeRef() throws Exception {
        // Initialize the database
        roleTypeRefRepository.saveAndFlush(roleTypeRef);

        int databaseSizeBeforeDelete = roleTypeRefRepository.findAll().size();

        // Delete the roleTypeRef
        restRoleTypeRefMockMvc
            .perform(delete(ENTITY_API_URL_ID, roleTypeRef.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RoleTypeRef> roleTypeRefList = roleTypeRefRepository.findAll();
        assertThat(roleTypeRefList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
