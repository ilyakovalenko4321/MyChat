package com.IKov.MyChat_Recomendation.service.Impl;

import com.IKov.MyChat_Recomendation.domain.statistic.RecommendationStatistics;
import com.IKov.MyChat_Recomendation.domain.user.GENDER;
import com.IKov.MyChat_Recomendation.repository.StatisticRepository;
import com.IKov.MyChat_Recomendation.service.RedisStatisticsService;
import com.IKov.MyChat_Recomendation.service.StatisticsService;
import com.IKov.MyChat_Recomendation.domain.user.UserPropertiesToVectorize;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticRepository statisticRepository;
    private final RedisStatisticsService redisStatisticsService;

    @Override
    public RecommendationStatistics calculateNewAvg(UserPropertiesToVectorize userProperties) {
        RecommendationStatistics statistics = redisStatisticsService.getStatistics(userProperties.getGender());
        statistics.multiplyByNumber();
        statistics.add(userProperties);
        statistics.divideByNumber();
        log.info("Пересчитана статистика для пользователя {}", userProperties.getUserTag());
        return statistics;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void saveNewAverageUser() {
        RecommendationStatistics statisticsMALE = redisStatisticsService.getStatistics(GENDER.MALE);
        RecommendationStatistics statisticsFEMALE = redisStatisticsService.getStatistics(GENDER.FEMALE);

        statisticRepository.saveOrUpdate(statisticsMALE);
        statisticRepository.saveOrUpdate(statisticsFEMALE);
        log.info("Ежедневное сохранение статистики завершено");
    }
}
