package com.myreflectionthoughts.moviereviewservice.bdd;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.EntityExchangeResult;

import com.myreflectionthoughts.moviereviewservice.dto.response.ReviewResponse;


public class GetReviewTest extends TestSetup{
    
     /*
           * First add  a review
           * Query the API for the added review
     */

     @Test
     void testGetReview(){
        String reviewID;
        var addReviewPayload = ConstructUtils.constructAddReviewPayload();

     EntityExchangeResult<ReviewResponse> addReviewResponse =  this.webReviewClient
            .post()
            .uri(BASE_URL)
            .bodyValue(addReviewPayload)
            .exchange()
            .expectStatus()
            .isCreated()
            .expectBody(ReviewResponse.class)
            .returnResult();

            reviewID = addReviewResponse.getResponseBody().getReviewId();
            
        // trying to retreive the added review by reviewID

        this.webReviewClient.get()
                            .uri(BASE_URL+reviewID)
                            .exchange()
                            .expectStatus()
                            .isOk()
                            .expectBody(ReviewResponse.class)
                            .consumeWith(recievedReviewResponse->{
                                assertEquals(reviewID,recievedReviewResponse.getResponseBody().getReviewId());
                                assertEquals(addReviewPayload.getReview(), recievedReviewResponse.getResponseBody().getReview());
                                assertEquals(addReviewPayload.getRating(), recievedReviewResponse.getResponseBody().getRating());
                                assertEquals(addReviewPayload.getMovieInfoId(), recievedReviewResponse.getResponseBody().getMovieInfoId());
                            }) ;  
            
     }

     @Test
     void testGetReview_ThrowsReviewNotFoundException(){

        var reviewID = "rabdom@reviewID";
            
        // trying to retreive the added review by any random reviewID

        this.webReviewClient.get()
                            .uri(BASE_URL+reviewID)
                            .exchange()
                            .expectStatus()
                            .isBadRequest()
                            .expectBody(String.class)
                            .consumeWith(receivedMessage->{

                                assertEquals(String.format("The Requested review (id:- %s) does not exist",reviewID) , receivedMessage.getResponseBody());  
                            });
     }

}
