package org.example.lab6_2.controller;

import org.example.lab6_2.model.Comment;
import org.example.lab6_2.model.NewComment;
import org.example.lab6_2.model.UpdateComment;
import org.example.lab6_2.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v3/comments")
@Slf4j
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<Comment>> listComments() {
        log.info("GET /comments called");
        return ResponseEntity.ok(commentService.listComments());
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody NewComment newComment) {
        log.info("POST /comments called with: {}", newComment);
        Comment comment = commentService.createComment(newComment);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long commentId) {
        log.info("GET /comments/{} called", commentId);
        return commentService.getCommentById(commentId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long commentId, @RequestBody UpdateComment updateComment) {
        log.info("PUT /comments/{} called with: {}", commentId, updateComment);
        return commentService.updateComment(commentId, updateComment)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        log.info("DELETE /comments/{} called", commentId);
        boolean deleted = commentService.deleteComment(commentId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
