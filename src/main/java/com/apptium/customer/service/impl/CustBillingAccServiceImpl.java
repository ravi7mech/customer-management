package com.apptium.customer.service.impl;

import com.apptium.customer.domain.CustBillingAcc;
import com.apptium.customer.repository.CustBillingAccRepository;
import com.apptium.customer.service.CustBillingAccService;
import com.apptium.customer.service.dto.CustBillingAccDTO;
import com.apptium.customer.service.mapper.CustBillingAccMapper;
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
 * Service Implementation for managing {@link CustBillingAcc}.
 */
@Service
@Transactional
public class CustBillingAccServiceImpl implements CustBillingAccService {

    private final Logger log = LoggerFactory.getLogger(CustBillingAccServiceImpl.class);

    private final CustBillingAccRepository custBillingAccRepository;

    private final CustBillingAccMapper custBillingAccMapper;

    public CustBillingAccServiceImpl(CustBillingAccRepository custBillingAccRepository, CustBillingAccMapper custBillingAccMapper) {
        this.custBillingAccRepository = custBillingAccRepository;
        this.custBillingAccMapper = custBillingAccMapper;
    }

    @Override
    public CustBillingAccDTO save(CustBillingAccDTO custBillingAccDTO) {
        log.debug("Request to save CustBillingAcc : {}", custBillingAccDTO);
        CustBillingAcc custBillingAcc = custBillingAccMapper.toEntity(custBillingAccDTO);
        custBillingAcc = custBillingAccRepository.save(custBillingAcc);
        return custBillingAccMapper.toDto(custBillingAcc);
    }

    @Override
    public Optional<CustBillingAccDTO> partialUpdate(CustBillingAccDTO custBillingAccDTO) {
        log.debug("Request to partially update CustBillingAcc : {}", custBillingAccDTO);

        return custBillingAccRepository
            .findById(custBillingAccDTO.getId())
            .map(
                existingCustBillingAcc -> {
                    custBillingAccMapper.partialUpdate(existingCustBillingAcc, custBillingAccDTO);

                    return existingCustBillingAcc;
                }
            )
            .map(custBillingAccRepository::save)
            .map(custBillingAccMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustBillingAccDTO> findAll() {
        log.debug("Request to get all CustBillingAccs");
        return custBillingAccRepository
            .findAll()
            .stream()
            .map(custBillingAccMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the custBillingAccs where Customer is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CustBillingAccDTO> findAllWhereCustomerIsNull() {
        log.debug("Request to get all custBillingAccs where Customer is null");
        return StreamSupport
            .stream(custBillingAccRepository.findAll().spliterator(), false)
            .filter(custBillingAcc -> custBillingAcc.getCustomer() == null)
            .map(custBillingAccMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustBillingAccDTO> findOne(Long id) {
        log.debug("Request to get CustBillingAcc : {}", id);
        return custBillingAccRepository.findById(id).map(custBillingAccMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustBillingAcc : {}", id);
        custBillingAccRepository.deleteById(id);
    }
}
