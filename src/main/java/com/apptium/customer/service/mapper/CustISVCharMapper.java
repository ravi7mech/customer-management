package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.CustISVCharDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustISVChar} and its DTO {@link CustISVCharDTO}.
 */
@Mapper(componentModel = "spring", uses = { CustISVRefMapper.class })
public interface CustISVCharMapper extends EntityMapper<CustISVCharDTO, CustISVChar> {
    @Mapping(target = "custISVRef", source = "custISVRef", qualifiedByName = "id")
    CustISVCharDTO toDto(CustISVChar s);
}
