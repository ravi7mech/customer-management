package com.apptium.customer.web.rest;

import com.apptium.customer.repository.CustCharRepository;
import com.apptium.customer.service.CustCharQueryService;
import com.apptium.customer.service.CustCharService;
import com.apptium.customer.service.criteria.CustCharCriteria;
import com.apptium.customer.service.dto.CustCharDTO;
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
 * REST controller for managing {@link com.apptium.customer.domain.CustChar}.
 */
@RestController
@RequestMapping("/api")
public class CustCharResource {

    private final Logger log = LoggerFactory.getLogger(CustCharResource.class);

    private static final String ENTITY_NAME = "customerManagementCustChar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustCharService custCharService;

    private final CustCharRepository custCharRepository;

    private final CustCharQueryService custCharQueryService;

    public CustCharResource(
        CustCharService custCharService,
        CustCharRepository custCharRepository,
        CustCharQueryService custCharQueryService
    ) {
        this.custCharService = custCharService;
        this.custCharRepository = custCharRepository;
        this.custCharQueryService = custCharQueryService;
    }

    /**
     * {@code POST  /cust-chars} : Create a new custChar.
     *
     * @param custCharDTO the custCharDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custCharDTO, or with status {@code 400 (Bad Request)} if the custChar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-chars")
    public ResponseEntity<CustCharDTO> createCustChar(@Valid @RequestBody CustCharDTO custCharDTO) throws URISyntaxException {
        log.debug("REST request to save CustChar : {}", custCharDTO);
        if (custCharDTO.getId() != null) {
            throw new BadRequestAlertException("A new custChar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustCharDTO result = custCharService.save(custCharDTO);
        return ResponseEntity
            .created(new URI("/api/cust-chars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-chars/:id} : Updates an existing custChar.
     *
     * @param id the id of the custCharDTO to save.
     * @param custCharDTO the custCharDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custCharDTO,
     * or with status {@code 400 (Bad Request)} if the custCharDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custCharDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-chars/{id}")
    public ResponseEntity<CustCharDTO> updateCustChar(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustCharDTO custCharDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CustChar : {}, {}", id, custCharDTO);
        if (custCharDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custCharDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustCharDTO result = custCharService.save(custCharDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custCharDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-chars/:id} : Partial updates given fields of an existing custChar, field will ignore if it is null
     *
     * @param id the id of the custCharDTO to save.
     * @param custCharDTO the custCharDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custCharDTO,
     * or with status {@code 400 (Bad Request)} if the custCharDTO is not valid,
     * or with status {@code 404 (Not Found)} if the custCharDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the custCharDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-chars/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustCharDTO> partialUpdateCustChar(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustCharDTO custCharDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustChar partially : {}, {}", id, custCharDTO);
        if (custCharDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custCharDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustCharDTO> result = custCharService.partialUpdate(custCharDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custCharDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-chars} : get all the custChars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custChars in body.
     */
    @GetMapping("/cust-chars")
    public ResponseEntity<List<CustCharDTO>> getAllCustChars(CustCharCriteria criteria) {
        log.debug("REST request to get CustChars by criteria: {}", criteria);
        List<CustCharDTO> entityList = custCharQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /cust-chars/count} : count all the custChars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cust-chars/count")
    public ResponseEntity<Long> countCustChars(CustCharCriteria criteria) {
        log.debug("REST request to count CustChars by criteria: {}", criteria);
        return ResponseEntity.ok().body(custCharQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cust-chars/:id} : get the "id" custChar.
     *
     * @param id the id of the custCharDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custCharDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-chars/{id}")
    public ResponseEntity<CustCharDTO> getCustChar(@PathVariable Long id) {
        log.debug("REST request to get CustChar : {}", id);
        Optional<CustCharDTO> custCharDTO = custCharService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custCharDTO);
    }

    /**
     * {@code DELETE  /cust-chars/:id} : delete the "id" custChar.
     *
     * @param id the id of the custCharDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-chars/{id}")
    public ResponseEntity<Void> deleteCustChar(@PathVariable Long id) {
        log.debug("REST request to delete CustChar : {}", id);
        custCharService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
