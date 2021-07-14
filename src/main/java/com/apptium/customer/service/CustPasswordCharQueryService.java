package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.CustPasswordChar;
import com.apptium.customer.repository.CustPasswordCharRepository;
import com.apptium.customer.service.criteria.CustPasswordCharCriteria;
import com.apptium.customer.service.dto.CustPasswordCharDTO;
import com.apptium.customer.service.mapper.CustPasswordCharMapper;
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
 * Service for executing complex queries for {@link CustPasswordChar} entities in the database.
 * The main input is a {@link CustPasswordCharCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustPasswordCharDTO} or a {@link Page} of {@link CustPasswordCharDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustPasswordCharQueryService extends QueryService<CustPasswordChar> {

    private final Logger log = LoggerFactory.getLogger(CustPasswordCharQueryService.class);

    private final CustPasswordCharRepository custPasswordCharRepository;

    private final CustPasswordCharMapper custPasswordCharMapper;

    public CustPasswordCharQueryService(
        CustPasswordCharRepository custPasswordCharRepository,
        CustPasswordCharMapper custPasswordCharMapper
    ) {
        this.custPasswordCharRepository = custPasswordCharRepository;
        this.custPasswordCharMapper = custPasswordCharMapper;
    }

    /**
     * Return a {@link List} of {@link CustPasswordCharDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustPasswordCharDTO> findByCriteria(CustPasswordCharCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustPasswordChar> specification = createSpecification(criteria);
        return custPasswordCharMapper.toDto(custPasswordCharRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustPasswordCharDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustPasswordCharDTO> findByCriteria(CustPasswordCharCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustPasswordChar> specification = createSpecification(criteria);
        return custPasswordCharRepository.findAll(specification, page).map(custPasswordCharMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustPasswordCharCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustPasswordChar> specification = createSpecification(criteria);
        return custPasswordCharRepository.count(specification);
    }

    /**
     * Function to convert {@link CustPasswordCharCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustPasswordChar> createSpecification(CustPasswordCharCriteria criteria) {
        Specification<CustPasswordChar> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustPasswordChar_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CustPasswordChar_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), CustPasswordChar_.value));
            }
            if (criteria.getValueType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValueType(), CustPasswordChar_.valueType));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCustomerId(), CustPasswordChar_.customerId));
            }
            if (criteria.getCustomerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomerId(),
                            root -> root.join(CustPasswordChar_.customer, JoinType.LEFT).get(Customer_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
