package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IndAuditTrialDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndAuditTrialDTO.class);
        IndAuditTrialDTO indAuditTrialDTO1 = new IndAuditTrialDTO();
        indAuditTrialDTO1.setId(1L);
        IndAuditTrialDTO indAuditTrialDTO2 = new IndAuditTrialDTO();
        assertThat(indAuditTrialDTO1).isNotEqualTo(indAuditTrialDTO2);
        indAuditTrialDTO2.setId(indAuditTrialDTO1.getId());
        assertThat(indAuditTrialDTO1).isEqualTo(indAuditTrialDTO2);
        indAuditTrialDTO2.setId(2L);
        assertThat(indAuditTrialDTO1).isNotEqualTo(indAuditTrialDTO2);
        indAuditTrialDTO1.setId(null);
        assertThat(indAuditTrialDTO1).isNotEqualTo(indAuditTrialDTO2);
    }
}
