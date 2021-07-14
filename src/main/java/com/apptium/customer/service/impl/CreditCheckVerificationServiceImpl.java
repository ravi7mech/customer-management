package com.apptium.customer.service.impl;

import com.apptium.customer.domain.CreditCheckVerification;
import com.apptium.customer.repository.CreditCheckVerificationRepository;
import com.apptium.customer.service.CreditCheckVerificationService;
import com.apptium.customer.service.dto.CreditCheckVerificationDTO;
import com.apptium.customer.service.mapper.CreditCheckVerificationMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CreditCheckVerification}.
 */
@Service
@Transactional
public class CreditCheckVerificationServiceImpl implements CreditCheckVerificationService {

    private final Logger log = LoggerFactory.getLogger(CreditCheckVerificationServiceImpl.class);

    private final CreditCheckVerificationRepository creditCheckVerificationRepository;

    private final CreditCheckVerificationMapper creditCheckVerificationMapper;

    public CreditCheckVerificationServiceImpl(
        CreditCheckVerificationRepository creditCheckVerificationRepository,
        CreditCheckVerificationMapper creditCheckVerificationMapper
    ) {
        this.creditCheckVerificationRepository = creditCheckVerificationRepository;
        this.creditCheckVerificationMapper = creditCheckVerificationMapper;
    }

    @Override
    public CreditCheckVerificationDTO save(CreditCheckVerificationDTO creditCheckVerificationDTO) {
        log.debug("Request to save CreditCheckVerification : {}", creditCheckVerificationDTO);
        CreditCheckVerification creditCheckVerification = creditCheckVerificationMapper.toEntity(creditCheckVerificationDTO);
        creditCheckVerification = creditCheckVerificationRepository.save(creditCheckVerification);
        return creditCheckVerificationMapper.toDto(creditCheckVerification);
    }

    @Override
    public Optional<CreditCheckVerificationDTO> partialUpdate(CreditCheckVerificationDTO creditCheckVerificationDTO) {
        log.debug("Request to partially update CreditCheckVerification : {}", creditCheckVerificationDTO);

        return creditCheckVerificationRepository
            .findById(creditCheckVerificationDTO.getId())
            .map(
                existingCreditCheckVerification -> {
                    creditCheckVerificationMapper.partialUpdate(existingCreditCheckVerification, creditCheckVerificationDTO);

                    return existingCreditCheckVerification;
                }
            )
            .map(creditCheckVerificationRepository::save)
            .map(creditCheckVerificationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CreditCheckVerificationDTO> findAll() {
        log.debug("Request to get all CreditCheckVerifications");
        return creditCheckVerificationRepository
            .findAll()
            .stream()
            .map(creditCheckVerificationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CreditCheckVerificationDTO> findOne(Long id) {
        log.debug("Request to get CreditCheckVerification : {}", id);
        return creditCheckVerificationRepository.findById(id).map(creditCheckVerificationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CreditCheckVerification : {}", id);
        creditCheckVerificationRepository.deleteById(id);
    }
}
