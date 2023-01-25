package com.myreflectionthoughts.moviereviewservice.contracts;

import reactor.core.publisher.Mono;

public interface UpdateEntity<Req,Res> {
    Mono<Res> updateEntity(Req reqDTO);
}
