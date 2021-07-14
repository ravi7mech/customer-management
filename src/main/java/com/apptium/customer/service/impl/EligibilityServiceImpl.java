package com.apptium.customer.service.impl;

import com.apptium.customer.domain.Eligibility;
import com.apptium.customer.repository.EligibilityRepository;
import com.apptium.customer.service.EligibilityService;
import com.apptium.customer.service.dto.EligibilityDTO;
import com.apptium.customer.service.mapper.EligibilityMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Eligibility}.
 */
@Service
@Transactional
public class EligibilityServiceImpl implements EligibilityService {

    private final Logger log = LoggerFactory.getLogger(EligibilityServiceImpl.class);

    private final EligibilityRepository eligibilityRepository;

    private final EligibilityMapper eligibilityMapper;

    public EligibilityServiceImpl(EligibilityRepository eligibilityRepository, EligibilityMapper eligibilityMapper) {
        this.eligibilityRepository = eligibilityRepository;
        this.eligibilityMapper = eligibilityMapper;
    }

    @Override
    public EligibilityDTO save(EligibilityDTO eligibilityDTO) {
        log.debug("Request to save Eligibility : {}", eligibilityDTO);
        Eligibility eligibility = eligibilityMapper.toEntity(eligibilityDTO);
        eligibility = eligibilityRepository.save(eligibility);
        return eligibilityMapper.toDto(eligibility);
    }

    @Override
    public Optional<EligibilityDTO> partialUpdate(EligibilityDTO eligibilityDTO) {
        log.debug("Request to partially update Eligibility : {}", eligibilityDTO);

        return eligibilityRepository
            .findById(eligibilityDTO.getId())
            .map(
                existingEligibility -> {
                    eligibilityMapper.partialUpdate(existingEligibility, eligibilityDTO);

                    return existingEligibility;
                }
            )
            .map(eligibilityRepository::save)
            .map(eligibilityMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EligibilityDTO> findAll() {
        log.debug("Request to get all Eligibilities");
        return eligibilityRepository.findAll().stream().map(eligibilityMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EligibilityDTO> findOne(Long id) {
        log.debug("Request to get Eligibility : {}", id);
        return eligibilityRepository.findById(id).map(eligibilityMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Eligibility : {}", id);
        eligibilityRepository.deleteById(id);
    }
}
