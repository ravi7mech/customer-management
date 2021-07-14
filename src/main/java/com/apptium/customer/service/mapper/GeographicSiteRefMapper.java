package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.GeographicSiteRefDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GeographicSiteRef} and its DTO {@link GeographicSiteRefDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GeographicSiteRefMapper extends EntityMapper<GeographicSiteRefDTO, GeographicSiteRef> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GeographicSiteRefDTO toDtoId(GeographicSiteRef geographicSiteRef);
}
