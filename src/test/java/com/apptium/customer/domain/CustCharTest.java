package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustCharTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustChar.class);
        CustChar custChar1 = new CustChar();
        custChar1.setId(1L);
        CustChar custChar2 = new CustChar();
        custChar2.setId(custChar1.getId());
        assertThat(custChar1).isEqualTo(custChar2);
        custChar2.setId(2L);
        assertThat(custChar1).isNotEqualTo(custChar2);
        custChar1.setId(null);
        assertThat(custChar1).isNotEqualTo(custChar2);
    }
}
