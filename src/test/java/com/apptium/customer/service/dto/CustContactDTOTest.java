package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustContactDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustContactDTO.class);
        CustContactDTO custContactDTO1 = new CustContactDTO();
        custContactDTO1.setId(1L);
        CustContactDTO custContactDTO2 = new CustContactDTO();
        assertThat(custContactDTO1).isNotEqualTo(custContactDTO2);
        custContactDTO2.setId(custContactDTO1.getId());
        assertThat(custContactDTO1).isEqualTo(custContactDTO2);
        custContactDTO2.setId(2L);
        assertThat(custContactDTO1).isNotEqualTo(custContactDTO2);
        custContactDTO1.setId(null);
        assertThat(custContactDTO1).isNotEqualTo(custContactDTO2);
    }
}
