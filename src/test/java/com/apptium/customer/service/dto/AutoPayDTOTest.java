package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AutoPayDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AutoPayDTO.class);
        AutoPayDTO autoPayDTO1 = new AutoPayDTO();
        autoPayDTO1.setId(1L);
        AutoPayDTO autoPayDTO2 = new AutoPayDTO();
        assertThat(autoPayDTO1).isNotEqualTo(autoPayDTO2);
        autoPayDTO2.setId(autoPayDTO1.getId());
        assertThat(autoPayDTO1).isEqualTo(autoPayDTO2);
        autoPayDTO2.setId(2L);
        assertThat(autoPayDTO1).isNotEqualTo(autoPayDTO2);
        autoPayDTO1.setId(null);
        assertThat(autoPayDTO1).isNotEqualTo(autoPayDTO2);
    }
}
