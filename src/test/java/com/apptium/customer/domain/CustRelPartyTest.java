package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustRelPartyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustRelParty.class);
        CustRelParty custRelParty1 = new CustRelParty();
        custRelParty1.setId(1L);
        CustRelParty custRelParty2 = new CustRelParty();
        custRelParty2.setId(custRelParty1.getId());
        assertThat(custRelParty1).isEqualTo(custRelParty2);
        custRelParty2.setId(2L);
        assertThat(custRelParty1).isNotEqualTo(custRelParty2);
        custRelParty1.setId(null);
        assertThat(custRelParty1).isNotEqualTo(custRelParty2);
    }
}
