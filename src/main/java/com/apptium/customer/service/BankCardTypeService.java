package com.apptium.customer.service;

import com.apptium.customer.service.dto.BankCardTypeDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.BankCardType}.
 */
public interface BankCardTypeService {
    /**
     * Save a bankCardType.
     *
     * @param bankCardTypeDTO the entity to save.
     * @return the persisted entity.
     */
    BankCardTypeDTO save(BankCardTypeDTO bankCardTypeDTO);

    /**
     * Partially updates a bankCardType.
     *
     * @param bankCardTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BankCardTypeDTO> partialUpdate(BankCardTypeDTO bankCardTypeDTO);

    /**
     * Get all the bankCardTypes.
     *
     * @return the list of entities.
     */
    List<BankCardTypeDTO> findAll();

    /**
     * Get the "id" bankCardType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BankCardTypeDTO> findOne(Long id);

    /**
     * Delete the "id" bankCardType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
