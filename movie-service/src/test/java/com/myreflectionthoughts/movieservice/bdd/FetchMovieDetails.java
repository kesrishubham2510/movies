package com.myreflectionthoughts.movieservice.bdd;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.UrlPattern;
import com.myreflectionthoughts.movieservice.dto.response.MovieResponse;
import com.myreflectionthoughts.movieservice.exceptions.MovieInfoServiceException;
import com.myreflectionthoughts.movieservice.exceptions.MovieInfoServiceServerException;
import com.myreflectionthoughts.movieservice.exceptions.MovieReviewServiceServerException;

/*
     * To retreive a movieById, a call will be made to the other services namely
       movie-info-service and movie-review-service.

     * Those services will not be running, so the services have been mocked using 
       WireMock
*/

public class FetchMovieDetails extends TestSetup {

  @Test
  void retrieveMovieByID() {

    var movieId = "randomMovieID";

    /*
         * By default the file "movieInfoResponse.json" will be
           looked under /test/resources/__files
     */

    // stub for movie-info-service
    this.wireMockServer.stubFor(
        WireMock.get(String.format("/movie-info-service/movie/%s", movieId))
            .willReturn(WireMock.aResponse().withStatus(200).withHeader("Content-Type", "application/json")
                .withBodyFile("movieInfoResponse.json")));

    // stub for movie-review-service
    this.wireMockServer.stubFor(
        WireMock.get(String.format("/movie-review-service/review/for/%s", movieId))
            .willReturn(WireMock.aResponse().withStatus(200).withHeader("Content-Type", "application/json")
                .withBodyFile("reviewResponseList.json")));

    webTestClient
        .get()
        .uri(String.format("/movie-service/%s", movieId))
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(MovieResponse.class)
        .consumeWith(movieExchangeResult -> {
          assertEquals(movieId, movieExchangeResult.getResponseBody().getMovieInfo().getMovieInfoId());
          assertEquals(3, movieExchangeResult.getResponseBody().getReviews().size());
        });
  }

  @Test
  void testGetMovieDetails_EmptyReviewList() {

    var movieId = "randomMovieID";

    // stub for movie-info-service
    this.wireMockServer
        .stubFor(WireMock.get(String.format("/movie-info-service/movie/%s", movieId))
            .willReturn(WireMock.aResponse().withStatus(200).withHeader("Content-type", "application/json")
                .withBodyFile("movieInfoResponse.json")));

    // stub for movie-review-service
    this.wireMockServer
        .stubFor(WireMock.get(String.format("/movie-review-service/review/for/%s", movieId))
            .willReturn(WireMock.aResponse().withHeader("Content-type", "application/json")
                .withBodyFile("emptyReviewResponseList.json")));

    webTestClient.get()
        .uri("movie-service/{movieId}", movieId)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(MovieResponse.class)
        .consumeWith(movieExchangeResult -> {
          assertEquals(movieId, movieExchangeResult.getResponseBody().getMovieInfo().getMovieInfoId());
          assertEquals(0, movieExchangeResult.getResponseBody().getReviews().size());
        });
  }

  @Test
  void testFetchMovieByID_ThrowsMovieInfoServicdeException_404() {

    var movieId = "randomMovieID";

    // stub for movie-info-service
    this.wireMockServer
        .stubFor(WireMock.get(String.format("/movie-info-service/movie/%s", movieId))
            .willReturn(WireMock.aResponse().withStatus(404).withHeader("Content-type", "application/json")
                .withBodyFile("movieInfoNotFoundException.json")));

    // stub for movie-review-service
    this.wireMockServer
        .stubFor(WireMock.get(String.format("/movie-review-service/review/for/%s", movieId))
            .willReturn(WireMock.aResponse().withHeader("Content-type", "application/json")
                .withBodyFile("emptyReviewResponseList.json")));

    webTestClient.get()
        .uri("movie-service/{movieId}", movieId)
        .exchange()
        .expectStatus()
        .isNotFound()
        .expectBody(MovieInfoServiceException.class)
        .consumeWith(movieExceptionExchangeResult -> {
          assertEquals(String.format("Movie info for id:- %s does not exist", movieId),
              movieExceptionExchangeResult.getResponseBody().getMessage());
        });
  }

  @Test
  void testFetchByMovieID_ThrowsMovieInfoServiceException_500() {

    var movieId = "randomMovieID";

    // stub for movie-info-service
    this.wireMockServer
        .stubFor(WireMock.get(String.format("/movie-info-service/movie/%s", movieId))
            .willReturn(WireMock.aResponse().withStatus(500).withHeader("Content-type", "application/json")
                .withBodyFile("movieInfoServiceServerException500.json")));

    // stub for movie-review-service
    this.wireMockServer
        .stubFor(WireMock.get(String.format("/movie-review-service/review/for/%s", movieId))
            .willReturn(WireMock.aResponse().withHeader("Content-type", "application/json")
                .withBodyFile("emptyReviewResponseList.json")));

    webTestClient.get()
        .uri("movie-service/{movieId}", movieId)
        .exchange()
        .expectStatus()
        .is5xxServerError()
        .expectBody(MovieInfoServiceServerException.class)
        .consumeWith(movieExceptionExchangeResult -> {
          assertEquals("Internal Server Occured at movie-info-service",
              movieExceptionExchangeResult.getResponseBody().getMessage());
        });

     this.wireMockServer.verify(3, WireMock.getRequestedFor(UrlPattern.fromOneOf(String.format("/movie-info-service/movie/%s", movieId), null, null, null)));   
    }

  @Test
  void testFetchByMovieID_ThrowsMovieReviewServiceException_500() {

    var movieId = "randomMovieID";

    // stub for movie-info-service
    this.wireMockServer
        .stubFor(WireMock.get(String.format("/movie-info-service/movie/%s", movieId))
            .willReturn(WireMock.aResponse().withStatus(200).withHeader("Content-type", "application/json")
                .withBodyFile("movieInfoResponse.json")));

    // stub for movie-review-service
    this.wireMockServer
        .stubFor(WireMock.get(String.format("/movie-review-service/review/for/%s", movieId))
            .willReturn(WireMock.aResponse().withStatus(500).withHeader("Content-type", "application/json")
                .withBodyFile("movieReviewServiceServerExceptions.json")));

    webTestClient.get()
        .uri("movie-service/{movieId}", movieId)
        .exchange()
        .expectStatus()
        .is5xxServerError()
        .expectBody(MovieReviewServiceServerException.class)
        .consumeWith(movieExceptionExchangeResult -> {
          assertEquals("Internal Server Occured at movie-review-service",
              movieExceptionExchangeResult.getResponseBody().getMessage());
        });

    this.wireMockServer.verify(3, WireMock.getRequestedFor(UrlPattern.fromOneOf(String.format("/movie-review-service/review/for/%s", movieId), null, null, null)));   
   
  }
}
