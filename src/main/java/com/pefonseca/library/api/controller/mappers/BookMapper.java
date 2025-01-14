package com.pefonseca.library.api.controller.mappers;

import com.pefonseca.library.api.controller.dto.BookRegistrationDTO;
import com.pefonseca.library.api.controller.dto.ResultFindBookDTO;
import com.pefonseca.library.api.model.Author;
import com.pefonseca.library.api.model.Book;
import com.pefonseca.library.api.repository.AuthorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = {AuthorMapper.class})
public abstract class BookMapper {

    @Autowired
    private AuthorRepository repository;

    @Mapping(target = "author", expression = "java(getAuthorById(registrationDTO.idAuthor()))")
    public abstract Book toEntity(BookRegistrationDTO registrationDTO);

    public Author getAuthorById(UUID authorId) {
        return repository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Author not found with ID: " + authorId));
    }

    public abstract ResultFindBookDTO toDTO(Book book);

    @Mapping(target = "author", source = "author")
    public abstract ResultFindBookDTO toDTOWithAuthor(Book book);
}