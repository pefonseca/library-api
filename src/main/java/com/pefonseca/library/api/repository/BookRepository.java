package com.pefonseca.library.api.repository;

import com.pefonseca.library.api.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
}
