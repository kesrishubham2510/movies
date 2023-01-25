package com.myreflectionthoughts.movieinfoservice.bdd;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.EntityExchangeResult;

import com.myreflectionthoughts.movieinfoservice.dto.response.MovieInfoDeletionResponse;
import com.myreflectionthoughts.movieinfoservice.dto.response.MovieInfoResponse;
import com.myreflectionthoughts.movieinfoservice.exceptions.MovieInfoNotFoundException;

public class DeleteMovieInfo extends TestSetUp {
    
    @Test
    void testDeleteMovieInfo(){
        /**
             * Add a new MovieInfo
             * Send request to delete the added movieinfo
             * Query the database with the ID of the deleted movieInfo to confirm the deletion
         */

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

         // delete the movieInfo in the database
         movieInfoWebClient.delete()
                           .uri(BASE_URL+mongoObjectID)
                           .exchange()
                           .expectStatus()
                           .is2xxSuccessful()
                           .expectBody(MovieInfoDeletionResponse.class)
                           .consumeWith(recievedResponse->{
                                assertEquals(mongoObjectID, recievedResponse.getResponseBody().getId());
                                assertEquals(String.format("Request for deleting movie (id:- %s) has been sucessfully completed",mongoObjectID),recievedResponse.getResponseBody().getMessage());
                           });
        
         // verifying by querying the database and expecting the MovieInfoNotfoundException
         movieInfoWebClient.get()
                           .uri(BASE_URL+mongoObjectID)
                           .exchange()
                           .expectStatus()
                           .isBadRequest()
                           .expectBody(MovieInfoNotFoundException.class);  
 
    }
}
