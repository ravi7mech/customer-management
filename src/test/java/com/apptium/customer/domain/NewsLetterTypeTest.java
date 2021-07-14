package com.apptium.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NewsLetterTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NewsLetterType.class);
        NewsLetterType newsLetterType1 = new NewsLetterType();
        newsLetterType1.setId(1L);
        NewsLetterType newsLetterType2 = new NewsLetterType();
        newsLetterType2.setId(newsLetterType1.getId());
        assertThat(newsLetterType1).isEqualTo(newsLetterType2);
        newsLetterType2.setId(2L);
        assertThat(newsLetterType1).isNotEqualTo(newsLetterType2);
        newsLetterType1.setId(null);
        assertThat(newsLetterType1).isNotEqualTo(newsLetterType2);
    }
}
