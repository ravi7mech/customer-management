package com.apptium.customer.web.rest;

import com.apptium.customer.repository.CustRelPartyRepository;
import com.apptium.customer.service.CustRelPartyQueryService;
import com.apptium.customer.service.CustRelPartyService;
import com.apptium.customer.service.criteria.CustRelPartyCriteria;
import com.apptium.customer.service.dto.CustRelPartyDTO;
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
 * REST controller for managing {@link com.apptium.customer.domain.CustRelParty}.
 */
@RestController
@RequestMapping("/api")
public class CustRelPartyResource {

    private final Logger log = LoggerFactory.getLogger(CustRelPartyResource.class);

    private static final String ENTITY_NAME = "customerManagementCustRelParty";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustRelPartyService custRelPartyService;

    private final CustRelPartyRepository custRelPartyRepository;

    private final CustRelPartyQueryService custRelPartyQueryService;

    public CustRelPartyResource(
        CustRelPartyService custRelPartyService,
        CustRelPartyRepository custRelPartyRepository,
        CustRelPartyQueryService custRelPartyQueryService
    ) {
        this.custRelPartyService = custRelPartyService;
        this.custRelPartyRepository = custRelPartyRepository;
        this.custRelPartyQueryService = custRelPartyQueryService;
    }

    /**
     * {@code POST  /cust-rel-parties} : Create a new custRelParty.
     *
     * @param custRelPartyDTO the custRelPartyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custRelPartyDTO, or with status {@code 400 (Bad Request)} if the custRelParty has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-rel-parties")
    public ResponseEntity<CustRelPartyDTO> createCustRelParty(@Valid @RequestBody CustRelPartyDTO custRelPartyDTO)
        throws URISyntaxException {
        log.debug("REST request to save CustRelParty : {}", custRelPartyDTO);
        if (custRelPartyDTO.getId() != null) {
            throw new BadRequestAlertException("A new custRelParty cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustRelPartyDTO result = custRelPartyService.save(custRelPartyDTO);
        return ResponseEntity
            .created(new URI("/api/cust-rel-parties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-rel-parties/:id} : Updates an existing custRelParty.
     *
     * @param id the id of the custRelPartyDTO to save.
     * @param custRelPartyDTO the custRelPartyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custRelPartyDTO,
     * or with status {@code 400 (Bad Request)} if the custRelPartyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custRelPartyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-rel-parties/{id}")
    public ResponseEntity<CustRelPartyDTO> updateCustRelParty(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustRelPartyDTO custRelPartyDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CustRelParty : {}, {}", id, custRelPartyDTO);
        if (custRelPartyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custRelPartyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custRelPartyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustRelPartyDTO result = custRelPartyService.save(custRelPartyDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custRelPartyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-rel-parties/:id} : Partial updates given fields of an existing custRelParty, field will ignore if it is null
     *
     * @param id the id of the custRelPartyDTO to save.
     * @param custRelPartyDTO the custRelPartyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custRelPartyDTO,
     * or with status {@code 400 (Bad Request)} if the custRelPartyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the custRelPartyDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the custRelPartyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-rel-parties/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustRelPartyDTO> partialUpdateCustRelParty(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustRelPartyDTO custRelPartyDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustRelParty partially : {}, {}", id, custRelPartyDTO);
        if (custRelPartyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custRelPartyDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custRelPartyRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustRelPartyDTO> result = custRelPartyService.partialUpdate(custRelPartyDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custRelPartyDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-rel-parties} : get all the custRelParties.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custRelParties in body.
     */
    @GetMapping("/cust-rel-parties")
    public ResponseEntity<List<CustRelPartyDTO>> getAllCustRelParties(CustRelPartyCriteria criteria) {
        log.debug("REST request to get CustRelParties by criteria: {}", criteria);
        List<CustRelPartyDTO> entityList = custRelPartyQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /cust-rel-parties/count} : count all the custRelParties.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cust-rel-parties/count")
    public ResponseEntity<Long> countCustRelParties(CustRelPartyCriteria criteria) {
        log.debug("REST request to count CustRelParties by criteria: {}", criteria);
        return ResponseEntity.ok().body(custRelPartyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cust-rel-parties/:id} : get the "id" custRelParty.
     *
     * @param id the id of the custRelPartyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custRelPartyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-rel-parties/{id}")
    public ResponseEntity<CustRelPartyDTO> getCustRelParty(@PathVariable Long id) {
        log.debug("REST request to get CustRelParty : {}", id);
        Optional<CustRelPartyDTO> custRelPartyDTO = custRelPartyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custRelPartyDTO);
    }

    /**
     * {@code DELETE  /cust-rel-parties/:id} : delete the "id" custRelParty.
     *
     * @param id the id of the custRelPartyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-rel-parties/{id}")
    public ResponseEntity<Void> deleteCustRelParty(@PathVariable Long id) {
        log.debug("REST request to delete CustRelParty : {}", id);
        custRelPartyService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
