package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.CustCreditProfile;
import com.apptium.customer.repository.CustCreditProfileRepository;
import com.apptium.customer.service.criteria.CustCreditProfileCriteria;
import com.apptium.customer.service.dto.CustCreditProfileDTO;
import com.apptium.customer.service.mapper.CustCreditProfileMapper;
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
 * Service for executing complex queries for {@link CustCreditProfile} entities in the database.
 * The main input is a {@link CustCreditProfileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustCreditProfileDTO} or a {@link Page} of {@link CustCreditProfileDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustCreditProfileQueryService extends QueryService<CustCreditProfile> {

    private final Logger log = LoggerFactory.getLogger(CustCreditProfileQueryService.class);

    private final CustCreditProfileRepository custCreditProfileRepository;

    private final CustCreditProfileMapper custCreditProfileMapper;

    public CustCreditProfileQueryService(
        CustCreditProfileRepository custCreditProfileRepository,
        CustCreditProfileMapper custCreditProfileMapper
    ) {
        this.custCreditProfileRepository = custCreditProfileRepository;
        this.custCreditProfileMapper = custCreditProfileMapper;
    }

    /**
     * Return a {@link List} of {@link CustCreditProfileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustCreditProfileDTO> findByCriteria(CustCreditProfileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustCreditProfile> specification = createSpecification(criteria);
        return custCreditProfileMapper.toDto(custCreditProfileRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustCreditProfileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustCreditProfileDTO> findByCriteria(CustCreditProfileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustCreditProfile> specification = createSpecification(criteria);
        return custCreditProfileRepository.findAll(specification, page).map(custCreditProfileMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustCreditProfileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustCreditProfile> specification = createSpecification(criteria);
        return custCreditProfileRepository.count(specification);
    }

    /**
     * Function to convert {@link CustCreditProfileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustCreditProfile> createSpecification(CustCreditProfileCriteria criteria) {
        Specification<CustCreditProfile> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustCreditProfile_.id));
            }
            if (criteria.getCustIdTypeOne() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustIdTypeOne(), CustCreditProfile_.custIdTypeOne));
            }
            if (criteria.getCustIdRefOne() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustIdRefOne(), CustCreditProfile_.custIdRefOne));
            }
            if (criteria.getCustIdTypeTwo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustIdTypeTwo(), CustCreditProfile_.custIdTypeTwo));
            }
            if (criteria.getCustIdRefTwo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustIdRefTwo(), CustCreditProfile_.custIdRefTwo));
            }
            if (criteria.getCreditCardNumber() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCreditCardNumber(), CustCreditProfile_.creditCardNumber));
            }
            if (criteria.getCreditProfileData() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCreditProfileData(), CustCreditProfile_.creditProfileData));
            }
            if (criteria.getCreditRiskRating() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getCreditRiskRating(), CustCreditProfile_.creditRiskRating));
            }
            if (criteria.getCreditRiskRatingDesc() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getCreditRiskRatingDesc(), CustCreditProfile_.creditRiskRatingDesc)
                    );
            }
            if (criteria.getCreditScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreditScore(), CustCreditProfile_.creditScore));
            }
            if (criteria.getValidUntil() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidUntil(), CustCreditProfile_.validUntil));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCustomerId(), CustCreditProfile_.customerId));
            }
            if (criteria.getCustomerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomerId(),
                            root -> root.join(CustCreditProfile_.customer, JoinType.LEFT).get(Customer_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
