package com.myreflectionthoughts.movieinfoservice.models;



import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Data
@Document(collection = "movie-infos")
public class MovieInfo {
    
    @Id
    private String movieInfoId;
    private String title;
    private Integer year;
    private List<String> cast;
    private LocalDate releaseDate;
}
