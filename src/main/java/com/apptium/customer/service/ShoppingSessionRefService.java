package com.apptium.customer.service;

import com.apptium.customer.service.dto.ShoppingSessionRefDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.ShoppingSessionRef}.
 */
public interface ShoppingSessionRefService {
    /**
     * Save a shoppingSessionRef.
     *
     * @param shoppingSessionRefDTO the entity to save.
     * @return the persisted entity.
     */
    ShoppingSessionRefDTO save(ShoppingSessionRefDTO shoppingSessionRefDTO);

    /**
     * Partially updates a shoppingSessionRef.
     *
     * @param shoppingSessionRefDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ShoppingSessionRefDTO> partialUpdate(ShoppingSessionRefDTO shoppingSessionRefDTO);

    /**
     * Get all the shoppingSessionRefs.
     *
     * @return the list of entities.
     */
    List<ShoppingSessionRefDTO> findAll();

    /**
     * Get the "id" shoppingSessionRef.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShoppingSessionRefDTO> findOne(Long id);

    /**
     * Delete the "id" shoppingSessionRef.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
