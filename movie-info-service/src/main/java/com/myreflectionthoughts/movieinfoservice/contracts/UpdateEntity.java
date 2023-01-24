package com.myreflectionthoughts.movieinfoservice.contracts;

import reactor.core.publisher.Mono;

public interface UpdateEntity<Req,Res> {
    Mono<Res> update(Mono<Req> updateReqDTO);
}
