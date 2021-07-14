package com.apptium.customer.service.impl;

import com.apptium.customer.domain.CustPasswordChar;
import com.apptium.customer.repository.CustPasswordCharRepository;
import com.apptium.customer.service.CustPasswordCharService;
import com.apptium.customer.service.dto.CustPasswordCharDTO;
import com.apptium.customer.service.mapper.CustPasswordCharMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustPasswordChar}.
 */
@Service
@Transactional
public class CustPasswordCharServiceImpl implements CustPasswordCharService {

    private final Logger log = LoggerFactory.getLogger(CustPasswordCharServiceImpl.class);

    private final CustPasswordCharRepository custPasswordCharRepository;

    private final CustPasswordCharMapper custPasswordCharMapper;

    public CustPasswordCharServiceImpl(
        CustPasswordCharRepository custPasswordCharRepository,
        CustPasswordCharMapper custPasswordCharMapper
    ) {
        this.custPasswordCharRepository = custPasswordCharRepository;
        this.custPasswordCharMapper = custPasswordCharMapper;
    }

    @Override
    public CustPasswordCharDTO save(CustPasswordCharDTO custPasswordCharDTO) {
        log.debug("Request to save CustPasswordChar : {}", custPasswordCharDTO);
        CustPasswordChar custPasswordChar = custPasswordCharMapper.toEntity(custPasswordCharDTO);
        custPasswordChar = custPasswordCharRepository.save(custPasswordChar);
        return custPasswordCharMapper.toDto(custPasswordChar);
    }

    @Override
    public Optional<CustPasswordCharDTO> partialUpdate(CustPasswordCharDTO custPasswordCharDTO) {
        log.debug("Request to partially update CustPasswordChar : {}", custPasswordCharDTO);

        return custPasswordCharRepository
            .findById(custPasswordCharDTO.getId())
            .map(
                existingCustPasswordChar -> {
                    custPasswordCharMapper.partialUpdate(existingCustPasswordChar, custPasswordCharDTO);

                    return existingCustPasswordChar;
                }
            )
            .map(custPasswordCharRepository::save)
            .map(custPasswordCharMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustPasswordCharDTO> findAll() {
        log.debug("Request to get all CustPasswordChars");
        return custPasswordCharRepository
            .findAll()
            .stream()
            .map(custPasswordCharMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustPasswordCharDTO> findOne(Long id) {
        log.debug("Request to get CustPasswordChar : {}", id);
        return custPasswordCharRepository.findById(id).map(custPasswordCharMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustPasswordChar : {}", id);
        custPasswordCharRepository.deleteById(id);
    }
}
