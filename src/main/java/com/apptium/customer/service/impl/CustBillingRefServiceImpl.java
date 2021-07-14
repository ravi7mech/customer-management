package com.apptium.customer.service.impl;

import com.apptium.customer.domain.CustBillingRef;
import com.apptium.customer.repository.CustBillingRefRepository;
import com.apptium.customer.service.CustBillingRefService;
import com.apptium.customer.service.dto.CustBillingRefDTO;
import com.apptium.customer.service.mapper.CustBillingRefMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustBillingRef}.
 */
@Service
@Transactional
public class CustBillingRefServiceImpl implements CustBillingRefService {

    private final Logger log = LoggerFactory.getLogger(CustBillingRefServiceImpl.class);

    private final CustBillingRefRepository custBillingRefRepository;

    private final CustBillingRefMapper custBillingRefMapper;

    public CustBillingRefServiceImpl(CustBillingRefRepository custBillingRefRepository, CustBillingRefMapper custBillingRefMapper) {
        this.custBillingRefRepository = custBillingRefRepository;
        this.custBillingRefMapper = custBillingRefMapper;
    }

    @Override
    public CustBillingRefDTO save(CustBillingRefDTO custBillingRefDTO) {
        log.debug("Request to save CustBillingRef : {}", custBillingRefDTO);
        CustBillingRef custBillingRef = custBillingRefMapper.toEntity(custBillingRefDTO);
        custBillingRef = custBillingRefRepository.save(custBillingRef);
        return custBillingRefMapper.toDto(custBillingRef);
    }

    @Override
    public Optional<CustBillingRefDTO> partialUpdate(CustBillingRefDTO custBillingRefDTO) {
        log.debug("Request to partially update CustBillingRef : {}", custBillingRefDTO);

        return custBillingRefRepository
            .findById(custBillingRefDTO.getId())
            .map(
                existingCustBillingRef -> {
                    custBillingRefMapper.partialUpdate(existingCustBillingRef, custBillingRefDTO);

                    return existingCustBillingRef;
                }
            )
            .map(custBillingRefRepository::save)
            .map(custBillingRefMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustBillingRefDTO> findAll() {
        log.debug("Request to get all CustBillingRefs");
        return custBillingRefRepository
            .findAll()
            .stream()
            .map(custBillingRefMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the custBillingRefs where Customer is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CustBillingRefDTO> findAllWhereCustomerIsNull() {
        log.debug("Request to get all custBillingRefs where Customer is null");
        return StreamSupport
            .stream(custBillingRefRepository.findAll().spliterator(), false)
            .filter(custBillingRef -> custBillingRef.getCustomer() == null)
            .map(custBillingRefMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustBillingRefDTO> findOne(Long id) {
        log.debug("Request to get CustBillingRef : {}", id);
        return custBillingRefRepository.findById(id).map(custBillingRefMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustBillingRef : {}", id);
        custBillingRefRepository.deleteById(id);
    }
}
