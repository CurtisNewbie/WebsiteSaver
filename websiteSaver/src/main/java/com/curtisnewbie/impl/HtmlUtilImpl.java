package com.curtisnewbie.impl;

import com.curtisnewbie.api.HtmlUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuangyongj
 */
@Component
public class HtmlUtilImpl implements HtmlUtil {

    private static final String HTML_TEXT_TYPE_PAT = "^.*text\\s*/\\s*html.*$";
    private static final String ENCODING = "UTF-8";
    private static final Logger logger = LoggerFactory.getLogger(HtmlUtilImpl.class);
    private static final String LINK_TAG = "link";
    private static final String BASE_TAG = "base";
    private static final String HREF_ATTR = "href";
    private static final String REL_ATTR = "rel";
    private static final String REL_NAME = "stylesheet";

    @Value("${conn.timeout}")
    private int TIMEOUT;

    @Override
    public String grabHtmlContent(String url) throws IOException, HtmlContentIncorrectException {
        logger.info(">>> Trying to grab html content of {}", url);
        HttpURLConnection urlConn = null;
        try {
            urlConn = (HttpURLConnection) new URL(url).openConnection();
            urlConn.setConnectTimeout(TIMEOUT);
            urlConn.connect();
            if (!urlConn.getContentType().matches(HTML_TEXT_TYPE_PAT)) {
                logger.error(">>> Error. Incorrect HTTP URL Content: {}", urlConn.getContentType());
                throw new HtmlContentIncorrectException(urlConn.getContentType());
            }
            try (BufferedInputStream bufferedIn = new BufferedInputStream(urlConn.getInputStream())) {
                byte[] bytes = bufferedIn.readAllBytes();
                logger.info(">>> Finish grabbing html content of {}", url);
                return new String(bytes, ENCODING);
            }
        } finally {
            if (urlConn != null)
                urlConn.disconnect();
        }
    }

    @Override
    public List<String> extractCssLinks(String htmlContent) {
        Document doc = Jsoup.parse(htmlContent);
        Iterable<Element> linkTags = doc.getElementsByTag(LINK_TAG);
        List<String> cssLinks = new ArrayList<>();
        linkTags.forEach(e -> {
            if (e.hasAttr(REL_ATTR) && e.attr(REL_ATTR).equalsIgnoreCase(REL_NAME) && e.hasAttr(HREF_ATTR))
                cssLinks.add(e.attr(HREF_ATTR));
        });
        logger.info(">>> Extracted css links: {}", cssLinks.toString());
        return cssLinks;
    }

    @Override
    public String extractBaseUrl(String htmlContent) {
        logger.info(">>> Trying to extract base url from html content");
        Document doc = Jsoup.parse(htmlContent);
        List<Element> baseTags = doc.getElementsByTag(BASE_TAG);
        if (baseTags.isEmpty())
            return null;
        for (Element b : baseTags) {
            if (b.hasAttr(HREF_ATTR)){
                String base = b.attr(HREF_ATTR);
                logger.info(">>> Extracted base url: {}", base);
                return base;
            }
        }
        return null;
    }
}
