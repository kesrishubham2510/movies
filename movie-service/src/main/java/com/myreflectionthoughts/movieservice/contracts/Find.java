package com.myreflectionthoughts.movieservice.contracts;

import reactor.core.publisher.Mono;

public interface Find<Res> {
    Mono<Res> findInfo(String movieInfoId);
}
