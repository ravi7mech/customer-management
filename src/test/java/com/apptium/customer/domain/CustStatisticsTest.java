package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CustStatisticsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustStatistics.class);
        CustStatistics custStatistics1 = new CustStatistics();
        custStatistics1.setId(1L);
        CustStatistics custStatistics2 = new CustStatistics();
        custStatistics2.setId(custStatistics1.getId());
        assertThat(custStatistics1).isEqualTo(custStatistics2);
        custStatistics2.setId(2L);
        assertThat(custStatistics1).isNotEqualTo(custStatistics2);
        custStatistics1.setId(null);
        assertThat(custStatistics1).isNotEqualTo(custStatistics2);
    }
}
