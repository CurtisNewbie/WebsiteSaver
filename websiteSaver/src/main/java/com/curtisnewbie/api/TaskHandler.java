package com.curtisnewbie.api;

/**
 * <p>
 * A handler for {@link TaskWithResult}
 * </p>
 *
 * @author zhuangyongj
 */
public interface TaskHandler {

    /**
     * Handle {@code Task} without the ability to get the completed result
     */
    void asyncHandle(TaskWithoutResult t);

    /**
     * Handle {@code Task} without the ability to get the completed result
     */
    void asyncHandle(TaskWithoutResult t, String taskName);
}
