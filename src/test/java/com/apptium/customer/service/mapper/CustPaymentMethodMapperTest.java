package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustPaymentMethodMapperTest {

    private CustPaymentMethodMapper custPaymentMethodMapper;

    @BeforeEach
    public void setUp() {
        custPaymentMethodMapper = new CustPaymentMethodMapperImpl();
    }
}
