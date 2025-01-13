package com.pefonseca.library.api.repository;

import com.pefonseca.library.api.model.Author;
import com.pefonseca.library.api.model.Book;
import com.pefonseca.library.api.model.GenderBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    List<Book> findByAuthor(Author author);
    List<Book> findByTitle(String title);
    List<Book> findByIsbn(String isbn);
    List<Book> findByTitleAndPrice(String title, BigDecimal price);
    List<Book> findByTitleOrIsbn(String title, String isbn);
    List<Book> findByPublicationDateBetween(LocalDate start, LocalDate end);

    @Query(value = "select b from Book as b order by b.title, b.price")
    List<Book> findAllBooksWithQuery();

    @Query(value = "select distinct b.title from Book b")
    List<String> findNamesDistinctBookTitles();

    @Query("""
        select b.title
        from Book b
        join b.author a
        where a.nationality = 'England'
        order by b.gender
    """)
    List<String> findNamesDistinctAuthors();

    @Query(value = "select b from Book b where b.gender = :genderBook order by :paramOrdination")
    List<Book> findByGender(@Param("genderBook") GenderBook genderBook,
                            @Param("paramOrdination") String paramOrdination);

    @Query(value = "select b from Book b where b.gender = ?1 order by ?2")
    List<Book> findByGenderAndOrdinationWithParamDistinct(GenderBook genderBook, String paramOrdination);
}
