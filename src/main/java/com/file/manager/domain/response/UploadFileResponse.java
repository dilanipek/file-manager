package com.file.manager.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UploadFileResponse {
    private String fileName;
    private String fileUploadDirectory;
    private String fileExtension;
    private long size;

}
