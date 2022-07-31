package com.file.manager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileManagerException extends RuntimeException{
    public FileManagerException(String message){
        super(message);
    }
    public FileManagerException(String message,Throwable cause){
        super(message,cause);
    }
}
