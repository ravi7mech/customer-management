package com.apptium.customer.web.rest;

import com.apptium.customer.repository.IndActivationRepository;
import com.apptium.customer.service.IndActivationQueryService;
import com.apptium.customer.service.IndActivationService;
import com.apptium.customer.service.criteria.IndActivationCriteria;
import com.apptium.customer.service.dto.IndActivationDTO;
import com.apptium.customer.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
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
 * REST controller for managing {@link com.apptium.customer.domain.IndActivation}.
 */
@RestController
@RequestMapping("/api")
public class IndActivationResource {

    private final Logger log = LoggerFactory.getLogger(IndActivationResource.class);

    private static final String ENTITY_NAME = "customerManagementIndActivation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndActivationService indActivationService;

    private final IndActivationRepository indActivationRepository;

    private final IndActivationQueryService indActivationQueryService;

    public IndActivationResource(
        IndActivationService indActivationService,
        IndActivationRepository indActivationRepository,
        IndActivationQueryService indActivationQueryService
    ) {
        this.indActivationService = indActivationService;
        this.indActivationRepository = indActivationRepository;
        this.indActivationQueryService = indActivationQueryService;
    }

    /**
     * {@code POST  /ind-activations} : Create a new indActivation.
     *
     * @param indActivationDTO the indActivationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new indActivationDTO, or with status {@code 400 (Bad Request)} if the indActivation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ind-activations")
    public ResponseEntity<IndActivationDTO> createIndActivation(@Valid @RequestBody IndActivationDTO indActivationDTO)
        throws URISyntaxException {
        log.debug("REST request to save IndActivation : {}", indActivationDTO);
        if (indActivationDTO.getId() != null) {
            throw new BadRequestAlertException("A new indActivation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IndActivationDTO result = indActivationService.save(indActivationDTO);
        return ResponseEntity
            .created(new URI("/api/ind-activations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ind-activations/:id} : Updates an existing indActivation.
     *
     * @param id the id of the indActivationDTO to save.
     * @param indActivationDTO the indActivationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indActivationDTO,
     * or with status {@code 400 (Bad Request)} if the indActivationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the indActivationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ind-activations/{id}")
    public ResponseEntity<IndActivationDTO> updateIndActivation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IndActivationDTO indActivationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update IndActivation : {}, {}", id, indActivationDTO);
        if (indActivationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indActivationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indActivationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IndActivationDTO result = indActivationService.save(indActivationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, indActivationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ind-activations/:id} : Partial updates given fields of an existing indActivation, field will ignore if it is null
     *
     * @param id the id of the indActivationDTO to save.
     * @param indActivationDTO the indActivationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indActivationDTO,
     * or with status {@code 400 (Bad Request)} if the indActivationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the indActivationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the indActivationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ind-activations/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<IndActivationDTO> partialUpdateIndActivation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IndActivationDTO indActivationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update IndActivation partially : {}, {}", id, indActivationDTO);
        if (indActivationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indActivationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indActivationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IndActivationDTO> result = indActivationService.partialUpdate(indActivationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, indActivationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ind-activations} : get all the indActivations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of indActivations in body.
     */
    @GetMapping("/ind-activations")
    public ResponseEntity<List<IndActivationDTO>> getAllIndActivations(IndActivationCriteria criteria) {
        log.debug("REST request to get IndActivations by criteria: {}", criteria);
        List<IndActivationDTO> entityList = indActivationQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ind-activations/count} : count all the indActivations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ind-activations/count")
    public ResponseEntity<Long> countIndActivations(IndActivationCriteria criteria) {
        log.debug("REST request to count IndActivations by criteria: {}", criteria);
        return ResponseEntity.ok().body(indActivationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ind-activations/:id} : get the "id" indActivation.
     *
     * @param id the id of the indActivationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the indActivationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ind-activations/{id}")
    public ResponseEntity<IndActivationDTO> getIndActivation(@PathVariable Long id) {
        log.debug("REST request to get IndActivation : {}", id);
        Optional<IndActivationDTO> indActivationDTO = indActivationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(indActivationDTO);
    }

    /**
     * {@code DELETE  /ind-activations/:id} : delete the "id" indActivation.
     *
     * @param id the id of the indActivationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ind-activations/{id}")
    public ResponseEntity<Void> deleteIndActivation(@PathVariable Long id) {
        log.debug("REST request to delete IndActivation : {}", id);
        indActivationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
