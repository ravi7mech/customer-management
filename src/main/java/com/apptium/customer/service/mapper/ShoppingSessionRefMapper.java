package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.ShoppingSessionRefDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ShoppingSessionRef} and its DTO {@link ShoppingSessionRefDTO}.
 */
@Mapper(componentModel = "spring", uses = { CustomerMapper.class, IndividualMapper.class })
public interface ShoppingSessionRefMapper extends EntityMapper<ShoppingSessionRefDTO, ShoppingSessionRef> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "id")
    @Mapping(target = "individual", source = "individual", qualifiedByName = "id")
    ShoppingSessionRefDTO toDto(ShoppingSessionRef s);
}
