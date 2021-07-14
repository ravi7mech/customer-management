package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.IndNewsLetterConf;
import com.apptium.customer.repository.IndNewsLetterConfRepository;
import com.apptium.customer.service.criteria.IndNewsLetterConfCriteria;
import com.apptium.customer.service.dto.IndNewsLetterConfDTO;
import com.apptium.customer.service.mapper.IndNewsLetterConfMapper;
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
 * Service for executing complex queries for {@link IndNewsLetterConf} entities in the database.
 * The main input is a {@link IndNewsLetterConfCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link IndNewsLetterConfDTO} or a {@link Page} of {@link IndNewsLetterConfDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IndNewsLetterConfQueryService extends QueryService<IndNewsLetterConf> {

    private final Logger log = LoggerFactory.getLogger(IndNewsLetterConfQueryService.class);

    private final IndNewsLetterConfRepository indNewsLetterConfRepository;

    private final IndNewsLetterConfMapper indNewsLetterConfMapper;

    public IndNewsLetterConfQueryService(
        IndNewsLetterConfRepository indNewsLetterConfRepository,
        IndNewsLetterConfMapper indNewsLetterConfMapper
    ) {
        this.indNewsLetterConfRepository = indNewsLetterConfRepository;
        this.indNewsLetterConfMapper = indNewsLetterConfMapper;
    }

    /**
     * Return a {@link List} of {@link IndNewsLetterConfDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<IndNewsLetterConfDTO> findByCriteria(IndNewsLetterConfCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<IndNewsLetterConf> specification = createSpecification(criteria);
        return indNewsLetterConfMapper.toDto(indNewsLetterConfRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link IndNewsLetterConfDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<IndNewsLetterConfDTO> findByCriteria(IndNewsLetterConfCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<IndNewsLetterConf> specification = createSpecification(criteria);
        return indNewsLetterConfRepository.findAll(specification, page).map(indNewsLetterConfMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IndNewsLetterConfCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<IndNewsLetterConf> specification = createSpecification(criteria);
        return indNewsLetterConfRepository.count(specification);
    }

    /**
     * Function to convert {@link IndNewsLetterConfCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<IndNewsLetterConf> createSpecification(IndNewsLetterConfCriteria criteria) {
        Specification<IndNewsLetterConf> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), IndNewsLetterConf_.id));
            }
            if (criteria.getNewLetterTypeId() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getNewLetterTypeId(), IndNewsLetterConf_.newLetterTypeId));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), IndNewsLetterConf_.value));
            }
            if (criteria.getIndividualId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIndividualId(), IndNewsLetterConf_.individualId));
            }
            if (criteria.getIndividualId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getIndividualId(),
                            root -> root.join(IndNewsLetterConf_.individual, JoinType.LEFT).get(Individual_.id)
                        )
                    );
            }
            if (criteria.getNewsLetterTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNewsLetterTypeId(),
                            root -> root.join(IndNewsLetterConf_.newsLetterType, JoinType.LEFT).get(NewsLetterType_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
