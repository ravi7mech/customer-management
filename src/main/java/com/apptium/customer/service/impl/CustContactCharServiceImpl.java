package com.apptium.customer.service.impl;

import com.apptium.customer.domain.CustContactChar;
import com.apptium.customer.repository.CustContactCharRepository;
import com.apptium.customer.service.CustContactCharService;
import com.apptium.customer.service.dto.CustContactCharDTO;
import com.apptium.customer.service.mapper.CustContactCharMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustContactChar}.
 */
@Service
@Transactional
public class CustContactCharServiceImpl implements CustContactCharService {

    private final Logger log = LoggerFactory.getLogger(CustContactCharServiceImpl.class);

    private final CustContactCharRepository custContactCharRepository;

    private final CustContactCharMapper custContactCharMapper;

    public CustContactCharServiceImpl(CustContactCharRepository custContactCharRepository, CustContactCharMapper custContactCharMapper) {
        this.custContactCharRepository = custContactCharRepository;
        this.custContactCharMapper = custContactCharMapper;
    }

    @Override
    public CustContactCharDTO save(CustContactCharDTO custContactCharDTO) {
        log.debug("Request to save CustContactChar : {}", custContactCharDTO);
        CustContactChar custContactChar = custContactCharMapper.toEntity(custContactCharDTO);
        custContactChar = custContactCharRepository.save(custContactChar);
        return custContactCharMapper.toDto(custContactChar);
    }

    @Override
    public Optional<CustContactCharDTO> partialUpdate(CustContactCharDTO custContactCharDTO) {
        log.debug("Request to partially update CustContactChar : {}", custContactCharDTO);

        return custContactCharRepository
            .findById(custContactCharDTO.getId())
            .map(
                existingCustContactChar -> {
                    custContactCharMapper.partialUpdate(existingCustContactChar, custContactCharDTO);

                    return existingCustContactChar;
                }
            )
            .map(custContactCharRepository::save)
            .map(custContactCharMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustContactCharDTO> findAll() {
        log.debug("Request to get all CustContactChars");
        return custContactCharRepository
            .findAll()
            .stream()
            .map(custContactCharMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustContactCharDTO> findOne(Long id) {
        log.debug("Request to get CustContactChar : {}", id);
        return custContactCharRepository.findById(id).map(custContactCharMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustContactChar : {}", id);
        custContactCharRepository.deleteById(id);
    }
}
