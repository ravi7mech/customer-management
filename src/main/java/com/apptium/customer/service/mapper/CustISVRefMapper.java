package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.CustISVRefDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustISVRef} and its DTO {@link CustISVRefDTO}.
 */
@Mapper(componentModel = "spring", uses = { CustomerMapper.class })
public interface CustISVRefMapper extends EntityMapper<CustISVRefDTO, CustISVRef> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "id")
    CustISVRefDTO toDto(CustISVRef s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustISVRefDTO toDtoId(CustISVRef custISVRef);
}
