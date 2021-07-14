package com.apptium.customer.web.rest;

import com.apptium.customer.repository.GeographicSiteRefRepository;
import com.apptium.customer.service.GeographicSiteRefQueryService;
import com.apptium.customer.service.GeographicSiteRefService;
import com.apptium.customer.service.criteria.GeographicSiteRefCriteria;
import com.apptium.customer.service.dto.GeographicSiteRefDTO;
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
 * REST controller for managing {@link com.apptium.customer.domain.GeographicSiteRef}.
 */
@RestController
@RequestMapping("/api")
public class GeographicSiteRefResource {

    private final Logger log = LoggerFactory.getLogger(GeographicSiteRefResource.class);

    private static final String ENTITY_NAME = "customerManagementGeographicSiteRef";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GeographicSiteRefService geographicSiteRefService;

    private final GeographicSiteRefRepository geographicSiteRefRepository;

    private final GeographicSiteRefQueryService geographicSiteRefQueryService;

    public GeographicSiteRefResource(
        GeographicSiteRefService geographicSiteRefService,
        GeographicSiteRefRepository geographicSiteRefRepository,
        GeographicSiteRefQueryService geographicSiteRefQueryService
    ) {
        this.geographicSiteRefService = geographicSiteRefService;
        this.geographicSiteRefRepository = geographicSiteRefRepository;
        this.geographicSiteRefQueryService = geographicSiteRefQueryService;
    }

    /**
     * {@code POST  /geographic-site-refs} : Create a new geographicSiteRef.
     *
     * @param geographicSiteRefDTO the geographicSiteRefDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new geographicSiteRefDTO, or with status {@code 400 (Bad Request)} if the geographicSiteRef has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/geographic-site-refs")
    public ResponseEntity<GeographicSiteRefDTO> createGeographicSiteRef(@Valid @RequestBody GeographicSiteRefDTO geographicSiteRefDTO)
        throws URISyntaxException {
        log.debug("REST request to save GeographicSiteRef : {}", geographicSiteRefDTO);
        if (geographicSiteRefDTO.getId() != null) {
            throw new BadRequestAlertException("A new geographicSiteRef cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GeographicSiteRefDTO result = geographicSiteRefService.save(geographicSiteRefDTO);
        return ResponseEntity
            .created(new URI("/api/geographic-site-refs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /geographic-site-refs/:id} : Updates an existing geographicSiteRef.
     *
     * @param id the id of the geographicSiteRefDTO to save.
     * @param geographicSiteRefDTO the geographicSiteRefDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated geographicSiteRefDTO,
     * or with status {@code 400 (Bad Request)} if the geographicSiteRefDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the geographicSiteRefDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/geographic-site-refs/{id}")
    public ResponseEntity<GeographicSiteRefDTO> updateGeographicSiteRef(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GeographicSiteRefDTO geographicSiteRefDTO
    ) throws URISyntaxException {
        log.debug("REST request to update GeographicSiteRef : {}, {}", id, geographicSiteRefDTO);
        if (geographicSiteRefDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, geographicSiteRefDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!geographicSiteRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GeographicSiteRefDTO result = geographicSiteRefService.save(geographicSiteRefDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, geographicSiteRefDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /geographic-site-refs/:id} : Partial updates given fields of an existing geographicSiteRef, field will ignore if it is null
     *
     * @param id the id of the geographicSiteRefDTO to save.
     * @param geographicSiteRefDTO the geographicSiteRefDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated geographicSiteRefDTO,
     * or with status {@code 400 (Bad Request)} if the geographicSiteRefDTO is not valid,
     * or with status {@code 404 (Not Found)} if the geographicSiteRefDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the geographicSiteRefDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/geographic-site-refs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<GeographicSiteRefDTO> partialUpdateGeographicSiteRef(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GeographicSiteRefDTO geographicSiteRefDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update GeographicSiteRef partially : {}, {}", id, geographicSiteRefDTO);
        if (geographicSiteRefDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, geographicSiteRefDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!geographicSiteRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GeographicSiteRefDTO> result = geographicSiteRefService.partialUpdate(geographicSiteRefDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, geographicSiteRefDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /geographic-site-refs} : get all the geographicSiteRefs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of geographicSiteRefs in body.
     */
    @GetMapping("/geographic-site-refs")
    public ResponseEntity<List<GeographicSiteRefDTO>> getAllGeographicSiteRefs(GeographicSiteRefCriteria criteria) {
        log.debug("REST request to get GeographicSiteRefs by criteria: {}", criteria);
        List<GeographicSiteRefDTO> entityList = geographicSiteRefQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /geographic-site-refs/count} : count all the geographicSiteRefs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/geographic-site-refs/count")
    public ResponseEntity<Long> countGeographicSiteRefs(GeographicSiteRefCriteria criteria) {
        log.debug("REST request to count GeographicSiteRefs by criteria: {}", criteria);
        return ResponseEntity.ok().body(geographicSiteRefQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /geographic-site-refs/:id} : get the "id" geographicSiteRef.
     *
     * @param id the id of the geographicSiteRefDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the geographicSiteRefDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/geographic-site-refs/{id}")
    public ResponseEntity<GeographicSiteRefDTO> getGeographicSiteRef(@PathVariable Long id) {
        log.debug("REST request to get GeographicSiteRef : {}", id);
        Optional<GeographicSiteRefDTO> geographicSiteRefDTO = geographicSiteRefService.findOne(id);
        return ResponseUtil.wrapOrNotFound(geographicSiteRefDTO);
    }

    /**
     * {@code DELETE  /geographic-site-refs/:id} : delete the "id" geographicSiteRef.
     *
     * @param id the id of the geographicSiteRefDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/geographic-site-refs/{id}")
    public ResponseEntity<Void> deleteGeographicSiteRef(@PathVariable Long id) {
        log.debug("REST request to delete GeographicSiteRef : {}", id);
        geographicSiteRefService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
