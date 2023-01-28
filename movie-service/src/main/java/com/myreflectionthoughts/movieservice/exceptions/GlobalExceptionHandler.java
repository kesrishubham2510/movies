package com.myreflectionthoughts.movieservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.myreflectionthoughts.movieservice.dto.response.ExceptionResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MovieInfoServiceException.class)
    public ResponseEntity<ExceptionResponse> handleMovieNotFoundException(MovieInfoServiceException movieInfoServiceException){
        var exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(movieInfoServiceException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }
}
