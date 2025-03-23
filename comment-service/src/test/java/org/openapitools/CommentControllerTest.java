package org.openapitools;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openapitools.model.Comment;
import org.openapitools.model.NewComment;
import org.openapitools.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        commentRepository.deleteAll();
    }

    @Test
    public void testCreateAndGetComment() throws Exception {
        NewComment newComment = new NewComment();
        newComment.setAuthorId(42L);
        newComment.setPostId(100L);
        newComment.setMessage("Test comment");

        // Создание комментария
        String response = mockMvc.perform(post("/api/v3/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newComment)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andReturn().getResponse().getContentAsString();

        Comment createdComment = objectMapper.readValue(response, Comment.class);

        // Получение комментария
        mockMvc.perform(get("/api/v3/comments/" + createdComment.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Test comment")));
    }

    @Test
    public void testUpdateComment() throws Exception {
        Comment comment = new Comment();
        comment.setAuthorId(42L);
        comment.setPostId(100L);
        comment.setMessage("Original message");
        comment.setCreatedAt(Instant.now());
        comment = commentRepository.save(comment);

        // Обновление комментария
        String updateJson = "{\"message\": \"Updated message\"}";
        mockMvc.perform(put("/api/v3/comments/" + comment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Updated message")));
    }

    @Test
    public void testDeleteComment() throws Exception {
        Comment comment = new Comment();
        comment.setAuthorId(42L);
        comment.setPostId(100L);
        comment.setMessage("To be deleted");
        comment.setCreatedAt(Instant.now());
        comment = commentRepository.save(comment);

        // Удаление комментария
        mockMvc.perform(delete("/api/v3/comments/" + comment.getId()))
                .andExpect(status().isNoContent());

        // Проверка удаления
        mockMvc.perform(get("/api/v3/comments/" + comment.getId()))
                .andExpect(status().isNotFound());
    }
}
