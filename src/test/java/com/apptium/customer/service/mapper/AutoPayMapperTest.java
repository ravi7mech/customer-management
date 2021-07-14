package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AutoPayMapperTest {

    private AutoPayMapper autoPayMapper;

    @BeforeEach
    public void setUp() {
        autoPayMapper = new AutoPayMapperImpl();
    }
}
