package com.file.manager.controller;

import com.file.manager.domain.response.UploadFileResponse;
import com.file.manager.service.FileManagerService;
import com.file.manager.service.impl.FileManagerServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@SecurityRequirement(name = "filemanager")
public class FileManagerController {

    @Autowired
    private FileManagerService fileManagerService;

    @Operation(  // Swagger/OpenAPI 3.x annotation to describe the endpoint
            summary = "Small summary of the end-point",
            description = "A detailed description of the end-point"
    )
    @PostMapping(
            value = "/uploadFile",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}  // Note the consumes in the mapping
    )

    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileManagerService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws IOException {
        Resource resource = fileManagerService.loadFileAsResource(fileName);
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {

        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
