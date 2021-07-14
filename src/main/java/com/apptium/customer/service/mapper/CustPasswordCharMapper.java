package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.CustPasswordCharDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustPasswordChar} and its DTO {@link CustPasswordCharDTO}.
 */
@Mapper(componentModel = "spring", uses = { CustomerMapper.class })
public interface CustPasswordCharMapper extends EntityMapper<CustPasswordCharDTO, CustPasswordChar> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "id")
    CustPasswordCharDTO toDto(CustPasswordChar s);
}
