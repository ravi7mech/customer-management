package com.apptium.customer.service.impl;

import com.apptium.customer.domain.CustCommunicationRef;
import com.apptium.customer.repository.CustCommunicationRefRepository;
import com.apptium.customer.service.CustCommunicationRefService;
import com.apptium.customer.service.dto.CustCommunicationRefDTO;
import com.apptium.customer.service.mapper.CustCommunicationRefMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustCommunicationRef}.
 */
@Service
@Transactional
public class CustCommunicationRefServiceImpl implements CustCommunicationRefService {

    private final Logger log = LoggerFactory.getLogger(CustCommunicationRefServiceImpl.class);

    private final CustCommunicationRefRepository custCommunicationRefRepository;

    private final CustCommunicationRefMapper custCommunicationRefMapper;

    public CustCommunicationRefServiceImpl(
        CustCommunicationRefRepository custCommunicationRefRepository,
        CustCommunicationRefMapper custCommunicationRefMapper
    ) {
        this.custCommunicationRefRepository = custCommunicationRefRepository;
        this.custCommunicationRefMapper = custCommunicationRefMapper;
    }

    @Override
    public CustCommunicationRefDTO save(CustCommunicationRefDTO custCommunicationRefDTO) {
        log.debug("Request to save CustCommunicationRef : {}", custCommunicationRefDTO);
        CustCommunicationRef custCommunicationRef = custCommunicationRefMapper.toEntity(custCommunicationRefDTO);
        custCommunicationRef = custCommunicationRefRepository.save(custCommunicationRef);
        return custCommunicationRefMapper.toDto(custCommunicationRef);
    }

    @Override
    public Optional<CustCommunicationRefDTO> partialUpdate(CustCommunicationRefDTO custCommunicationRefDTO) {
        log.debug("Request to partially update CustCommunicationRef : {}", custCommunicationRefDTO);

        return custCommunicationRefRepository
            .findById(custCommunicationRefDTO.getId())
            .map(
                existingCustCommunicationRef -> {
                    custCommunicationRefMapper.partialUpdate(existingCustCommunicationRef, custCommunicationRefDTO);

                    return existingCustCommunicationRef;
                }
            )
            .map(custCommunicationRefRepository::save)
            .map(custCommunicationRefMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustCommunicationRefDTO> findAll() {
        log.debug("Request to get all CustCommunicationRefs");
        return custCommunicationRefRepository
            .findAll()
            .stream()
            .map(custCommunicationRefMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustCommunicationRefDTO> findOne(Long id) {
        log.debug("Request to get CustCommunicationRef : {}", id);
        return custCommunicationRefRepository.findById(id).map(custCommunicationRefMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustCommunicationRef : {}", id);
        custCommunicationRefRepository.deleteById(id);
    }
}
