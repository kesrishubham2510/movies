package com.myreflectionthoughts.movieinfoservice.bdd;

import org.junit.jupiter.api.Test;

import com.myreflectionthoughts.movieinfoservice.exceptions.MovieInfoNotFoundException;

public class GetMovie extends TestSetUp {
    
    /*
        *  The test tries to query database with a movieInfoID that doesn't exist
    */

    @Test
    void testGetMovie(){
       
        var mongoObjectID = "anyRandomID";
        
        movieInfoWebClient.get()
                          .uri(BASE_URL+mongoObjectID)
                          .exchange()
                          .expectStatus()
                          .isNotFound()
                          .expectBody(MovieInfoNotFoundException.class);  
        
    }
}
