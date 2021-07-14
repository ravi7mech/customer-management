package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustNewsLetterConfigTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustNewsLetterConfig.class);
        CustNewsLetterConfig custNewsLetterConfig1 = new CustNewsLetterConfig();
        custNewsLetterConfig1.setId(1L);
        CustNewsLetterConfig custNewsLetterConfig2 = new CustNewsLetterConfig();
        custNewsLetterConfig2.setId(custNewsLetterConfig1.getId());
        assertThat(custNewsLetterConfig1).isEqualTo(custNewsLetterConfig2);
        custNewsLetterConfig2.setId(2L);
        assertThat(custNewsLetterConfig1).isNotEqualTo(custNewsLetterConfig2);
        custNewsLetterConfig1.setId(null);
        assertThat(custNewsLetterConfig1).isNotEqualTo(custNewsLetterConfig2);
    }
}
