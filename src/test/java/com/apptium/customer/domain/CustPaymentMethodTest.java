package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustPaymentMethodTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustPaymentMethod.class);
        CustPaymentMethod custPaymentMethod1 = new CustPaymentMethod();
        custPaymentMethod1.setId(1L);
        CustPaymentMethod custPaymentMethod2 = new CustPaymentMethod();
        custPaymentMethod2.setId(custPaymentMethod1.getId());
        assertThat(custPaymentMethod1).isEqualTo(custPaymentMethod2);
        custPaymentMethod2.setId(2L);
        assertThat(custPaymentMethod1).isNotEqualTo(custPaymentMethod2);
        custPaymentMethod1.setId(null);
        assertThat(custPaymentMethod1).isNotEqualTo(custPaymentMethod2);
    }
}
