package com.myreflectionthoughts.moviereviewservice.contracts;

import reactor.core.publisher.Flux;

public interface FindReviews<Res> {
    Flux<Res> findForId(String id);
}
