package com.myreflectionthoughts.movieinfoservice.bdd;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.myreflectionthoughts.movieinfoservice.dto.response.MovieInfoResponse;

public class GetAllMovieInfo extends TestSetUp{
    

    // Asserting against the initial four MovieInfo

    @Test
    void testGetAllMovieInfo(){

       movieInfoWebClient.get()
                         .uri(BASE_URL+"all")    
                         .exchange()
                         .expectStatus()
                         .is2xxSuccessful()
                         .expectBodyList(MovieInfoResponse.class)
                         .value(recievedMovieInfoList->{
                            // asserting against the length of the pre-initilised list (length=4)
                            assertEquals(4, recievedMovieInfoList.size());
                         });
    }


}
