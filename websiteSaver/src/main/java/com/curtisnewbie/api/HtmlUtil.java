package com.curtisnewbie.api;

import com.curtisnewbie.impl.HtmlContentIncorrectException;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
     * @return html model
     */
    Document grapDoc(String url) throws IOException, HtmlContentIncorrectException;

    /**
     * Extract CSS links
     *
     * @param document html document
     * @return
     */
    List<String> extractCssLinks(Document document);

    /**
     * Extract Base url from {@code base} html tag.
     *
     * @param document html document
     * @return base url or NULL if not found
     */
    Optional<String> extractBaseUrl(Document document);

    /**
     * Extract title
     *
     * @param document html document
     * @return
     */
    List<String> extractTitle(Document document);
}
