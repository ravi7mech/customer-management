package com.apptium.customer.web.rest;

import com.apptium.customer.repository.CustCreditProfileRepository;
import com.apptium.customer.service.CustCreditProfileQueryService;
import com.apptium.customer.service.CustCreditProfileService;
import com.apptium.customer.service.criteria.CustCreditProfileCriteria;
import com.apptium.customer.service.dto.CustCreditProfileDTO;
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
 * REST controller for managing {@link com.apptium.customer.domain.CustCreditProfile}.
 */
@RestController
@RequestMapping("/api")
public class CustCreditProfileResource {

    private final Logger log = LoggerFactory.getLogger(CustCreditProfileResource.class);

    private static final String ENTITY_NAME = "customerManagementCustCreditProfile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustCreditProfileService custCreditProfileService;

    private final CustCreditProfileRepository custCreditProfileRepository;

    private final CustCreditProfileQueryService custCreditProfileQueryService;

    public CustCreditProfileResource(
        CustCreditProfileService custCreditProfileService,
        CustCreditProfileRepository custCreditProfileRepository,
        CustCreditProfileQueryService custCreditProfileQueryService
    ) {
        this.custCreditProfileService = custCreditProfileService;
        this.custCreditProfileRepository = custCreditProfileRepository;
        this.custCreditProfileQueryService = custCreditProfileQueryService;
    }

    /**
     * {@code POST  /cust-credit-profiles} : Create a new custCreditProfile.
     *
     * @param custCreditProfileDTO the custCreditProfileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custCreditProfileDTO, or with status {@code 400 (Bad Request)} if the custCreditProfile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-credit-profiles")
    public ResponseEntity<CustCreditProfileDTO> createCustCreditProfile(@Valid @RequestBody CustCreditProfileDTO custCreditProfileDTO)
        throws URISyntaxException {
        log.debug("REST request to save CustCreditProfile : {}", custCreditProfileDTO);
        if (custCreditProfileDTO.getId() != null) {
            throw new BadRequestAlertException("A new custCreditProfile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustCreditProfileDTO result = custCreditProfileService.save(custCreditProfileDTO);
        return ResponseEntity
            .created(new URI("/api/cust-credit-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-credit-profiles/:id} : Updates an existing custCreditProfile.
     *
     * @param id the id of the custCreditProfileDTO to save.
     * @param custCreditProfileDTO the custCreditProfileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custCreditProfileDTO,
     * or with status {@code 400 (Bad Request)} if the custCreditProfileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custCreditProfileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-credit-profiles/{id}")
    public ResponseEntity<CustCreditProfileDTO> updateCustCreditProfile(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustCreditProfileDTO custCreditProfileDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CustCreditProfile : {}, {}", id, custCreditProfileDTO);
        if (custCreditProfileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custCreditProfileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custCreditProfileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustCreditProfileDTO result = custCreditProfileService.save(custCreditProfileDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custCreditProfileDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-credit-profiles/:id} : Partial updates given fields of an existing custCreditProfile, field will ignore if it is null
     *
     * @param id the id of the custCreditProfileDTO to save.
     * @param custCreditProfileDTO the custCreditProfileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custCreditProfileDTO,
     * or with status {@code 400 (Bad Request)} if the custCreditProfileDTO is not valid,
     * or with status {@code 404 (Not Found)} if the custCreditProfileDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the custCreditProfileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-credit-profiles/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustCreditProfileDTO> partialUpdateCustCreditProfile(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustCreditProfileDTO custCreditProfileDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustCreditProfile partially : {}, {}", id, custCreditProfileDTO);
        if (custCreditProfileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custCreditProfileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custCreditProfileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustCreditProfileDTO> result = custCreditProfileService.partialUpdate(custCreditProfileDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custCreditProfileDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-credit-profiles} : get all the custCreditProfiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custCreditProfiles in body.
     */
    @GetMapping("/cust-credit-profiles")
    public ResponseEntity<List<CustCreditProfileDTO>> getAllCustCreditProfiles(CustCreditProfileCriteria criteria) {
        log.debug("REST request to get CustCreditProfiles by criteria: {}", criteria);
        List<CustCreditProfileDTO> entityList = custCreditProfileQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /cust-credit-profiles/count} : count all the custCreditProfiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cust-credit-profiles/count")
    public ResponseEntity<Long> countCustCreditProfiles(CustCreditProfileCriteria criteria) {
        log.debug("REST request to count CustCreditProfiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(custCreditProfileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cust-credit-profiles/:id} : get the "id" custCreditProfile.
     *
     * @param id the id of the custCreditProfileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custCreditProfileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-credit-profiles/{id}")
    public ResponseEntity<CustCreditProfileDTO> getCustCreditProfile(@PathVariable Long id) {
        log.debug("REST request to get CustCreditProfile : {}", id);
        Optional<CustCreditProfileDTO> custCreditProfileDTO = custCreditProfileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custCreditProfileDTO);
    }

    /**
     * {@code DELETE  /cust-credit-profiles/:id} : delete the "id" custCreditProfile.
     *
     * @param id the id of the custCreditProfileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-credit-profiles/{id}")
    public ResponseEntity<Void> deleteCustCreditProfile(@PathVariable Long id) {
        log.debug("REST request to delete CustCreditProfile : {}", id);
        custCreditProfileService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
