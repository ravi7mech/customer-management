package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustBillingAccMapperTest {

    private CustBillingAccMapper custBillingAccMapper;

    @BeforeEach
    public void setUp() {
        custBillingAccMapper = new CustBillingAccMapperImpl();
    }
}
