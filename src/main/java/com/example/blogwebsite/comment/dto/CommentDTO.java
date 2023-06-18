package com.example.blogwebsite.comment.dto;

import com.example.blogwebsite.user.dto.UserDTOSimple;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private UUID id;
    private String content;
    private int likes;
    private UserDTOSimple user;
    private UUID parent;
}
