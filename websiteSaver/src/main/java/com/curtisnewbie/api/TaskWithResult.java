package com.curtisnewbie.api;

import java.util.Optional;

/**
 * A Task that returns result of {@code T} generic type
 *
 * @param <T>
 * @author zhuangyongj
 */
@FunctionalInterface
public interface TaskWithResult<T> {

    /**
     * The job that this task will accomplish
     * @return
     */
    T completeJob();

}
