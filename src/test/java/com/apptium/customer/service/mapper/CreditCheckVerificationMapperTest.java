package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreditCheckVerificationMapperTest {

    private CreditCheckVerificationMapper creditCheckVerificationMapper;

    @BeforeEach
    public void setUp() {
        creditCheckVerificationMapper = new CreditCheckVerificationMapperImpl();
    }
}
