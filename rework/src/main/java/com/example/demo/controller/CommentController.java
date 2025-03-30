package com.example.demo.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Comment;
import com.example.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments() {
        log.info("Getting all comments");
        List<Comment> comments = commentService.getAllComments();
        return comments.isEmpty() 
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        log.info("Getting comment with id: {}", id);
        return commentService.getCommentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        log.info("Creating new comment: {}", comment);
        if (comment.getContent() == null || comment.getContent().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Comment createdComment = commentService.createComment(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        log.info("Updating comment with id: {}", id);
        comment.setId(id);
        Comment updatedComment = commentService.updateComment(comment);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        log.info("Deleting comment with id: {}", id);
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}