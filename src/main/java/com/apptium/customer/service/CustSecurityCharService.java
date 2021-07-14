package com.apptium.customer.service;

import com.apptium.customer.service.dto.CustSecurityCharDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.CustSecurityChar}.
 */
public interface CustSecurityCharService {
    /**
     * Save a custSecurityChar.
     *
     * @param custSecurityCharDTO the entity to save.
     * @return the persisted entity.
     */
    CustSecurityCharDTO save(CustSecurityCharDTO custSecurityCharDTO);

    /**
     * Partially updates a custSecurityChar.
     *
     * @param custSecurityCharDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustSecurityCharDTO> partialUpdate(CustSecurityCharDTO custSecurityCharDTO);

    /**
     * Get all the custSecurityChars.
     *
     * @return the list of entities.
     */
    List<CustSecurityCharDTO> findAll();

    /**
     * Get the "id" custSecurityChar.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustSecurityCharDTO> findOne(Long id);

    /**
     * Delete the "id" custSecurityChar.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
