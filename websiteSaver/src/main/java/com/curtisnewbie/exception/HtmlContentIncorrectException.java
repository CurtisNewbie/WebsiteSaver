package com.curtisnewbie.exception;


/**
 * Content of the http url isn't text/html
 *
 * @author zhuangyongj
 */
public class HtmlContentIncorrectException extends Exception {

    public HtmlContentIncorrectException() {
    }

    public HtmlContentIncorrectException(String msg) {
        super(msg);
    }
}

