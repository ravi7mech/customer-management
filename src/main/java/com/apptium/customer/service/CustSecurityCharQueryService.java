package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.CustSecurityChar;
import com.apptium.customer.repository.CustSecurityCharRepository;
import com.apptium.customer.service.criteria.CustSecurityCharCriteria;
import com.apptium.customer.service.dto.CustSecurityCharDTO;
import com.apptium.customer.service.mapper.CustSecurityCharMapper;
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
 * Service for executing complex queries for {@link CustSecurityChar} entities in the database.
 * The main input is a {@link CustSecurityCharCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustSecurityCharDTO} or a {@link Page} of {@link CustSecurityCharDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustSecurityCharQueryService extends QueryService<CustSecurityChar> {

    private final Logger log = LoggerFactory.getLogger(CustSecurityCharQueryService.class);

    private final CustSecurityCharRepository custSecurityCharRepository;

    private final CustSecurityCharMapper custSecurityCharMapper;

    public CustSecurityCharQueryService(
        CustSecurityCharRepository custSecurityCharRepository,
        CustSecurityCharMapper custSecurityCharMapper
    ) {
        this.custSecurityCharRepository = custSecurityCharRepository;
        this.custSecurityCharMapper = custSecurityCharMapper;
    }

    /**
     * Return a {@link List} of {@link CustSecurityCharDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustSecurityCharDTO> findByCriteria(CustSecurityCharCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustSecurityChar> specification = createSpecification(criteria);
        return custSecurityCharMapper.toDto(custSecurityCharRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustSecurityCharDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustSecurityCharDTO> findByCriteria(CustSecurityCharCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustSecurityChar> specification = createSpecification(criteria);
        return custSecurityCharRepository.findAll(specification, page).map(custSecurityCharMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustSecurityCharCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustSecurityChar> specification = createSpecification(criteria);
        return custSecurityCharRepository.count(specification);
    }

    /**
     * Function to convert {@link CustSecurityCharCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustSecurityChar> createSpecification(CustSecurityCharCriteria criteria) {
        Specification<CustSecurityChar> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustSecurityChar_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CustSecurityChar_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), CustSecurityChar_.value));
            }
            if (criteria.getValueType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValueType(), CustSecurityChar_.valueType));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCustomerId(), CustSecurityChar_.customerId));
            }
            if (criteria.getCustomerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomerId(),
                            root -> root.join(CustSecurityChar_.customer, JoinType.LEFT).get(Customer_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
