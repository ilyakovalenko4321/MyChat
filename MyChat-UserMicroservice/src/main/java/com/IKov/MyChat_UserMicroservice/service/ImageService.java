package com.IKov.MyChat_UserMicroservice.service;

import com.IKov.MyChat_UserMicroservice.domain.profiles.ProfilePicture;
import com.IKov.MyChat_UserMicroservice.domain.profiles.UserProfile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    ProfilePicture uploadImage(MultipartFile multipartFile, Long userId);

}
