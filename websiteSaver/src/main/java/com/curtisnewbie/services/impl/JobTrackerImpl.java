package com.curtisnewbie.services.impl;

import com.curtisnewbie.dto.JobInfo;
import com.curtisnewbie.services.api.JobTracker;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author yongjie.zhuang
 */
@Component
public class JobTrackerImpl implements JobTracker {

    private final ConcurrentMap<Integer, JobInfo> jobMap = new ConcurrentHashMap<>();
    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public List<JobInfo> getAllJobs() {
        return new ArrayList<>(jobMap.values());
    }

    @Override
    public int addJobInfo(JobInfo jobInfo) {
        Objects.requireNonNull(jobInfo);
        int randomId = secureRandom.nextInt();
        jobMap.put(randomId, jobInfo);
        return randomId;
    }

    @Override
    public void removeJobInfo(int jobId) {
        jobMap.remove(jobId);
    }
}