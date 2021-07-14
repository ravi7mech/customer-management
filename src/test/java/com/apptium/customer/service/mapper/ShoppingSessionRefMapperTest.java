package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShoppingSessionRefMapperTest {

    private ShoppingSessionRefMapper shoppingSessionRefMapper;

    @BeforeEach
    public void setUp() {
        shoppingSessionRefMapper = new ShoppingSessionRefMapperImpl();
    }
}
