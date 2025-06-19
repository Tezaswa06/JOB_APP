package com.JobAPP.companyMS.company.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "reviewMs",url = "${reviewMs.url}")
public interface ReviewClient {

    @GetMapping("/api/reviews/averageRating")
    Double getAverageRating(@RequestParam("companyId") Long companyId);
}
