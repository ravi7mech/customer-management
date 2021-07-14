package com.apptium.customer.service.impl;

import com.apptium.customer.domain.CustSecurityChar;
import com.apptium.customer.repository.CustSecurityCharRepository;
import com.apptium.customer.service.CustSecurityCharService;
import com.apptium.customer.service.dto.CustSecurityCharDTO;
import com.apptium.customer.service.mapper.CustSecurityCharMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustSecurityChar}.
 */
@Service
@Transactional
public class CustSecurityCharServiceImpl implements CustSecurityCharService {

    private final Logger log = LoggerFactory.getLogger(CustSecurityCharServiceImpl.class);

    private final CustSecurityCharRepository custSecurityCharRepository;

    private final CustSecurityCharMapper custSecurityCharMapper;

    public CustSecurityCharServiceImpl(
        CustSecurityCharRepository custSecurityCharRepository,
        CustSecurityCharMapper custSecurityCharMapper
    ) {
        this.custSecurityCharRepository = custSecurityCharRepository;
        this.custSecurityCharMapper = custSecurityCharMapper;
    }

    @Override
    public CustSecurityCharDTO save(CustSecurityCharDTO custSecurityCharDTO) {
        log.debug("Request to save CustSecurityChar : {}", custSecurityCharDTO);
        CustSecurityChar custSecurityChar = custSecurityCharMapper.toEntity(custSecurityCharDTO);
        custSecurityChar = custSecurityCharRepository.save(custSecurityChar);
        return custSecurityCharMapper.toDto(custSecurityChar);
    }

    @Override
    public Optional<CustSecurityCharDTO> partialUpdate(CustSecurityCharDTO custSecurityCharDTO) {
        log.debug("Request to partially update CustSecurityChar : {}", custSecurityCharDTO);

        return custSecurityCharRepository
            .findById(custSecurityCharDTO.getId())
            .map(
                existingCustSecurityChar -> {
                    custSecurityCharMapper.partialUpdate(existingCustSecurityChar, custSecurityCharDTO);

                    return existingCustSecurityChar;
                }
            )
            .map(custSecurityCharRepository::save)
            .map(custSecurityCharMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustSecurityCharDTO> findAll() {
        log.debug("Request to get all CustSecurityChars");
        return custSecurityCharRepository
            .findAll()
            .stream()
            .map(custSecurityCharMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustSecurityCharDTO> findOne(Long id) {
        log.debug("Request to get CustSecurityChar : {}", id);
        return custSecurityCharRepository.findById(id).map(custSecurityCharMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustSecurityChar : {}", id);
        custSecurityCharRepository.deleteById(id);
    }
}
