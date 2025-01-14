package com.pefonseca.library.api.controller.dto;

import com.pefonseca.library.api.model.GenderBook;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ResultFindBookDTO(UUID id,
                                String isbn,
                                String title,
                                LocalDate publicationDate,
                                GenderBook gender,
                                BigDecimal price,
                                AuthorDTO author
) {
}
