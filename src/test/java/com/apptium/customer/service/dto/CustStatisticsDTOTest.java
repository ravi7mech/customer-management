package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustStatisticsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustStatisticsDTO.class);
        CustStatisticsDTO custStatisticsDTO1 = new CustStatisticsDTO();
        custStatisticsDTO1.setId(1L);
        CustStatisticsDTO custStatisticsDTO2 = new CustStatisticsDTO();
        assertThat(custStatisticsDTO1).isNotEqualTo(custStatisticsDTO2);
        custStatisticsDTO2.setId(custStatisticsDTO1.getId());
        assertThat(custStatisticsDTO1).isEqualTo(custStatisticsDTO2);
        custStatisticsDTO2.setId(2L);
        assertThat(custStatisticsDTO1).isNotEqualTo(custStatisticsDTO2);
        custStatisticsDTO1.setId(null);
        assertThat(custStatisticsDTO1).isNotEqualTo(custStatisticsDTO2);
    }
}
