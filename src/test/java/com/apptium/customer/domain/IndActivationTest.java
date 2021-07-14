package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IndActivationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndActivation.class);
        IndActivation indActivation1 = new IndActivation();
        indActivation1.setId(1L);
        IndActivation indActivation2 = new IndActivation();
        indActivation2.setId(indActivation1.getId());
        assertThat(indActivation1).isEqualTo(indActivation2);
        indActivation2.setId(2L);
        assertThat(indActivation1).isNotEqualTo(indActivation2);
        indActivation1.setId(null);
        assertThat(indActivation1).isNotEqualTo(indActivation2);
    }
}
