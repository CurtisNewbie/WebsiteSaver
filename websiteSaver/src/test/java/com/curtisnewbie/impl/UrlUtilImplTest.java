package com.curtisnewbie.impl;

import com.curtisnewbie.api.UrlUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UrlUtilImplTest {
    private static final String BASE_URL = "www.google.com";
    private static final String NORMAL_URL = String.format("hTtps://%s/feels/gucci/man", BASE_URL);
    private static final String ILLEGAL_URL = String.format("/%s/feels/gucci/man", BASE_URL);

    @Autowired
    private UrlUtil urlUtil;

    @Test
    void shouldParseBaseUrl() {
        Assertions.assertFalse(urlUtil.parseBaseUrl(NORMAL_URL).isEmpty());
        Assertions.assertTrue(urlUtil.parseBaseUrl(ILLEGAL_URL).isEmpty());
        Assertions.assertTrue(urlUtil.parseBaseUrl(NORMAL_URL).equals(BASE_URL));
    }
}
