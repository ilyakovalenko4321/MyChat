package com.IKov.MyChat_UserMicroservice.service;

import com.IKov.MyChat_UserMicroservice.domain.pictures.Avatar;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    Avatar uploadImage(MultipartFile multipartFile, String userTag);

}
