package com.myreflectionthoughts.moviereviewservice.dto.request;

import lombok.Data;

@Data
public class UpdateReview {
    
    private String reviewId;
    private String movieInfoId;
    private String review;
    private Double rating;
}
