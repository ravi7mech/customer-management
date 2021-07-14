package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustCommunicationRefTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustCommunicationRef.class);
        CustCommunicationRef custCommunicationRef1 = new CustCommunicationRef();
        custCommunicationRef1.setId(1L);
        CustCommunicationRef custCommunicationRef2 = new CustCommunicationRef();
        custCommunicationRef2.setId(custCommunicationRef1.getId());
        assertThat(custCommunicationRef1).isEqualTo(custCommunicationRef2);
        custCommunicationRef2.setId(2L);
        assertThat(custCommunicationRef1).isNotEqualTo(custCommunicationRef2);
        custCommunicationRef1.setId(null);
        assertThat(custCommunicationRef1).isNotEqualTo(custCommunicationRef2);
    }
}
