package com.example.blogwebsite.file;

import com.example.blogwebsite.common.exception.file.FileDownloadException;
import com.example.blogwebsite.common.exception.file.FileEmptyException;
import com.example.blogwebsite.common.exception.file.FileUploadException;
import com.example.blogwebsite.common.model.ResponseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/files")

public class FileRestResource {
    private final FileService fileService;

    public FileRestResource(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object uploadFile(@RequestPart("file") MultipartFile multipartFile) throws FileEmptyException, FileUploadException, IOException {
        if (multipartFile.isEmpty()) {
            throw new FileEmptyException("File is empty. Cannot save an empty file");
        }
        boolean isValidFile = isValidFile(multipartFile);
        List<String> allowedFileExtensions = new ArrayList<>(Arrays.asList("pdf", "txt", "epub", "csv", "png", "jpg", "jpeg", "srt"));

        if (isValidFile && allowedFileExtensions.contains(FilenameUtils.getExtension(multipartFile.getOriginalFilename()))) {
            String fileName = fileService.uploadFile(multipartFile);
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .content("file uploaded successfully. File unique name =>" + fileName)
                    .hasErrors(true)
                    .build();
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .content("Invalid File. File extension or File name is not supported")
                    .hasErrors(true)
                    .build();
            return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/uploads")
    public Object uploadFiles(@RequestParam String path) throws FileEmptyException, FileUploadException, IOException {
        fileService.updateloadFilesFromPath(path);
        return new ResponseEntity<>("OK", HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/download")
    public Object downloadFile(@RequestParam("fileName") @NotBlank @NotNull String fileName) throws FileDownloadException, IOException {
        Object response = fileService.downloadFile(fileName);
        if (response != null) {
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"").body(response);
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .content("File could not be downloaded")
                    .hasErrors(true)
                    .build();
            return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam("fileName") @NotBlank @NotNull String fileName) {
        boolean isDeleted = fileService.delete(fileName);
        if (isDeleted) {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .content("File deleted successfully")
                    .hasErrors(false)
                    .build();
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .content("File does not exist\"")
                    .hasErrors(false)
                    .build();
            return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
        }
    }

    private boolean isValidFile(MultipartFile multipartFile) {
        if (Objects.isNull(multipartFile.getOriginalFilename())) {
            return false;
        }
        return !multipartFile.getOriginalFilename().trim().equals("");
    }

}
