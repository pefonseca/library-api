package com.pefonseca.library.api.repository;

import com.pefonseca.library.api.model.Author;
import com.pefonseca.library.api.model.Book;
import com.pefonseca.library.api.model.GenderBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID>, JpaSpecificationExecutor<Book> {

    List<Book> findByAuthor(Author author);
    List<Book> findByTitle(String title);
    Optional<Book> findByIsbn(String isbn);
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

    @Modifying
    @Transactional
    @Query(value = "delete from Book where gender = ?1")
    void deleteByGender(GenderBook genderBook);

    @Modifying
    @Transactional
    @Query(value = "update Book set publicationDate = ?1") // update sem where kk
    void updateDatePublication(LocalDate publicationDate);

    boolean existsByAuthor(Author author);
}
