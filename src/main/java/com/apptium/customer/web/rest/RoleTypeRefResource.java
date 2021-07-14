package com.apptium.customer.web.rest;

import com.apptium.customer.repository.RoleTypeRefRepository;
import com.apptium.customer.service.RoleTypeRefQueryService;
import com.apptium.customer.service.RoleTypeRefService;
import com.apptium.customer.service.criteria.RoleTypeRefCriteria;
import com.apptium.customer.service.dto.RoleTypeRefDTO;
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
 * REST controller for managing {@link com.apptium.customer.domain.RoleTypeRef}.
 */
@RestController
@RequestMapping("/api")
public class RoleTypeRefResource {

    private final Logger log = LoggerFactory.getLogger(RoleTypeRefResource.class);

    private static final String ENTITY_NAME = "customerManagementRoleTypeRef";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RoleTypeRefService roleTypeRefService;

    private final RoleTypeRefRepository roleTypeRefRepository;

    private final RoleTypeRefQueryService roleTypeRefQueryService;

    public RoleTypeRefResource(
        RoleTypeRefService roleTypeRefService,
        RoleTypeRefRepository roleTypeRefRepository,
        RoleTypeRefQueryService roleTypeRefQueryService
    ) {
        this.roleTypeRefService = roleTypeRefService;
        this.roleTypeRefRepository = roleTypeRefRepository;
        this.roleTypeRefQueryService = roleTypeRefQueryService;
    }

    /**
     * {@code POST  /role-type-refs} : Create a new roleTypeRef.
     *
     * @param roleTypeRefDTO the roleTypeRefDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new roleTypeRefDTO, or with status {@code 400 (Bad Request)} if the roleTypeRef has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/role-type-refs")
    public ResponseEntity<RoleTypeRefDTO> createRoleTypeRef(@Valid @RequestBody RoleTypeRefDTO roleTypeRefDTO) throws URISyntaxException {
        log.debug("REST request to save RoleTypeRef : {}", roleTypeRefDTO);
        if (roleTypeRefDTO.getId() != null) {
            throw new BadRequestAlertException("A new roleTypeRef cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoleTypeRefDTO result = roleTypeRefService.save(roleTypeRefDTO);
        return ResponseEntity
            .created(new URI("/api/role-type-refs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /role-type-refs/:id} : Updates an existing roleTypeRef.
     *
     * @param id the id of the roleTypeRefDTO to save.
     * @param roleTypeRefDTO the roleTypeRefDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleTypeRefDTO,
     * or with status {@code 400 (Bad Request)} if the roleTypeRefDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the roleTypeRefDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/role-type-refs/{id}")
    public ResponseEntity<RoleTypeRefDTO> updateRoleTypeRef(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RoleTypeRefDTO roleTypeRefDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RoleTypeRef : {}, {}", id, roleTypeRefDTO);
        if (roleTypeRefDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roleTypeRefDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roleTypeRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RoleTypeRefDTO result = roleTypeRefService.save(roleTypeRefDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, roleTypeRefDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /role-type-refs/:id} : Partial updates given fields of an existing roleTypeRef, field will ignore if it is null
     *
     * @param id the id of the roleTypeRefDTO to save.
     * @param roleTypeRefDTO the roleTypeRefDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated roleTypeRefDTO,
     * or with status {@code 400 (Bad Request)} if the roleTypeRefDTO is not valid,
     * or with status {@code 404 (Not Found)} if the roleTypeRefDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the roleTypeRefDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/role-type-refs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RoleTypeRefDTO> partialUpdateRoleTypeRef(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RoleTypeRefDTO roleTypeRefDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RoleTypeRef partially : {}, {}", id, roleTypeRefDTO);
        if (roleTypeRefDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, roleTypeRefDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!roleTypeRefRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RoleTypeRefDTO> result = roleTypeRefService.partialUpdate(roleTypeRefDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, roleTypeRefDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /role-type-refs} : get all the roleTypeRefs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roleTypeRefs in body.
     */
    @GetMapping("/role-type-refs")
    public ResponseEntity<List<RoleTypeRefDTO>> getAllRoleTypeRefs(RoleTypeRefCriteria criteria) {
        log.debug("REST request to get RoleTypeRefs by criteria: {}", criteria);
        List<RoleTypeRefDTO> entityList = roleTypeRefQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /role-type-refs/count} : count all the roleTypeRefs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/role-type-refs/count")
    public ResponseEntity<Long> countRoleTypeRefs(RoleTypeRefCriteria criteria) {
        log.debug("REST request to count RoleTypeRefs by criteria: {}", criteria);
        return ResponseEntity.ok().body(roleTypeRefQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /role-type-refs/:id} : get the "id" roleTypeRef.
     *
     * @param id the id of the roleTypeRefDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the roleTypeRefDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/role-type-refs/{id}")
    public ResponseEntity<RoleTypeRefDTO> getRoleTypeRef(@PathVariable Long id) {
        log.debug("REST request to get RoleTypeRef : {}", id);
        Optional<RoleTypeRefDTO> roleTypeRefDTO = roleTypeRefService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roleTypeRefDTO);
    }

    /**
     * {@code DELETE  /role-type-refs/:id} : delete the "id" roleTypeRef.
     *
     * @param id the id of the roleTypeRefDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/role-type-refs/{id}")
    public ResponseEntity<Void> deleteRoleTypeRef(@PathVariable Long id) {
        log.debug("REST request to delete RoleTypeRef : {}", id);
        roleTypeRefService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
