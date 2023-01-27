package com.myreflectionthoughts.moviereviewservice.bdd;

import com.myreflectionthoughts.moviereviewservice.dto.request.AddReview;
import com.myreflectionthoughts.moviereviewservice.dto.request.UpdateReview;

public class ConstructUtils {
    
    public static AddReview constructAddReviewPayload(){

        var addReview = new AddReview();
        addReview.setMovieInfoId("movieId@MongoDB");
        addReview.setRating(3.5);
        addReview.setReview("The movie is not upto the mark, it's based on false incidents.");
        return addReview;
    }

    public static UpdateReview constructUpdateReviewPayload(String reviewId, String movieInfoId, String updatedReview, Double updatedRating){

        var updateReview = new UpdateReview();
        updateReview.setReviewId(reviewId);
        updateReview.setMovieInfoId(movieInfoId);
        updateReview.setRating(updatedRating);
        updateReview.setReview(updatedReview);
        return updateReview;
    }
}
