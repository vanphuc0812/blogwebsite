package com.example.blogwebsite.blogpost.dto;

import com.example.blogwebsite.blogpost.validation.annotation.UniqueTitle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogSaveDTO {
    @Size(min = 5, max = 100, message = "{blog.title.size}")
    @NotBlank(message = "{blog.title.blank}")
    @UniqueTitle(message = "{blog.title.existed}")
    private String title;
    private String content;
    private String username;
}
