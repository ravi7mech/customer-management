package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IndividualTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Individual.class);
        Individual individual1 = new Individual();
        individual1.setId(1L);
        Individual individual2 = new Individual();
        individual2.setId(individual1.getId());
        assertThat(individual1).isEqualTo(individual2);
        individual2.setId(2L);
        assertThat(individual1).isNotEqualTo(individual2);
        individual1.setId(null);
        assertThat(individual1).isNotEqualTo(individual2);
    }
}
