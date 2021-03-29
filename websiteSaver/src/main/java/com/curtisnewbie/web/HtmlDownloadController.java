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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @author zhuangyongj
 */
@RestController
@RequestMapping("/download")
public class HtmlDownloadController {
    /** Pattern for http:// or https:// */
    private static Pattern HTTP_PROTOCOL_PATTERN = Pattern.compile("[Hh][Tt][Tt][Pp][Ss]?://");

    private static final Logger logger = LoggerFactory.getLogger(HtmlDownloadController.class);

    @Autowired
    private HtmlUtil htmlUtil;

    @Autowired
    private TaskHandler taskHandler;

    @Autowired
    private ChromeUtil chromeUtil;

    @Autowired
    private RsaDecryptionService rsaDecryptionService;

    @Value("${rootDir}")
    private String rootDir;

    @PostMapping("/with/chrome")
    public ResponseEntity convertWithChrome(@RequestBody QueryEntity q) {
        taskHandler.asyncHandle(() -> {
            if (StringUtils.hasLength(q.getPath()))
                grab2PdfWithChrome(rsaDecryptionService.decrypt(q.getUrl()), rsaDecryptionService.decrypt(q.getPath()));
            else
                grab2PdfWithChrome(rsaDecryptionService.decrypt(q.getUrl()));
        }, "grab2PdfWithChrome");
        return ResponseEntity.ok().build();
    }

    /**
     * Grab website as pdf using google-chrome
     *
     * @param url
     * @param path
     */
    private void grab2PdfWithChrome(String url, String path) {
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
