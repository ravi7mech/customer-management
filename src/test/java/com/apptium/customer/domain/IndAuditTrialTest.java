package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IndAuditTrialTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndAuditTrial.class);
        IndAuditTrial indAuditTrial1 = new IndAuditTrial();
        indAuditTrial1.setId(1L);
        IndAuditTrial indAuditTrial2 = new IndAuditTrial();
        indAuditTrial2.setId(indAuditTrial1.getId());
        assertThat(indAuditTrial1).isEqualTo(indAuditTrial2);
        indAuditTrial2.setId(2L);
        assertThat(indAuditTrial1).isNotEqualTo(indAuditTrial2);
        indAuditTrial1.setId(null);
        assertThat(indAuditTrial1).isNotEqualTo(indAuditTrial2);
    }
}
