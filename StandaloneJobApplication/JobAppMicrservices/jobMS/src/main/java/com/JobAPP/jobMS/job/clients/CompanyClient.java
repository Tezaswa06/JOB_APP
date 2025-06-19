package com.JobAPP.jobMS.job.clients;

import com.JobAPP.jobMS.job.external.Company;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "company-ms",url = "${company-ms.url}")
public interface CompanyClient {
    // Define methods to interact with the Company service
    // For example:
    // @GetMapping("/companies/{id}")
    // Company getCompanyById(@PathVariable("id") Long id);
    // You can add more methods as needed to interact with the Company service
    @GetMapping("/api/companies/getCompanyById/{id}")
    Company getCompanyById(@PathVariable("id") Long id);
}
