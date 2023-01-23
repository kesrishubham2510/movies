package com.myreflectionthoughts.movieinfoservice.dto.request;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class AddMovieInfo {

    private String title;
    private Integer year;
    private List<String> cast;
    private LocalDate releaseDate;
}
