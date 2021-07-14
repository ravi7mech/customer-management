package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.IndAuditTrial;
import com.apptium.customer.repository.IndAuditTrialRepository;
import com.apptium.customer.service.criteria.IndAuditTrialCriteria;
import com.apptium.customer.service.dto.IndAuditTrialDTO;
import com.apptium.customer.service.mapper.IndAuditTrialMapper;
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
 * Service for executing complex queries for {@link IndAuditTrial} entities in the database.
 * The main input is a {@link IndAuditTrialCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link IndAuditTrialDTO} or a {@link Page} of {@link IndAuditTrialDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IndAuditTrialQueryService extends QueryService<IndAuditTrial> {

    private final Logger log = LoggerFactory.getLogger(IndAuditTrialQueryService.class);

    private final IndAuditTrialRepository indAuditTrialRepository;

    private final IndAuditTrialMapper indAuditTrialMapper;

    public IndAuditTrialQueryService(IndAuditTrialRepository indAuditTrialRepository, IndAuditTrialMapper indAuditTrialMapper) {
        this.indAuditTrialRepository = indAuditTrialRepository;
        this.indAuditTrialMapper = indAuditTrialMapper;
    }

    /**
     * Return a {@link List} of {@link IndAuditTrialDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<IndAuditTrialDTO> findByCriteria(IndAuditTrialCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<IndAuditTrial> specification = createSpecification(criteria);
        return indAuditTrialMapper.toDto(indAuditTrialRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link IndAuditTrialDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<IndAuditTrialDTO> findByCriteria(IndAuditTrialCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<IndAuditTrial> specification = createSpecification(criteria);
        return indAuditTrialRepository.findAll(specification, page).map(indAuditTrialMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IndAuditTrialCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<IndAuditTrial> specification = createSpecification(criteria);
        return indAuditTrialRepository.count(specification);
    }

    /**
     * Function to convert {@link IndAuditTrialCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<IndAuditTrial> createSpecification(IndAuditTrialCriteria criteria) {
        Specification<IndAuditTrial> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), IndAuditTrial_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), IndAuditTrial_.name));
            }
            if (criteria.getActivity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActivity(), IndAuditTrial_.activity));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCustomerId(), IndAuditTrial_.customerId));
            }
            if (criteria.getIndividualId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIndividualId(), IndAuditTrial_.individualId));
            }
            if (criteria.getIndividualId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getIndividualId(),
                            root -> root.join(IndAuditTrial_.individual, JoinType.LEFT).get(Individual_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
