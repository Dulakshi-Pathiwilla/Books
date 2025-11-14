package com.example.LibraryManagement.Service;

import com.example.LibraryManagement.DTO.BookDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {

    BookDTO addNewBook(BookDTO bookDTO);
    List<BookDTO> getAllBooks();
    BookDTO findBookById(String id);
    BookDTO updateExistingBook(String id, BookDTO bookDTO);
    void deleteBook(String id);
    List<BookDTO> findByPublicationYear(int publicationYear);
    String findGenre(String id);
    void deleteByYear(int publicationYear);
}
