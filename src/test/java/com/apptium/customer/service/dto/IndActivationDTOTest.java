package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IndActivationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndActivationDTO.class);
        IndActivationDTO indActivationDTO1 = new IndActivationDTO();
        indActivationDTO1.setId(1L);
        IndActivationDTO indActivationDTO2 = new IndActivationDTO();
        assertThat(indActivationDTO1).isNotEqualTo(indActivationDTO2);
        indActivationDTO2.setId(indActivationDTO1.getId());
        assertThat(indActivationDTO1).isEqualTo(indActivationDTO2);
        indActivationDTO2.setId(2L);
        assertThat(indActivationDTO1).isNotEqualTo(indActivationDTO2);
        indActivationDTO1.setId(null);
        assertThat(indActivationDTO1).isNotEqualTo(indActivationDTO2);
    }
}
