package com.curtisnewbie.api;

import com.curtisnewbie.impl.HtmlContentIncorrectException;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

/**
 * Class that provides util methods related to pdf
 *
 * @author zhuangyongj
 */
public interface HtmlUtil {

    /**
     * Grab the html content of a link
     *
     * @param url website url
     * @return html content
     */
    String grabHtmlContent(String url) throws IOException, HtmlContentIncorrectException;

    /**
     * Extract CSS links
     *
     * @param htmlContent
     * @return
     */
    List<String> extractCssLinks(String htmlContent);

    /**
     * Extract Base url from {@code base} html tag.
     *
     * @param htmlContent html content
     * @return base url or NULL if not found
     */
    String extractBaseUrl(String htmlContent);
}
