package com.apptium.customer.service;

import com.apptium.customer.service.dto.CustContactCharDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.CustContactChar}.
 */
public interface CustContactCharService {
    /**
     * Save a custContactChar.
     *
     * @param custContactCharDTO the entity to save.
     * @return the persisted entity.
     */
    CustContactCharDTO save(CustContactCharDTO custContactCharDTO);

    /**
     * Partially updates a custContactChar.
     *
     * @param custContactCharDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustContactCharDTO> partialUpdate(CustContactCharDTO custContactCharDTO);

    /**
     * Get all the custContactChars.
     *
     * @return the list of entities.
     */
    List<CustContactCharDTO> findAll();

    /**
     * Get the "id" custContactChar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustContactCharDTO> findOne(Long id);

    /**
     * Delete the "id" custContactChar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
