package com.apptium.customer.service;

import com.apptium.customer.service.dto.IndAuditTrialDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.IndAuditTrial}.
 */
public interface IndAuditTrialService {
    /**
     * Save a indAuditTrial.
     *
     * @param indAuditTrialDTO the entity to save.
     * @return the persisted entity.
     */
    IndAuditTrialDTO save(IndAuditTrialDTO indAuditTrialDTO);

    /**
     * Partially updates a indAuditTrial.
     *
     * @param indAuditTrialDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IndAuditTrialDTO> partialUpdate(IndAuditTrialDTO indAuditTrialDTO);

    /**
     * Get all the indAuditTrials.
     *
     * @return the list of entities.
     */
    List<IndAuditTrialDTO> findAll();

    /**
     * Get the "id" indAuditTrial.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IndAuditTrialDTO> findOne(Long id);

    /**
     * Delete the "id" indAuditTrial.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
