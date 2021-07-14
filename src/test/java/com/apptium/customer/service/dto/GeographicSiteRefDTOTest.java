package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GeographicSiteRefDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GeographicSiteRefDTO.class);
        GeographicSiteRefDTO geographicSiteRefDTO1 = new GeographicSiteRefDTO();
        geographicSiteRefDTO1.setId(1L);
        GeographicSiteRefDTO geographicSiteRefDTO2 = new GeographicSiteRefDTO();
        assertThat(geographicSiteRefDTO1).isNotEqualTo(geographicSiteRefDTO2);
        geographicSiteRefDTO2.setId(geographicSiteRefDTO1.getId());
        assertThat(geographicSiteRefDTO1).isEqualTo(geographicSiteRefDTO2);
        geographicSiteRefDTO2.setId(2L);
        assertThat(geographicSiteRefDTO1).isNotEqualTo(geographicSiteRefDTO2);
        geographicSiteRefDTO1.setId(null);
        assertThat(geographicSiteRefDTO1).isNotEqualTo(geographicSiteRefDTO2);
    }
}
