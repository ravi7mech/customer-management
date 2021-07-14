package com.apptium.customer.service.impl;

import com.apptium.customer.domain.IndActivation;
import com.apptium.customer.repository.IndActivationRepository;
import com.apptium.customer.service.IndActivationService;
import com.apptium.customer.service.dto.IndActivationDTO;
import com.apptium.customer.service.mapper.IndActivationMapper;
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
 * Service Implementation for managing {@link IndActivation}.
 */
@Service
@Transactional
public class IndActivationServiceImpl implements IndActivationService {

    private final Logger log = LoggerFactory.getLogger(IndActivationServiceImpl.class);

    private final IndActivationRepository indActivationRepository;

    private final IndActivationMapper indActivationMapper;

    public IndActivationServiceImpl(IndActivationRepository indActivationRepository, IndActivationMapper indActivationMapper) {
        this.indActivationRepository = indActivationRepository;
        this.indActivationMapper = indActivationMapper;
    }

    @Override
    public IndActivationDTO save(IndActivationDTO indActivationDTO) {
        log.debug("Request to save IndActivation : {}", indActivationDTO);
        IndActivation indActivation = indActivationMapper.toEntity(indActivationDTO);
        indActivation = indActivationRepository.save(indActivation);
        return indActivationMapper.toDto(indActivation);
    }

    @Override
    public Optional<IndActivationDTO> partialUpdate(IndActivationDTO indActivationDTO) {
        log.debug("Request to partially update IndActivation : {}", indActivationDTO);

        return indActivationRepository
            .findById(indActivationDTO.getId())
            .map(
                existingIndActivation -> {
                    indActivationMapper.partialUpdate(existingIndActivation, indActivationDTO);

                    return existingIndActivation;
                }
            )
            .map(indActivationRepository::save)
            .map(indActivationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IndActivationDTO> findAll() {
        log.debug("Request to get all IndActivations");
        return indActivationRepository.findAll().stream().map(indActivationMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the indActivations where Individual is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<IndActivationDTO> findAllWhereIndividualIsNull() {
        log.debug("Request to get all indActivations where Individual is null");
        return StreamSupport
            .stream(indActivationRepository.findAll().spliterator(), false)
            .filter(indActivation -> indActivation.getIndividual() == null)
            .map(indActivationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IndActivationDTO> findOne(Long id) {
        log.debug("Request to get IndActivation : {}", id);
        return indActivationRepository.findById(id).map(indActivationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IndActivation : {}", id);
        indActivationRepository.deleteById(id);
    }
}
