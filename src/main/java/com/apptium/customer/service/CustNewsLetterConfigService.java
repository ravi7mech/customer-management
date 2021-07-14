package com.apptium.customer.service;

import com.apptium.customer.service.dto.CustNewsLetterConfigDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.CustNewsLetterConfig}.
 */
public interface CustNewsLetterConfigService {
    /**
     * Save a custNewsLetterConfig.
     *
     * @param custNewsLetterConfigDTO the entity to save.
     * @return the persisted entity.
     */
    CustNewsLetterConfigDTO save(CustNewsLetterConfigDTO custNewsLetterConfigDTO);

    /**
     * Partially updates a custNewsLetterConfig.
     *
     * @param custNewsLetterConfigDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustNewsLetterConfigDTO> partialUpdate(CustNewsLetterConfigDTO custNewsLetterConfigDTO);

    /**
     * Get all the custNewsLetterConfigs.
     *
     * @return the list of entities.
     */
    List<CustNewsLetterConfigDTO> findAll();

    /**
     * Get the "id" custNewsLetterConfig.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustNewsLetterConfigDTO> findOne(Long id);

    /**
     * Delete the "id" custNewsLetterConfig.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
