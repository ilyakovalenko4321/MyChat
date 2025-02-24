package com.IKov.MyChat_UserMicroservice.domain.profiles;

import jakarta.persistence.*;
import lombok.Data;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "user_profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                           // Уникальный идентификатор пользователя

    @Column(nullable = false, unique = true)
    private String tag;                        // Имя пользователя

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false, unique = true)
    private String email;                      // Электронная почта

    private String phoneNumber;                // Номер телефона (необязательный)

    @Column(nullable = false)
    private Integer weight;

    @Column(nullable = false)
    private Double height;

    @Enumerated(EnumType.STRING)
    private HOBBY hobby;

    @Enumerated(EnumType.STRING)
    private PROFESSION profession;

    private Long earnings;

    @Column(nullable = false)
    private Integer age;             // Дата рождения

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GENDER gender;                     // Пол пользователя (например, MALE, FEMALE, OTHER)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ORIENTATION orientation;           // Ориентация (например, HETERO, HOMO, BI)

    @Column(length = 500)
    private String aboutMe;                    // Описание профиля

    @Column(nullable = false)
    private String city;                       // Город проживания

    @Column(nullable = false)
    private String country;                    // Страна

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // Дата создания профиля


    @Transient
    private List<String> pictures; // Хранение URL-ов или имен файлов изображений (можно переделать в отдельную сущность)

    public enum GENDER {
        MALE, FEMALE, OTHER
    }

    public enum ORIENTATION {
        HETERO, HOMO, BI
    }
}
