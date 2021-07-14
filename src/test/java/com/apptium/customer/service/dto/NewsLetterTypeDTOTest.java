package com.apptium.customer.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.apptium.customer.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NewsLetterTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NewsLetterTypeDTO.class);
        NewsLetterTypeDTO newsLetterTypeDTO1 = new NewsLetterTypeDTO();
        newsLetterTypeDTO1.setId(1L);
        NewsLetterTypeDTO newsLetterTypeDTO2 = new NewsLetterTypeDTO();
        assertThat(newsLetterTypeDTO1).isNotEqualTo(newsLetterTypeDTO2);
        newsLetterTypeDTO2.setId(newsLetterTypeDTO1.getId());
        assertThat(newsLetterTypeDTO1).isEqualTo(newsLetterTypeDTO2);
        newsLetterTypeDTO2.setId(2L);
        assertThat(newsLetterTypeDTO1).isNotEqualTo(newsLetterTypeDTO2);
        newsLetterTypeDTO1.setId(null);
        assertThat(newsLetterTypeDTO1).isNotEqualTo(newsLetterTypeDTO2);
    }
}
