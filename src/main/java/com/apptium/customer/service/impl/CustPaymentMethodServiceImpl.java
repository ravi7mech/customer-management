package com.apptium.customer.service.impl;

import com.apptium.customer.domain.CustPaymentMethod;
import com.apptium.customer.repository.CustPaymentMethodRepository;
import com.apptium.customer.service.CustPaymentMethodService;
import com.apptium.customer.service.dto.CustPaymentMethodDTO;
import com.apptium.customer.service.mapper.CustPaymentMethodMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustPaymentMethod}.
 */
@Service
@Transactional
public class CustPaymentMethodServiceImpl implements CustPaymentMethodService {

    private final Logger log = LoggerFactory.getLogger(CustPaymentMethodServiceImpl.class);

    private final CustPaymentMethodRepository custPaymentMethodRepository;

    private final CustPaymentMethodMapper custPaymentMethodMapper;

    public CustPaymentMethodServiceImpl(
        CustPaymentMethodRepository custPaymentMethodRepository,
        CustPaymentMethodMapper custPaymentMethodMapper
    ) {
        this.custPaymentMethodRepository = custPaymentMethodRepository;
        this.custPaymentMethodMapper = custPaymentMethodMapper;
    }

    @Override
    public CustPaymentMethodDTO save(CustPaymentMethodDTO custPaymentMethodDTO) {
        log.debug("Request to save CustPaymentMethod : {}", custPaymentMethodDTO);
        CustPaymentMethod custPaymentMethod = custPaymentMethodMapper.toEntity(custPaymentMethodDTO);
        custPaymentMethod = custPaymentMethodRepository.save(custPaymentMethod);
        return custPaymentMethodMapper.toDto(custPaymentMethod);
    }

    @Override
    public Optional<CustPaymentMethodDTO> partialUpdate(CustPaymentMethodDTO custPaymentMethodDTO) {
        log.debug("Request to partially update CustPaymentMethod : {}", custPaymentMethodDTO);

        return custPaymentMethodRepository
            .findById(custPaymentMethodDTO.getId())
            .map(
                existingCustPaymentMethod -> {
                    custPaymentMethodMapper.partialUpdate(existingCustPaymentMethod, custPaymentMethodDTO);

                    return existingCustPaymentMethod;
                }
            )
            .map(custPaymentMethodRepository::save)
            .map(custPaymentMethodMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustPaymentMethodDTO> findAll() {
        log.debug("Request to get all CustPaymentMethods");
        return custPaymentMethodRepository
            .findAll()
            .stream()
            .map(custPaymentMethodMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustPaymentMethodDTO> findOne(Long id) {
        log.debug("Request to get CustPaymentMethod : {}", id);
        return custPaymentMethodRepository.findById(id).map(custPaymentMethodMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustPaymentMethod : {}", id);
        custPaymentMethodRepository.deleteById(id);
    }
}
