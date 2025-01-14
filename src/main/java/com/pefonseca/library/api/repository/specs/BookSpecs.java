package com.pefonseca.library.api.repository.specs;

import com.pefonseca.library.api.model.Book;
import com.pefonseca.library.api.model.GenderBook;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecs {

    public static Specification<Book> isbnEqual(String isbn) {
        return (root, query, cb) -> cb.equal(root.get("isbn"), isbn);
    }

    public static Specification<Book> titleLike(String title) {
        return (root, query, cb) -> cb.like(cb.upper(root.get("title")), "%" + title.toUpperCase() + "%");
    }

    public static Specification<Book> genderEqual(GenderBook gender) {
        return (root, query, cb) -> cb.equal(root.get("gender"), gender);
    }

    public static Specification<Book> publicationDate(Integer publicationDate) {
        return (root, query, cb) -> cb.equal(cb.function("to_char", String.class, root.get("publicationDate"), cb.literal("YYYY")), publicationDate.toString());
    }

    public static Specification<Book> nameAuthorLike(String nameAuthor) {
        return (root, query, cb) -> {
            //return cb.like(cb.upper(root.get("author").get("name")), "%" + nameAuthor.toUpperCase() + "%");

            Join<Object, Object> joinAuthor = root.join("author", JoinType.LEFT);
            return cb.like(cb.upper(joinAuthor.get("name")), "%" + nameAuthor.toUpperCase() + "%");
        };
    }
}
