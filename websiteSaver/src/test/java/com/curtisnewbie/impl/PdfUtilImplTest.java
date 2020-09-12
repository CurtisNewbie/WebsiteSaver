package com.curtisnewbie.impl;

import com.curtisnewbie.api.PdfUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootTest
public class PdfUtilImplTest {

    private static final String HTML_CONTENT = "<h1>Hello World</h1><p>conversion success</p><b>random stuff</b>";
    private static final String DIR = "target/pdf";
    private static final String PDF_PATH = String.format("%s/test.pdf", DIR);

    @Autowired
    private PdfUtil pdfUtil;

    @Test
    public void shouldConvertToPdfFile() throws IOException {
        // conversion success
        Assertions.assertTrue(pdfUtil.toPdfFile(HTML_CONTENT, PDF_PATH));
        // pdf generated
        Assertions.assertTrue(Files.exists(Path.of(PDF_PATH)));
    }
}
