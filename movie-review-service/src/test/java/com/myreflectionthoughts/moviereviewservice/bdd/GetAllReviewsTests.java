package com.myreflectionthoughts.moviereviewservice.bdd;

import org.junit.jupiter.api.Test;

import com.myreflectionthoughts.moviereviewservice.dto.response.ReviewResponse;

public class GetAllReviewsTests extends TestSetup{
    
    @Test
    void testGetallReviews(){
        
        this.webReviewClient.get()
                            .uri(BASE_URL)
                            .exchange()
                            .expectStatus()
                            .isOk()
                            .expectBodyList(ReviewResponse.class)
                            .hasSize(4);

    }
}
