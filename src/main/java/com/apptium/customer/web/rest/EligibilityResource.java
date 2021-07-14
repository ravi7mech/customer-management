package com.apptium.customer.web.rest;

import com.apptium.customer.repository.EligibilityRepository;
import com.apptium.customer.service.EligibilityQueryService;
import com.apptium.customer.service.EligibilityService;
import com.apptium.customer.service.criteria.EligibilityCriteria;
import com.apptium.customer.service.dto.EligibilityDTO;
import com.apptium.customer.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.apptium.customer.domain.Eligibility}.
 */
@RestController
@RequestMapping("/api")
public class EligibilityResource {

    private final Logger log = LoggerFactory.getLogger(EligibilityResource.class);

    private static final String ENTITY_NAME = "customerManagementEligibility";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EligibilityService eligibilityService;

    private final EligibilityRepository eligibilityRepository;

    private final EligibilityQueryService eligibilityQueryService;

    public EligibilityResource(
        EligibilityService eligibilityService,
        EligibilityRepository eligibilityRepository,
        EligibilityQueryService eligibilityQueryService
    ) {
        this.eligibilityService = eligibilityService;
        this.eligibilityRepository = eligibilityRepository;
        this.eligibilityQueryService = eligibilityQueryService;
    }

    /**
     * {@code POST  /eligibilities} : Create a new eligibility.
     *
     * @param eligibilityDTO the eligibilityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eligibilityDTO, or with status {@code 400 (Bad Request)} if the eligibility has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/eligibilities")
    public ResponseEntity<EligibilityDTO> createEligibility(@Valid @RequestBody EligibilityDTO eligibilityDTO) throws URISyntaxException {
        log.debug("REST request to save Eligibility : {}", eligibilityDTO);
        if (eligibilityDTO.getId() != null) {
            throw new BadRequestAlertException("A new eligibility cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EligibilityDTO result = eligibilityService.save(eligibilityDTO);
        return ResponseEntity
            .created(new URI("/api/eligibilities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /eligibilities/:id} : Updates an existing eligibility.
     *
     * @param id the id of the eligibilityDTO to save.
     * @param eligibilityDTO the eligibilityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eligibilityDTO,
     * or with status {@code 400 (Bad Request)} if the eligibilityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eligibilityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/eligibilities/{id}")
    public ResponseEntity<EligibilityDTO> updateEligibility(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EligibilityDTO eligibilityDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Eligibility : {}, {}", id, eligibilityDTO);
        if (eligibilityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eligibilityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eligibilityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EligibilityDTO result = eligibilityService.save(eligibilityDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eligibilityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /eligibilities/:id} : Partial updates given fields of an existing eligibility, field will ignore if it is null
     *
     * @param id the id of the eligibilityDTO to save.
     * @param eligibilityDTO the eligibilityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eligibilityDTO,
     * or with status {@code 400 (Bad Request)} if the eligibilityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the eligibilityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the eligibilityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/eligibilities/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EligibilityDTO> partialUpdateEligibility(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EligibilityDTO eligibilityDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Eligibility partially : {}, {}", id, eligibilityDTO);
        if (eligibilityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eligibilityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eligibilityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EligibilityDTO> result = eligibilityService.partialUpdate(eligibilityDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eligibilityDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /eligibilities} : get all the eligibilities.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eligibilities in body.
     */
    @GetMapping("/eligibilities")
    public ResponseEntity<List<EligibilityDTO>> getAllEligibilities(EligibilityCriteria criteria) {
        log.debug("REST request to get Eligibilities by criteria: {}", criteria);
        List<EligibilityDTO> entityList = eligibilityQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /eligibilities/count} : count all the eligibilities.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/eligibilities/count")
    public ResponseEntity<Long> countEligibilities(EligibilityCriteria criteria) {
        log.debug("REST request to count Eligibilities by criteria: {}", criteria);
        return ResponseEntity.ok().body(eligibilityQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /eligibilities/:id} : get the "id" eligibility.
     *
     * @param id the id of the eligibilityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eligibilityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/eligibilities/{id}")
    public ResponseEntity<EligibilityDTO> getEligibility(@PathVariable Long id) {
        log.debug("REST request to get Eligibility : {}", id);
        Optional<EligibilityDTO> eligibilityDTO = eligibilityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eligibilityDTO);
    }

    /**
     * {@code DELETE  /eligibilities/:id} : delete the "id" eligibility.
     *
     * @param id the id of the eligibilityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/eligibilities/{id}")
    public ResponseEntity<Void> deleteEligibility(@PathVariable Long id) {
        log.debug("REST request to delete Eligibility : {}", id);
        eligibilityService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
