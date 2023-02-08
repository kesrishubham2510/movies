package com.myreflectionthoughts.library.dto.response;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class MovieInfoResponse {
    private String movieInfoId;
    private String title;
    private Integer year;
    private List<String> cast;
    private LocalDate releaseDate;
}
