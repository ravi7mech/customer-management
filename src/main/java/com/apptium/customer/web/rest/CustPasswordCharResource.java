package com.apptium.customer.web.rest;

import com.apptium.customer.repository.CustPasswordCharRepository;
import com.apptium.customer.service.CustPasswordCharQueryService;
import com.apptium.customer.service.CustPasswordCharService;
import com.apptium.customer.service.criteria.CustPasswordCharCriteria;
import com.apptium.customer.service.dto.CustPasswordCharDTO;
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
 * REST controller for managing {@link com.apptium.customer.domain.CustPasswordChar}.
 */
@RestController
@RequestMapping("/api")
public class CustPasswordCharResource {

    private final Logger log = LoggerFactory.getLogger(CustPasswordCharResource.class);

    private static final String ENTITY_NAME = "customerManagementCustPasswordChar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustPasswordCharService custPasswordCharService;

    private final CustPasswordCharRepository custPasswordCharRepository;

    private final CustPasswordCharQueryService custPasswordCharQueryService;

    public CustPasswordCharResource(
        CustPasswordCharService custPasswordCharService,
        CustPasswordCharRepository custPasswordCharRepository,
        CustPasswordCharQueryService custPasswordCharQueryService
    ) {
        this.custPasswordCharService = custPasswordCharService;
        this.custPasswordCharRepository = custPasswordCharRepository;
        this.custPasswordCharQueryService = custPasswordCharQueryService;
    }

    /**
     * {@code POST  /cust-password-chars} : Create a new custPasswordChar.
     *
     * @param custPasswordCharDTO the custPasswordCharDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custPasswordCharDTO, or with status {@code 400 (Bad Request)} if the custPasswordChar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-password-chars")
    public ResponseEntity<CustPasswordCharDTO> createCustPasswordChar(@Valid @RequestBody CustPasswordCharDTO custPasswordCharDTO)
        throws URISyntaxException {
        log.debug("REST request to save CustPasswordChar : {}", custPasswordCharDTO);
        if (custPasswordCharDTO.getId() != null) {
            throw new BadRequestAlertException("A new custPasswordChar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustPasswordCharDTO result = custPasswordCharService.save(custPasswordCharDTO);
        return ResponseEntity
            .created(new URI("/api/cust-password-chars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-password-chars/:id} : Updates an existing custPasswordChar.
     *
     * @param id the id of the custPasswordCharDTO to save.
     * @param custPasswordCharDTO the custPasswordCharDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custPasswordCharDTO,
     * or with status {@code 400 (Bad Request)} if the custPasswordCharDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custPasswordCharDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-password-chars/{id}")
    public ResponseEntity<CustPasswordCharDTO> updateCustPasswordChar(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustPasswordCharDTO custPasswordCharDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CustPasswordChar : {}, {}", id, custPasswordCharDTO);
        if (custPasswordCharDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custPasswordCharDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custPasswordCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustPasswordCharDTO result = custPasswordCharService.save(custPasswordCharDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custPasswordCharDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-password-chars/:id} : Partial updates given fields of an existing custPasswordChar, field will ignore if it is null
     *
     * @param id the id of the custPasswordCharDTO to save.
     * @param custPasswordCharDTO the custPasswordCharDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custPasswordCharDTO,
     * or with status {@code 400 (Bad Request)} if the custPasswordCharDTO is not valid,
     * or with status {@code 404 (Not Found)} if the custPasswordCharDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the custPasswordCharDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-password-chars/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustPasswordCharDTO> partialUpdateCustPasswordChar(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustPasswordCharDTO custPasswordCharDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustPasswordChar partially : {}, {}", id, custPasswordCharDTO);
        if (custPasswordCharDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custPasswordCharDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custPasswordCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustPasswordCharDTO> result = custPasswordCharService.partialUpdate(custPasswordCharDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custPasswordCharDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-password-chars} : get all the custPasswordChars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custPasswordChars in body.
     */
    @GetMapping("/cust-password-chars")
    public ResponseEntity<List<CustPasswordCharDTO>> getAllCustPasswordChars(CustPasswordCharCriteria criteria) {
        log.debug("REST request to get CustPasswordChars by criteria: {}", criteria);
        List<CustPasswordCharDTO> entityList = custPasswordCharQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /cust-password-chars/count} : count all the custPasswordChars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cust-password-chars/count")
    public ResponseEntity<Long> countCustPasswordChars(CustPasswordCharCriteria criteria) {
        log.debug("REST request to count CustPasswordChars by criteria: {}", criteria);
        return ResponseEntity.ok().body(custPasswordCharQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cust-password-chars/:id} : get the "id" custPasswordChar.
     *
     * @param id the id of the custPasswordCharDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custPasswordCharDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-password-chars/{id}")
    public ResponseEntity<CustPasswordCharDTO> getCustPasswordChar(@PathVariable Long id) {
        log.debug("REST request to get CustPasswordChar : {}", id);
        Optional<CustPasswordCharDTO> custPasswordCharDTO = custPasswordCharService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custPasswordCharDTO);
    }

    /**
     * {@code DELETE  /cust-password-chars/:id} : delete the "id" custPasswordChar.
     *
     * @param id the id of the custPasswordCharDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-password-chars/{id}")
    public ResponseEntity<Void> deleteCustPasswordChar(@PathVariable Long id) {
        log.debug("REST request to delete CustPasswordChar : {}", id);
        custPasswordCharService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
