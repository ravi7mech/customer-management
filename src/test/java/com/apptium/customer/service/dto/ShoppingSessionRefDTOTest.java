package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ShoppingSessionRefDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShoppingSessionRefDTO.class);
        ShoppingSessionRefDTO shoppingSessionRefDTO1 = new ShoppingSessionRefDTO();
        shoppingSessionRefDTO1.setId(1L);
        ShoppingSessionRefDTO shoppingSessionRefDTO2 = new ShoppingSessionRefDTO();
        assertThat(shoppingSessionRefDTO1).isNotEqualTo(shoppingSessionRefDTO2);
        shoppingSessionRefDTO2.setId(shoppingSessionRefDTO1.getId());
        assertThat(shoppingSessionRefDTO1).isEqualTo(shoppingSessionRefDTO2);
        shoppingSessionRefDTO2.setId(2L);
        assertThat(shoppingSessionRefDTO1).isNotEqualTo(shoppingSessionRefDTO2);
        shoppingSessionRefDTO1.setId(null);
        assertThat(shoppingSessionRefDTO1).isNotEqualTo(shoppingSessionRefDTO2);
    }
}
