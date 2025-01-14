package com.pefonseca.library.api.repository;

import com.pefonseca.library.api.model.Author;
import com.pefonseca.library.api.model.Book;
import com.pefonseca.library.api.model.GenderBook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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

//    @Test
//    void createBookTest() {
//        var author = authorRepository.findById(UUID.fromString("4660125a-3c13-4e81-8a00-cfffbd181b1c")).orElse(null);
//
//        Book book = new Book();
//        book.setIsbn("isbn");
//        book.setTitle("title");
//        book.setAuthor(author);
//        book.setPrice(BigDecimal.valueOf(1));
//        book.setGender(GenderBook.MISTERIO);
//        repository.save(book);
//    }
}
