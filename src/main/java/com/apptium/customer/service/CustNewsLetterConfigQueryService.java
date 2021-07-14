package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.CustNewsLetterConfig;
import com.apptium.customer.repository.CustNewsLetterConfigRepository;
import com.apptium.customer.service.criteria.CustNewsLetterConfigCriteria;
import com.apptium.customer.service.dto.CustNewsLetterConfigDTO;
import com.apptium.customer.service.mapper.CustNewsLetterConfigMapper;
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
 * Service for executing complex queries for {@link CustNewsLetterConfig} entities in the database.
 * The main input is a {@link CustNewsLetterConfigCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustNewsLetterConfigDTO} or a {@link Page} of {@link CustNewsLetterConfigDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustNewsLetterConfigQueryService extends QueryService<CustNewsLetterConfig> {

    private final Logger log = LoggerFactory.getLogger(CustNewsLetterConfigQueryService.class);

    private final CustNewsLetterConfigRepository custNewsLetterConfigRepository;

    private final CustNewsLetterConfigMapper custNewsLetterConfigMapper;

    public CustNewsLetterConfigQueryService(
        CustNewsLetterConfigRepository custNewsLetterConfigRepository,
        CustNewsLetterConfigMapper custNewsLetterConfigMapper
    ) {
        this.custNewsLetterConfigRepository = custNewsLetterConfigRepository;
        this.custNewsLetterConfigMapper = custNewsLetterConfigMapper;
    }

    /**
     * Return a {@link List} of {@link CustNewsLetterConfigDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustNewsLetterConfigDTO> findByCriteria(CustNewsLetterConfigCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustNewsLetterConfig> specification = createSpecification(criteria);
        return custNewsLetterConfigMapper.toDto(custNewsLetterConfigRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustNewsLetterConfigDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustNewsLetterConfigDTO> findByCriteria(CustNewsLetterConfigCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustNewsLetterConfig> specification = createSpecification(criteria);
        return custNewsLetterConfigRepository.findAll(specification, page).map(custNewsLetterConfigMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustNewsLetterConfigCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustNewsLetterConfig> specification = createSpecification(criteria);
        return custNewsLetterConfigRepository.count(specification);
    }

    /**
     * Function to convert {@link CustNewsLetterConfigCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustNewsLetterConfig> createSpecification(CustNewsLetterConfigCriteria criteria) {
        Specification<CustNewsLetterConfig> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustNewsLetterConfig_.id));
            }
            if (criteria.getNewLetterTypeId() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getNewLetterTypeId(), CustNewsLetterConfig_.newLetterTypeId));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), CustNewsLetterConfig_.value));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCustomerId(), CustNewsLetterConfig_.customerId));
            }
            if (criteria.getCustomerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomerId(),
                            root -> root.join(CustNewsLetterConfig_.customer, JoinType.LEFT).get(Customer_.id)
                        )
                    );
            }
            if (criteria.getNewsLetterTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNewsLetterTypeId(),
                            root -> root.join(CustNewsLetterConfig_.newsLetterType, JoinType.LEFT).get(NewsLetterType_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
