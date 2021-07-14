package com.apptium.customer.service;

import com.apptium.customer.service.dto.CustContactDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.CustContact}.
 */
public interface CustContactService {
    /**
     * Save a custContact.
     *
     * @param custContactDTO the entity to save.
     * @return the persisted entity.
     */
    CustContactDTO save(CustContactDTO custContactDTO);

    /**
     * Partially updates a custContact.
     *
     * @param custContactDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustContactDTO> partialUpdate(CustContactDTO custContactDTO);

    /**
     * Get all the custContacts.
     *
     * @return the list of entities.
     */
    List<CustContactDTO> findAll();

    /**
     * Get the "id" custContact.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustContactDTO> findOne(Long id);

    /**
     * Delete the "id" custContact.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
