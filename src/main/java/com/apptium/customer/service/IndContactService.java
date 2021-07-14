package com.apptium.customer.service;

import com.apptium.customer.service.dto.IndContactDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.IndContact}.
 */
public interface IndContactService {
    /**
     * Save a indContact.
     *
     * @param indContactDTO the entity to save.
     * @return the persisted entity.
     */
    IndContactDTO save(IndContactDTO indContactDTO);

    /**
     * Partially updates a indContact.
     *
     * @param indContactDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IndContactDTO> partialUpdate(IndContactDTO indContactDTO);

    /**
     * Get all the indContacts.
     *
     * @return the list of entities.
     */
    List<IndContactDTO> findAll();

    /**
     * Get the "id" indContact.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IndContactDTO> findOne(Long id);

    /**
     * Delete the "id" indContact.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
