package com.myreflectionthoughts.moviereviewservice.bdd;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.EntityExchangeResult;

import com.myreflectionthoughts.library.dto.response.ReviewResponse;
import com.myreflectionthoughts.moviereviewservice.ConstructUtils;

public class UpdateReviewTest extends TestSetup {
    
    @Test
    void testUpdateReview(){
        
         /*
           * First add  a review
           * Send the PUT request to uypdate the added review
         */

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
             
      // trying to update the added review by sending PUT request
      var updateReviewPayload = ConstructUtils.constructUpdateReviewPayload(reviewID,addReviewPayload.getMovieInfoId(),addReviewPayload.getReview()+" !!",3.6);
      this.webReviewClient
                        .put()
                        .uri(BASE_URL)
                        .bodyValue(updateReviewPayload)
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .expectBody(ReviewResponse.class)
                        .consumeWith(updatedReviewResponse->{
                            assertEquals(updateReviewPayload.getReviewId() ,updatedReviewResponse.getResponseBody().getReviewId());
                            assertEquals(updateReviewPayload.getMovieInfoId() ,updatedReviewResponse.getResponseBody().getMovieInfoId());
                            assertEquals(updateReviewPayload.getReview() ,updatedReviewResponse.getResponseBody().getReview());
                            assertEquals(updateReviewPayload.getRating() ,updatedReviewResponse.getResponseBody().getRating());
                        });


    }

    @Test
    void testUpdateReview_ThrowsReviewNotFoundException(){
        
        var reviewID = "random@reviewID";
        
        // trying to update the added review by sending PUT request
        var updateReviewPayload = ConstructUtils.constructUpdateReviewPayload(reviewID,"movieInfo@ID","I am trying to update the review "+" !!",3.6);
    
        this.webReviewClient
                            .put()
                            .uri(BASE_URL)
                            .bodyValue(updateReviewPayload)
                            .exchange()
                            .expectStatus()
                            .isNotFound()
                            .expectBody(String.class)
                            .consumeWith(receivedResponse->{
                                assertEquals(String.format("The Requested review (id:- %s) does not exist",reviewID) , receivedResponse.getResponseBody());  
                            });

    }
}
