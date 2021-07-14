package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IndCharDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndCharDTO.class);
        IndCharDTO indCharDTO1 = new IndCharDTO();
        indCharDTO1.setId(1L);
        IndCharDTO indCharDTO2 = new IndCharDTO();
        assertThat(indCharDTO1).isNotEqualTo(indCharDTO2);
        indCharDTO2.setId(indCharDTO1.getId());
        assertThat(indCharDTO1).isEqualTo(indCharDTO2);
        indCharDTO2.setId(2L);
        assertThat(indCharDTO1).isNotEqualTo(indCharDTO2);
        indCharDTO1.setId(null);
        assertThat(indCharDTO1).isNotEqualTo(indCharDTO2);
    }
}
