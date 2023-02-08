package com.myreflectionthoughts.movieinfoservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.myreflectionthoughts.library.dto.response.MovieInfoResponse;
import com.myreflectionthoughts.movieinfoservice.services.MovieInfoService;

import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("movie-info-service/movie/")
public class MovieInfoController {

    @Autowired
    private MovieInfoService movieInfoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<ResponseEntity<MovieInfoResponse>> addMovieInfo(@RequestBody @Valid Mono<AddMovieInfo> addMovieInfo){
        return movieInfoService.save(addMovieInfo).map(movieInfoResponse -> ResponseEntity.status(HttpStatus.CREATED).body(movieInfoResponse));
    }

    @GetMapping("all")
    @ResponseStatus(HttpStatus.OK)
    Flux<MovieInfoResponse> getAllMovies(){
        return movieInfoService.getAll();
    }

    @GetMapping("{id}")
    // @ResponseStatus(HttpStatus.OK)
    Mono<ResponseEntity<MovieInfoResponse>> getMovie(@PathVariable("id") String movieId){
        return movieInfoService.findEntity(movieId).map(ResponseEntity::ok);
    }

    @PutMapping()
    // @ResponseStatus(HttpStatus.OK)
    Mono<ResponseEntity<MovieInfoResponse>> updateMovieInfo(@RequestBody @Valid Mono<UpdateMovieInfo> updateMovieInfo){
        return movieInfoService.update(updateMovieInfo).map(ResponseEntity::ok);
    }

    @DeleteMapping("{id}")
    // @ResponseStatus(HttpStatus.OK)
    Mono<ResponseEntity<MovieInfoDeletionResponse>> deleteMovieInfo(@PathVariable("id") String movieId){
      return movieInfoService.delete(movieId).map(ResponseEntity::ok);
    }

    
}
