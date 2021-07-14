package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.CustContactCharDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustContactChar} and its DTO {@link CustContactCharDTO}.
 */
@Mapper(componentModel = "spring", uses = { CustContactMapper.class })
public interface CustContactCharMapper extends EntityMapper<CustContactCharDTO, CustContactChar> {
    @Mapping(target = "custContact", source = "custContact", qualifiedByName = "id")
    CustContactCharDTO toDto(CustContactChar s);
}
