package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.CustContact;
import com.apptium.customer.repository.CustContactRepository;
import com.apptium.customer.service.criteria.CustContactCriteria;
import com.apptium.customer.service.dto.CustContactDTO;
import com.apptium.customer.service.mapper.CustContactMapper;
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
 * Service for executing complex queries for {@link CustContact} entities in the database.
 * The main input is a {@link CustContactCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustContactDTO} or a {@link Page} of {@link CustContactDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustContactQueryService extends QueryService<CustContact> {

    private final Logger log = LoggerFactory.getLogger(CustContactQueryService.class);

    private final CustContactRepository custContactRepository;

    private final CustContactMapper custContactMapper;

    public CustContactQueryService(CustContactRepository custContactRepository, CustContactMapper custContactMapper) {
        this.custContactRepository = custContactRepository;
        this.custContactMapper = custContactMapper;
    }

    /**
     * Return a {@link List} of {@link CustContactDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustContactDTO> findByCriteria(CustContactCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustContact> specification = createSpecification(criteria);
        return custContactMapper.toDto(custContactRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustContactDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustContactDTO> findByCriteria(CustContactCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustContact> specification = createSpecification(criteria);
        return custContactRepository.findAll(specification, page).map(custContactMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustContactCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustContact> specification = createSpecification(criteria);
        return custContactRepository.count(specification);
    }

    /**
     * Function to convert {@link CustContactCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustContact> createSpecification(CustContactCriteria criteria) {
        Specification<CustContact> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustContact_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CustContact_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), CustContact_.description));
            }
            if (criteria.getPreferred() != null) {
                specification = specification.and(buildSpecification(criteria.getPreferred(), CustContact_.preferred));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), CustContact_.type));
            }
            if (criteria.getValidFrom() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidFrom(), CustContact_.validFrom));
            }
            if (criteria.getValidTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidTo(), CustContact_.validTo));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCustomerId(), CustContact_.customerId));
            }
            if (criteria.getGeographicSiteRefId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getGeographicSiteRefId(),
                            root -> root.join(CustContact_.geographicSiteRef, JoinType.LEFT).get(GeographicSiteRef_.id)
                        )
                    );
            }
            if (criteria.getCustContactCharId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustContactCharId(),
                            root -> root.join(CustContact_.custContactChars, JoinType.LEFT).get(CustContactChar_.id)
                        )
                    );
            }
            if (criteria.getCustomerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomerId(),
                            root -> root.join(CustContact_.customer, JoinType.LEFT).get(Customer_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
