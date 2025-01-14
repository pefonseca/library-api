package com.pefonseca.library.api.controller;

import com.pefonseca.library.api.controller.dto.BookRegistrationDTO;
import com.pefonseca.library.api.controller.dto.ResultFindBookDTO;
import com.pefonseca.library.api.controller.mappers.BookMapper;
import com.pefonseca.library.api.model.Book;
import com.pefonseca.library.api.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping(value = "/books")
@RequiredArgsConstructor
public class BookController implements GenericController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody BookRegistrationDTO bookRegistrationDTO) {
        Book book = bookMapper.toEntity(bookRegistrationDTO);
        bookService.save(book);
        URI url = generateLocationUri(book.getId());
        return ResponseEntity.created(url).build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResultFindBookDTO> findById(@PathVariable(value = "id") String id) {
        return bookService.findById(UUID.fromString(id))
                .map(book -> {
                    var dto = bookMapper.toDTO(book);
                    return ResponseEntity.ok().body(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") String id) {
        return bookService.findById(UUID.fromString(id))
                .map(book -> {
                    bookService.delete(book);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
