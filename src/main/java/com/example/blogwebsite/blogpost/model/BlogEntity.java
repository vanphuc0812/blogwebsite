package com.example.blogwebsite.blogpost.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BlogEntity {
    @UtilityClass
    public static class BlogPostMappedToUser {
        public static final String BLOG_MAPPED_USER = "user";
        public static final String JOIN_TABLE = "BW_BLOG_USER";
        public static final String JOIN_TABLE_BLOG_ID = "BW_BLOG_ID";
        public static final String JOIN_TABLE_USER_ID = "BW_USER_ID";
    }


    @UtilityClass
    public static class BlogPost {
        public static final String TABLE_NAME = "BW_BLOGPOST";
        public static final String TITLE = "BW_TITLE";
        public static final String CONTENT = "BW_CONTENT";
    }
}
