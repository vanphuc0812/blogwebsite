package com.example.blogwebsite.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentSaveDTO {
    private String content;
    private UUID blogID;
    private String username;
    private UUID parent;
}
