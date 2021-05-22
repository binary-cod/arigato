package com.binarycod.arigato.services;

import com.binarycod.arigato.domain.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.iot.model.CannedAccessControlList;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AwsS3Service {

    @Autowired
    ImageService imageService;

    String bucketName = "arigatocommerce";
    S3Client s3Client;



    public AwsS3Service(){
        init();
    }

    private void init(){
        Region region = Region.EU_WEST_3;
        s3Client = S3Client
                .builder()
                .region(region)
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .build();
    }


    public void upload(MultipartFile file){
        String key = file.getOriginalFilename();

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .acl(String.valueOf(CannedAccessControlList.PUBLIC_READ))
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.putObject(objectRequest,
                RequestBody.fromFile(convertFromMultipart(file)));

        //image is on db
        Image image = new Image();
        image.setName(key);
        image.setLink("https://arigatocommerce.s3.eu-west-3.amazonaws.com/"+key);
        imageService.create(image);

    }

    public List<Image> listAllImages(){
        List<S3Object> s3ObjectList = listBucketObjects(s3Client, bucketName);
        return s3ObjectList
                .stream()
                .map(s3Object -> {
                    Image image = new Image();
                    image.setName(s3Object.key());
                    image.setLink("https://arigatocommerce.s3.eu-west-3.amazonaws.com/"+s3Object.key());
                    return image;
                }).collect(Collectors.toList());
    }

    private List<S3Object> listBucketObjects(S3Client s3Client, String bucketName) {
        try {
            ListObjectsRequest listObjectsRequest = ListObjectsRequest
                    .builder()
                    .bucket(bucketName)
                    .build();

            ListObjectsResponse listObjectsResponse = s3Client.listObjects(listObjectsRequest);
           return listObjectsResponse.contents();
        } catch (S3Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void delete(String key){
        DeleteObjectRequest deleteObjectRequest= DeleteObjectRequest
                .builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
        imageService.deleteImage(key);

    }
    private File convertFromMultipart(MultipartFile multipartFile){
        File convertedFile = new File(multipartFile.getOriginalFilename());
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(convertedFile);
            fileOutputStream.write(multipartFile.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertedFile;

    }
}

