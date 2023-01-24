package com.myreflectionthoughts.movieinfoservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.myreflectionthoughts.movieinfoservice.dto.request.AddMovieInfo;
import com.myreflectionthoughts.movieinfoservice.dto.request.UpdateMovieInfo;
import com.myreflectionthoughts.movieinfoservice.dto.response.MovieInfoDeletionResponse;
import com.myreflectionthoughts.movieinfoservice.dto.response.MovieInfoResponse;
import com.myreflectionthoughts.movieinfoservice.services.MovieInfoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("movie-info-service/movie/")
public class MovieInfoController {

    @Autowired
    private MovieInfoService movieInfoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<MovieInfoResponse> addMovieInfo(@RequestBody Mono<AddMovieInfo> addMovieInfo){
        return movieInfoService.save(addMovieInfo);
    }

    @GetMapping("all")
    @ResponseStatus(HttpStatus.OK)
    Flux<MovieInfoResponse> getAllMovies(){
        return movieInfoService.getAll();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<MovieInfoResponse> getMovie(@PathVariable("id") String movieId){
        return movieInfoService.findEntity(movieId);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    Mono<MovieInfoResponse> updateMovieInfo(@RequestBody Mono<UpdateMovieInfo> updateMovieInfo){
        return movieInfoService.update(updateMovieInfo);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<MovieInfoDeletionResponse> deleteMovieInfo(@PathVariable("id") String movieId){
      return movieInfoService.delete(movieId);
    }

    
}
