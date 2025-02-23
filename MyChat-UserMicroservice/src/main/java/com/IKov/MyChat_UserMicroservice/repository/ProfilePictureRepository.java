package com.IKov.MyChat_UserMicroservice.repository;

import com.IKov.MyChat_UserMicroservice.domain.profiles.ProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, Long> {



}
