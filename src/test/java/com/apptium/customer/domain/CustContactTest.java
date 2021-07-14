package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustContactTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustContact.class);
        CustContact custContact1 = new CustContact();
        custContact1.setId(1L);
        CustContact custContact2 = new CustContact();
        custContact2.setId(custContact1.getId());
        assertThat(custContact1).isEqualTo(custContact2);
        custContact2.setId(2L);
        assertThat(custContact1).isNotEqualTo(custContact2);
        custContact1.setId(null);
        assertThat(custContact1).isNotEqualTo(custContact2);
    }
}
