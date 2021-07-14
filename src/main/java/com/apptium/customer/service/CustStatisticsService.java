package com.apptium.customer.service;

import com.apptium.customer.service.dto.CustStatisticsDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.CustStatistics}.
 */
public interface CustStatisticsService {
    /**
     * Save a custStatistics.
     *
     * @param custStatisticsDTO the entity to save.
     * @return the persisted entity.
     */
    CustStatisticsDTO save(CustStatisticsDTO custStatisticsDTO);

    /**
     * Partially updates a custStatistics.
     *
     * @param custStatisticsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CustStatisticsDTO> partialUpdate(CustStatisticsDTO custStatisticsDTO);

    /**
     * Get all the custStatistics.
     *
     * @return the list of entities.
     */
    List<CustStatisticsDTO> findAll();

    /**
     * Get the "id" custStatistics.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CustStatisticsDTO> findOne(Long id);

    /**
     * Delete the "id" custStatistics.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
