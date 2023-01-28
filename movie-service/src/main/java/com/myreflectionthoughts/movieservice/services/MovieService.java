package com.myreflectionthoughts.movieservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.myreflectionthoughts.movieinfoservice.dto.response.MovieInfoResponse;
import com.myreflectionthoughts.moviereviewservice.dto.response.ReviewResponse;
import com.myreflectionthoughts.movieservice.contracts.Find;
import com.myreflectionthoughts.movieservice.dto.response.MovieResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovieService implements Find<MovieResponse>{
    
    @Autowired
    @Qualifier("movie-info-client")
    private WebClient movieInfoServiceClient;

    @Autowired
    @Qualifier("movie-review-client")
    private WebClient movieReviewServiceClient;

    @Override
    public Mono<MovieResponse> findInfo(String movieInfoId) {
       return getMovieInfoById(movieInfoId).flatMap(movieInfo->{
   
            var movieReviewListMono = getReviewsForMovie(movieInfoId).collectList();
          
            return movieReviewListMono.flatMap(movieReviewList->{
                  var movieResponse = new MovieResponse();
                  movieResponse.setMovieInfo(movieInfo);
                  movieResponse.setReviews(movieReviewList);
                  return Mono.just(movieResponse);
                }
            );
       });   
  }

    
    private Mono<MovieInfoResponse> getMovieInfoById(String movieInfoId){
        return movieInfoServiceClient
                                    .get()
                                    .uri("{movieInfoID}",movieInfoId)
                                    .retrieve()
                                    .bodyToMono(MovieInfoResponse.class);
    }

    private Flux<ReviewResponse> getReviewsForMovie(String movieInfoId){
        return movieReviewServiceClient
                                      .get() 
                                      .uri("/for/{movieInfoID}",movieInfoId)
                                      .retrieve()
                                      .bodyToFlux(ReviewResponse.class);
    }

}
