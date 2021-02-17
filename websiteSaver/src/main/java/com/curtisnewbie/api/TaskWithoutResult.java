package com.curtisnewbie.api;

import com.curtisnewbie.exception.DecryptionFailureException;
import com.curtisnewbie.impl.HtmlContentIncorrectException;

import java.io.IOException;

/**
 * Task without the need to return result
 *
 * @author zhuangyongj
 */
@FunctionalInterface
public interface TaskWithoutResult {

    /**
     * The job that this task will accomplish
     *
     * @return
     */
    void completeJob() throws DecryptionFailureException, IOException, HtmlContentIncorrectException;
}
