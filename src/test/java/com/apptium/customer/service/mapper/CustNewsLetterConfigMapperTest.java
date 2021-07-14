package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustNewsLetterConfigMapperTest {

    private CustNewsLetterConfigMapper custNewsLetterConfigMapper;

    @BeforeEach
    public void setUp() {
        custNewsLetterConfigMapper = new CustNewsLetterConfigMapperImpl();
    }
}
