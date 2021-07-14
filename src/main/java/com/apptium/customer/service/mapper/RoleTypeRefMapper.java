package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.RoleTypeRefDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RoleTypeRef} and its DTO {@link RoleTypeRefDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RoleTypeRefMapper extends EntityMapper<RoleTypeRefDTO, RoleTypeRef> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RoleTypeRefDTO toDtoId(RoleTypeRef roleTypeRef);
}
