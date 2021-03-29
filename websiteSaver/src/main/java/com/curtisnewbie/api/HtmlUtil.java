package com.curtisnewbie.api;

import com.curtisnewbie.exception.DecryptionFailureException;
import com.curtisnewbie.exception.HtmlContentIncorrectException;
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
     * Grab the html content of a link in the forms of {@link Document}
     *
     * @param url website url
     * @return html model
     */
    Document fetchDocument(String url) throws IOException, HtmlContentIncorrectException;

    /**
     * Extract title
     *
     * @param document html document
     * @return
     */
    List<String> extractTitle(Document document);
}
