package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustSecurityCharMapperTest {

    private CustSecurityCharMapper custSecurityCharMapper;

    @BeforeEach
    public void setUp() {
        custSecurityCharMapper = new CustSecurityCharMapperImpl();
    }
}
