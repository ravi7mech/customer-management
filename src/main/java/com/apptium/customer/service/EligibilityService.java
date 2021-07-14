package com.apptium.customer.service;

import com.apptium.customer.service.dto.EligibilityDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.Eligibility}.
 */
public interface EligibilityService {
    /**
     * Save a eligibility.
     *
     * @param eligibilityDTO the entity to save.
     * @return the persisted entity.
     */
    EligibilityDTO save(EligibilityDTO eligibilityDTO);

    /**
     * Partially updates a eligibility.
     *
     * @param eligibilityDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EligibilityDTO> partialUpdate(EligibilityDTO eligibilityDTO);

    /**
     * Get all the eligibilities.
     *
     * @return the list of entities.
     */
    List<EligibilityDTO> findAll();

    /**
     * Get the "id" eligibility.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EligibilityDTO> findOne(Long id);

    /**
     * Delete the "id" eligibility.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
