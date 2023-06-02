package com.example.blogwebsite.file;

import com.example.blogwebsite.common.util.FileUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;


public interface FileService {

    public String save(MultipartFile file);

    public void saveMultiple(List<MultipartFile> files);

    public Resource load(String fileName);
}

@Service
class FileServiceImp implements FileService {

    @Override
    public String save(MultipartFile file) {
        return FileUtil.saveFile(file);
    }

    @Override
    public void saveMultiple(List<MultipartFile> files) {
        files.forEach(FileUtil::saveFile);
    }

    @Override
    public Resource load(String fileName) {
        try {
            Path file = FileUtil.ROOTPATH.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Error resource not exits");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error resource not exits" + e.getMessage());
        }


    }
}
