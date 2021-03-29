package com.curtisnewbie.impl;

import com.curtisnewbie.api.HtmlUtil;
import com.curtisnewbie.exception.HtmlContentIncorrectException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author zhuangyongj
 */
@Component
public class HtmlUtilImpl implements HtmlUtil {

    private static final Pattern HTML_TEXT_TYPE_PAT = Pattern.compile("^.*text\\s*/\\s*html.*$");
    private static final Logger logger = LoggerFactory.getLogger(HtmlUtilImpl.class);
    private static final String TITLE_TAG = "title";

    @Value("${html.connect.timeout}")
    private int CONNECT_TIMEOUT;
    @Value("${html.read.timeout}")
    private int READ_TIMEOUT;

    @PostConstruct
    void init() {
        logger.info("Setting connect timeout to: '{}' milliseconds", CONNECT_TIMEOUT);
        logger.info("Setting read timeout to: '{}' milliseconds", READ_TIMEOUT);
    }

    @Override
    public Document fetchDocument(String url) throws IOException, HtmlContentIncorrectException {
        logger.info("Fetch html content of '{}'", url);
        HttpURLConnection urlConn = null;
        try {
            urlConn = (HttpURLConnection) new URL(url).openConnection();
            urlConn.setConnectTimeout(CONNECT_TIMEOUT);
            urlConn.setReadTimeout(READ_TIMEOUT);
            urlConn.connect();
            if (!HTML_TEXT_TYPE_PAT.matcher(urlConn.getContentType()).matches()) {
                throw new HtmlContentIncorrectException("Incorrect HTTP URL Content: " + urlConn.getContentType());
            }
            try (BufferedInputStream bufferedIn = new BufferedInputStream(urlConn.getInputStream())) {
                byte[] bytes = bufferedIn.readAllBytes();
                return Jsoup.parse(new String(bytes, StandardCharsets.UTF_8), url);
            }
        } finally {
            if (urlConn != null)
                urlConn.disconnect();
        }
    }

    @Override
    public List<String> extractTitle(Document doc) {
        logger.info("Extract title of '{}'", doc.location());
        List<Element> titleTags = doc.getElementsByTag(TITLE_TAG);
        List<String> titles = new ArrayList<>();
        titleTags.stream().filter(t -> StringUtils.hasLength(t.text())).forEach(t -> titles.add(t.text()));
        return titles;
    }
}
