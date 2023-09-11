package com.devertelo.webflux.infrastructure;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BookRepository extends R2dbcRepository<Book, Long> {

    Flux<Book> findByName(String name);
}
