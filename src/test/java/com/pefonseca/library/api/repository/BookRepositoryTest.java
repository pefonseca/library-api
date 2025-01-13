package com.pefonseca.library.api.repository;

import com.pefonseca.library.api.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void findForTitleTest() {
        List<Book> bookList = repository.findByTitle("");
        bookList.forEach(System.out::println);
    }

    @Test
    void findForIsbnTest() {
        List<Book> bookList = repository.findByIsbn("");
        bookList.forEach(System.out::println);
    }

    @Test
    void findForTitleAndPriceTest() {
        List<Book> bookList = repository.findByTitleAndPrice("", BigDecimal.valueOf(1));
        bookList.forEach(System.out::println);
    }

    @Test
    void findForTitleOrIsbnTest() {
        List<Book> bookList = repository.findByTitleOrIsbn("", "isbn");
        bookList.forEach(System.out::println);
    }

    @Test
    void findBooksWithQueryJPQL() {
        var result = repository.findAllBooksWithQuery();
        result.forEach(System.out::println);
    }
}
