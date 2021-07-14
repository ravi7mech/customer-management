package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.Eligibility;
import com.apptium.customer.repository.EligibilityRepository;
import com.apptium.customer.service.criteria.EligibilityCriteria;
import com.apptium.customer.service.dto.EligibilityDTO;
import com.apptium.customer.service.mapper.EligibilityMapper;
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
 * Service for executing complex queries for {@link Eligibility} entities in the database.
 * The main input is a {@link EligibilityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EligibilityDTO} or a {@link Page} of {@link EligibilityDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EligibilityQueryService extends QueryService<Eligibility> {

    private final Logger log = LoggerFactory.getLogger(EligibilityQueryService.class);

    private final EligibilityRepository eligibilityRepository;

    private final EligibilityMapper eligibilityMapper;

    public EligibilityQueryService(EligibilityRepository eligibilityRepository, EligibilityMapper eligibilityMapper) {
        this.eligibilityRepository = eligibilityRepository;
        this.eligibilityMapper = eligibilityMapper;
    }

    /**
     * Return a {@link List} of {@link EligibilityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EligibilityDTO> findByCriteria(EligibilityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Eligibility> specification = createSpecification(criteria);
        return eligibilityMapper.toDto(eligibilityRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EligibilityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EligibilityDTO> findByCriteria(EligibilityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Eligibility> specification = createSpecification(criteria);
        return eligibilityRepository.findAll(specification, page).map(eligibilityMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EligibilityCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Eligibility> specification = createSpecification(criteria);
        return eligibilityRepository.count(specification);
    }

    /**
     * Function to convert {@link EligibilityCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Eligibility> createSpecification(EligibilityCriteria criteria) {
        Specification<Eligibility> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Eligibility_.id));
            }
            if (criteria.getNoOfLines() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoOfLines(), Eligibility_.noOfLines));
            }
            if (criteria.getCreditAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreditAmount(), Eligibility_.creditAmount));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Eligibility_.description));
            }
            if (criteria.getIsEligiblePaylater() != null) {
                specification = specification.and(buildSpecification(criteria.getIsEligiblePaylater(), Eligibility_.isEligiblePaylater));
            }
        }
        return specification;
    }
}
