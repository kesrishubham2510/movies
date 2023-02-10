package com.myreflectionthoughts.movieservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import com.myreflectionthoughts.library.dto.response.MovieInfoResponse;
import com.myreflectionthoughts.movieservice.dto.response.MovieResponse;
import com.myreflectionthoughts.movieservice.services.MovieService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movie-service/")
public class MovieController {
 
    @Autowired
    private MovieService movieService;

    @GetMapping("{movieInfoId}")
    public Mono<ResponseEntity<MovieResponse>> getMovieDetails(@PathVariable("movieInfoId") String movieInfoId){
        return movieService.findInfo(movieInfoId)
        .map(ResponseEntity::ok)
        .onErrorReturn(WebClientRequestException.class, ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
    }

    @GetMapping(value = "stream/movie", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<MovieInfoResponse> getLiveMovieUpdates(){
        return movieService.getLatestMovieUpdates();
    }
    
}
