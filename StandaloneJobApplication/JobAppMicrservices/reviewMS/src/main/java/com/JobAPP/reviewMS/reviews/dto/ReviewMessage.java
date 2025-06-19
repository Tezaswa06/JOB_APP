package com.JobAPP.reviewMS.reviews.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewMessage {
    private Long id;
    private Long companyId;
    private String description;
    private double rating;
    private String title;
}
