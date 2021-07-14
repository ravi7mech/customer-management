package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.IndActivation;
import com.apptium.customer.repository.IndActivationRepository;
import com.apptium.customer.service.criteria.IndActivationCriteria;
import com.apptium.customer.service.dto.IndActivationDTO;
import com.apptium.customer.service.mapper.IndActivationMapper;
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
 * Service for executing complex queries for {@link IndActivation} entities in the database.
 * The main input is a {@link IndActivationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link IndActivationDTO} or a {@link Page} of {@link IndActivationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IndActivationQueryService extends QueryService<IndActivation> {

    private final Logger log = LoggerFactory.getLogger(IndActivationQueryService.class);

    private final IndActivationRepository indActivationRepository;

    private final IndActivationMapper indActivationMapper;

    public IndActivationQueryService(IndActivationRepository indActivationRepository, IndActivationMapper indActivationMapper) {
        this.indActivationRepository = indActivationRepository;
        this.indActivationMapper = indActivationMapper;
    }

    /**
     * Return a {@link List} of {@link IndActivationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<IndActivationDTO> findByCriteria(IndActivationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<IndActivation> specification = createSpecification(criteria);
        return indActivationMapper.toDto(indActivationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link IndActivationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<IndActivationDTO> findByCriteria(IndActivationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<IndActivation> specification = createSpecification(criteria);
        return indActivationRepository.findAll(specification, page).map(indActivationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IndActivationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<IndActivation> specification = createSpecification(criteria);
        return indActivationRepository.count(specification);
    }

    /**
     * Function to convert {@link IndActivationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<IndActivation> createSpecification(IndActivationCriteria criteria) {
        Specification<IndActivation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), IndActivation_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), IndActivation_.name));
            }
            if (criteria.getActivity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActivity(), IndActivation_.activity));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCustomerId(), IndActivation_.customerId));
            }
            if (criteria.getIndividualId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIndividualId(), IndActivation_.individualId));
            }
            if (criteria.getIndividualId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getIndividualId(),
                            root -> root.join(IndActivation_.individual, JoinType.LEFT).get(Individual_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
