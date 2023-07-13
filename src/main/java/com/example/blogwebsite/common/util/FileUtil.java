package com.example.blogwebsite.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {
    public static final Path ROOTPATH = Paths.get("images");
    private static final String SAVE_FILE_ERROR_MESSAGE = "Error save file to root folder";
    private static final String SAVE_FILE_SUCCESS_MESSAGE = "Success save file to root folder";

    private static final String ROOT_ERROR_MESSAGE = "Error while creating root folder";

    public static String saveFile(MultipartFile file, String filename) {
        if (init()) {
            try {
                Files.copy(file.getInputStream(), ROOTPATH.resolve(filename));
            } catch (Exception e) {
                return SAVE_FILE_ERROR_MESSAGE + e.getMessage();
            }
            return SAVE_FILE_SUCCESS_MESSAGE + ": " + filename;
        } else {
            return ROOT_ERROR_MESSAGE;
        }

    }

    public static String saveFile(MultipartFile file) {
        if (init()) {
            String filename = CustomRandom.generateRandomFilename();
            try {
                Files.copy(file.getInputStream(), ROOTPATH.resolve(filename));
            } catch (Exception e) {
                return SAVE_FILE_ERROR_MESSAGE + e.getMessage();
            }
            return SAVE_FILE_SUCCESS_MESSAGE + ": " + filename;

        } else {
            return ROOT_ERROR_MESSAGE;
        }
    }

    private static boolean init() {
        try {
            if (!Files.exists(FileUtil.ROOTPATH)) {
                // Tạo folder root
                Files.createDirectory(FileUtil.ROOTPATH);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
