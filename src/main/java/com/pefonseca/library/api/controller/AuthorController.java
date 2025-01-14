package com.pefonseca.library.api.controller;

import com.pefonseca.library.api.controller.dto.AuthorDTO;
import com.pefonseca.library.api.controller.dto.ErrorResponse;
import com.pefonseca.library.api.exceptions.DuplicateRecordException;
import com.pefonseca.library.api.exceptions.OperationNotPermitted;
import com.pefonseca.library.api.model.Author;
import com.pefonseca.library.api.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody AuthorDTO authorDTO) {
        try {
            Author authorEntity = authorDTO.mapperToAuthor();
            authorService.save(authorEntity);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(authorEntity.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        } catch (DuplicateRecordException e) {
            var errorDTO = ErrorResponse.responseConflict(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AuthorDTO> findById(@PathVariable(value = "id") String id) {
        var authorId = UUID.fromString(id);
        Optional<Author> authorOptional = authorService.findById(authorId);
        if (authorOptional.isPresent()) {
            Author authorEntity = authorOptional.get();
            AuthorDTO dto = new AuthorDTO(authorEntity.getId(),
                                          authorEntity.getName(),
                                          authorEntity.getBirthDate(),
                                          authorEntity.getNationality());

            return ResponseEntity.ok().body(dto);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") String id) {
        try {
            var authorId = UUID.fromString(id);
            Optional<Author> authorOptional = authorService.findById(authorId);
            if (authorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            authorService.delete(authorOptional.get());
            return ResponseEntity.noContent().build();
        } catch (OperationNotPermitted e) {
            var errorDTO = ErrorResponse.responseConflict(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> findAll(@RequestParam(value = "name", required = false) String name,
                                                   @RequestParam(value = "nationality", required = false) String nationality) {

        var result = authorService.findAll(name, nationality)
                .stream()
                .map(author ->
                        new AuthorDTO(author.getId(),
                                author.getName(),
                                author.getBirthDate(),
                                author.getNationality())
                ).toList();

        return ResponseEntity.ok(result);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") String id,
                                       @RequestBody AuthorDTO authorDTO) {
        try {
            var authorId = UUID.fromString(id);
            Optional<Author> authorOptional = authorService.findById(authorId);
            if (authorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            var authorEntity = authorOptional.get();
            authorEntity.setName(authorDTO.name());
            authorEntity.setNationality(authorDTO.nationality());
            authorEntity.setBirthDate(authorDTO.birthDate());

            authorService.update(authorEntity);

            return ResponseEntity.noContent().build();
        } catch (DuplicateRecordException e) {
            var errorDTO = ErrorResponse.responseConflict(e.getMessage());
            return ResponseEntity.status(errorDTO.status()).body(errorDTO);
        }
    }
}