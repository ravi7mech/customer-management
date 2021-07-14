package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CreditCheckVerificationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditCheckVerificationDTO.class);
        CreditCheckVerificationDTO creditCheckVerificationDTO1 = new CreditCheckVerificationDTO();
        creditCheckVerificationDTO1.setId(1L);
        CreditCheckVerificationDTO creditCheckVerificationDTO2 = new CreditCheckVerificationDTO();
        assertThat(creditCheckVerificationDTO1).isNotEqualTo(creditCheckVerificationDTO2);
        creditCheckVerificationDTO2.setId(creditCheckVerificationDTO1.getId());
        assertThat(creditCheckVerificationDTO1).isEqualTo(creditCheckVerificationDTO2);
        creditCheckVerificationDTO2.setId(2L);
        assertThat(creditCheckVerificationDTO1).isNotEqualTo(creditCheckVerificationDTO2);
        creditCheckVerificationDTO1.setId(null);
        assertThat(creditCheckVerificationDTO1).isNotEqualTo(creditCheckVerificationDTO2);
    }
}
