package com.apptium.customer.service;

import com.apptium.customer.service.dto.CustRelPartyDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.CustRelParty}.
 */
public interface CustRelPartyService {
    /**
     * Save a custRelParty.
     *
     * @param custRelPartyDTO the entity to save.
     * @return the persisted entity.
     */
    CustRelPartyDTO save(CustRelPartyDTO custRelPartyDTO);

    /**
     * Partially updates a custRelParty.
     *
     * @param custRelPartyDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustRelPartyDTO> partialUpdate(CustRelPartyDTO custRelPartyDTO);

    /**
     * Get all the custRelParties.
     *
     * @return the list of entities.
     */
    List<CustRelPartyDTO> findAll();

    /**
     * Get the "id" custRelParty.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustRelPartyDTO> findOne(Long id);

    /**
     * Delete the "id" custRelParty.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
