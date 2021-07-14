package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IndividualDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndividualDTO.class);
        IndividualDTO individualDTO1 = new IndividualDTO();
        individualDTO1.setId(1L);
        IndividualDTO individualDTO2 = new IndividualDTO();
        assertThat(individualDTO1).isNotEqualTo(individualDTO2);
        individualDTO2.setId(individualDTO1.getId());
        assertThat(individualDTO1).isEqualTo(individualDTO2);
        individualDTO2.setId(2L);
        assertThat(individualDTO1).isNotEqualTo(individualDTO2);
        individualDTO1.setId(null);
        assertThat(individualDTO1).isNotEqualTo(individualDTO2);
    }
}
