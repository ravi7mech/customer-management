package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IndNewsLetterConfDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndNewsLetterConfDTO.class);
        IndNewsLetterConfDTO indNewsLetterConfDTO1 = new IndNewsLetterConfDTO();
        indNewsLetterConfDTO1.setId(1L);
        IndNewsLetterConfDTO indNewsLetterConfDTO2 = new IndNewsLetterConfDTO();
        assertThat(indNewsLetterConfDTO1).isNotEqualTo(indNewsLetterConfDTO2);
        indNewsLetterConfDTO2.setId(indNewsLetterConfDTO1.getId());
        assertThat(indNewsLetterConfDTO1).isEqualTo(indNewsLetterConfDTO2);
        indNewsLetterConfDTO2.setId(2L);
        assertThat(indNewsLetterConfDTO1).isNotEqualTo(indNewsLetterConfDTO2);
        indNewsLetterConfDTO1.setId(null);
        assertThat(indNewsLetterConfDTO1).isNotEqualTo(indNewsLetterConfDTO2);
    }
}
