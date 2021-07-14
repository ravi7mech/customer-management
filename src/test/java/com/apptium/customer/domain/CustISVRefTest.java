package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustISVRefTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustISVRef.class);
        CustISVRef custISVRef1 = new CustISVRef();
        custISVRef1.setId(1L);
        CustISVRef custISVRef2 = new CustISVRef();
        custISVRef2.setId(custISVRef1.getId());
        assertThat(custISVRef1).isEqualTo(custISVRef2);
        custISVRef2.setId(2L);
        assertThat(custISVRef1).isNotEqualTo(custISVRef2);
        custISVRef1.setId(null);
        assertThat(custISVRef1).isNotEqualTo(custISVRef2);
    }
}
