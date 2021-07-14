package com.apptium.customer.web.rest;

import com.apptium.customer.repository.IndividualRepository;
import com.apptium.customer.service.IndividualQueryService;
import com.apptium.customer.service.IndividualService;
import com.apptium.customer.service.criteria.IndividualCriteria;
import com.apptium.customer.service.dto.IndividualDTO;
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
 * REST controller for managing {@link com.apptium.customer.domain.Individual}.
 */
@RestController
@RequestMapping("/api")
public class IndividualResource {

    private final Logger log = LoggerFactory.getLogger(IndividualResource.class);

    private static final String ENTITY_NAME = "customerManagementIndividual";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndividualService individualService;

    private final IndividualRepository individualRepository;

    private final IndividualQueryService individualQueryService;

    public IndividualResource(
        IndividualService individualService,
        IndividualRepository individualRepository,
        IndividualQueryService individualQueryService
    ) {
        this.individualService = individualService;
        this.individualRepository = individualRepository;
        this.individualQueryService = individualQueryService;
    }

    /**
     * {@code POST  /individuals} : Create a new individual.
     *
     * @param individualDTO the individualDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new individualDTO, or with status {@code 400 (Bad Request)} if the individual has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/individuals")
    public ResponseEntity<IndividualDTO> createIndividual(@Valid @RequestBody IndividualDTO individualDTO) throws URISyntaxException {
        log.debug("REST request to save Individual : {}", individualDTO);
        if (individualDTO.getId() != null) {
            throw new BadRequestAlertException("A new individual cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IndividualDTO result = individualService.save(individualDTO);
        return ResponseEntity
            .created(new URI("/api/individuals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /individuals/:id} : Updates an existing individual.
     *
     * @param id the id of the individualDTO to save.
     * @param individualDTO the individualDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated individualDTO,
     * or with status {@code 400 (Bad Request)} if the individualDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the individualDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/individuals/{id}")
    public ResponseEntity<IndividualDTO> updateIndividual(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IndividualDTO individualDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Individual : {}, {}", id, individualDTO);
        if (individualDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, individualDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!individualRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IndividualDTO result = individualService.save(individualDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, individualDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /individuals/:id} : Partial updates given fields of an existing individual, field will ignore if it is null
     *
     * @param id the id of the individualDTO to save.
     * @param individualDTO the individualDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated individualDTO,
     * or with status {@code 400 (Bad Request)} if the individualDTO is not valid,
     * or with status {@code 404 (Not Found)} if the individualDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the individualDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/individuals/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<IndividualDTO> partialUpdateIndividual(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IndividualDTO individualDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Individual partially : {}, {}", id, individualDTO);
        if (individualDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, individualDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!individualRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IndividualDTO> result = individualService.partialUpdate(individualDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, individualDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /individuals} : get all the individuals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of individuals in body.
     */
    @GetMapping("/individuals")
    public ResponseEntity<List<IndividualDTO>> getAllIndividuals(IndividualCriteria criteria) {
        log.debug("REST request to get Individuals by criteria: {}", criteria);
        List<IndividualDTO> entityList = individualQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /individuals/count} : count all the individuals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/individuals/count")
    public ResponseEntity<Long> countIndividuals(IndividualCriteria criteria) {
        log.debug("REST request to count Individuals by criteria: {}", criteria);
        return ResponseEntity.ok().body(individualQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /individuals/:id} : get the "id" individual.
     *
     * @param id the id of the individualDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the individualDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/individuals/{id}")
    public ResponseEntity<IndividualDTO> getIndividual(@PathVariable Long id) {
        log.debug("REST request to get Individual : {}", id);
        Optional<IndividualDTO> individualDTO = individualService.findOne(id);
        return ResponseUtil.wrapOrNotFound(individualDTO);
    }

    /**
     * {@code DELETE  /individuals/:id} : delete the "id" individual.
     *
     * @param id the id of the individualDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/individuals/{id}")
    public ResponseEntity<Void> deleteIndividual(@PathVariable Long id) {
        log.debug("REST request to delete Individual : {}", id);
        individualService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
