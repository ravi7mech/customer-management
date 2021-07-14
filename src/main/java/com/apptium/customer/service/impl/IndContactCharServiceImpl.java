package com.apptium.customer.service.impl;

import com.apptium.customer.domain.IndContactChar;
import com.apptium.customer.repository.IndContactCharRepository;
import com.apptium.customer.service.IndContactCharService;
import com.apptium.customer.service.dto.IndContactCharDTO;
import com.apptium.customer.service.mapper.IndContactCharMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link IndContactChar}.
 */
@Service
@Transactional
public class IndContactCharServiceImpl implements IndContactCharService {

    private final Logger log = LoggerFactory.getLogger(IndContactCharServiceImpl.class);

    private final IndContactCharRepository indContactCharRepository;

    private final IndContactCharMapper indContactCharMapper;

    public IndContactCharServiceImpl(IndContactCharRepository indContactCharRepository, IndContactCharMapper indContactCharMapper) {
        this.indContactCharRepository = indContactCharRepository;
        this.indContactCharMapper = indContactCharMapper;
    }

    @Override
    public IndContactCharDTO save(IndContactCharDTO indContactCharDTO) {
        log.debug("Request to save IndContactChar : {}", indContactCharDTO);
        IndContactChar indContactChar = indContactCharMapper.toEntity(indContactCharDTO);
        indContactChar = indContactCharRepository.save(indContactChar);
        return indContactCharMapper.toDto(indContactChar);
    }

    @Override
    public Optional<IndContactCharDTO> partialUpdate(IndContactCharDTO indContactCharDTO) {
        log.debug("Request to partially update IndContactChar : {}", indContactCharDTO);

        return indContactCharRepository
            .findById(indContactCharDTO.getId())
            .map(
                existingIndContactChar -> {
                    indContactCharMapper.partialUpdate(existingIndContactChar, indContactCharDTO);

                    return existingIndContactChar;
                }
            )
            .map(indContactCharRepository::save)
            .map(indContactCharMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IndContactCharDTO> findAll() {
        log.debug("Request to get all IndContactChars");
        return indContactCharRepository
            .findAll()
            .stream()
            .map(indContactCharMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IndContactCharDTO> findOne(Long id) {
        log.debug("Request to get IndContactChar : {}", id);
        return indContactCharRepository.findById(id).map(indContactCharMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IndContactChar : {}", id);
        indContactCharRepository.deleteById(id);
    }
}
