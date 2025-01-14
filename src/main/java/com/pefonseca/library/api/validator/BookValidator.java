package com.pefonseca.library.api.validator;

import com.pefonseca.library.api.exceptions.DuplicateRecordException;
import com.pefonseca.library.api.exceptions.InvalidPropertyException;
import com.pefonseca.library.api.model.Book;
import com.pefonseca.library.api.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookValidator {

    private final BookRepository bookRepository;

    private static final int YEAR_REQUIRED_PRICE = 2020;

    public void validate(Book book) {
        if(existsBookWithIsbn(book)) {
            throw new DuplicateRecordException("ISBN already exists");
        }

        if(isPriceRequired(book)) {
            throw new InvalidPropertyException("price", "Price must be not null");
        };
    }

    private boolean existsBookWithIsbn(Book book) {
        Optional<Book> bookExists = bookRepository.findByIsbn(book.getIsbn());

        if(book.getId() == null) {
            return bookExists.isPresent();
        }

        return bookExists.map(Book::getId).stream().anyMatch(id -> !id.equals(book.getId()));
    }

    private boolean isPriceRequired(Book book) {
        return book.getPrice() == null && book.getPublicationDate().getYear() >= YEAR_REQUIRED_PRICE;
    }
}
