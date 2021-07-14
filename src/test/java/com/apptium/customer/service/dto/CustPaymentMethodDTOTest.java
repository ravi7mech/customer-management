package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustPaymentMethodDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustPaymentMethodDTO.class);
        CustPaymentMethodDTO custPaymentMethodDTO1 = new CustPaymentMethodDTO();
        custPaymentMethodDTO1.setId(1L);
        CustPaymentMethodDTO custPaymentMethodDTO2 = new CustPaymentMethodDTO();
        assertThat(custPaymentMethodDTO1).isNotEqualTo(custPaymentMethodDTO2);
        custPaymentMethodDTO2.setId(custPaymentMethodDTO1.getId());
        assertThat(custPaymentMethodDTO1).isEqualTo(custPaymentMethodDTO2);
        custPaymentMethodDTO2.setId(2L);
        assertThat(custPaymentMethodDTO1).isNotEqualTo(custPaymentMethodDTO2);
        custPaymentMethodDTO1.setId(null);
        assertThat(custPaymentMethodDTO1).isNotEqualTo(custPaymentMethodDTO2);
    }
}
