package com.apptium.customer.service;

import com.apptium.customer.service.dto.IndNewsLetterConfDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.IndNewsLetterConf}.
 */
public interface IndNewsLetterConfService {
    /**
     * Save a indNewsLetterConf.
     *
     * @param indNewsLetterConfDTO the entity to save.
     * @return the persisted entity.
     */
    IndNewsLetterConfDTO save(IndNewsLetterConfDTO indNewsLetterConfDTO);

    /**
     * Partially updates a indNewsLetterConf.
     *
     * @param indNewsLetterConfDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IndNewsLetterConfDTO> partialUpdate(IndNewsLetterConfDTO indNewsLetterConfDTO);

    /**
     * Get all the indNewsLetterConfs.
     *
     * @return the list of entities.
     */
    List<IndNewsLetterConfDTO> findAll();
    /**
     * Get all the IndNewsLetterConfDTO where Individual is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<IndNewsLetterConfDTO> findAllWhereIndividualIsNull();

    /**
     * Get the "id" indNewsLetterConf.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IndNewsLetterConfDTO> findOne(Long id);

    /**
     * Delete the "id" indNewsLetterConf.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
