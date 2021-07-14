package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustContactCharMapperTest {

    private CustContactCharMapper custContactCharMapper;

    @BeforeEach
    public void setUp() {
        custContactCharMapper = new CustContactCharMapperImpl();
    }
}
