package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustCreditProfileMapperTest {

    private CustCreditProfileMapper custCreditProfileMapper;

    @BeforeEach
    public void setUp() {
        custCreditProfileMapper = new CustCreditProfileMapperImpl();
    }
}
