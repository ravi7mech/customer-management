package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.CustISVChar;
import com.apptium.customer.repository.CustISVCharRepository;
import com.apptium.customer.service.criteria.CustISVCharCriteria;
import com.apptium.customer.service.dto.CustISVCharDTO;
import com.apptium.customer.service.mapper.CustISVCharMapper;
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
 * Service for executing complex queries for {@link CustISVChar} entities in the database.
 * The main input is a {@link CustISVCharCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustISVCharDTO} or a {@link Page} of {@link CustISVCharDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustISVCharQueryService extends QueryService<CustISVChar> {

    private final Logger log = LoggerFactory.getLogger(CustISVCharQueryService.class);

    private final CustISVCharRepository custISVCharRepository;

    private final CustISVCharMapper custISVCharMapper;

    public CustISVCharQueryService(CustISVCharRepository custISVCharRepository, CustISVCharMapper custISVCharMapper) {
        this.custISVCharRepository = custISVCharRepository;
        this.custISVCharMapper = custISVCharMapper;
    }

    /**
     * Return a {@link List} of {@link CustISVCharDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustISVCharDTO> findByCriteria(CustISVCharCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustISVChar> specification = createSpecification(criteria);
        return custISVCharMapper.toDto(custISVCharRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustISVCharDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustISVCharDTO> findByCriteria(CustISVCharCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustISVChar> specification = createSpecification(criteria);
        return custISVCharRepository.findAll(specification, page).map(custISVCharMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustISVCharCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustISVChar> specification = createSpecification(criteria);
        return custISVCharRepository.count(specification);
    }

    /**
     * Function to convert {@link CustISVCharCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustISVChar> createSpecification(CustISVCharCriteria criteria) {
        Specification<CustISVChar> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustISVChar_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CustISVChar_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValue(), CustISVChar_.value));
            }
            if (criteria.getValueType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValueType(), CustISVChar_.valueType));
            }
            if (criteria.getCustIsvId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCustIsvId(), CustISVChar_.custIsvId));
            }
            if (criteria.getCustISVRefId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustISVRefId(),
                            root -> root.join(CustISVChar_.custISVRef, JoinType.LEFT).get(CustISVRef_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
