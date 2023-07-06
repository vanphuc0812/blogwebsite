package com.example.blogwebsite.comment.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommentEntity {
    @UtilityClass
    public static class BlogPostMappedToComment {
        public static final String BLOG_MAPPED_COMMENT = "blog";
        public static final String JOIN_TABLE = "BLOG_COMMENT";
        public static final String JOIN_TABLE_BLOG_ID = "BLOG_ID";
        public static final String JOIN_TABLE_COMMENT_ID = "COMMENT_ID";
    }

    @UtilityClass
    public static class UserMappedToComment {
        public static final String USER_MAPPED_COMMENT = "user";
        public static final String JOIN_TABLE = "USER_COMMENT";
        public static final String JOIN_TABLE_BLOG_ID = "USER_ID";
        public static final String JOIN_TABLE_COMMENT_ID = "COMMENT_ID";
    }

    @UtilityClass
    public static class Comment {
        public static final String TABLE_NAME = "COMMENT";
        public static final String CONTENT = "CONTENT";

        public static final String LIKES = "LIKES";

        public static final String OWNER = "OWNER";
        public static final String BLOG = "BLOG";


    }
}
