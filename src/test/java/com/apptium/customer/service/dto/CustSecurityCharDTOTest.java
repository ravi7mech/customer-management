package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustSecurityCharDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustSecurityCharDTO.class);
        CustSecurityCharDTO custSecurityCharDTO1 = new CustSecurityCharDTO();
        custSecurityCharDTO1.setId(1L);
        CustSecurityCharDTO custSecurityCharDTO2 = new CustSecurityCharDTO();
        assertThat(custSecurityCharDTO1).isNotEqualTo(custSecurityCharDTO2);
        custSecurityCharDTO2.setId(custSecurityCharDTO1.getId());
        assertThat(custSecurityCharDTO1).isEqualTo(custSecurityCharDTO2);
        custSecurityCharDTO2.setId(2L);
        assertThat(custSecurityCharDTO1).isNotEqualTo(custSecurityCharDTO2);
        custSecurityCharDTO1.setId(null);
        assertThat(custSecurityCharDTO1).isNotEqualTo(custSecurityCharDTO2);
    }
}
