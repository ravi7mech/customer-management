package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoleTypeRefMapperTest {

    private RoleTypeRefMapper roleTypeRefMapper;

    @BeforeEach
    public void setUp() {
        roleTypeRefMapper = new RoleTypeRefMapperImpl();
    }
}
