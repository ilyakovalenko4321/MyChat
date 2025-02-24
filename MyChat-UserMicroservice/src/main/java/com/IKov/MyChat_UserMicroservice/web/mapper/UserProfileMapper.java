package com.IKov.MyChat_UserMicroservice.web.mapper;

import com.IKov.MyChat_UserMicroservice.domain.location.Location;
import com.IKov.MyChat_UserMicroservice.domain.profiles.Profile;
import com.IKov.MyChat_UserMicroservice.web.dto.UserProfileDto;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapper {

    public static UserProfileDto toDto(Profile profile) {
        if (profile == null) {
            return null;
        }
        UserProfileDto dto = new UserProfileDto();
        dto.setTag(profile.getTag());
        dto.setEmail(profile.getEmail());
        dto.setName(profile.getName());
        dto.setSurname(profile.getSurname());
        dto.setPhoneNumber(profile.getPhoneNumber());
        dto.setWeight(profile.getWeight());
        dto.setHeight(profile.getHeight());
        dto.setHobby(profile.getHobby());
        dto.setProfession(profile.getProfession());
        dto.setEarnings(profile.getEarnings());
        dto.setAge(profile.getAge());
        dto.setGender(profile.getGender());
        dto.setOrientation(profile.getOrientation());
        dto.setAboutMe(profile.getAboutMe());
        dto.setCity(profile.getCity());
        dto.setCountry(profile.getCountry());
        dto.setCreatedAt(profile.getCreatedAt());

        return dto;
    }

    public static Profile toEntity(UserProfileDto dto) {
        if (dto == null) {
            return null;
        }
        Profile profile = new Profile();
        profile.setTag(dto.getTag());
        profile.setEmail(dto.getEmail());
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        profile.setPhoneNumber(dto.getPhoneNumber());
        profile.setWeight(dto.getWeight());
        profile.setHeight(dto.getHeight());
        profile.setHobby(dto.getHobby());
        profile.setProfession(dto.getProfession());
        profile.setEarnings(dto.getEarnings());
        profile.setAge(dto.getAge());
        profile.setGender(dto.getGender());
        profile.setOrientation(dto.getOrientation());
        profile.setAboutMe(dto.getAboutMe());
        profile.setCity(dto.getCity());
        profile.setCountry(dto.getCountry());
        profile.setCreatedAt(dto.getCreatedAt());
        return profile;
    }

    public static Location toLocation(UserProfileDto dto) {
        Location location = new Location();
        location.setCity(dto.getCity());
        location.setCountry(dto.getCountry());

        if (dto.getLocation() != null) {
            location.setLocation(dto.getLocation());
        }

        return location;
    }
}