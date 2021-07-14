package com.apptium.customer.service;

import com.apptium.customer.service.dto.IndCharDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.IndChar}.
 */
public interface IndCharService {
    /**
     * Save a indChar.
     *
     * @param indCharDTO the entity to save.
     * @return the persisted entity.
     */
    IndCharDTO save(IndCharDTO indCharDTO);

    /**
     * Partially updates a indChar.
     *
     * @param indCharDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IndCharDTO> partialUpdate(IndCharDTO indCharDTO);

    /**
     * Get all the indChars.
     *
     * @return the list of entities.
     */
    List<IndCharDTO> findAll();

    /**
     * Get the "id" indChar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IndCharDTO> findOne(Long id);

    /**
     * Delete the "id" indChar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
