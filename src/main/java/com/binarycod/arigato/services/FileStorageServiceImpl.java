package com.binarycod.arigato.services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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
            System.out.println("URI will be "+ buildUrl(file.getName()).getURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Resource buildUrl(String filename){

        Path file = Paths.get(storageLocation).resolve(filename);
        try {
            Resource resource = new UrlResource(file.toUri());
            return resource;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public Stream<Path> loadAll() {
        Path root = Paths.get(storageLocation);
        try {
            return Files.walk(root, 1).filter(path -> !path.equals(root)).map(root :: relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load files");
        }
    }
}
