package com.myreflectionthoughts.movieinfoservice.contracts;

import reactor.core.publisher.Mono;

public interface SaveEntity<Req,Res>{
      
      Mono<Res> save(Mono<Req> reqDTO);
}
