package com.IKov.MyChat_UserMicroservice.service.Impl;

import com.IKov.MyChat_UserMicroservice.domain.location.Location;
import com.IKov.MyChat_UserMicroservice.domain.profiles.Profile;
import com.IKov.MyChat_UserMicroservice.repository.LocationRepository;
import com.IKov.MyChat_UserMicroservice.repository.UserRepository;
import com.IKov.MyChat_UserMicroservice.service.ImageService;
import com.IKov.MyChat_UserMicroservice.service.UserService;
import com.IKov.MyChat_UserMicroservice.web.dto.UserProfileDto;
import com.IKov.MyChat_UserMicroservice.web.mapper.UserProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ImageService imageService;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    @Override
    @Transactional
    public void create(UserProfileDto userProfileDto) {
        Profile profile = saveProfile(userProfileDto);
        Location location = saveLocation(userProfileDto, profile);
        uploadImages(userProfileDto.getPictures(), profile.getTag()); // Сохраняем через tag
    }

    private Profile saveProfile(UserProfileDto userProfileDto) {
        Profile profile = UserProfileMapper.toEntity(userProfileDto);
        return userRepository.save(profile); // Сохраняем и возвращаем профиль
    }

    private Location saveLocation(UserProfileDto userProfileDto, Profile profile) {
        Location location = UserProfileMapper.toLocation(userProfileDto);
        location.setUserTag(profile.getTag());  // Используем tag вместо id
        locationRepository.saveUserLocation(profile.getTag(), location.getCity(), location.getCountry(), location.getLocation().toString());
        return location;
    }

    private void uploadImages(List<MultipartFile> multipartFiles, String userTag) {  // Используем tag вместо id
        multipartFiles.forEach(file -> imageService.uploadImage(file, userTag)); // Используем tag
    }
}
