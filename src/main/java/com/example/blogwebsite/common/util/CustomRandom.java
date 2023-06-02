package com.example.blogwebsite.common.util;

import java.util.UUID;

public class CustomRandom {
    public static String generateRandomFilename() {
        UUID uuid = UUID.randomUUID();
        String randomString = uuid.toString();
        return randomString;
    }
}
