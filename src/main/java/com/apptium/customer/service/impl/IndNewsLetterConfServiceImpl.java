package com.apptium.customer.service.impl;

import com.apptium.customer.domain.IndNewsLetterConf;
import com.apptium.customer.repository.IndNewsLetterConfRepository;
import com.apptium.customer.service.IndNewsLetterConfService;
import com.apptium.customer.service.dto.IndNewsLetterConfDTO;
import com.apptium.customer.service.mapper.IndNewsLetterConfMapper;
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
 * Service Implementation for managing {@link IndNewsLetterConf}.
 */
@Service
@Transactional
public class IndNewsLetterConfServiceImpl implements IndNewsLetterConfService {

    private final Logger log = LoggerFactory.getLogger(IndNewsLetterConfServiceImpl.class);

    private final IndNewsLetterConfRepository indNewsLetterConfRepository;

    private final IndNewsLetterConfMapper indNewsLetterConfMapper;

    public IndNewsLetterConfServiceImpl(
        IndNewsLetterConfRepository indNewsLetterConfRepository,
        IndNewsLetterConfMapper indNewsLetterConfMapper
    ) {
        this.indNewsLetterConfRepository = indNewsLetterConfRepository;
        this.indNewsLetterConfMapper = indNewsLetterConfMapper;
    }

    @Override
    public IndNewsLetterConfDTO save(IndNewsLetterConfDTO indNewsLetterConfDTO) {
        log.debug("Request to save IndNewsLetterConf : {}", indNewsLetterConfDTO);
        IndNewsLetterConf indNewsLetterConf = indNewsLetterConfMapper.toEntity(indNewsLetterConfDTO);
        indNewsLetterConf = indNewsLetterConfRepository.save(indNewsLetterConf);
        return indNewsLetterConfMapper.toDto(indNewsLetterConf);
    }

    @Override
    public Optional<IndNewsLetterConfDTO> partialUpdate(IndNewsLetterConfDTO indNewsLetterConfDTO) {
        log.debug("Request to partially update IndNewsLetterConf : {}", indNewsLetterConfDTO);

        return indNewsLetterConfRepository
            .findById(indNewsLetterConfDTO.getId())
            .map(
                existingIndNewsLetterConf -> {
                    indNewsLetterConfMapper.partialUpdate(existingIndNewsLetterConf, indNewsLetterConfDTO);

                    return existingIndNewsLetterConf;
                }
            )
            .map(indNewsLetterConfRepository::save)
            .map(indNewsLetterConfMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IndNewsLetterConfDTO> findAll() {
        log.debug("Request to get all IndNewsLetterConfs");
        return indNewsLetterConfRepository
            .findAll()
            .stream()
            .map(indNewsLetterConfMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the indNewsLetterConfs where Individual is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<IndNewsLetterConfDTO> findAllWhereIndividualIsNull() {
        log.debug("Request to get all indNewsLetterConfs where Individual is null");
        return StreamSupport
            .stream(indNewsLetterConfRepository.findAll().spliterator(), false)
            .filter(indNewsLetterConf -> indNewsLetterConf.getIndividual() == null)
            .map(indNewsLetterConfMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IndNewsLetterConfDTO> findOne(Long id) {
        log.debug("Request to get IndNewsLetterConf : {}", id);
        return indNewsLetterConfRepository.findById(id).map(indNewsLetterConfMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IndNewsLetterConf : {}", id);
        indNewsLetterConfRepository.deleteById(id);
    }
}
