package com.pefonseca.library.api.validator;

import com.pefonseca.library.api.exceptions.DuplicateRecordException;
import com.pefonseca.library.api.model.Author;
import com.pefonseca.library.api.repository.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthorValidator {

    private final AuthorRepository authorRepository;

    public AuthorValidator(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void validate(Author author) {
        if(existAuthorRegistered(author)) {
            throw new DuplicateRecordException("Author already registered");
        }
    }

    private boolean existAuthorRegistered(Author author) {
        Optional<Author> authorExists = authorRepository.findByNameAndBirthDateAndNationality(author.getName(), author.getBirthDate(), author.getNationality());

        if(author.getId() == null) {
            return authorExists.isPresent();
        }

        return !author.getId().equals(authorExists.get().getId()) && authorExists.isPresent();
    }
}
