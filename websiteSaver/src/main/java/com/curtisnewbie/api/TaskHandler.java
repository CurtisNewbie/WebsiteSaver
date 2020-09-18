package com.curtisnewbie.api;

import java.util.Optional;
import java.util.concurrent.Future;

/**
 * <p>
 * A handler for {@link Task}
 * </p>
 *
 * @author zhuangyongj
 */
public interface TaskHandler {

    /**
     * Handle {@code Task}  of generic type {@code T} without the ability to get the completed result
     *
     * @param <T> type of result
     */
    <T> void handle(Task<T> t);
}
