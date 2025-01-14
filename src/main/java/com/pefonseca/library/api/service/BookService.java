package com.pefonseca.library.api.service;

import com.pefonseca.library.api.model.Book;

import java.util.Optional;
import java.util.UUID;

public interface BookService {
    Book save(Book book);
    Optional<Book> findById(UUID id);
    void delete(Book book);
}
