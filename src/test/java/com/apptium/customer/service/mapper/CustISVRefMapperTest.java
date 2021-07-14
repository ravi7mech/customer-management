package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustISVRefMapperTest {

    private CustISVRefMapper custISVRefMapper;

    @BeforeEach
    public void setUp() {
        custISVRefMapper = new CustISVRefMapperImpl();
    }
}
