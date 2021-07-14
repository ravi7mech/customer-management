package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.CustRelParty;
import com.apptium.customer.repository.CustRelPartyRepository;
import com.apptium.customer.service.criteria.CustRelPartyCriteria;
import com.apptium.customer.service.dto.CustRelPartyDTO;
import com.apptium.customer.service.mapper.CustRelPartyMapper;
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
 * Service for executing complex queries for {@link CustRelParty} entities in the database.
 * The main input is a {@link CustRelPartyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustRelPartyDTO} or a {@link Page} of {@link CustRelPartyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustRelPartyQueryService extends QueryService<CustRelParty> {

    private final Logger log = LoggerFactory.getLogger(CustRelPartyQueryService.class);

    private final CustRelPartyRepository custRelPartyRepository;

    private final CustRelPartyMapper custRelPartyMapper;

    public CustRelPartyQueryService(CustRelPartyRepository custRelPartyRepository, CustRelPartyMapper custRelPartyMapper) {
        this.custRelPartyRepository = custRelPartyRepository;
        this.custRelPartyMapper = custRelPartyMapper;
    }

    /**
     * Return a {@link List} of {@link CustRelPartyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustRelPartyDTO> findByCriteria(CustRelPartyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustRelParty> specification = createSpecification(criteria);
        return custRelPartyMapper.toDto(custRelPartyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustRelPartyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustRelPartyDTO> findByCriteria(CustRelPartyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustRelParty> specification = createSpecification(criteria);
        return custRelPartyRepository.findAll(specification, page).map(custRelPartyMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustRelPartyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustRelParty> specification = createSpecification(criteria);
        return custRelPartyRepository.count(specification);
    }

    /**
     * Function to convert {@link CustRelPartyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustRelParty> createSpecification(CustRelPartyCriteria criteria) {
        Specification<CustRelParty> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustRelParty_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CustRelParty_.name));
            }
            if (criteria.getRoleId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRoleId(), CustRelParty_.roleId));
            }
            if (criteria.getIndividualId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIndividualId(), CustRelParty_.individualId));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), CustRelParty_.validFrom));
            }
            if (criteria.getValidTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTo(), CustRelParty_.validTo));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCustomerId(), CustRelParty_.customerId));
            }
            if (criteria.getCustomerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomerId(),
                            root -> root.join(CustRelParty_.customer, JoinType.LEFT).get(Customer_.id)
                        )
                    );
            }
            if (criteria.getDepartmentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDepartmentId(),
                            root -> root.join(CustRelParty_.department, JoinType.LEFT).get(Department_.id)
                        )
                    );
            }
            if (criteria.getRoleTypeRefId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRoleTypeRefId(),
                            root -> root.join(CustRelParty_.roleTypeRef, JoinType.LEFT).get(RoleTypeRef_.id)
                        )
                    );
            }
            if (criteria.getIndividualId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getIndividualId(),
                            root -> root.join(CustRelParty_.individual, JoinType.LEFT).get(Individual_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
