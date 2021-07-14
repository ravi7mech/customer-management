package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.CreditCheckVerification;
import com.apptium.customer.repository.CreditCheckVerificationRepository;
import com.apptium.customer.service.criteria.CreditCheckVerificationCriteria;
import com.apptium.customer.service.dto.CreditCheckVerificationDTO;
import com.apptium.customer.service.mapper.CreditCheckVerificationMapper;
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
 * Service for executing complex queries for {@link CreditCheckVerification} entities in the database.
 * The main input is a {@link CreditCheckVerificationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CreditCheckVerificationDTO} or a {@link Page} of {@link CreditCheckVerificationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CreditCheckVerificationQueryService extends QueryService<CreditCheckVerification> {

    private final Logger log = LoggerFactory.getLogger(CreditCheckVerificationQueryService.class);

    private final CreditCheckVerificationRepository creditCheckVerificationRepository;

    private final CreditCheckVerificationMapper creditCheckVerificationMapper;

    public CreditCheckVerificationQueryService(
        CreditCheckVerificationRepository creditCheckVerificationRepository,
        CreditCheckVerificationMapper creditCheckVerificationMapper
    ) {
        this.creditCheckVerificationRepository = creditCheckVerificationRepository;
        this.creditCheckVerificationMapper = creditCheckVerificationMapper;
    }

    /**
     * Return a {@link List} of {@link CreditCheckVerificationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CreditCheckVerificationDTO> findByCriteria(CreditCheckVerificationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CreditCheckVerification> specification = createSpecification(criteria);
        return creditCheckVerificationMapper.toDto(creditCheckVerificationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CreditCheckVerificationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CreditCheckVerificationDTO> findByCriteria(CreditCheckVerificationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CreditCheckVerification> specification = createSpecification(criteria);
        return creditCheckVerificationRepository.findAll(specification, page).map(creditCheckVerificationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CreditCheckVerificationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CreditCheckVerification> specification = createSpecification(criteria);
        return creditCheckVerificationRepository.count(specification);
    }

    /**
     * Function to convert {@link CreditCheckVerificationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CreditCheckVerification> createSpecification(CreditCheckVerificationCriteria criteria) {
        Specification<CreditCheckVerification> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CreditCheckVerification_.id));
            }
            if (criteria.getVerQuestion() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getVerQuestion(), CreditCheckVerification_.verQuestion));
            }
            if (criteria.getVerQuestionChoice() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getVerQuestionChoice(), CreditCheckVerification_.verQuestionChoice)
                    );
            }
            if (criteria.getVerAnswer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVerAnswer(), CreditCheckVerification_.verAnswer));
            }
        }
        return specification;
    }
}
