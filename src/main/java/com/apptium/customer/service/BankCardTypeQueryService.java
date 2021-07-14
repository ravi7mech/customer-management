package com.apptium.customer.service;

import com.apptium.customer.domain.*; // for static metamodels
import com.apptium.customer.domain.BankCardType;
import com.apptium.customer.repository.BankCardTypeRepository;
import com.apptium.customer.service.criteria.BankCardTypeCriteria;
import com.apptium.customer.service.dto.BankCardTypeDTO;
import com.apptium.customer.service.mapper.BankCardTypeMapper;
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
 * Service for executing complex queries for {@link BankCardType} entities in the database.
 * The main input is a {@link BankCardTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BankCardTypeDTO} or a {@link Page} of {@link BankCardTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BankCardTypeQueryService extends QueryService<BankCardType> {

    private final Logger log = LoggerFactory.getLogger(BankCardTypeQueryService.class);

    private final BankCardTypeRepository bankCardTypeRepository;

    private final BankCardTypeMapper bankCardTypeMapper;

    public BankCardTypeQueryService(BankCardTypeRepository bankCardTypeRepository, BankCardTypeMapper bankCardTypeMapper) {
        this.bankCardTypeRepository = bankCardTypeRepository;
        this.bankCardTypeMapper = bankCardTypeMapper;
    }

    /**
     * Return a {@link List} of {@link BankCardTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BankCardTypeDTO> findByCriteria(BankCardTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BankCardType> specification = createSpecification(criteria);
        return bankCardTypeMapper.toDto(bankCardTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BankCardTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BankCardTypeDTO> findByCriteria(BankCardTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BankCardType> specification = createSpecification(criteria);
        return bankCardTypeRepository.findAll(specification, page).map(bankCardTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BankCardTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BankCardType> specification = createSpecification(criteria);
        return bankCardTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link BankCardTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BankCardType> createSpecification(BankCardTypeCriteria criteria) {
        Specification<BankCardType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BankCardType_.id));
            }
            if (criteria.getBrand() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBrand(), BankCardType_.brand));
            }
            if (criteria.getCardType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCardType(), BankCardType_.cardType));
            }
            if (criteria.getCardNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCardNumber(), BankCardType_.cardNumber));
            }
            if (criteria.getExpirationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpirationDate(), BankCardType_.expirationDate));
            }
            if (criteria.getCvv() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCvv(), BankCardType_.cvv));
            }
            if (criteria.getLastFourDigits() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastFourDigits(), BankCardType_.lastFourDigits));
            }
            if (criteria.getBank() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBank(), BankCardType_.bank));
            }
            if (criteria.getCustPaymentMethodId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCustPaymentMethodId(),
                            root -> root.join(BankCardType_.custPaymentMethod, JoinType.LEFT).get(CustPaymentMethod_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
