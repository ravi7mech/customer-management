package com.apptium.customer.service.impl;

import com.apptium.customer.domain.CustNewsLetterConfig;
import com.apptium.customer.repository.CustNewsLetterConfigRepository;
import com.apptium.customer.service.CustNewsLetterConfigService;
import com.apptium.customer.service.dto.CustNewsLetterConfigDTO;
import com.apptium.customer.service.mapper.CustNewsLetterConfigMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustNewsLetterConfig}.
 */
@Service
@Transactional
public class CustNewsLetterConfigServiceImpl implements CustNewsLetterConfigService {

    private final Logger log = LoggerFactory.getLogger(CustNewsLetterConfigServiceImpl.class);

    private final CustNewsLetterConfigRepository custNewsLetterConfigRepository;

    private final CustNewsLetterConfigMapper custNewsLetterConfigMapper;

    public CustNewsLetterConfigServiceImpl(
        CustNewsLetterConfigRepository custNewsLetterConfigRepository,
        CustNewsLetterConfigMapper custNewsLetterConfigMapper
    ) {
        this.custNewsLetterConfigRepository = custNewsLetterConfigRepository;
        this.custNewsLetterConfigMapper = custNewsLetterConfigMapper;
    }

    @Override
    public CustNewsLetterConfigDTO save(CustNewsLetterConfigDTO custNewsLetterConfigDTO) {
        log.debug("Request to save CustNewsLetterConfig : {}", custNewsLetterConfigDTO);
        CustNewsLetterConfig custNewsLetterConfig = custNewsLetterConfigMapper.toEntity(custNewsLetterConfigDTO);
        custNewsLetterConfig = custNewsLetterConfigRepository.save(custNewsLetterConfig);
        return custNewsLetterConfigMapper.toDto(custNewsLetterConfig);
    }

    @Override
    public Optional<CustNewsLetterConfigDTO> partialUpdate(CustNewsLetterConfigDTO custNewsLetterConfigDTO) {
        log.debug("Request to partially update CustNewsLetterConfig : {}", custNewsLetterConfigDTO);

        return custNewsLetterConfigRepository
            .findById(custNewsLetterConfigDTO.getId())
            .map(
                existingCustNewsLetterConfig -> {
                    custNewsLetterConfigMapper.partialUpdate(existingCustNewsLetterConfig, custNewsLetterConfigDTO);

                    return existingCustNewsLetterConfig;
                }
            )
            .map(custNewsLetterConfigRepository::save)
            .map(custNewsLetterConfigMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustNewsLetterConfigDTO> findAll() {
        log.debug("Request to get all CustNewsLetterConfigs");
        return custNewsLetterConfigRepository
            .findAll()
            .stream()
            .map(custNewsLetterConfigMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustNewsLetterConfigDTO> findOne(Long id) {
        log.debug("Request to get CustNewsLetterConfig : {}", id);
        return custNewsLetterConfigRepository.findById(id).map(custNewsLetterConfigMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustNewsLetterConfig : {}", id);
        custNewsLetterConfigRepository.deleteById(id);
    }
}
