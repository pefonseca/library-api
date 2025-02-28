package com.pefonseca.library.api.controller;

import com.pefonseca.library.api.controller.dto.BookRegistrationDTO;
import com.pefonseca.library.api.controller.dto.ResultFindBookDTO;
import com.pefonseca.library.api.controller.mappers.BookMapper;
import com.pefonseca.library.api.model.Author;
import com.pefonseca.library.api.model.Book;
import com.pefonseca.library.api.model.GenderBook;
import com.pefonseca.library.api.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
import java.util.UUID;

@RestController
@RequestMapping(value = "/books")
@RequiredArgsConstructor
public class BookController implements GenericController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @PostMapping
    @PreAuthorize(value = "hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Void> save(@Valid @RequestBody BookRegistrationDTO bookRegistrationDTO) {
        Book book = bookMapper.toEntity(bookRegistrationDTO);
        bookService.save(book);
        URI url = generateLocationUri(book.getId());
        return ResponseEntity.created(url).build();
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize(value = "hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<ResultFindBookDTO> findById(@PathVariable(value = "id") String id) {
        return bookService.findById(UUID.fromString(id))
                .map(book -> {
                    var dto = bookMapper.toDTO(book);
                    return ResponseEntity.ok().body(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") String id) {
        return bookService.findById(UUID.fromString(id))
                .map(book -> {
                    bookService.delete(book);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize(value = "hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Page<ResultFindBookDTO>> findAll(@RequestParam(value = "isbn", required = false) String isbn,
                                                           @RequestParam(value = "title", required = false) String title,
                                                           @RequestParam(value = "nameAuthor", required = false) String nameAuthor,
                                                           @RequestParam(value = "gender", required = false) GenderBook gender,
                                                           @RequestParam(value = "publicationDate", required = false) Integer publicationDate,
                                                           @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                           @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Page<Book> resultBooks = bookService.findBooks(isbn, title, nameAuthor, gender, publicationDate, page, size);
        Page<ResultFindBookDTO> resultListBooks = resultBooks.map(bookMapper::toDTO);

        return ResponseEntity.ok(resultListBooks);
    }

    @PutMapping("/{id}")
    @PreAuthorize(value = "hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Object> update(@Valid @RequestBody BookRegistrationDTO bookRegistrationDTO,
                                         @PathVariable(value = "id") String id) {
        try {
            return bookService.findById(UUID.fromString(id))
                    .map(book -> {
                        book.setPublicationDate(bookRegistrationDTO.publicationDate());
                        book.setIsbn(bookRegistrationDTO.isbn());
                        book.setPrice(bookRegistrationDTO.price());
                        book.setGender(bookRegistrationDTO.gender());
                        book.setTitle(bookRegistrationDTO.title());

                        Author author = bookMapper.getAuthorById(bookRegistrationDTO.idAuthor());
                        book.setAuthor(author);

                        bookService.update(book);

                        return ResponseEntity.noContent().build();
                    }).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado: " + e.getMessage());
        }
    }
}
