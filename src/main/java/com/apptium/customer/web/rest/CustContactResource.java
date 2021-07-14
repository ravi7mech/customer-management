package com.apptium.customer.web.rest;

import com.apptium.customer.repository.CustContactRepository;
import com.apptium.customer.service.CustContactQueryService;
import com.apptium.customer.service.CustContactService;
import com.apptium.customer.service.criteria.CustContactCriteria;
import com.apptium.customer.service.dto.CustContactDTO;
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
 * REST controller for managing {@link com.apptium.customer.domain.CustContact}.
 */
@RestController
@RequestMapping("/api")
public class CustContactResource {

    private final Logger log = LoggerFactory.getLogger(CustContactResource.class);

    private static final String ENTITY_NAME = "customerManagementCustContact";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CustContactService custContactService;

    private final CustContactRepository custContactRepository;

    private final CustContactQueryService custContactQueryService;

    public CustContactResource(
        CustContactService custContactService,
        CustContactRepository custContactRepository,
        CustContactQueryService custContactQueryService
    ) {
        this.custContactService = custContactService;
        this.custContactRepository = custContactRepository;
        this.custContactQueryService = custContactQueryService;
    }

    /**
     * {@code POST  /cust-contacts} : Create a new custContact.
     *
     * @param custContactDTO the custContactDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new custContactDTO, or with status {@code 400 (Bad Request)} if the custContact has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cust-contacts")
    public ResponseEntity<CustContactDTO> createCustContact(@Valid @RequestBody CustContactDTO custContactDTO) throws URISyntaxException {
        log.debug("REST request to save CustContact : {}", custContactDTO);
        if (custContactDTO.getId() != null) {
            throw new BadRequestAlertException("A new custContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CustContactDTO result = custContactService.save(custContactDTO);
        return ResponseEntity
            .created(new URI("/api/cust-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cust-contacts/:id} : Updates an existing custContact.
     *
     * @param id the id of the custContactDTO to save.
     * @param custContactDTO the custContactDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custContactDTO,
     * or with status {@code 400 (Bad Request)} if the custContactDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the custContactDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cust-contacts/{id}")
    public ResponseEntity<CustContactDTO> updateCustContact(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CustContactDTO custContactDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CustContact : {}, {}", id, custContactDTO);
        if (custContactDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custContactDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custContactRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CustContactDTO result = custContactService.save(custContactDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custContactDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cust-contacts/:id} : Partial updates given fields of an existing custContact, field will ignore if it is null
     *
     * @param id the id of the custContactDTO to save.
     * @param custContactDTO the custContactDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated custContactDTO,
     * or with status {@code 400 (Bad Request)} if the custContactDTO is not valid,
     * or with status {@code 404 (Not Found)} if the custContactDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the custContactDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cust-contacts/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CustContactDTO> partialUpdateCustContact(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CustContactDTO custContactDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CustContact partially : {}, {}", id, custContactDTO);
        if (custContactDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, custContactDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!custContactRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CustContactDTO> result = custContactService.partialUpdate(custContactDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, custContactDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cust-contacts} : get all the custContacts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of custContacts in body.
     */
    @GetMapping("/cust-contacts")
    public ResponseEntity<List<CustContactDTO>> getAllCustContacts(CustContactCriteria criteria) {
        log.debug("REST request to get CustContacts by criteria: {}", criteria);
        List<CustContactDTO> entityList = custContactQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /cust-contacts/count} : count all the custContacts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cust-contacts/count")
    public ResponseEntity<Long> countCustContacts(CustContactCriteria criteria) {
        log.debug("REST request to count CustContacts by criteria: {}", criteria);
        return ResponseEntity.ok().body(custContactQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cust-contacts/:id} : get the "id" custContact.
     *
     * @param id the id of the custContactDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the custContactDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cust-contacts/{id}")
    public ResponseEntity<CustContactDTO> getCustContact(@PathVariable Long id) {
        log.debug("REST request to get CustContact : {}", id);
        Optional<CustContactDTO> custContactDTO = custContactService.findOne(id);
        return ResponseUtil.wrapOrNotFound(custContactDTO);
    }

    /**
     * {@code DELETE  /cust-contacts/:id} : delete the "id" custContact.
     *
     * @param id the id of the custContactDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cust-contacts/{id}")
    public ResponseEntity<Void> deleteCustContact(@PathVariable Long id) {
        log.debug("REST request to delete CustContact : {}", id);
        custContactService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
