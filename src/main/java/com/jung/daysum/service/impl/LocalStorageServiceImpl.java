package com.jung.daysum.service.impl;

import com.jung.daysum.response.exeption.Exception400;
import com.jung.daysum.response.exeption.Exception500;
import com.jung.daysum.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocalStorageServiceImpl implements StorageService {

    @Value("${storage.local.root-path}")
    private String rootPath;


    @Transactional
    @Override
    public String uploadImage(
            MultipartFile file,
            String directory
    ) throws IOException {
        if(file == null || file.isEmpty())
            throw new Exception400.StorageBadRequest("imageFile = null");

        String originalFilename = file.getOriginalFilename();

        String extension = "";
        if(originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(
                    originalFilename.lastIndexOf(".")
            );
        }

        String fileName =
                UUID.randomUUID() + extension;

        Path directoryPath =
                Paths.get(rootPath, directory);

        Files.createDirectories(directoryPath);

        Path filePath =
                directoryPath.resolve(fileName);

        file.transferTo(filePath);

        String objectKey =
                directory + "/" + fileName;

        log.info(
                "Success File Upload - newFileName: {}, newObjectKey: {}",
                fileName,
                objectKey
        );

        return objectKey;
    }


    @Transactional
    @Override
    public void deleteImage(String objectKey) {
        if(objectKey == null)
            throw new Exception500.StorageServer(
                    "imageObjectKey = null"
            );

        Path filePath =
                Paths.get(rootPath, objectKey);

        try {
            Files.deleteIfExists(filePath);

            log.info(
                    "Success File Delete - deleteObjectKey: {}",
                    objectKey
            );
        }
        catch(IOException e) {
            log.info(
                    "Error File Delete - errorObjectKey: {}",
                    objectKey
            );

            throw new Exception500.StorageServer(
                    "로컬 스토리지 이미지 처리 에러"
            );
        }
    }
}