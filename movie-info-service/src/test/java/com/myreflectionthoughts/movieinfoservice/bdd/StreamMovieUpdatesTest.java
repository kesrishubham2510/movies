package com.myreflectionthoughts.movieinfoservice.bdd;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.myreflectionthoughts.library.dto.response.MovieInfoResponse;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class StreamMovieUpdatesTest extends TestSetUp{
    
    /*
       * Add a new movie
       * hit the streaming end-point
     
     */

    @Test
    void testMovieUpdatesStreaming(){
        
       // adding the movie
       var addMovieInfoPayload = ConstructUtils.prepareAddMovieRequestPayload("Movie_test_bdd", 1990, List.of(new String("Actor 1"), new String("Programmed Actor 2")), "1990-12-01"); 
       MovieInfoResponse addMovieInfoResponse =  movieInfoWebClient.post()
                         .uri(BASE_URL)
                         .bodyValue(addMovieInfoPayload)
                         .exchange()
                         .expectStatus()
                         .isCreated()
                         .expectBody(MovieInfoResponse.class)
                         .returnResult()
                         .getResponseBody();

                           
       
       var movieInfoId = addMovieInfoResponse.getMovieInfoId();
         
       Flux<MovieInfoResponse> movieInfoResponseMono = this.movieInfoWebClient
            .get()
            .uri(String.format("%sstream",BASE_URL))
            .exchange()
            .expectStatus()
            .is2xxSuccessful()
            .returnResult(MovieInfoResponse.class)
            .getResponseBody();
       
       // streaming endpoint keeps the connection open so we need to explicitly cancel this connection by calling thenCancel() 
       StepVerifier.create(movieInfoResponseMono).consumeNextWith(movieInfoResponse->{
        assertEquals(movieInfoId,movieInfoResponse.getMovieInfoId());
       }).thenCancel().verify();

    }
}
