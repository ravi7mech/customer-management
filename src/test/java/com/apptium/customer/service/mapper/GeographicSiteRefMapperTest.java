package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GeographicSiteRefMapperTest {

    private GeographicSiteRefMapper geographicSiteRefMapper;

    @BeforeEach
    public void setUp() {
        geographicSiteRefMapper = new GeographicSiteRefMapperImpl();
    }
}
