package com.JobAPP.jobMS.job.clients;

import com.JobAPP.jobMS.job.external.Company;
import com.JobAPP.jobMS.job.external.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "reviewMs",url = "${reviewMs.url}")
public interface ReviewClient {

    @GetMapping("api/reviews/getAllReviewsForACompany")
    List<Review> getAllReviewsForACompany(@RequestParam("companyId") Long companyId);
}
