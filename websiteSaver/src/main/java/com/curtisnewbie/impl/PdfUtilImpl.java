package com.curtisnewbie.impl;

import com.curtisnewbie.api.PdfUtil;
import com.curtisnewbie.api.UrlUtil;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UrlUtil urlUtil;

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
            logger.error(">>> Failed to convert html content to pdf file. Error Msg: {}", e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean toPdfFile(String htmlContent, String baseUrl, String path) {
        try {
            Path p = Path.of(path);
            if (Files.isDirectory(p)) {  // is a dir
                logger.info(">>> {} is a directory, aborting...", p.toAbsolutePath());
                return false;
            }
            Path parent = p.getParent();
            if (!Files.exists(parent)) { // create dir if not exists
                logger.info(">>> Parent directory {} not exists, creating directory.", parent.toAbsolutePath());
                Files.createDirectory(parent);
            }
            ConverterProperties props = new ConverterProperties();
            props.setBaseUri(baseUrl);
            logger.info(">>> Start PDF conversion...");
            HtmlConverter.convertToPdf(htmlContent, new FileOutputStream(path), props);
        } catch (Exception e) {
            logger.error(">>> Failed to convert html content to pdf file. Error Msg: {}", e.getMessage());
            return false;
        }
        logger.info(">>> Finish PDF conversion...");
        return true;
    }

}
