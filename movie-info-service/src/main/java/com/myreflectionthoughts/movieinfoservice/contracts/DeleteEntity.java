package com.myreflectionthoughts.movieinfoservice.contracts;

import reactor.core.publisher.Mono;

public interface DeleteEntity<Res>{
    Mono<Res> delete(String id);
}
