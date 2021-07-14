package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.RoleTypeRef;
import com.apptium.customer.repository.RoleTypeRefRepository;
import com.apptium.customer.service.criteria.RoleTypeRefCriteria;
import com.apptium.customer.service.dto.RoleTypeRefDTO;
import com.apptium.customer.service.mapper.RoleTypeRefMapper;
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
 * Service for executing complex queries for {@link RoleTypeRef} entities in the database.
 * The main input is a {@link RoleTypeRefCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RoleTypeRefDTO} or a {@link Page} of {@link RoleTypeRefDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RoleTypeRefQueryService extends QueryService<RoleTypeRef> {

    private final Logger log = LoggerFactory.getLogger(RoleTypeRefQueryService.class);

    private final RoleTypeRefRepository roleTypeRefRepository;

    private final RoleTypeRefMapper roleTypeRefMapper;

    public RoleTypeRefQueryService(RoleTypeRefRepository roleTypeRefRepository, RoleTypeRefMapper roleTypeRefMapper) {
        this.roleTypeRefRepository = roleTypeRefRepository;
        this.roleTypeRefMapper = roleTypeRefMapper;
    }

    /**
     * Return a {@link List} of {@link RoleTypeRefDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RoleTypeRefDTO> findByCriteria(RoleTypeRefCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RoleTypeRef> specification = createSpecification(criteria);
        return roleTypeRefMapper.toDto(roleTypeRefRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RoleTypeRefDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RoleTypeRefDTO> findByCriteria(RoleTypeRefCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RoleTypeRef> specification = createSpecification(criteria);
        return roleTypeRefRepository.findAll(specification, page).map(roleTypeRefMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RoleTypeRefCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RoleTypeRef> specification = createSpecification(criteria);
        return roleTypeRefRepository.count(specification);
    }

    /**
     * Function to convert {@link RoleTypeRefCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RoleTypeRef> createSpecification(RoleTypeRefCriteria criteria) {
        Specification<RoleTypeRef> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RoleTypeRef_.id));
            }
            if (criteria.getRoleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoleName(), RoleTypeRef_.roleName));
            }
            if (criteria.getRoleType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoleType(), RoleTypeRef_.roleType));
            }
            if (criteria.getCustRelPartyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustRelPartyId(),
                            root -> root.join(RoleTypeRef_.custRelParties, JoinType.LEFT).get(CustRelParty_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
