package com.apptium.customer.service;

import com.apptium.customer.service.dto.CreditCheckVerificationDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.CreditCheckVerification}.
 */
public interface CreditCheckVerificationService {
    /**
     * Save a creditCheckVerification.
     *
     * @param creditCheckVerificationDTO the entity to save.
     * @return the persisted entity.
     */
    CreditCheckVerificationDTO save(CreditCheckVerificationDTO creditCheckVerificationDTO);

    /**
     * Partially updates a creditCheckVerification.
     *
     * @param creditCheckVerificationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CreditCheckVerificationDTO> partialUpdate(CreditCheckVerificationDTO creditCheckVerificationDTO);

    /**
     * Get all the creditCheckVerifications.
     *
     * @return the list of entities.
     */
    List<CreditCheckVerificationDTO> findAll();

    /**
     * Get the "id" creditCheckVerification.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CreditCheckVerificationDTO> findOne(Long id);

    /**
     * Delete the "id" creditCheckVerification.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
