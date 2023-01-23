package com.myreflectionthoughts.movieinfoservice.unit.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.myreflectionthoughts.movieinfoservice.ConstructUtils;
import com.myreflectionthoughts.movieinfoservice.controllers.MovieInfoController;
import com.myreflectionthoughts.movieinfoservice.dto.response.MovieInfoResponse;
import com.myreflectionthoughts.movieinfoservice.services.MovieInfoService;

import reactor.core.publisher.Mono;

@WebFluxTest(MovieInfoController.class)
public class MovieInfoControllerTest {

    @MockBean
    private MovieInfoService movieInfoServiceMock;

    @Autowired
    private WebTestClient webTestClient;

    private ConstructUtils constructUtils;

    MovieInfoControllerTest(){
        constructUtils = new ConstructUtils();
    }

    @Test
    void testAddMovieInfo(){
        
        var addMovieInfoRequestBody =  constructUtils.constructMovieInfoResponse();

        when(movieInfoServiceMock.save(any())).thenReturn(Mono.just(addMovieInfoRequestBody));

        webTestClient.post()
                        .uri("/movie-info-service/")
                        .bodyValue(constructUtils.constructAddMovieInfo())
                        .exchange()
                        .expectStatus()
                        .is2xxSuccessful()
                        .expectBody(MovieInfoResponse.class)
                        .value(expectedMovieInfoResponse->{
                            assertEquals(addMovieInfoRequestBody.getMovieInfoId(),expectedMovieInfoResponse.getMovieInfoId());
                            assertEquals(addMovieInfoRequestBody.getTitle(), expectedMovieInfoResponse.getTitle());
                            assertEquals(addMovieInfoRequestBody.getYear(), expectedMovieInfoResponse.getYear());
                            assertEquals(addMovieInfoRequestBody.getReleaseDate(), expectedMovieInfoResponse.getReleaseDate());
                            assertEquals(addMovieInfoRequestBody.getCast(), expectedMovieInfoResponse.getCast());
                        });
    
        verify(movieInfoServiceMock,times(1)).save(any());
    
    }

    
    
}
