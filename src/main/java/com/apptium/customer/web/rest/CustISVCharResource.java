package com.apptium.customer.web.rest;

import com.apptium.customer.repository.CustISVCharRepository;
import com.apptium.customer.service.CustISVCharQueryService;
import com.apptium.customer.service.CustISVCharService;
import com.apptium.customer.service.criteria.CustISVCharCriteria;
import com.apptium.customer.service.dto.CustISVCharDTO;
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
 * REST controller for managing {@link com.apptium.customer.domain.CustISVChar}.
 */
@RestController
@RequestMapping("/api")
public class CustISVCharResource {

    private final Logger log = LoggerFactory.getLogger(CustISVCharResource.class);

    private static final String ENTITY_NAME = "customerManagementCustIsvChar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustISVCharService custISVCharService;

    private final CustISVCharRepository custISVCharRepository;

    private final CustISVCharQueryService custISVCharQueryService;

    public CustISVCharResource(
        CustISVCharService custISVCharService,
        CustISVCharRepository custISVCharRepository,
        CustISVCharQueryService custISVCharQueryService
    ) {
        this.custISVCharService = custISVCharService;
        this.custISVCharRepository = custISVCharRepository;
        this.custISVCharQueryService = custISVCharQueryService;
    }

    /**
     * {@code POST  /cust-isv-chars} : Create a new custISVChar.
     *
     * @param custISVCharDTO the custISVCharDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custISVCharDTO, or with status {@code 400 (Bad Request)} if the custISVChar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-isv-chars")
    public ResponseEntity<CustISVCharDTO> createCustISVChar(@Valid @RequestBody CustISVCharDTO custISVCharDTO) throws URISyntaxException {
        log.debug("REST request to save CustISVChar : {}", custISVCharDTO);
        if (custISVCharDTO.getId() != null) {
            throw new BadRequestAlertException("A new custISVChar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustISVCharDTO result = custISVCharService.save(custISVCharDTO);
        return ResponseEntity
            .created(new URI("/api/cust-isv-chars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-isv-chars/:id} : Updates an existing custISVChar.
     *
     * @param id the id of the custISVCharDTO to save.
     * @param custISVCharDTO the custISVCharDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custISVCharDTO,
     * or with status {@code 400 (Bad Request)} if the custISVCharDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custISVCharDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-isv-chars/{id}")
    public ResponseEntity<CustISVCharDTO> updateCustISVChar(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustISVCharDTO custISVCharDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CustISVChar : {}, {}", id, custISVCharDTO);
        if (custISVCharDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custISVCharDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custISVCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustISVCharDTO result = custISVCharService.save(custISVCharDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custISVCharDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-isv-chars/:id} : Partial updates given fields of an existing custISVChar, field will ignore if it is null
     *
     * @param id the id of the custISVCharDTO to save.
     * @param custISVCharDTO the custISVCharDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custISVCharDTO,
     * or with status {@code 400 (Bad Request)} if the custISVCharDTO is not valid,
     * or with status {@code 404 (Not Found)} if the custISVCharDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the custISVCharDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-isv-chars/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustISVCharDTO> partialUpdateCustISVChar(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustISVCharDTO custISVCharDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustISVChar partially : {}, {}", id, custISVCharDTO);
        if (custISVCharDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custISVCharDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custISVCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustISVCharDTO> result = custISVCharService.partialUpdate(custISVCharDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custISVCharDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-isv-chars} : get all the custISVChars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custISVChars in body.
     */
    @GetMapping("/cust-isv-chars")
    public ResponseEntity<List<CustISVCharDTO>> getAllCustISVChars(CustISVCharCriteria criteria) {
        log.debug("REST request to get CustISVChars by criteria: {}", criteria);
        List<CustISVCharDTO> entityList = custISVCharQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /cust-isv-chars/count} : count all the custISVChars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cust-isv-chars/count")
    public ResponseEntity<Long> countCustISVChars(CustISVCharCriteria criteria) {
        log.debug("REST request to count CustISVChars by criteria: {}", criteria);
        return ResponseEntity.ok().body(custISVCharQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cust-isv-chars/:id} : get the "id" custISVChar.
     *
     * @param id the id of the custISVCharDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custISVCharDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-isv-chars/{id}")
    public ResponseEntity<CustISVCharDTO> getCustISVChar(@PathVariable Long id) {
        log.debug("REST request to get CustISVChar : {}", id);
        Optional<CustISVCharDTO> custISVCharDTO = custISVCharService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custISVCharDTO);
    }

    /**
     * {@code DELETE  /cust-isv-chars/:id} : delete the "id" custISVChar.
     *
     * @param id the id of the custISVCharDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-isv-chars/{id}")
    public ResponseEntity<Void> deleteCustISVChar(@PathVariable Long id) {
        log.debug("REST request to delete CustISVChar : {}", id);
        custISVCharService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
