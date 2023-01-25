package com.myreflectionthoughts.moviereviewservice.dto.request;

import lombok.Data;

@Data
public class AddReview {
    
    private String movieInfoId;
    private String review;
    private Double rating;
}
