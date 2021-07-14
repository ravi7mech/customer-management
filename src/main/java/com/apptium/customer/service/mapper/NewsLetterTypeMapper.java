package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.NewsLetterTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NewsLetterType} and its DTO {@link NewsLetterTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NewsLetterTypeMapper extends EntityMapper<NewsLetterTypeDTO, NewsLetterType> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NewsLetterTypeDTO toDtoId(NewsLetterType newsLetterType);
}
