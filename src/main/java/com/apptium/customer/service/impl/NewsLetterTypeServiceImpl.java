package com.apptium.customer.service.impl;

import com.apptium.customer.domain.NewsLetterType;
import com.apptium.customer.repository.NewsLetterTypeRepository;
import com.apptium.customer.service.NewsLetterTypeService;
import com.apptium.customer.service.dto.NewsLetterTypeDTO;
import com.apptium.customer.service.mapper.NewsLetterTypeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NewsLetterType}.
 */
@Service
@Transactional
public class NewsLetterTypeServiceImpl implements NewsLetterTypeService {

    private final Logger log = LoggerFactory.getLogger(NewsLetterTypeServiceImpl.class);

    private final NewsLetterTypeRepository newsLetterTypeRepository;

    private final NewsLetterTypeMapper newsLetterTypeMapper;

    public NewsLetterTypeServiceImpl(NewsLetterTypeRepository newsLetterTypeRepository, NewsLetterTypeMapper newsLetterTypeMapper) {
        this.newsLetterTypeRepository = newsLetterTypeRepository;
        this.newsLetterTypeMapper = newsLetterTypeMapper;
    }

    @Override
    public NewsLetterTypeDTO save(NewsLetterTypeDTO newsLetterTypeDTO) {
        log.debug("Request to save NewsLetterType : {}", newsLetterTypeDTO);
        NewsLetterType newsLetterType = newsLetterTypeMapper.toEntity(newsLetterTypeDTO);
        newsLetterType = newsLetterTypeRepository.save(newsLetterType);
        return newsLetterTypeMapper.toDto(newsLetterType);
    }

    @Override
    public Optional<NewsLetterTypeDTO> partialUpdate(NewsLetterTypeDTO newsLetterTypeDTO) {
        log.debug("Request to partially update NewsLetterType : {}", newsLetterTypeDTO);

        return newsLetterTypeRepository
            .findById(newsLetterTypeDTO.getId())
            .map(
                existingNewsLetterType -> {
                    newsLetterTypeMapper.partialUpdate(existingNewsLetterType, newsLetterTypeDTO);

                    return existingNewsLetterType;
                }
            )
            .map(newsLetterTypeRepository::save)
            .map(newsLetterTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NewsLetterTypeDTO> findAll() {
        log.debug("Request to get all NewsLetterTypes");
        return newsLetterTypeRepository
            .findAll()
            .stream()
            .map(newsLetterTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NewsLetterTypeDTO> findOne(Long id) {
        log.debug("Request to get NewsLetterType : {}", id);
        return newsLetterTypeRepository.findById(id).map(newsLetterTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NewsLetterType : {}", id);
        newsLetterTypeRepository.deleteById(id);
    }
}
