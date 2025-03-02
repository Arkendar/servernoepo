package com.example.lab4.service;

import com.example.lab4.model.Book;
import com.example.lab4.repository.InMemoryBookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private InMemoryBookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void testFindAll() {
        Book book1 = new Book(1L, "Project Aiter. Finding yourself", "Sivov Semyon");
        Book book2 = new Book(2L, "Project Aiter. The price of life", "Semyon Sivov");
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        // Вызов метода и проверка результата
        List<Book> books = bookService.findAll();
        assertEquals(2, books.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        Book book = new Book(1L, "Project Aiter. Finding yourself", "Sivov Semyon");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Вызов метода и проверка результата
        Optional<Book> foundBook = bookService.findById(1L);
        assertEquals(book, foundBook.get());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    public void testSave() {
        Book book = new Book(null, "Project Aiter. Finding yourself", "Sivov Semyon");
        Book savedBook = new Book(1L, "Project Aiter. Finding yourself", "Sivov Semyon");
        when(bookRepository.save(book)).thenReturn(savedBook);

        // Вызов метода и проверка результата
        Book result = bookService.save(book);
        assertEquals(savedBook, result);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void testDeleteById() {
        // Вызов метода и проверка результата
        bookService.deleteById(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }
}