package com.curtisnewbie.web;

import com.curtisnewbie.exception.DecryptionFailureException;
import com.curtisnewbie.exception.HtmlContentIncorrectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author yongjie.zhuang
 */
@ControllerAdvice
public class ControllerAdviceConfig {

    private static final Logger logger = LoggerFactory.getLogger(ControllerAdviceConfig.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DecryptionFailureException.class, HtmlContentIncorrectException.class})
    public void badRequest(Exception e) {
        logger.warn("Bad request", e);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public void ioException(Exception e) {
        logger.warn("Internal error, unknown exception occurred", e);
    }

}
