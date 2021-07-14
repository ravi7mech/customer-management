package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.CustChar;
import com.apptium.customer.repository.CustCharRepository;
import com.apptium.customer.service.criteria.CustCharCriteria;
import com.apptium.customer.service.dto.CustCharDTO;
import com.apptium.customer.service.mapper.CustCharMapper;
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
 * Service for executing complex queries for {@link CustChar} entities in the database.
 * The main input is a {@link CustCharCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustCharDTO} or a {@link Page} of {@link CustCharDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustCharQueryService extends QueryService<CustChar> {

    private final Logger log = LoggerFactory.getLogger(CustCharQueryService.class);

    private final CustCharRepository custCharRepository;

    private final CustCharMapper custCharMapper;

    public CustCharQueryService(CustCharRepository custCharRepository, CustCharMapper custCharMapper) {
        this.custCharRepository = custCharRepository;
        this.custCharMapper = custCharMapper;
    }

    /**
     * Return a {@link List} of {@link CustCharDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustCharDTO> findByCriteria(CustCharCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustChar> specification = createSpecification(criteria);
        return custCharMapper.toDto(custCharRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustCharDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustCharDTO> findByCriteria(CustCharCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustChar> specification = createSpecification(criteria);
        return custCharRepository.findAll(specification, page).map(custCharMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustCharCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustChar> specification = createSpecification(criteria);
        return custCharRepository.count(specification);
    }

    /**
     * Function to convert {@link CustCharCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustChar> createSpecification(CustCharCriteria criteria) {
        Specification<CustChar> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustChar_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CustChar_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), CustChar_.value));
            }
            if (criteria.getValueType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValueType(), CustChar_.valueType));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCustomerId(), CustChar_.customerId));
            }
            if (criteria.getCustomerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCustomerId(), root -> root.join(CustChar_.customer, JoinType.LEFT).get(Customer_.id))
                    );
            }
        }
        return specification;
    }
}
