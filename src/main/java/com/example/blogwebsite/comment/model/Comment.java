package com.example.blogwebsite.comment.model;

import com.example.blogwebsite.blogpost.model.Blog;
import com.example.blogwebsite.common.model.BaseEntity;
import com.example.blogwebsite.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = CommentEntity.Comment.TABLE_NAME)
public class Comment extends BaseEntity {
    @Column(name = CommentEntity.Comment.CONTENT, length = 1000)
    private String content;


    @ManyToOne
    @JoinTable(
            name = CommentEntity.BlogPostMappedToComment.JOIN_TABLE,
            joinColumns = @JoinColumn(name = CommentEntity.BlogPostMappedToComment.JOIN_TABLE_BLOG_ID),
            inverseJoinColumns = @JoinColumn(name = CommentEntity.BlogPostMappedToComment.JOIN_TABLE_COMMENT_ID)
    )
    private Blog blog;

    @ManyToOne
    @JoinTable(
            name = CommentEntity.UserMappedToComment.JOIN_TABLE,
            joinColumns = @JoinColumn(name = CommentEntity.UserMappedToComment.JOIN_TABLE_USER_ID),
            inverseJoinColumns = @JoinColumn(name = CommentEntity.UserMappedToComment.JOIN_TABLE_COMMENT_ID)
    )
    private User user;

    @Column(name = CommentEntity.Comment.LIKES)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = CommentEntity.UserLikeToComment.JOIN_TABLE,
            joinColumns = @JoinColumn(name = CommentEntity.UserLikeToComment.JOIN_TABLE_USER_ID),
            inverseJoinColumns = @JoinColumn(name = CommentEntity.UserLikeToComment.JOIN_TABLE_COMMENT_ID))
    private Set<User> likes = new LinkedHashSet<>();
    @Column(name = CommentEntity.Comment.NUMBER_OF_LIKES)
    private int numberOfLikes;
    private UUID parent;
}
