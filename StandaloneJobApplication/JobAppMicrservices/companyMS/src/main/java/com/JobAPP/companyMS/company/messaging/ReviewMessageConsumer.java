package com.JobAPP.companyMS.company.messaging;

import com.JobAPP.companyMS.company.CompanyService;
import com.JobAPP.companyMS.company.dto.ReviewMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageConsumer {
    private final CompanyService companyService;

    public ReviewMessageConsumer(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RabbitListener(queues = "companyRatingQueue") // Corrected queue name
    public void consumeMessage(ReviewMessage reviewMessage) {
        companyService.updateComapnyRating(reviewMessage);
    }
}