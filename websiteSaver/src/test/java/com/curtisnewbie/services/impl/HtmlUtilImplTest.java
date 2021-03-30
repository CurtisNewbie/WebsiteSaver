package com.curtisnewbie.services.impl;

import com.curtisnewbie.services.api.HtmlUtil;
import com.curtisnewbie.exception.HtmlContentIncorrectException;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

/**
 * This test won't always work
 */
@SpringBootTest
public class HtmlUtilImplTest {

    // this may not always work
    private String HTML_CONTENT_URL = "https://bing.com";

    @Autowired
    private HtmlUtil htmlUtil;

    @Test
    @Disabled
    void shouldFetchHtmlContent() throws IOException, HtmlContentIncorrectException {
        Document doc = htmlUtil.fetchDocument(HTML_CONTENT_URL);
        Assertions.assertNotNull(doc);
        System.out.println(doc.toString());
    }

    @Test
    @Disabled
    void shouldExtractTitle() throws IOException, HtmlContentIncorrectException {
        Document doc = htmlUtil.fetchDocument(HTML_CONTENT_URL);
        Assertions.assertNotNull(doc);
        List<String> titles = htmlUtil.extractTitle(doc);
        Assertions.assertFalse(titles.isEmpty());
        System.out.printf("Titles: %s\n", titles);
    }
}
