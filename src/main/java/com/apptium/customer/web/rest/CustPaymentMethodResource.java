package com.apptium.customer.web.rest;

import com.apptium.customer.repository.CustPaymentMethodRepository;
import com.apptium.customer.service.CustPaymentMethodQueryService;
import com.apptium.customer.service.CustPaymentMethodService;
import com.apptium.customer.service.criteria.CustPaymentMethodCriteria;
import com.apptium.customer.service.dto.CustPaymentMethodDTO;
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
 * REST controller for managing {@link com.apptium.customer.domain.CustPaymentMethod}.
 */
@RestController
@RequestMapping("/api")
public class CustPaymentMethodResource {

    private final Logger log = LoggerFactory.getLogger(CustPaymentMethodResource.class);

    private static final String ENTITY_NAME = "customerManagementCustPaymentMethod";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustPaymentMethodService custPaymentMethodService;

    private final CustPaymentMethodRepository custPaymentMethodRepository;

    private final CustPaymentMethodQueryService custPaymentMethodQueryService;

    public CustPaymentMethodResource(
        CustPaymentMethodService custPaymentMethodService,
        CustPaymentMethodRepository custPaymentMethodRepository,
        CustPaymentMethodQueryService custPaymentMethodQueryService
    ) {
        this.custPaymentMethodService = custPaymentMethodService;
        this.custPaymentMethodRepository = custPaymentMethodRepository;
        this.custPaymentMethodQueryService = custPaymentMethodQueryService;
    }

    /**
     * {@code POST  /cust-payment-methods} : Create a new custPaymentMethod.
     *
     * @param custPaymentMethodDTO the custPaymentMethodDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custPaymentMethodDTO, or with status {@code 400 (Bad Request)} if the custPaymentMethod has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-payment-methods")
    public ResponseEntity<CustPaymentMethodDTO> createCustPaymentMethod(@Valid @RequestBody CustPaymentMethodDTO custPaymentMethodDTO)
        throws URISyntaxException {
        log.debug("REST request to save CustPaymentMethod : {}", custPaymentMethodDTO);
        if (custPaymentMethodDTO.getId() != null) {
            throw new BadRequestAlertException("A new custPaymentMethod cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustPaymentMethodDTO result = custPaymentMethodService.save(custPaymentMethodDTO);
        return ResponseEntity
            .created(new URI("/api/cust-payment-methods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-payment-methods/:id} : Updates an existing custPaymentMethod.
     *
     * @param id the id of the custPaymentMethodDTO to save.
     * @param custPaymentMethodDTO the custPaymentMethodDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custPaymentMethodDTO,
     * or with status {@code 400 (Bad Request)} if the custPaymentMethodDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custPaymentMethodDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-payment-methods/{id}")
    public ResponseEntity<CustPaymentMethodDTO> updateCustPaymentMethod(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustPaymentMethodDTO custPaymentMethodDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CustPaymentMethod : {}, {}", id, custPaymentMethodDTO);
        if (custPaymentMethodDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custPaymentMethodDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custPaymentMethodRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustPaymentMethodDTO result = custPaymentMethodService.save(custPaymentMethodDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custPaymentMethodDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-payment-methods/:id} : Partial updates given fields of an existing custPaymentMethod, field will ignore if it is null
     *
     * @param id the id of the custPaymentMethodDTO to save.
     * @param custPaymentMethodDTO the custPaymentMethodDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custPaymentMethodDTO,
     * or with status {@code 400 (Bad Request)} if the custPaymentMethodDTO is not valid,
     * or with status {@code 404 (Not Found)} if the custPaymentMethodDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the custPaymentMethodDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-payment-methods/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustPaymentMethodDTO> partialUpdateCustPaymentMethod(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustPaymentMethodDTO custPaymentMethodDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustPaymentMethod partially : {}, {}", id, custPaymentMethodDTO);
        if (custPaymentMethodDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custPaymentMethodDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custPaymentMethodRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustPaymentMethodDTO> result = custPaymentMethodService.partialUpdate(custPaymentMethodDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custPaymentMethodDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-payment-methods} : get all the custPaymentMethods.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custPaymentMethods in body.
     */
    @GetMapping("/cust-payment-methods")
    public ResponseEntity<List<CustPaymentMethodDTO>> getAllCustPaymentMethods(CustPaymentMethodCriteria criteria) {
        log.debug("REST request to get CustPaymentMethods by criteria: {}", criteria);
        List<CustPaymentMethodDTO> entityList = custPaymentMethodQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /cust-payment-methods/count} : count all the custPaymentMethods.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cust-payment-methods/count")
    public ResponseEntity<Long> countCustPaymentMethods(CustPaymentMethodCriteria criteria) {
        log.debug("REST request to count CustPaymentMethods by criteria: {}", criteria);
        return ResponseEntity.ok().body(custPaymentMethodQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cust-payment-methods/:id} : get the "id" custPaymentMethod.
     *
     * @param id the id of the custPaymentMethodDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custPaymentMethodDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-payment-methods/{id}")
    public ResponseEntity<CustPaymentMethodDTO> getCustPaymentMethod(@PathVariable Long id) {
        log.debug("REST request to get CustPaymentMethod : {}", id);
        Optional<CustPaymentMethodDTO> custPaymentMethodDTO = custPaymentMethodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custPaymentMethodDTO);
    }

    /**
     * {@code DELETE  /cust-payment-methods/:id} : delete the "id" custPaymentMethod.
     *
     * @param id the id of the custPaymentMethodDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-payment-methods/{id}")
    public ResponseEntity<Void> deleteCustPaymentMethod(@PathVariable Long id) {
        log.debug("REST request to delete CustPaymentMethod : {}", id);
        custPaymentMethodService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
