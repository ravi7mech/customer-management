package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EligibilityDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EligibilityDTO.class);
        EligibilityDTO eligibilityDTO1 = new EligibilityDTO();
        eligibilityDTO1.setId(1L);
        EligibilityDTO eligibilityDTO2 = new EligibilityDTO();
        assertThat(eligibilityDTO1).isNotEqualTo(eligibilityDTO2);
        eligibilityDTO2.setId(eligibilityDTO1.getId());
        assertThat(eligibilityDTO1).isEqualTo(eligibilityDTO2);
        eligibilityDTO2.setId(2L);
        assertThat(eligibilityDTO1).isNotEqualTo(eligibilityDTO2);
        eligibilityDTO1.setId(null);
        assertThat(eligibilityDTO1).isNotEqualTo(eligibilityDTO2);
    }
}
