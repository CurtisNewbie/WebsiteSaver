package com.curtisnewbie.api;

import com.curtisnewbie.impl.HtmlContentIncorrectException;

import java.io.IOException;

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
}
