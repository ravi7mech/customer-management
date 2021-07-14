package com.apptium.customer.service;

import com.apptium.customer.service.dto.CustCharDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.CustChar}.
 */
public interface CustCharService {
    /**
     * Save a custChar.
     *
     * @param custCharDTO the entity to save.
     * @return the persisted entity.
     */
    CustCharDTO save(CustCharDTO custCharDTO);

    /**
     * Partially updates a custChar.
     *
     * @param custCharDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustCharDTO> partialUpdate(CustCharDTO custCharDTO);

    /**
     * Get all the custChars.
     *
     * @return the list of entities.
     */
    List<CustCharDTO> findAll();

    /**
     * Get the "id" custChar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustCharDTO> findOne(Long id);

    /**
     * Delete the "id" custChar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
