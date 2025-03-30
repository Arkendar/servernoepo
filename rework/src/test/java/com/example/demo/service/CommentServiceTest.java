package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import com.example.demo.model.Comment;
import com.example.demo.repository.CommentRepository;

class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    private Comment comment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        comment = new Comment(1L, "user123", "Great post!",
                LocalDateTime.now(), null, "ACTIVE");
    }

    @Test
    void testGetAllComments() {
        when(commentRepository.findAll()).thenReturn(Arrays.asList(comment));
        List<Comment> comments = commentService.getAllComments();
        assertFalse(comments.isEmpty());
        assertEquals(1, comments.size());
        assertEquals("user123", comments.get(0).getAuthor());
    }

    @Test
    void testGetCommentById_Found() {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        Optional<Comment> foundComment = commentService.getCommentById(1L);
        assertTrue(foundComment.isPresent());
        assertEquals("Great post!", foundComment.get().getContent());
    }

    @Test
    void testGetCommentById_NotFound() {
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Comment> foundComment = commentService.getCommentById(1L);
        assertFalse(foundComment.isPresent());
    }

    @Test
    void testCreateComment() {
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        Comment createdComment = commentService.createComment(comment);
        assertNotNull(createdComment);
        assertEquals("user123", createdComment.getAuthor());
    }

    @Test
    void testUpdateComment() {
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        Comment updatedComment = commentService.updateComment(comment);
        assertNotNull(updatedComment);
        assertEquals("Great post!", updatedComment.getContent());
    }

    @Test
    void testDeleteComment() {
        doNothing().when(commentRepository).deleteById(1L);
        assertDoesNotThrow(() -> commentService.deleteComment(1L));
        verify(commentRepository, times(1)).deleteById(1L);
    }
}