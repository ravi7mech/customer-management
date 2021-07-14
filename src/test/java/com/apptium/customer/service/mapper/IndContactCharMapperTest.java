package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IndContactCharMapperTest {

    private IndContactCharMapper indContactCharMapper;

    @BeforeEach
    public void setUp() {
        indContactCharMapper = new IndContactCharMapperImpl();
    }
}
