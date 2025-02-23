package com.IKov.MyChat_UserMicroservice.repository;

import com.IKov.MyChat_UserMicroservice.domain.profiles.UserProfile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserProfile, Long> {



}
