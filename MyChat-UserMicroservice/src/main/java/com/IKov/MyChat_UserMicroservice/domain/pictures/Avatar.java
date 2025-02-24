package com.IKov.MyChat_UserMicroservice.domain.pictures;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "profile_pictures")
@Data
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userTag;

    private String pictureUrl;
}