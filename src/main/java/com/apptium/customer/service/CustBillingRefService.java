package com.apptium.customer.service;

import com.apptium.customer.service.dto.CustBillingRefDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.CustBillingRef}.
 */
public interface CustBillingRefService {
    /**
     * Save a custBillingRef.
     *
     * @param custBillingRefDTO the entity to save.
     * @return the persisted entity.
     */
    CustBillingRefDTO save(CustBillingRefDTO custBillingRefDTO);

    /**
     * Partially updates a custBillingRef.
     *
     * @param custBillingRefDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustBillingRefDTO> partialUpdate(CustBillingRefDTO custBillingRefDTO);

    /**
     * Get all the custBillingRefs.
     *
     * @return the list of entities.
     */
    List<CustBillingRefDTO> findAll();
    /**
     * Get all the CustBillingRefDTO where Customer is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<CustBillingRefDTO> findAllWhereCustomerIsNull();

    /**
     * Get the "id" custBillingRef.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustBillingRefDTO> findOne(Long id);

    /**
     * Delete the "id" custBillingRef.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
