package com.apptium.customer.web.rest;

import com.apptium.customer.repository.ShoppingSessionRefRepository;
import com.apptium.customer.service.ShoppingSessionRefQueryService;
import com.apptium.customer.service.ShoppingSessionRefService;
import com.apptium.customer.service.criteria.ShoppingSessionRefCriteria;
import com.apptium.customer.service.dto.ShoppingSessionRefDTO;
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
 * REST controller for managing {@link com.apptium.customer.domain.ShoppingSessionRef}.
 */
@RestController
@RequestMapping("/api")
public class ShoppingSessionRefResource {

    private final Logger log = LoggerFactory.getLogger(ShoppingSessionRefResource.class);

    private static final String ENTITY_NAME = "customerManagementShoppingSessionRef";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShoppingSessionRefService shoppingSessionRefService;

    private final ShoppingSessionRefRepository shoppingSessionRefRepository;

    private final ShoppingSessionRefQueryService shoppingSessionRefQueryService;

    public ShoppingSessionRefResource(
        ShoppingSessionRefService shoppingSessionRefService,
        ShoppingSessionRefRepository shoppingSessionRefRepository,
        ShoppingSessionRefQueryService shoppingSessionRefQueryService
    ) {
        this.shoppingSessionRefService = shoppingSessionRefService;
        this.shoppingSessionRefRepository = shoppingSessionRefRepository;
        this.shoppingSessionRefQueryService = shoppingSessionRefQueryService;
    }

    /**
     * {@code POST  /shopping-session-refs} : Create a new shoppingSessionRef.
     *
     * @param shoppingSessionRefDTO the shoppingSessionRefDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shoppingSessionRefDTO, or with status {@code 400 (Bad Request)} if the shoppingSessionRef has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shopping-session-refs")
    public ResponseEntity<ShoppingSessionRefDTO> createShoppingSessionRef(@Valid @RequestBody ShoppingSessionRefDTO shoppingSessionRefDTO)
        throws URISyntaxException {
        log.debug("REST request to save ShoppingSessionRef : {}", shoppingSessionRefDTO);
        if (shoppingSessionRefDTO.getId() != null) {
            throw new BadRequestAlertException("A new shoppingSessionRef cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShoppingSessionRefDTO result = shoppingSessionRefService.save(shoppingSessionRefDTO);
        return ResponseEntity
            .created(new URI("/api/shopping-session-refs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shopping-session-refs/:id} : Updates an existing shoppingSessionRef.
     *
     * @param id the id of the shoppingSessionRefDTO to save.
     * @param shoppingSessionRefDTO the shoppingSessionRefDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shoppingSessionRefDTO,
     * or with status {@code 400 (Bad Request)} if the shoppingSessionRefDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shoppingSessionRefDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shopping-session-refs/{id}")
    public ResponseEntity<ShoppingSessionRefDTO> updateShoppingSessionRef(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ShoppingSessionRefDTO shoppingSessionRefDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ShoppingSessionRef : {}, {}", id, shoppingSessionRefDTO);
        if (shoppingSessionRefDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shoppingSessionRefDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shoppingSessionRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ShoppingSessionRefDTO result = shoppingSessionRefService.save(shoppingSessionRefDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, shoppingSessionRefDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /shopping-session-refs/:id} : Partial updates given fields of an existing shoppingSessionRef, field will ignore if it is null
     *
     * @param id the id of the shoppingSessionRefDTO to save.
     * @param shoppingSessionRefDTO the shoppingSessionRefDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shoppingSessionRefDTO,
     * or with status {@code 400 (Bad Request)} if the shoppingSessionRefDTO is not valid,
     * or with status {@code 404 (Not Found)} if the shoppingSessionRefDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the shoppingSessionRefDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/shopping-session-refs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ShoppingSessionRefDTO> partialUpdateShoppingSessionRef(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ShoppingSessionRefDTO shoppingSessionRefDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ShoppingSessionRef partially : {}, {}", id, shoppingSessionRefDTO);
        if (shoppingSessionRefDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shoppingSessionRefDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shoppingSessionRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ShoppingSessionRefDTO> result = shoppingSessionRefService.partialUpdate(shoppingSessionRefDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, shoppingSessionRefDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /shopping-session-refs} : get all the shoppingSessionRefs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shoppingSessionRefs in body.
     */
    @GetMapping("/shopping-session-refs")
    public ResponseEntity<List<ShoppingSessionRefDTO>> getAllShoppingSessionRefs(ShoppingSessionRefCriteria criteria) {
        log.debug("REST request to get ShoppingSessionRefs by criteria: {}", criteria);
        List<ShoppingSessionRefDTO> entityList = shoppingSessionRefQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /shopping-session-refs/count} : count all the shoppingSessionRefs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/shopping-session-refs/count")
    public ResponseEntity<Long> countShoppingSessionRefs(ShoppingSessionRefCriteria criteria) {
        log.debug("REST request to count ShoppingSessionRefs by criteria: {}", criteria);
        return ResponseEntity.ok().body(shoppingSessionRefQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /shopping-session-refs/:id} : get the "id" shoppingSessionRef.
     *
     * @param id the id of the shoppingSessionRefDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shoppingSessionRefDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shopping-session-refs/{id}")
    public ResponseEntity<ShoppingSessionRefDTO> getShoppingSessionRef(@PathVariable Long id) {
        log.debug("REST request to get ShoppingSessionRef : {}", id);
        Optional<ShoppingSessionRefDTO> shoppingSessionRefDTO = shoppingSessionRefService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shoppingSessionRefDTO);
    }

    /**
     * {@code DELETE  /shopping-session-refs/:id} : delete the "id" shoppingSessionRef.
     *
     * @param id the id of the shoppingSessionRefDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shopping-session-refs/{id}")
    public ResponseEntity<Void> deleteShoppingSessionRef(@PathVariable Long id) {
        log.debug("REST request to delete ShoppingSessionRef : {}", id);
        shoppingSessionRefService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
