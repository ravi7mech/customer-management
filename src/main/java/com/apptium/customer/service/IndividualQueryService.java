package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.Individual;
import com.apptium.customer.repository.IndividualRepository;
import com.apptium.customer.service.criteria.IndividualCriteria;
import com.apptium.customer.service.dto.IndividualDTO;
import com.apptium.customer.service.mapper.IndividualMapper;
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
 * Service for executing complex queries for {@link Individual} entities in the database.
 * The main input is a {@link IndividualCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link IndividualDTO} or a {@link Page} of {@link IndividualDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IndividualQueryService extends QueryService<Individual> {

    private final Logger log = LoggerFactory.getLogger(IndividualQueryService.class);

    private final IndividualRepository individualRepository;

    private final IndividualMapper individualMapper;

    public IndividualQueryService(IndividualRepository individualRepository, IndividualMapper individualMapper) {
        this.individualRepository = individualRepository;
        this.individualMapper = individualMapper;
    }

    /**
     * Return a {@link List} of {@link IndividualDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<IndividualDTO> findByCriteria(IndividualCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Individual> specification = createSpecification(criteria);
        return individualMapper.toDto(individualRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link IndividualDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<IndividualDTO> findByCriteria(IndividualCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Individual> specification = createSpecification(criteria);
        return individualRepository.findAll(specification, page).map(individualMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IndividualCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Individual> specification = createSpecification(criteria);
        return individualRepository.count(specification);
    }

    /**
     * Function to convert {@link IndividualCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Individual> createSpecification(IndividualCriteria criteria) {
        Specification<Individual> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Individual_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Individual_.title));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), Individual_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), Individual_.lastName));
            }
            if (criteria.getMiddleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMiddleName(), Individual_.middleName));
            }
            if (criteria.getFormattedName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFormattedName(), Individual_.formattedName));
            }
            if (criteria.getDateOfBirth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfBirth(), Individual_.dateOfBirth));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGender(), Individual_.gender));
            }
            if (criteria.getMaritalStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaritalStatus(), Individual_.maritalStatus));
            }
            if (criteria.getNationality() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNationality(), Individual_.nationality));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Individual_.status));
            }
            if (criteria.getIndActivationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getIndActivationId(),
                            root -> root.join(Individual_.indActivation, JoinType.LEFT).get(IndActivation_.id)
                        )
                    );
            }
            if (criteria.getIndNewsLetterConfId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getIndNewsLetterConfId(),
                            root -> root.join(Individual_.indNewsLetterConf, JoinType.LEFT).get(IndNewsLetterConf_.id)
                        )
                    );
            }
            if (criteria.getIndContactId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getIndContactId(),
                            root -> root.join(Individual_.indContacts, JoinType.LEFT).get(IndContact_.id)
                        )
                    );
            }
            if (criteria.getIndCharId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getIndCharId(), root -> root.join(Individual_.indChars, JoinType.LEFT).get(IndChar_.id))
                    );
            }
            if (criteria.getIndAuditTrialId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getIndAuditTrialId(),
                            root -> root.join(Individual_.indAuditTrials, JoinType.LEFT).get(IndAuditTrial_.id)
                        )
                    );
            }
            if (criteria.getCustRelPartyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustRelPartyId(),
                            root -> root.join(Individual_.custRelParties, JoinType.LEFT).get(CustRelParty_.id)
                        )
                    );
            }
            if (criteria.getShoppingSessionRefId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getShoppingSessionRefId(),
                            root -> root.join(Individual_.shoppingSessionRefs, JoinType.LEFT).get(ShoppingSessionRef_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
