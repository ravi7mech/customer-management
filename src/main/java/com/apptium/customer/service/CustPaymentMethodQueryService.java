package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.CustPaymentMethod;
import com.apptium.customer.repository.CustPaymentMethodRepository;
import com.apptium.customer.service.criteria.CustPaymentMethodCriteria;
import com.apptium.customer.service.dto.CustPaymentMethodDTO;
import com.apptium.customer.service.mapper.CustPaymentMethodMapper;
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
 * Service for executing complex queries for {@link CustPaymentMethod} entities in the database.
 * The main input is a {@link CustPaymentMethodCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustPaymentMethodDTO} or a {@link Page} of {@link CustPaymentMethodDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustPaymentMethodQueryService extends QueryService<CustPaymentMethod> {

    private final Logger log = LoggerFactory.getLogger(CustPaymentMethodQueryService.class);

    private final CustPaymentMethodRepository custPaymentMethodRepository;

    private final CustPaymentMethodMapper custPaymentMethodMapper;

    public CustPaymentMethodQueryService(
        CustPaymentMethodRepository custPaymentMethodRepository,
        CustPaymentMethodMapper custPaymentMethodMapper
    ) {
        this.custPaymentMethodRepository = custPaymentMethodRepository;
        this.custPaymentMethodMapper = custPaymentMethodMapper;
    }

    /**
     * Return a {@link List} of {@link CustPaymentMethodDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustPaymentMethodDTO> findByCriteria(CustPaymentMethodCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustPaymentMethod> specification = createSpecification(criteria);
        return custPaymentMethodMapper.toDto(custPaymentMethodRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustPaymentMethodDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustPaymentMethodDTO> findByCriteria(CustPaymentMethodCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustPaymentMethod> specification = createSpecification(criteria);
        return custPaymentMethodRepository.findAll(specification, page).map(custPaymentMethodMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustPaymentMethodCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustPaymentMethod> specification = createSpecification(criteria);
        return custPaymentMethodRepository.count(specification);
    }

    /**
     * Function to convert {@link CustPaymentMethodCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustPaymentMethod> createSpecification(CustPaymentMethodCriteria criteria) {
        Specification<CustPaymentMethod> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustPaymentMethod_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CustPaymentMethod_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CustPaymentMethod_.description));
            }
            if (criteria.getPreferred() != null) {
                specification = specification.and(buildSpecification(criteria.getPreferred(), CustPaymentMethod_.preferred));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), CustPaymentMethod_.type));
            }
            if (criteria.getAuthorizationCode() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getAuthorizationCode(), CustPaymentMethod_.authorizationCode));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), CustPaymentMethod_.status));
            }
            if (criteria.getStatusDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatusDate(), CustPaymentMethod_.statusDate));
            }
            if (criteria.getDetails() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDetails(), CustPaymentMethod_.details));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCustomerId(), CustPaymentMethod_.customerId));
            }
            if (criteria.getBankCardTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBankCardTypeId(),
                            root -> root.join(CustPaymentMethod_.bankCardTypes, JoinType.LEFT).get(BankCardType_.id)
                        )
                    );
            }
            if (criteria.getCustBillingRefId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustBillingRefId(),
                            root -> root.join(CustPaymentMethod_.custBillingRef, JoinType.LEFT).get(CustBillingRef_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
