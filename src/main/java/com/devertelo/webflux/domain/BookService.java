package com.devertelo.webflux.domain;

import com.devertelo.webflux.infrastructure.Book;
import com.devertelo.webflux.infrastructure.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class BookService {

    private final BookRepository repository;

    public Flux<Book> getAllBooks() {
        return repository.findAll();
    }

    public Mono<Book> getBookById(Long id) {
        return repository.findById(id);
    }

    public Flux<Book> findByName(String name) {
        return repository.findByName(name);
    }

    public Mono<Void> delete(Long id) {
        return repository.deleteById(id);
    }

    public Mono<Book> create(Book book) {
        return repository.save(book);
    }

    public Mono<Book> update(Book book) {
        return repository.findById(book.getId())
                .map(entity -> fillBook(entity, book))
                .flatMap(repository::save)
                .switchIfEmpty(Mono.empty());
    }

    private Book fillBook(Book book, Book bookRequest) {
        book.setAuthor(bookRequest.getAuthor());
        book.setName(bookRequest.getName());
        return book;
    }
}
