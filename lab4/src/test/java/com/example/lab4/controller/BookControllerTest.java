package com.example.lab4.controller;

import com.example.lab4.model.Book;
import com.example.lab4.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllBooks() throws Exception {
        Book book1 = new Book(1L, "Project Aiter. Finding yourself", "Sivov Semyon");
        Book book2 = new Book(2L, "Project Aiter. The price of life", "Sivo");
        List<Book> books = Arrays.asList(book1, book2);
        when(bookService.findAll()).thenReturn(books);

        // Вызов метода и проверка результата
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(books)));
    }

    @Test
    public void testGetBookById() throws Exception {
        Book book = new Book(1L, "Project Aiter. Finding yourself", "Sivov Semyon");
        when(bookService.findById(1L)).thenReturn(Optional.of(book));

        // Вызов метода и проверка результата
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(book)));
    }

    @Test
    public void testCreateBook() throws Exception {
        Book book = new Book(null, "Project Aiter. Finding yourself", "Sivov Semyon");
        Book savedBook = new Book(1L, "Project Aiter. Finding yourself", "Sivov Semyon");
        when(bookService.save(Mockito.any(Book.class))).thenReturn(savedBook);

        // Вызов метода и проверка результата
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(savedBook)));
    }

    @Test
    public void testDeleteBook() throws Exception {
        // Вызов метода и проверка результата
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/1"))
                .andExpect(status().isNoContent());

        verify(bookService, times(1)).deleteById(1L);
    }
}