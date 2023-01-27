package com.myreflectionthoughts.moviereviewservice.bdd;

import java.util.List;

import com.myreflectionthoughts.moviereviewservice.models.Review;

public class TestData {
    
    public static List<Review> intialReviews(){

       var movieReview1 = new Review();
       movieReview1.setMovieInfoId("movieInfoId@1");
       movieReview1.setRating(3.2);
       movieReview1.setReview("This is review 1");

       var movieReview2 = new Review();
       movieReview2.setMovieInfoId("movieInfoId@2");
       movieReview2.setRating(3.3);
       movieReview2.setReview("This is review 2");

       var movieReview3 = new Review();
       movieReview3.setMovieInfoId("movieInfoId@3");
       movieReview3.setRating(3.4);
       movieReview3.setReview("This is review 3");

       var movieReview4 = new Review();
       movieReview4.setMovieInfoId("movieInfoId@4");
       movieReview4.setRating(3.5);
       movieReview4.setReview("This is review 4");

       return List.of(movieReview1, movieReview2,movieReview3, movieReview4);
    }
    
}
