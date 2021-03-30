package com.curtisnewbie.web;

import com.curtisnewbie.services.api.*;
import com.curtisnewbie.dto.JobInfo;
import com.curtisnewbie.dto.QueryDto;
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
import java.util.List;
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
    @Autowired
    private JobTracker jobTracker;

    @Value("${rootDir}")
    private String rootDir;

    @PostMapping("/with/chrome")
    public ResponseEntity convertWithChrome(@RequestBody QueryDto q) throws DecryptionFailureException {
        if (!StringUtils.hasLength(q.getUrl()))
            return ResponseEntity.badRequest().build();

        String url = rsaDecryptionService.decrypt(q.getUrl());
        if (StringUtils.hasLength(q.getPath())) {
            String path = rsaDecryptionService.decrypt(q.getPath());
            CompletableFuture.supplyAsync(() -> {
                int jobId = jobTracker.addJobInfo(new JobInfo(url));
                try {
                    grab2PdfWithChrome(url, path);
                } catch (IOException e) {
                    logger.error("Failed to convert webpage to pdf", e);
                }
                return jobId;
            }).whenComplete((jobId, e) -> {
                jobTracker.removeJobInfo(jobId);
            });
        } else {
            CompletableFuture.supplyAsync(() -> {
                int jobId = jobTracker.addJobInfo(new JobInfo(url));
                try {
                    grab2PdfWithChrome(url);
                } catch (IOException | HtmlContentIncorrectException e) {
                    logger.error("Failed to convert webpage to pdf", e);
                }
                return jobId;
            }).whenComplete((jobId, e) -> {
                jobTracker.removeJobInfo(jobId);
            });
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/job/list")
    public ResponseEntity<List<JobInfo>> getJobInfoList() {
        return ResponseEntity.ok(jobTracker.getAllJobs());
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
