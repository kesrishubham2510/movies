package com.myreflectionthoughts.movieinfoservice.exceptions;

import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.myreflectionthoughts.movieinfoservice.dto.response.ExceptionResponse;
import com.myreflectionthoughts.movieinfoservice.dto.response.ValidationExceptionResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MovieInfoNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleMovieInfoNotFoundException(MovieInfoNotFoundException movieInfoNotFoundException){
        var exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(movieInfoNotFoundException.getMessage());
        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    // To handle the bean validation related errors
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ValidationExceptionResponse> handleBeanValidations(WebExchangeBindException webExchangeBindException){

         var validationExceptionResponse = new ValidationExceptionResponse();
         var error = webExchangeBindException.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).sorted().collect(Collectors.toList());
         
         for(String validationErrorMessage:error)
            validationExceptionResponse.getValidationErrors().add(validationErrorMessage);


         return ResponseEntity.badRequest().body(validationExceptionResponse);
    }
}
