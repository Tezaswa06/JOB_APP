package com.app.jobApp.company;

import org.springframework.stereotype.Service;

import java.util.List;


public interface CompanyService {

    List<Company> getAllCompanies();
    boolean updateCompany(Company company, Long companyId);
    void createCompanies(Company company);
    boolean deleteCompany(Long companyId);
    Company getCompanyById(Long companyId);
}
