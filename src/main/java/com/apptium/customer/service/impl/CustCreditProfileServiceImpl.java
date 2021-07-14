package com.apptium.customer.service.impl;

import com.apptium.customer.domain.CustCreditProfile;
import com.apptium.customer.repository.CustCreditProfileRepository;
import com.apptium.customer.service.CustCreditProfileService;
import com.apptium.customer.service.dto.CustCreditProfileDTO;
import com.apptium.customer.service.mapper.CustCreditProfileMapper;
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
 * Service Implementation for managing {@link CustCreditProfile}.
 */
@Service
@Transactional
public class CustCreditProfileServiceImpl implements CustCreditProfileService {

    private final Logger log = LoggerFactory.getLogger(CustCreditProfileServiceImpl.class);

    private final CustCreditProfileRepository custCreditProfileRepository;

    private final CustCreditProfileMapper custCreditProfileMapper;

    public CustCreditProfileServiceImpl(
        CustCreditProfileRepository custCreditProfileRepository,
        CustCreditProfileMapper custCreditProfileMapper
    ) {
        this.custCreditProfileRepository = custCreditProfileRepository;
        this.custCreditProfileMapper = custCreditProfileMapper;
    }

    @Override
    public CustCreditProfileDTO save(CustCreditProfileDTO custCreditProfileDTO) {
        log.debug("Request to save CustCreditProfile : {}", custCreditProfileDTO);
        CustCreditProfile custCreditProfile = custCreditProfileMapper.toEntity(custCreditProfileDTO);
        custCreditProfile = custCreditProfileRepository.save(custCreditProfile);
        return custCreditProfileMapper.toDto(custCreditProfile);
    }

    @Override
    public Optional<CustCreditProfileDTO> partialUpdate(CustCreditProfileDTO custCreditProfileDTO) {
        log.debug("Request to partially update CustCreditProfile : {}", custCreditProfileDTO);

        return custCreditProfileRepository
            .findById(custCreditProfileDTO.getId())
            .map(
                existingCustCreditProfile -> {
                    custCreditProfileMapper.partialUpdate(existingCustCreditProfile, custCreditProfileDTO);

                    return existingCustCreditProfile;
                }
            )
            .map(custCreditProfileRepository::save)
            .map(custCreditProfileMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustCreditProfileDTO> findAll() {
        log.debug("Request to get all CustCreditProfiles");
        return custCreditProfileRepository
            .findAll()
            .stream()
            .map(custCreditProfileMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the custCreditProfiles where Customer is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CustCreditProfileDTO> findAllWhereCustomerIsNull() {
        log.debug("Request to get all custCreditProfiles where Customer is null");
        return StreamSupport
            .stream(custCreditProfileRepository.findAll().spliterator(), false)
            .filter(custCreditProfile -> custCreditProfile.getCustomer() == null)
            .map(custCreditProfileMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustCreditProfileDTO> findOne(Long id) {
        log.debug("Request to get CustCreditProfile : {}", id);
        return custCreditProfileRepository.findById(id).map(custCreditProfileMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustCreditProfile : {}", id);
        custCreditProfileRepository.deleteById(id);
    }
}
