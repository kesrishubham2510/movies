package com.myreflectionthoughts.moviereviewservice.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.myreflectionthoughts.moviereviewservice.models.Review;

public interface ReviewRepository extends ReactiveMongoRepository<Review,String>{
    
}
