package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.CustBillingRef;
import com.apptium.customer.repository.CustBillingRefRepository;
import com.apptium.customer.service.criteria.CustBillingRefCriteria;
import com.apptium.customer.service.dto.CustBillingRefDTO;
import com.apptium.customer.service.mapper.CustBillingRefMapper;
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
 * Service for executing complex queries for {@link CustBillingRef} entities in the database.
 * The main input is a {@link CustBillingRefCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustBillingRefDTO} or a {@link Page} of {@link CustBillingRefDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustBillingRefQueryService extends QueryService<CustBillingRef> {

    private final Logger log = LoggerFactory.getLogger(CustBillingRefQueryService.class);

    private final CustBillingRefRepository custBillingRefRepository;

    private final CustBillingRefMapper custBillingRefMapper;

    public CustBillingRefQueryService(CustBillingRefRepository custBillingRefRepository, CustBillingRefMapper custBillingRefMapper) {
        this.custBillingRefRepository = custBillingRefRepository;
        this.custBillingRefMapper = custBillingRefMapper;
    }

    /**
     * Return a {@link List} of {@link CustBillingRefDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustBillingRefDTO> findByCriteria(CustBillingRefCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustBillingRef> specification = createSpecification(criteria);
        return custBillingRefMapper.toDto(custBillingRefRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustBillingRefDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustBillingRefDTO> findByCriteria(CustBillingRefCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustBillingRef> specification = createSpecification(criteria);
        return custBillingRefRepository.findAll(specification, page).map(custBillingRefMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustBillingRefCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustBillingRef> specification = createSpecification(criteria);
        return custBillingRefRepository.count(specification);
    }

    /**
     * Function to convert {@link CustBillingRefCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustBillingRef> createSpecification(CustBillingRefCriteria criteria) {
        Specification<CustBillingRef> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustBillingRef_.id));
            }
            if (criteria.getAmountDue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmountDue(), CustBillingRef_.amountDue));
            }
            if (criteria.getBillDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBillDate(), CustBillingRef_.billDate));
            }
            if (criteria.getBillNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBillNo(), CustBillingRef_.billNo));
            }
            if (criteria.getBillingPeriod() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBillingPeriod(), CustBillingRef_.billingPeriod));
            }
            if (criteria.getCategory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategory(), CustBillingRef_.category));
            }
            if (criteria.getHref() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHref(), CustBillingRef_.href));
            }
            if (criteria.getLastUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedDate(), CustBillingRef_.lastUpdatedDate));
            }
            if (criteria.getNextUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNextUpdatedDate(), CustBillingRef_.nextUpdatedDate));
            }
            if (criteria.getPaymentDueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaymentDueDate(), CustBillingRef_.paymentDueDate));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getState(), CustBillingRef_.state));
            }
            if (criteria.getTaxExcludedAmount() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTaxExcludedAmount(), CustBillingRef_.taxExcludedAmount));
            }
            if (criteria.getTaxIncludedAmount() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTaxIncludedAmount(), CustBillingRef_.taxIncludedAmount));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCustomerId(), CustBillingRef_.customerId));
            }
            if (criteria.getCustPaymentMethodId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustPaymentMethodId(),
                            root -> root.join(CustBillingRef_.custPaymentMethods, JoinType.LEFT).get(CustPaymentMethod_.id)
                        )
                    );
            }
            if (criteria.getCustomerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomerId(),
                            root -> root.join(CustBillingRef_.customer, JoinType.LEFT).get(Customer_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
