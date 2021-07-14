package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.GeographicSiteRef;
import com.apptium.customer.repository.GeographicSiteRefRepository;
import com.apptium.customer.service.criteria.GeographicSiteRefCriteria;
import com.apptium.customer.service.dto.GeographicSiteRefDTO;
import com.apptium.customer.service.mapper.GeographicSiteRefMapper;
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
 * Service for executing complex queries for {@link GeographicSiteRef} entities in the database.
 * The main input is a {@link GeographicSiteRefCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GeographicSiteRefDTO} or a {@link Page} of {@link GeographicSiteRefDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GeographicSiteRefQueryService extends QueryService<GeographicSiteRef> {

    private final Logger log = LoggerFactory.getLogger(GeographicSiteRefQueryService.class);

    private final GeographicSiteRefRepository geographicSiteRefRepository;

    private final GeographicSiteRefMapper geographicSiteRefMapper;

    public GeographicSiteRefQueryService(
        GeographicSiteRefRepository geographicSiteRefRepository,
        GeographicSiteRefMapper geographicSiteRefMapper
    ) {
        this.geographicSiteRefRepository = geographicSiteRefRepository;
        this.geographicSiteRefMapper = geographicSiteRefMapper;
    }

    /**
     * Return a {@link List} of {@link GeographicSiteRefDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GeographicSiteRefDTO> findByCriteria(GeographicSiteRefCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<GeographicSiteRef> specification = createSpecification(criteria);
        return geographicSiteRefMapper.toDto(geographicSiteRefRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link GeographicSiteRefDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GeographicSiteRefDTO> findByCriteria(GeographicSiteRefCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<GeographicSiteRef> specification = createSpecification(criteria);
        return geographicSiteRefRepository.findAll(specification, page).map(geographicSiteRefMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GeographicSiteRefCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<GeographicSiteRef> specification = createSpecification(criteria);
        return geographicSiteRefRepository.count(specification);
    }

    /**
     * Function to convert {@link GeographicSiteRefCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<GeographicSiteRef> createSpecification(GeographicSiteRefCriteria criteria) {
        Specification<GeographicSiteRef> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), GeographicSiteRef_.id));
            }
            if (criteria.getSiteRef() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSiteRef(), GeographicSiteRef_.siteRef));
            }
            if (criteria.getLocation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocation(), GeographicSiteRef_.location));
            }
            if (criteria.getCustConId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCustConId(), GeographicSiteRef_.custConId));
            }
            if (criteria.getCustContactId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustContactId(),
                            root -> root.join(GeographicSiteRef_.custContact, JoinType.LEFT).get(CustContact_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
