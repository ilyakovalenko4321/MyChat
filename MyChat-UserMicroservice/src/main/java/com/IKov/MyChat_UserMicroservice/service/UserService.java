package com.IKov.MyChat_UserMicroservice.service;

import com.IKov.MyChat_UserMicroservice.web.dto.UserProfileDto;

public interface UserService {

    void create(UserProfileDto userProfileDto);

}
