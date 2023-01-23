package com.myreflectionthoughts.movieinfoservice.unit.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.myreflectionthoughts.movieinfoservice.ConstructUtils;
import com.myreflectionthoughts.movieinfoservice.controllers.MovieInfoController;
import com.myreflectionthoughts.movieinfoservice.dto.response.ExceptionResponse;
import com.myreflectionthoughts.movieinfoservice.dto.response.MovieInfoResponse;
import com.myreflectionthoughts.movieinfoservice.exceptions.MovieInfoNotFoundException;
import com.myreflectionthoughts.movieinfoservice.services.MovieInfoService;

import reactor.core.publisher.Flux;
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
                        .uri("/movie-info-service/movie/")
                        .bodyValue(constructUtils.constructAddMovieInfo())
                        .exchange()
                        .expectStatus()
                        .is2xxSuccessful()
                        .expectBody(MovieInfoResponse.class)
                        .value(actualMovieInfoResponse->{
                            assertEquals(addMovieInfoRequestBody.getMovieInfoId(),actualMovieInfoResponse.getMovieInfoId());
                            assertEquals(addMovieInfoRequestBody.getTitle(), actualMovieInfoResponse.getTitle());
                            assertEquals(addMovieInfoRequestBody.getYear(), actualMovieInfoResponse.getYear());
                            assertEquals(addMovieInfoRequestBody.getReleaseDate(), actualMovieInfoResponse.getReleaseDate());
                            assertEquals(addMovieInfoRequestBody.getCast(), actualMovieInfoResponse.getCast());
                        });
    
        verify(movieInfoServiceMock,times(1)).save(any());
    }

    @Test
    void testGetAllMovies(){
        var expectedMovieInfoResponse = constructUtils.constructMovieInfoResponse();
        when(movieInfoServiceMock.getAll()).thenReturn(Flux.just(expectedMovieInfoResponse));

        webTestClient.get()
                     .uri("/movie-info-service/movie/all")
                     .exchange()
                     .expectStatus()
                     .is2xxSuccessful()
                     .expectBodyList(MovieInfoResponse.class)
                     .isEqualTo(List.of(expectedMovieInfoResponse));
        
        verify(movieInfoServiceMock,times(1)).getAll();
    }

    @Test
    void testGetMovie_Success(){

        var expectedMovieInfoResponse = constructUtils.constructMovieInfoResponse();
        when(movieInfoServiceMock.findEntity(anyString())).thenReturn(Mono.just(expectedMovieInfoResponse));

        webTestClient.get()
                     .uri("/movie-info-service/movie/{id}","abcd@movie")
                     .exchange()
                     .expectStatus()
                     .is2xxSuccessful()
                     .expectBody(MovieInfoResponse.class)
                     .value(actualMovieInfoResponse->{
                        assertEquals(expectedMovieInfoResponse.getMovieInfoId(), actualMovieInfoResponse.getMovieInfoId());
                        assertEquals(expectedMovieInfoResponse.getTitle(), actualMovieInfoResponse.getTitle());
                        assertEquals(expectedMovieInfoResponse.getReleaseDate(), actualMovieInfoResponse.getReleaseDate());
                        assertEquals(expectedMovieInfoResponse.getCast(), actualMovieInfoResponse.getCast());
                        assertEquals(expectedMovieInfoResponse.getReleaseDate(), actualMovieInfoResponse.getReleaseDate());
                     });

        verify(movieInfoServiceMock,times(1)).findEntity(anyString());             
    }
    
    @Test
    void testGetMovie_Failure(){


        when(movieInfoServiceMock.findEntity(anyString())).thenThrow(MovieInfoNotFoundException.class);

        webTestClient.get()
                     .uri("/movie-info-service/movie/{id}","abcd@movie")
                     .exchange()
                     .expectStatus()
                     .is4xxClientError()
                     .expectBody(ExceptionResponse.class);
                     
        verify(movieInfoServiceMock,times(1)).findEntity(anyString());             
    }
    
    
}
