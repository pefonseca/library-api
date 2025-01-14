package com.pefonseca.library.api.service;

import com.pefonseca.library.api.model.Book;
import com.pefonseca.library.api.model.GenderBook;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookService {
    Book save(Book book);
    Optional<Book> findById(UUID id);
    void delete(Book book);
    List<Book> findBooks(String isbn,
                         String title,
                         String nameAuthor,
                         GenderBook gender,
                         Integer publicationDate);

    void update(Book book);
}
