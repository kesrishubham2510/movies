package com.myreflectionthoughts.moviereviewservice.contracts;

import reactor.core.publisher.Mono;

public interface SaveEntity<Req,Res> {
    Mono<Res> save(Req reqDTO);
}
