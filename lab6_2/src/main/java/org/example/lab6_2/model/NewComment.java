package org.example.lab6_2.model;

import lombok.Data;

@Data
public class NewComment {
    private Long authorId;
    private Long postId;
    private String message;
}
