package com.pefonseca.library.api.repository;

import com.pefonseca.library.api.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository repository;

    @Test
    public void saveTest() {
        Author author = new Author();
        author.setName("test");
        author.setNationality("test");
        author.setBirthDate(LocalDate.now());

        var authorSave = repository.save(author);
        System.out.println("Author saved: " + authorSave);
    }

    @Test
    public void updateTest() {
        var id = UUID.fromString("b5f77ee1-04af-4ffc-9741-2b46c22d1a73");
        Optional<Author> authorOptional = repository.findById(id);

        if(authorOptional.isPresent()) {
            Author author = authorOptional.get();
            System.out.println("Author for updated: " + author);
            System.out.println(author);

            author.setBirthDate(LocalDate.of(1960,1,30));

            repository.save(author);
        }
    }

    @Test
    public void findAllTest() {
        List<Author> authorList = repository.findAll();
        authorList.forEach(System.out::println);
    }

    @Test
    public void countTest() {
        long count = repository.count();
        System.out.println("Count: " + count);
    }

//    @Test
//    public void deleteTest() {
//        var id = UUID.fromString("c750f09a-a5ae-4fc3-a7ea-8ec8112ee703");
//        repository.deleteById(id);
//    }
//
//    @Test
//    public void deleteForObject() {
//        var id = UUID.fromString("be7a8db2-f2cf-4ccb-9495-d7883247c4da");
//        var maria = repository.findById(id).get();
//        repository.delete(maria);
//    }
}