package com.myreflectionthoughts.movieinfoservice.unit.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.myreflectionthoughts.movieinfoservice.ConstructUtils;
import com.myreflectionthoughts.movieinfoservice.dto.request.AddMovieInfo;
import com.myreflectionthoughts.movieinfoservice.dto.response.MovieInfoResponse;
import com.myreflectionthoughts.movieinfoservice.models.MovieInfo;
import com.myreflectionthoughts.movieinfoservice.repositories.MovieInfoRepository;
import com.myreflectionthoughts.movieinfoservice.services.MovieInfoService;
import com.myreflectionthoughts.movieinfoservice.utils.MovieInfoMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class MovieInfoServiceTest {
    
     @InjectMocks
     private MovieInfoService movieInfoService;

     @Mock
     private MovieInfoRepository movieInfoRepositoryMock; 

     @Mock
     private MovieInfoMapper movieInfoMapperMock;

     private ConstructUtils constructUtils;

     MovieInfoServiceTest(){
        constructUtils = new ConstructUtils();
     }

     @Test
     void testSave(){
       
       var movieInfoEntity = constructUtils.constructMovieInfoEntity();
       var expectedMovieResponse = constructUtils.constructMovieInfoResponse();

       when(movieInfoMapperMock.toMovieInfo(any(AddMovieInfo.class))).thenReturn(movieInfoEntity);
       when(movieInfoRepositoryMock.save(any(MovieInfo.class))).thenReturn(Mono.just(movieInfoEntity));
       when(movieInfoMapperMock.toMovieResponseDTO(any(MovieInfo.class))).thenReturn(constructUtils.constructMovieInfoResponse());
       
       Mono<MovieInfoResponse> actualMovieInfoResponse = movieInfoService.save(Mono.just(constructUtils.constructAddMovieInfo()));

       StepVerifier.create(actualMovieInfoResponse).consumeNextWith(movieInfoResponse->{

          assertEquals(expectedMovieResponse.getMovieInfoId(), movieInfoResponse.getMovieInfoId());
          assertEquals(expectedMovieResponse.getReleaseDate(),movieInfoEntity.getReleaseDate());
          assertEquals(expectedMovieResponse.getTitle(), movieInfoResponse.getTitle());
          assertEquals(expectedMovieResponse.getCast(), movieInfoResponse.getCast());
          assertEquals(expectedMovieResponse.getYear(), movieInfoResponse.getYear());
       }).verifyComplete();
    }

    @Test
    void testGetAll(){

       var expectedMovieInfoResponse = constructUtils.constructMovieInfoResponse();

       when(movieInfoRepositoryMock.findAll()).thenReturn(Flux.just(constructUtils.constructMovieInfoEntity()));
       when(movieInfoMapperMock.toMovieResponseDTO(any(MovieInfo.class))).thenReturn(expectedMovieInfoResponse);

       Flux<MovieInfoResponse> actualMovieInfoRetreived = movieInfoService.getAll();
       
       StepVerifier.create(actualMovieInfoRetreived).expectNextCount(1).verifyComplete();
    }
    

}
