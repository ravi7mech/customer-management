package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RoleTypeRefDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoleTypeRefDTO.class);
        RoleTypeRefDTO roleTypeRefDTO1 = new RoleTypeRefDTO();
        roleTypeRefDTO1.setId(1L);
        RoleTypeRefDTO roleTypeRefDTO2 = new RoleTypeRefDTO();
        assertThat(roleTypeRefDTO1).isNotEqualTo(roleTypeRefDTO2);
        roleTypeRefDTO2.setId(roleTypeRefDTO1.getId());
        assertThat(roleTypeRefDTO1).isEqualTo(roleTypeRefDTO2);
        roleTypeRefDTO2.setId(2L);
        assertThat(roleTypeRefDTO1).isNotEqualTo(roleTypeRefDTO2);
        roleTypeRefDTO1.setId(null);
        assertThat(roleTypeRefDTO1).isNotEqualTo(roleTypeRefDTO2);
    }
}
