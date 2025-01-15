package com.pefonseca.library.api.controller;

import com.pefonseca.library.api.controller.dto.AuthorDTO;
import com.pefonseca.library.api.controller.mappers.AuthorMapper;
import com.pefonseca.library.api.model.Author;
import com.pefonseca.library.api.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/authors")
@RequiredArgsConstructor
public class AuthorController implements GenericController{

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> save(@Valid @RequestBody AuthorDTO authorDTO) {
        Author authorEntity = authorMapper.toEntity(authorDTO);
        authorService.save(authorEntity);

        URI location = generateLocationUri(authorEntity.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize(value = "hasAnyRole('OPERADOR','GERENTE')")
    public ResponseEntity<AuthorDTO> findById(@PathVariable(value = "id") String id) {
        var authorId = UUID.fromString(id);

        return authorService.findById(authorId).map(author -> {
            AuthorDTO dto = authorMapper.toDTO(author);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> delete(@PathVariable(value = "id") String id) {
        var authorId = UUID.fromString(id);
        Optional<Author> authorOptional = authorService.findById(authorId);
        if (authorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        authorService.delete(authorOptional.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize(value = "hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<List<AuthorDTO>> findAll(@RequestParam(value = "name", required = false) String name,
                                                   @RequestParam(value = "nationality", required = false) String nationality) {

        var resultList = authorService.findAllByExample(name, nationality).stream()
                                                                      .map(authorMapper::toDTO)
                                                                      .toList();

        return ResponseEntity.ok(resultList);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> update(@PathVariable("id") String id,
                                       @Valid @RequestBody AuthorDTO authorDTO) {
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
    }
}