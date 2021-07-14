package com.apptium.customer.web.rest;

import com.apptium.customer.repository.IndContactRepository;
import com.apptium.customer.service.IndContactQueryService;
import com.apptium.customer.service.IndContactService;
import com.apptium.customer.service.criteria.IndContactCriteria;
import com.apptium.customer.service.dto.IndContactDTO;
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
 * REST controller for managing {@link com.apptium.customer.domain.IndContact}.
 */
@RestController
@RequestMapping("/api")
public class IndContactResource {

    private final Logger log = LoggerFactory.getLogger(IndContactResource.class);

    private static final String ENTITY_NAME = "customerManagementIndContact";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IndContactService indContactService;

    private final IndContactRepository indContactRepository;

    private final IndContactQueryService indContactQueryService;

    public IndContactResource(
        IndContactService indContactService,
        IndContactRepository indContactRepository,
        IndContactQueryService indContactQueryService
    ) {
        this.indContactService = indContactService;
        this.indContactRepository = indContactRepository;
        this.indContactQueryService = indContactQueryService;
    }

    /**
     * {@code POST  /ind-contacts} : Create a new indContact.
     *
     * @param indContactDTO the indContactDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new indContactDTO, or with status {@code 400 (Bad Request)} if the indContact has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ind-contacts")
    public ResponseEntity<IndContactDTO> createIndContact(@Valid @RequestBody IndContactDTO indContactDTO) throws URISyntaxException {
        log.debug("REST request to save IndContact : {}", indContactDTO);
        if (indContactDTO.getId() != null) {
            throw new BadRequestAlertException("A new indContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IndContactDTO result = indContactService.save(indContactDTO);
        return ResponseEntity
            .created(new URI("/api/ind-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ind-contacts/:id} : Updates an existing indContact.
     *
     * @param id the id of the indContactDTO to save.
     * @param indContactDTO the indContactDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indContactDTO,
     * or with status {@code 400 (Bad Request)} if the indContactDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the indContactDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ind-contacts/{id}")
    public ResponseEntity<IndContactDTO> updateIndContact(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IndContactDTO indContactDTO
    ) throws URISyntaxException {
        log.debug("REST request to update IndContact : {}, {}", id, indContactDTO);
        if (indContactDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indContactDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indContactRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IndContactDTO result = indContactService.save(indContactDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, indContactDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ind-contacts/:id} : Partial updates given fields of an existing indContact, field will ignore if it is null
     *
     * @param id the id of the indContactDTO to save.
     * @param indContactDTO the indContactDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated indContactDTO,
     * or with status {@code 400 (Bad Request)} if the indContactDTO is not valid,
     * or with status {@code 404 (Not Found)} if the indContactDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the indContactDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ind-contacts/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<IndContactDTO> partialUpdateIndContact(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IndContactDTO indContactDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update IndContact partially : {}, {}", id, indContactDTO);
        if (indContactDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, indContactDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!indContactRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IndContactDTO> result = indContactService.partialUpdate(indContactDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, indContactDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ind-contacts} : get all the indContacts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of indContacts in body.
     */
    @GetMapping("/ind-contacts")
    public ResponseEntity<List<IndContactDTO>> getAllIndContacts(IndContactCriteria criteria) {
        log.debug("REST request to get IndContacts by criteria: {}", criteria);
        List<IndContactDTO> entityList = indContactQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /ind-contacts/count} : count all the indContacts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ind-contacts/count")
    public ResponseEntity<Long> countIndContacts(IndContactCriteria criteria) {
        log.debug("REST request to count IndContacts by criteria: {}", criteria);
        return ResponseEntity.ok().body(indContactQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ind-contacts/:id} : get the "id" indContact.
     *
     * @param id the id of the indContactDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the indContactDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ind-contacts/{id}")
    public ResponseEntity<IndContactDTO> getIndContact(@PathVariable Long id) {
        log.debug("REST request to get IndContact : {}", id);
        Optional<IndContactDTO> indContactDTO = indContactService.findOne(id);
        return ResponseUtil.wrapOrNotFound(indContactDTO);
    }

    /**
     * {@code DELETE  /ind-contacts/:id} : delete the "id" indContact.
     *
     * @param id the id of the indContactDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ind-contacts/{id}")
    public ResponseEntity<Void> deleteIndContact(@PathVariable Long id) {
        log.debug("REST request to delete IndContact : {}", id);
        indContactService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
