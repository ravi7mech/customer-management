package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.IndAuditTrialDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link IndAuditTrial} and its DTO {@link IndAuditTrialDTO}.
 */
@Mapper(componentModel = "spring", uses = { IndividualMapper.class })
public interface IndAuditTrialMapper extends EntityMapper<IndAuditTrialDTO, IndAuditTrial> {
    @Mapping(target = "individual", source = "individual", qualifiedByName = "id")
    IndAuditTrialDTO toDto(IndAuditTrial s);
}
