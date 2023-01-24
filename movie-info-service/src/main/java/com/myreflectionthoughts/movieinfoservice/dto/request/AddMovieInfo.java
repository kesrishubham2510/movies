package com.myreflectionthoughts.movieinfoservice.dto.request;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AddMovieInfo {

    @NotBlank(message = "Title must not be empty or whitespaces")
    private String title;
    @NotNull
    @Positive(message="Year should be a positive value")
    private Integer year;
    private List<@NotBlank(message = "The cast names should not be blank or whitespaces") String> cast;
    @NotNull(message = "Add the release date in YYYY-MM-dd format")
    private LocalDate releaseDate;
}
