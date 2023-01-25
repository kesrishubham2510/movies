package com.myreflectionthoughts.moviereviewservice.contracts;

import reactor.core.publisher.Flux;

public interface FindAll<Res> {
    Flux<Res> getAll();
}
