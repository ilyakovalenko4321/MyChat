package com.IKov.MyChat_UserMicroservice.service.Impl;

import com.IKov.MyChat_UserMicroservice.domain.exception.BucketCreationException;
import com.IKov.MyChat_UserMicroservice.domain.profiles.ProfilePicture;
import com.IKov.MyChat_UserMicroservice.domain.profiles.UserProfile;
import com.IKov.MyChat_UserMicroservice.repository.ProfilePictureRepository;
import com.IKov.MyChat_UserMicroservice.service.ImageService;
import com.IKov.MyChat_UserMicroservice.service.props.MinioProps;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;


@Data
@Service
public class ImageServiceImpl implements ImageService {

    private final MinioClient minioClient;
    private final MinioProps minioProps;
    private final ProfilePictureRepository profilePictureRepository;

    @Override
    public ProfilePicture uploadImage(MultipartFile multipartFile, Long userId) {
        try{
            createBucket();
        } catch (Exception e){
            throw new BucketCreationException("Unable to create or load bucket");
        }

        String fileName = generateFileName(multipartFile);

        InputStream inputStream;

        try{
            inputStream = multipartFile.getInputStream();
        }catch (Exception e){
            throw new BucketCreationException("ddf");
        }

        String url = saveImage(inputStream, fileName);

        return saveImageUrl(url, userId);
    }

    @SneakyThrows
    private void createBucket(){
        boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProps.getBucket()).build());
        if(!bucketExists){
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProps.getBucket()).build());
        }
    }

    private String generateFileName(MultipartFile file){
        return UUID.randomUUID() + "." + getExtension(file);
    }

    private String getExtension(MultipartFile file){
        return file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.')+1);
    }

    @SneakyThrows
    private String saveImage(InputStream inputStream, String fileName){
        minioClient.putObject(PutObjectArgs.builder()
                .stream(inputStream, inputStream.available(), -1)
                .bucket(minioProps.getBucket())
                .object(fileName)
                .build());
        return fileName;
    }

    private ProfilePicture saveImageUrl(String url, Long userId){
        ProfilePicture profilePicture = new ProfilePicture();
        profilePicture.setUserId(userId);

        profilePicture.setPictureUrl(url);
        return profilePictureRepository.save(profilePicture);
    }

}
