package com.apptium.customer.service;

import com.apptium.customer.service.dto.IndActivationDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.IndActivation}.
 */
public interface IndActivationService {
    /**
     * Save a indActivation.
     *
     * @param indActivationDTO the entity to save.
     * @return the persisted entity.
     */
    IndActivationDTO save(IndActivationDTO indActivationDTO);

    /**
     * Partially updates a indActivation.
     *
     * @param indActivationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IndActivationDTO> partialUpdate(IndActivationDTO indActivationDTO);

    /**
     * Get all the indActivations.
     *
     * @return the list of entities.
     */
    List<IndActivationDTO> findAll();
    /**
     * Get all the IndActivationDTO where Individual is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<IndActivationDTO> findAllWhereIndividualIsNull();

    /**
     * Get the "id" indActivation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IndActivationDTO> findOne(Long id);

    /**
     * Delete the "id" indActivation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
