package com.myreflectionthoughts.moviereviewservice;

import com.myreflectionthoughts.library.dto.response.ReviewResponse;
import com.myreflectionthoughts.moviereviewservice.dto.request.AddReview;
import com.myreflectionthoughts.moviereviewservice.dto.request.UpdateReview;
import com.myreflectionthoughts.moviereviewservice.dto.response.DeleteReviewResponse;
import com.myreflectionthoughts.moviereviewservice.models.Review;

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

    public static ReviewResponse constructReviewResponse(String reviewId, String movieInfoId, String review, Double rating){
        
        var reviewResponse = new ReviewResponse();

        reviewResponse.setMovieInfoId(movieInfoId);
        reviewResponse.setRating(rating);
        reviewResponse.setReview(review);
        reviewResponse.setReviewId(reviewId);
        return reviewResponse;
    }

    public static Review constructReview(String reviewId, String movieInfoId, String reviewComment, Double rating){
        var review = new Review();
        review.setMovieInfoId(movieInfoId);
        review.setReviewId(reviewId);
        review.setRating(rating);
        review.setReview(reviewComment);

        return review;
    }

    public static DeleteReviewResponse constructDeleteReviewResponse(String reviewId, String message){
        var deletionResponse = new DeleteReviewResponse();
        deletionResponse.setReviewId(reviewId);
        deletionResponse.setMessage(message);

        return deletionResponse;
    }
}
