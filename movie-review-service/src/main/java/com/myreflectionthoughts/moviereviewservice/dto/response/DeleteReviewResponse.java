package com.myreflectionthoughts.moviereviewservice.dto.response;

import lombok.Data;

@Data
public class DeleteReviewResponse {
    private String reviewId;
    private String message;
}
