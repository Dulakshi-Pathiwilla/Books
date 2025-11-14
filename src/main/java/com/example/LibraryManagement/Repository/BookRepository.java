package com.example.LibraryManagement.Repository;

import com.example.LibraryManagement.Entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;


public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findByPublicationYear(int publicationYear);
    void deleteByPublicationYear(int publicationYear);
}
