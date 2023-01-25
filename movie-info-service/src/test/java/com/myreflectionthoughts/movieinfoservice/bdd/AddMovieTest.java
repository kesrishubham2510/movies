package com.myreflectionthoughts.movieinfoservice.bdd;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.myreflectionthoughts.movieinfoservice.dto.response.MovieInfoResponse;

public class AddMovieTest extends TestSetUp{
   
      @Test
      void testAddMovieToDatabase(){


        var addMovieInfoPayload = ConstructUtils.prepareAddMovieRequestPayload("Movie_test_bdd", 1990, List.of(new String("Actor 1"), new String("Programmed Actor 2")), "1990-12-01"); 
        movieInfoWebClient.post()
                          .uri(BASE_URL)
                          .bodyValue(addMovieInfoPayload)
                          .exchange()
                          .expectStatus()
                          .isCreated()
                          .expectBody(MovieInfoResponse.class)
                          .consumeWith(receivedResponse->{
                             assertEquals(addMovieInfoPayload.getTitle(), receivedResponse.getResponseBody().getTitle());
                             assertEquals(addMovieInfoPayload.getReleaseDate(), receivedResponse.getResponseBody().getReleaseDate());
                             assertEquals(addMovieInfoPayload.getYear(), receivedResponse.getResponseBody().getYear());
                             assertEquals(addMovieInfoPayload.getCast(), receivedResponse.getResponseBody().getCast());
                          });
       }
   
}
