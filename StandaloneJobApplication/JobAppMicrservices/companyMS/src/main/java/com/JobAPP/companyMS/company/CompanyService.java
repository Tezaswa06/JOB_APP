package com.JobAPP.companyMS.company;

import com.JobAPP.companyMS.company.dto.ReviewMessage;

import java.util.List;


public interface CompanyService {

    List<Company> getAllCompanies();
    boolean updateCompany(Company company, Long companyId);
    void createCompanies(Company company);
    boolean deleteCompany(Long companyId);
    Company getCompanyById(Long companyId);
    public void updateComapnyRating(ReviewMessage reviewMessage);
}
