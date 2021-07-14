package com.apptium.customer.service.impl;

import com.apptium.customer.domain.CustStatistics;
import com.apptium.customer.repository.CustStatisticsRepository;
import com.apptium.customer.service.CustStatisticsService;
import com.apptium.customer.service.dto.CustStatisticsDTO;
import com.apptium.customer.service.mapper.CustStatisticsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustStatistics}.
 */
@Service
@Transactional
public class CustStatisticsServiceImpl implements CustStatisticsService {

    private final Logger log = LoggerFactory.getLogger(CustStatisticsServiceImpl.class);

    private final CustStatisticsRepository custStatisticsRepository;

    private final CustStatisticsMapper custStatisticsMapper;

    public CustStatisticsServiceImpl(CustStatisticsRepository custStatisticsRepository, CustStatisticsMapper custStatisticsMapper) {
        this.custStatisticsRepository = custStatisticsRepository;
        this.custStatisticsMapper = custStatisticsMapper;
    }

    @Override
    public CustStatisticsDTO save(CustStatisticsDTO custStatisticsDTO) {
        log.debug("Request to save CustStatistics : {}", custStatisticsDTO);
        CustStatistics custStatistics = custStatisticsMapper.toEntity(custStatisticsDTO);
        custStatistics = custStatisticsRepository.save(custStatistics);
        return custStatisticsMapper.toDto(custStatistics);
    }

    @Override
    public Optional<CustStatisticsDTO> partialUpdate(CustStatisticsDTO custStatisticsDTO) {
        log.debug("Request to partially update CustStatistics : {}", custStatisticsDTO);

        return custStatisticsRepository
            .findById(custStatisticsDTO.getId())
            .map(
                existingCustStatistics -> {
                    custStatisticsMapper.partialUpdate(existingCustStatistics, custStatisticsDTO);

                    return existingCustStatistics;
                }
            )
            .map(custStatisticsRepository::save)
            .map(custStatisticsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustStatisticsDTO> findAll() {
        log.debug("Request to get all CustStatistics");
        return custStatisticsRepository
            .findAll()
            .stream()
            .map(custStatisticsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustStatisticsDTO> findOne(Long id) {
        log.debug("Request to get CustStatistics : {}", id);
        return custStatisticsRepository.findById(id).map(custStatisticsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustStatistics : {}", id);
        custStatisticsRepository.deleteById(id);
    }
}
