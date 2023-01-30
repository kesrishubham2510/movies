package com.myreflectionthoughts.movieservice.bdd;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.github.tomakehurst.wiremock.WireMockServer;

/*
         * To simulate a live running server in during the tests, wiremock has been used.
         * The wiremock server needs a specific port to run, here port:8025 is be used.
         * The restClients ( movie-info-service and movie-review-service ) are configured to run on 8002 & 8003,
           but when we mock those server using wiremock, we need them to run on a port where wiremock server runs. 
           Hence, the restClient URLs will be overriden for the test environment.
  
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
    "restClient.infoService = http://localhost:8025/movie-info-service/movie/",
    "restClient.reviewService = http://localhost:8025/movie-review-service/review/"
})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestSetup {

  protected WireMockServer wireMockServer;

  @Autowired
  protected WebTestClient webTestClient;

  @BeforeAll
  void setUpServer() {

    wireMockServer = new WireMockServer(8025);
    wireMockServer.start();
  }

  @AfterAll
  void shutDownServer() {
    this.wireMockServer.stop();
  }
}