package com.apptium.customer.service.impl;

import com.apptium.customer.domain.Industry;
import com.apptium.customer.repository.IndustryRepository;
import com.apptium.customer.service.IndustryService;
import com.apptium.customer.service.dto.IndustryDTO;
import com.apptium.customer.service.mapper.IndustryMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Industry}.
 */
@Service
@Transactional
public class IndustryServiceImpl implements IndustryService {

    private final Logger log = LoggerFactory.getLogger(IndustryServiceImpl.class);

    private final IndustryRepository industryRepository;

    private final IndustryMapper industryMapper;

    public IndustryServiceImpl(IndustryRepository industryRepository, IndustryMapper industryMapper) {
        this.industryRepository = industryRepository;
        this.industryMapper = industryMapper;
    }

    @Override
    public IndustryDTO save(IndustryDTO industryDTO) {
        log.debug("Request to save Industry : {}", industryDTO);
        Industry industry = industryMapper.toEntity(industryDTO);
        industry = industryRepository.save(industry);
        return industryMapper.toDto(industry);
    }

    @Override
    public Optional<IndustryDTO> partialUpdate(IndustryDTO industryDTO) {
        log.debug("Request to partially update Industry : {}", industryDTO);

        return industryRepository
            .findById(industryDTO.getId())
            .map(
                existingIndustry -> {
                    industryMapper.partialUpdate(existingIndustry, industryDTO);

                    return existingIndustry;
                }
            )
            .map(industryRepository::save)
            .map(industryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IndustryDTO> findAll() {
        log.debug("Request to get all Industries");
        return industryRepository.findAll().stream().map(industryMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IndustryDTO> findOne(Long id) {
        log.debug("Request to get Industry : {}", id);
        return industryRepository.findById(id).map(industryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Industry : {}", id);
        industryRepository.deleteById(id);
    }
}
