package com.pefonseca.library.api;

import com.pefonseca.library.api.model.Author;
import com.pefonseca.library.api.repository.AuthorRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class LibraryApiApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(LibraryApiApplication.class, args);
        AuthorRepository repository = context.getBean(AuthorRepository.class);
        exampleSaveRegister(repository);
    }

    public static void exampleSaveRegister(AuthorRepository repository) {
        Author author = new Author();
        author.setName("John Doe");
        author.setNationality("English");
        author.setBirthDate(LocalDate.of(1950, 1, 31));
        var savedAuthor = repository.save(author);

        System.out.println("Author saved: " + savedAuthor);
    }

}
