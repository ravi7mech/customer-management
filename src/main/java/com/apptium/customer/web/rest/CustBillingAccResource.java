package com.apptium.customer.web.rest;

import com.apptium.customer.repository.CustBillingAccRepository;
import com.apptium.customer.service.CustBillingAccQueryService;
import com.apptium.customer.service.CustBillingAccService;
import com.apptium.customer.service.criteria.CustBillingAccCriteria;
import com.apptium.customer.service.dto.CustBillingAccDTO;
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
 * REST controller for managing {@link com.apptium.customer.domain.CustBillingAcc}.
 */
@RestController
@RequestMapping("/api")
public class CustBillingAccResource {

    private final Logger log = LoggerFactory.getLogger(CustBillingAccResource.class);

    private static final String ENTITY_NAME = "customerManagementCustBillingAcc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustBillingAccService custBillingAccService;

    private final CustBillingAccRepository custBillingAccRepository;

    private final CustBillingAccQueryService custBillingAccQueryService;

    public CustBillingAccResource(
        CustBillingAccService custBillingAccService,
        CustBillingAccRepository custBillingAccRepository,
        CustBillingAccQueryService custBillingAccQueryService
    ) {
        this.custBillingAccService = custBillingAccService;
        this.custBillingAccRepository = custBillingAccRepository;
        this.custBillingAccQueryService = custBillingAccQueryService;
    }

    /**
     * {@code POST  /cust-billing-accs} : Create a new custBillingAcc.
     *
     * @param custBillingAccDTO the custBillingAccDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custBillingAccDTO, or with status {@code 400 (Bad Request)} if the custBillingAcc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-billing-accs")
    public ResponseEntity<CustBillingAccDTO> createCustBillingAcc(@Valid @RequestBody CustBillingAccDTO custBillingAccDTO)
        throws URISyntaxException {
        log.debug("REST request to save CustBillingAcc : {}", custBillingAccDTO);
        if (custBillingAccDTO.getId() != null) {
            throw new BadRequestAlertException("A new custBillingAcc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustBillingAccDTO result = custBillingAccService.save(custBillingAccDTO);
        return ResponseEntity
            .created(new URI("/api/cust-billing-accs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-billing-accs/:id} : Updates an existing custBillingAcc.
     *
     * @param id the id of the custBillingAccDTO to save.
     * @param custBillingAccDTO the custBillingAccDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custBillingAccDTO,
     * or with status {@code 400 (Bad Request)} if the custBillingAccDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custBillingAccDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-billing-accs/{id}")
    public ResponseEntity<CustBillingAccDTO> updateCustBillingAcc(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustBillingAccDTO custBillingAccDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CustBillingAcc : {}, {}", id, custBillingAccDTO);
        if (custBillingAccDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custBillingAccDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custBillingAccRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustBillingAccDTO result = custBillingAccService.save(custBillingAccDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custBillingAccDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-billing-accs/:id} : Partial updates given fields of an existing custBillingAcc, field will ignore if it is null
     *
     * @param id the id of the custBillingAccDTO to save.
     * @param custBillingAccDTO the custBillingAccDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custBillingAccDTO,
     * or with status {@code 400 (Bad Request)} if the custBillingAccDTO is not valid,
     * or with status {@code 404 (Not Found)} if the custBillingAccDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the custBillingAccDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-billing-accs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustBillingAccDTO> partialUpdateCustBillingAcc(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustBillingAccDTO custBillingAccDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustBillingAcc partially : {}, {}", id, custBillingAccDTO);
        if (custBillingAccDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custBillingAccDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custBillingAccRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustBillingAccDTO> result = custBillingAccService.partialUpdate(custBillingAccDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custBillingAccDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-billing-accs} : get all the custBillingAccs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custBillingAccs in body.
     */
    @GetMapping("/cust-billing-accs")
    public ResponseEntity<List<CustBillingAccDTO>> getAllCustBillingAccs(CustBillingAccCriteria criteria) {
        log.debug("REST request to get CustBillingAccs by criteria: {}", criteria);
        List<CustBillingAccDTO> entityList = custBillingAccQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /cust-billing-accs/count} : count all the custBillingAccs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cust-billing-accs/count")
    public ResponseEntity<Long> countCustBillingAccs(CustBillingAccCriteria criteria) {
        log.debug("REST request to count CustBillingAccs by criteria: {}", criteria);
        return ResponseEntity.ok().body(custBillingAccQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cust-billing-accs/:id} : get the "id" custBillingAcc.
     *
     * @param id the id of the custBillingAccDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custBillingAccDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-billing-accs/{id}")
    public ResponseEntity<CustBillingAccDTO> getCustBillingAcc(@PathVariable Long id) {
        log.debug("REST request to get CustBillingAcc : {}", id);
        Optional<CustBillingAccDTO> custBillingAccDTO = custBillingAccService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custBillingAccDTO);
    }

    /**
     * {@code DELETE  /cust-billing-accs/:id} : delete the "id" custBillingAcc.
     *
     * @param id the id of the custBillingAccDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-billing-accs/{id}")
    public ResponseEntity<Void> deleteCustBillingAcc(@PathVariable Long id) {
        log.debug("REST request to delete CustBillingAcc : {}", id);
        custBillingAccService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
