package com.curtisnewbie.impl;

import com.curtisnewbie.api.ChromeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author zhuangyongj
 */
@Component
public class ChromeUtilImpl implements ChromeUtil {

    private static final Logger logger = LoggerFactory.getLogger(ChromeUtilImpl.class);

    @Value("${rootDir}")
    private String rootDir;

    /**
     * Grab webpage and convert it to PDF file with the support of chrome (headless)
     *
     * @param url  of web page
     * @param path filename / path
     */
    @Override
    public void grab2Pdf(String url, String path) {
        // remove all spaces and append .pdf if necessary
        path = appendPdfFileExt(removeSpaces(path));
        logger.info(">>> [BEGIN] Request fetching and converting webpage '{}' to pdf '{}' using chrome " +
                "(headless) ", url, path);
        try {
            // spaces are not allowed, and quotes are not working (for unknown reasons)
            String cmd = String.format("google-chrome --headless --print-to-pdf=%s%s%s %s", rootDir, File.separator,
                    path, url);
            logger.info(">>> Created command \"{}\" for chrome (headless)", cmd);
            Runtime runtime = Runtime.getRuntime();
            Process p = runtime.exec(cmd);
            // getInputStream() has no input, use getErrorStream() instead
            try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()))) {
                String line;
                while (p.isAlive() && (line = br.readLine()) != null) {
                    logger.info(">>> [PROCESS] {}", line);
                }
            }
            logger.info(">>> [END] Finish fetching and converting webpage {} to pdf {}", url, path);
        } catch (IOException e) {
            logger.error(">>> [ERROR] Failed to fetch html content. Error Msg: {}", e);
        }
    }

    private String removeSpaces(String str) {
        return str.replaceAll("\\s", "");
    }

    private String appendPdfFileExt(String path) {
        String mPath = path;
        if (!mPath.matches("^.*\\.[Pp][Dd][Ff]$")) {
            if (mPath.endsWith("."))
                mPath += "pdf";
            else
                mPath += ".pdf";
        }
        return mPath;
    }
}
