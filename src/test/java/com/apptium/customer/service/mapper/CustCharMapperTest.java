package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustCharMapperTest {

    private CustCharMapper custCharMapper;

    @BeforeEach
    public void setUp() {
        custCharMapper = new CustCharMapperImpl();
    }
}
