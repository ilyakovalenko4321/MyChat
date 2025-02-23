package com.IKov.MyChat_UserMicroservice.web.mapper;

import com.IKov.MyChat_UserMicroservice.domain.profiles.UserProfile;
import com.IKov.MyChat_UserMicroservice.domain.profiles.UserLocation;
import com.IKov.MyChat_UserMicroservice.web.dto.UserProfileDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserProfileMapper {

    public UserProfile toEntity(UserProfileDto dto) {
        UserProfile userProfile = new UserProfile();
        userProfile.setTag(dto.getTag());
        userProfile.setName(dto.getName());
        userProfile.setSurname(dto.getSurname());
        userProfile.setEmail(dto.getEmail());
        userProfile.setPhoneNumber(dto.getPhoneNumber());
        userProfile.setDateOfBirth(dto.getDateOfBirth());
        userProfile.setGender(dto.getGender());
        userProfile.setOrientation(dto.getOrientation());
        userProfile.setAboutMe(dto.getAboutMe());
        userProfile.setCreatedAt(LocalDateTime.now());


        return userProfile;
    }

    public UserProfileDto toDto(UserProfile userProfile) {
        UserProfileDto dto = new UserProfileDto();
        dto.setTag(userProfile.getTag());
        dto.setName(userProfile.getName());
        dto.setSurname(userProfile.getSurname());
        dto.setEmail(userProfile.getEmail());
        dto.setPhoneNumber(userProfile.getPhoneNumber());
        dto.setDateOfBirth(userProfile.getDateOfBirth());
        dto.setGender(userProfile.getGender());
        dto.setOrientation(userProfile.getOrientation());
        dto.setAboutMe(userProfile.getAboutMe());
        dto.setCreatedAt(userProfile.getCreatedAt());



        return dto;
    }

    public UserLocation toLocation(UserProfileDto dto) {
        UserLocation location = new UserLocation();
        location.setCity(dto.getCity());
        location.setCountry(dto.getCountry());

        if (dto.getLocation() != null) {
            location.setLocation(dto.getLocation());
        }

        return location;
    }
}
