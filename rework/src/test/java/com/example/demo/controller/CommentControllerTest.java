package com.example.demo.controller;

import com.example.demo.model.Comment;
import com.example.demo.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CommentControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    private Comment comment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Регистрируем модуль для работы с LocalDateTime

        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
        comment = new Comment(1L, "user123", "Great post!",
                LocalDateTime.now(), null, "ACTIVE");
    }

    @Test
    void testGetAllComments() throws Exception {
        when(commentService.getAllComments()).thenReturn(Arrays.asList(comment));

        mockMvc.perform(get("/api/v1/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].author").value("user123"));
    }

    @Test
    void testGetCommentById_Found() throws Exception {
        when(commentService.getCommentById(1L)).thenReturn(Optional.of(comment));

        mockMvc.perform(get("/api/v1/comments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Great post!"));
    }

    @Test
    void testGetCommentById_NotFound() throws Exception {
        when(commentService.getCommentById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/comments/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateComment() throws Exception {
        when(commentService.createComment(any(Comment.class))).thenReturn(comment);

        mockMvc.perform(post("/api/v1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.author").value("user123"));
    }

    @Test
    void testUpdateComment() throws Exception {
        when(commentService.updateComment(any(Comment.class))).thenReturn(comment);

        mockMvc.perform(put("/api/v1/comments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Great post!"));
    }

    @Test
    void testDeleteComment() throws Exception {
        when(commentService.getCommentById(1L)).thenReturn(Optional.of(comment));
        doNothing().when(commentService).deleteComment(1L);

        mockMvc.perform(delete("/api/v1/comments/1"))
                .andExpect(status().isNoContent());

        verify(commentService, times(1)).deleteComment(1L);
    }

    @Test
    void testCreateComment_InvalidInput() throws Exception {
        Comment invalidComment = new Comment(null, "", null, null, null, null);

        mockMvc.perform(post("/api/v1/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidComment)))
                .andExpect(status().isBadRequest());
    }
}