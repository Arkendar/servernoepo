package com.example.demo.repository;

import com.example.demo.model.Comment;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepository {
    private final Map<Long, Comment> commentStorage = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public List<Comment> findAll() {
        return new ArrayList<>(commentStorage.values());
    }

    public Optional<Comment> findById(Long id) {
        return Optional.ofNullable(commentStorage.get(id));
    }

    public Comment save(Comment comment) {
        if (comment.getId() == null) {
            comment.setId(idCounter.getAndIncrement());
            comment.setCreatedDate(LocalDateTime.now());
        } else {
            comment.setModifiedDate(LocalDateTime.now());
        }
        commentStorage.put(comment.getId(), comment);
        return comment;
    }

    public void deleteById(Long id) {
        commentStorage.remove(id);
    }
}