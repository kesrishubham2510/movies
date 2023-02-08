package com.myreflectionthoughts.moviereviewservice.bdd;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.myreflectionthoughts.library.dto.response.ReviewResponse;

public class AddReviewTest extends TestSetup{
    
  

     @Test
     void testAddReview(){
        
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
     }
     
}
