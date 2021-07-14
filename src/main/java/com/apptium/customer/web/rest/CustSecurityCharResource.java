package com.apptium.customer.web.rest;

import com.apptium.customer.repository.CustSecurityCharRepository;
import com.apptium.customer.service.CustSecurityCharQueryService;
import com.apptium.customer.service.CustSecurityCharService;
import com.apptium.customer.service.criteria.CustSecurityCharCriteria;
import com.apptium.customer.service.dto.CustSecurityCharDTO;
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
 * REST controller for managing {@link com.apptium.customer.domain.CustSecurityChar}.
 */
@RestController
@RequestMapping("/api")
public class CustSecurityCharResource {

    private final Logger log = LoggerFactory.getLogger(CustSecurityCharResource.class);

    private static final String ENTITY_NAME = "customerManagementCustSecurityChar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustSecurityCharService custSecurityCharService;

    private final CustSecurityCharRepository custSecurityCharRepository;

    private final CustSecurityCharQueryService custSecurityCharQueryService;

    public CustSecurityCharResource(
        CustSecurityCharService custSecurityCharService,
        CustSecurityCharRepository custSecurityCharRepository,
        CustSecurityCharQueryService custSecurityCharQueryService
    ) {
        this.custSecurityCharService = custSecurityCharService;
        this.custSecurityCharRepository = custSecurityCharRepository;
        this.custSecurityCharQueryService = custSecurityCharQueryService;
    }

    /**
     * {@code POST  /cust-security-chars} : Create a new custSecurityChar.
     *
     * @param custSecurityCharDTO the custSecurityCharDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custSecurityCharDTO, or with status {@code 400 (Bad Request)} if the custSecurityChar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-security-chars")
    public ResponseEntity<CustSecurityCharDTO> createCustSecurityChar(@Valid @RequestBody CustSecurityCharDTO custSecurityCharDTO)
        throws URISyntaxException {
        log.debug("REST request to save CustSecurityChar : {}", custSecurityCharDTO);
        if (custSecurityCharDTO.getId() != null) {
            throw new BadRequestAlertException("A new custSecurityChar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustSecurityCharDTO result = custSecurityCharService.save(custSecurityCharDTO);
        return ResponseEntity
            .created(new URI("/api/cust-security-chars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-security-chars/:id} : Updates an existing custSecurityChar.
     *
     * @param id the id of the custSecurityCharDTO to save.
     * @param custSecurityCharDTO the custSecurityCharDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custSecurityCharDTO,
     * or with status {@code 400 (Bad Request)} if the custSecurityCharDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custSecurityCharDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-security-chars/{id}")
    public ResponseEntity<CustSecurityCharDTO> updateCustSecurityChar(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustSecurityCharDTO custSecurityCharDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CustSecurityChar : {}, {}", id, custSecurityCharDTO);
        if (custSecurityCharDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custSecurityCharDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custSecurityCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustSecurityCharDTO result = custSecurityCharService.save(custSecurityCharDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custSecurityCharDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-security-chars/:id} : Partial updates given fields of an existing custSecurityChar, field will ignore if it is null
     *
     * @param id the id of the custSecurityCharDTO to save.
     * @param custSecurityCharDTO the custSecurityCharDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custSecurityCharDTO,
     * or with status {@code 400 (Bad Request)} if the custSecurityCharDTO is not valid,
     * or with status {@code 404 (Not Found)} if the custSecurityCharDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the custSecurityCharDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-security-chars/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustSecurityCharDTO> partialUpdateCustSecurityChar(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustSecurityCharDTO custSecurityCharDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustSecurityChar partially : {}, {}", id, custSecurityCharDTO);
        if (custSecurityCharDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custSecurityCharDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custSecurityCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustSecurityCharDTO> result = custSecurityCharService.partialUpdate(custSecurityCharDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custSecurityCharDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-security-chars} : get all the custSecurityChars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custSecurityChars in body.
     */
    @GetMapping("/cust-security-chars")
    public ResponseEntity<List<CustSecurityCharDTO>> getAllCustSecurityChars(CustSecurityCharCriteria criteria) {
        log.debug("REST request to get CustSecurityChars by criteria: {}", criteria);
        List<CustSecurityCharDTO> entityList = custSecurityCharQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /cust-security-chars/count} : count all the custSecurityChars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cust-security-chars/count")
    public ResponseEntity<Long> countCustSecurityChars(CustSecurityCharCriteria criteria) {
        log.debug("REST request to count CustSecurityChars by criteria: {}", criteria);
        return ResponseEntity.ok().body(custSecurityCharQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cust-security-chars/:id} : get the "id" custSecurityChar.
     *
     * @param id the id of the custSecurityCharDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custSecurityCharDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-security-chars/{id}")
    public ResponseEntity<CustSecurityCharDTO> getCustSecurityChar(@PathVariable Long id) {
        log.debug("REST request to get CustSecurityChar : {}", id);
        Optional<CustSecurityCharDTO> custSecurityCharDTO = custSecurityCharService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custSecurityCharDTO);
    }

    /**
     * {@code DELETE  /cust-security-chars/:id} : delete the "id" custSecurityChar.
     *
     * @param id the id of the custSecurityCharDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-security-chars/{id}")
    public ResponseEntity<Void> deleteCustSecurityChar(@PathVariable Long id) {
        log.debug("REST request to delete CustSecurityChar : {}", id);
        custSecurityCharService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
