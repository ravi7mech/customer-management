package com.apptium.customer.service.impl;

import com.apptium.customer.domain.CustContact;
import com.apptium.customer.repository.CustContactRepository;
import com.apptium.customer.service.CustContactService;
import com.apptium.customer.service.dto.CustContactDTO;
import com.apptium.customer.service.mapper.CustContactMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustContact}.
 */
@Service
@Transactional
public class CustContactServiceImpl implements CustContactService {

    private final Logger log = LoggerFactory.getLogger(CustContactServiceImpl.class);

    private final CustContactRepository custContactRepository;

    private final CustContactMapper custContactMapper;

    public CustContactServiceImpl(CustContactRepository custContactRepository, CustContactMapper custContactMapper) {
        this.custContactRepository = custContactRepository;
        this.custContactMapper = custContactMapper;
    }

    @Override
    public CustContactDTO save(CustContactDTO custContactDTO) {
        log.debug("Request to save CustContact : {}", custContactDTO);
        CustContact custContact = custContactMapper.toEntity(custContactDTO);
        custContact = custContactRepository.save(custContact);
        return custContactMapper.toDto(custContact);
    }

    @Override
    public Optional<CustContactDTO> partialUpdate(CustContactDTO custContactDTO) {
        log.debug("Request to partially update CustContact : {}", custContactDTO);

        return custContactRepository
            .findById(custContactDTO.getId())
            .map(
                existingCustContact -> {
                    custContactMapper.partialUpdate(existingCustContact, custContactDTO);

                    return existingCustContact;
                }
            )
            .map(custContactRepository::save)
            .map(custContactMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustContactDTO> findAll() {
        log.debug("Request to get all CustContacts");
        return custContactRepository.findAll().stream().map(custContactMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustContactDTO> findOne(Long id) {
        log.debug("Request to get CustContact : {}", id);
        return custContactRepository.findById(id).map(custContactMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustContact : {}", id);
        custContactRepository.deleteById(id);
    }
}
