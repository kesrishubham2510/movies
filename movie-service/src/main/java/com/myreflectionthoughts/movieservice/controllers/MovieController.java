package com.myreflectionthoughts.movieservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myreflectionthoughts.movieservice.dto.response.MovieResponse;
import com.myreflectionthoughts.movieservice.services.MovieService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movie-service/")
public class MovieController {
 
    @Autowired
    private MovieService movieService;

    @GetMapping("{movieInfoId}")
    public Mono<ResponseEntity<MovieResponse>> getMovieDetails(@PathVariable("movieInfoId") String movieInfoId){
        return movieService.findInfo(movieInfoId).map(ResponseEntity::ok);
    }
    
}
