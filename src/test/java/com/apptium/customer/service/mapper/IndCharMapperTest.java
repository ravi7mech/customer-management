package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IndCharMapperTest {

    private IndCharMapper indCharMapper;

    @BeforeEach
    public void setUp() {
        indCharMapper = new IndCharMapperImpl();
    }
}
