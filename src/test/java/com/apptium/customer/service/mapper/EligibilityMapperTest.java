package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EligibilityMapperTest {

    private EligibilityMapper eligibilityMapper;

    @BeforeEach
    public void setUp() {
        eligibilityMapper = new EligibilityMapperImpl();
    }
}
