package com.example.blogwebsite.blogpost.model;

import com.example.blogwebsite.common.model.BaseEntity;
import com.example.blogwebsite.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = BlogEntity.BlogPost.TABLE_NAME)
public class Blog extends BaseEntity {
    
    @Column(name = BlogEntity.BlogPost.TITLE)
    private String title;
    @Column(name = BlogEntity.BlogPost.CONTENT)
    private String content;
    @ManyToOne
    @JoinTable(
            name = BlogEntity.BlogPostMappedToUser.JOIN_TABLE,
            joinColumns = @JoinColumn(name = BlogEntity.BlogPostMappedToUser.JOIN_TABLE_BLOG_ID),
            inverseJoinColumns = @JoinColumn(name = BlogEntity.BlogPostMappedToUser.JOIN_TABLE_USER_ID)
    )
    private User user;
    private String username;

    public void setUser(User user) {
        this.user = user;
        this.username = user.getUsername();
    }
}
