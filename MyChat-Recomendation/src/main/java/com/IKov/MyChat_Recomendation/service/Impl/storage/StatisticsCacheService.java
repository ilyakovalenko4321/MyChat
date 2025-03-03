package com.IKov.MyChat_Recomendation.service.Impl.storage;

import com.IKov.MyChat_Recomendation.domain.statistic.RecommendationStatistics;
import com.IKov.MyChat_Recomendation.domain.user.GENDER;
import com.IKov.MyChat_Recomendation.service.RedisStatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticsCacheService implements RedisStatisticsService {

    private final RedisTemplate<String, Object> redisTemplate;

    private String getKey(GENDER gender) {
        return "statistics:" + gender.name().toLowerCase();
    }

    @Override
    public void saveStatistics(GENDER gender, RecommendationStatistics stats) {
        redisTemplate.opsForValue().set(getKey(gender), stats);
        log.info("Статистика для {} сохранена в Redis.", gender);
    }

    @Override
    public RecommendationStatistics getStatistics(GENDER gender) {
        RecommendationStatistics recommendationStatistics = (RecommendationStatistics) redisTemplate.opsForValue().get(getKey(gender));
        if (recommendationStatistics != null && recommendationStatistics.getAvgAge() != null) {
            return recommendationStatistics;
        } else if (gender == GENDER.MALE) {
            return RecommendationStatistics.averageMALE();
        } else {
            return RecommendationStatistics.averageFEMALE();
        }
    }

    @Override
    public void deleteStatistics(GENDER gender) {
        redisTemplate.delete(getKey(gender));
        log.info("Статистика для {} удалена из Redis.", gender);
    }
}
