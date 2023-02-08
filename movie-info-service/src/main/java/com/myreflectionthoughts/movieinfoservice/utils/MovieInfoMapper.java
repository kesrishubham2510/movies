package com.myreflectionthoughts.movieinfoservice.utils;

import org.springframework.stereotype.Component;

import com.myreflectionthoughts.movieinfoservice.dto.request.AddMovieInfo;
import com.myreflectionthoughts.library.dto.response.MovieInfoResponse;
import com.myreflectionthoughts.movieinfoservice.models.MovieInfo;

@Component
public class MovieInfoMapper {
    
    public MovieInfo toMovieInfo(AddMovieInfo reqDTO){
        var movieInfo = new MovieInfo();
        movieInfo.setTitle(reqDTO.getTitle());
        movieInfo.setYear(reqDTO.getYear());
        movieInfo.setCast(reqDTO.getCast());
        movieInfo.setReleaseDate(reqDTO.getReleaseDate());
        return movieInfo;
    }

    public MovieInfoResponse toMovieResponseDTO(MovieInfo movieInfo){
        var movieInfoResponse = new MovieInfoResponse();
        movieInfoResponse.setMovieInfoId(movieInfo.getMovieInfoId());
        movieInfoResponse.setTitle(movieInfo.getTitle());
        movieInfoResponse.setYear(movieInfo.getYear());
        movieInfoResponse.setCast(movieInfo.getCast());
        movieInfoResponse.setReleaseDate(movieInfo.getReleaseDate());

        return movieInfoResponse;
    }
}
