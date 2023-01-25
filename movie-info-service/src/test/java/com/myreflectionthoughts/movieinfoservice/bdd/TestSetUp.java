package com.myreflectionthoughts.movieinfoservice.bdd;

import java.util.function.Consumer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.myreflectionthoughts.movieinfoservice.models.MovieInfo;
import com.myreflectionthoughts.movieinfoservice.repositories.MovieInfoRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestSetUp {
    
    protected static String BASE_URL = "/movie-info-service/movie/"; 
   
    @Autowired
    private MovieInfoRepository movieInfoRepository;

    @Autowired
    protected WebTestClient movieInfoWebClient;


    @BeforeEach
    void prepareData(){
        Consumer<MovieInfo> logMovieInfoStats = (movieInfo)-> { 
            System.out.println(movieInfo);
            movieInfo.getCast().stream().forEach(System.out::println);
        };
        movieInfoRepository.saveAll(TestData.getMovieList()).subscribe(logMovieInfoStats);
    
    }
    
    @AfterEach
    void cleanData(){
        movieInfoRepository.deleteAll().subscribe();
    }
}
