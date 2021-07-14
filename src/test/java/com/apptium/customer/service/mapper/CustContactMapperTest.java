package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustContactMapperTest {

    private CustContactMapper custContactMapper;

    @BeforeEach
    public void setUp() {
        custContactMapper = new CustContactMapperImpl();
    }
}
