package com.example.blogwebsite.comment.dto;

import com.example.blogwebsite.user.dto.UserDTOSimple;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    @NotBlank
    private UUID id;
    private String content;
    private Set<UserDTOSimple> likes;
    private int numberOfLikes;
    private UserDTOSimple user;
    private UUID parent;
    private String createdAt;
}
