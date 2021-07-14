package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustRelPartyMapperTest {

    private CustRelPartyMapper custRelPartyMapper;

    @BeforeEach
    public void setUp() {
        custRelPartyMapper = new CustRelPartyMapperImpl();
    }
}
