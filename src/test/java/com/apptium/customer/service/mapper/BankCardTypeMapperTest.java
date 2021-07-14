package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankCardTypeMapperTest {

    private BankCardTypeMapper bankCardTypeMapper;

    @BeforeEach
    public void setUp() {
        bankCardTypeMapper = new BankCardTypeMapperImpl();
    }
}
