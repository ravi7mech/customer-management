package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.CustStatistics;
import com.apptium.customer.repository.CustStatisticsRepository;
import com.apptium.customer.service.criteria.CustStatisticsCriteria;
import com.apptium.customer.service.dto.CustStatisticsDTO;
import com.apptium.customer.service.mapper.CustStatisticsMapper;
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
 * Service for executing complex queries for {@link CustStatistics} entities in the database.
 * The main input is a {@link CustStatisticsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustStatisticsDTO} or a {@link Page} of {@link CustStatisticsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustStatisticsQueryService extends QueryService<CustStatistics> {

    private final Logger log = LoggerFactory.getLogger(CustStatisticsQueryService.class);

    private final CustStatisticsRepository custStatisticsRepository;

    private final CustStatisticsMapper custStatisticsMapper;

    public CustStatisticsQueryService(CustStatisticsRepository custStatisticsRepository, CustStatisticsMapper custStatisticsMapper) {
        this.custStatisticsRepository = custStatisticsRepository;
        this.custStatisticsMapper = custStatisticsMapper;
    }

    /**
     * Return a {@link List} of {@link CustStatisticsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustStatisticsDTO> findByCriteria(CustStatisticsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustStatistics> specification = createSpecification(criteria);
        return custStatisticsMapper.toDto(custStatisticsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustStatisticsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustStatisticsDTO> findByCriteria(CustStatisticsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustStatistics> specification = createSpecification(criteria);
        return custStatisticsRepository.findAll(specification, page).map(custStatisticsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustStatisticsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustStatistics> specification = createSpecification(criteria);
        return custStatisticsRepository.count(specification);
    }

    /**
     * Function to convert {@link CustStatisticsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustStatistics> createSpecification(CustStatisticsCriteria criteria) {
        Specification<CustStatistics> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustStatistics_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CustStatistics_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), CustStatistics_.value));
            }
            if (criteria.getValuetype() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValuetype(), CustStatistics_.valuetype));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCustomerId(), CustStatistics_.customerId));
            }
            if (criteria.getCustomerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomerId(),
                            root -> root.join(CustStatistics_.customer, JoinType.LEFT).get(Customer_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
