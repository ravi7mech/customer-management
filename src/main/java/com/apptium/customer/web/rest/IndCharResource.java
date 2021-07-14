package com.apptium.customer.web.rest;

import com.apptium.customer.repository.IndCharRepository;
import com.apptium.customer.service.IndCharQueryService;
import com.apptium.customer.service.IndCharService;
import com.apptium.customer.service.criteria.IndCharCriteria;
import com.apptium.customer.service.dto.IndCharDTO;
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
 * REST controller for managing {@link com.apptium.customer.domain.IndChar}.
 */
@RestController
@RequestMapping("/api")
public class IndCharResource {

    private final Logger log = LoggerFactory.getLogger(IndCharResource.class);

    private static final String ENTITY_NAME = "customerManagementIndChar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndCharService indCharService;

    private final IndCharRepository indCharRepository;

    private final IndCharQueryService indCharQueryService;

    public IndCharResource(IndCharService indCharService, IndCharRepository indCharRepository, IndCharQueryService indCharQueryService) {
        this.indCharService = indCharService;
        this.indCharRepository = indCharRepository;
        this.indCharQueryService = indCharQueryService;
    }

    /**
     * {@code POST  /ind-chars} : Create a new indChar.
     *
     * @param indCharDTO the indCharDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new indCharDTO, or with status {@code 400 (Bad Request)} if the indChar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ind-chars")
    public ResponseEntity<IndCharDTO> createIndChar(@Valid @RequestBody IndCharDTO indCharDTO) throws URISyntaxException {
        log.debug("REST request to save IndChar : {}", indCharDTO);
        if (indCharDTO.getId() != null) {
            throw new BadRequestAlertException("A new indChar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IndCharDTO result = indCharService.save(indCharDTO);
        return ResponseEntity
            .created(new URI("/api/ind-chars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ind-chars/:id} : Updates an existing indChar.
     *
     * @param id the id of the indCharDTO to save.
     * @param indCharDTO the indCharDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indCharDTO,
     * or with status {@code 400 (Bad Request)} if the indCharDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the indCharDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ind-chars/{id}")
    public ResponseEntity<IndCharDTO> updateIndChar(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IndCharDTO indCharDTO
    ) throws URISyntaxException {
        log.debug("REST request to update IndChar : {}, {}", id, indCharDTO);
        if (indCharDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indCharDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IndCharDTO result = indCharService.save(indCharDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, indCharDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ind-chars/:id} : Partial updates given fields of an existing indChar, field will ignore if it is null
     *
     * @param id the id of the indCharDTO to save.
     * @param indCharDTO the indCharDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indCharDTO,
     * or with status {@code 400 (Bad Request)} if the indCharDTO is not valid,
     * or with status {@code 404 (Not Found)} if the indCharDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the indCharDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ind-chars/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<IndCharDTO> partialUpdateIndChar(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IndCharDTO indCharDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update IndChar partially : {}, {}", id, indCharDTO);
        if (indCharDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indCharDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IndCharDTO> result = indCharService.partialUpdate(indCharDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, indCharDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ind-chars} : get all the indChars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of indChars in body.
     */
    @GetMapping("/ind-chars")
    public ResponseEntity<List<IndCharDTO>> getAllIndChars(IndCharCriteria criteria) {
        log.debug("REST request to get IndChars by criteria: {}", criteria);
        List<IndCharDTO> entityList = indCharQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ind-chars/count} : count all the indChars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ind-chars/count")
    public ResponseEntity<Long> countIndChars(IndCharCriteria criteria) {
        log.debug("REST request to count IndChars by criteria: {}", criteria);
        return ResponseEntity.ok().body(indCharQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ind-chars/:id} : get the "id" indChar.
     *
     * @param id the id of the indCharDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the indCharDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ind-chars/{id}")
    public ResponseEntity<IndCharDTO> getIndChar(@PathVariable Long id) {
        log.debug("REST request to get IndChar : {}", id);
        Optional<IndCharDTO> indCharDTO = indCharService.findOne(id);
        return ResponseUtil.wrapOrNotFound(indCharDTO);
    }

    /**
     * {@code DELETE  /ind-chars/:id} : delete the "id" indChar.
     *
     * @param id the id of the indCharDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ind-chars/{id}")
    public ResponseEntity<Void> deleteIndChar(@PathVariable Long id) {
        log.debug("REST request to delete IndChar : {}", id);
        indCharService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
