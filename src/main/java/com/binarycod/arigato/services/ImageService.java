package com.binarycod.arigato.services;

import com.binarycod.arigato.domain.Image;
import com.binarycod.arigato.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    @Autowired
    ImageRepository imageRepository;

    public void create(Image image){
        imageRepository.save(image);
    }

    public List<Image> getImageList(){
        return imageRepository.findAll();
    }

    public void deleteImage(String key){
        imageRepository.deleteById(key);
    }
}
