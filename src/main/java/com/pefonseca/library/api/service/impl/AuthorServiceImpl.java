package com.pefonseca.library.api.service.impl;

import com.pefonseca.library.api.exceptions.OperationNotPermitted;
import com.pefonseca.library.api.model.Author;
import com.pefonseca.library.api.repository.AuthorRepository;
import com.pefonseca.library.api.repository.BookRepository;
import com.pefonseca.library.api.service.AuthorService;
import com.pefonseca.library.api.validator.AuthorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorValidator authorValidator;
    private final BookRepository bookRepository;

    @Override
    public Author save(Author author) {
        authorValidator.validate(author);
        return authorRepository.save(author);
    }

    @Override
    public Optional<Author> findById(UUID id) {
        return authorRepository.findById(id);
    }

    @Override
    public void delete(Author author) {
        if(haveBook(author)) {
            throw new OperationNotPermitted("Não é permitido excluir um Autor que possui livro cadastrado.");
        }
        authorRepository.delete(author);
    }

    @Override
    public List<Author> findAll(String name, String nationality) {

        if(name != null && nationality != null) {
            return authorRepository.findByNameAndNationality(name, nationality);
        }

        if(name != null) {
            return authorRepository.findByName(name);
        }

        if(nationality != null) {
            return authorRepository.findByNationality(nationality);
        }

        return authorRepository.findAll();
    }

    @Override
    public void update(Author author) {
        if(author.getId() == null) {
            throw new IllegalArgumentException("Para atualizar é necessário que o autor esteja salvo na base.");
        }
        authorValidator.validate(author);
        authorRepository.save(author);
    }

    public boolean haveBook(Author author) {
        return bookRepository.existsByAuthor(author);
    }
}
