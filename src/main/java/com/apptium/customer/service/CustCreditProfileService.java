package com.apptium.customer.service;

import com.apptium.customer.service.dto.CustCreditProfileDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.CustCreditProfile}.
 */
public interface CustCreditProfileService {
    /**
     * Save a custCreditProfile.
     *
     * @param custCreditProfileDTO the entity to save.
     * @return the persisted entity.
     */
    CustCreditProfileDTO save(CustCreditProfileDTO custCreditProfileDTO);

    /**
     * Partially updates a custCreditProfile.
     *
     * @param custCreditProfileDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustCreditProfileDTO> partialUpdate(CustCreditProfileDTO custCreditProfileDTO);

    /**
     * Get all the custCreditProfiles.
     *
     * @return the list of entities.
     */
    List<CustCreditProfileDTO> findAll();
    /**
     * Get all the CustCreditProfileDTO where Customer is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<CustCreditProfileDTO> findAllWhereCustomerIsNull();

    /**
     * Get the "id" custCreditProfile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustCreditProfileDTO> findOne(Long id);

    /**
     * Delete the "id" custCreditProfile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
