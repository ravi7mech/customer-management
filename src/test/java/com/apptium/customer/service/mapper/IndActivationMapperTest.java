package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IndActivationMapperTest {

    private IndActivationMapper indActivationMapper;

    @BeforeEach
    public void setUp() {
        indActivationMapper = new IndActivationMapperImpl();
    }
}
