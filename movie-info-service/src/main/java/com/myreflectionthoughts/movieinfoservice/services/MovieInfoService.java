package com.myreflectionthoughts.movieinfoservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myreflectionthoughts.movieinfoservice.contracts.FindAll;
import com.myreflectionthoughts.movieinfoservice.contracts.SaveEntity;
import com.myreflectionthoughts.movieinfoservice.dto.request.AddMovieInfo;
import com.myreflectionthoughts.movieinfoservice.dto.response.MovieInfoResponse;
import com.myreflectionthoughts.movieinfoservice.repositories.MovieInfoRepository;
import com.myreflectionthoughts.movieinfoservice.utils.MovieInfoMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovieInfoService 
implements SaveEntity<AddMovieInfo,MovieInfoResponse>,
FindAll<MovieInfoResponse>
{
    
    @Autowired 
    private MovieInfoRepository movieInfoRepository;

    @Autowired
    private MovieInfoMapper movieInfoMapper;

    @Override
    public Mono<MovieInfoResponse> save(Mono<AddMovieInfo> reqDTO) {
       return reqDTO.map(movieInfoMapper::toMovieInfo).flatMap(movieInfoRepository::save).map(movieInfoMapper::toMovieResponseDTO);
    }

    @Override
    public Flux<MovieInfoResponse> getAll() {
        return movieInfoRepository.findAll().map(movieInfoMapper::toMovieResponseDTO);
    }

}
