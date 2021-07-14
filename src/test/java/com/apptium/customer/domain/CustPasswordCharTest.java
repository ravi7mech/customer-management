package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustPasswordCharTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustPasswordChar.class);
        CustPasswordChar custPasswordChar1 = new CustPasswordChar();
        custPasswordChar1.setId(1L);
        CustPasswordChar custPasswordChar2 = new CustPasswordChar();
        custPasswordChar2.setId(custPasswordChar1.getId());
        assertThat(custPasswordChar1).isEqualTo(custPasswordChar2);
        custPasswordChar2.setId(2L);
        assertThat(custPasswordChar1).isNotEqualTo(custPasswordChar2);
        custPasswordChar1.setId(null);
        assertThat(custPasswordChar1).isNotEqualTo(custPasswordChar2);
    }
}
