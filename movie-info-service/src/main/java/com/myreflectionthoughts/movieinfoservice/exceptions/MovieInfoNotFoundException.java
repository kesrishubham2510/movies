package com.myreflectionthoughts.movieinfoservice.exceptions;

public class MovieInfoNotFoundException extends RuntimeException{

    public MovieInfoNotFoundException(String message) {
        super(message);
    }
    
}
