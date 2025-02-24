package com.IKov.MyChat_UserMicroservice.repository;

import com.IKov.MyChat_UserMicroservice.domain.pictures.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilePictureRepository extends JpaRepository<Avatar, Long> {



}
