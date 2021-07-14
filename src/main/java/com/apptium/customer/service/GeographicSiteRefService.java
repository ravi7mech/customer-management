package com.apptium.customer.service;

import com.apptium.customer.service.dto.GeographicSiteRefDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.GeographicSiteRef}.
 */
public interface GeographicSiteRefService {
    /**
     * Save a geographicSiteRef.
     *
     * @param geographicSiteRefDTO the entity to save.
     * @return the persisted entity.
     */
    GeographicSiteRefDTO save(GeographicSiteRefDTO geographicSiteRefDTO);

    /**
     * Partially updates a geographicSiteRef.
     *
     * @param geographicSiteRefDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GeographicSiteRefDTO> partialUpdate(GeographicSiteRefDTO geographicSiteRefDTO);

    /**
     * Get all the geographicSiteRefs.
     *
     * @return the list of entities.
     */
    List<GeographicSiteRefDTO> findAll();
    /**
     * Get all the GeographicSiteRefDTO where CustContact is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<GeographicSiteRefDTO> findAllWhereCustContactIsNull();

    /**
     * Get the "id" geographicSiteRef.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GeographicSiteRefDTO> findOne(Long id);

    /**
     * Delete the "id" geographicSiteRef.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
