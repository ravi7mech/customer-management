package com.apptium.customer.service.impl;

import com.apptium.customer.domain.CustRelParty;
import com.apptium.customer.repository.CustRelPartyRepository;
import com.apptium.customer.service.CustRelPartyService;
import com.apptium.customer.service.dto.CustRelPartyDTO;
import com.apptium.customer.service.mapper.CustRelPartyMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustRelParty}.
 */
@Service
@Transactional
public class CustRelPartyServiceImpl implements CustRelPartyService {

    private final Logger log = LoggerFactory.getLogger(CustRelPartyServiceImpl.class);

    private final CustRelPartyRepository custRelPartyRepository;

    private final CustRelPartyMapper custRelPartyMapper;

    public CustRelPartyServiceImpl(CustRelPartyRepository custRelPartyRepository, CustRelPartyMapper custRelPartyMapper) {
        this.custRelPartyRepository = custRelPartyRepository;
        this.custRelPartyMapper = custRelPartyMapper;
    }

    @Override
    public CustRelPartyDTO save(CustRelPartyDTO custRelPartyDTO) {
        log.debug("Request to save CustRelParty : {}", custRelPartyDTO);
        CustRelParty custRelParty = custRelPartyMapper.toEntity(custRelPartyDTO);
        custRelParty = custRelPartyRepository.save(custRelParty);
        return custRelPartyMapper.toDto(custRelParty);
    }

    @Override
    public Optional<CustRelPartyDTO> partialUpdate(CustRelPartyDTO custRelPartyDTO) {
        log.debug("Request to partially update CustRelParty : {}", custRelPartyDTO);

        return custRelPartyRepository
            .findById(custRelPartyDTO.getId())
            .map(
                existingCustRelParty -> {
                    custRelPartyMapper.partialUpdate(existingCustRelParty, custRelPartyDTO);

                    return existingCustRelParty;
                }
            )
            .map(custRelPartyRepository::save)
            .map(custRelPartyMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustRelPartyDTO> findAll() {
        log.debug("Request to get all CustRelParties");
        return custRelPartyRepository.findAll().stream().map(custRelPartyMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustRelPartyDTO> findOne(Long id) {
        log.debug("Request to get CustRelParty : {}", id);
        return custRelPartyRepository.findById(id).map(custRelPartyMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustRelParty : {}", id);
        custRelPartyRepository.deleteById(id);
    }
}
