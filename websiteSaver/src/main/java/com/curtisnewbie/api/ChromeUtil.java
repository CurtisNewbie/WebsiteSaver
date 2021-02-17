package com.curtisnewbie.api;

import com.curtisnewbie.impl.HtmlContentIncorrectException;

import java.io.IOException;

/**
 * <p>
 * Util class that invokes google-chrome via CLI to accomplish certain operations
 * </p>
 *
 * @author zhuangyongj
 */
public interface ChromeUtil {

    /**
     * Grab webpage and convert it to PDF file with the support of chrome (headless mode in CLI)
     *
     * @param url  of web page
     * @param path filename / path
     */
    void grab2Pdf(String url, String path);

    /**
     * Grab webpage and convert it to PDF file with the support of chrome (headless mode in CLI)
     *
     * @param url  of web page
     */
    void grab2Pdf(String url) throws IOException, HtmlContentIncorrectException;
}
