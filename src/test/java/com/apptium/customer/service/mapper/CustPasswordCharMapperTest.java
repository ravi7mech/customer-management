package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustPasswordCharMapperTest {

    private CustPasswordCharMapper custPasswordCharMapper;

    @BeforeEach
    public void setUp() {
        custPasswordCharMapper = new CustPasswordCharMapperImpl();
    }
}
