package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankCardTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankCardType.class);
        BankCardType bankCardType1 = new BankCardType();
        bankCardType1.setId(1L);
        BankCardType bankCardType2 = new BankCardType();
        bankCardType2.setId(bankCardType1.getId());
        assertThat(bankCardType1).isEqualTo(bankCardType2);
        bankCardType2.setId(2L);
        assertThat(bankCardType1).isNotEqualTo(bankCardType2);
        bankCardType1.setId(null);
        assertThat(bankCardType1).isNotEqualTo(bankCardType2);
    }
}
