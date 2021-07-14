package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustCommunicationRefMapperTest {

    private CustCommunicationRefMapper custCommunicationRefMapper;

    @BeforeEach
    public void setUp() {
        custCommunicationRefMapper = new CustCommunicationRefMapperImpl();
    }
}
