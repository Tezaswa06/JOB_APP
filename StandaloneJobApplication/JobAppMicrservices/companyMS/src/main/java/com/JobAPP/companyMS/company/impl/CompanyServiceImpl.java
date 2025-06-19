package com.JobAPP.companyMS.company.impl;


import com.JobAPP.companyMS.company.Company;
import com.JobAPP.companyMS.company.CompanyRepository;
import com.JobAPP.companyMS.company.CompanyService;
import com.JobAPP.companyMS.company.clients.ReviewClient;
import com.JobAPP.companyMS.company.dto.ReviewMessage;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    private CompanyRepository companyRepository;
    private ReviewClient reviewClient;

    public CompanyServiceImpl(CompanyRepository companyRepository, ReviewClient reviewClient) {
        this.companyRepository = companyRepository;
        this.reviewClient = reviewClient;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public boolean updateCompany(Company company, Long companyId) {
        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (optionalCompany.isPresent()) {
            Company companyToUpdate = optionalCompany.get();
            companyToUpdate.setName(company.getName());
            companyToUpdate.setDescription(company.getDescription());
            companyRepository.save(companyToUpdate);
            return true;
        }
        return false;
    }

    @Override
    public void createCompanies(Company company) {
        companyRepository.save(company);
    }

    @Override
    public boolean deleteCompany(Long companyId) {
        if (companyRepository.existsById(companyId)) {
            companyRepository.deleteById(companyId);
            return true;
        }
        return false;
    }

    @Override
    public Company getCompanyById(Long companyId) {
        return companyRepository.findById(companyId).orElse(null);
    }

    @Override
    public void updateComapnyRating(ReviewMessage reviewMessage) {
        System.out.println(reviewMessage.getDescription());
        Company company = companyRepository.findById(reviewMessage.getCompanyId()).orElseThrow(
                () -> new NotFoundException("Company not found with id: " + reviewMessage.getCompanyId())
        );

        double averageRating = reviewClient.getAverageRating(reviewMessage.getCompanyId());
        company.setRating(averageRating);
        companyRepository.save(company);

    }

}
