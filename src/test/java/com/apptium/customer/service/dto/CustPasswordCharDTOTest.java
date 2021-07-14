package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustPasswordCharDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustPasswordCharDTO.class);
        CustPasswordCharDTO custPasswordCharDTO1 = new CustPasswordCharDTO();
        custPasswordCharDTO1.setId(1L);
        CustPasswordCharDTO custPasswordCharDTO2 = new CustPasswordCharDTO();
        assertThat(custPasswordCharDTO1).isNotEqualTo(custPasswordCharDTO2);
        custPasswordCharDTO2.setId(custPasswordCharDTO1.getId());
        assertThat(custPasswordCharDTO1).isEqualTo(custPasswordCharDTO2);
        custPasswordCharDTO2.setId(2L);
        assertThat(custPasswordCharDTO1).isNotEqualTo(custPasswordCharDTO2);
        custPasswordCharDTO1.setId(null);
        assertThat(custPasswordCharDTO1).isNotEqualTo(custPasswordCharDTO2);
    }
}
