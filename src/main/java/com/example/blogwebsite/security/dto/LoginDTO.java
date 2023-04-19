package com.example.blogwebsite.security.dto;

import com.example.blogwebsite.security.validation.MustBeExistedUser;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    @MustBeExistedUser
    private String username;
    private String password;
}
