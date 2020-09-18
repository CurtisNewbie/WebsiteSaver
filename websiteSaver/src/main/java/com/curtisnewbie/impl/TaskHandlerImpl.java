package com.curtisnewbie.impl;

import com.curtisnewbie.api.Task;
import com.curtisnewbie.api.TaskHandler;
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
    public void asyncHandle(Task<?> t) {
        try {
            logger.info(">>> Handling Job");
            t.completeJob();
            logger.info(">>> Finish handling job");
        } catch (Exception e) {
            logger.error(">>> Error occurred while handling job - ERROR: {}", e.getMessage() != null ?
                    e.getMessage() : e.toString());
        }
    }
}
