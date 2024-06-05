// BookController.java
package com.example.library.controller;

import com.example.library.exception.BookNotAvailableException;
import com.example.library.exception.BookNotFoundException;
import com.example.library.model.Book;
import com.example.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public Page<Book> getAllBooks(@RequestParam(required = false) Book.Status status, Pageable pageable) {
        return bookService.getAllBooks(status, pageable);
    }

    @GetMapping("/search")
    public Page<Book> searchBooks(@RequestParam(required = false) String title,
                                  @RequestParam(required = false) String author,
                                  @RequestParam(required = false) String isbn,
                                  Pageable pageable) {
        return bookService.searchBooks(title, author, isbn, pageable);
    }

    @PostMapping
    public Book addBook(@Valid @RequestBody Book book, BindingResult result) {
        if (result.hasErrors()) {
            throw new IllegalArgumentException("Invalid book data");
        }
        return bookService.addBook(book);
    }

    @PutMapping
    public Book updateBook(@Valid @RequestBody Book book, BindingResult result) {
        if (result.hasErrors()) {
            throw new IllegalArgumentException("Invalid book data");
        }
        return bookService.updateBook(book);
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> deleteBook(@PathVariable String isbn) {
        bookService.deleteBook(isbn);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{isbn}/borrow")
    public Book borrowBook(@PathVariable String isbn) {
        return bookService.borrowBook(isbn);
    }

    @PostMapping("/{isbn}/return")
    public Book returnBook(@PathVariable String isbn) {
        return bookService.returnBook(isbn);
    }

    @PostMapping("/{isbn}/reserve")
    public Book reserveBook(@PathVariable String isbn) {
        return bookService.reserveBook(isbn);
    }

    @PostMapping("/{isbn}/cancel-reservation")
    public Book cancelReservation(@PathVariable String isbn) {
        return bookService.cancelReservation(isbn);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleBookNotFoundException(BookNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return response;
    }

    @ExceptionHandler(BookNotAvailableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBookNotAvailableException(BookNotAvailableException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return response;
    }
}