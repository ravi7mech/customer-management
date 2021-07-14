package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustBillingAccDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustBillingAccDTO.class);
        CustBillingAccDTO custBillingAccDTO1 = new CustBillingAccDTO();
        custBillingAccDTO1.setId(1L);
        CustBillingAccDTO custBillingAccDTO2 = new CustBillingAccDTO();
        assertThat(custBillingAccDTO1).isNotEqualTo(custBillingAccDTO2);
        custBillingAccDTO2.setId(custBillingAccDTO1.getId());
        assertThat(custBillingAccDTO1).isEqualTo(custBillingAccDTO2);
        custBillingAccDTO2.setId(2L);
        assertThat(custBillingAccDTO1).isNotEqualTo(custBillingAccDTO2);
        custBillingAccDTO1.setId(null);
        assertThat(custBillingAccDTO1).isNotEqualTo(custBillingAccDTO2);
    }
}
