package com.jung.daysum.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {

    String uploadImage(MultipartFile file, String directory) throws IOException;

    void deleteImage(String objectKey);
}