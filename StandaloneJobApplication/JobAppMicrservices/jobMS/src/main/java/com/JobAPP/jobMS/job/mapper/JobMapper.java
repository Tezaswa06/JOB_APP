package com.JobAPP.jobMS.job.mapper;

import com.JobAPP.jobMS.job.Job;
import com.JobAPP.jobMS.job.dto.JobDTO;
import com.JobAPP.jobMS.job.external.Company;
import com.JobAPP.jobMS.job.external.Review;

import java.util.List;

public class JobMapper {
    public static JobDTO mapJobToJobWithCompanyDTO(Job job, Company company, List<Review> reviews) {
        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setCompany(company);
        jobDTO.setReviews(reviews);
        return jobDTO;
    }
}
