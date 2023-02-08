package com.myreflectionthoughts.movieservice.dto.response;

import java.util.List;

import com.myreflectionthoughts.library.dto.response.MovieInfoResponse;
import com.myreflectionthoughts.library.dto.response.ReviewResponse;

import lombok.Data;

@Data
public class MovieResponse {
    
    private MovieInfoResponse movieInfo;
    private List<ReviewResponse> reviews;
}
