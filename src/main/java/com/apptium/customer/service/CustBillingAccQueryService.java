package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.CustBillingAcc;
import com.apptium.customer.repository.CustBillingAccRepository;
import com.apptium.customer.service.criteria.CustBillingAccCriteria;
import com.apptium.customer.service.dto.CustBillingAccDTO;
import com.apptium.customer.service.mapper.CustBillingAccMapper;
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
 * Service for executing complex queries for {@link CustBillingAcc} entities in the database.
 * The main input is a {@link CustBillingAccCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustBillingAccDTO} or a {@link Page} of {@link CustBillingAccDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustBillingAccQueryService extends QueryService<CustBillingAcc> {

    private final Logger log = LoggerFactory.getLogger(CustBillingAccQueryService.class);

    private final CustBillingAccRepository custBillingAccRepository;

    private final CustBillingAccMapper custBillingAccMapper;

    public CustBillingAccQueryService(CustBillingAccRepository custBillingAccRepository, CustBillingAccMapper custBillingAccMapper) {
        this.custBillingAccRepository = custBillingAccRepository;
        this.custBillingAccMapper = custBillingAccMapper;
    }

    /**
     * Return a {@link List} of {@link CustBillingAccDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustBillingAccDTO> findByCriteria(CustBillingAccCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustBillingAcc> specification = createSpecification(criteria);
        return custBillingAccMapper.toDto(custBillingAccRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustBillingAccDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustBillingAccDTO> findByCriteria(CustBillingAccCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustBillingAcc> specification = createSpecification(criteria);
        return custBillingAccRepository.findAll(specification, page).map(custBillingAccMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustBillingAccCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustBillingAcc> specification = createSpecification(criteria);
        return custBillingAccRepository.count(specification);
    }

    /**
     * Function to convert {@link CustBillingAccCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustBillingAcc> createSpecification(CustBillingAccCriteria criteria) {
        Specification<CustBillingAcc> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustBillingAcc_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CustBillingAcc_.name));
            }
            if (criteria.getHref() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHref(), CustBillingAcc_.href));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), CustBillingAcc_.status));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CustBillingAcc_.description));
            }
            if (criteria.getBillingAccountNumber() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getBillingAccountNumber(), CustBillingAcc_.billingAccountNumber));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCustomerId(), CustBillingAcc_.customerId));
            }
            if (criteria.getCurrencyCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrencyCode(), CustBillingAcc_.currencyCode));
            }
            if (criteria.getCustomerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomerId(),
                            root -> root.join(CustBillingAcc_.customer, JoinType.LEFT).get(Customer_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
