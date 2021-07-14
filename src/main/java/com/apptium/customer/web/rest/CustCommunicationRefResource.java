package com.apptium.customer.web.rest;

import com.apptium.customer.repository.CustCommunicationRefRepository;
import com.apptium.customer.service.CustCommunicationRefQueryService;
import com.apptium.customer.service.CustCommunicationRefService;
import com.apptium.customer.service.criteria.CustCommunicationRefCriteria;
import com.apptium.customer.service.dto.CustCommunicationRefDTO;
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
 * REST controller for managing {@link com.apptium.customer.domain.CustCommunicationRef}.
 */
@RestController
@RequestMapping("/api")
public class CustCommunicationRefResource {

    private final Logger log = LoggerFactory.getLogger(CustCommunicationRefResource.class);

    private static final String ENTITY_NAME = "customerManagementCustCommunicationRef";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustCommunicationRefService custCommunicationRefService;

    private final CustCommunicationRefRepository custCommunicationRefRepository;

    private final CustCommunicationRefQueryService custCommunicationRefQueryService;

    public CustCommunicationRefResource(
        CustCommunicationRefService custCommunicationRefService,
        CustCommunicationRefRepository custCommunicationRefRepository,
        CustCommunicationRefQueryService custCommunicationRefQueryService
    ) {
        this.custCommunicationRefService = custCommunicationRefService;
        this.custCommunicationRefRepository = custCommunicationRefRepository;
        this.custCommunicationRefQueryService = custCommunicationRefQueryService;
    }

    /**
     * {@code POST  /cust-communication-refs} : Create a new custCommunicationRef.
     *
     * @param custCommunicationRefDTO the custCommunicationRefDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custCommunicationRefDTO, or with status {@code 400 (Bad Request)} if the custCommunicationRef has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-communication-refs")
    public ResponseEntity<CustCommunicationRefDTO> createCustCommunicationRef(
        @Valid @RequestBody CustCommunicationRefDTO custCommunicationRefDTO
    ) throws URISyntaxException {
        log.debug("REST request to save CustCommunicationRef : {}", custCommunicationRefDTO);
        if (custCommunicationRefDTO.getId() != null) {
            throw new BadRequestAlertException("A new custCommunicationRef cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustCommunicationRefDTO result = custCommunicationRefService.save(custCommunicationRefDTO);
        return ResponseEntity
            .created(new URI("/api/cust-communication-refs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-communication-refs/:id} : Updates an existing custCommunicationRef.
     *
     * @param id the id of the custCommunicationRefDTO to save.
     * @param custCommunicationRefDTO the custCommunicationRefDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custCommunicationRefDTO,
     * or with status {@code 400 (Bad Request)} if the custCommunicationRefDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custCommunicationRefDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-communication-refs/{id}")
    public ResponseEntity<CustCommunicationRefDTO> updateCustCommunicationRef(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustCommunicationRefDTO custCommunicationRefDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CustCommunicationRef : {}, {}", id, custCommunicationRefDTO);
        if (custCommunicationRefDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custCommunicationRefDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custCommunicationRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustCommunicationRefDTO result = custCommunicationRefService.save(custCommunicationRefDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custCommunicationRefDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-communication-refs/:id} : Partial updates given fields of an existing custCommunicationRef, field will ignore if it is null
     *
     * @param id the id of the custCommunicationRefDTO to save.
     * @param custCommunicationRefDTO the custCommunicationRefDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custCommunicationRefDTO,
     * or with status {@code 400 (Bad Request)} if the custCommunicationRefDTO is not valid,
     * or with status {@code 404 (Not Found)} if the custCommunicationRefDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the custCommunicationRefDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-communication-refs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustCommunicationRefDTO> partialUpdateCustCommunicationRef(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustCommunicationRefDTO custCommunicationRefDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustCommunicationRef partially : {}, {}", id, custCommunicationRefDTO);
        if (custCommunicationRefDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custCommunicationRefDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custCommunicationRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustCommunicationRefDTO> result = custCommunicationRefService.partialUpdate(custCommunicationRefDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custCommunicationRefDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-communication-refs} : get all the custCommunicationRefs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custCommunicationRefs in body.
     */
    @GetMapping("/cust-communication-refs")
    public ResponseEntity<List<CustCommunicationRefDTO>> getAllCustCommunicationRefs(CustCommunicationRefCriteria criteria) {
        log.debug("REST request to get CustCommunicationRefs by criteria: {}", criteria);
        List<CustCommunicationRefDTO> entityList = custCommunicationRefQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /cust-communication-refs/count} : count all the custCommunicationRefs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cust-communication-refs/count")
    public ResponseEntity<Long> countCustCommunicationRefs(CustCommunicationRefCriteria criteria) {
        log.debug("REST request to count CustCommunicationRefs by criteria: {}", criteria);
        return ResponseEntity.ok().body(custCommunicationRefQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cust-communication-refs/:id} : get the "id" custCommunicationRef.
     *
     * @param id the id of the custCommunicationRefDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custCommunicationRefDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-communication-refs/{id}")
    public ResponseEntity<CustCommunicationRefDTO> getCustCommunicationRef(@PathVariable Long id) {
        log.debug("REST request to get CustCommunicationRef : {}", id);
        Optional<CustCommunicationRefDTO> custCommunicationRefDTO = custCommunicationRefService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custCommunicationRefDTO);
    }

    /**
     * {@code DELETE  /cust-communication-refs/:id} : delete the "id" custCommunicationRef.
     *
     * @param id the id of the custCommunicationRefDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-communication-refs/{id}")
    public ResponseEntity<Void> deleteCustCommunicationRef(@PathVariable Long id) {
        log.debug("REST request to delete CustCommunicationRef : {}", id);
        custCommunicationRefService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
