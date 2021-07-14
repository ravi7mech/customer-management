package com.apptium.customer.service;

import com.apptium.customer.service.dto.AutoPayDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.AutoPay}.
 */
public interface AutoPayService {
    /**
     * Save a autoPay.
     *
     * @param autoPayDTO the entity to save.
     * @return the persisted entity.
     */
    AutoPayDTO save(AutoPayDTO autoPayDTO);

    /**
     * Partially updates a autoPay.
     *
     * @param autoPayDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AutoPayDTO> partialUpdate(AutoPayDTO autoPayDTO);

    /**
     * Get all the autoPays.
     *
     * @return the list of entities.
     */
    List<AutoPayDTO> findAll();

    /**
     * Get the "id" autoPay.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AutoPayDTO> findOne(Long id);

    /**
     * Delete the "id" autoPay.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
