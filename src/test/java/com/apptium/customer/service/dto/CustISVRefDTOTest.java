package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustISVRefDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustISVRefDTO.class);
        CustISVRefDTO custISVRefDTO1 = new CustISVRefDTO();
        custISVRefDTO1.setId(1L);
        CustISVRefDTO custISVRefDTO2 = new CustISVRefDTO();
        assertThat(custISVRefDTO1).isNotEqualTo(custISVRefDTO2);
        custISVRefDTO2.setId(custISVRefDTO1.getId());
        assertThat(custISVRefDTO1).isEqualTo(custISVRefDTO2);
        custISVRefDTO2.setId(2L);
        assertThat(custISVRefDTO1).isNotEqualTo(custISVRefDTO2);
        custISVRefDTO1.setId(null);
        assertThat(custISVRefDTO1).isNotEqualTo(custISVRefDTO2);
    }
}
