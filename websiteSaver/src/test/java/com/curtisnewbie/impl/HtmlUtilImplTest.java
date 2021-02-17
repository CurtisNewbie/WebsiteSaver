package com.curtisnewbie.impl;

import com.curtisnewbie.api.HtmlUtil;
import com.curtisnewbie.api.PdfUtil;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * This test won't always work
 */
@SpringBootTest
public class HtmlUtilImplTest {

    // this may not always work
    private String HTML_CONTENT_URL = "https://docs.oracle.com/javase/7/docs/api/java/net/HttpURLConnection.html";

    @Autowired
    private HtmlUtil htmlUtil;

    @Autowired
    private PdfUtil pdfUtil;

    @Test
    @Disabled
    void shouldGrabHtmlContent() throws IOException, HtmlContentIncorrectException {
        Document doc = htmlUtil.grapDoc(HTML_CONTENT_URL);
        Assertions.assertNotNull(doc);
        if (doc != null) {
            System.out.println(doc.toString());
        }
    }
}
