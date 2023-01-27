package com.myreflectionthoughts.moviereviewservice.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.myreflectionthoughts.moviereviewservice.models.Review;

import reactor.core.publisher.Flux;

public interface ReviewRepository extends ReactiveMongoRepository<Review,String>{
    
     Flux<Review> findByMovieInfoId(String movieId);
}
