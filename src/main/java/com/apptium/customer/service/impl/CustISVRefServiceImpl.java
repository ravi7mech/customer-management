package com.apptium.customer.service.impl;

import com.apptium.customer.domain.CustISVRef;
import com.apptium.customer.repository.CustISVRefRepository;
import com.apptium.customer.service.CustISVRefService;
import com.apptium.customer.service.dto.CustISVRefDTO;
import com.apptium.customer.service.mapper.CustISVRefMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustISVRef}.
 */
@Service
@Transactional
public class CustISVRefServiceImpl implements CustISVRefService {

    private final Logger log = LoggerFactory.getLogger(CustISVRefServiceImpl.class);

    private final CustISVRefRepository custISVRefRepository;

    private final CustISVRefMapper custISVRefMapper;

    public CustISVRefServiceImpl(CustISVRefRepository custISVRefRepository, CustISVRefMapper custISVRefMapper) {
        this.custISVRefRepository = custISVRefRepository;
        this.custISVRefMapper = custISVRefMapper;
    }

    @Override
    public CustISVRefDTO save(CustISVRefDTO custISVRefDTO) {
        log.debug("Request to save CustISVRef : {}", custISVRefDTO);
        CustISVRef custISVRef = custISVRefMapper.toEntity(custISVRefDTO);
        custISVRef = custISVRefRepository.save(custISVRef);
        return custISVRefMapper.toDto(custISVRef);
    }

    @Override
    public Optional<CustISVRefDTO> partialUpdate(CustISVRefDTO custISVRefDTO) {
        log.debug("Request to partially update CustISVRef : {}", custISVRefDTO);

        return custISVRefRepository
            .findById(custISVRefDTO.getId())
            .map(
                existingCustISVRef -> {
                    custISVRefMapper.partialUpdate(existingCustISVRef, custISVRefDTO);

                    return existingCustISVRef;
                }
            )
            .map(custISVRefRepository::save)
            .map(custISVRefMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustISVRefDTO> findAll() {
        log.debug("Request to get all CustISVRefs");
        return custISVRefRepository.findAll().stream().map(custISVRefMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustISVRefDTO> findOne(Long id) {
        log.debug("Request to get CustISVRef : {}", id);
        return custISVRefRepository.findById(id).map(custISVRefMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustISVRef : {}", id);
        custISVRefRepository.deleteById(id);
    }
}
