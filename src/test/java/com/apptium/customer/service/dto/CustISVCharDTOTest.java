package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustISVCharDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustISVCharDTO.class);
        CustISVCharDTO custISVCharDTO1 = new CustISVCharDTO();
        custISVCharDTO1.setId(1L);
        CustISVCharDTO custISVCharDTO2 = new CustISVCharDTO();
        assertThat(custISVCharDTO1).isNotEqualTo(custISVCharDTO2);
        custISVCharDTO2.setId(custISVCharDTO1.getId());
        assertThat(custISVCharDTO1).isEqualTo(custISVCharDTO2);
        custISVCharDTO2.setId(2L);
        assertThat(custISVCharDTO1).isNotEqualTo(custISVCharDTO2);
        custISVCharDTO1.setId(null);
        assertThat(custISVCharDTO1).isNotEqualTo(custISVCharDTO2);
    }
}
