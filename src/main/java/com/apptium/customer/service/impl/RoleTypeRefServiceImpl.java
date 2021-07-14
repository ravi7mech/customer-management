package com.apptium.customer.service.impl;

import com.apptium.customer.domain.RoleTypeRef;
import com.apptium.customer.repository.RoleTypeRefRepository;
import com.apptium.customer.service.RoleTypeRefService;
import com.apptium.customer.service.dto.RoleTypeRefDTO;
import com.apptium.customer.service.mapper.RoleTypeRefMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RoleTypeRef}.
 */
@Service
@Transactional
public class RoleTypeRefServiceImpl implements RoleTypeRefService {

    private final Logger log = LoggerFactory.getLogger(RoleTypeRefServiceImpl.class);

    private final RoleTypeRefRepository roleTypeRefRepository;

    private final RoleTypeRefMapper roleTypeRefMapper;

    public RoleTypeRefServiceImpl(RoleTypeRefRepository roleTypeRefRepository, RoleTypeRefMapper roleTypeRefMapper) {
        this.roleTypeRefRepository = roleTypeRefRepository;
        this.roleTypeRefMapper = roleTypeRefMapper;
    }

    @Override
    public RoleTypeRefDTO save(RoleTypeRefDTO roleTypeRefDTO) {
        log.debug("Request to save RoleTypeRef : {}", roleTypeRefDTO);
        RoleTypeRef roleTypeRef = roleTypeRefMapper.toEntity(roleTypeRefDTO);
        roleTypeRef = roleTypeRefRepository.save(roleTypeRef);
        return roleTypeRefMapper.toDto(roleTypeRef);
    }

    @Override
    public Optional<RoleTypeRefDTO> partialUpdate(RoleTypeRefDTO roleTypeRefDTO) {
        log.debug("Request to partially update RoleTypeRef : {}", roleTypeRefDTO);

        return roleTypeRefRepository
            .findById(roleTypeRefDTO.getId())
            .map(
                existingRoleTypeRef -> {
                    roleTypeRefMapper.partialUpdate(existingRoleTypeRef, roleTypeRefDTO);

                    return existingRoleTypeRef;
                }
            )
            .map(roleTypeRefRepository::save)
            .map(roleTypeRefMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleTypeRefDTO> findAll() {
        log.debug("Request to get all RoleTypeRefs");
        return roleTypeRefRepository.findAll().stream().map(roleTypeRefMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoleTypeRefDTO> findOne(Long id) {
        log.debug("Request to get RoleTypeRef : {}", id);
        return roleTypeRefRepository.findById(id).map(roleTypeRefMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RoleTypeRef : {}", id);
        roleTypeRefRepository.deleteById(id);
    }
}
