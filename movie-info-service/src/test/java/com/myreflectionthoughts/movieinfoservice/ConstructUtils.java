package com.myreflectionthoughts.movieinfoservice;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.myreflectionthoughts.movieinfoservice.dto.request.AddMovieInfo;
import com.myreflectionthoughts.movieinfoservice.dto.response.MovieInfoResponse;
import com.myreflectionthoughts.movieinfoservice.models.MovieInfo;


public class ConstructUtils {

    private String movieId;
    private String  title;
    private Integer  year;
    private List<String> cast;
    private LocalDate releaseDate;


    public ConstructUtils(){
        title = "Movie 1";
        movieId = "abcd@movie";
        year = 1998;
        cast = List.of( new String("Actor 1"), new String("Actor 2"),new String("Actress 1"));
        releaseDate =  LocalDate.parse("2010-12-25", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }


    public AddMovieInfo constructAddMovieInfo(){
        var addMovieInfo = new AddMovieInfo();
        addMovieInfo.setTitle(title);
        addMovieInfo.setYear(year);
        addMovieInfo.setCast(cast);
        addMovieInfo.setReleaseDate(releaseDate);
        return addMovieInfo;
    }

    public MovieInfo constructMovieInfoEntity(){
        var movieInfo = new MovieInfo();

        movieInfo.setMovieInfoId(movieId);
        movieInfo.setTitle(title);
        movieInfo.setCast(cast);
        movieInfo.setYear(year);
        movieInfo.setReleaseDate(releaseDate);
        return movieInfo;
    }

    public MovieInfoResponse constructMovieInfoResponse(){

        var movieInfoResponse = new MovieInfoResponse();

        movieInfoResponse.setMovieInfoId(movieId);
        movieInfoResponse.setTitle(title);
        movieInfoResponse.setCast(cast);
        movieInfoResponse.setYear(year);
        movieInfoResponse.setReleaseDate(releaseDate);

        return movieInfoResponse;
    }
    
}
