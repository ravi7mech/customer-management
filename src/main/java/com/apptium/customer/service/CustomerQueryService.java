package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.Customer;
import com.apptium.customer.repository.CustomerRepository;
import com.apptium.customer.service.criteria.CustomerCriteria;
import com.apptium.customer.service.dto.CustomerDTO;
import com.apptium.customer.service.mapper.CustomerMapper;
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
 * Service for executing complex queries for {@link Customer} entities in the database.
 * The main input is a {@link CustomerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CustomerDTO} or a {@link Page} of {@link CustomerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CustomerQueryService extends QueryService<Customer> {

    private final Logger log = LoggerFactory.getLogger(CustomerQueryService.class);

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    public CustomerQueryService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    /**
     * Return a {@link List} of {@link CustomerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerDTO> findByCriteria(CustomerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Customer> specification = createSpecification(criteria);
        return customerMapper.toDto(customerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CustomerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerDTO> findByCriteria(CustomerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Customer> specification = createSpecification(criteria);
        return customerRepository.findAll(specification, page).map(customerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CustomerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Customer> specification = createSpecification(criteria);
        return customerRepository.count(specification);
    }

    /**
     * Function to convert {@link CustomerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Customer> createSpecification(CustomerCriteria criteria) {
        Specification<Customer> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Customer_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Customer_.name));
            }
            if (criteria.getFormattedName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFormattedName(), Customer_.formattedName));
            }
            if (criteria.getTradingName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTradingName(), Customer_.tradingName));
            }
            if (criteria.getCustType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustType(), Customer_.custType));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Customer_.title));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), Customer_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), Customer_.lastName));
            }
            if (criteria.getMiddleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMiddleName(), Customer_.middleName));
            }
            if (criteria.getDateOfBirth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfBirth(), Customer_.dateOfBirth));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGender(), Customer_.gender));
            }
            if (criteria.getMaritalStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaritalStatus(), Customer_.maritalStatus));
            }
            if (criteria.getNationality() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNationality(), Customer_.nationality));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Customer_.status));
            }
            if (criteria.getCustomerEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustomerEmail(), Customer_.customerEmail));
            }
            if (criteria.getCompanyIdType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompanyIdType(), Customer_.companyIdType));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyId(), Customer_.companyId));
            }
            if (criteria.getPrimaryConAdminIndId() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPrimaryConAdminIndId(), Customer_.primaryConAdminIndId));
            }
            if (criteria.getCustBillingAccId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustBillingAccId(),
                            root -> root.join(Customer_.custBillingAcc, JoinType.LEFT).get(CustBillingAcc_.id)
                        )
                    );
            }
            if (criteria.getCustCreditProfileId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustCreditProfileId(),
                            root -> root.join(Customer_.custCreditProfile, JoinType.LEFT).get(CustCreditProfile_.id)
                        )
                    );
            }
            if (criteria.getCustBillingRefId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustBillingRefId(),
                            root -> root.join(Customer_.custBillingRef, JoinType.LEFT).get(CustBillingRef_.id)
                        )
                    );
            }
            if (criteria.getCustContactId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustContactId(),
                            root -> root.join(Customer_.custContacts, JoinType.LEFT).get(CustContact_.id)
                        )
                    );
            }
            if (criteria.getCustStatisticsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustStatisticsId(),
                            root -> root.join(Customer_.custStatistics, JoinType.LEFT).get(CustStatistics_.id)
                        )
                    );
            }
            if (criteria.getCustCharId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustCharId(),
                            root -> root.join(Customer_.custChars, JoinType.LEFT).get(CustChar_.id)
                        )
                    );
            }
            if (criteria.getCustCommunicationRefId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustCommunicationRefId(),
                            root -> root.join(Customer_.custCommunicationRefs, JoinType.LEFT).get(CustCommunicationRef_.id)
                        )
                    );
            }
            if (criteria.getCustPasswordCharId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustPasswordCharId(),
                            root -> root.join(Customer_.custPasswordChars, JoinType.LEFT).get(CustPasswordChar_.id)
                        )
                    );
            }
            if (criteria.getCustNewsLetterConfigId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustNewsLetterConfigId(),
                            root -> root.join(Customer_.custNewsLetterConfigs, JoinType.LEFT).get(CustNewsLetterConfig_.id)
                        )
                    );
            }
            if (criteria.getCustSecurityCharId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustSecurityCharId(),
                            root -> root.join(Customer_.custSecurityChars, JoinType.LEFT).get(CustSecurityChar_.id)
                        )
                    );
            }
            if (criteria.getCustRelPartyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustRelPartyId(),
                            root -> root.join(Customer_.custRelParties, JoinType.LEFT).get(CustRelParty_.id)
                        )
                    );
            }
            if (criteria.getCustISVRefId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustISVRefId(),
                            root -> root.join(Customer_.custISVRefs, JoinType.LEFT).get(CustISVRef_.id)
                        )
                    );
            }
            if (criteria.getShoppingSessionRefId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getShoppingSessionRefId(),
                            root -> root.join(Customer_.shoppingSessionRefs, JoinType.LEFT).get(ShoppingSessionRef_.id)
                        )
                    );
            }
            if (criteria.getIndustryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getIndustryId(), root -> root.join(Customer_.industry, JoinType.LEFT).get(Industry_.id))
                    );
            }
        }
        return specification;
    }
}
