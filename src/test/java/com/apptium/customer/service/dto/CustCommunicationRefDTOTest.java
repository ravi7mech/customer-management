package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustCommunicationRefDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustCommunicationRefDTO.class);
        CustCommunicationRefDTO custCommunicationRefDTO1 = new CustCommunicationRefDTO();
        custCommunicationRefDTO1.setId(1L);
        CustCommunicationRefDTO custCommunicationRefDTO2 = new CustCommunicationRefDTO();
        assertThat(custCommunicationRefDTO1).isNotEqualTo(custCommunicationRefDTO2);
        custCommunicationRefDTO2.setId(custCommunicationRefDTO1.getId());
        assertThat(custCommunicationRefDTO1).isEqualTo(custCommunicationRefDTO2);
        custCommunicationRefDTO2.setId(2L);
        assertThat(custCommunicationRefDTO1).isNotEqualTo(custCommunicationRefDTO2);
        custCommunicationRefDTO1.setId(null);
        assertThat(custCommunicationRefDTO1).isNotEqualTo(custCommunicationRefDTO2);
    }
}
