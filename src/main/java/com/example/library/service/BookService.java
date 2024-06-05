// BookService.java
package com.example.library.service;

import com.example.library.exception.BookNotAvailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.library.repository.BookRepository;
import com.example.library.model.Book;
import com.example.library.exception.BookNotFoundException;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book addBook(Book book) {
        book.setStatus(Book.Status.AVAILABLE);
        return bookRepository.save(book);
    }

    public Page<Book> getAllBooks(Book.Status status, Pageable pageable) {
        if (status == null) {
            return bookRepository.findAll(pageable);
        } else {
            return bookRepository.findByStatus(status, pageable);
        }
    }

    public Book getBookByIsbn(String isbn) {
        return bookRepository.findById(isbn)
                .orElseThrow(() -> new BookNotFoundException("Book not found with isbn : " + isbn));
    }

    public Page<Book> searchBooks(Book.Status status, String keyword, Pageable pageable) {
        return bookRepository.findByStatusAndTitleContainingOrStatusAndAuthorContainingOrStatusAndIsbnContaining(
                status, keyword, status, keyword, status, keyword, pageable);
    }

    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(String isbn) {
        bookRepository.deleteById(isbn);
    }

    public Book borrowBook(String isbn) {
        Book book = getBookByIsbn(isbn);
        if (book.getStatus() == Book.Status.BORROWED) {
            throw new BookNotAvailableException("Book is already borrowed");
        }
        book.setStatus(Book.Status.BORROWED);
        return bookRepository.save(book);
    }

    public Book returnBook(String isbn) {
        Book book = getBookByIsbn(isbn);
        if (book.getStatus() == Book.Status.AVAILABLE) {
            throw new BookNotAvailableException("Book is not borrowed");
        }
        book.setStatus(Book.Status.AVAILABLE);
        return bookRepository.save(book);
    }

    public Book reserveBook(String isbn) {
        Book book = getBookByIsbn(isbn);
        if (book.getStatus() != Book.Status.AVAILABLE) {
            throw new BookNotAvailableException("Book is not available for reservation");
        }
        book.setStatus(Book.Status.RESERVED);
        return bookRepository.save(book);
    }

    public Book cancelReservation(String isbn) {
        Book book = getBookByIsbn(isbn);
        if (book.getStatus() != Book.Status.RESERVED) {
            throw new BookNotAvailableException("Book is not reserved");
        }
        book.setStatus(Book.Status.AVAILABLE);
        return bookRepository.save(book);
    }

    public Page<Book> searchBooks(String title, String author, String isbn, Pageable pageable) {
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrIsbnContainingIgnoreCase(
                title, author, isbn, pageable);
    }
}