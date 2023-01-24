package com.myreflectionthoughts.movieinfoservice.dto.response;

import java.util.ArrayList;
import java.util.List;

public class ValidationExceptionResponse {
    private List<String> validationErrors;

    public ValidationExceptionResponse(){
        validationErrors = new ArrayList<>();
    }

    public List<String> getValidationErrors(){
        return validationErrors;
    }
}
