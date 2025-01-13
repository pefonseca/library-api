package com.pefonseca.library.api.repository;

import com.pefonseca.library.api.model.Author;
import com.pefonseca.library.api.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository repository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void listBookByAuthor() {
        Author author = new Author();
        author.setName("John Doe");
        author.setBirthDate(LocalDate.now());
        author.setNationality("England");
        author.setBooks(new ArrayList<>());

        author = repository.save(author);

        List<Book> bookList = bookRepository.findByAuthor(author);
        author.setBooks(bookList);

        author.getBooks().forEach(System.out::println);
    }
}