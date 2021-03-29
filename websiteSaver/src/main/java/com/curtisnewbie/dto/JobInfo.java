package com.curtisnewbie.dto;

import java.time.LocalDateTime;

/**
 * Info of a job
 *
 * @author yongjie.zhuang
 */
public class JobInfo {

    private final String name;

    private final LocalDateTime startTime;

    public JobInfo(String name, LocalDateTime startTime) {
        this.name = name;
        this.startTime = startTime;
    }

    public JobInfo(String name) {
        this.name = name;
        this.startTime = LocalDateTime.now();
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public String getName() {
        return name;
    }
}
