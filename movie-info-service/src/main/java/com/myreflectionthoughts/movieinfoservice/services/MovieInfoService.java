package com.myreflectionthoughts.movieinfoservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myreflectionthoughts.library.dto.response.MovieInfoResponse;
import com.myreflectionthoughts.movieinfoservice.contracts.DeleteEntity;
import com.myreflectionthoughts.movieinfoservice.contracts.FindAll;
import com.myreflectionthoughts.movieinfoservice.contracts.FindOne;
import com.myreflectionthoughts.movieinfoservice.contracts.SaveEntity;
import com.myreflectionthoughts.movieinfoservice.contracts.UpdateEntity;
import com.myreflectionthoughts.movieinfoservice.dto.request.AddMovieInfo;
import com.myreflectionthoughts.movieinfoservice.dto.request.UpdateMovieInfo;
import com.myreflectionthoughts.movieinfoservice.dto.response.MovieInfoDeletionResponse;
import com.myreflectionthoughts.movieinfoservice.exceptions.MovieInfoNotFoundException;
import com.myreflectionthoughts.movieinfoservice.repositories.MovieInfoRepository;
import com.myreflectionthoughts.movieinfoservice.utils.MovieInfoMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class MovieInfoService 
implements SaveEntity<AddMovieInfo,MovieInfoResponse>,
FindAll<MovieInfoResponse>,
FindOne<MovieInfoResponse>,
UpdateEntity<UpdateMovieInfo,MovieInfoResponse>,
DeleteEntity<MovieInfoDeletionResponse>
{
    
    // to every new subscriber, this sink will publish all the movieInfo's from the sink

    private Sinks.Many<MovieInfoResponse> movieInfoSink = Sinks.many().replay().all();

    @Autowired 
    private MovieInfoRepository movieInfoRepository;

    @Autowired
    private MovieInfoMapper movieInfoMapper;

    @Override
    public Mono<MovieInfoResponse> save(Mono<AddMovieInfo> reqDTO) {
        return reqDTO.map(movieInfoMapper::toMovieInfo).flatMap(movieInfoRepository::save).map(movieInfoMapper::toMovieResponseDTO).doOnNext(savedMovie->movieInfoSink.tryEmitNext(savedMovie));
    }

    @Override
    public Flux<MovieInfoResponse> getAll() {
        return movieInfoRepository.findAll().map(movieInfoMapper::toMovieResponseDTO);
    }

    @Override
    public Mono<MovieInfoResponse> findEntity(String movieId) {


        return movieInfoRepository
                                        .findById(movieId)
                                        .map(movieInfoMapper::toMovieResponseDTO)
                                        .switchIfEmpty(Mono.error(
                                            new MovieInfoNotFoundException(String.format("Movie info for id:- %s does not exist",movieId))
                                        ));
    
    }

    @Override
    public Mono<MovieInfoResponse> update(Mono<UpdateMovieInfo> updateReqDTO) {
 
        return updateReqDTO.flatMap(requestDTO->{
              return movieInfoRepository
                                        .findById(requestDTO.getMovieInfoId())
                                        .flatMap(existingMovieInfo->{
                                            
                                            existingMovieInfo.setCast(requestDTO.getCast());
                                            existingMovieInfo.setReleaseDate(requestDTO.getReleaseDate());
                                            existingMovieInfo.setTitle(requestDTO.getTitle());
                                            existingMovieInfo.setYear(requestDTO.getYear());
                                            
                                            return movieInfoRepository.save(existingMovieInfo).map(movieInfoMapper::toMovieResponseDTO);
                                        })
                                        .switchIfEmpty(
                                            Mono.error(
                                                new MovieInfoNotFoundException(String.format("Movie info for id:- %s does not exist",requestDTO.getMovieInfoId()))
                                            )
                                        );
        });
    }

    @Override
    public Mono<MovieInfoDeletionResponse> delete(String movieId) {
        return movieInfoRepository.findById(movieId).flatMap(retreivedMovie->{
            var movieInfoDeletionResponse = new MovieInfoDeletionResponse();
            movieInfoDeletionResponse.setId(movieId);
            movieInfoDeletionResponse.setMessage(String.format("Request for deleting movie (id:- %s) has been sucessfully completed",movieId));
            return movieInfoRepository.deleteById(movieId).thenReturn(movieInfoDeletionResponse);
        }).switchIfEmpty(
            Mono.error(
                new MovieInfoNotFoundException(String.format("Movie info for id:- %s does not exist",movieId))
            )
        );
        
    }

    public Sinks.Many<MovieInfoResponse> getMovieSink(){
        return movieInfoSink;
    }
    

}
