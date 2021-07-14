package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustRelPartyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustRelPartyDTO.class);
        CustRelPartyDTO custRelPartyDTO1 = new CustRelPartyDTO();
        custRelPartyDTO1.setId(1L);
        CustRelPartyDTO custRelPartyDTO2 = new CustRelPartyDTO();
        assertThat(custRelPartyDTO1).isNotEqualTo(custRelPartyDTO2);
        custRelPartyDTO2.setId(custRelPartyDTO1.getId());
        assertThat(custRelPartyDTO1).isEqualTo(custRelPartyDTO2);
        custRelPartyDTO2.setId(2L);
        assertThat(custRelPartyDTO1).isNotEqualTo(custRelPartyDTO2);
        custRelPartyDTO1.setId(null);
        assertThat(custRelPartyDTO1).isNotEqualTo(custRelPartyDTO2);
    }
}
