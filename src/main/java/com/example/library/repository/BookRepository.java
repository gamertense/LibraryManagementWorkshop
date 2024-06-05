// BookRepository.java
package com.example.library.repository;

import com.example.library.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {

    Page<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrIsbnContainingIgnoreCase(
            String title, String author, String isbn, Pageable pageable);
    Page<Book> findByStatus(Book.Status status, Pageable pageable);
    Page<Book> findByStatusAndTitleContainingOrStatusAndAuthorContainingOrStatusAndIsbnContaining(
            Book.Status status1, String title, Book.Status status2, String author, Book.Status status3, String isbn, Pageable pageable);
}