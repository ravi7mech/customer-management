package com.apptium.customer.service.impl;

import com.apptium.customer.domain.IndChar;
import com.apptium.customer.repository.IndCharRepository;
import com.apptium.customer.service.IndCharService;
import com.apptium.customer.service.dto.IndCharDTO;
import com.apptium.customer.service.mapper.IndCharMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link IndChar}.
 */
@Service
@Transactional
public class IndCharServiceImpl implements IndCharService {

    private final Logger log = LoggerFactory.getLogger(IndCharServiceImpl.class);

    private final IndCharRepository indCharRepository;

    private final IndCharMapper indCharMapper;

    public IndCharServiceImpl(IndCharRepository indCharRepository, IndCharMapper indCharMapper) {
        this.indCharRepository = indCharRepository;
        this.indCharMapper = indCharMapper;
    }

    @Override
    public IndCharDTO save(IndCharDTO indCharDTO) {
        log.debug("Request to save IndChar : {}", indCharDTO);
        IndChar indChar = indCharMapper.toEntity(indCharDTO);
        indChar = indCharRepository.save(indChar);
        return indCharMapper.toDto(indChar);
    }

    @Override
    public Optional<IndCharDTO> partialUpdate(IndCharDTO indCharDTO) {
        log.debug("Request to partially update IndChar : {}", indCharDTO);

        return indCharRepository
            .findById(indCharDTO.getId())
            .map(
                existingIndChar -> {
                    indCharMapper.partialUpdate(existingIndChar, indCharDTO);

                    return existingIndChar;
                }
            )
            .map(indCharRepository::save)
            .map(indCharMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IndCharDTO> findAll() {
        log.debug("Request to get all IndChars");
        return indCharRepository.findAll().stream().map(indCharMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IndCharDTO> findOne(Long id) {
        log.debug("Request to get IndChar : {}", id);
        return indCharRepository.findById(id).map(indCharMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IndChar : {}", id);
        indCharRepository.deleteById(id);
    }
}
