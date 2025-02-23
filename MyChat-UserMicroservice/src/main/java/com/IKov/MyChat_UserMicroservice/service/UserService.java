package com.IKov.MyChat_UserMicroservice.service;

import com.IKov.MyChat_UserMicroservice.web.dto.UserProfileDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    void create(UserProfileDto userProfileDto);

    void upload(List<MultipartFile> multipartFiles, Long userId);

}
