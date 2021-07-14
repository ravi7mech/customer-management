package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustContactCharDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustContactCharDTO.class);
        CustContactCharDTO custContactCharDTO1 = new CustContactCharDTO();
        custContactCharDTO1.setId(1L);
        CustContactCharDTO custContactCharDTO2 = new CustContactCharDTO();
        assertThat(custContactCharDTO1).isNotEqualTo(custContactCharDTO2);
        custContactCharDTO2.setId(custContactCharDTO1.getId());
        assertThat(custContactCharDTO1).isEqualTo(custContactCharDTO2);
        custContactCharDTO2.setId(2L);
        assertThat(custContactCharDTO1).isNotEqualTo(custContactCharDTO2);
        custContactCharDTO1.setId(null);
        assertThat(custContactCharDTO1).isNotEqualTo(custContactCharDTO2);
    }
}
