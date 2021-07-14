package com.apptium.customer.web.rest;

import com.apptium.customer.repository.AutoPayRepository;
import com.apptium.customer.service.AutoPayQueryService;
import com.apptium.customer.service.AutoPayService;
import com.apptium.customer.service.criteria.AutoPayCriteria;
import com.apptium.customer.service.dto.AutoPayDTO;
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
 * REST controller for managing {@link com.apptium.customer.domain.AutoPay}.
 */
@RestController
@RequestMapping("/api")
public class AutoPayResource {

    private final Logger log = LoggerFactory.getLogger(AutoPayResource.class);

    private static final String ENTITY_NAME = "customerManagementAutoPay";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutoPayService autoPayService;

    private final AutoPayRepository autoPayRepository;

    private final AutoPayQueryService autoPayQueryService;

    public AutoPayResource(AutoPayService autoPayService, AutoPayRepository autoPayRepository, AutoPayQueryService autoPayQueryService) {
        this.autoPayService = autoPayService;
        this.autoPayRepository = autoPayRepository;
        this.autoPayQueryService = autoPayQueryService;
    }

    /**
     * {@code POST  /auto-pays} : Create a new autoPay.
     *
     * @param autoPayDTO the autoPayDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autoPayDTO, or with status {@code 400 (Bad Request)} if the autoPay has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/auto-pays")
    public ResponseEntity<AutoPayDTO> createAutoPay(@Valid @RequestBody AutoPayDTO autoPayDTO) throws URISyntaxException {
        log.debug("REST request to save AutoPay : {}", autoPayDTO);
        if (autoPayDTO.getId() != null) {
            throw new BadRequestAlertException("A new autoPay cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AutoPayDTO result = autoPayService.save(autoPayDTO);
        return ResponseEntity
            .created(new URI("/api/auto-pays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /auto-pays/:id} : Updates an existing autoPay.
     *
     * @param id the id of the autoPayDTO to save.
     * @param autoPayDTO the autoPayDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autoPayDTO,
     * or with status {@code 400 (Bad Request)} if the autoPayDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autoPayDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/auto-pays/{id}")
    public ResponseEntity<AutoPayDTO> updateAutoPay(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AutoPayDTO autoPayDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AutoPay : {}, {}", id, autoPayDTO);
        if (autoPayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, autoPayDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autoPayRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AutoPayDTO result = autoPayService.save(autoPayDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, autoPayDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /auto-pays/:id} : Partial updates given fields of an existing autoPay, field will ignore if it is null
     *
     * @param id the id of the autoPayDTO to save.
     * @param autoPayDTO the autoPayDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autoPayDTO,
     * or with status {@code 400 (Bad Request)} if the autoPayDTO is not valid,
     * or with status {@code 404 (Not Found)} if the autoPayDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the autoPayDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/auto-pays/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AutoPayDTO> partialUpdateAutoPay(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AutoPayDTO autoPayDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AutoPay partially : {}, {}", id, autoPayDTO);
        if (autoPayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, autoPayDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autoPayRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AutoPayDTO> result = autoPayService.partialUpdate(autoPayDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, autoPayDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /auto-pays} : get all the autoPays.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autoPays in body.
     */
    @GetMapping("/auto-pays")
    public ResponseEntity<List<AutoPayDTO>> getAllAutoPays(AutoPayCriteria criteria) {
        log.debug("REST request to get AutoPays by criteria: {}", criteria);
        List<AutoPayDTO> entityList = autoPayQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /auto-pays/count} : count all the autoPays.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/auto-pays/count")
    public ResponseEntity<Long> countAutoPays(AutoPayCriteria criteria) {
        log.debug("REST request to count AutoPays by criteria: {}", criteria);
        return ResponseEntity.ok().body(autoPayQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /auto-pays/:id} : get the "id" autoPay.
     *
     * @param id the id of the autoPayDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autoPayDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/auto-pays/{id}")
    public ResponseEntity<AutoPayDTO> getAutoPay(@PathVariable Long id) {
        log.debug("REST request to get AutoPay : {}", id);
        Optional<AutoPayDTO> autoPayDTO = autoPayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(autoPayDTO);
    }

    /**
     * {@code DELETE  /auto-pays/:id} : delete the "id" autoPay.
     *
     * @param id the id of the autoPayDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/auto-pays/{id}")
    public ResponseEntity<Void> deleteAutoPay(@PathVariable Long id) {
        log.debug("REST request to delete AutoPay : {}", id);
        autoPayService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
