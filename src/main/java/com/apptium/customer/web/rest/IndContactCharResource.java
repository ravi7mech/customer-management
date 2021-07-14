package com.apptium.customer.web.rest;

import com.apptium.customer.repository.IndContactCharRepository;
import com.apptium.customer.service.IndContactCharQueryService;
import com.apptium.customer.service.IndContactCharService;
import com.apptium.customer.service.criteria.IndContactCharCriteria;
import com.apptium.customer.service.dto.IndContactCharDTO;
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
 * REST controller for managing {@link com.apptium.customer.domain.IndContactChar}.
 */
@RestController
@RequestMapping("/api")
public class IndContactCharResource {

    private final Logger log = LoggerFactory.getLogger(IndContactCharResource.class);

    private static final String ENTITY_NAME = "customerManagementIndContactChar";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndContactCharService indContactCharService;

    private final IndContactCharRepository indContactCharRepository;

    private final IndContactCharQueryService indContactCharQueryService;

    public IndContactCharResource(
        IndContactCharService indContactCharService,
        IndContactCharRepository indContactCharRepository,
        IndContactCharQueryService indContactCharQueryService
    ) {
        this.indContactCharService = indContactCharService;
        this.indContactCharRepository = indContactCharRepository;
        this.indContactCharQueryService = indContactCharQueryService;
    }

    /**
     * {@code POST  /ind-contact-chars} : Create a new indContactChar.
     *
     * @param indContactCharDTO the indContactCharDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new indContactCharDTO, or with status {@code 400 (Bad Request)} if the indContactChar has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ind-contact-chars")
    public ResponseEntity<IndContactCharDTO> createIndContactChar(@Valid @RequestBody IndContactCharDTO indContactCharDTO)
        throws URISyntaxException {
        log.debug("REST request to save IndContactChar : {}", indContactCharDTO);
        if (indContactCharDTO.getId() != null) {
            throw new BadRequestAlertException("A new indContactChar cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IndContactCharDTO result = indContactCharService.save(indContactCharDTO);
        return ResponseEntity
            .created(new URI("/api/ind-contact-chars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ind-contact-chars/:id} : Updates an existing indContactChar.
     *
     * @param id the id of the indContactCharDTO to save.
     * @param indContactCharDTO the indContactCharDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indContactCharDTO,
     * or with status {@code 400 (Bad Request)} if the indContactCharDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the indContactCharDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ind-contact-chars/{id}")
    public ResponseEntity<IndContactCharDTO> updateIndContactChar(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IndContactCharDTO indContactCharDTO
    ) throws URISyntaxException {
        log.debug("REST request to update IndContactChar : {}, {}", id, indContactCharDTO);
        if (indContactCharDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indContactCharDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indContactCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IndContactCharDTO result = indContactCharService.save(indContactCharDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, indContactCharDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ind-contact-chars/:id} : Partial updates given fields of an existing indContactChar, field will ignore if it is null
     *
     * @param id the id of the indContactCharDTO to save.
     * @param indContactCharDTO the indContactCharDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indContactCharDTO,
     * or with status {@code 400 (Bad Request)} if the indContactCharDTO is not valid,
     * or with status {@code 404 (Not Found)} if the indContactCharDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the indContactCharDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ind-contact-chars/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<IndContactCharDTO> partialUpdateIndContactChar(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IndContactCharDTO indContactCharDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update IndContactChar partially : {}, {}", id, indContactCharDTO);
        if (indContactCharDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indContactCharDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indContactCharRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IndContactCharDTO> result = indContactCharService.partialUpdate(indContactCharDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, indContactCharDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ind-contact-chars} : get all the indContactChars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of indContactChars in body.
     */
    @GetMapping("/ind-contact-chars")
    public ResponseEntity<List<IndContactCharDTO>> getAllIndContactChars(IndContactCharCriteria criteria) {
        log.debug("REST request to get IndContactChars by criteria: {}", criteria);
        List<IndContactCharDTO> entityList = indContactCharQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ind-contact-chars/count} : count all the indContactChars.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ind-contact-chars/count")
    public ResponseEntity<Long> countIndContactChars(IndContactCharCriteria criteria) {
        log.debug("REST request to count IndContactChars by criteria: {}", criteria);
        return ResponseEntity.ok().body(indContactCharQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ind-contact-chars/:id} : get the "id" indContactChar.
     *
     * @param id the id of the indContactCharDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the indContactCharDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ind-contact-chars/{id}")
    public ResponseEntity<IndContactCharDTO> getIndContactChar(@PathVariable Long id) {
        log.debug("REST request to get IndContactChar : {}", id);
        Optional<IndContactCharDTO> indContactCharDTO = indContactCharService.findOne(id);
        return ResponseUtil.wrapOrNotFound(indContactCharDTO);
    }

    /**
     * {@code DELETE  /ind-contact-chars/:id} : delete the "id" indContactChar.
     *
     * @param id the id of the indContactCharDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ind-contact-chars/{id}")
    public ResponseEntity<Void> deleteIndContactChar(@PathVariable Long id) {
        log.debug("REST request to delete IndContactChar : {}", id);
        indContactCharService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
