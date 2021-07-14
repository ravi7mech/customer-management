package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.CustCharDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustChar} and its DTO {@link CustCharDTO}.
 */
@Mapper(componentModel = "spring", uses = { CustomerMapper.class })
public interface CustCharMapper extends EntityMapper<CustCharDTO, CustChar> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "id")
    CustCharDTO toDto(CustChar s);
}
