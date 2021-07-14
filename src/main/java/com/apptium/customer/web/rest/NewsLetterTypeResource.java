package com.apptium.customer.web.rest;

import com.apptium.customer.repository.NewsLetterTypeRepository;
import com.apptium.customer.service.NewsLetterTypeQueryService;
import com.apptium.customer.service.NewsLetterTypeService;
import com.apptium.customer.service.criteria.NewsLetterTypeCriteria;
import com.apptium.customer.service.dto.NewsLetterTypeDTO;
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
 * REST controller for managing {@link com.apptium.customer.domain.NewsLetterType}.
 */
@RestController
@RequestMapping("/api")
public class NewsLetterTypeResource {

    private final Logger log = LoggerFactory.getLogger(NewsLetterTypeResource.class);

    private static final String ENTITY_NAME = "customerManagementNewsLetterType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NewsLetterTypeService newsLetterTypeService;

    private final NewsLetterTypeRepository newsLetterTypeRepository;

    private final NewsLetterTypeQueryService newsLetterTypeQueryService;

    public NewsLetterTypeResource(
        NewsLetterTypeService newsLetterTypeService,
        NewsLetterTypeRepository newsLetterTypeRepository,
        NewsLetterTypeQueryService newsLetterTypeQueryService
    ) {
        this.newsLetterTypeService = newsLetterTypeService;
        this.newsLetterTypeRepository = newsLetterTypeRepository;
        this.newsLetterTypeQueryService = newsLetterTypeQueryService;
    }

    /**
     * {@code POST  /news-letter-types} : Create a new newsLetterType.
     *
     * @param newsLetterTypeDTO the newsLetterTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new newsLetterTypeDTO, or with status {@code 400 (Bad Request)} if the newsLetterType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/news-letter-types")
    public ResponseEntity<NewsLetterTypeDTO> createNewsLetterType(@Valid @RequestBody NewsLetterTypeDTO newsLetterTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save NewsLetterType : {}", newsLetterTypeDTO);
        if (newsLetterTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new newsLetterType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NewsLetterTypeDTO result = newsLetterTypeService.save(newsLetterTypeDTO);
        return ResponseEntity
            .created(new URI("/api/news-letter-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /news-letter-types/:id} : Updates an existing newsLetterType.
     *
     * @param id the id of the newsLetterTypeDTO to save.
     * @param newsLetterTypeDTO the newsLetterTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated newsLetterTypeDTO,
     * or with status {@code 400 (Bad Request)} if the newsLetterTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the newsLetterTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/news-letter-types/{id}")
    public ResponseEntity<NewsLetterTypeDTO> updateNewsLetterType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NewsLetterTypeDTO newsLetterTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update NewsLetterType : {}, {}", id, newsLetterTypeDTO);
        if (newsLetterTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, newsLetterTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!newsLetterTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NewsLetterTypeDTO result = newsLetterTypeService.save(newsLetterTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, newsLetterTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /news-letter-types/:id} : Partial updates given fields of an existing newsLetterType, field will ignore if it is null
     *
     * @param id the id of the newsLetterTypeDTO to save.
     * @param newsLetterTypeDTO the newsLetterTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated newsLetterTypeDTO,
     * or with status {@code 400 (Bad Request)} if the newsLetterTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the newsLetterTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the newsLetterTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/news-letter-types/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<NewsLetterTypeDTO> partialUpdateNewsLetterType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NewsLetterTypeDTO newsLetterTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update NewsLetterType partially : {}, {}", id, newsLetterTypeDTO);
        if (newsLetterTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, newsLetterTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!newsLetterTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NewsLetterTypeDTO> result = newsLetterTypeService.partialUpdate(newsLetterTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, newsLetterTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /news-letter-types} : get all the newsLetterTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of newsLetterTypes in body.
     */
    @GetMapping("/news-letter-types")
    public ResponseEntity<List<NewsLetterTypeDTO>> getAllNewsLetterTypes(NewsLetterTypeCriteria criteria) {
        log.debug("REST request to get NewsLetterTypes by criteria: {}", criteria);
        List<NewsLetterTypeDTO> entityList = newsLetterTypeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /news-letter-types/count} : count all the newsLetterTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/news-letter-types/count")
    public ResponseEntity<Long> countNewsLetterTypes(NewsLetterTypeCriteria criteria) {
        log.debug("REST request to count NewsLetterTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(newsLetterTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /news-letter-types/:id} : get the "id" newsLetterType.
     *
     * @param id the id of the newsLetterTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the newsLetterTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/news-letter-types/{id}")
    public ResponseEntity<NewsLetterTypeDTO> getNewsLetterType(@PathVariable Long id) {
        log.debug("REST request to get NewsLetterType : {}", id);
        Optional<NewsLetterTypeDTO> newsLetterTypeDTO = newsLetterTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(newsLetterTypeDTO);
    }

    /**
     * {@code DELETE  /news-letter-types/:id} : delete the "id" newsLetterType.
     *
     * @param id the id of the newsLetterTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/news-letter-types/{id}")
    public ResponseEntity<Void> deleteNewsLetterType(@PathVariable Long id) {
        log.debug("REST request to delete NewsLetterType : {}", id);
        newsLetterTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
