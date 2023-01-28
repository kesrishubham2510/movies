package com.myreflectionthoughts.movieservice.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {
    
    @Value("${restClient.infoService}")
    private String movieInfoServiceURL;
    
    @Value("${restClient.reviewService}")
    private String movieReviewServiceURL;

    @Bean(name="movie-review-client")
    public WebClient webReviewClient(){
       System.out.println("URL:- "+movieReviewServiceURL);
       return WebClient.create(movieReviewServiceURL);
    }

    @Bean(name="movie-info-client")
    public WebClient webInfoClient(){
        System.out.println("URL:- "+movieInfoServiceURL);
        return WebClient.create(movieInfoServiceURL);
    }
}
