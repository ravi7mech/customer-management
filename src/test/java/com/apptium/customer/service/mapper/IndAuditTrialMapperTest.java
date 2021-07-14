package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IndAuditTrialMapperTest {

    private IndAuditTrialMapper indAuditTrialMapper;

    @BeforeEach
    public void setUp() {
        indAuditTrialMapper = new IndAuditTrialMapperImpl();
    }
}
