package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IndContactMapperTest {

    private IndContactMapper indContactMapper;

    @BeforeEach
    public void setUp() {
        indContactMapper = new IndContactMapperImpl();
    }
}
