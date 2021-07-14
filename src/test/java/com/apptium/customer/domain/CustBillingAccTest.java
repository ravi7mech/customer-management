package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustBillingAccTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustBillingAcc.class);
        CustBillingAcc custBillingAcc1 = new CustBillingAcc();
        custBillingAcc1.setId(1L);
        CustBillingAcc custBillingAcc2 = new CustBillingAcc();
        custBillingAcc2.setId(custBillingAcc1.getId());
        assertThat(custBillingAcc1).isEqualTo(custBillingAcc2);
        custBillingAcc2.setId(2L);
        assertThat(custBillingAcc1).isNotEqualTo(custBillingAcc2);
        custBillingAcc1.setId(null);
        assertThat(custBillingAcc1).isNotEqualTo(custBillingAcc2);
    }
}
