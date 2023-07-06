package com.example.blogwebsite.blogpost.dto;

import com.example.blogwebsite.blogpost.validation.annotation.UniqueTitle;
import com.example.blogwebsite.user.dto.UserDTOSimple;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogDTO {
    private UUID id;
    @Size(min = 5, max = 100, message = "{blog.title.size}")
    @NotBlank(message = "{blog.title.blank}")
    @UniqueTitle(message = "{blog.title.existed}")
    private String title;
    private String content;
    private String shortContent;
    private String transliterated;
    private String createdAt;
    private UserDTOSimple user;
    private int views;

}
