package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.CustCommunicationRef;
import com.apptium.customer.repository.CustCommunicationRefRepository;
import com.apptium.customer.service.criteria.CustCommunicationRefCriteria;
import com.apptium.customer.service.dto.CustCommunicationRefDTO;
import com.apptium.customer.service.mapper.CustCommunicationRefMapper;
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
 * Service for executing complex queries for {@link CustCommunicationRef} entities in the database.
 * The main input is a {@link CustCommunicationRefCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustCommunicationRefDTO} or a {@link Page} of {@link CustCommunicationRefDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustCommunicationRefQueryService extends QueryService<CustCommunicationRef> {

    private final Logger log = LoggerFactory.getLogger(CustCommunicationRefQueryService.class);

    private final CustCommunicationRefRepository custCommunicationRefRepository;

    private final CustCommunicationRefMapper custCommunicationRefMapper;

    public CustCommunicationRefQueryService(
        CustCommunicationRefRepository custCommunicationRefRepository,
        CustCommunicationRefMapper custCommunicationRefMapper
    ) {
        this.custCommunicationRefRepository = custCommunicationRefRepository;
        this.custCommunicationRefMapper = custCommunicationRefMapper;
    }

    /**
     * Return a {@link List} of {@link CustCommunicationRefDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustCommunicationRefDTO> findByCriteria(CustCommunicationRefCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustCommunicationRef> specification = createSpecification(criteria);
        return custCommunicationRefMapper.toDto(custCommunicationRefRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustCommunicationRefDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustCommunicationRefDTO> findByCriteria(CustCommunicationRefCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustCommunicationRef> specification = createSpecification(criteria);
        return custCommunicationRefRepository.findAll(specification, page).map(custCommunicationRefMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustCommunicationRefCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustCommunicationRef> specification = createSpecification(criteria);
        return custCommunicationRefRepository.count(specification);
    }

    /**
     * Function to convert {@link CustCommunicationRefCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustCommunicationRef> createSpecification(CustCommunicationRefCriteria criteria) {
        Specification<CustCommunicationRef> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustCommunicationRef_.id));
            }
            if (criteria.getCustomerNotificationId() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getCustomerNotificationId(), CustCommunicationRef_.customerNotificationId)
                    );
            }
            if (criteria.getRole() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRole(), CustCommunicationRef_.role));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), CustCommunicationRef_.status));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCustomerId(), CustCommunicationRef_.customerId));
            }
            if (criteria.getCustomerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomerId(),
                            root -> root.join(CustCommunicationRef_.customer, JoinType.LEFT).get(Customer_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
