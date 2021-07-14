package com.apptium.customer.web.rest;

import com.apptium.customer.repository.IndNewsLetterConfRepository;
import com.apptium.customer.service.IndNewsLetterConfQueryService;
import com.apptium.customer.service.IndNewsLetterConfService;
import com.apptium.customer.service.criteria.IndNewsLetterConfCriteria;
import com.apptium.customer.service.dto.IndNewsLetterConfDTO;
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
 * REST controller for managing {@link com.apptium.customer.domain.IndNewsLetterConf}.
 */
@RestController
@RequestMapping("/api")
public class IndNewsLetterConfResource {

    private final Logger log = LoggerFactory.getLogger(IndNewsLetterConfResource.class);

    private static final String ENTITY_NAME = "customerManagementIndNewsLetterConf";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndNewsLetterConfService indNewsLetterConfService;

    private final IndNewsLetterConfRepository indNewsLetterConfRepository;

    private final IndNewsLetterConfQueryService indNewsLetterConfQueryService;

    public IndNewsLetterConfResource(
        IndNewsLetterConfService indNewsLetterConfService,
        IndNewsLetterConfRepository indNewsLetterConfRepository,
        IndNewsLetterConfQueryService indNewsLetterConfQueryService
    ) {
        this.indNewsLetterConfService = indNewsLetterConfService;
        this.indNewsLetterConfRepository = indNewsLetterConfRepository;
        this.indNewsLetterConfQueryService = indNewsLetterConfQueryService;
    }

    /**
     * {@code POST  /ind-news-letter-confs} : Create a new indNewsLetterConf.
     *
     * @param indNewsLetterConfDTO the indNewsLetterConfDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new indNewsLetterConfDTO, or with status {@code 400 (Bad Request)} if the indNewsLetterConf has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ind-news-letter-confs")
    public ResponseEntity<IndNewsLetterConfDTO> createIndNewsLetterConf(@Valid @RequestBody IndNewsLetterConfDTO indNewsLetterConfDTO)
        throws URISyntaxException {
        log.debug("REST request to save IndNewsLetterConf : {}", indNewsLetterConfDTO);
        if (indNewsLetterConfDTO.getId() != null) {
            throw new BadRequestAlertException("A new indNewsLetterConf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IndNewsLetterConfDTO result = indNewsLetterConfService.save(indNewsLetterConfDTO);
        return ResponseEntity
            .created(new URI("/api/ind-news-letter-confs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ind-news-letter-confs/:id} : Updates an existing indNewsLetterConf.
     *
     * @param id the id of the indNewsLetterConfDTO to save.
     * @param indNewsLetterConfDTO the indNewsLetterConfDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indNewsLetterConfDTO,
     * or with status {@code 400 (Bad Request)} if the indNewsLetterConfDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the indNewsLetterConfDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ind-news-letter-confs/{id}")
    public ResponseEntity<IndNewsLetterConfDTO> updateIndNewsLetterConf(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IndNewsLetterConfDTO indNewsLetterConfDTO
    ) throws URISyntaxException {
        log.debug("REST request to update IndNewsLetterConf : {}, {}", id, indNewsLetterConfDTO);
        if (indNewsLetterConfDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indNewsLetterConfDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indNewsLetterConfRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IndNewsLetterConfDTO result = indNewsLetterConfService.save(indNewsLetterConfDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, indNewsLetterConfDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ind-news-letter-confs/:id} : Partial updates given fields of an existing indNewsLetterConf, field will ignore if it is null
     *
     * @param id the id of the indNewsLetterConfDTO to save.
     * @param indNewsLetterConfDTO the indNewsLetterConfDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indNewsLetterConfDTO,
     * or with status {@code 400 (Bad Request)} if the indNewsLetterConfDTO is not valid,
     * or with status {@code 404 (Not Found)} if the indNewsLetterConfDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the indNewsLetterConfDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ind-news-letter-confs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<IndNewsLetterConfDTO> partialUpdateIndNewsLetterConf(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IndNewsLetterConfDTO indNewsLetterConfDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update IndNewsLetterConf partially : {}, {}", id, indNewsLetterConfDTO);
        if (indNewsLetterConfDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indNewsLetterConfDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indNewsLetterConfRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IndNewsLetterConfDTO> result = indNewsLetterConfService.partialUpdate(indNewsLetterConfDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, indNewsLetterConfDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ind-news-letter-confs} : get all the indNewsLetterConfs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of indNewsLetterConfs in body.
     */
    @GetMapping("/ind-news-letter-confs")
    public ResponseEntity<List<IndNewsLetterConfDTO>> getAllIndNewsLetterConfs(IndNewsLetterConfCriteria criteria) {
        log.debug("REST request to get IndNewsLetterConfs by criteria: {}", criteria);
        List<IndNewsLetterConfDTO> entityList = indNewsLetterConfQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ind-news-letter-confs/count} : count all the indNewsLetterConfs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ind-news-letter-confs/count")
    public ResponseEntity<Long> countIndNewsLetterConfs(IndNewsLetterConfCriteria criteria) {
        log.debug("REST request to count IndNewsLetterConfs by criteria: {}", criteria);
        return ResponseEntity.ok().body(indNewsLetterConfQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ind-news-letter-confs/:id} : get the "id" indNewsLetterConf.
     *
     * @param id the id of the indNewsLetterConfDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the indNewsLetterConfDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ind-news-letter-confs/{id}")
    public ResponseEntity<IndNewsLetterConfDTO> getIndNewsLetterConf(@PathVariable Long id) {
        log.debug("REST request to get IndNewsLetterConf : {}", id);
        Optional<IndNewsLetterConfDTO> indNewsLetterConfDTO = indNewsLetterConfService.findOne(id);
        return ResponseUtil.wrapOrNotFound(indNewsLetterConfDTO);
    }

    /**
     * {@code DELETE  /ind-news-letter-confs/:id} : delete the "id" indNewsLetterConf.
     *
     * @param id the id of the indNewsLetterConfDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ind-news-letter-confs/{id}")
    public ResponseEntity<Void> deleteIndNewsLetterConf(@PathVariable Long id) {
        log.debug("REST request to delete IndNewsLetterConf : {}", id);
        indNewsLetterConfService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
