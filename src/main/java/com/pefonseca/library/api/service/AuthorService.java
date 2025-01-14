package com.pefonseca.library.api.service;

import com.pefonseca.library.api.model.Author;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorService {

    Author save(Author author);
    Optional<Author> findById(UUID id);
    void delete(Author author);
    List<Author> findAll(String name, String nationality);
    void update(Author author);
}
