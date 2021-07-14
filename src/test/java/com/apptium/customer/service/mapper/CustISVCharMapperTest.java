package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustISVCharMapperTest {

    private CustISVCharMapper custISVCharMapper;

    @BeforeEach
    public void setUp() {
        custISVCharMapper = new CustISVCharMapperImpl();
    }
}
