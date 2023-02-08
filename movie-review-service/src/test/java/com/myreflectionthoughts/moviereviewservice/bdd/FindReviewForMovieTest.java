package com.myreflectionthoughts.moviereviewservice.bdd;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.myreflectionthoughts.library.dto.response.ReviewResponse;

public class FindReviewForMovieTest extends TestSetup{

    @Test
    void testGetAllReviewsOfMovie(){

        /*
             * add review for a movie
             * query the database for the same review using movieInfoId
         */

        var addReviewPayload = ConstructUtils.constructAddReviewPayload();

        this.webReviewClient
                           .post()
                           .uri(BASE_URL)
                           .bodyValue(addReviewPayload)
                           .exchange()
                           .expectStatus()
                           .isCreated()
                           .expectBody(ReviewResponse.class)
                           .value(receivedResponse->{
                               assertEquals(addReviewPayload.getMovieInfoId(), receivedResponse.getMovieInfoId());
                               assertEquals(addReviewPayload.getReview(), receivedResponse.getReview());
                               assertEquals(addReviewPayload.getRating(), receivedResponse.getRating());
                           });

        // querying the api for the added review using movieInfoID
        this.webReviewClient
                            .get()
                            .uri(String.format("%sfor/%s",BASE_URL,addReviewPayload.getMovieInfoId()))
                            .exchange()
                            .expectStatus()
                            .isOk()
                            .expectBodyList(ReviewResponse.class)
                            .consumeWith(movieReviewsResponse->{
                                 assertEquals( addReviewPayload.getMovieInfoId(),movieReviewsResponse.getResponseBody().get(0).getMovieInfoId());
                                 assertEquals(addReviewPayload.getRating(),movieReviewsResponse.getResponseBody().get(0).getRating());
                                 assertEquals(addReviewPayload.getReview(),movieReviewsResponse.getResponseBody().get(0).getReview());
                            })
                            .hasSize(1);  

    }

    @Test
    void testGetAllReviewsOfMovie_ThrowsReviewNotFoundException(){

         /*
             * add review for a movie
             * query the database for the same review using wrong movieInfoId
         */

         var addReviewPayload = ConstructUtils.constructAddReviewPayload();

         this.webReviewClient
                            .post()
                            .uri(BASE_URL)
                            .bodyValue(addReviewPayload)
                            .exchange()
                            .expectStatus()
                            .isCreated()
                            .expectBody(ReviewResponse.class)
                            .value(receivedResponse->{
                                assertEquals(addReviewPayload.getMovieInfoId(), receivedResponse.getMovieInfoId());
                                assertEquals(addReviewPayload.getReview(), receivedResponse.getReview());
                                assertEquals(addReviewPayload.getRating(), receivedResponse.getRating());
                            });
 
         // querying the api for the added review using movieInfoID
         var wrongMovieID =  addReviewPayload.getMovieInfoId()+" ";
         this.webReviewClient
                             .get()
                             .uri(String.format("%sfor/%s",BASE_URL,wrongMovieID))
                             .exchange()
                             .expectStatus()
                             .isNotFound()
                             .expectBody(String.class)
                             .consumeWith(movieReviewsResponse->{
                                assertEquals(String.format("No reviews were found for the requested movie (id:- %s), Please check movieInfoId",wrongMovieID), movieReviewsResponse.getResponseBody());
                             });  
 
    }
     


}
