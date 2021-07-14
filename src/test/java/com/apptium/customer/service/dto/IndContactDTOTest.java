package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IndContactDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndContactDTO.class);
        IndContactDTO indContactDTO1 = new IndContactDTO();
        indContactDTO1.setId(1L);
        IndContactDTO indContactDTO2 = new IndContactDTO();
        assertThat(indContactDTO1).isNotEqualTo(indContactDTO2);
        indContactDTO2.setId(indContactDTO1.getId());
        assertThat(indContactDTO1).isEqualTo(indContactDTO2);
        indContactDTO2.setId(2L);
        assertThat(indContactDTO1).isNotEqualTo(indContactDTO2);
        indContactDTO1.setId(null);
        assertThat(indContactDTO1).isNotEqualTo(indContactDTO2);
    }
}
