package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.IndContact;
import com.apptium.customer.repository.IndContactRepository;
import com.apptium.customer.service.criteria.IndContactCriteria;
import com.apptium.customer.service.dto.IndContactDTO;
import com.apptium.customer.service.mapper.IndContactMapper;
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
 * Service for executing complex queries for {@link IndContact} entities in the database.
 * The main input is a {@link IndContactCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link IndContactDTO} or a {@link Page} of {@link IndContactDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IndContactQueryService extends QueryService<IndContact> {

    private final Logger log = LoggerFactory.getLogger(IndContactQueryService.class);

    private final IndContactRepository indContactRepository;

    private final IndContactMapper indContactMapper;

    public IndContactQueryService(IndContactRepository indContactRepository, IndContactMapper indContactMapper) {
        this.indContactRepository = indContactRepository;
        this.indContactMapper = indContactMapper;
    }

    /**
     * Return a {@link List} of {@link IndContactDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<IndContactDTO> findByCriteria(IndContactCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<IndContact> specification = createSpecification(criteria);
        return indContactMapper.toDto(indContactRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link IndContactDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<IndContactDTO> findByCriteria(IndContactCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<IndContact> specification = createSpecification(criteria);
        return indContactRepository.findAll(specification, page).map(indContactMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IndContactCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<IndContact> specification = createSpecification(criteria);
        return indContactRepository.count(specification);
    }

    /**
     * Function to convert {@link IndContactCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<IndContact> createSpecification(IndContactCriteria criteria) {
        Specification<IndContact> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), IndContact_.id));
            }
            if (criteria.getPreferred() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPreferred(), IndContact_.preferred));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), IndContact_.type));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), IndContact_.validFrom));
            }
            if (criteria.getValidTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTo(), IndContact_.validTo));
            }
            if (criteria.getIndividualId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIndividualId(), IndContact_.individualId));
            }
            if (criteria.getIndContactCharId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getIndContactCharId(),
                            root -> root.join(IndContact_.indContactChars, JoinType.LEFT).get(IndContactChar_.id)
                        )
                    );
            }
            if (criteria.getIndividualId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getIndividualId(),
                            root -> root.join(IndContact_.individual, JoinType.LEFT).get(Individual_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
