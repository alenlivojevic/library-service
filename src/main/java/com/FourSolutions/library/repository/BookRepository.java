package com.FourSolutions.library.repository;

import com.FourSolutions.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrderByTitleAsc(String queryAuthor, String queryTitle);
}
