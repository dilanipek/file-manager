package com.file.manager.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileManagerService {
     String storeFile(MultipartFile file) throws IOException;

     Resource loadFileAsResource(String fileName) throws IOException;

     void deleteFile(String fileName);
}
