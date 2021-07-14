package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustCreditProfileTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustCreditProfile.class);
        CustCreditProfile custCreditProfile1 = new CustCreditProfile();
        custCreditProfile1.setId(1L);
        CustCreditProfile custCreditProfile2 = new CustCreditProfile();
        custCreditProfile2.setId(custCreditProfile1.getId());
        assertThat(custCreditProfile1).isEqualTo(custCreditProfile2);
        custCreditProfile2.setId(2L);
        assertThat(custCreditProfile1).isNotEqualTo(custCreditProfile2);
        custCreditProfile1.setId(null);
        assertThat(custCreditProfile1).isNotEqualTo(custCreditProfile2);
    }
}
