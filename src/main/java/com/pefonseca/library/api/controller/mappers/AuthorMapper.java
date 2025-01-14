package com.pefonseca.library.api.controller.mappers;

import com.pefonseca.library.api.controller.dto.AuthorDTO;
import com.pefonseca.library.api.model.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    Author toEntity(AuthorDTO dto);
    AuthorDTO toDTO(Author entity);

}
