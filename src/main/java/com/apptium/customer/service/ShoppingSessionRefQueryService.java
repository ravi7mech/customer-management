package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.ShoppingSessionRef;
import com.apptium.customer.repository.ShoppingSessionRefRepository;
import com.apptium.customer.service.criteria.ShoppingSessionRefCriteria;
import com.apptium.customer.service.dto.ShoppingSessionRefDTO;
import com.apptium.customer.service.mapper.ShoppingSessionRefMapper;
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
 * Service for executing complex queries for {@link ShoppingSessionRef} entities in the database.
 * The main input is a {@link ShoppingSessionRefCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ShoppingSessionRefDTO} or a {@link Page} of {@link ShoppingSessionRefDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ShoppingSessionRefQueryService extends QueryService<ShoppingSessionRef> {

    private final Logger log = LoggerFactory.getLogger(ShoppingSessionRefQueryService.class);

    private final ShoppingSessionRefRepository shoppingSessionRefRepository;

    private final ShoppingSessionRefMapper shoppingSessionRefMapper;

    public ShoppingSessionRefQueryService(
        ShoppingSessionRefRepository shoppingSessionRefRepository,
        ShoppingSessionRefMapper shoppingSessionRefMapper
    ) {
        this.shoppingSessionRefRepository = shoppingSessionRefRepository;
        this.shoppingSessionRefMapper = shoppingSessionRefMapper;
    }

    /**
     * Return a {@link List} of {@link ShoppingSessionRefDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ShoppingSessionRefDTO> findByCriteria(ShoppingSessionRefCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ShoppingSessionRef> specification = createSpecification(criteria);
        return shoppingSessionRefMapper.toDto(shoppingSessionRefRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ShoppingSessionRefDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ShoppingSessionRefDTO> findByCriteria(ShoppingSessionRefCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ShoppingSessionRef> specification = createSpecification(criteria);
        return shoppingSessionRefRepository.findAll(specification, page).map(shoppingSessionRefMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ShoppingSessionRefCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ShoppingSessionRef> specification = createSpecification(criteria);
        return shoppingSessionRefRepository.count(specification);
    }

    /**
     * Function to convert {@link ShoppingSessionRefCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ShoppingSessionRef> createSpecification(ShoppingSessionRefCriteria criteria) {
        Specification<ShoppingSessionRef> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ShoppingSessionRef_.id));
            }
            if (criteria.getHref() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHref(), ShoppingSessionRef_.href));
            }
            if (criteria.getShoppingSessionId() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getShoppingSessionId(), ShoppingSessionRef_.shoppingSessionId));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), ShoppingSessionRef_.status));
            }
            if (criteria.getSessionAbondoned() != null) {
                specification = specification.and(buildSpecification(criteria.getSessionAbondoned(), ShoppingSessionRef_.sessionAbondoned));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCustomerId(), ShoppingSessionRef_.customerId));
            }
            if (criteria.getChannel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChannel(), ShoppingSessionRef_.channel));
            }
            if (criteria.getIndividualId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIndividualId(), ShoppingSessionRef_.individualId));
            }
            if (criteria.getCustomerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustomerId(),
                            root -> root.join(ShoppingSessionRef_.customer, JoinType.LEFT).get(Customer_.id)
                        )
                    );
            }
            if (criteria.getIndividualId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getIndividualId(),
                            root -> root.join(ShoppingSessionRef_.individual, JoinType.LEFT).get(Individual_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
