package com.example.blogwebsite.role.model;

import com.example.blogwebsite.common.model.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = RoleEntity.Operation.TABLE_NAME)
public class Operation extends BaseEntity {
    @Column(name = RoleEntity.Operation.NAME, unique = true, length = 100)
    @Length(min = 5, max = 100, message = "{operation.name.size}")
    private String name;

    @Column(name = RoleEntity.Operation.CODE, unique = true)
    @Length(min = 3, max = 10, message = "{operation.code.size}")
    private String code;

    @Column(name = RoleEntity.Operation.DESCRIPTION)
    @NotBlank
    private String description;

    @Column(name = RoleEntity.Operation.TYPE, nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToMany(mappedBy = RoleEntity.RoleMappedOperation.OPERATION_MAPPED_ROLE, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Role> roles = new LinkedHashSet<>();

    public enum Type {
        SAVE_OR_UPDATE,
        FETCH,
        REMOVE
    }
}
