package com.example.blogwebsite.user.model;

import com.example.blogwebsite.blogpost.model.Blog;
import com.example.blogwebsite.blogpost.model.BlogEntity;
import com.example.blogwebsite.common.model.BaseEntity;
import com.example.blogwebsite.role.model.RoleEntity;
import com.example.blogwebsite.role.model.UserGroup;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = UserEntity.User.TABLE_NAME)
@NamedQueries({
        @NamedQuery(name = "User.findByUsernameLikeIgnoreCase", query = "select u from User u where upper(u.username) like upper(:username)")
})
public class User extends BaseEntity {

    @Column(name = UserEntity.User.NAME)
    private String name;
    @Column(name = UserEntity.User.USERNAME
            , nullable = false
            , unique = true
            , length = 100
            , updatable = false
    )
    private String username;

    @Column(name = UserEntity.User.PASSWORD)
    private String password;

    @Column(name = UserEntity.User.EMAIL
            , unique = true
            , nullable = false
            , length = 100
    )
    private String email;

    @Column(name = UserEntity.User.PHONE)
    private String phone;

    @Column(name = UserEntity.User.ADDRESS)
    private String address;

    @Column(name = UserEntity.User.BIRTH)
    private String birth;

    @Column(name = UserEntity.User.AVATAR)
    private String avatar;

    @Column(name = UserEntity.User.GENDER)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    // OneToMany
    // relationship Bidirectional
    @ManyToMany(mappedBy = RoleEntity.UserGroupMappedUser.USER_MAPPED_USER_GROUP)
    private Set<UserGroup> userGroups = new LinkedHashSet<>();
    @Transient
    private String token;
    @Enumerated(EnumType.STRING)
    private Provider provider;

    @OneToMany(mappedBy = BlogEntity.BlogPostMappedToUser.BLOG_MAPPED_USER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Blog> blogs = new LinkedHashSet<>();

    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }

    public enum Provider {
        local, google
    }
}