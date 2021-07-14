package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustCreditProfileDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustCreditProfileDTO.class);
        CustCreditProfileDTO custCreditProfileDTO1 = new CustCreditProfileDTO();
        custCreditProfileDTO1.setId(1L);
        CustCreditProfileDTO custCreditProfileDTO2 = new CustCreditProfileDTO();
        assertThat(custCreditProfileDTO1).isNotEqualTo(custCreditProfileDTO2);
        custCreditProfileDTO2.setId(custCreditProfileDTO1.getId());
        assertThat(custCreditProfileDTO1).isEqualTo(custCreditProfileDTO2);
        custCreditProfileDTO2.setId(2L);
        assertThat(custCreditProfileDTO1).isNotEqualTo(custCreditProfileDTO2);
        custCreditProfileDTO1.setId(null);
        assertThat(custCreditProfileDTO1).isNotEqualTo(custCreditProfileDTO2);
    }
}
