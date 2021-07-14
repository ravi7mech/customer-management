package com.apptium.customer.service.impl;

import com.apptium.customer.domain.CustChar;
import com.apptium.customer.repository.CustCharRepository;
import com.apptium.customer.service.CustCharService;
import com.apptium.customer.service.dto.CustCharDTO;
import com.apptium.customer.service.mapper.CustCharMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustChar}.
 */
@Service
@Transactional
public class CustCharServiceImpl implements CustCharService {

    private final Logger log = LoggerFactory.getLogger(CustCharServiceImpl.class);

    private final CustCharRepository custCharRepository;

    private final CustCharMapper custCharMapper;

    public CustCharServiceImpl(CustCharRepository custCharRepository, CustCharMapper custCharMapper) {
        this.custCharRepository = custCharRepository;
        this.custCharMapper = custCharMapper;
    }

    @Override
    public CustCharDTO save(CustCharDTO custCharDTO) {
        log.debug("Request to save CustChar : {}", custCharDTO);
        CustChar custChar = custCharMapper.toEntity(custCharDTO);
        custChar = custCharRepository.save(custChar);
        return custCharMapper.toDto(custChar);
    }

    @Override
    public Optional<CustCharDTO> partialUpdate(CustCharDTO custCharDTO) {
        log.debug("Request to partially update CustChar : {}", custCharDTO);

        return custCharRepository
            .findById(custCharDTO.getId())
            .map(
                existingCustChar -> {
                    custCharMapper.partialUpdate(existingCustChar, custCharDTO);

                    return existingCustChar;
                }
            )
            .map(custCharRepository::save)
            .map(custCharMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustCharDTO> findAll() {
        log.debug("Request to get all CustChars");
        return custCharRepository.findAll().stream().map(custCharMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustCharDTO> findOne(Long id) {
        log.debug("Request to get CustChar : {}", id);
        return custCharRepository.findById(id).map(custCharMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustChar : {}", id);
        custCharRepository.deleteById(id);
    }
}
