package com.example.blogwebsite.role.model;

import com.example.blogwebsite.common.model.BaseEntity;
import com.example.blogwebsite.user.model.User;
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
@Table(name = RoleEntity.UserGroup.TABLE_NAME)
public class UserGroup extends BaseEntity {

    @Column(name = RoleEntity.UserGroup.NAME, unique = true, length = 100)
    @Length(min = 5, max = 100, message = "{usergroup.name.size}")
    private String name;

    @Column(name = RoleEntity.UserGroup.CODE, unique = true)
    @Length(min = 3, max = 10, message = "{usergroup.code.size}")
    private String code;

    @Column(name = RoleEntity.UserGroup.DESCRIPTION)
    @NotBlank
    private String description;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = RoleEntity.UserGroupMappedUser.JOIN_TABLE,
            joinColumns = @JoinColumn(name = RoleEntity.UserGroupMappedUser.JOIN_TABLE_USER_GROUP_ID),
            inverseJoinColumns = @JoinColumn(name = RoleEntity.UserGroupMappedUser.JOIN_TABLE_USER_ID))
    private Set<User> users = new LinkedHashSet<>();

    @ManyToMany(mappedBy = RoleEntity.RoleMappedUserGroup.USER_GROUP_MAPPED_ROLE, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Role> roles = new LinkedHashSet<>();

    public void addUser(User user) {
        this.users.add(user);
        user.getUserGroups().add(this);
    }

    public void removeUser(User user) {
        this.users.remove(user);
        user.getUserGroups().remove(this);
    }

}
