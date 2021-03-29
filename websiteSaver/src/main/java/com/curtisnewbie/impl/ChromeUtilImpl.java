package com.curtisnewbie.impl;

import com.curtisnewbie.api.ChromeUtil;
import com.curtisnewbie.api.HtmlUtil;
import com.curtisnewbie.exception.HtmlContentIncorrectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author zhuangyongj
 */
@Component
public class ChromeUtilImpl implements ChromeUtil {

    private static final Logger logger = LoggerFactory.getLogger(ChromeUtilImpl.class);

    @Value("${rootDir}")
    private String rootDir;

    @Autowired
    private HtmlUtil htmlUtil;

    @PostConstruct
    void init() {
        logger.info("ChromeUtil initialised, rootDir: '{}'", rootDir);
    }

    /**
     * Grab webpage and convert it to PDF file with the support of chrome (headless)
     *
     * @param url  of web page
     * @param path filename / path
     */
    @Override
    public void grab2Pdf(final String url, String path) throws IOException {
        // spaces are not allowed, and quotes are not working (for unknown reasons)
        // remove all spaces and append .pdf if necessary
        path = appendPdfFileExt(removeInvalidChars(path));

        // starts building process command
        logger.info("Converting webpage '{}' to pdf '{}' using chrome-headless", url, path);
        final String baseCmd = "google-chrome --headless --print-to-pdf=";
        final String concatedPath = rootDir + File.separator + path;
        final String cmd = String.format("%s%s %s", baseCmd, concatedPath, url);
        logger.info("Created command \"{}\" for chrome (headless)", cmd);
        Runtime runtime = Runtime.getRuntime();
        Process p = runtime.exec(cmd);
        // getInputStream() has no input, use getErrorStream() instead
        try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()))) {
            String line;
            while (p.isAlive() && (line = br.readLine()) != null) {
                logger.info("[PROCESS] stdout: '{}'", line);
            }
        }
        logger.info("Finished convertion from webpage {} to pdf {}", url, path);
    }

    @Override
    public void grab2Pdf(final String url) throws IOException, HtmlContentIncorrectException {
        logger.info("Converting webpage '{}' to pdf using chrome-headless, extract title before fetching", url);
        List<String> titles = htmlUtil.extractTitle(htmlUtil.fetchDocument(url));
        if (titles.isEmpty())
            throw new IllegalArgumentException("There is no title for this website, '" + url + "'");
        grab2Pdf(url, titles.get(0));
    }

    /**
     * Remove all spaces and '.' chars
     *
     * @throws IllegalArgumentException if the path becomes empty after invalid chars removal
     */
    private static String removeInvalidChars(String path) {
        path = path.replaceAll("[\\s\\.]", "");
        if (path.startsWith(File.separator)) {
            if (path.length() > 1)
                return path.substring(1);
            else
                throw new IllegalArgumentException("File path illegal, path should not be empty after invalid chars removal, path: '" + path + "'");
        } else {
            return path;
        }
    }

    /** Append '.pdf' file extension if necessary */
    private static String appendPdfFileExt(String path) {
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
