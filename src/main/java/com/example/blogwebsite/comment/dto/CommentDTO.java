package com.example.blogwebsite.comment.dto;

import com.example.blogwebsite.user.dto.UserDTOSimple;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private String content;
    private int likes;
    //    private BlogDTO blog;
    private UserDTOSimple user;
    private CommentDTO parent;
}
