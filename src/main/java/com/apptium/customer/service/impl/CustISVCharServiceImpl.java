package com.apptium.customer.service.impl;

import com.apptium.customer.domain.CustISVChar;
import com.apptium.customer.repository.CustISVCharRepository;
import com.apptium.customer.service.CustISVCharService;
import com.apptium.customer.service.dto.CustISVCharDTO;
import com.apptium.customer.service.mapper.CustISVCharMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustISVChar}.
 */
@Service
@Transactional
public class CustISVCharServiceImpl implements CustISVCharService {

    private final Logger log = LoggerFactory.getLogger(CustISVCharServiceImpl.class);

    private final CustISVCharRepository custISVCharRepository;

    private final CustISVCharMapper custISVCharMapper;

    public CustISVCharServiceImpl(CustISVCharRepository custISVCharRepository, CustISVCharMapper custISVCharMapper) {
        this.custISVCharRepository = custISVCharRepository;
        this.custISVCharMapper = custISVCharMapper;
    }

    @Override
    public CustISVCharDTO save(CustISVCharDTO custISVCharDTO) {
        log.debug("Request to save CustISVChar : {}", custISVCharDTO);
        CustISVChar custISVChar = custISVCharMapper.toEntity(custISVCharDTO);
        custISVChar = custISVCharRepository.save(custISVChar);
        return custISVCharMapper.toDto(custISVChar);
    }

    @Override
    public Optional<CustISVCharDTO> partialUpdate(CustISVCharDTO custISVCharDTO) {
        log.debug("Request to partially update CustISVChar : {}", custISVCharDTO);

        return custISVCharRepository
            .findById(custISVCharDTO.getId())
            .map(
                existingCustISVChar -> {
                    custISVCharMapper.partialUpdate(existingCustISVChar, custISVCharDTO);

                    return existingCustISVChar;
                }
            )
            .map(custISVCharRepository::save)
            .map(custISVCharMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustISVCharDTO> findAll() {
        log.debug("Request to get all CustISVChars");
        return custISVCharRepository.findAll().stream().map(custISVCharMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustISVCharDTO> findOne(Long id) {
        log.debug("Request to get CustISVChar : {}", id);
        return custISVCharRepository.findById(id).map(custISVCharMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustISVChar : {}", id);
        custISVCharRepository.deleteById(id);
    }
}
