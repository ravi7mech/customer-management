package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AutoPayTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutoPay.class);
        AutoPay autoPay1 = new AutoPay();
        autoPay1.setId(1L);
        AutoPay autoPay2 = new AutoPay();
        autoPay2.setId(autoPay1.getId());
        assertThat(autoPay1).isEqualTo(autoPay2);
        autoPay2.setId(2L);
        assertThat(autoPay1).isNotEqualTo(autoPay2);
        autoPay1.setId(null);
        assertThat(autoPay1).isNotEqualTo(autoPay2);
    }
}
