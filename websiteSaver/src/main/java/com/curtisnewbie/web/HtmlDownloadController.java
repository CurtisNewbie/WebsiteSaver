package com.curtisnewbie.web;

import com.curtisnewbie.api.HtmlUtil;
import com.curtisnewbie.api.PdfUtil;
import com.curtisnewbie.api.Task;
import com.curtisnewbie.api.TaskHandler;
import com.curtisnewbie.impl.HtmlContentIncorrectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

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

    @GetMapping
    public ResponseEntity fetchAndConvert2Pdf(@RequestHeader("url") String url,
                                              @RequestHeader("target") String target) {
        taskHandler.handle(() -> {
            doAsyncFetchAndConvert2Pdf(url, target);
            return null;
        });
        return ResponseEntity.ok().build();
    }

    private void doAsyncFetchAndConvert2Pdf(String url, String target) {
        logger.info(">>> Request fetching and converting webpage {} to pdf {}", url, target);
        try {
            String ctn = htmlUtil.grabHtmlContent(url);
            String baseUrl = htmlUtil.extractBaseUrl(ctn); // check if a base url is declared
            if (baseUrl == null)
                baseUrl = url;
            if (!pdfUtil.toPdfFile(ctn, baseUrl, target)) {
                logger.error(">>> Failed to convert to pdf");
            }
        } catch (IOException | HtmlContentIncorrectException e) {
            logger.error("Failed to fetch html content. Error Msg: {}", e);
        }
        logger.info(">>> Finish fetching and converting webpage {} to pdf {}", url, target);
    }
}
