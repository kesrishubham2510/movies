package com.myreflectionthoughts.movieinfoservice.bdd;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.myreflectionthoughts.movieinfoservice.dto.request.AddMovieInfo;
import com.myreflectionthoughts.movieinfoservice.dto.request.UpdateMovieInfo;

public class ConstructUtils {
    
    public static AddMovieInfo prepareAddMovieRequestPayload(
        String title,
        Integer year,
        List<String> cast,
        String releaseDate
    ){
        var addMovieInfo = new AddMovieInfo();
        addMovieInfo.setTitle(title);
        addMovieInfo.setYear(year);
        addMovieInfo.setCast(cast);
        addMovieInfo.setReleaseDate(LocalDate.parse(releaseDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return addMovieInfo;
    }
    
    public static UpdateMovieInfo prepareUpdateMovieRequestPayload(
        String movieInfoId,
        String title,
        Integer year,
        List<String> cast,
        String releaseDate
    ){
        var updateMovieInfo = new UpdateMovieInfo();
        updateMovieInfo.setMovieInfoId(movieInfoId);
        updateMovieInfo.setTitle(title);
        updateMovieInfo.setYear(year);
        updateMovieInfo.setCast(cast);
        updateMovieInfo.setReleaseDate(LocalDate.parse(releaseDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return updateMovieInfo;
    }
    
}

