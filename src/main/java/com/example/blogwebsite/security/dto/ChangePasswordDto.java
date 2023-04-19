package com.example.blogwebsite.security.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDto {
    String username;
    String oldPassword;
    String newPassword;
}
