package com.example.blogwebsite.role.dto;

import com.example.blogwebsite.role.validation.annotation.UniqueRoleName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleWithOperationsDTO {
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

    private Set<OperationDTO> operations;
}
