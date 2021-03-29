package com.curtisnewbie.web;

import com.curtisnewbie.api.*;
import com.curtisnewbie.dto.QueryEntity;
import com.curtisnewbie.exception.DecryptionFailureException;
import com.curtisnewbie.exception.HtmlContentIncorrectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

/**
 * @author zhuangyongj
 */
@RestController
@RequestMapping("/download")
public class HtmlDownloadController {
    /** Pattern for http:// or https:// */
    private static Pattern HTTP_PROTOCOL_PATTERN = Pattern.compile("^[Hh][Tt][Tt][Pp][Ss]?://.*$");

    private static final Logger logger = LoggerFactory.getLogger(HtmlDownloadController.class);

    @Autowired
    private HtmlUtil htmlUtil;

    @Autowired
    private ChromeUtil chromeUtil;

    @Autowired
    private RsaDecryptionService rsaDecryptionService;

    @Value("${rootDir}")
    private String rootDir;

    @PostMapping("/with/chrome")
    public ResponseEntity convertWithChrome(@RequestBody QueryEntity q) throws DecryptionFailureException {
        if (!StringUtils.hasLength(q.getUrl()))
            return ResponseEntity.badRequest().build();

        String url = rsaDecryptionService.decrypt(q.getUrl());
        if (StringUtils.hasLength(q.getPath())) {
            String path = rsaDecryptionService.decrypt(q.getPath());
            CompletableFuture.runAsync(() -> {
                try {
                    grab2PdfWithChrome(url, path);
                } catch (IOException e) {
                    logger.error("Failed to convert webpage to pdf", e);
                }
            });
        } else {
            CompletableFuture.runAsync(() -> {
                try {
                    grab2PdfWithChrome(url);
                } catch (IOException | HtmlContentIncorrectException e) {
                    logger.error("Failed to convert webpage to pdf", e);
                }
            });
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Grab website as pdf using google-chrome
     *
     * @param url
     * @param path
     */
    private void grab2PdfWithChrome(String url, String path) throws IOException {
        chromeUtil.grab2Pdf(appendProtocolIfNotExists(url), path);
    }

    /**
     * Grab website as pdf using google-chrome
     *
     * @param url
     */
    private void grab2PdfWithChrome(String url) throws IOException, HtmlContentIncorrectException {
        chromeUtil.grab2Pdf(appendProtocolIfNotExists(url));
    }

    private static String appendProtocolIfNotExists(String url) {
        if (!HTTP_PROTOCOL_PATTERN.matcher(url).matches())
            return "http://" + url;
        else
            return url;
    }
}
