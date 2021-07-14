package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.IndActivationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link IndActivation} and its DTO {@link IndActivationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IndActivationMapper extends EntityMapper<IndActivationDTO, IndActivation> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    IndActivationDTO toDtoId(IndActivation indActivation);
}
