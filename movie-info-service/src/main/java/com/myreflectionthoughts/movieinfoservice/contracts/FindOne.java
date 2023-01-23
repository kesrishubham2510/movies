package com.myreflectionthoughts.movieinfoservice.contracts;

import reactor.core.publisher.Mono;

public interface FindOne<Res> {
    Mono<Res> findEntity(String id);
}
