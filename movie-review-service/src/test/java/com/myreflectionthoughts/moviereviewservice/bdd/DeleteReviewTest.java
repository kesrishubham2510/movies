package com.myreflectionthoughts.moviereviewservice.bdd;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.EntityExchangeResult;

import com.myreflectionthoughts.moviereviewservice.dto.response.DeleteReviewResponse;
import com.myreflectionthoughts.moviereviewservice.dto.response.ReviewResponse;

public class DeleteReviewTest extends TestSetup{
    
    @Test
    void testDeleteReview(){
        
         /*
           * First add  a review
           * Send the delete request to delete the added review
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
       this.webReviewClient
                        .delete()
                        .uri(BASE_URL+reviewID)
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .expectBody(DeleteReviewResponse.class)
                        .consumeWith(deleteReviewResponse->{
                            assertEquals(reviewID, deleteReviewResponse.getResponseBody().getReviewId());
                            assertEquals(String.format("The Requested review (id:- %s) has been successfully deleted.",reviewID) , deleteReviewResponse.getResponseBody().getMessage());
                        });
    }

    @Test
    void testDeleteById_ThrowsReviewNotFoundException(){

      // the server should excception with a message for a Review that does not exist
      var reviewID = "random@reviewID";
      this.webReviewClient
                          .delete()
                          .uri(BASE_URL+reviewID)
                          .exchange()
                          .expectStatus()
                          .isNotFound()
                          .expectBody(String.class)
                          .consumeWith(receivedResponse->{
                              assertEquals(String.format("The Requested review (id:- %s) does not exist",reviewID) , receivedResponse.getResponseBody());  
                          });

    }
}
