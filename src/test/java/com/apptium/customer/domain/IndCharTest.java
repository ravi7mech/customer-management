package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IndCharTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndChar.class);
        IndChar indChar1 = new IndChar();
        indChar1.setId(1L);
        IndChar indChar2 = new IndChar();
        indChar2.setId(indChar1.getId());
        assertThat(indChar1).isEqualTo(indChar2);
        indChar2.setId(2L);
        assertThat(indChar1).isNotEqualTo(indChar2);
        indChar1.setId(null);
        assertThat(indChar1).isNotEqualTo(indChar2);
    }
}
