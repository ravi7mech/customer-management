package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IndividualMapperTest {

    private IndividualMapper individualMapper;

    @BeforeEach
    public void setUp() {
        individualMapper = new IndividualMapperImpl();
    }
}
