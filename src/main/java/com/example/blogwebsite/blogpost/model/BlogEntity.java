package com.example.blogwebsite.blogpost.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BlogEntity {
    @UtilityClass
    public static class BlogPostMappedToUser {
        public static final String BLOG_MAPPED_USER = "user";
        public static final String JOIN_TABLE = "BLOG_USER";
        public static final String JOIN_TABLE_BLOG_ID = "BLOG_ID";
        public static final String JOIN_TABLE_USER_ID = "USER_ID";
    }


    @UtilityClass
    public static class BlogPost {
        public static final String TABLE_NAME = "BLOGPOST";
        public static final String TITLE = "TITLE";
        public static final String CONTENT = "CONTENT";
        public static final String CONTENT_SHORT = "CONTENT_SHORT";
        public static final String TRANSLITERATE = "TRANSLITERATE";
    }
}
