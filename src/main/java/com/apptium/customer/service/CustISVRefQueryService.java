package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.CustISVRef;
import com.apptium.customer.repository.CustISVRefRepository;
import com.apptium.customer.service.criteria.CustISVRefCriteria;
import com.apptium.customer.service.dto.CustISVRefDTO;
import com.apptium.customer.service.mapper.CustISVRefMapper;
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
 * Service for executing complex queries for {@link CustISVRef} entities in the database.
 * The main input is a {@link CustISVRefCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustISVRefDTO} or a {@link Page} of {@link CustISVRefDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustISVRefQueryService extends QueryService<CustISVRef> {

    private final Logger log = LoggerFactory.getLogger(CustISVRefQueryService.class);

    private final CustISVRefRepository custISVRefRepository;

    private final CustISVRefMapper custISVRefMapper;

    public CustISVRefQueryService(CustISVRefRepository custISVRefRepository, CustISVRefMapper custISVRefMapper) {
        this.custISVRefRepository = custISVRefRepository;
        this.custISVRefMapper = custISVRefMapper;
    }

    /**
     * Return a {@link List} of {@link CustISVRefDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustISVRefDTO> findByCriteria(CustISVRefCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustISVRef> specification = createSpecification(criteria);
        return custISVRefMapper.toDto(custISVRefRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustISVRefDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustISVRefDTO> findByCriteria(CustISVRefCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustISVRef> specification = createSpecification(criteria);
        return custISVRefRepository.findAll(specification, page).map(custISVRefMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustISVRefCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustISVRef> specification = createSpecification(criteria);
        return custISVRefRepository.count(specification);
    }

    /**
     * Function to convert {@link CustISVRefCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustISVRef> createSpecification(CustISVRefCriteria criteria) {
        Specification<CustISVRef> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustISVRef_.id));
            }
            if (criteria.getIsvId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsvId(), CustISVRef_.isvId));
            }
            if (criteria.getIsvName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIsvName(), CustISVRef_.isvName));
            }
            if (criteria.getIsvCustId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIsvCustId(), CustISVRef_.isvCustId));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCustomerId(), CustISVRef_.customerId));
            }
            if (criteria.getCustISVCharId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustISVCharId(),
                            root -> root.join(CustISVRef_.custISVChars, JoinType.LEFT).get(CustISVChar_.id)
                        )
                    );
            }
            if (criteria.getCustomerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomerId(),
                            root -> root.join(CustISVRef_.customer, JoinType.LEFT).get(Customer_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
