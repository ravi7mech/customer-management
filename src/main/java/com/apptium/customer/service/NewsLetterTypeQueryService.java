package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.NewsLetterType;
import com.apptium.customer.repository.NewsLetterTypeRepository;
import com.apptium.customer.service.criteria.NewsLetterTypeCriteria;
import com.apptium.customer.service.dto.NewsLetterTypeDTO;
import com.apptium.customer.service.mapper.NewsLetterTypeMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link NewsLetterType} entities in the database.
 * The main input is a {@link NewsLetterTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NewsLetterTypeDTO} or a {@link Page} of {@link NewsLetterTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NewsLetterTypeQueryService extends QueryService<NewsLetterType> {

    private final Logger log = LoggerFactory.getLogger(NewsLetterTypeQueryService.class);

    private final NewsLetterTypeRepository newsLetterTypeRepository;

    private final NewsLetterTypeMapper newsLetterTypeMapper;

    public NewsLetterTypeQueryService(NewsLetterTypeRepository newsLetterTypeRepository, NewsLetterTypeMapper newsLetterTypeMapper) {
        this.newsLetterTypeRepository = newsLetterTypeRepository;
        this.newsLetterTypeMapper = newsLetterTypeMapper;
    }

    /**
     * Return a {@link List} of {@link NewsLetterTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NewsLetterTypeDTO> findByCriteria(NewsLetterTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NewsLetterType> specification = createSpecification(criteria);
        return newsLetterTypeMapper.toDto(newsLetterTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NewsLetterTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NewsLetterTypeDTO> findByCriteria(NewsLetterTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NewsLetterType> specification = createSpecification(criteria);
        return newsLetterTypeRepository.findAll(specification, page).map(newsLetterTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NewsLetterTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NewsLetterType> specification = createSpecification(criteria);
        return newsLetterTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link NewsLetterTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NewsLetterType> createSpecification(NewsLetterTypeCriteria criteria) {
        Specification<NewsLetterType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NewsLetterType_.id));
            }
            if (criteria.getNewLetterType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNewLetterType(), NewsLetterType_.newLetterType));
            }
            if (criteria.getDisplayValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDisplayValue(), NewsLetterType_.displayValue));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), NewsLetterType_.description));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), NewsLetterType_.status));
            }
            if (criteria.getCustNewsLetterConfigId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustNewsLetterConfigId(),
                            root -> root.join(NewsLetterType_.custNewsLetterConfigs, JoinType.LEFT).get(CustNewsLetterConfig_.id)
                        )
                    );
            }
            if (criteria.getIndNewsLetterConfId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getIndNewsLetterConfId(),
                            root -> root.join(NewsLetterType_.indNewsLetterConfs, JoinType.LEFT).get(IndNewsLetterConf_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
