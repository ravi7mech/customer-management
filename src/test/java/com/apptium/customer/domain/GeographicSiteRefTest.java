package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GeographicSiteRefTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GeographicSiteRef.class);
        GeographicSiteRef geographicSiteRef1 = new GeographicSiteRef();
        geographicSiteRef1.setId(1L);
        GeographicSiteRef geographicSiteRef2 = new GeographicSiteRef();
        geographicSiteRef2.setId(geographicSiteRef1.getId());
        assertThat(geographicSiteRef1).isEqualTo(geographicSiteRef2);
        geographicSiteRef2.setId(2L);
        assertThat(geographicSiteRef1).isNotEqualTo(geographicSiteRef2);
        geographicSiteRef1.setId(null);
        assertThat(geographicSiteRef1).isNotEqualTo(geographicSiteRef2);
    }
}
