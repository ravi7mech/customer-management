package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.IndContactCharDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link IndContactChar} and its DTO {@link IndContactCharDTO}.
 */
@Mapper(componentModel = "spring", uses = { IndContactMapper.class })
public interface IndContactCharMapper extends EntityMapper<IndContactCharDTO, IndContactChar> {
    @Mapping(target = "indContact", source = "indContact", qualifiedByName = "id")
    IndContactCharDTO toDto(IndContactChar s);
}
