package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustISVCharTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustISVChar.class);
        CustISVChar custISVChar1 = new CustISVChar();
        custISVChar1.setId(1L);
        CustISVChar custISVChar2 = new CustISVChar();
        custISVChar2.setId(custISVChar1.getId());
        assertThat(custISVChar1).isEqualTo(custISVChar2);
        custISVChar2.setId(2L);
        assertThat(custISVChar1).isNotEqualTo(custISVChar2);
        custISVChar1.setId(null);
        assertThat(custISVChar1).isNotEqualTo(custISVChar2);
    }
}
