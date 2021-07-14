package com.apptium.customer.service;

import com.apptium.customer.service.dto.CustISVCharDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.CustISVChar}.
 */
public interface CustISVCharService {
    /**
     * Save a custISVChar.
     *
     * @param custISVCharDTO the entity to save.
     * @return the persisted entity.
     */
    CustISVCharDTO save(CustISVCharDTO custISVCharDTO);

    /**
     * Partially updates a custISVChar.
     *
     * @param custISVCharDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustISVCharDTO> partialUpdate(CustISVCharDTO custISVCharDTO);

    /**
     * Get all the custISVChars.
     *
     * @return the list of entities.
     */
    List<CustISVCharDTO> findAll();

    /**
     * Get the "id" custISVChar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustISVCharDTO> findOne(Long id);

    /**
     * Delete the "id" custISVChar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
