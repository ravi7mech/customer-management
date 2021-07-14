package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankCardTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankCardTypeDTO.class);
        BankCardTypeDTO bankCardTypeDTO1 = new BankCardTypeDTO();
        bankCardTypeDTO1.setId(1L);
        BankCardTypeDTO bankCardTypeDTO2 = new BankCardTypeDTO();
        assertThat(bankCardTypeDTO1).isNotEqualTo(bankCardTypeDTO2);
        bankCardTypeDTO2.setId(bankCardTypeDTO1.getId());
        assertThat(bankCardTypeDTO1).isEqualTo(bankCardTypeDTO2);
        bankCardTypeDTO2.setId(2L);
        assertThat(bankCardTypeDTO1).isNotEqualTo(bankCardTypeDTO2);
        bankCardTypeDTO1.setId(null);
        assertThat(bankCardTypeDTO1).isNotEqualTo(bankCardTypeDTO2);
    }
}
