package com.apptium.customer.service.impl;

import com.apptium.customer.domain.GeographicSiteRef;
import com.apptium.customer.repository.GeographicSiteRefRepository;
import com.apptium.customer.service.GeographicSiteRefService;
import com.apptium.customer.service.dto.GeographicSiteRefDTO;
import com.apptium.customer.service.mapper.GeographicSiteRefMapper;
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
 * Service Implementation for managing {@link GeographicSiteRef}.
 */
@Service
@Transactional
public class GeographicSiteRefServiceImpl implements GeographicSiteRefService {

    private final Logger log = LoggerFactory.getLogger(GeographicSiteRefServiceImpl.class);

    private final GeographicSiteRefRepository geographicSiteRefRepository;

    private final GeographicSiteRefMapper geographicSiteRefMapper;

    public GeographicSiteRefServiceImpl(
        GeographicSiteRefRepository geographicSiteRefRepository,
        GeographicSiteRefMapper geographicSiteRefMapper
    ) {
        this.geographicSiteRefRepository = geographicSiteRefRepository;
        this.geographicSiteRefMapper = geographicSiteRefMapper;
    }

    @Override
    public GeographicSiteRefDTO save(GeographicSiteRefDTO geographicSiteRefDTO) {
        log.debug("Request to save GeographicSiteRef : {}", geographicSiteRefDTO);
        GeographicSiteRef geographicSiteRef = geographicSiteRefMapper.toEntity(geographicSiteRefDTO);
        geographicSiteRef = geographicSiteRefRepository.save(geographicSiteRef);
        return geographicSiteRefMapper.toDto(geographicSiteRef);
    }

    @Override
    public Optional<GeographicSiteRefDTO> partialUpdate(GeographicSiteRefDTO geographicSiteRefDTO) {
        log.debug("Request to partially update GeographicSiteRef : {}", geographicSiteRefDTO);

        return geographicSiteRefRepository
            .findById(geographicSiteRefDTO.getId())
            .map(
                existingGeographicSiteRef -> {
                    geographicSiteRefMapper.partialUpdate(existingGeographicSiteRef, geographicSiteRefDTO);

                    return existingGeographicSiteRef;
                }
            )
            .map(geographicSiteRefRepository::save)
            .map(geographicSiteRefMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GeographicSiteRefDTO> findAll() {
        log.debug("Request to get all GeographicSiteRefs");
        return geographicSiteRefRepository
            .findAll()
            .stream()
            .map(geographicSiteRefMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the geographicSiteRefs where CustContact is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GeographicSiteRefDTO> findAllWhereCustContactIsNull() {
        log.debug("Request to get all geographicSiteRefs where CustContact is null");
        return StreamSupport
            .stream(geographicSiteRefRepository.findAll().spliterator(), false)
            .filter(geographicSiteRef -> geographicSiteRef.getCustContact() == null)
            .map(geographicSiteRefMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GeographicSiteRefDTO> findOne(Long id) {
        log.debug("Request to get GeographicSiteRef : {}", id);
        return geographicSiteRefRepository.findById(id).map(geographicSiteRefMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GeographicSiteRef : {}", id);
        geographicSiteRefRepository.deleteById(id);
    }
}
