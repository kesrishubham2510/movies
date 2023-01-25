package com.myreflectionthoughts.moviereviewservice.contracts;

import reactor.core.publisher.Mono;

public interface FindOne<Res> {
    Mono<Res> find(String id);
}
