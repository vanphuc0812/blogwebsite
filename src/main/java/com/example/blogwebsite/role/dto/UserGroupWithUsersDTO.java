package com.example.blogwebsite.role.dto;

import com.example.blogwebsite.role.model.UserGroup;
import com.example.blogwebsite.role.validation.annotation.UniqueUserGroupName;
import com.example.blogwebsite.user.dto.UserDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Set;
import java.util.UUID;

/**
 * A DTO for the {@link UserGroup} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGroupWithUsersDTO {
    private UUID id;
    @Length(min = 5, max = 100, message = "{usergroup.name.size}")
    @UniqueUserGroupName
    private String name;
    @Length(min = 3, max = 10, message = "{usergroup.code.size}")
    private String code;
    @NotBlank(message = "{usergroup.description.blank}")
    private String description;
    private Set<UserDTO> users;
}
