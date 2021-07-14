package com.apptium.customer.web.rest;

import com.apptium.customer.repository.CustBillingRefRepository;
import com.apptium.customer.service.CustBillingRefQueryService;
import com.apptium.customer.service.CustBillingRefService;
import com.apptium.customer.service.criteria.CustBillingRefCriteria;
import com.apptium.customer.service.dto.CustBillingRefDTO;
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
 * REST controller for managing {@link com.apptium.customer.domain.CustBillingRef}.
 */
@RestController
@RequestMapping("/api")
public class CustBillingRefResource {

    private final Logger log = LoggerFactory.getLogger(CustBillingRefResource.class);

    private static final String ENTITY_NAME = "customerManagementCustBillingRef";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustBillingRefService custBillingRefService;

    private final CustBillingRefRepository custBillingRefRepository;

    private final CustBillingRefQueryService custBillingRefQueryService;

    public CustBillingRefResource(
        CustBillingRefService custBillingRefService,
        CustBillingRefRepository custBillingRefRepository,
        CustBillingRefQueryService custBillingRefQueryService
    ) {
        this.custBillingRefService = custBillingRefService;
        this.custBillingRefRepository = custBillingRefRepository;
        this.custBillingRefQueryService = custBillingRefQueryService;
    }

    /**
     * {@code POST  /cust-billing-refs} : Create a new custBillingRef.
     *
     * @param custBillingRefDTO the custBillingRefDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custBillingRefDTO, or with status {@code 400 (Bad Request)} if the custBillingRef has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-billing-refs")
    public ResponseEntity<CustBillingRefDTO> createCustBillingRef(@Valid @RequestBody CustBillingRefDTO custBillingRefDTO)
        throws URISyntaxException {
        log.debug("REST request to save CustBillingRef : {}", custBillingRefDTO);
        if (custBillingRefDTO.getId() != null) {
            throw new BadRequestAlertException("A new custBillingRef cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustBillingRefDTO result = custBillingRefService.save(custBillingRefDTO);
        return ResponseEntity
            .created(new URI("/api/cust-billing-refs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-billing-refs/:id} : Updates an existing custBillingRef.
     *
     * @param id the id of the custBillingRefDTO to save.
     * @param custBillingRefDTO the custBillingRefDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custBillingRefDTO,
     * or with status {@code 400 (Bad Request)} if the custBillingRefDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custBillingRefDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-billing-refs/{id}")
    public ResponseEntity<CustBillingRefDTO> updateCustBillingRef(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustBillingRefDTO custBillingRefDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CustBillingRef : {}, {}", id, custBillingRefDTO);
        if (custBillingRefDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custBillingRefDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custBillingRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustBillingRefDTO result = custBillingRefService.save(custBillingRefDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custBillingRefDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-billing-refs/:id} : Partial updates given fields of an existing custBillingRef, field will ignore if it is null
     *
     * @param id the id of the custBillingRefDTO to save.
     * @param custBillingRefDTO the custBillingRefDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custBillingRefDTO,
     * or with status {@code 400 (Bad Request)} if the custBillingRefDTO is not valid,
     * or with status {@code 404 (Not Found)} if the custBillingRefDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the custBillingRefDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-billing-refs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustBillingRefDTO> partialUpdateCustBillingRef(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustBillingRefDTO custBillingRefDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustBillingRef partially : {}, {}", id, custBillingRefDTO);
        if (custBillingRefDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custBillingRefDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custBillingRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustBillingRefDTO> result = custBillingRefService.partialUpdate(custBillingRefDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custBillingRefDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-billing-refs} : get all the custBillingRefs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custBillingRefs in body.
     */
    @GetMapping("/cust-billing-refs")
    public ResponseEntity<List<CustBillingRefDTO>> getAllCustBillingRefs(CustBillingRefCriteria criteria) {
        log.debug("REST request to get CustBillingRefs by criteria: {}", criteria);
        List<CustBillingRefDTO> entityList = custBillingRefQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /cust-billing-refs/count} : count all the custBillingRefs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cust-billing-refs/count")
    public ResponseEntity<Long> countCustBillingRefs(CustBillingRefCriteria criteria) {
        log.debug("REST request to count CustBillingRefs by criteria: {}", criteria);
        return ResponseEntity.ok().body(custBillingRefQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cust-billing-refs/:id} : get the "id" custBillingRef.
     *
     * @param id the id of the custBillingRefDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custBillingRefDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-billing-refs/{id}")
    public ResponseEntity<CustBillingRefDTO> getCustBillingRef(@PathVariable Long id) {
        log.debug("REST request to get CustBillingRef : {}", id);
        Optional<CustBillingRefDTO> custBillingRefDTO = custBillingRefService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custBillingRefDTO);
    }

    /**
     * {@code DELETE  /cust-billing-refs/:id} : delete the "id" custBillingRef.
     *
     * @param id the id of the custBillingRefDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-billing-refs/{id}")
    public ResponseEntity<Void> deleteCustBillingRef(@PathVariable Long id) {
        log.debug("REST request to delete CustBillingRef : {}", id);
        custBillingRefService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
