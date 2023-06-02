package com.example.blogwebsite.file;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/files")

public class FileRestResource {
    private final FileService fileService;

    public FileRestResource(FileService fileService) {
        this.fileService = fileService;
    }
    
    @PostMapping(path = "/Upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object uploadFile(@RequestPart("file") MultipartFile file) {
        return new ResponseEntity<>(fileService.save(file), HttpStatus.OK);
    }

    @PostMapping(path = "/UploadFiles", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object uploadFiles(@RequestPart("file") List<MultipartFile> files) {
        fileService.saveMultiple(files);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @GetMapping("/{filename}") // đọc file
    public Object loadFile(@PathVariable("filename") String fileName) {
        Resource resource = fileService.load(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

}
