package com.example.blogwebsite.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.example.blogwebsite.common.exception.file.FileDownloadException;
import com.example.blogwebsite.common.exception.file.FileUploadException;
import com.example.blogwebsite.common.util.CustomRandom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public interface FileService {
    String uploadFile(MultipartFile multipartFile) throws FileUploadException, IOException;

    Object downloadFile(String fileName) throws FileDownloadException, IOException;

    boolean delete(String fileName);

    void updateloadFilesFromPath(String path);


}

@Service
@Slf4j
@RequiredArgsConstructor
class FileServiceImpl implements FileService {
    private final AmazonS3 s3Client;
    @Value("${aws.bucketName}")
    private String bucketName;

    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        // convert multipart file  to a file
        File file = new File(multipartFile.getOriginalFilename());
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(multipartFile.getBytes());
        }

        // generate file name
        String fileName = CustomRandom.generateRandomFilename();

        // upload file
        PutObjectRequest request = new PutObjectRequest(bucketName, fileName, file);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("plain/" + FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
        metadata.addUserMetadata("Title", "File Upload - " + fileName);
        metadata.setContentLength(file.length());
        request.setMetadata(metadata);
        s3Client.putObject(request);

        // delete file
        file.delete();

        return fileName;
    }

    public void updateloadFilesFromPath(String path) {
        File reportDir = new File(path);
        if (reportDir.exists()) {
            for (String fileName : reportDir.list()) {

                File file = new File(path + fileName);
                if (!file.exists()) return;
                // upload file
                PutObjectRequest request = new PutObjectRequest(bucketName, fileName, file);
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentType("plain/" + FilenameUtils.getExtension(fileName));
                metadata.addUserMetadata("Title", "File Upload - " + fileName);
                metadata.setContentLength(file.length());
                request.setMetadata(metadata);
                s3Client.putObject(request);

                // delete file
                file.delete();
            }
        }
    }

    @Override
    public Object downloadFile(String fileName) throws FileDownloadException, IOException {
        if (bucketIsEmpty()) {
            throw new FileDownloadException("Requested bucket does not exist or is empty");
        }
        S3Object object = s3Client.getObject(bucketName, fileName);
        try (S3ObjectInputStream s3is = object.getObjectContent()) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
                byte[] read_buf = new byte[1024];
                int read_len = 0;
                while ((read_len = s3is.read(read_buf)) > 0) {
                    fileOutputStream.write(read_buf, 0, read_len);
                }
            }
            Path pathObject = Paths.get(fileName);
            Resource resource = new UrlResource(pathObject.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileDownloadException("Could not find the file!");
            }
        }
    }

    @Override
    public boolean delete(String fileName) {
        File file = Paths.get(fileName).toFile();
        if (file.exists()) {
            file.delete();
            return true;
        }
        return false;
    }

    private boolean bucketIsEmpty() {
        ListObjectsV2Result result = s3Client.listObjectsV2(this.bucketName);
        if (result == null) {
            return false;
        }
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        return objects.isEmpty();
    }

}

