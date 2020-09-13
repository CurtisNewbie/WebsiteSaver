package com.curtisnewbie.impl;

import com.curtisnewbie.api.HtmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author zhuangyongj
 */
@Component
public class HtmlUtilImpl implements HtmlUtil {

    private static final String HTML_TEXT_TYPE_PAT = "^\\s*text\\s*/\\s*html\\s*$";
    private static final String ENCODING = "UTF-8";
    private static final Logger logger = LoggerFactory.getLogger(HtmlUtilImpl.class);

    @Value("${conn.timeout}")
    private int TIMEOUT;

    @Override
    public String grabHtmlContent(String url) throws IOException, HtmlContentIncorrectException {
        HttpURLConnection urlConn = null;
        try {
            urlConn = (HttpURLConnection) new URL(url).openConnection();
            urlConn.setConnectTimeout(TIMEOUT);
            urlConn.connect();
            if (!urlConn.getContentType().matches(HTML_TEXT_TYPE_PAT)) {
                logger.error("Error. Incorrect HTTP URL Content: {}", urlConn.getContentType());
                throw new HtmlContentIncorrectException();
            }
            try (BufferedInputStream bufferedIn = new BufferedInputStream(urlConn.getInputStream())) {
                byte[] bytes = bufferedIn.readAllBytes();
                return new String(bytes, ENCODING);
            }
        } finally {
            if (urlConn != null)
                urlConn.disconnect();
        }
    }
}
