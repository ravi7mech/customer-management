package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustBillingRefDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustBillingRefDTO.class);
        CustBillingRefDTO custBillingRefDTO1 = new CustBillingRefDTO();
        custBillingRefDTO1.setId(1L);
        CustBillingRefDTO custBillingRefDTO2 = new CustBillingRefDTO();
        assertThat(custBillingRefDTO1).isNotEqualTo(custBillingRefDTO2);
        custBillingRefDTO2.setId(custBillingRefDTO1.getId());
        assertThat(custBillingRefDTO1).isEqualTo(custBillingRefDTO2);
        custBillingRefDTO2.setId(2L);
        assertThat(custBillingRefDTO1).isNotEqualTo(custBillingRefDTO2);
        custBillingRefDTO1.setId(null);
        assertThat(custBillingRefDTO1).isNotEqualTo(custBillingRefDTO2);
    }
}
