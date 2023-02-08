package com.myreflectionthoughts.moviereviewservice.utils;

import org.springframework.stereotype.Component;

import com.myreflectionthoughts.moviereviewservice.dto.request.AddReview;
import com.myreflectionthoughts.library.dto.response.ReviewResponse;
import com.myreflectionthoughts.moviereviewservice.models.Review;

@Component
public class MovieReviewMapper{
    
     public Review toEntity(AddReview reqDTO){
         var  movieReview = new Review();   
         movieReview.setRating(reqDTO.getRating());
         movieReview.setReview(reqDTO.getReview());
         movieReview.setMovieInfoId(reqDTO.getMovieInfoId());
         return movieReview;
     }

     public ReviewResponse toReviewResponse(Review review){
        var reviewResponse = new ReviewResponse();
        reviewResponse.setMovieInfoId(review.getMovieInfoId());
        reviewResponse.setReviewId(review.getReviewId());
        reviewResponse.setRating(review.getRating());
        reviewResponse.setReview(review.getReview());
        return reviewResponse;
     }
    
}
