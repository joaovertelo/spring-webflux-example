package com.devertelo.webflux.controller;

import com.devertelo.webflux.domain.BookService;
import com.devertelo.webflux.infrastructure.Book;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@AllArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService service;

    @PostMapping
    public Mono<BookDTO> create(@RequestBody Mono<BookDTO> bookDTO) {
        return bookDTO
                .map(this::toBook)
                .flatMap(service::create)
                .map(this::toBookDto);
    }

    @GetMapping
    public Flux<BookDTO> getAll() {
        return service.getAllBooks()
                .map(this::toBookDto);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<BookDTO>> getById(@PathVariable Long id) {
        return service.getBookById(id)
                .map(this::toBookDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<BookDTO>> update(@PathVariable Long id, @RequestBody Mono<BookDTO> bookDTO) {
        return bookDTO
                .map(this::toBook)
                .map(book -> {
                    book.setId(id);
                    return book;
                })
                .flatMap(service::update)
                .map(this::toBookDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    private BookDTO toBookDto(Book book) {
        return new BookDTO(book.getId(), book.getName(), book.getAuthor(), book.getCreatedAt());
    }

    private Book toBook(BookDTO dto) {
        return new Book(dto.id(), dto.name(), dto.author(), LocalDateTime.now());
    }

}
