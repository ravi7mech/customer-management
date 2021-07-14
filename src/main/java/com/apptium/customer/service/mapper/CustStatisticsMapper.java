package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.CustStatisticsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustStatistics} and its DTO {@link CustStatisticsDTO}.
 */
@Mapper(componentModel = "spring", uses = { CustomerMapper.class })
public interface CustStatisticsMapper extends EntityMapper<CustStatisticsDTO, CustStatistics> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "id")
    CustStatisticsDTO toDto(CustStatistics s);
}
