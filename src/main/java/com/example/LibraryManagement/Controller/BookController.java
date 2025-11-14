package com.example.LibraryManagement.Controller;

import com.example.LibraryManagement.DTO.BookDTO;
import com.example.LibraryManagement.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/addBook")
    public BookDTO addNewBook(@RequestBody BookDTO bookDTO){
        return bookService.addNewBook(bookDTO);
    }

    @GetMapping("/getAll")
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/findById/{id}")
    public BookDTO searchById(@PathVariable("id") String id){
        return bookService.findBookById(id);
    }

    @PutMapping("/updateBooks/{id}")
    public BookDTO update(@PathVariable("id") String id,@RequestBody BookDTO bookDTO){
        return bookService.updateExistingBook(id,bookDTO);
    }

    @DeleteMapping("delete/{id}")
    public String deleteBook(@PathVariable("id") String id){
        bookService.deleteBook(id);
        return "Book is deleted";
    }

    @GetMapping("/year/{publicationYear}")
    public List<BookDTO> searchByYear(@PathVariable("publicationYear") int publicationYear){
        return bookService.findByPublicationYear(publicationYear);
    }

    @GetMapping("genre/{id}")
    public String getGenre(@PathVariable("id") String id){
        return bookService.findGenre(id);
    }

    @DeleteMapping("/deleteYear/{year}")
    public void deleteByYear(@PathVariable("year") int year){
        bookService.deleteByYear(year);
    }
}
