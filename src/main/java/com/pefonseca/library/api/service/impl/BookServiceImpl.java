package com.pefonseca.library.api.service.impl;

import com.pefonseca.library.api.model.AuthUser;
import com.pefonseca.library.api.model.Book;
import com.pefonseca.library.api.model.GenderBook;
import com.pefonseca.library.api.repository.BookRepository;
import com.pefonseca.library.api.security.SecurityService;
import com.pefonseca.library.api.service.BookService;
import com.pefonseca.library.api.validator.BookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.pefonseca.library.api.repository.specs.BookSpecs.genderEqual;
import static com.pefonseca.library.api.repository.specs.BookSpecs.isbnEqual;
import static com.pefonseca.library.api.repository.specs.BookSpecs.nameAuthorLike;
import static com.pefonseca.library.api.repository.specs.BookSpecs.publicationDate;
import static com.pefonseca.library.api.repository.specs.BookSpecs.titleLike;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;
    private final BookValidator validator;
    private final SecurityService securityService;

    @Override
    public Book save(Book book) {
        validator.validate(book);
        AuthUser user = securityService.findUserAuthenticated();
        book.setUser(user);
        return repository.save(book);
    }

    @Override
    public Optional<Book> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public void delete(Book book) {
        repository.delete(book);
    }

    @Override
    public Page<Book> findBooks(String isbn, String title, String nameAuthor, GenderBook gender, Integer publicationDate, Integer page, Integer pageLimit) {
        Specification<Book> specs = Specification.where((root, query, cb) -> cb.conjunction());

        if(isbn != null) {
            specs = specs.and(isbnEqual(isbn));
        }

        if(title != null) {
            specs = specs.and(titleLike(title));
        }

        if(gender != null) {
            specs = specs.and(genderEqual(gender));
        }

        if(publicationDate != null) {
            specs = specs.and(publicationDate(publicationDate));
        }

        if(nameAuthor != null) {
            specs = specs.and(nameAuthorLike(nameAuthor));
        }

        Pageable pageRequest = PageRequest.of(page, pageLimit);

        return repository.findAll(specs, pageRequest);
    }

    @Override
    public void update(Book book) {
        if(book.getId() == null) {
            throw new IllegalArgumentException("Para atualizar é necessário que o livro já esteja salvo na base de dados.");
        }

        validator.validate(book);
        repository.save(book);
    }
}
