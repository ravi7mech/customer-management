package com.apptium.customer.service.impl;

import com.apptium.customer.domain.BankCardType;
import com.apptium.customer.repository.BankCardTypeRepository;
import com.apptium.customer.service.BankCardTypeService;
import com.apptium.customer.service.dto.BankCardTypeDTO;
import com.apptium.customer.service.mapper.BankCardTypeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BankCardType}.
 */
@Service
@Transactional
public class BankCardTypeServiceImpl implements BankCardTypeService {

    private final Logger log = LoggerFactory.getLogger(BankCardTypeServiceImpl.class);

    private final BankCardTypeRepository bankCardTypeRepository;

    private final BankCardTypeMapper bankCardTypeMapper;

    public BankCardTypeServiceImpl(BankCardTypeRepository bankCardTypeRepository, BankCardTypeMapper bankCardTypeMapper) {
        this.bankCardTypeRepository = bankCardTypeRepository;
        this.bankCardTypeMapper = bankCardTypeMapper;
    }

    @Override
    public BankCardTypeDTO save(BankCardTypeDTO bankCardTypeDTO) {
        log.debug("Request to save BankCardType : {}", bankCardTypeDTO);
        BankCardType bankCardType = bankCardTypeMapper.toEntity(bankCardTypeDTO);
        bankCardType = bankCardTypeRepository.save(bankCardType);
        return bankCardTypeMapper.toDto(bankCardType);
    }

    @Override
    public Optional<BankCardTypeDTO> partialUpdate(BankCardTypeDTO bankCardTypeDTO) {
        log.debug("Request to partially update BankCardType : {}", bankCardTypeDTO);

        return bankCardTypeRepository
            .findById(bankCardTypeDTO.getId())
            .map(
                existingBankCardType -> {
                    bankCardTypeMapper.partialUpdate(existingBankCardType, bankCardTypeDTO);

                    return existingBankCardType;
                }
            )
            .map(bankCardTypeRepository::save)
            .map(bankCardTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BankCardTypeDTO> findAll() {
        log.debug("Request to get all BankCardTypes");
        return bankCardTypeRepository.findAll().stream().map(bankCardTypeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BankCardTypeDTO> findOne(Long id) {
        log.debug("Request to get BankCardType : {}", id);
        return bankCardTypeRepository.findById(id).map(bankCardTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BankCardType : {}", id);
        bankCardTypeRepository.deleteById(id);
    }
}
