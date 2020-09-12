package com.curtisnewbie.impl;

import com.curtisnewbie.api.PdfUtil;
import com.itextpdf.html2pdf.HtmlConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author zhuangyongj
 */
@Component
public class PdfUtilImpl implements PdfUtil {

    private static final Logger logger = LoggerFactory.getLogger(PdfUtilImpl.class);

    @Override
    public boolean toPdfFile(String htmlContent, String path) {
        try {
            Path p = Path.of(path);
            if (Files.isDirectory(p))  // is a dir
                return false;
            Path parent = p.getParent();
            if (!Files.exists(parent)) // create dir if not exists
                Files.createDirectory(parent);
            HtmlConverter.convertToPdf(htmlContent, new FileOutputStream(path));
        } catch (Exception e) {
            logger.error("Failed to convert html content to pdf file. Error Msg: {}", e.getMessage());
            return false;
        }
        return true;
    }
}
