package com.example.blogwebsite.role.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RoleEntity {
    @UtilityClass
    public static class RoleMappedOperation {
        public static final String OPERATION_MAPPED_ROLE = "operations";
        public static final String JOIN_TABLE = "ROLE_OPERATION";
        public static final String JOIN_TABLE_ROLE_ID = "ROLE_ID";
        public static final String JOIN_TABLE_SERVICE_ID = "OPERATION_ID";
    }

    @UtilityClass
    public static class RoleMappedUserGroup {
        public static final String USER_GROUP_MAPPED_ROLE = "userGroups";
        public static final String JOIN_TABLE = "ROLE_USER_GROUP";
        public static final String JOIN_TABLE_ROLE_ID = "ROLE_ID";
        public static final String JOIN_TABLE_USER_GROUP_ID = "USER_GROUP_ID";
    }

    @UtilityClass
    public static class UserGroupMappedUser {
        public static final String USER_MAPPED_USER_GROUP = "users";
        public static final String JOIN_TABLE = "USER_USER_GROUP";
        public static final String JOIN_TABLE_USER_ID = "USER_ID";
        public static final String JOIN_TABLE_USER_GROUP_ID = "USER_GROUP_ID";
    }

    @UtilityClass
    public static class Role {
        public static final String TABLE_NAME = "ROLE";
        public static final String NAME = "NAME";
        public static final String DESCRIPTION = "DESCRIPTION";
        public static final String CODE = "CODE";
    }

    @UtilityClass
    public static class UserGroup {

        public static final String TABLE_NAME = "USER_GROUP";
        public static final String NAME = "NAME";
        public static final String DESCRIPTION = "DESCRIPTION";
        public static final String CODE = "CODE";
    }

    @UtilityClass
    public static class Operation {
        public static final String TABLE_NAME = "OPERATION";
        public static final String NAME = "NAME";
        public static final String DESCRIPTION = "DESCRIPTION";
        public static final String CODE = "CODE";
        public static final String TYPE = "TYPE";
    }
}
