package com.myreflectionthoughts.moviereviewservice.routers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.myreflectionthoughts.moviereviewservice.handlers.ReviewRequestHanlder;

@Configuration
public class ReviewRouter{

    @Autowired
    private ReviewRequestHanlder reviewRequestHanlder;

    @Bean
    public RouterFunction<ServerResponse> reviewsRoute(){
         return RouterFunctions.route()
                               .POST("/movie-review-service/review/",reviewRequestHanlder::handleAddReview)
                               .GET("/movie-review-service/review/{id}", reviewRequestHanlder::handleGetReview)
                               .GET("/movie-review-service/review/",reviewRequestHanlder::handleGetallReviews)
                               .GET("/movie-review-service/review/for/{movieID}",reviewRequestHanlder::handleReviewForMovie)
                               .PUT("/movie-review-service/review/",reviewRequestHanlder::handleUpdateReview)
                               .DELETE("/movie-review-service/review/{id}",reviewRequestHanlder::handleDeleteReview)
                               .build();
    }

}
