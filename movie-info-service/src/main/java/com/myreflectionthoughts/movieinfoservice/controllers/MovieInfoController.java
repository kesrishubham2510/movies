package com.myreflectionthoughts.movieinfoservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.myreflectionthoughts.movieinfoservice.dto.request.AddMovieInfo;
import com.myreflectionthoughts.movieinfoservice.dto.response.MovieInfoResponse;
import com.myreflectionthoughts.movieinfoservice.services.MovieInfoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("movie-info-service/")
public class MovieInfoController {

    @Autowired
    private MovieInfoService movieInfoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<MovieInfoResponse> addMovieInfo(@RequestBody Mono<AddMovieInfo> addMovieInfo){
        return movieInfoService.save(addMovieInfo);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Flux<MovieInfoResponse> getAllMovies(){
        return movieInfoService.getAll();
    }

    
}
