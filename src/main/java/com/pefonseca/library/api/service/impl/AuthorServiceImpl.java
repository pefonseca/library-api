package com.pefonseca.library.api.service.impl;

import com.pefonseca.library.api.exceptions.OperationNotPermitted;
import com.pefonseca.library.api.model.AuthUser;
import com.pefonseca.library.api.model.Author;
import com.pefonseca.library.api.repository.AuthorRepository;
import com.pefonseca.library.api.repository.BookRepository;
import com.pefonseca.library.api.security.SecurityService;
import com.pefonseca.library.api.service.AuthorService;
import com.pefonseca.library.api.validator.AuthorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
    private final SecurityService securityService;

    @Override
    public Author save(Author author) {
        authorValidator.validate(author);
        AuthUser user = securityService.findUserAuthenticated();
        author.setUser(user);
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
    public List<Author> findAllByExample(String name, String nationality) {
        var author = new Author();
        author.setName(name);
        author.setNationality(nationality);

        ExampleMatcher matcher = ExampleMatcher.matching()
                                               //.withIgnorePaths("id", "name", "nationality") ignora campos que não queira que sejá pesquisado.
                                               .withIgnoreNullValues() // ignora valroes nulos
                                               .withIgnoreCase() // ignora LOWER ou UPPER case
                                               .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); // qualquer parte do texto contenha essa string

        Example<Author> example = Example.of(author, matcher);

        return authorRepository.findAll(example);
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

