package com.binarycod.arigato.services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileStorageServiceImpl implements FileStorageService{

    @Value("${app.upload.location:${user.home}}")
    public String storageLocation;

    @Override
    public void init() {

    }

    @Override
    public void store(MultipartFile file) {
        Path uploadLocation = Paths.get(storageLocation
                + File.separator
                + StringUtils.cleanPath(file.getOriginalFilename()));

        try {
            Files.copy(file.getInputStream(), uploadLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }
}
