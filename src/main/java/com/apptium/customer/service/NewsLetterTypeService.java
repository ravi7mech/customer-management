package com.apptium.customer.service;

import com.apptium.customer.service.dto.NewsLetterTypeDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.apptium.customer.domain.NewsLetterType}.
 */
public interface NewsLetterTypeService {
    /**
     * Save a newsLetterType.
     *
     * @param newsLetterTypeDTO the entity to save.
     * @return the persisted entity.
     */
    NewsLetterTypeDTO save(NewsLetterTypeDTO newsLetterTypeDTO);

    /**
     * Partially updates a newsLetterType.
     *
     * @param newsLetterTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NewsLetterTypeDTO> partialUpdate(NewsLetterTypeDTO newsLetterTypeDTO);

    /**
     * Get all the newsLetterTypes.
     *
     * @return the list of entities.
     */
    List<NewsLetterTypeDTO> findAll();

    /**
     * Get the "id" newsLetterType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NewsLetterTypeDTO> findOne(Long id);

    /**
     * Delete the "id" newsLetterType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
