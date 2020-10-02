package com.curtisnewbie.web;

import com.curtisnewbie.api.*;
import com.curtisnewbie.dto.QueryEntity;
import com.curtisnewbie.exception.DecryptionFailureException;
import com.curtisnewbie.impl.HtmlContentIncorrectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author zhuangyongj
 */
@RestController
@RequestMapping("/download")
public class HtmlDownloadController {

    private static final Logger logger = LoggerFactory.getLogger(HtmlDownloadController.class);

    @Autowired
    private HtmlUtil htmlUtil;

    @Autowired
    private PdfUtil pdfUtil;

    @Autowired
    private TaskHandler taskHandler;

    @Autowired
    private ChromeUtil chromeUtil;

    @Autowired
    private RsaDecryptionService rsaDecryptionService;

    @Autowired
    private AuthService authService;

    @Value("${rootDir}")
    private String rootDir;

    @PostMapping("/with/itext")
    public ResponseEntity fetchAndConvert2Pdf(@RequestBody QueryEntity q) {
        logger.info("Received QueryEntity: {}", q.toString());
        try {
            String authKey = rsaDecryptionService.decrypt(q.getAuthKey());
            if (authService.isAuthenticated(authKey)) {
                taskHandler.asyncHandle(() -> {
                    fetchAndConvert2pdf(q.getUrl(), q.getPath());
                }, "fetchAndConvert2Pdf");
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (DecryptionFailureException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/with/chrome")
    public ResponseEntity convertWithChrome(@RequestBody QueryEntity q) {
        logger.info("Received QueryEntity: {}", q.toString());
        try {
            String authKey = rsaDecryptionService.decrypt(q.getAuthKey());
            if (authService.isAuthenticated(authKey)) {
                taskHandler.asyncHandle(() -> {
                    grab2PdfWithChrome(q.getUrl(), q.getPath());
                }, "grab2PdfWithChrome");
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (DecryptionFailureException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Grab website as pdf using google-chrome
     *
     * @param url
     * @param path
     */
    private void grab2PdfWithChrome(String url, String path) {
        chromeUtil.grab2Pdf(url, path);
    }

    /**
     * Grab pure html content and convert to pdf programmatically with Itext library
     *
     * @param url
     * @param path
     */
    private void fetchAndConvert2pdf(String url, String path) {
        logger.info(">>> [BEGIN] Request fetching and converting webpage '{}' to pdf '{}'", url, path);
        try {
            String ctn = htmlUtil.grabHtmlContent(url);
            String baseUrl = htmlUtil.extractBaseUrl(ctn); // check if a base url is declared
            if (baseUrl == null)
                baseUrl = url;
            if (!pdfUtil.toPdfFile(ctn, baseUrl, path)) {
                logger.error(">>> [ERROR] Failed to convert to pdf");
            }
        } catch (IOException | HtmlContentIncorrectException e) {
            logger.error(">>> [ERROR] Failed to fetch html content. Error Msg: {}", e);
        }
        logger.info(">>> [END] Finish fetching and converting webpage {} to pdf {}", url, path);
    }
}
