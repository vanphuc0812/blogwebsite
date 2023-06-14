package com.example.blogwebsite.blogpost.model;

import com.example.blogwebsite.comment.model.Comment;
import com.example.blogwebsite.comment.model.CommentEntity;
import com.example.blogwebsite.common.model.BaseEntity;
import com.example.blogwebsite.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = BlogEntity.BlogPost.TABLE_NAME)
public class Blog extends BaseEntity {

    @Column(name = BlogEntity.BlogPost.TITLE, nullable = false, unique = true)
    private String title;
    @Column(name = BlogEntity.BlogPost.CONTENT, length = 100000)
    private String content;
    @Column(name = BlogEntity.BlogPost.CONTENT_SHORT, length = 1000)
    private String shortContent;
    @Column(name = BlogEntity.BlogPost.TRANSLITERATE)
    private String transliterated;
    @Column(name = BlogEntity.BlogPost.VIEWS)
    private int views;
    @ManyToOne
    @JoinTable(
            name = BlogEntity.BlogPostMappedToUser.JOIN_TABLE,
            joinColumns = @JoinColumn(name = BlogEntity.BlogPostMappedToUser.JOIN_TABLE_BLOG_ID),
            inverseJoinColumns = @JoinColumn(name = BlogEntity.BlogPostMappedToUser.JOIN_TABLE_USER_ID)
    )
    private User user;

    @OneToMany(mappedBy = CommentEntity.BlogPostMappedToComment.BLOG_MAPPED_COMMENT, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Comment> comments = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
    }
}
