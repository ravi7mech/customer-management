package com.apptium.customer.service;

import com.apptium.customer.service.dto.CustISVRefDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.CustISVRef}.
 */
public interface CustISVRefService {
    /**
     * Save a custISVRef.
     *
     * @param custISVRefDTO the entity to save.
     * @return the persisted entity.
     */
    CustISVRefDTO save(CustISVRefDTO custISVRefDTO);

    /**
     * Partially updates a custISVRef.
     *
     * @param custISVRefDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustISVRefDTO> partialUpdate(CustISVRefDTO custISVRefDTO);

    /**
     * Get all the custISVRefs.
     *
     * @return the list of entities.
     */
    List<CustISVRefDTO> findAll();

    /**
     * Get the "id" custISVRef.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustISVRefDTO> findOne(Long id);

    /**
     * Delete the "id" custISVRef.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
