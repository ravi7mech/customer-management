package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.IndustryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Industry} and its DTO {@link IndustryDTO}.
 */
@Mapper(componentModel = "spring", uses = { CustomerMapper.class })
public interface IndustryMapper extends EntityMapper<IndustryDTO, Industry> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "id")
    IndustryDTO toDto(Industry s);
}
