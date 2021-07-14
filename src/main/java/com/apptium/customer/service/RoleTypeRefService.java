package com.apptium.customer.service;

import com.apptium.customer.service.dto.RoleTypeRefDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.RoleTypeRef}.
 */
public interface RoleTypeRefService {
    /**
     * Save a roleTypeRef.
     *
     * @param roleTypeRefDTO the entity to save.
     * @return the persisted entity.
     */
    RoleTypeRefDTO save(RoleTypeRefDTO roleTypeRefDTO);

    /**
     * Partially updates a roleTypeRef.
     *
     * @param roleTypeRefDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RoleTypeRefDTO> partialUpdate(RoleTypeRefDTO roleTypeRefDTO);

    /**
     * Get all the roleTypeRefs.
     *
     * @return the list of entities.
     */
    List<RoleTypeRefDTO> findAll();

    /**
     * Get the "id" roleTypeRef.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RoleTypeRefDTO> findOne(Long id);

    /**
     * Delete the "id" roleTypeRef.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
