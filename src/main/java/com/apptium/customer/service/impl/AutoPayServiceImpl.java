package com.apptium.customer.service.impl;

import com.apptium.customer.domain.AutoPay;
import com.apptium.customer.repository.AutoPayRepository;
import com.apptium.customer.service.AutoPayService;
import com.apptium.customer.service.dto.AutoPayDTO;
import com.apptium.customer.service.mapper.AutoPayMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AutoPay}.
 */
@Service
@Transactional
public class AutoPayServiceImpl implements AutoPayService {

    private final Logger log = LoggerFactory.getLogger(AutoPayServiceImpl.class);

    private final AutoPayRepository autoPayRepository;

    private final AutoPayMapper autoPayMapper;

    public AutoPayServiceImpl(AutoPayRepository autoPayRepository, AutoPayMapper autoPayMapper) {
        this.autoPayRepository = autoPayRepository;
        this.autoPayMapper = autoPayMapper;
    }

    @Override
    public AutoPayDTO save(AutoPayDTO autoPayDTO) {
        log.debug("Request to save AutoPay : {}", autoPayDTO);
        AutoPay autoPay = autoPayMapper.toEntity(autoPayDTO);
        autoPay = autoPayRepository.save(autoPay);
        return autoPayMapper.toDto(autoPay);
    }

    @Override
    public Optional<AutoPayDTO> partialUpdate(AutoPayDTO autoPayDTO) {
        log.debug("Request to partially update AutoPay : {}", autoPayDTO);

        return autoPayRepository
            .findById(autoPayDTO.getId())
            .map(
                existingAutoPay -> {
                    autoPayMapper.partialUpdate(existingAutoPay, autoPayDTO);

                    return existingAutoPay;
                }
            )
            .map(autoPayRepository::save)
            .map(autoPayMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AutoPayDTO> findAll() {
        log.debug("Request to get all AutoPays");
        return autoPayRepository.findAll().stream().map(autoPayMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AutoPayDTO> findOne(Long id) {
        log.debug("Request to get AutoPay : {}", id);
        return autoPayRepository.findById(id).map(autoPayMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AutoPay : {}", id);
        autoPayRepository.deleteById(id);
    }
}
