package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IndContactCharDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndContactCharDTO.class);
        IndContactCharDTO indContactCharDTO1 = new IndContactCharDTO();
        indContactCharDTO1.setId(1L);
        IndContactCharDTO indContactCharDTO2 = new IndContactCharDTO();
        assertThat(indContactCharDTO1).isNotEqualTo(indContactCharDTO2);
        indContactCharDTO2.setId(indContactCharDTO1.getId());
        assertThat(indContactCharDTO1).isEqualTo(indContactCharDTO2);
        indContactCharDTO2.setId(2L);
        assertThat(indContactCharDTO1).isNotEqualTo(indContactCharDTO2);
        indContactCharDTO1.setId(null);
        assertThat(indContactCharDTO1).isNotEqualTo(indContactCharDTO2);
    }
}
