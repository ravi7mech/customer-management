package com.apptium.customer.service.impl;

import com.apptium.customer.domain.IndContact;
import com.apptium.customer.repository.IndContactRepository;
import com.apptium.customer.service.IndContactService;
import com.apptium.customer.service.dto.IndContactDTO;
import com.apptium.customer.service.mapper.IndContactMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link IndContact}.
 */
@Service
@Transactional
public class IndContactServiceImpl implements IndContactService {

    private final Logger log = LoggerFactory.getLogger(IndContactServiceImpl.class);

    private final IndContactRepository indContactRepository;

    private final IndContactMapper indContactMapper;

    public IndContactServiceImpl(IndContactRepository indContactRepository, IndContactMapper indContactMapper) {
        this.indContactRepository = indContactRepository;
        this.indContactMapper = indContactMapper;
    }

    @Override
    public IndContactDTO save(IndContactDTO indContactDTO) {
        log.debug("Request to save IndContact : {}", indContactDTO);
        IndContact indContact = indContactMapper.toEntity(indContactDTO);
        indContact = indContactRepository.save(indContact);
        return indContactMapper.toDto(indContact);
    }

    @Override
    public Optional<IndContactDTO> partialUpdate(IndContactDTO indContactDTO) {
        log.debug("Request to partially update IndContact : {}", indContactDTO);

        return indContactRepository
            .findById(indContactDTO.getId())
            .map(
                existingIndContact -> {
                    indContactMapper.partialUpdate(existingIndContact, indContactDTO);

                    return existingIndContact;
                }
            )
            .map(indContactRepository::save)
            .map(indContactMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IndContactDTO> findAll() {
        log.debug("Request to get all IndContacts");
        return indContactRepository.findAll().stream().map(indContactMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IndContactDTO> findOne(Long id) {
        log.debug("Request to get IndContact : {}", id);
        return indContactRepository.findById(id).map(indContactMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IndContact : {}", id);
        indContactRepository.deleteById(id);
    }
}
