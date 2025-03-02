package com.example.lab4.repository;

import com.example.lab4.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryBookRepository {
    private final List<Book> books = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    public List<Book> findAll() {
        return new ArrayList<>(books);
    }

    public Optional<Book> findById(Long id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst();
    }

    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId(counter.incrementAndGet());
        }
        books.add(book);
        return book;
    }

    public void deleteById(Long id) {
        books.removeIf(book -> book.getId().equals(id));
    }
}