package com.apptium.customer.web.rest;

import com.apptium.customer.repository.BankCardTypeRepository;
import com.apptium.customer.service.BankCardTypeQueryService;
import com.apptium.customer.service.BankCardTypeService;
import com.apptium.customer.service.criteria.BankCardTypeCriteria;
import com.apptium.customer.service.dto.BankCardTypeDTO;
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
 * REST controller for managing {@link com.apptium.customer.domain.BankCardType}.
 */
@RestController
@RequestMapping("/api")
public class BankCardTypeResource {

    private final Logger log = LoggerFactory.getLogger(BankCardTypeResource.class);

    private static final String ENTITY_NAME = "customerManagementBankCardType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankCardTypeService bankCardTypeService;

    private final BankCardTypeRepository bankCardTypeRepository;

    private final BankCardTypeQueryService bankCardTypeQueryService;

    public BankCardTypeResource(
        BankCardTypeService bankCardTypeService,
        BankCardTypeRepository bankCardTypeRepository,
        BankCardTypeQueryService bankCardTypeQueryService
    ) {
        this.bankCardTypeService = bankCardTypeService;
        this.bankCardTypeRepository = bankCardTypeRepository;
        this.bankCardTypeQueryService = bankCardTypeQueryService;
    }

    /**
     * {@code POST  /bank-card-types} : Create a new bankCardType.
     *
     * @param bankCardTypeDTO the bankCardTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankCardTypeDTO, or with status {@code 400 (Bad Request)} if the bankCardType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bank-card-types")
    public ResponseEntity<BankCardTypeDTO> createBankCardType(@Valid @RequestBody BankCardTypeDTO bankCardTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save BankCardType : {}", bankCardTypeDTO);
        if (bankCardTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new bankCardType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BankCardTypeDTO result = bankCardTypeService.save(bankCardTypeDTO);
        return ResponseEntity
            .created(new URI("/api/bank-card-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bank-card-types/:id} : Updates an existing bankCardType.
     *
     * @param id the id of the bankCardTypeDTO to save.
     * @param bankCardTypeDTO the bankCardTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankCardTypeDTO,
     * or with status {@code 400 (Bad Request)} if the bankCardTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankCardTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bank-card-types/{id}")
    public ResponseEntity<BankCardTypeDTO> updateBankCardType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BankCardTypeDTO bankCardTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BankCardType : {}, {}", id, bankCardTypeDTO);
        if (bankCardTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankCardTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankCardTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BankCardTypeDTO result = bankCardTypeService.save(bankCardTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bankCardTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bank-card-types/:id} : Partial updates given fields of an existing bankCardType, field will ignore if it is null
     *
     * @param id the id of the bankCardTypeDTO to save.
     * @param bankCardTypeDTO the bankCardTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankCardTypeDTO,
     * or with status {@code 400 (Bad Request)} if the bankCardTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bankCardTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankCardTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bank-card-types/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<BankCardTypeDTO> partialUpdateBankCardType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BankCardTypeDTO bankCardTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BankCardType partially : {}, {}", id, bankCardTypeDTO);
        if (bankCardTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankCardTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankCardTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BankCardTypeDTO> result = bankCardTypeService.partialUpdate(bankCardTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bankCardTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bank-card-types} : get all the bankCardTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankCardTypes in body.
     */
    @GetMapping("/bank-card-types")
    public ResponseEntity<List<BankCardTypeDTO>> getAllBankCardTypes(BankCardTypeCriteria criteria) {
        log.debug("REST request to get BankCardTypes by criteria: {}", criteria);
        List<BankCardTypeDTO> entityList = bankCardTypeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /bank-card-types/count} : count all the bankCardTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/bank-card-types/count")
    public ResponseEntity<Long> countBankCardTypes(BankCardTypeCriteria criteria) {
        log.debug("REST request to count BankCardTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(bankCardTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /bank-card-types/:id} : get the "id" bankCardType.
     *
     * @param id the id of the bankCardTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankCardTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bank-card-types/{id}")
    public ResponseEntity<BankCardTypeDTO> getBankCardType(@PathVariable Long id) {
        log.debug("REST request to get BankCardType : {}", id);
        Optional<BankCardTypeDTO> bankCardTypeDTO = bankCardTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankCardTypeDTO);
    }

    /**
     * {@code DELETE  /bank-card-types/:id} : delete the "id" bankCardType.
     *
     * @param id the id of the bankCardTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bank-card-types/{id}")
    public ResponseEntity<Void> deleteBankCardType(@PathVariable Long id) {
        log.debug("REST request to delete BankCardType : {}", id);
        bankCardTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
