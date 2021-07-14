package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustStatisticsMapperTest {

    private CustStatisticsMapper custStatisticsMapper;

    @BeforeEach
    public void setUp() {
        custStatisticsMapper = new CustStatisticsMapperImpl();
    }
}
