package com.myreflectionthoughts.movieservice.Junit.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.myreflectionthoughts.library.dto.response.MovieInfoResponse;
import com.myreflectionthoughts.library.dto.response.ReviewResponse;
import com.myreflectionthoughts.movieservice.controllers.MovieController;
import com.myreflectionthoughts.movieservice.dto.response.MovieResponse;
import com.myreflectionthoughts.movieservice.exceptions.MovieInfoServiceException;
import com.myreflectionthoughts.movieservice.exceptions.MovieInfoServiceServerException;
import com.myreflectionthoughts.movieservice.exceptions.MovieReviewServiceServerException;
import com.myreflectionthoughts.movieservice.services.MovieService;

import reactor.core.publisher.Mono;

@WebFluxTest(MovieController.class)
public class MovieControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private MovieService movieServiceMock;

    private MovieResponse movieResponse;
    private String movieId;
    private String title;
    private Integer year;
    private List<String> cast;
    private LocalDate releaseDate;

    MovieControllerTest() {
        movieId = "abcd@movie";
        title = "Movie 1";
        year = 1998;
        cast = List.of( new String("Actor 1"), new String("Actor 2"),new String("Actress 1"));
        releaseDate =  LocalDate.parse("2010-12-25", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        movieResponse = new MovieResponse();
        movieResponse.setMovieInfo(constructMovieInfoResponse());
        movieResponse.setReviews(List.of(constructReviewResponse()));
    }

    private MovieInfoResponse constructMovieInfoResponse() {

        var movieInfoResponse = new MovieInfoResponse();

        movieInfoResponse.setMovieInfoId(movieId);
        movieInfoResponse.setTitle(title);
        movieInfoResponse.setCast(cast);
        movieInfoResponse.setYear(year);
        movieInfoResponse.setReleaseDate(releaseDate);

        return movieInfoResponse;
    }

    private ReviewResponse constructReviewResponse() {

        var reviewResponse = new ReviewResponse();
        reviewResponse.setMovieInfoId("movieId@MongoDB");
        reviewResponse.setRating(3.5);
        reviewResponse.setReview("The movie is not upto the mark, it's based on false incidents.");
        reviewResponse.setReviewId("randomReviewID");
        return reviewResponse;
    }

    @Test
    void testGetMovieDetails() {

        when(movieServiceMock.findInfo(movieId)).thenReturn(Mono.just(movieResponse));

        webTestClient.get()
                     .uri("/movie-service/{movieId}",movieId) 
                     .exchange()
                     .expectStatus()
                     .is2xxSuccessful()
                     .expectBody(MovieResponse.class)
                     .consumeWith(movieExchangeResult->{
                         var receivedMovieDetails =  movieExchangeResult.getResponseBody();
                        assertEquals(movieId, receivedMovieDetails.getMovieInfo().getMovieInfoId());
                        assertEquals(title, receivedMovieDetails.getMovieInfo().getTitle());
                        assertEquals(year, receivedMovieDetails.getMovieInfo().getYear());
                        assertEquals(cast, receivedMovieDetails.getMovieInfo().getCast());
                        assertEquals(releaseDate, receivedMovieDetails.getMovieInfo().getReleaseDate());
                        assertEquals(1, receivedMovieDetails.getReviews().size());                        
                     });
        
        verify(movieServiceMock,times(1)).findInfo(anyString());
    }


    @Test
    void testGetMovieDetails_ThrowsMovieInfoServiceException_404() {

        when(movieServiceMock.findInfo(movieId)).thenThrow(MovieInfoServiceException.class);

        webTestClient.get()
                     .uri("/movie-service/{movieId}",movieId) 
                     .exchange()
                     .expectStatus()
                     .is4xxClientError();
        
        verify(movieServiceMock,times(1)).findInfo(anyString());
    }

    @Test
    void testGetMovieDetails_ThrowsMovieInfoServiceServerException_500() {

        when(movieServiceMock.findInfo(movieId)).thenThrow(MovieInfoServiceServerException.class);

        webTestClient.get()
                     .uri("/movie-service/{movieId}",movieId) 
                     .exchange()
                     .expectStatus()
                     .is5xxServerError();
        
        verify(movieServiceMock,times(1)).findInfo(anyString());
    }

    @Test
    void testGetMovieDetails_ThrowsMovieReviewServiceServerException_500() {

        when(movieServiceMock.findInfo(movieId)).thenThrow(MovieReviewServiceServerException.class);

        webTestClient.get()
                     .uri("/movie-service/{movieId}",movieId) 
                     .exchange()
                     .expectStatus()
                     .is5xxServerError();
        
        verify(movieServiceMock,times(1)).findInfo(anyString());
    }

}
