package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IndustryMapperTest {

    private IndustryMapper industryMapper;

    @BeforeEach
    public void setUp() {
        industryMapper = new IndustryMapperImpl();
    }
}
