package com.myreflectionthoughts.moviereviewservice.exceptions;

public class ReviewNotFoundException extends RuntimeException{
    public ReviewNotFoundException(String message) {
        super(message);
    }
}
