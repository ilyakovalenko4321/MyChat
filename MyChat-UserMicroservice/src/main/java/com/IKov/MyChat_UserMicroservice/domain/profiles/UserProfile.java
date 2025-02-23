package com.IKov.MyChat_UserMicroservice.domain.profiles;

import jakarta.persistence.*;
import lombok.Data;
import org.locationtech.jts.geom.Point;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                           // Уникальный идентификатор пользователя
    private String tag;                        // Имя пользователя
    private String name;
    private String surname;
    private String email;                      // Электронная почта
    private String phoneNumber;                // Номер телефона (необязательный)
    private LocalDate dateOfBirth;             // Дата рождения
    @Enumerated(EnumType.STRING)
    private GENDER gender;                     // Пол пользователя (например, MALE, FEMALE, OTHER)
    @Enumerated(EnumType.STRING)
    private ORIENTATION orientation;           // Ориентация (например, HETERO, HOMO, BI)
    private String aboutMe;                    // Описание профиля
    private LocalDateTime createdAt;           // Дата создания профиля


    public enum GENDER{
        MALE, FEMALE, OTHER
    }

    public enum ORIENTATION{
        HETERO, HOMO, BI
    }
}