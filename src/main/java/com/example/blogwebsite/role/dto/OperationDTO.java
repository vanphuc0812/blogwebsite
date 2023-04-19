package com.example.blogwebsite.role.dto;

import com.example.blogwebsite.role.model.Operation;
import com.example.blogwebsite.role.validation.annotation.UniqueOperationCode;
import com.example.blogwebsite.role.validation.annotation.UniqueOperationName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationDTO {
    private UUID id;

    @Size(min = 5, max = 100, message = "{operation.name.size}")
    @NotBlank
    @UniqueOperationName
    private String name;

    @Size(min = 3, max = 10, message = "{operation.code.size}")
    @NotBlank
    @UniqueOperationCode
    private String code;

    @NotBlank(message = "{operation.description.blank}")
    private String description;

    private Operation.Type type;
}
