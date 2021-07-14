package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.IndContactChar;
import com.apptium.customer.repository.IndContactCharRepository;
import com.apptium.customer.service.criteria.IndContactCharCriteria;
import com.apptium.customer.service.dto.IndContactCharDTO;
import com.apptium.customer.service.mapper.IndContactCharMapper;
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
 * Service for executing complex queries for {@link IndContactChar} entities in the database.
 * The main input is a {@link IndContactCharCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link IndContactCharDTO} or a {@link Page} of {@link IndContactCharDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IndContactCharQueryService extends QueryService<IndContactChar> {

    private final Logger log = LoggerFactory.getLogger(IndContactCharQueryService.class);

    private final IndContactCharRepository indContactCharRepository;

    private final IndContactCharMapper indContactCharMapper;

    public IndContactCharQueryService(IndContactCharRepository indContactCharRepository, IndContactCharMapper indContactCharMapper) {
        this.indContactCharRepository = indContactCharRepository;
        this.indContactCharMapper = indContactCharMapper;
    }

    /**
     * Return a {@link List} of {@link IndContactCharDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<IndContactCharDTO> findByCriteria(IndContactCharCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<IndContactChar> specification = createSpecification(criteria);
        return indContactCharMapper.toDto(indContactCharRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link IndContactCharDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<IndContactCharDTO> findByCriteria(IndContactCharCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<IndContactChar> specification = createSpecification(criteria);
        return indContactCharRepository.findAll(specification, page).map(indContactCharMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IndContactCharCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<IndContactChar> specification = createSpecification(criteria);
        return indContactCharRepository.count(specification);
    }

    /**
     * Function to convert {@link IndContactCharCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<IndContactChar> createSpecification(IndContactCharCriteria criteria) {
        Specification<IndContactChar> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), IndContactChar_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), IndContactChar_.type));
            }
            if (criteria.getStreetOne() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStreetOne(), IndContactChar_.streetOne));
            }
            if (criteria.getStreetTwo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStreetTwo(), IndContactChar_.streetTwo));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), IndContactChar_.city));
            }
            if (criteria.getStateOrProvince() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStateOrProvince(), IndContactChar_.stateOrProvince));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), IndContactChar_.country));
            }
            if (criteria.getPostCode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPostCode(), IndContactChar_.postCode));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPhoneNumber(), IndContactChar_.phoneNumber));
            }
            if (criteria.getEmailAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmailAddress(), IndContactChar_.emailAddress));
            }
            if (criteria.getFaxNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFaxNumber(), IndContactChar_.faxNumber));
            }
            if (criteria.getLatitude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLatitude(), IndContactChar_.latitude));
            }
            if (criteria.getLongitude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLongitude(), IndContactChar_.longitude));
            }
            if (criteria.getSvContactId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSvContactId(), IndContactChar_.svContactId));
            }
            if (criteria.getIsEmailValid() != null) {
                specification = specification.and(buildSpecification(criteria.getIsEmailValid(), IndContactChar_.isEmailValid));
            }
            if (criteria.getIsAddressValid() != null) {
                specification = specification.and(buildSpecification(criteria.getIsAddressValid(), IndContactChar_.isAddressValid));
            }
            if (criteria.getIndConId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIndConId(), IndContactChar_.indConId));
            }
            if (criteria.getIndContactId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getIndContactId(),
                            root -> root.join(IndContactChar_.indContact, JoinType.LEFT).get(IndContact_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
