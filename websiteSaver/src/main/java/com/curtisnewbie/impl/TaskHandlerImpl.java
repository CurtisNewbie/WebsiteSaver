package com.curtisnewbie.impl;

import com.curtisnewbie.api.TaskWithResult;
import com.curtisnewbie.api.TaskHandler;
import com.curtisnewbie.api.TaskWithoutResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@code TaskHandler}
 *
 * @author zhuangyongj
 */
@Component
public class TaskHandlerImpl implements TaskHandler {

    private static final Logger logger = LoggerFactory.getLogger(TaskHandlerImpl.class);

    @Async
    @Override
    public void asyncHandle(TaskWithoutResult t) {
        try {
            logger.info(">>> [TASK] Handling Job in Thread: {}", Thread.currentThread().getId());
            t.completeJob();
            logger.info(">>> [TASK] Finish handling job in Thread: {}", Thread.currentThread().getId());
        } catch (Exception e) {
            logger.error(">>> [TASK] Error occurred while handling job - ERROR: {}", e.getMessage() != null ?
                    e.getMessage() : e.toString());
        }
    }

    @Async
    @Override
    public void asyncHandle(TaskWithoutResult t, String taskName) {
        try {
            logger.info(">>> [TASK] Handling Job '{}' in Thread: {}", taskName, Thread.currentThread().getId());
            t.completeJob();
            logger.info(">>> [TASK] Finish handling job '{}' in Thread: {}", taskName, Thread.currentThread().getId());
        } catch (Exception e) {
            logger.error(">>> [TASK] Error occurred while handling job '{}' - ERROR: {}", taskName,
                    e.getMessage() != null ? e.getMessage() : e.toString());
        }
    }
}
