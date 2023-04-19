package com.example.blogwebsite.role.dto;

import com.example.blogwebsite.role.validation.annotation.UniqueRoleName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    // Data Transfer Object
    private UUID id;

    @Size(min = 5, max = 100, message = "{role.name.size}")
    @NotBlank
    @UniqueRoleName
    private String name;

    @Size(min = 3, max = 10, message = "{role.code.size}")
    @NotBlank
    private String code;

    @NotBlank(message = "{role.description.blank}")
    private String description;
}
