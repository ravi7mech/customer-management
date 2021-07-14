package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IndustryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndustryDTO.class);
        IndustryDTO industryDTO1 = new IndustryDTO();
        industryDTO1.setId(1L);
        IndustryDTO industryDTO2 = new IndustryDTO();
        assertThat(industryDTO1).isNotEqualTo(industryDTO2);
        industryDTO2.setId(industryDTO1.getId());
        assertThat(industryDTO1).isEqualTo(industryDTO2);
        industryDTO2.setId(2L);
        assertThat(industryDTO1).isNotEqualTo(industryDTO2);
        industryDTO1.setId(null);
        assertThat(industryDTO1).isNotEqualTo(industryDTO2);
    }
}
