package com.apptium.customer.web.rest;

import com.apptium.customer.repository.CreditCheckVerificationRepository;
import com.apptium.customer.service.CreditCheckVerificationQueryService;
import com.apptium.customer.service.CreditCheckVerificationService;
import com.apptium.customer.service.criteria.CreditCheckVerificationCriteria;
import com.apptium.customer.service.dto.CreditCheckVerificationDTO;
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
 * REST controller for managing {@link com.apptium.customer.domain.CreditCheckVerification}.
 */
@RestController
@RequestMapping("/api")
public class CreditCheckVerificationResource {

    private final Logger log = LoggerFactory.getLogger(CreditCheckVerificationResource.class);

    private static final String ENTITY_NAME = "customerManagementCreditCheckVerification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CreditCheckVerificationService creditCheckVerificationService;

    private final CreditCheckVerificationRepository creditCheckVerificationRepository;

    private final CreditCheckVerificationQueryService creditCheckVerificationQueryService;

    public CreditCheckVerificationResource(
        CreditCheckVerificationService creditCheckVerificationService,
        CreditCheckVerificationRepository creditCheckVerificationRepository,
        CreditCheckVerificationQueryService creditCheckVerificationQueryService
    ) {
        this.creditCheckVerificationService = creditCheckVerificationService;
        this.creditCheckVerificationRepository = creditCheckVerificationRepository;
        this.creditCheckVerificationQueryService = creditCheckVerificationQueryService;
    }

    /**
     * {@code POST  /credit-check-verifications} : Create a new creditCheckVerification.
     *
     * @param creditCheckVerificationDTO the creditCheckVerificationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new creditCheckVerificationDTO, or with status {@code 400 (Bad Request)} if the creditCheckVerification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/credit-check-verifications")
    public ResponseEntity<CreditCheckVerificationDTO> createCreditCheckVerification(
        @Valid @RequestBody CreditCheckVerificationDTO creditCheckVerificationDTO
    ) throws URISyntaxException {
        log.debug("REST request to save CreditCheckVerification : {}", creditCheckVerificationDTO);
        if (creditCheckVerificationDTO.getId() != null) {
            throw new BadRequestAlertException("A new creditCheckVerification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CreditCheckVerificationDTO result = creditCheckVerificationService.save(creditCheckVerificationDTO);
        return ResponseEntity
            .created(new URI("/api/credit-check-verifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /credit-check-verifications/:id} : Updates an existing creditCheckVerification.
     *
     * @param id the id of the creditCheckVerificationDTO to save.
     * @param creditCheckVerificationDTO the creditCheckVerificationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated creditCheckVerificationDTO,
     * or with status {@code 400 (Bad Request)} if the creditCheckVerificationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the creditCheckVerificationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/credit-check-verifications/{id}")
    public ResponseEntity<CreditCheckVerificationDTO> updateCreditCheckVerification(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CreditCheckVerificationDTO creditCheckVerificationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CreditCheckVerification : {}, {}", id, creditCheckVerificationDTO);
        if (creditCheckVerificationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, creditCheckVerificationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!creditCheckVerificationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CreditCheckVerificationDTO result = creditCheckVerificationService.save(creditCheckVerificationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, creditCheckVerificationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /credit-check-verifications/:id} : Partial updates given fields of an existing creditCheckVerification, field will ignore if it is null
     *
     * @param id the id of the creditCheckVerificationDTO to save.
     * @param creditCheckVerificationDTO the creditCheckVerificationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated creditCheckVerificationDTO,
     * or with status {@code 400 (Bad Request)} if the creditCheckVerificationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the creditCheckVerificationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the creditCheckVerificationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/credit-check-verifications/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CreditCheckVerificationDTO> partialUpdateCreditCheckVerification(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CreditCheckVerificationDTO creditCheckVerificationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CreditCheckVerification partially : {}, {}", id, creditCheckVerificationDTO);
        if (creditCheckVerificationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, creditCheckVerificationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!creditCheckVerificationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CreditCheckVerificationDTO> result = creditCheckVerificationService.partialUpdate(creditCheckVerificationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, creditCheckVerificationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /credit-check-verifications} : get all the creditCheckVerifications.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of creditCheckVerifications in body.
     */
    @GetMapping("/credit-check-verifications")
    public ResponseEntity<List<CreditCheckVerificationDTO>> getAllCreditCheckVerifications(CreditCheckVerificationCriteria criteria) {
        log.debug("REST request to get CreditCheckVerifications by criteria: {}", criteria);
        List<CreditCheckVerificationDTO> entityList = creditCheckVerificationQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /credit-check-verifications/count} : count all the creditCheckVerifications.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/credit-check-verifications/count")
    public ResponseEntity<Long> countCreditCheckVerifications(CreditCheckVerificationCriteria criteria) {
        log.debug("REST request to count CreditCheckVerifications by criteria: {}", criteria);
        return ResponseEntity.ok().body(creditCheckVerificationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /credit-check-verifications/:id} : get the "id" creditCheckVerification.
     *
     * @param id the id of the creditCheckVerificationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the creditCheckVerificationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/credit-check-verifications/{id}")
    public ResponseEntity<CreditCheckVerificationDTO> getCreditCheckVerification(@PathVariable Long id) {
        log.debug("REST request to get CreditCheckVerification : {}", id);
        Optional<CreditCheckVerificationDTO> creditCheckVerificationDTO = creditCheckVerificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(creditCheckVerificationDTO);
    }

    /**
     * {@code DELETE  /credit-check-verifications/:id} : delete the "id" creditCheckVerification.
     *
     * @param id the id of the creditCheckVerificationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/credit-check-verifications/{id}")
    public ResponseEntity<Void> deleteCreditCheckVerification(@PathVariable Long id) {
        log.debug("REST request to delete CreditCheckVerification : {}", id);
        creditCheckVerificationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
