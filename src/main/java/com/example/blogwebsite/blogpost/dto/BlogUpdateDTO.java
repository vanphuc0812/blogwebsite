package com.example.blogwebsite.blogpost.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogUpdateDTO {
    private UUID id;
    private String title;
    private String content;
    private String shortContent;
    private String transliterated;
    private String publishedAt;
}
