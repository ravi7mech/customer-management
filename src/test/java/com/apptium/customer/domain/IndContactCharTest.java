package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IndContactCharTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndContactChar.class);
        IndContactChar indContactChar1 = new IndContactChar();
        indContactChar1.setId(1L);
        IndContactChar indContactChar2 = new IndContactChar();
        indContactChar2.setId(indContactChar1.getId());
        assertThat(indContactChar1).isEqualTo(indContactChar2);
        indContactChar2.setId(2L);
        assertThat(indContactChar1).isNotEqualTo(indContactChar2);
        indContactChar1.setId(null);
        assertThat(indContactChar1).isNotEqualTo(indContactChar2);
    }
}
