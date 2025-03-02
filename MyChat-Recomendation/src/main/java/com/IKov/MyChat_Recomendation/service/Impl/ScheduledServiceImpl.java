package com.IKov.MyChat_Recomendation.service.Impl;

import com.IKov.MyChat_Recomendation.domain.statistic.RecommendationStatistics;
import com.IKov.MyChat_Recomendation.domain.user.GENDER;
import com.IKov.MyChat_Recomendation.repository.StatisticRepository;
import com.IKov.MyChat_Recomendation.repository.TemporaryRepository;
import com.IKov.MyChat_Recomendation.service.RedisStatisticsService;
import com.IKov.MyChat_Recomendation.service.UserVectorizeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduledServiceImpl {

    private final StatisticRepository statisticRepository;
    private final RedisStatisticsService redisStatisticsService;
    private final TemporaryRepository temporaryRepository;
    private final UserVectorizeService vectorizeService;

    @Scheduled(cron = "0 0 0 * * *")
    public void saveNewAverageUser() {
        RecommendationStatistics statisticsMALE = redisStatisticsService.getStatistics(GENDER.MALE);
        RecommendationStatistics statisticsFEMALE = redisStatisticsService.getStatistics(GENDER.FEMALE);

        statisticRepository.saveOrUpdate(statisticsMALE);
        statisticRepository.saveOrUpdate(statisticsFEMALE);
        log.info("Ежедневное сохранение статистики завершено");
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void setNewTemporaryData(){
        List<String> userTags = temporaryRepository.getAllTags();
        temporaryRepository.deleteAll();
        userTags.forEach(userTag -> {

        });
    }
}
