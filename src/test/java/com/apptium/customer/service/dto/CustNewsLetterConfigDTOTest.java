package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustNewsLetterConfigDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustNewsLetterConfigDTO.class);
        CustNewsLetterConfigDTO custNewsLetterConfigDTO1 = new CustNewsLetterConfigDTO();
        custNewsLetterConfigDTO1.setId(1L);
        CustNewsLetterConfigDTO custNewsLetterConfigDTO2 = new CustNewsLetterConfigDTO();
        assertThat(custNewsLetterConfigDTO1).isNotEqualTo(custNewsLetterConfigDTO2);
        custNewsLetterConfigDTO2.setId(custNewsLetterConfigDTO1.getId());
        assertThat(custNewsLetterConfigDTO1).isEqualTo(custNewsLetterConfigDTO2);
        custNewsLetterConfigDTO2.setId(2L);
        assertThat(custNewsLetterConfigDTO1).isNotEqualTo(custNewsLetterConfigDTO2);
        custNewsLetterConfigDTO1.setId(null);
        assertThat(custNewsLetterConfigDTO1).isNotEqualTo(custNewsLetterConfigDTO2);
    }
}
