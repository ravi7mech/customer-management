package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RoleTypeRefTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoleTypeRef.class);
        RoleTypeRef roleTypeRef1 = new RoleTypeRef();
        roleTypeRef1.setId(1L);
        RoleTypeRef roleTypeRef2 = new RoleTypeRef();
        roleTypeRef2.setId(roleTypeRef1.getId());
        assertThat(roleTypeRef1).isEqualTo(roleTypeRef2);
        roleTypeRef2.setId(2L);
        assertThat(roleTypeRef1).isNotEqualTo(roleTypeRef2);
        roleTypeRef1.setId(null);
        assertThat(roleTypeRef1).isNotEqualTo(roleTypeRef2);
    }
}
