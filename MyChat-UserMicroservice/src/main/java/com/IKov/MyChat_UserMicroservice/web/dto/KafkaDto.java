package com.IKov.MyChat_UserMicroservice.web.dto;

import com.IKov.MyChat_UserMicroservice.domain.profiles.Profile;
import lombok.Data;
import org.checkerframework.checker.units.qual.K;

@Data
public class KafkaDto {

    private String userTag;

    private Double height;

    private Long age;

    private Integer weight;

    private Profile.GENDER gender;

    private Long earnings;

    private Integer beauty;

    private Double personalityExtraversion; // Экстраверсия

    private Double personalityOpenness;     // Открытость новому опыту

    private Double personalityConscientiousness; // Добросовестность

    private Double lifeValueFamily; // Важность семьи

    private Double lifeValueCareer; // Важность карьеры

    private Double activityLevel; // Физическая/социальная активность

}
