package com.myreflectionthoughts.movieinfoservice.unit.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.myreflectionthoughts.movieinfoservice.ConstructUtils;
import com.myreflectionthoughts.movieinfoservice.dto.request.AddMovieInfo;
import com.myreflectionthoughts.movieinfoservice.dto.response.MovieInfoResponse;
import com.myreflectionthoughts.movieinfoservice.exceptions.MovieInfoNotFoundException;
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

       verify(movieInfoMapperMock,times(1)).toMovieInfo(any(AddMovieInfo.class));
       verify(movieInfoRepositoryMock,times(1)).save(any(MovieInfo.class));
       verify(movieInfoMapperMock,times(1)).toMovieResponseDTO(any(MovieInfo.class));
      
    }

    @Test
    void testGetAll(){

       var expectedMovieInfoResponse = constructUtils.constructMovieInfoResponse();

       when(movieInfoRepositoryMock.findAll()).thenReturn(Flux.just(constructUtils.constructMovieInfoEntity()));
       when(movieInfoMapperMock.toMovieResponseDTO(any(MovieInfo.class))).thenReturn(expectedMovieInfoResponse);

       Flux<MovieInfoResponse> actualMovieInfoRetreived = movieInfoService.getAll();
       
       StepVerifier.create(actualMovieInfoRetreived).expectNextCount(1).verifyComplete();
      
       verify(movieInfoRepositoryMock,times(1)).findAll();
       verify(movieInfoMapperMock,times(1)).toMovieResponseDTO(any(MovieInfo.class));


      }

    @Test
    void testFindEntity_Success(){
      
        var expectedMovieInfo = constructUtils.constructMovieInfoEntity();
        var expectedMovieInfoResponse = constructUtils.constructMovieInfoResponse();
      
        when(movieInfoRepositoryMock.findById(anyString())).thenReturn(Mono.just(expectedMovieInfo));
        when(movieInfoMapperMock.toMovieResponseDTO(any(MovieInfo.class))).thenReturn(expectedMovieInfoResponse);

        Mono<MovieInfoResponse> actualMovieRetrieved = movieInfoService.findEntity("abcd@movie");
      
        StepVerifier.create(actualMovieRetrieved).consumeNextWith(actualMovieInfoResponse->{
            assertEquals(expectedMovieInfo.getMovieInfoId(), actualMovieInfoResponse.getMovieInfoId());
            assertEquals(expectedMovieInfo.getTitle(), actualMovieInfoResponse.getTitle());
            assertEquals(expectedMovieInfo.getYear(), actualMovieInfoResponse.getYear());
            assertEquals(expectedMovieInfo.getCast(), actualMovieInfoResponse.getCast());
            assertEquals(expectedMovieInfo.getReleaseDate(), actualMovieInfoResponse.getReleaseDate());
        }).verifyComplete();

        verify(movieInfoRepositoryMock,times(1)).findById(anyString());
        verify(movieInfoMapperMock,times(1)).toMovieResponseDTO(any(MovieInfo.class));
    }
    

    @Test
    void testFindEntity_Failure(){
  
      var expectedMovieInfoResponse = constructUtils.constructMovieInfoResponse();
    
      when(movieInfoRepositoryMock.findById(anyString())).thenReturn(Mono.empty());
      when(movieInfoMapperMock.toMovieResponseDTO(any(MovieInfo.class))).thenReturn(expectedMovieInfoResponse);

      Mono<MovieInfoResponse> actualMovieRetrieved = movieInfoService.findEntity("abcd@movie");
    
      StepVerifier.create(actualMovieRetrieved).expectError(MovieInfoNotFoundException.class);

      verify(movieInfoRepositoryMock,times(1)).findById(anyString());
      verify(movieInfoMapperMock,times(0)).toMovieResponseDTO(any(MovieInfo.class));


    }
    
}
