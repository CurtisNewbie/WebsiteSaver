package com.curtisnewbie.web;

import com.curtisnewbie.api.*;
import com.curtisnewbie.dto.QueryEntity;
import com.curtisnewbie.exception.DecryptionFailureException;
import com.curtisnewbie.impl.HtmlContentIncorrectException;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

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

    @Value("${isLocalOnly}")
    private boolean isLocalOnly;

    @PostMapping("/with/itext")
    public ResponseEntity fetchAndConvert2Pdf(@RequestBody QueryEntity q, HttpServletRequest request) {
        if (!canRequest(request))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        try {
            String authKey = rsaDecryptionService.decrypt(q.getAuthKey());
            if (authService.isAuthenticated(authKey)) {
                taskHandler.asyncHandle(() -> {
                    fetchAndConvert2pdf(rsaDecryptionService.decrypt(q.getUrl()), rsaDecryptionService.decrypt(q.getPath()));
                }, "fetchAndConvert2Pdf");
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (DecryptionFailureException e) {
            logger.error("Decryption failure", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/with/chrome")
    public ResponseEntity convertWithChrome(@RequestBody QueryEntity q, HttpServletRequest request) {
        if (!canRequest(request))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        try {
            String authKey = rsaDecryptionService.decrypt(q.getAuthKey());
            if (authService.isAuthenticated(authKey)) {
                taskHandler.asyncHandle(() -> {
                    if (StringUtils.hasLength(q.getPath()))
                        grab2PdfWithChrome(rsaDecryptionService.decrypt(q.getUrl()), rsaDecryptionService.decrypt(q.getPath()));
                    else
                        grab2PdfWithChrome(rsaDecryptionService.decrypt(q.getUrl()));
                }, "grab2PdfWithChrome");
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (DecryptionFailureException e) {
            logger.error("Decryption failure", e);
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
        if (!url.matches("[Hh][Tt][Tt][Pp][Ss]?://"))
            url = "http://" + url;
        chromeUtil.grab2Pdf(url, path);
    }

    /**
     * Grab website as pdf using google-chrome
     *
     * @param url
     */
    private void grab2PdfWithChrome(String url) throws IOException, HtmlContentIncorrectException {
        chromeUtil.grab2Pdf(url);
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
            Document doc = htmlUtil.grapDoc(url);
            Optional<String> baseUrlOpt = htmlUtil.extractBaseUrl(doc); // check if a base url is declared
            String baseUrl;
            if (baseUrlOpt.isEmpty())
                baseUrl = url;
            else
                baseUrl = baseUrlOpt.get();
            if (!pdfUtil.toPdfFile(doc.toString(), baseUrl, path)) {
                logger.error(">>> [ERROR] Failed to convert to pdf");
            }
        } catch (IOException | HtmlContentIncorrectException e) {
            logger.error(">>> [ERROR] Failed to fetch html content. Error Msg: {}", e);
        }
        logger.info(">>> [END] Finish fetching and converting webpage {} to pdf {}", url, path);
    }

    private boolean canRequest(HttpServletRequest request) {
        if (isLocalOnly) {
            logger.info("'isLocalOnly' flag turned on, verifying if the request is from localhost");
            return request.getRemoteAddr().equals(request.getLocalAddr());
        } else {
            return true;
        }
    }
}
