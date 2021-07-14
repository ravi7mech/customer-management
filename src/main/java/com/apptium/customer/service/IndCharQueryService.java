package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.IndChar;
import com.apptium.customer.repository.IndCharRepository;
import com.apptium.customer.service.criteria.IndCharCriteria;
import com.apptium.customer.service.dto.IndCharDTO;
import com.apptium.customer.service.mapper.IndCharMapper;
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
 * Service for executing complex queries for {@link IndChar} entities in the database.
 * The main input is a {@link IndCharCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link IndCharDTO} or a {@link Page} of {@link IndCharDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IndCharQueryService extends QueryService<IndChar> {

    private final Logger log = LoggerFactory.getLogger(IndCharQueryService.class);

    private final IndCharRepository indCharRepository;

    private final IndCharMapper indCharMapper;

    public IndCharQueryService(IndCharRepository indCharRepository, IndCharMapper indCharMapper) {
        this.indCharRepository = indCharRepository;
        this.indCharMapper = indCharMapper;
    }

    /**
     * Return a {@link List} of {@link IndCharDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<IndCharDTO> findByCriteria(IndCharCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<IndChar> specification = createSpecification(criteria);
        return indCharMapper.toDto(indCharRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link IndCharDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<IndCharDTO> findByCriteria(IndCharCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<IndChar> specification = createSpecification(criteria);
        return indCharRepository.findAll(specification, page).map(indCharMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IndCharCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<IndChar> specification = createSpecification(criteria);
        return indCharRepository.count(specification);
    }

    /**
     * Function to convert {@link IndCharCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<IndChar> createSpecification(IndCharCriteria criteria) {
        Specification<IndChar> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), IndChar_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), IndChar_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildSpecification(criteria.getValue(), IndChar_.value));
            }
            if (criteria.getValueType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValueType(), IndChar_.valueType));
            }
            if (criteria.getIndividualId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIndividualId(), IndChar_.individualId));
            }
            if (criteria.getIndividualId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getIndividualId(),
                            root -> root.join(IndChar_.individual, JoinType.LEFT).get(Individual_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
