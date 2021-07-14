package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IndContactTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndContact.class);
        IndContact indContact1 = new IndContact();
        indContact1.setId(1L);
        IndContact indContact2 = new IndContact();
        indContact2.setId(indContact1.getId());
        assertThat(indContact1).isEqualTo(indContact2);
        indContact2.setId(2L);
        assertThat(indContact1).isNotEqualTo(indContact2);
        indContact1.setId(null);
        assertThat(indContact1).isNotEqualTo(indContact2);
    }
}
