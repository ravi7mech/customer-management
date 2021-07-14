package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CreditCheckVerificationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditCheckVerification.class);
        CreditCheckVerification creditCheckVerification1 = new CreditCheckVerification();
        creditCheckVerification1.setId(1L);
        CreditCheckVerification creditCheckVerification2 = new CreditCheckVerification();
        creditCheckVerification2.setId(creditCheckVerification1.getId());
        assertThat(creditCheckVerification1).isEqualTo(creditCheckVerification2);
        creditCheckVerification2.setId(2L);
        assertThat(creditCheckVerification1).isNotEqualTo(creditCheckVerification2);
        creditCheckVerification1.setId(null);
        assertThat(creditCheckVerification1).isNotEqualTo(creditCheckVerification2);
    }
}
