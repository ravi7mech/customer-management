package com.apptium.customer.service;

import com.apptium.customer.service.dto.CustPaymentMethodDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.CustPaymentMethod}.
 */
public interface CustPaymentMethodService {
    /**
     * Save a custPaymentMethod.
     *
     * @param custPaymentMethodDTO the entity to save.
     * @return the persisted entity.
     */
    CustPaymentMethodDTO save(CustPaymentMethodDTO custPaymentMethodDTO);

    /**
     * Partially updates a custPaymentMethod.
     *
     * @param custPaymentMethodDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustPaymentMethodDTO> partialUpdate(CustPaymentMethodDTO custPaymentMethodDTO);

    /**
     * Get all the custPaymentMethods.
     *
     * @return the list of entities.
     */
    List<CustPaymentMethodDTO> findAll();

    /**
     * Get the "id" custPaymentMethod.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustPaymentMethodDTO> findOne(Long id);

    /**
     * Delete the "id" custPaymentMethod.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
