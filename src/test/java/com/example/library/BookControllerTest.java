package com.example.library;

import com.example.library.controller.BookController;
import com.example.library.exception.BookNotAvailableException;
import com.example.library.model.Book;
import com.example.library.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    public void getAllBooksTest() throws Exception {
        // Create some test data
        Book book1 = new Book();
        book1.setIsbn("123");
        book1.setTitle("Book 1");
        book1.setAuthor("Author 1");
        book1.setPublisher("Publisher 1");
        book1.setStatus(Book.Status.AVAILABLE);

        Book book2 = new Book();
        book2.setIsbn("456");
        book2.setTitle("Book 2");
        book2.setAuthor("Author 2");
        book2.setPublisher("Publisher 2");
        book2.setStatus(Book.Status.BORROWED);

        // Create a page object
        Page<Book> page = new PageImpl<>(Arrays.asList(book1, book2));

        // Define the behavior of the bookService
        when(bookService.getAllBooks(null, PageRequest.of(0, 20))).thenReturn(page);

        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                // Expect a 200 OK status
                .andExpect(status().isOk())
                // Expect the first book in the response to have an ISBN of "123"
                .andExpect(jsonPath("$.content[0].isbn", is("123")))
                // Expect the second book in the response to have an ISBN of "456"
                .andExpect(jsonPath("$.content[1].isbn", is("456")));
    }

    @Test
    public void searchBooksTest() throws Exception {
        // Create some test data
        Book book1 = new Book();
        book1.setIsbn("123");
        book1.setTitle("Book 1");
        book1.setAuthor("Author 1");
        book1.setPublisher("Publisher 1");
        book1.setStatus(Book.Status.AVAILABLE);

        Book book2 = new Book();
        book2.setIsbn("456");
        book2.setTitle("Book 2");
        book2.setAuthor("Author 2");
        book2.setPublisher("Publisher 2");
        book2.setStatus(Book.Status.BORROWED);

        // Create a page object
        Page<Book> page = new PageImpl<>(Arrays.asList(book1));

        // Define the behavior of the bookService
        when(bookService.searchBooks("Book 1", null, null, PageRequest.of(0, 20))).thenReturn(page);

        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/books/search?title=Book 1"))
                // Expect a 200 OK status
                .andExpect(status().isOk())
                // Expect the first book in the response to have an ISBN of "123"
                .andExpect(jsonPath("$.content[0].isbn", is("123")))
                // Expect the response to only contain one book
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void addBookTest() throws Exception {
        // Create a new book
        Book book = new Book();
        book.setIsbn("123");
        book.setTitle("Book 1");
        book.setAuthor("Author 1");
        book.setPublisher("Publisher 1");
        book.setStatus(Book.Status.AVAILABLE);

        // Define the behavior of the bookService
        when(bookService.addBook(book)).thenReturn(book);

        // Perform the POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/books")
                .contentType("application/json")
                .content("{\"isbn\":\"123\",\"title\":\"Book 1\",\"author\":\"Author 1\",\"publisher\":\"Publisher 1\",\"status\":\"AVAILABLE\"}"))
                // Expect a 200 OK status
                .andExpect(status().isOk())
                // Expect the response to have an ISBN of "123"
                .andExpect(jsonPath("$.isbn", is("123")));
    }

    @Test
    public void updateBookTest() throws Exception {
        // Create a new book
        Book book = new Book();
        book.setIsbn("123");
        book.setTitle("Book 1");
        book.setAuthor("Author 1");
        book.setPublisher("Publisher 1");
        book.setStatus(Book.Status.AVAILABLE);

        // Define the behavior of the bookService
        when(bookService.updateBook(book)).thenReturn(book);

        // Perform the PUT request
        mockMvc.perform(MockMvcRequestBuilders.put("/books")
                .contentType("application/json")
                .content("{\"isbn\":\"123\",\"title\":\"Book 1\",\"author\":\"Author 1\",\"publisher\":\"Publisher 1\",\"status\":\"AVAILABLE\"}"))
                // Expect a 200 OK status
                .andExpect(status().isOk())
                // Expect the response to have an ISBN of "123"
                .andExpect(jsonPath("$.isbn", is("123")));
    }

    @Test
    public void deleteBookTest() throws Exception {
        // Perform the DELETE request
        mockMvc.perform(MockMvcRequestBuilders.delete("/books/123"))
                // Expect a 204 No Content status
                .andExpect(status().isNoContent());
    }

    @Test
    public void borrowBookTest() throws Exception {
        // Create a new book
        Book book = new Book();
        book.setIsbn("123");
        book.setTitle("Book 1");
        book.setAuthor("Author 1");
        book.setPublisher("Publisher 1");
        book.setStatus(Book.Status.BORROWED);

        // Define the behavior of the bookService
        when(bookService.borrowBook("123")).thenReturn(book);

        // Perform the POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/books/123/borrow"))
                // Expect a 200 OK status
                .andExpect(status().isOk())
                // Expect the response to have an ISBN of "123"
                .andExpect(jsonPath("$.isbn", is("123")));
    }

    @Test
    public void returnBookTest() throws Exception {
        // Create a new book
        Book book = new Book();
        book.setIsbn("123");
        book.setTitle("Book 1");
        book.setAuthor("Author 1");
        book.setPublisher("Publisher 1");
        book.setStatus(Book.Status.AVAILABLE);

        // Define the behavior of the bookService
        when(bookService.returnBook("123")).thenReturn(book);

        // Perform the POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/books/123/return"))
                // Expect a 200 OK status
                .andExpect(status().isOk())
                // Expect the response to have an ISBN of "123"
                .andExpect(jsonPath("$.isbn", is("123")));
    }

    @Test
    public void reserveBookTest() throws Exception {
        // Create a new book
        Book book = new Book();
        book.setIsbn("123");
        book.setTitle("Book 1");
        book.setAuthor("Author 1");
        book.setPublisher("Publisher 1");
        book.setStatus(Book.Status.RESERVED);

        // Define the behavior of the bookService
        when(bookService.reserveBook("123")).thenReturn(book);

        // Perform the POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/books/123/reserve"))
                // Expect a 200 OK status
                .andExpect(status().isOk())
                // Expect the response to have an ISBN of "123"
                .andExpect(jsonPath("$.isbn", is("123")));
    }

    @Test
    public void borrowBookNotAvailableTest() throws Exception {
        // Define the behavior of the bookService
        when(bookService.borrowBook("123")).thenThrow(new BookNotAvailableException("Book not available"));

        // Perform the POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/books/123/borrow"))
                // Expect a 400 Bad Request status
                .andExpect(status().isBadRequest())
                // Expect the response to have an error message
                .andExpect(jsonPath("$.message", is("Book not available")));
    }

    @Test
    public void returnBookNotAvailableTest() throws Exception {
        // Define the behavior of the bookService
        when(bookService.returnBook("123")).thenThrow(new BookNotAvailableException("Book not available"));

        // Perform the POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/books/123/return"))
                // Expect a 400 Bad Request status
                .andExpect(status().isBadRequest())
                // Expect the response to have an error message
                .andExpect(jsonPath("$.message", is("Book not available")));
    }
}