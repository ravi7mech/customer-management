package com.apptium.customer.web.rest;

import com.apptium.customer.repository.CustContactCharRepository;
import com.apptium.customer.service.CustContactCharQueryService;
import com.apptium.customer.service.CustContactCharService;
import com.apptium.customer.service.criteria.CustContactCharCriteria;
import com.apptium.customer.service.dto.CustContactCharDTO;
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
 * REST controller for managing {@link com.apptium.customer.domain.CustContactChar}.
 */
@RestController
@RequestMapping("/api")
public class CustContactCharResource {

    private final Logger log = LoggerFactory.getLogger(CustContactCharResource.class);

    private static final String ENTITY_NAME = "customerManagementCustContactChar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustContactCharService custContactCharService;

    private final CustContactCharRepository custContactCharRepository;

    private final CustContactCharQueryService custContactCharQueryService;

    public CustContactCharResource(
        CustContactCharService custContactCharService,
        CustContactCharRepository custContactCharRepository,
        CustContactCharQueryService custContactCharQueryService
    ) {
        this.custContactCharService = custContactCharService;
        this.custContactCharRepository = custContactCharRepository;
        this.custContactCharQueryService = custContactCharQueryService;
    }

    /**
     * {@code POST  /cust-contact-chars} : Create a new custContactChar.
     *
     * @param custContactCharDTO the custContactCharDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custContactCharDTO, or with status {@code 400 (Bad Request)} if the custContactChar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-contact-chars")
    public ResponseEntity<CustContactCharDTO> createCustContactChar(@Valid @RequestBody CustContactCharDTO custContactCharDTO)
        throws URISyntaxException {
        log.debug("REST request to save CustContactChar : {}", custContactCharDTO);
        if (custContactCharDTO.getId() != null) {
            throw new BadRequestAlertException("A new custContactChar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustContactCharDTO result = custContactCharService.save(custContactCharDTO);
        return ResponseEntity
            .created(new URI("/api/cust-contact-chars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-contact-chars/:id} : Updates an existing custContactChar.
     *
     * @param id the id of the custContactCharDTO to save.
     * @param custContactCharDTO the custContactCharDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custContactCharDTO,
     * or with status {@code 400 (Bad Request)} if the custContactCharDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custContactCharDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-contact-chars/{id}")
    public ResponseEntity<CustContactCharDTO> updateCustContactChar(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustContactCharDTO custContactCharDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CustContactChar : {}, {}", id, custContactCharDTO);
        if (custContactCharDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custContactCharDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custContactCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustContactCharDTO result = custContactCharService.save(custContactCharDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custContactCharDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-contact-chars/:id} : Partial updates given fields of an existing custContactChar, field will ignore if it is null
     *
     * @param id the id of the custContactCharDTO to save.
     * @param custContactCharDTO the custContactCharDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custContactCharDTO,
     * or with status {@code 400 (Bad Request)} if the custContactCharDTO is not valid,
     * or with status {@code 404 (Not Found)} if the custContactCharDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the custContactCharDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-contact-chars/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustContactCharDTO> partialUpdateCustContactChar(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustContactCharDTO custContactCharDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustContactChar partially : {}, {}", id, custContactCharDTO);
        if (custContactCharDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custContactCharDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custContactCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustContactCharDTO> result = custContactCharService.partialUpdate(custContactCharDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custContactCharDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-contact-chars} : get all the custContactChars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custContactChars in body.
     */
    @GetMapping("/cust-contact-chars")
    public ResponseEntity<List<CustContactCharDTO>> getAllCustContactChars(CustContactCharCriteria criteria) {
        log.debug("REST request to get CustContactChars by criteria: {}", criteria);
        List<CustContactCharDTO> entityList = custContactCharQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /cust-contact-chars/count} : count all the custContactChars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cust-contact-chars/count")
    public ResponseEntity<Long> countCustContactChars(CustContactCharCriteria criteria) {
        log.debug("REST request to count CustContactChars by criteria: {}", criteria);
        return ResponseEntity.ok().body(custContactCharQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cust-contact-chars/:id} : get the "id" custContactChar.
     *
     * @param id the id of the custContactCharDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custContactCharDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-contact-chars/{id}")
    public ResponseEntity<CustContactCharDTO> getCustContactChar(@PathVariable Long id) {
        log.debug("REST request to get CustContactChar : {}", id);
        Optional<CustContactCharDTO> custContactCharDTO = custContactCharService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custContactCharDTO);
    }

    /**
     * {@code DELETE  /cust-contact-chars/:id} : delete the "id" custContactChar.
     *
     * @param id the id of the custContactCharDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-contact-chars/{id}")
    public ResponseEntity<Void> deleteCustContactChar(@PathVariable Long id) {
        log.debug("REST request to delete CustContactChar : {}", id);
        custContactCharService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
