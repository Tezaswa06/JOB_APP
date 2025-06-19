package com.JobAPP.jobMS.job.impl;


import com.JobAPP.jobMS.job.Job;
import com.JobAPP.jobMS.job.JobRepository;
import com.JobAPP.jobMS.job.JobService;
import com.JobAPP.jobMS.job.clients.CompanyClient;
import com.JobAPP.jobMS.job.clients.ReviewClient;
import com.JobAPP.jobMS.job.dto.JobDTO;
import com.JobAPP.jobMS.job.external.Company;
import com.JobAPP.jobMS.job.external.Review;
import com.JobAPP.jobMS.job.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    JobRepository jobRepository;

    @Autowired
    RestTemplate restTemplate;

    private CompanyClient companyClient;
    private ReviewClient reviewClient;

    int attempt = 0;

    public JobServiceImpl(JobRepository jobRepository,CompanyClient companyClient,ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }


    @Override
//    @CircuitBreaker(name = "companyBreaker",fallbackMethod = "companyBreakerFallback")
    //@Retry(name = "companyBreaker",fallbackMethod = "companyBreakerFallback")
    @RateLimiter(name = "companyBreaker")
    public List<JobDTO> findAllJobs(){
        System.out.println("Attempt number: " + ++attempt);
        List<Job> jobs = jobRepository.findAll();
        List<JobDTO> jobDTOS = new ArrayList<>();
        return jobs.stream().map(this :: convertToDto)
                .collect(Collectors.toList());
    }

    public  List<String> companyBreakerFallback(Exception e){
        List<String> list = new ArrayList<>();
        list.add("Company service is currently unavailable. Please try again later.");
        return list;
    }

    private JobDTO convertToDto(Job job) {
//        Company company = restTemplate.getForObject(
//                "http://company-ms:8082/api/companies/getCompanyById/1",
//                Company.class
//        );
        Company company = companyClient.getCompanyById(job.getCompanyId());
//        ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange("http://reviewMS:8083/api/reviews/getAllReviewsForACompany?companyId=" + job.getCompanyId(),
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Review>>() {
//                });

//        List<Review> reviews = reviewResponse.getBody();
        List<Review> reviews = reviewClient.getAllReviewsForACompany(job.getCompanyId());

        JobDTO jobDTO = JobMapper.mapJobToJobWithCompanyDTO(job, company,reviews);
//        jobDTO.setCompany(company);
        return jobDTO;
    }

    @Override
    public void createJob(Job job) {
       jobRepository.save(job);
    }

    @Override
    public JobDTO findJobById(Long id ) {
        Job job = jobRepository.findById(id).orElse(null);
        return convertToDto(job);
    }

    @Override
    public boolean deleteJobById(Long id) {
        try {
            jobRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateJobById(Long id, Job updatedJob) {
        Optional<Job> jobOptional = jobRepository.findById(id);
    if (jobOptional.isPresent()) {
        Job job = jobOptional.get();
        job.setTitle(updatedJob.getTitle());
        job.setDescription(updatedJob.getDescription());
        job.setMinSalary(updatedJob.getMinSalary());
        job.setMaxSalary(updatedJob.getMaxSalary());
        job.setLocation(updatedJob.getLocation());
        jobRepository.save(job);
        return true;
    }
        return false;
    }
}
