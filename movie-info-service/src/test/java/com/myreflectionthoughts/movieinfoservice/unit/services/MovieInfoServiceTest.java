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

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class MovieInfoServiceTest {
    
     @InjectMocks
     private MovieInfoService movieInfoService;

     @Mock
     private MovieInfoRepository movieInfoRepository; 

     @Mock
     private MovieInfoMapper movieInfoMapper;

     private ConstructUtils constructUtils;

     MovieInfoServiceTest(){
        constructUtils = new ConstructUtils();
     }

     @Test
     void testSave(){
       
       var movieInfoEntity = constructUtils.constructMovieInfoEntity();
       var expectedMovieResponse = constructUtils.constructMovieInfoResponse();

       when(movieInfoMapper.toMovieInfo(any(AddMovieInfo.class))).thenReturn(movieInfoEntity);
       when(movieInfoRepository.save(any(MovieInfo.class))).thenReturn(Mono.just(movieInfoEntity));
       when(movieInfoMapper.toMovieResponseDTO(any(MovieInfo.class))).thenReturn(constructUtils.constructMovieInfoResponse());
       
       Mono<MovieInfoResponse> actualMovieInfoResponse = movieInfoService.save(Mono.just(constructUtils.constructAddMovieInfo()));

       StepVerifier.create(actualMovieInfoResponse).consumeNextWith(movieInfoResponse->{

          assertEquals(expectedMovieResponse.getMovieInfoId(), movieInfoResponse.getMovieInfoId());
          assertEquals(expectedMovieResponse.getReleaseDate(),movieInfoEntity.getReleaseDate());
          assertEquals(expectedMovieResponse.getTitle(), movieInfoResponse.getTitle());
          assertEquals(expectedMovieResponse.getCast(), movieInfoResponse.getCast());
          assertEquals(expectedMovieResponse.getYear(), movieInfoResponse.getYear());
       }).verifyComplete();
    }
    

}
