package com.myreflectionthoughts.movieinfoservice.contracts;

import reactor.core.publisher.Flux;

public interface FindAll<Res> {
    Flux<Res> getAll();
}
