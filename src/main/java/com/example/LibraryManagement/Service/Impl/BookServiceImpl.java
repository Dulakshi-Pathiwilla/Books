package com.example.LibraryManagement.Service.Impl;

import com.example.LibraryManagement.DTO.BookDTO;
import com.example.LibraryManagement.Entity.Book;
import com.example.LibraryManagement.Repository.BookRepository;
import com.example.LibraryManagement.Service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BookDTO addNewBook(BookDTO bookDTO){
        Book book = modelMapper.map(bookDTO, Book.class);
        Book saved = bookRepository.save(book);
        return modelMapper.map(saved, BookDTO.class);
    }

    @Override
    public List<BookDTO> getAllBooks(){
        return bookRepository.findAll().stream().map(book -> modelMapper.map(book,BookDTO.class)).collect(Collectors.toList());
    }

    @Override
    public BookDTO findBookById(String id){
        return bookRepository.findById(id).map(book -> modelMapper.map(book, BookDTO.class)) .orElse(null);
    }

    @Override
    public BookDTO updateExistingBook(String id , BookDTO bookDTO){
       BookDTO existingBook = findBookById(id);
       Book book = modelMapper.map(bookDTO, Book.class);
       Book updated = bookRepository.save(book);
       return modelMapper.map(updated,BookDTO.class);
    }

    @Override
    public void deleteBook(String id){
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDTO> findByPublicationYear(int publicationYear){
      return bookRepository.findByPublicationYear(publicationYear).stream().map(book -> modelMapper.map(book,BookDTO.class)).collect(Collectors.toList());
    }

    @Override
    public String findGenre(String id){
        BookDTO bookDTO = findBookById(id);
        return bookDTO.getGenre();
    }
    @Override
    public void deleteByYear(int publicationYear){
       bookRepository.deleteByPublicationYear(publicationYear);
    }
}
