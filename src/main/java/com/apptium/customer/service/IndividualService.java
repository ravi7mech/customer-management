package com.apptium.customer.service;

import com.apptium.customer.service.dto.IndividualDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.Individual}.
 */
public interface IndividualService {
    /**
     * Save a individual.
     *
     * @param individualDTO the entity to save.
     * @return the persisted entity.
     */
    IndividualDTO save(IndividualDTO individualDTO);

    /**
     * Partially updates a individual.
     *
     * @param individualDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IndividualDTO> partialUpdate(IndividualDTO individualDTO);

    /**
     * Get all the individuals.
     *
     * @return the list of entities.
     */
    List<IndividualDTO> findAll();

    /**
     * Get the "id" individual.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IndividualDTO> findOne(Long id);

    /**
     * Delete the "id" individual.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
