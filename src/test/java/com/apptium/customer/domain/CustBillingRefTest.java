package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustBillingRefTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustBillingRef.class);
        CustBillingRef custBillingRef1 = new CustBillingRef();
        custBillingRef1.setId(1L);
        CustBillingRef custBillingRef2 = new CustBillingRef();
        custBillingRef2.setId(custBillingRef1.getId());
        assertThat(custBillingRef1).isEqualTo(custBillingRef2);
        custBillingRef2.setId(2L);
        assertThat(custBillingRef1).isNotEqualTo(custBillingRef2);
        custBillingRef1.setId(null);
        assertThat(custBillingRef1).isNotEqualTo(custBillingRef2);
    }
}
