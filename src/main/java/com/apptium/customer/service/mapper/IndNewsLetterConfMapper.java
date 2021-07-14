package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.IndNewsLetterConfDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link IndNewsLetterConf} and its DTO {@link IndNewsLetterConfDTO}.
 */
@Mapper(componentModel = "spring", uses = { NewsLetterTypeMapper.class })
public interface IndNewsLetterConfMapper extends EntityMapper<IndNewsLetterConfDTO, IndNewsLetterConf> {
    @Mapping(target = "newsLetterType", source = "newsLetterType", qualifiedByName = "id")
    IndNewsLetterConfDTO toDto(IndNewsLetterConf s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    IndNewsLetterConfDTO toDtoId(IndNewsLetterConf indNewsLetterConf);
}
