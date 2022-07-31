package com.file.manager.service.impl;

import com.file.manager.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService
{

    private final ResourceLoader resourceLoader;

    @Override
    public Resource loadFileAsResource( )
    {
        return resourceLoader.getResource( "asdasd" );
    }
}