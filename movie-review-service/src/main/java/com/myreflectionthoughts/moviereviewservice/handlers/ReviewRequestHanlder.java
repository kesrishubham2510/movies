package com.myreflectionthoughts.moviereviewservice.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.myreflectionthoughts.moviereviewservice.dto.request.AddReview;
import com.myreflectionthoughts.moviereviewservice.dto.request.UpdateReview;
import com.myreflectionthoughts.moviereviewservice.dto.response.DeleteReviewResponse;
import com.myreflectionthoughts.moviereviewservice.dto.response.ReviewResponse;
import com.myreflectionthoughts.moviereviewservice.services.ReviewService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReviewRequestHanlder {

    @Autowired
    private ReviewService reviewService;
    
    public Mono<ServerResponse> handleAddReview(ServerRequest serverRequest){
 
        Mono<ReviewResponse> response =reviewService.save(serverRequest.bodyToMono(AddReview.class));
        return ServerResponse.status(HttpStatus.CREATED).body(response,ReviewResponse.class);
    }

    public Mono<ServerResponse> handleGetReview(ServerRequest serverRequest){

         String reviewId = serverRequest.pathVariable("id");
         Mono<ReviewResponse> response = reviewService.find(reviewId);
         return ServerResponse.ok().body(response, ReviewResponse.class);
    }

    public Mono<ServerResponse> handleGetallReviews(ServerRequest serverRequest){
        Flux<ReviewResponse> reviewsFlux = reviewService.getAll();
        return ServerResponse.ok().body(reviewsFlux, ReviewResponse.class);
    }

    public Mono<ServerResponse> handleUpdateReview(ServerRequest serverRequest){
        Mono<ReviewResponse> response = reviewService.updateEntity(serverRequest.bodyToMono(UpdateReview.class));
        return ServerResponse.ok().body(response, ReviewResponse.class);
    }

    public Mono<ServerResponse> handleDeleteReview(ServerRequest serverRequest){
        Mono<DeleteReviewResponse> response = reviewService.delete(serverRequest.pathVariable("id"));
        return ServerResponse.ok().body(response, DeleteReviewResponse.class);
    }

    public Mono<ServerResponse> handleReviewForMovie(ServerRequest serverRequest){
        Flux<ReviewResponse> response = reviewService.findForId(serverRequest.pathVariable("movieID"));
        return ServerResponse.ok().body(response, ReviewResponse.class);
    }

}
