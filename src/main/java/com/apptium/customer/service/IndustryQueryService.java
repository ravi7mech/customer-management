package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.Industry;
import com.apptium.customer.repository.IndustryRepository;
import com.apptium.customer.service.criteria.IndustryCriteria;
import com.apptium.customer.service.dto.IndustryDTO;
import com.apptium.customer.service.mapper.IndustryMapper;
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
 * Service for executing complex queries for {@link Industry} entities in the database.
 * The main input is a {@link IndustryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link IndustryDTO} or a {@link Page} of {@link IndustryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IndustryQueryService extends QueryService<Industry> {

    private final Logger log = LoggerFactory.getLogger(IndustryQueryService.class);

    private final IndustryRepository industryRepository;

    private final IndustryMapper industryMapper;

    public IndustryQueryService(IndustryRepository industryRepository, IndustryMapper industryMapper) {
        this.industryRepository = industryRepository;
        this.industryMapper = industryMapper;
    }

    /**
     * Return a {@link List} of {@link IndustryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<IndustryDTO> findByCriteria(IndustryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Industry> specification = createSpecification(criteria);
        return industryMapper.toDto(industryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link IndustryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<IndustryDTO> findByCriteria(IndustryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Industry> specification = createSpecification(criteria);
        return industryRepository.findAll(specification, page).map(industryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IndustryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Industry> specification = createSpecification(criteria);
        return industryRepository.count(specification);
    }

    /**
     * Function to convert {@link IndustryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Industry> createSpecification(IndustryCriteria criteria) {
        Specification<Industry> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Industry_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Industry_.name));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Industry_.code));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Industry_.description));
            }
            if (criteria.getCustomerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCustomerId(), root -> root.join(Industry_.customer, JoinType.LEFT).get(Customer_.id))
                    );
            }
        }
        return specification;
    }
}
