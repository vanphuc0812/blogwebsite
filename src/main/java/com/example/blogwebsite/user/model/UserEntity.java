package com.example.blogwebsite.user.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserEntity {
    @UtilityClass
    public class User {

        public static final String TABLE_NAME = "BLOGGER";
        public static final String NAME = "NAME";
        public static final String USERNAME = "USERNAME";
        public static final String PASSWORD = "PASSWORD";
        public static final String EMAIL = "EMAIL";
        public static final String GENDER = "GENDER";
        public static final String AVATAR = "AVATAR";
        public static final String FOLLOWING = "FOLLOWING";

        public static final String FOLLOWED = "FOLLOWED";

    }

}
