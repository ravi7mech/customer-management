package com.apptium.customer.web.rest;

import com.apptium.customer.repository.CustStatisticsRepository;
import com.apptium.customer.service.CustStatisticsQueryService;
import com.apptium.customer.service.CustStatisticsService;
import com.apptium.customer.service.criteria.CustStatisticsCriteria;
import com.apptium.customer.service.dto.CustStatisticsDTO;
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
 * REST controller for managing {@link com.apptium.customer.domain.CustStatistics}.
 */
@RestController
@RequestMapping("/api")
public class CustStatisticsResource {

    private final Logger log = LoggerFactory.getLogger(CustStatisticsResource.class);

    private static final String ENTITY_NAME = "customerManagementCustStatistics";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustStatisticsService custStatisticsService;

    private final CustStatisticsRepository custStatisticsRepository;

    private final CustStatisticsQueryService custStatisticsQueryService;

    public CustStatisticsResource(
        CustStatisticsService custStatisticsService,
        CustStatisticsRepository custStatisticsRepository,
        CustStatisticsQueryService custStatisticsQueryService
    ) {
        this.custStatisticsService = custStatisticsService;
        this.custStatisticsRepository = custStatisticsRepository;
        this.custStatisticsQueryService = custStatisticsQueryService;
    }

    /**
     * {@code POST  /cust-statistics} : Create a new custStatistics.
     *
     * @param custStatisticsDTO the custStatisticsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custStatisticsDTO, or with status {@code 400 (Bad Request)} if the custStatistics has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-statistics")
    public ResponseEntity<CustStatisticsDTO> createCustStatistics(@Valid @RequestBody CustStatisticsDTO custStatisticsDTO)
        throws URISyntaxException {
        log.debug("REST request to save CustStatistics : {}", custStatisticsDTO);
        if (custStatisticsDTO.getId() != null) {
            throw new BadRequestAlertException("A new custStatistics cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustStatisticsDTO result = custStatisticsService.save(custStatisticsDTO);
        return ResponseEntity
            .created(new URI("/api/cust-statistics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-statistics/:id} : Updates an existing custStatistics.
     *
     * @param id the id of the custStatisticsDTO to save.
     * @param custStatisticsDTO the custStatisticsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custStatisticsDTO,
     * or with status {@code 400 (Bad Request)} if the custStatisticsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custStatisticsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-statistics/{id}")
    public ResponseEntity<CustStatisticsDTO> updateCustStatistics(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustStatisticsDTO custStatisticsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CustStatistics : {}, {}", id, custStatisticsDTO);
        if (custStatisticsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custStatisticsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custStatisticsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustStatisticsDTO result = custStatisticsService.save(custStatisticsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custStatisticsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-statistics/:id} : Partial updates given fields of an existing custStatistics, field will ignore if it is null
     *
     * @param id the id of the custStatisticsDTO to save.
     * @param custStatisticsDTO the custStatisticsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custStatisticsDTO,
     * or with status {@code 400 (Bad Request)} if the custStatisticsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the custStatisticsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the custStatisticsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-statistics/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustStatisticsDTO> partialUpdateCustStatistics(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustStatisticsDTO custStatisticsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustStatistics partially : {}, {}", id, custStatisticsDTO);
        if (custStatisticsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custStatisticsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custStatisticsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustStatisticsDTO> result = custStatisticsService.partialUpdate(custStatisticsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custStatisticsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-statistics} : get all the custStatistics.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custStatistics in body.
     */
    @GetMapping("/cust-statistics")
    public ResponseEntity<List<CustStatisticsDTO>> getAllCustStatistics(CustStatisticsCriteria criteria) {
        log.debug("REST request to get CustStatistics by criteria: {}", criteria);
        List<CustStatisticsDTO> entityList = custStatisticsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /cust-statistics/count} : count all the custStatistics.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cust-statistics/count")
    public ResponseEntity<Long> countCustStatistics(CustStatisticsCriteria criteria) {
        log.debug("REST request to count CustStatistics by criteria: {}", criteria);
        return ResponseEntity.ok().body(custStatisticsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cust-statistics/:id} : get the "id" custStatistics.
     *
     * @param id the id of the custStatisticsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custStatisticsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-statistics/{id}")
    public ResponseEntity<CustStatisticsDTO> getCustStatistics(@PathVariable Long id) {
        log.debug("REST request to get CustStatistics : {}", id);
        Optional<CustStatisticsDTO> custStatisticsDTO = custStatisticsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custStatisticsDTO);
    }

    /**
     * {@code DELETE  /cust-statistics/:id} : delete the "id" custStatistics.
     *
     * @param id the id of the custStatisticsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-statistics/{id}")
    public ResponseEntity<Void> deleteCustStatistics(@PathVariable Long id) {
        log.debug("REST request to delete CustStatistics : {}", id);
        custStatisticsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
