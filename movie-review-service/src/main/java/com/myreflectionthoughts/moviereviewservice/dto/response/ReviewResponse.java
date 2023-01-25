package com.myreflectionthoughts.moviereviewservice.dto.response;

import lombok.Data;

@Data
public class ReviewResponse {
    private String reviewId;
    private String movieInfoId;
    private String review;
    private Double rating;
}
