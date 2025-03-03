package com.IKov.MyChat_Recomendation.service.Impl;

import com.IKov.MyChat_Recomendation.domain.statistic.RecommendationStatistics;
import com.IKov.MyChat_Recomendation.domain.user.GENDER;
import com.IKov.MyChat_Recomendation.domain.user.UserTemporalData;
import com.IKov.MyChat_Recomendation.domain.vector.VectorizedUser;
import com.IKov.MyChat_Recomendation.domain.vector.VectorizedUserMapper;
import com.IKov.MyChat_Recomendation.domain.vector.VectorizedUserSql;
import com.IKov.MyChat_Recomendation.repository.StatisticRepository;
import com.IKov.MyChat_Recomendation.repository.TemporaryRepository;
import com.IKov.MyChat_Recomendation.repository.VectorizedUserRepositorySql;
import com.IKov.MyChat_Recomendation.service.ElasticsearchService;
import com.IKov.MyChat_Recomendation.service.RedisStatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduledServiceImpl {

    private final StatisticRepository statisticRepository;
    private final RedisStatisticsService redisStatisticsService;
    private final TemporaryRepository temporaryRepository;
    private final ElasticsearchService elasticsearchService;
    private final VectorizedUserRepositorySql vectorizedUserRepositorySql;
    private final VectorizedUserMapper vectorizedUserMapper;

    @Scheduled(cron = "0 0 0 * * *")
    public void saveNewAverageUser() {
        RecommendationStatistics statisticsMALE = redisStatisticsService.getStatistics(GENDER.MALE);
        RecommendationStatistics statisticsFEMALE = redisStatisticsService.getStatistics(GENDER.FEMALE);

        statisticRepository.saveOrUpdate(statisticsMALE);
        statisticRepository.saveOrUpdate(statisticsFEMALE);
        log.info("Ежедневное сохранение статистики завершено");
    }

//    @Scheduled(cron = "0 */2 * * * *") // Запуск каждый день в полночь
//    @Transactional
//    public void setNewTemporaryData() {
//        int offset = 0;
//        boolean hasMoreRows = true;
//
//        // Генератор случайных чисел
//        Random random = new Random();
//
//        while (hasMoreRows) {
//            // Выбираем пачку user_tag
//            int BATCH_SIZE = 1000;
//            List<UserTemporalData> userTemporalDataList = temporaryRepository.findAllWithOffsetAndLimit(offset, BATCH_SIZE);
//
//            // Если пачка пустая, завершаем цикл
//            if (userTemporalDataList.isEmpty()) {
//                hasMoreRows = false;
//            } else {
//                // Обновляем каждую строку в текущей пачке
//                for (UserTemporalData userTemporalData : userTemporalDataList) {
//                    int newTemporaryTableValue = random.nextInt(5) + 1; // Случайное число от 1 до 5
//                    userTemporalData.setTemporaryTable(newTemporaryTableValue);
//                    temporaryRepository.save(userTemporalData);
//                }
//
//                offset += BATCH_SIZE;
//            }
//        }
//
//        elasticsearchService.dropAll();
//
//        offset = 0;
//        hasMoreRows = true;
//
//        while (hasMoreRows) {
//            // Выбираем пачку user_tag
//            int BATCH_SIZE = 1000;
//            List<UserTemporalData> userTemporalDataList = temporaryRepository.findAllWithOffsetAndLimit(offset, BATCH_SIZE);
//
//            // Если пачка пустая, завершаем цикл
//            if (userTemporalDataList.isEmpty()) {
//                hasMoreRows = false;
//            } else {
//                userTemporalDataList.forEach(user -> {
//                    VectorizedUserSql vectorizedUserSql = vectorizedUserRepositorySql.getByUserTag(user.getUserTag());
//                    VectorizedUser vectorizedUser = vectorizedUserMapper.toElasticsearchEntity(vectorizedUserSql);
//                    elasticsearchService.saveUser(vectorizedUser, user);
//                });
//                offset += BATCH_SIZE;
//            }
//        }
//    }

    @Scheduled(cron = "0 0 2 * * *") // Запуск каждый день в 2 AM
    @Transactional
    public void setNewTemporaryData() {
        Random random = new Random();
        int offset = 0;
        int BATCH_SIZE = 1000;
        List<UserTemporalData> batch;

        while (!(batch = temporaryRepository.findAllWithOffsetAndLimit(offset, BATCH_SIZE)).isEmpty()) {
            batch.forEach(user -> {
                user.setTemporaryTable(random.nextInt(5) + 1);
                user.setOffsetUsers(0);
            });
            temporaryRepository.saveAll(batch);
            offset += BATCH_SIZE;
        }

        elasticsearchService.dropAll();
        processAndSaveToElasticsearch();
    }

    private void processAndSaveToElasticsearch() {
        int offset = 0;
        int BATCH_SIZE = 1000;
        List<UserTemporalData> batch;

        while (!(batch = temporaryRepository.findAllWithOffsetAndLimit(offset, BATCH_SIZE)).isEmpty()) {
            List<VectorizedUser> users = batch.parallelStream().map(user -> {
                VectorizedUserSql sqlUser = vectorizedUserRepositorySql.getByUserTag(user.getUserTag());
                return vectorizedUserMapper.toElasticsearchEntity(sqlUser);
            }).collect(Collectors.toList());

            elasticsearchService.saveAllUsers(users, batch);
            offset += BATCH_SIZE;
        }
    }
}
