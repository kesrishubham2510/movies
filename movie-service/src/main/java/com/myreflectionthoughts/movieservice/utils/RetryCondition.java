package com.myreflectionthoughts.movieservice.utils;

import java.time.Duration;
import java.util.function.Predicate;

import com.myreflectionthoughts.movieservice.exceptions.MovieInfoServiceServerException;
import com.myreflectionthoughts.movieservice.exceptions.MovieReviewServiceServerException;

import reactor.core.Exceptions;
import reactor.util.retry.Retry;

public class RetryCondition {
    

    /*
         To specify the condition the to retry in order to make the service resilient
        
         * Retry max 2 times with a time interval of 1 second between the failure of 
           request and sending a new one.
         * Only retry if the error is of 500, because 4XX error never yield result 
           even after retrying.
         * filter function takes predicate to decide the triggering of retrial for
           the failures.
    */
   
    public static Retry retryCondition(){
        Predicate<Throwable> retryFilter = (exception)-> (exception instanceof MovieInfoServiceServerException || exception instanceof MovieReviewServiceServerException);
    
        Retry retryCondition = Retry.fixedDelay(2, Duration.ofSeconds(1))
                                    .filter(retryFilter)
                                    .onRetryExhaustedThrow((retryBackoffSpec, retrySignal)->Exceptions.propagate(retrySignal.failure())) ;
        return retryCondition;
    }

}
