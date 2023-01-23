package com.myreflectionthoughts.movieinfoservice.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.myreflectionthoughts.movieinfoservice.models.MovieInfo;

@Repository
public interface MovieInfoRepository extends ReactiveMongoRepository<MovieInfo,String>{
    
}
