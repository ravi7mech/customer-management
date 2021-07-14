package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.CustContactChar;
import com.apptium.customer.repository.CustContactCharRepository;
import com.apptium.customer.service.criteria.CustContactCharCriteria;
import com.apptium.customer.service.dto.CustContactCharDTO;
import com.apptium.customer.service.mapper.CustContactCharMapper;
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
 * Service for executing complex queries for {@link CustContactChar} entities in the database.
 * The main input is a {@link CustContactCharCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustContactCharDTO} or a {@link Page} of {@link CustContactCharDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustContactCharQueryService extends QueryService<CustContactChar> {

    private final Logger log = LoggerFactory.getLogger(CustContactCharQueryService.class);

    private final CustContactCharRepository custContactCharRepository;

    private final CustContactCharMapper custContactCharMapper;

    public CustContactCharQueryService(CustContactCharRepository custContactCharRepository, CustContactCharMapper custContactCharMapper) {
        this.custContactCharRepository = custContactCharRepository;
        this.custContactCharMapper = custContactCharMapper;
    }

    /**
     * Return a {@link List} of {@link CustContactCharDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustContactCharDTO> findByCriteria(CustContactCharCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CustContactChar> specification = createSpecification(criteria);
        return custContactCharMapper.toDto(custContactCharRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustContactCharDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustContactCharDTO> findByCriteria(CustContactCharCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CustContactChar> specification = createSpecification(criteria);
        return custContactCharRepository.findAll(specification, page).map(custContactCharMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustContactCharCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CustContactChar> specification = createSpecification(criteria);
        return custContactCharRepository.count(specification);
    }

    /**
     * Function to convert {@link CustContactCharCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CustContactChar> createSpecification(CustContactCharCriteria criteria) {
        Specification<CustContactChar> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CustContactChar_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), CustContactChar_.type));
            }
            if (criteria.getStreetOne() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStreetOne(), CustContactChar_.streetOne));
            }
            if (criteria.getStreetTwo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStreetTwo(), CustContactChar_.streetTwo));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), CustContactChar_.city));
            }
            if (criteria.getStateOrProvince() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getStateOrProvince(), CustContactChar_.stateOrProvince));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), CustContactChar_.country));
            }
            if (criteria.getPostCode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPostCode(), CustContactChar_.postCode));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPhoneNumber(), CustContactChar_.phoneNumber));
            }
            if (criteria.getEmailAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmailAddress(), CustContactChar_.emailAddress));
            }
            if (criteria.getFaxNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFaxNumber(), CustContactChar_.faxNumber));
            }
            if (criteria.getLatitude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLatitude(), CustContactChar_.latitude));
            }
            if (criteria.getLongitude() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLongitude(), CustContactChar_.longitude));
            }
            if (criteria.getSvContactId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSvContactId(), CustContactChar_.svContactId));
            }
            if (criteria.getIsEmailValid() != null) {
                specification = specification.and(buildSpecification(criteria.getIsEmailValid(), CustContactChar_.isEmailValid));
            }
            if (criteria.getIsAddressValid() != null) {
                specification = specification.and(buildSpecification(criteria.getIsAddressValid(), CustContactChar_.isAddressValid));
            }
            if (criteria.getCustConMediumId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCustConMediumId(), CustContactChar_.custConMediumId));
            }
            if (criteria.getCustContactId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustContactId(),
                            root -> root.join(CustContactChar_.custContact, JoinType.LEFT).get(CustContact_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
