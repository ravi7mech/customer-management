package com.apptium.customer.service.mapper;

import com.apptium.customer.domain.*;
import com.apptium.customer.service.dto.EligibilityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Eligibility} and its DTO {@link EligibilityDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EligibilityMapper extends EntityMapper<EligibilityDTO, Eligibility> {}
