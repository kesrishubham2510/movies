package com.myreflectionthoughts.movieinfoservice.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.myreflectionthoughts.movieinfoservice.dto.response.ExceptionResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MovieInfoNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleMovieInfoNotFoundException(MovieInfoNotFoundException movieInfoNotFoundException){
        var exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(movieInfoNotFoundException.getMessage());
        return ResponseEntity.badRequest().body(exceptionResponse);
    }
}
