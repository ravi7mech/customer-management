package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IndNewsLetterConfTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndNewsLetterConf.class);
        IndNewsLetterConf indNewsLetterConf1 = new IndNewsLetterConf();
        indNewsLetterConf1.setId(1L);
        IndNewsLetterConf indNewsLetterConf2 = new IndNewsLetterConf();
        indNewsLetterConf2.setId(indNewsLetterConf1.getId());
        assertThat(indNewsLetterConf1).isEqualTo(indNewsLetterConf2);
        indNewsLetterConf2.setId(2L);
        assertThat(indNewsLetterConf1).isNotEqualTo(indNewsLetterConf2);
        indNewsLetterConf1.setId(null);
        assertThat(indNewsLetterConf1).isNotEqualTo(indNewsLetterConf2);
    }
}
