package com.apptium.customer.web.rest;

import com.apptium.customer.repository.IndustryRepository;
import com.apptium.customer.service.IndustryQueryService;
import com.apptium.customer.service.IndustryService;
import com.apptium.customer.service.criteria.IndustryCriteria;
import com.apptium.customer.service.dto.IndustryDTO;
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
 * REST controller for managing {@link com.apptium.customer.domain.Industry}.
 */
@RestController
@RequestMapping("/api")
public class IndustryResource {

    private final Logger log = LoggerFactory.getLogger(IndustryResource.class);

    private static final String ENTITY_NAME = "customerManagementIndustry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndustryService industryService;

    private final IndustryRepository industryRepository;

    private final IndustryQueryService industryQueryService;

    public IndustryResource(
        IndustryService industryService,
        IndustryRepository industryRepository,
        IndustryQueryService industryQueryService
    ) {
        this.industryService = industryService;
        this.industryRepository = industryRepository;
        this.industryQueryService = industryQueryService;
    }

    /**
     * {@code POST  /industries} : Create a new industry.
     *
     * @param industryDTO the industryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new industryDTO, or with status {@code 400 (Bad Request)} if the industry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/industries")
    public ResponseEntity<IndustryDTO> createIndustry(@Valid @RequestBody IndustryDTO industryDTO) throws URISyntaxException {
        log.debug("REST request to save Industry : {}", industryDTO);
        if (industryDTO.getId() != null) {
            throw new BadRequestAlertException("A new industry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IndustryDTO result = industryService.save(industryDTO);
        return ResponseEntity
            .created(new URI("/api/industries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /industries/:id} : Updates an existing industry.
     *
     * @param id the id of the industryDTO to save.
     * @param industryDTO the industryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated industryDTO,
     * or with status {@code 400 (Bad Request)} if the industryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the industryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/industries/{id}")
    public ResponseEntity<IndustryDTO> updateIndustry(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IndustryDTO industryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Industry : {}, {}", id, industryDTO);
        if (industryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, industryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!industryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IndustryDTO result = industryService.save(industryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, industryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /industries/:id} : Partial updates given fields of an existing industry, field will ignore if it is null
     *
     * @param id the id of the industryDTO to save.
     * @param industryDTO the industryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated industryDTO,
     * or with status {@code 400 (Bad Request)} if the industryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the industryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the industryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/industries/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<IndustryDTO> partialUpdateIndustry(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IndustryDTO industryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Industry partially : {}, {}", id, industryDTO);
        if (industryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, industryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!industryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IndustryDTO> result = industryService.partialUpdate(industryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, industryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /industries} : get all the industries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of industries in body.
     */
    @GetMapping("/industries")
    public ResponseEntity<List<IndustryDTO>> getAllIndustries(IndustryCriteria criteria) {
        log.debug("REST request to get Industries by criteria: {}", criteria);
        List<IndustryDTO> entityList = industryQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /industries/count} : count all the industries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/industries/count")
    public ResponseEntity<Long> countIndustries(IndustryCriteria criteria) {
        log.debug("REST request to count Industries by criteria: {}", criteria);
        return ResponseEntity.ok().body(industryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /industries/:id} : get the "id" industry.
     *
     * @param id the id of the industryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the industryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/industries/{id}")
    public ResponseEntity<IndustryDTO> getIndustry(@PathVariable Long id) {
        log.debug("REST request to get Industry : {}", id);
        Optional<IndustryDTO> industryDTO = industryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(industryDTO);
    }

    /**
     * {@code DELETE  /industries/:id} : delete the "id" industry.
     *
     * @param id the id of the industryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/industries/{id}")
    public ResponseEntity<Void> deleteIndustry(@PathVariable Long id) {
        log.debug("REST request to delete Industry : {}", id);
        industryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
