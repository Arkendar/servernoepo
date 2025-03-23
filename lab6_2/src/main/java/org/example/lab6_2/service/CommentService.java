package org.example.lab6_2.service;

import org.example.lab6_2.model.Comment;
import org.example.lab6_2.model.NewComment;
import org.example.lab6_2.model.UpdateComment;
import org.example.lab6_2.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> listComments() {
        log.info("Listing all comments");
        return commentRepository.findAll();
    }

    public Comment createComment(NewComment newComment) {
        log.info("Creating new comment: {}", newComment);
        Comment comment = new Comment();
        comment.setAuthorId(newComment.getAuthorId());
        comment.setPostId(newComment.getPostId());
        comment.setMessage(newComment.getMessage());
        comment.setCreatedAt(Instant.now());
        return commentRepository.save(comment);
    }

    public Optional<Comment> getCommentById(Long id) {
        log.info("Fetching comment with id: {}", id);
        return commentRepository.findById(id);
    }

    public Optional<Comment> updateComment(Long id, UpdateComment updateComment) {
        log.info("Updating comment with id: {} with new message: {}", id, updateComment.getMessage());
        return commentRepository.findById(id)
                .map(comment -> {
                    comment.setMessage(updateComment.getMessage());
                    return commentRepository.save(comment);
                });
    }

    public boolean deleteComment(Long id) {
        log.info("Deleting comment with id: {}", id);
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
