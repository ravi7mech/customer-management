package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ShoppingSessionRefTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShoppingSessionRef.class);
        ShoppingSessionRef shoppingSessionRef1 = new ShoppingSessionRef();
        shoppingSessionRef1.setId(1L);
        ShoppingSessionRef shoppingSessionRef2 = new ShoppingSessionRef();
        shoppingSessionRef2.setId(shoppingSessionRef1.getId());
        assertThat(shoppingSessionRef1).isEqualTo(shoppingSessionRef2);
        shoppingSessionRef2.setId(2L);
        assertThat(shoppingSessionRef1).isNotEqualTo(shoppingSessionRef2);
        shoppingSessionRef1.setId(null);
        assertThat(shoppingSessionRef1).isNotEqualTo(shoppingSessionRef2);
    }
}
