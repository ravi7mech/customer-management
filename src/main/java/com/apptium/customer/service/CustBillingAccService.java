package com.apptium.customer.service;

import com.apptium.customer.service.dto.CustBillingAccDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.CustBillingAcc}.
 */
public interface CustBillingAccService {
    /**
     * Save a custBillingAcc.
     *
     * @param custBillingAccDTO the entity to save.
     * @return the persisted entity.
     */
    CustBillingAccDTO save(CustBillingAccDTO custBillingAccDTO);

    /**
     * Partially updates a custBillingAcc.
     *
     * @param custBillingAccDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustBillingAccDTO> partialUpdate(CustBillingAccDTO custBillingAccDTO);

    /**
     * Get all the custBillingAccs.
     *
     * @return the list of entities.
     */
    List<CustBillingAccDTO> findAll();
    /**
     * Get all the CustBillingAccDTO where Customer is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<CustBillingAccDTO> findAllWhereCustomerIsNull();

    /**
     * Get the "id" custBillingAcc.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustBillingAccDTO> findOne(Long id);

    /**
     * Delete the "id" custBillingAcc.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
