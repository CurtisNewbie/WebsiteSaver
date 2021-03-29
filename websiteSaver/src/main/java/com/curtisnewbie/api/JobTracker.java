package com.curtisnewbie.api;

import com.curtisnewbie.dto.JobInfo;

import java.util.List;

/**
 * Tracker of job
 *
 * @author yongjie.zhuang
 */
public interface JobTracker {

    /** get all jobs that are currently being undertaken */
    List<JobInfo> getAllJobs();

    /** add job info, and return the unique id for this job */
    int addJobInfo(JobInfo jobInfo);

    /** remove job info using unique id of this job */
    void removeJobInfo(int jobId);
}
