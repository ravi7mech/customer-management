package com.apptium.customer.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NewsLetterTypeMapperTest {

    private NewsLetterTypeMapper newsLetterTypeMapper;

    @BeforeEach
    public void setUp() {
        newsLetterTypeMapper = new NewsLetterTypeMapperImpl();
    }
}
