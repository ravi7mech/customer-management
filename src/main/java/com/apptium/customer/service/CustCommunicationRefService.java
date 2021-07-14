package com.apptium.customer.service;

import com.apptium.customer.service.dto.CustCommunicationRefDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.CustCommunicationRef}.
 */
public interface CustCommunicationRefService {
    /**
     * Save a custCommunicationRef.
     *
     * @param custCommunicationRefDTO the entity to save.
     * @return the persisted entity.
     */
    CustCommunicationRefDTO save(CustCommunicationRefDTO custCommunicationRefDTO);

    /**
     * Partially updates a custCommunicationRef.
     *
     * @param custCommunicationRefDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustCommunicationRefDTO> partialUpdate(CustCommunicationRefDTO custCommunicationRefDTO);

    /**
     * Get all the custCommunicationRefs.
     *
     * @return the list of entities.
     */
    List<CustCommunicationRefDTO> findAll();

    /**
     * Get the "id" custCommunicationRef.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustCommunicationRefDTO> findOne(Long id);

    /**
     * Delete the "id" custCommunicationRef.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
