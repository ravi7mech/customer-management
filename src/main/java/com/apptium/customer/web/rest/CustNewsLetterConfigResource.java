package com.apptium.customer.web.rest;

import com.apptium.customer.repository.CustNewsLetterConfigRepository;
import com.apptium.customer.service.CustNewsLetterConfigQueryService;
import com.apptium.customer.service.CustNewsLetterConfigService;
import com.apptium.customer.service.criteria.CustNewsLetterConfigCriteria;
import com.apptium.customer.service.dto.CustNewsLetterConfigDTO;
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
 * REST controller for managing {@link com.apptium.customer.domain.CustNewsLetterConfig}.
 */
@RestController
@RequestMapping("/api")
public class CustNewsLetterConfigResource {

    private final Logger log = LoggerFactory.getLogger(CustNewsLetterConfigResource.class);

    private static final String ENTITY_NAME = "customerManagementCustNewsLetterConfig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustNewsLetterConfigService custNewsLetterConfigService;

    private final CustNewsLetterConfigRepository custNewsLetterConfigRepository;

    private final CustNewsLetterConfigQueryService custNewsLetterConfigQueryService;

    public CustNewsLetterConfigResource(
        CustNewsLetterConfigService custNewsLetterConfigService,
        CustNewsLetterConfigRepository custNewsLetterConfigRepository,
        CustNewsLetterConfigQueryService custNewsLetterConfigQueryService
    ) {
        this.custNewsLetterConfigService = custNewsLetterConfigService;
        this.custNewsLetterConfigRepository = custNewsLetterConfigRepository;
        this.custNewsLetterConfigQueryService = custNewsLetterConfigQueryService;
    }

    /**
     * {@code POST  /cust-news-letter-configs} : Create a new custNewsLetterConfig.
     *
     * @param custNewsLetterConfigDTO the custNewsLetterConfigDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custNewsLetterConfigDTO, or with status {@code 400 (Bad Request)} if the custNewsLetterConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-news-letter-configs")
    public ResponseEntity<CustNewsLetterConfigDTO> createCustNewsLetterConfig(
        @Valid @RequestBody CustNewsLetterConfigDTO custNewsLetterConfigDTO
    ) throws URISyntaxException {
        log.debug("REST request to save CustNewsLetterConfig : {}", custNewsLetterConfigDTO);
        if (custNewsLetterConfigDTO.getId() != null) {
            throw new BadRequestAlertException("A new custNewsLetterConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustNewsLetterConfigDTO result = custNewsLetterConfigService.save(custNewsLetterConfigDTO);
        return ResponseEntity
            .created(new URI("/api/cust-news-letter-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-news-letter-configs/:id} : Updates an existing custNewsLetterConfig.
     *
     * @param id the id of the custNewsLetterConfigDTO to save.
     * @param custNewsLetterConfigDTO the custNewsLetterConfigDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custNewsLetterConfigDTO,
     * or with status {@code 400 (Bad Request)} if the custNewsLetterConfigDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custNewsLetterConfigDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-news-letter-configs/{id}")
    public ResponseEntity<CustNewsLetterConfigDTO> updateCustNewsLetterConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustNewsLetterConfigDTO custNewsLetterConfigDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CustNewsLetterConfig : {}, {}", id, custNewsLetterConfigDTO);
        if (custNewsLetterConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custNewsLetterConfigDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custNewsLetterConfigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustNewsLetterConfigDTO result = custNewsLetterConfigService.save(custNewsLetterConfigDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custNewsLetterConfigDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-news-letter-configs/:id} : Partial updates given fields of an existing custNewsLetterConfig, field will ignore if it is null
     *
     * @param id the id of the custNewsLetterConfigDTO to save.
     * @param custNewsLetterConfigDTO the custNewsLetterConfigDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custNewsLetterConfigDTO,
     * or with status {@code 400 (Bad Request)} if the custNewsLetterConfigDTO is not valid,
     * or with status {@code 404 (Not Found)} if the custNewsLetterConfigDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the custNewsLetterConfigDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-news-letter-configs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustNewsLetterConfigDTO> partialUpdateCustNewsLetterConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustNewsLetterConfigDTO custNewsLetterConfigDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustNewsLetterConfig partially : {}, {}", id, custNewsLetterConfigDTO);
        if (custNewsLetterConfigDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custNewsLetterConfigDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custNewsLetterConfigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustNewsLetterConfigDTO> result = custNewsLetterConfigService.partialUpdate(custNewsLetterConfigDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custNewsLetterConfigDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-news-letter-configs} : get all the custNewsLetterConfigs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custNewsLetterConfigs in body.
     */
    @GetMapping("/cust-news-letter-configs")
    public ResponseEntity<List<CustNewsLetterConfigDTO>> getAllCustNewsLetterConfigs(CustNewsLetterConfigCriteria criteria) {
        log.debug("REST request to get CustNewsLetterConfigs by criteria: {}", criteria);
        List<CustNewsLetterConfigDTO> entityList = custNewsLetterConfigQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /cust-news-letter-configs/count} : count all the custNewsLetterConfigs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cust-news-letter-configs/count")
    public ResponseEntity<Long> countCustNewsLetterConfigs(CustNewsLetterConfigCriteria criteria) {
        log.debug("REST request to count CustNewsLetterConfigs by criteria: {}", criteria);
        return ResponseEntity.ok().body(custNewsLetterConfigQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cust-news-letter-configs/:id} : get the "id" custNewsLetterConfig.
     *
     * @param id the id of the custNewsLetterConfigDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custNewsLetterConfigDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-news-letter-configs/{id}")
    public ResponseEntity<CustNewsLetterConfigDTO> getCustNewsLetterConfig(@PathVariable Long id) {
        log.debug("REST request to get CustNewsLetterConfig : {}", id);
        Optional<CustNewsLetterConfigDTO> custNewsLetterConfigDTO = custNewsLetterConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custNewsLetterConfigDTO);
    }

    /**
     * {@code DELETE  /cust-news-letter-configs/:id} : delete the "id" custNewsLetterConfig.
     *
     * @param id the id of the custNewsLetterConfigDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-news-letter-configs/{id}")
    public ResponseEntity<Void> deleteCustNewsLetterConfig(@PathVariable Long id) {
        log.debug("REST request to delete CustNewsLetterConfig : {}", id);
        custNewsLetterConfigService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
