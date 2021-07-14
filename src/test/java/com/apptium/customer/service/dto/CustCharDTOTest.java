package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustCharDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustCharDTO.class);
        CustCharDTO custCharDTO1 = new CustCharDTO();
        custCharDTO1.setId(1L);
        CustCharDTO custCharDTO2 = new CustCharDTO();
        assertThat(custCharDTO1).isNotEqualTo(custCharDTO2);
        custCharDTO2.setId(custCharDTO1.getId());
        assertThat(custCharDTO1).isEqualTo(custCharDTO2);
        custCharDTO2.setId(2L);
        assertThat(custCharDTO1).isNotEqualTo(custCharDTO2);
        custCharDTO1.setId(null);
        assertThat(custCharDTO1).isNotEqualTo(custCharDTO2);
    }
}
