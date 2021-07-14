package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.IndCharDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link IndChar} and its DTO {@link IndCharDTO}.
 */
@Mapper(componentModel = "spring", uses = { IndividualMapper.class })
public interface IndCharMapper extends EntityMapper<IndCharDTO, IndChar> {
    @Mapping(target = "individual", source = "individual", qualifiedByName = "id")
    IndCharDTO toDto(IndChar s);
}
