package com.curtisnewbie.api;

import com.curtisnewbie.exception.DecryptionFailureException;

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
    void completeJob() throws DecryptionFailureException;
}
