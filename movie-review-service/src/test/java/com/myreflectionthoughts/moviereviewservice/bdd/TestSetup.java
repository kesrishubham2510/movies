package com.myreflectionthoughts.moviereviewservice.bdd;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.myreflectionthoughts.moviereviewservice.repositories.ReviewRepository;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestSetup {
    
    protected static final String BASE_URL = "/movie-review-service/review/";

    @Autowired
    protected ReviewRepository reviewRepository;

    @Autowired
    protected WebTestClient webReviewClient;

    @BeforeEach
    void prepareTestData(){
        // since saveAll() returns a pulisher, that publisher needs to be subscribed in order to execute the saveAll functionality
        reviewRepository.saveAll(TestData.intialReviews()).subscribe(System.out::println);
    }
    
    @AfterEach
    void testDataCleanUp(){
        // since deleteAll() returns a pulisher, that publisher needs to be subscribed in order to execute the deleteAll functionality
        reviewRepository.deleteAll().subscribe();
    }
    
}
