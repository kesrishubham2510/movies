package com.myreflectionthoughts.movieinfoservice.bdd;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.EntityExchangeResult;

import com.myreflectionthoughts.library.dto.response.MovieInfoResponse;
import com.myreflectionthoughts.movieinfoservice.exceptions.MovieInfoNotFoundException;

public class UpdateMovieInfo extends TestSetUp{
    
      /*
           * first add one movie to get acess to a MongoDB generated ObjectId
           * Send a upaterequest and verify
      */

    @Test
    void testUpdateMovieInfo(){

        String mongoObjectID;

        var addMovieInfoPayload = ConstructUtils.prepareAddMovieRequestPayload("Movie_test_bdd", 1990, List.of(new String("Actor 1"), new String("Programmed Actor 2")), "1990-12-01"); 
        
        // added a new MovieInfo
        EntityExchangeResult<MovieInfoResponse> addMovieInfoResponse =  movieInfoWebClient.post()
                          .uri(BASE_URL)
                          .bodyValue(addMovieInfoPayload)
                          .exchange()
                          .expectStatus()
                          .isCreated()
                          .expectBody(MovieInfoResponse.class)
                          .returnResult();
        
        // retreiving the mongoId of saved object   
        mongoObjectID = addMovieInfoResponse.getResponseBody().getMovieInfoId();
        
        // updating the saved object
        var updateMovieInfoPayload = ConstructUtils.prepareUpdateMovieRequestPayload(mongoObjectID, "Movie_test_bdd_updated", 1990, List.of(new String("Actor 1 updated"), new String("Programmed Actor 2")), "1990-12-01");
        
        movieInfoWebClient.put()
                          .uri(BASE_URL)
                          .bodyValue(updateMovieInfoPayload)
                          .exchange()
                          .expectStatus()
                          .is2xxSuccessful()
                          .expectBody(MovieInfoResponse.class)
                          .consumeWith(receivedResponse->{
                             assertEquals(updateMovieInfoPayload.getMovieInfoId(), receivedResponse.getResponseBody().getMovieInfoId());
                             assertEquals(updateMovieInfoPayload.getTitle(), receivedResponse.getResponseBody().getTitle());
                             assertEquals(updateMovieInfoPayload.getReleaseDate(), receivedResponse.getResponseBody().getReleaseDate());
                             assertEquals(updateMovieInfoPayload.getYear(), receivedResponse.getResponseBody().getYear());
                             assertEquals(updateMovieInfoPayload.getCast(), receivedResponse.getResponseBody().getCast());
                          });  

    }  

    /*
     *  The test checks the condition when a request is sent to update the movieInfo with wrong Id
     */
    @Test
    void testUpdateMovie_Failure(){

      var mongoObjectID = "abcd@movies";
      var updateMovieInfoPayload = ConstructUtils.prepareUpdateMovieRequestPayload(mongoObjectID, "Movie_test_bdd_updated", 1990, List.of(new String("Actor 1 updated"), new String("Programmed Actor 2")), "1990-12-01");
        
      movieInfoWebClient.put()
                        .uri(BASE_URL)
                        .bodyValue(updateMovieInfoPayload)
                        .exchange()
                        .expectStatus()
                        .isNotFound()
                        .expectBody(MovieInfoNotFoundException.class);  
    }


}
