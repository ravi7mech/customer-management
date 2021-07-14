package com.apptium.customer.web.rest;

import com.apptium.customer.repository.IndAuditTrialRepository;
import com.apptium.customer.service.IndAuditTrialQueryService;
import com.apptium.customer.service.IndAuditTrialService;
import com.apptium.customer.service.criteria.IndAuditTrialCriteria;
import com.apptium.customer.service.dto.IndAuditTrialDTO;
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
 * REST controller for managing {@link com.apptium.customer.domain.IndAuditTrial}.
 */
@RestController
@RequestMapping("/api")
public class IndAuditTrialResource {

    private final Logger log = LoggerFactory.getLogger(IndAuditTrialResource.class);

    private static final String ENTITY_NAME = "customerManagementIndAuditTrial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndAuditTrialService indAuditTrialService;

    private final IndAuditTrialRepository indAuditTrialRepository;

    private final IndAuditTrialQueryService indAuditTrialQueryService;

    public IndAuditTrialResource(
        IndAuditTrialService indAuditTrialService,
        IndAuditTrialRepository indAuditTrialRepository,
        IndAuditTrialQueryService indAuditTrialQueryService
    ) {
        this.indAuditTrialService = indAuditTrialService;
        this.indAuditTrialRepository = indAuditTrialRepository;
        this.indAuditTrialQueryService = indAuditTrialQueryService;
    }

    /**
     * {@code POST  /ind-audit-trials} : Create a new indAuditTrial.
     *
     * @param indAuditTrialDTO the indAuditTrialDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new indAuditTrialDTO, or with status {@code 400 (Bad Request)} if the indAuditTrial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ind-audit-trials")
    public ResponseEntity<IndAuditTrialDTO> createIndAuditTrial(@Valid @RequestBody IndAuditTrialDTO indAuditTrialDTO)
        throws URISyntaxException {
        log.debug("REST request to save IndAuditTrial : {}", indAuditTrialDTO);
        if (indAuditTrialDTO.getId() != null) {
            throw new BadRequestAlertException("A new indAuditTrial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IndAuditTrialDTO result = indAuditTrialService.save(indAuditTrialDTO);
        return ResponseEntity
            .created(new URI("/api/ind-audit-trials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ind-audit-trials/:id} : Updates an existing indAuditTrial.
     *
     * @param id the id of the indAuditTrialDTO to save.
     * @param indAuditTrialDTO the indAuditTrialDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indAuditTrialDTO,
     * or with status {@code 400 (Bad Request)} if the indAuditTrialDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the indAuditTrialDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ind-audit-trials/{id}")
    public ResponseEntity<IndAuditTrialDTO> updateIndAuditTrial(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IndAuditTrialDTO indAuditTrialDTO
    ) throws URISyntaxException {
        log.debug("REST request to update IndAuditTrial : {}, {}", id, indAuditTrialDTO);
        if (indAuditTrialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indAuditTrialDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indAuditTrialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IndAuditTrialDTO result = indAuditTrialService.save(indAuditTrialDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, indAuditTrialDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ind-audit-trials/:id} : Partial updates given fields of an existing indAuditTrial, field will ignore if it is null
     *
     * @param id the id of the indAuditTrialDTO to save.
     * @param indAuditTrialDTO the indAuditTrialDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indAuditTrialDTO,
     * or with status {@code 400 (Bad Request)} if the indAuditTrialDTO is not valid,
     * or with status {@code 404 (Not Found)} if the indAuditTrialDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the indAuditTrialDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ind-audit-trials/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<IndAuditTrialDTO> partialUpdateIndAuditTrial(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IndAuditTrialDTO indAuditTrialDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update IndAuditTrial partially : {}, {}", id, indAuditTrialDTO);
        if (indAuditTrialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indAuditTrialDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indAuditTrialRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IndAuditTrialDTO> result = indAuditTrialService.partialUpdate(indAuditTrialDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, indAuditTrialDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ind-audit-trials} : get all the indAuditTrials.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of indAuditTrials in body.
     */
    @GetMapping("/ind-audit-trials")
    public ResponseEntity<List<IndAuditTrialDTO>> getAllIndAuditTrials(IndAuditTrialCriteria criteria) {
        log.debug("REST request to get IndAuditTrials by criteria: {}", criteria);
        List<IndAuditTrialDTO> entityList = indAuditTrialQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ind-audit-trials/count} : count all the indAuditTrials.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ind-audit-trials/count")
    public ResponseEntity<Long> countIndAuditTrials(IndAuditTrialCriteria criteria) {
        log.debug("REST request to count IndAuditTrials by criteria: {}", criteria);
        return ResponseEntity.ok().body(indAuditTrialQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ind-audit-trials/:id} : get the "id" indAuditTrial.
     *
     * @param id the id of the indAuditTrialDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the indAuditTrialDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ind-audit-trials/{id}")
    public ResponseEntity<IndAuditTrialDTO> getIndAuditTrial(@PathVariable Long id) {
        log.debug("REST request to get IndAuditTrial : {}", id);
        Optional<IndAuditTrialDTO> indAuditTrialDTO = indAuditTrialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(indAuditTrialDTO);
    }

    /**
     * {@code DELETE  /ind-audit-trials/:id} : delete the "id" indAuditTrial.
     *
     * @param id the id of the indAuditTrialDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ind-audit-trials/{id}")
    public ResponseEntity<Void> deleteIndAuditTrial(@PathVariable Long id) {
        log.debug("REST request to delete IndAuditTrial : {}", id);
        indAuditTrialService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
