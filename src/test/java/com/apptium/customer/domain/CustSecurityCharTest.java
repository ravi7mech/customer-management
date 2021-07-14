package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustSecurityCharTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustSecurityChar.class);
        CustSecurityChar custSecurityChar1 = new CustSecurityChar();
        custSecurityChar1.setId(1L);
        CustSecurityChar custSecurityChar2 = new CustSecurityChar();
        custSecurityChar2.setId(custSecurityChar1.getId());
        assertThat(custSecurityChar1).isEqualTo(custSecurityChar2);
        custSecurityChar2.setId(2L);
        assertThat(custSecurityChar1).isNotEqualTo(custSecurityChar2);
        custSecurityChar1.setId(null);
        assertThat(custSecurityChar1).isNotEqualTo(custSecurityChar2);
    }
}
