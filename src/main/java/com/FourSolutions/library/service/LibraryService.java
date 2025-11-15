package com.FourSolutions.library.service;

import com.FourSolutions.library.dto.NewBookDto;
import com.FourSolutions.library.exception.LibraryException;
import com.FourSolutions.library.model.Book;
import com.FourSolutions.library.repository.BookRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LibraryService {
    BookRepository bookRepository;

    @Autowired
    private ObjectMapper mapper;


    @Transactional
    public ResponseEntity<String> addBook(NewBookDto newBookDto) {

        Book book = new Book();
        if(newBookDto.getAuthor() != null)
            book.setAuthor(newBookDto.getAuthor());
        else throw new LibraryException("Autor nije definiran!");
        if(newBookDto.getTitle() != null)
            book.setTitle(newBookDto.getTitle());
        else throw new LibraryException("Naslov nije definiran!");
        book.setAvailability(true);
        book.setBorrowedBy("N/A");

        bookRepository.save(book);

        return ResponseEntity.ok("Knjiga uspjesno dodana!");
    }

    public ResponseEntity<List<Book>> search(String query) {
        List<Book> bookList;
        if(query == null){
            throw new LibraryException("Parametar pretrage nije definiran!");
        } else {
            bookList = new ArrayList<>(searchByAuthorOrTitle(query));
        }
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(bookList);
    }

    private List<Book> searchByAuthorOrTitle(String query) {
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrderByTitleAsc(query, query);
    }

    @Transactional
    public ResponseEntity<String> borrow(Integer id, String person, String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Book book = bookRepository.findById(id.longValue())
                .orElseThrow(() -> new LibraryException("Knjiga ne postoji u bazi!"));
        book.setBorrowedBy(person);
        book.setAvailability(false);
        book.setDate(LocalDate.parse(date, formatter));
        bookRepository.save(book);
        return ResponseEntity.ok("Knjiga uspjesno posudena!");
    }

    @Transactional
    public ResponseEntity<String> returnBook(Integer id) {
        Book book = bookRepository.findById(id.longValue())
                .orElseThrow(() -> new LibraryException("Knjiga ne postoji u bazi!"));
        book.setBorrowedBy("N/A");
        book.setDate(null);
        book.setAvailability(true);
        bookRepository.save(book);
        return ResponseEntity.ok("Knjiga uspjesno vracena!");
    }

    @Transactional
    public ResponseEntity<String> deleteBook(Integer id) {
        bookRepository.deleteById(id.longValue());
        return ResponseEntity.ok("Knjiga uspjesno obrisana!");
    }

    public ResponseEntity<String> searchId(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new LibraryException("Knjiga ne postoji u bazi!"));
        String response;
        try{
            response = mapper.writeValueAsString(book);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(response);
    }

    public ResponseEntity<String> editBook(Book book) {

        bookRepository.save(book);

        return ResponseEntity.ok("Knjiga uspjesno uredena!");
    }
}
