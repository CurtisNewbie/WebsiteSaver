package com.curtisnewbie.api;

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
    void completeJob();
}
