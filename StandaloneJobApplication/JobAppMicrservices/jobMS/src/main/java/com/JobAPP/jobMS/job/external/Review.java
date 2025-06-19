package com.JobAPP.jobMS.job.external;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private Long id;
    private String title;
    private String description;
    private String rating;
    private Long companyId;
}
