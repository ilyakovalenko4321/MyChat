package com.IKov.MyChat_UserMicroservice.domain.profiles;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "profile_pictures")
@Data
public class ProfilePicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String pictureUrl;
}