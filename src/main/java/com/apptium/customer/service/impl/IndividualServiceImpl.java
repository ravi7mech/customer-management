package com.apptium.customer.service.impl;

import com.apptium.customer.domain.Individual;
import com.apptium.customer.repository.IndividualRepository;
import com.apptium.customer.service.IndividualService;
import com.apptium.customer.service.dto.IndividualDTO;
import com.apptium.customer.service.mapper.IndividualMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Individual}.
 */
@Service
@Transactional
public class IndividualServiceImpl implements IndividualService {

    private final Logger log = LoggerFactory.getLogger(IndividualServiceImpl.class);

    private final IndividualRepository individualRepository;

    private final IndividualMapper individualMapper;

    public IndividualServiceImpl(IndividualRepository individualRepository, IndividualMapper individualMapper) {
        this.individualRepository = individualRepository;
        this.individualMapper = individualMapper;
    }

    @Override
    public IndividualDTO save(IndividualDTO individualDTO) {
        log.debug("Request to save Individual : {}", individualDTO);
        Individual individual = individualMapper.toEntity(individualDTO);
        individual = individualRepository.save(individual);
        return individualMapper.toDto(individual);
    }

    @Override
    public Optional<IndividualDTO> partialUpdate(IndividualDTO individualDTO) {
        log.debug("Request to partially update Individual : {}", individualDTO);

        return individualRepository
            .findById(individualDTO.getId())
            .map(
                existingIndividual -> {
                    individualMapper.partialUpdate(existingIndividual, individualDTO);

                    return existingIndividual;
                }
            )
            .map(individualRepository::save)
            .map(individualMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IndividualDTO> findAll() {
        log.debug("Request to get all Individuals");
        return individualRepository.findAll().stream().map(individualMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IndividualDTO> findOne(Long id) {
        log.debug("Request to get Individual : {}", id);
        return individualRepository.findById(id).map(individualMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Individual : {}", id);
        individualRepository.deleteById(id);
    }
}
