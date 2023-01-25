package com.myreflectionthoughts.moviereviewservice.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "reviews")
public class Review {
    
    @Id
    private String reviewId;
    private String movieId;
    private String review;
    private Double rating;
}
