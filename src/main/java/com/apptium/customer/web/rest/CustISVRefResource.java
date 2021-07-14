package com.apptium.customer.web.rest;

import com.apptium.customer.repository.CustISVRefRepository;
import com.apptium.customer.service.CustISVRefQueryService;
import com.apptium.customer.service.CustISVRefService;
import com.apptium.customer.service.criteria.CustISVRefCriteria;
import com.apptium.customer.service.dto.CustISVRefDTO;
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
 * REST controller for managing {@link com.apptium.customer.domain.CustISVRef}.
 */
@RestController
@RequestMapping("/api")
public class CustISVRefResource {

    private final Logger log = LoggerFactory.getLogger(CustISVRefResource.class);

    private static final String ENTITY_NAME = "customerManagementCustIsvRef";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustISVRefService custISVRefService;

    private final CustISVRefRepository custISVRefRepository;

    private final CustISVRefQueryService custISVRefQueryService;

    public CustISVRefResource(
        CustISVRefService custISVRefService,
        CustISVRefRepository custISVRefRepository,
        CustISVRefQueryService custISVRefQueryService
    ) {
        this.custISVRefService = custISVRefService;
        this.custISVRefRepository = custISVRefRepository;
        this.custISVRefQueryService = custISVRefQueryService;
    }

    /**
     * {@code POST  /cust-isv-refs} : Create a new custISVRef.
     *
     * @param custISVRefDTO the custISVRefDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custISVRefDTO, or with status {@code 400 (Bad Request)} if the custISVRef has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-isv-refs")
    public ResponseEntity<CustISVRefDTO> createCustISVRef(@Valid @RequestBody CustISVRefDTO custISVRefDTO) throws URISyntaxException {
        log.debug("REST request to save CustISVRef : {}", custISVRefDTO);
        if (custISVRefDTO.getId() != null) {
            throw new BadRequestAlertException("A new custISVRef cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustISVRefDTO result = custISVRefService.save(custISVRefDTO);
        return ResponseEntity
            .created(new URI("/api/cust-isv-refs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-isv-refs/:id} : Updates an existing custISVRef.
     *
     * @param id the id of the custISVRefDTO to save.
     * @param custISVRefDTO the custISVRefDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custISVRefDTO,
     * or with status {@code 400 (Bad Request)} if the custISVRefDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custISVRefDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-isv-refs/{id}")
    public ResponseEntity<CustISVRefDTO> updateCustISVRef(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustISVRefDTO custISVRefDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CustISVRef : {}, {}", id, custISVRefDTO);
        if (custISVRefDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custISVRefDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custISVRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustISVRefDTO result = custISVRefService.save(custISVRefDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custISVRefDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-isv-refs/:id} : Partial updates given fields of an existing custISVRef, field will ignore if it is null
     *
     * @param id the id of the custISVRefDTO to save.
     * @param custISVRefDTO the custISVRefDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custISVRefDTO,
     * or with status {@code 400 (Bad Request)} if the custISVRefDTO is not valid,
     * or with status {@code 404 (Not Found)} if the custISVRefDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the custISVRefDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-isv-refs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustISVRefDTO> partialUpdateCustISVRef(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustISVRefDTO custISVRefDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustISVRef partially : {}, {}", id, custISVRefDTO);
        if (custISVRefDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custISVRefDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custISVRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustISVRefDTO> result = custISVRefService.partialUpdate(custISVRefDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custISVRefDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-isv-refs} : get all the custISVRefs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custISVRefs in body.
     */
    @GetMapping("/cust-isv-refs")
    public ResponseEntity<List<CustISVRefDTO>> getAllCustISVRefs(CustISVRefCriteria criteria) {
        log.debug("REST request to get CustISVRefs by criteria: {}", criteria);
        List<CustISVRefDTO> entityList = custISVRefQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /cust-isv-refs/count} : count all the custISVRefs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cust-isv-refs/count")
    public ResponseEntity<Long> countCustISVRefs(CustISVRefCriteria criteria) {
        log.debug("REST request to count CustISVRefs by criteria: {}", criteria);
        return ResponseEntity.ok().body(custISVRefQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cust-isv-refs/:id} : get the "id" custISVRef.
     *
     * @param id the id of the custISVRefDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custISVRefDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-isv-refs/{id}")
    public ResponseEntity<CustISVRefDTO> getCustISVRef(@PathVariable Long id) {
        log.debug("REST request to get CustISVRef : {}", id);
        Optional<CustISVRefDTO> custISVRefDTO = custISVRefService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custISVRefDTO);
    }

    /**
     * {@code DELETE  /cust-isv-refs/:id} : delete the "id" custISVRef.
     *
     * @param id the id of the custISVRefDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-isv-refs/{id}")
    public ResponseEntity<Void> deleteCustISVRef(@PathVariable Long id) {
        log.debug("REST request to delete CustISVRef : {}", id);
        custISVRefService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
