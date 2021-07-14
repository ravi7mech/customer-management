package com.apptium.customer.service.impl;

import com.apptium.customer.domain.ShoppingSessionRef;
import com.apptium.customer.repository.ShoppingSessionRefRepository;
import com.apptium.customer.service.ShoppingSessionRefService;
import com.apptium.customer.service.dto.ShoppingSessionRefDTO;
import com.apptium.customer.service.mapper.ShoppingSessionRefMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ShoppingSessionRef}.
 */
@Service
@Transactional
public class ShoppingSessionRefServiceImpl implements ShoppingSessionRefService {

    private final Logger log = LoggerFactory.getLogger(ShoppingSessionRefServiceImpl.class);

    private final ShoppingSessionRefRepository shoppingSessionRefRepository;

    private final ShoppingSessionRefMapper shoppingSessionRefMapper;

    public ShoppingSessionRefServiceImpl(
        ShoppingSessionRefRepository shoppingSessionRefRepository,
        ShoppingSessionRefMapper shoppingSessionRefMapper
    ) {
        this.shoppingSessionRefRepository = shoppingSessionRefRepository;
        this.shoppingSessionRefMapper = shoppingSessionRefMapper;
    }

    @Override
    public ShoppingSessionRefDTO save(ShoppingSessionRefDTO shoppingSessionRefDTO) {
        log.debug("Request to save ShoppingSessionRef : {}", shoppingSessionRefDTO);
        ShoppingSessionRef shoppingSessionRef = shoppingSessionRefMapper.toEntity(shoppingSessionRefDTO);
        shoppingSessionRef = shoppingSessionRefRepository.save(shoppingSessionRef);
        return shoppingSessionRefMapper.toDto(shoppingSessionRef);
    }

    @Override
    public Optional<ShoppingSessionRefDTO> partialUpdate(ShoppingSessionRefDTO shoppingSessionRefDTO) {
        log.debug("Request to partially update ShoppingSessionRef : {}", shoppingSessionRefDTO);

        return shoppingSessionRefRepository
            .findById(shoppingSessionRefDTO.getId())
            .map(
                existingShoppingSessionRef -> {
                    shoppingSessionRefMapper.partialUpdate(existingShoppingSessionRef, shoppingSessionRefDTO);

                    return existingShoppingSessionRef;
                }
            )
            .map(shoppingSessionRefRepository::save)
            .map(shoppingSessionRefMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShoppingSessionRefDTO> findAll() {
        log.debug("Request to get all ShoppingSessionRefs");
        return shoppingSessionRefRepository
            .findAll()
            .stream()
            .map(shoppingSessionRefMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ShoppingSessionRefDTO> findOne(Long id) {
        log.debug("Request to get ShoppingSessionRef : {}", id);
        return shoppingSessionRefRepository.findById(id).map(shoppingSessionRefMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShoppingSessionRef : {}", id);
        shoppingSessionRefRepository.deleteById(id);
    }
}
