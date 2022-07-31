package com.file.manager.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileManagerService {
    public String storeFile(MultipartFile file);

    public Resource loadFileAsResource(String fileName) throws IOException;
}
