package com.myreflectionthoughts.movieservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.myreflectionthoughts.movieservice.dto.response.ExceptionResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MovieInfoServiceException.class)
    public ResponseEntity<ExceptionResponse> handleMovieInfoServiceException(MovieInfoServiceException movieInfoServiceException){
        var exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(movieInfoServiceException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(MovieReviewServiceException.class)
    public ResponseEntity<ExceptionResponse> handleMovieReviewServiceException(MovieReviewServiceException movieReviewServiceException){
        var exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(movieReviewServiceException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(MovieInfoServiceServerException.class)
    public ResponseEntity<ExceptionResponse> handleMovieInfoServiceServerException(MovieInfoServiceServerException movieInfoServiceServerException){
        var exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(movieInfoServiceServerException.getMessage());
        return ResponseEntity.status(500).body(exceptionResponse);
    }

    @ExceptionHandler(MovieReviewServiceServerException.class)
    public ResponseEntity<ExceptionResponse> handleMovieReviewServiceServerException(MovieReviewServiceServerException movieReviewServiceServerException){
        var exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(movieReviewServiceServerException.getMessage());
        return ResponseEntity.status(500).body(exceptionResponse);
    }
}
