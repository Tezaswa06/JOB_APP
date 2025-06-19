package com.JobAPP.jobMS.job;

import com.JobAPP.jobMS.job.dto.JobDTO;

import java.util.List;

public interface JobService {

    List<JobDTO> findAllJobs();
    void createJob(Job job);

    JobDTO findJobById(Long id);

    boolean deleteJobById(Long id);

    boolean updateJobById(Long id, Job updatedJob);

}
