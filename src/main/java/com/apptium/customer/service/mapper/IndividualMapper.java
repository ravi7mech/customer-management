package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.IndividualDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Individual} and its DTO {@link IndividualDTO}.
 */
@Mapper(componentModel = "spring", uses = { IndActivationMapper.class, IndNewsLetterConfMapper.class })
public interface IndividualMapper extends EntityMapper<IndividualDTO, Individual> {
    @Mapping(target = "indActivation", source = "indActivation", qualifiedByName = "id")
    @Mapping(target = "indNewsLetterConf", source = "indNewsLetterConf", qualifiedByName = "id")
    IndividualDTO toDto(Individual s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    IndividualDTO toDtoId(Individual individual);
}
