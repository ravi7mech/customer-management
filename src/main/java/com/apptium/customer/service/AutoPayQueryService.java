package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.AutoPay;
import com.apptium.customer.repository.AutoPayRepository;
import com.apptium.customer.service.criteria.AutoPayCriteria;
import com.apptium.customer.service.dto.AutoPayDTO;
import com.apptium.customer.service.mapper.AutoPayMapper;
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
 * Service for executing complex queries for {@link AutoPay} entities in the database.
 * The main input is a {@link AutoPayCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AutoPayDTO} or a {@link Page} of {@link AutoPayDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AutoPayQueryService extends QueryService<AutoPay> {

    private final Logger log = LoggerFactory.getLogger(AutoPayQueryService.class);

    private final AutoPayRepository autoPayRepository;

    private final AutoPayMapper autoPayMapper;

    public AutoPayQueryService(AutoPayRepository autoPayRepository, AutoPayMapper autoPayMapper) {
        this.autoPayRepository = autoPayRepository;
        this.autoPayMapper = autoPayMapper;
    }

    /**
     * Return a {@link List} of {@link AutoPayDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AutoPayDTO> findByCriteria(AutoPayCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AutoPay> specification = createSpecification(criteria);
        return autoPayMapper.toDto(autoPayRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AutoPayDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AutoPayDTO> findByCriteria(AutoPayCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AutoPay> specification = createSpecification(criteria);
        return autoPayRepository.findAll(specification, page).map(autoPayMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AutoPayCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AutoPay> specification = createSpecification(criteria);
        return autoPayRepository.count(specification);
    }

    /**
     * Function to convert {@link AutoPayCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AutoPay> createSpecification(AutoPayCriteria criteria) {
        Specification<AutoPay> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AutoPay_.id));
            }
            if (criteria.getChannel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChannel(), AutoPay_.channel));
            }
            if (criteria.getAutoPayId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAutoPayId(), AutoPay_.autoPayId));
            }
            if (criteria.getDebitDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDebitDate(), AutoPay_.debitDate));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), AutoPay_.status));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCustomerId(), AutoPay_.customerId));
            }
        }
        return specification;
    }
}
