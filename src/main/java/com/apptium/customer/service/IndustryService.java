package com.apptium.customer.service;

import com.apptium.customer.service.dto.IndustryDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.Industry}.
 */
public interface IndustryService {
    /**
     * Save a industry.
     *
     * @param industryDTO the entity to save.
     * @return the persisted entity.
     */
    IndustryDTO save(IndustryDTO industryDTO);

    /**
     * Partially updates a industry.
     *
     * @param industryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IndustryDTO> partialUpdate(IndustryDTO industryDTO);

    /**
     * Get all the industries.
     *
     * @return the list of entities.
     */
    List<IndustryDTO> findAll();

    /**
     * Get the "id" industry.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IndustryDTO> findOne(Long id);

    /**
     * Delete the "id" industry.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
