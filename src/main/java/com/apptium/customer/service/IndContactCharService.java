package com.apptium.customer.service;

import com.apptium.customer.service.dto.IndContactCharDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.IndContactChar}.
 */
public interface IndContactCharService {
    /**
     * Save a indContactChar.
     *
     * @param indContactCharDTO the entity to save.
     * @return the persisted entity.
     */
    IndContactCharDTO save(IndContactCharDTO indContactCharDTO);

    /**
     * Partially updates a indContactChar.
     *
     * @param indContactCharDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IndContactCharDTO> partialUpdate(IndContactCharDTO indContactCharDTO);

    /**
     * Get all the indContactChars.
     *
     * @return the list of entities.
     */
    List<IndContactCharDTO> findAll();

    /**
     * Get the "id" indContactChar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IndContactCharDTO> findOne(Long id);

    /**
     * Delete the "id" indContactChar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
