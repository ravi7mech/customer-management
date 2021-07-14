package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.CustRelPartyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustRelParty} and its DTO {@link CustRelPartyDTO}.
 */
@Mapper(componentModel = "spring", uses = { CustomerMapper.class, DepartmentMapper.class, RoleTypeRefMapper.class, IndividualMapper.class })
public interface CustRelPartyMapper extends EntityMapper<CustRelPartyDTO, CustRelParty> {
    @Mapping(target = "customer", source = "customer", qualifiedByName = "id")
    @Mapping(target = "department", source = "department", qualifiedByName = "id")
    @Mapping(target = "roleTypeRef", source = "roleTypeRef", qualifiedByName = "id")
    @Mapping(target = "individual", source = "individual", qualifiedByName = "id")
    CustRelPartyDTO toDto(CustRelParty s);
}
