package com.FourSolutions.library.controller;

import com.FourSolutions.library.dto.NewBookDto;
import com.FourSolutions.library.model.Book;
import com.FourSolutions.library.service.LibraryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class LibraryController {
    LibraryService libraryService;

    @GetMapping("/test")
    public ResponseEntity<String> testApp() {
        return ResponseEntity.ok("Bok Care");
    }

    @PostMapping("/addBook")
    public ResponseEntity<String> addBook(@RequestBody NewBookDto newBookDto){
        return libraryService.addBook(newBookDto);
    }

    @PostMapping("/editBook")
    public ResponseEntity<String> editBook(@RequestBody Book book){
        return libraryService.editBook(book);
    }

    @GetMapping("/search")
    public ResponseEntity<String> search(@RequestParam(required = false) String query){
        if(query == null || query.length() < 2) return ResponseEntity.ok("");
        return libraryService.search(query);
    }

    @GetMapping("/searchId")
    public ResponseEntity<String> searchId(@RequestParam(required = true) Long id){
        return libraryService.searchId(id);
    }

    @GetMapping("/borrow")
    public ResponseEntity<String> borrow(@RequestParam Integer id,
                                         @RequestParam String person){
        return libraryService.borrow(id, person);
    }

    @GetMapping("/returnBook")
    public ResponseEntity<String> returnBook(@RequestParam Integer id){
        return libraryService.returnBook(id);
    }

    @GetMapping("/deleteBook")
    public ResponseEntity<String> deleteBook(@RequestParam Integer id){
        return libraryService.deleteBook(id);
    }
}
