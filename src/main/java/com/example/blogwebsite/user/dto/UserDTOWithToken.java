package com.example.blogwebsite.user.dto;

import com.example.blogwebsite.user.validation.annotation.CorrectGender;
import com.example.blogwebsite.user.validation.annotation.UniqueUsername;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTOWithToken {
    private UUID id;
    private String name;
    @Size(min = 5, max = 100, message = "{user.username.size}")
    @NotBlank(message = "{user.username.blank}")
    @UniqueUsername(message = "{user.username.existed}")
    private String username;
    @NotBlank(message = "{user.email.blank}")
    private String email;
    private String avatar;

    private String token;
    @CorrectGender(message = "{user.gender.incorrect}")
    private String gender;
}
