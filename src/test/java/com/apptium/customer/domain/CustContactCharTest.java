package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustContactCharTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustContactChar.class);
        CustContactChar custContactChar1 = new CustContactChar();
        custContactChar1.setId(1L);
        CustContactChar custContactChar2 = new CustContactChar();
        custContactChar2.setId(custContactChar1.getId());
        assertThat(custContactChar1).isEqualTo(custContactChar2);
        custContactChar2.setId(2L);
        assertThat(custContactChar1).isNotEqualTo(custContactChar2);
        custContactChar1.setId(null);
        assertThat(custContactChar1).isNotEqualTo(custContactChar2);
    }
}
