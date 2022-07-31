package com.file.manager.service.impl;

import com.file.manager.domain.entity.FileStore;
import com.file.manager.exception.FileManagerException;
import com.file.manager.exception.RequestedFileNotFoundException;
import com.file.manager.repository.FileStoreRepository;
import com.file.manager.service.FileManagerService;
import com.file.manager.utility.FileManagerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileManagerServiceImpl implements FileManagerService {

    private final Path fileStorageLocation;

    @Autowired
    public FileManagerServiceImpl(FileManagerProperties fileManagerProperties) {
        this.fileStorageLocation = Paths.get(fileManagerProperties.getUploadDirectory())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RequestedFileNotFoundException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Autowired
    private FileStoreRepository fileStoreRepository;


    @Override
    public String storeFile(MultipartFile file) {

        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileManagerException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            FileStore fileStore= new FileStore();

            fileStore.setFileName(file.getName());
            fileStore.setFileUploadDirectory(this.fileStorageLocation.toString());
            fileStore.setSize(file.getSize());
            fileStore.setFileExtension(file.getContentType());

            fileStoreRepository.save(fileStore);
            return fileName;
        } catch (IOException ex) {
            throw new FileManagerException("Could not store file " + fileName + ". Please try again!", ex);
        }

    }

    @Override
    public Resource loadFileAsResource(String fileName)  {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new RequestedFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RequestedFileNotFoundException("File not found " + fileName, ex);
        }

    }
}
