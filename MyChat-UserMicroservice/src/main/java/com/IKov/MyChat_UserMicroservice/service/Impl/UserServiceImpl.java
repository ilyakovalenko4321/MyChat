package com.IKov.MyChat_UserMicroservice.service.Impl;

import com.IKov.MyChat_UserMicroservice.domain.profiles.ProfilePicture;
import com.IKov.MyChat_UserMicroservice.domain.profiles.UserLocation;
import com.IKov.MyChat_UserMicroservice.domain.profiles.UserProfile;
import com.IKov.MyChat_UserMicroservice.repository.LocationRepository;
import com.IKov.MyChat_UserMicroservice.repository.ProfilePictureRepository;
import com.IKov.MyChat_UserMicroservice.repository.UserRepository;
import com.IKov.MyChat_UserMicroservice.service.ImageService;
import com.IKov.MyChat_UserMicroservice.service.UserService;
import com.IKov.MyChat_UserMicroservice.web.dto.UserProfileDto;
import com.IKov.MyChat_UserMicroservice.web.mapper.UserProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ImageService imageService;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final UserProfileMapper userProfileMapper;

    @Override
    @Transactional
    public void create(UserProfileDto userProfileDto) {
        UserProfile userProfile = userProfileMapper.toEntity(userProfileDto);
        UserLocation location = userProfileMapper.toLocation(userProfileDto);
        userProfile = userRepository.save(userProfile);
        location.setUser_id(userProfile.getId());
        locationRepository.saveUserLocation(userProfile.getId(), location.getCity(), location.getCountry(), location.getLocation().toString());

        upload(userProfileDto.getPictures(), userProfile.getId());
    }

    @Override
    @Transactional
    public void upload(List<MultipartFile> multipartFiles, Long userId){
        for(MultipartFile file: multipartFiles){
            imageService.uploadImage(file, userId);
        }
    }
}
