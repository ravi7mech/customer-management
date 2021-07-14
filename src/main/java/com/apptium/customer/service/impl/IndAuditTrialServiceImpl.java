package com.apptium.customer.service.impl;

import com.apptium.customer.domain.IndAuditTrial;
import com.apptium.customer.repository.IndAuditTrialRepository;
import com.apptium.customer.service.IndAuditTrialService;
import com.apptium.customer.service.dto.IndAuditTrialDTO;
import com.apptium.customer.service.mapper.IndAuditTrialMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link IndAuditTrial}.
 */
@Service
@Transactional
public class IndAuditTrialServiceImpl implements IndAuditTrialService {

    private final Logger log = LoggerFactory.getLogger(IndAuditTrialServiceImpl.class);

    private final IndAuditTrialRepository indAuditTrialRepository;

    private final IndAuditTrialMapper indAuditTrialMapper;

    public IndAuditTrialServiceImpl(IndAuditTrialRepository indAuditTrialRepository, IndAuditTrialMapper indAuditTrialMapper) {
        this.indAuditTrialRepository = indAuditTrialRepository;
        this.indAuditTrialMapper = indAuditTrialMapper;
    }

    @Override
    public IndAuditTrialDTO save(IndAuditTrialDTO indAuditTrialDTO) {
        log.debug("Request to save IndAuditTrial : {}", indAuditTrialDTO);
        IndAuditTrial indAuditTrial = indAuditTrialMapper.toEntity(indAuditTrialDTO);
        indAuditTrial = indAuditTrialRepository.save(indAuditTrial);
        return indAuditTrialMapper.toDto(indAuditTrial);
    }

    @Override
    public Optional<IndAuditTrialDTO> partialUpdate(IndAuditTrialDTO indAuditTrialDTO) {
        log.debug("Request to partially update IndAuditTrial : {}", indAuditTrialDTO);

        return indAuditTrialRepository
            .findById(indAuditTrialDTO.getId())
            .map(
                existingIndAuditTrial -> {
                    indAuditTrialMapper.partialUpdate(existingIndAuditTrial, indAuditTrialDTO);

                    return existingIndAuditTrial;
                }
            )
            .map(indAuditTrialRepository::save)
            .map(indAuditTrialMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IndAuditTrialDTO> findAll() {
        log.debug("Request to get all IndAuditTrials");
        return indAuditTrialRepository.findAll().stream().map(indAuditTrialMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IndAuditTrialDTO> findOne(Long id) {
        log.debug("Request to get IndAuditTrial : {}", id);
        return indAuditTrialRepository.findById(id).map(indAuditTrialMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IndAuditTrial : {}", id);
        indAuditTrialRepository.deleteById(id);
    }
}
