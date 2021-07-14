package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.IndContactDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link IndContact} and its DTO {@link IndContactDTO}.
 */
@Mapper(componentModel = "spring", uses = { IndividualMapper.class })
public interface IndContactMapper extends EntityMapper<IndContactDTO, IndContact> {
    @Mapping(target = "individual", source = "individual", qualifiedByName = "id")
    IndContactDTO toDto(IndContact s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    IndContactDTO toDtoId(IndContact indContact);
}
