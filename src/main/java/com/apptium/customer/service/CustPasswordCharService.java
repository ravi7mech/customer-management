package com.apptium.customer.service;

import com.apptium.customer.service.dto.CustPasswordCharDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.CustPasswordChar}.
 */
public interface CustPasswordCharService {
    /**
     * Save a custPasswordChar.
     *
     * @param custPasswordCharDTO the entity to save.
     * @return the persisted entity.
     */
    CustPasswordCharDTO save(CustPasswordCharDTO custPasswordCharDTO);

    /**
     * Partially updates a custPasswordChar.
     *
     * @param custPasswordCharDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustPasswordCharDTO> partialUpdate(CustPasswordCharDTO custPasswordCharDTO);

    /**
     * Get all the custPasswordChars.
     *
     * @return the list of entities.
     */
    List<CustPasswordCharDTO> findAll();

    /**
     * Get the "id" custPasswordChar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustPasswordCharDTO> findOne(Long id);

    /**
     * Delete the "id" custPasswordChar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
