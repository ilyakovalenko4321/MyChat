package com.IKov.MyChat_Recomendation.service.Impl;

import com.IKov.MyChat_Recomendation.domain.statistic.RecommendationStatistics;
import com.IKov.MyChat_Recomendation.domain.user.UserTemporalData;
import com.IKov.MyChat_Recomendation.domain.vector.VectorizedUser;
import com.IKov.MyChat_Recomendation.domain.vector.VectorizedUserMapper;
import com.IKov.MyChat_Recomendation.domain.vector.VectorizedUserSql;
import com.IKov.MyChat_Recomendation.repository.TemporaryRepository;
import com.IKov.MyChat_Recomendation.repository.VectorizedUserRepository;
import com.IKov.MyChat_Recomendation.repository.VectorizedUserRepositorySql;
import com.IKov.MyChat_Recomendation.service.ElasticsearchService;
import com.IKov.MyChat_Recomendation.service.RedisStatisticsService;
import com.IKov.MyChat_Recomendation.service.StatisticsService;
import com.IKov.MyChat_Recomendation.domain.user.UserPropertiesToVectorize;
import com.IKov.MyChat_Recomendation.service.UserVectorizeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserVectorizeServiceImpl implements UserVectorizeService {

    private final StatisticsService statisticsService;
    private final RedisStatisticsService redisStatisticsService;
    private final ElasticsearchService elasticsearchService;
    private final TemporaryRepository temporaryRepository;
    private final VectorizedUserRepositorySql vectorizedUserRepositorySql   ;
    private final VectorizedUserMapper vectorizedUserMapper;

    @Override
    public void vectorize(UserPropertiesToVectorize properties) {
        VectorizedUser vectorizedUser = vectorizeByGender(properties);
        UserTemporalData temporalData = createTemporaryData(vectorizedUser);
        VectorizedUserSql vectorizedUserSql = vectorizedUserMapper.toSqlEntity(vectorizedUser);

        elasticsearchService.saveUser(vectorizedUser, temporalData);
        temporaryRepository.save(temporalData);
        vectorizedUserRepositorySql.save(vectorizedUserSql);

        updateStatisticsWithNewUser(properties);
        log.info("Пользователь {} векторизован и сохранён", properties.getUserTag());
    }

    private VectorizedUser vectorizeByGender(UserPropertiesToVectorize user) {
        RecommendationStatistics stats = redisStatisticsService.getStatistics(user.getGender());
        if (stats == null) {
            throw new IllegalStateException("Нет статистики для пола: " + user.getGender());
        }
        if(user.getGender() == null){
            throw new IllegalStateException("Нет пользователя" + user.getUserTag());
        }

        double[] vector = buildNotNormalizedUserVector(user, stats);

        VectorizedUser vectorizedUser = new VectorizedUser();
        vectorizedUser.setUserTag(user.getUserTag());
        vectorizedUser.setGender(user.getGender());
        vectorizedUser.setVector(vector);
        return vectorizedUser;
    }

    private void updateStatisticsWithNewUser(UserPropertiesToVectorize properties) {
        RecommendationStatistics updatedStats = statisticsService.calculateNewAvg(properties);
        redisStatisticsService.saveStatistics(properties.getGender(), updatedStats);
        log.info("Обновлена статистика для пользователя {}", properties.getUserTag());
    }

    private double[] buildNotNormalizedUserVector(UserPropertiesToVectorize user, RecommendationStatistics stats){
        return new double[]{
                user.getHeight() != null ? user.getHeight() : stats.getAvgHeight(),
                user.getAge() != null ? user.getAge() : stats.getAvgAge(),
                user.getWeight() != null ? user.getWeight() : stats.getAvgWeight(),
                user.getEarnings() != null ? user.getEarnings() : stats.getAvgEarnings(),
                user.getBeauty() != null ? user.getBeauty() : stats.getAvgBeauty(),
                user.getPersonalityExtraversion() != null ? user.getPersonalityExtraversion() : stats.getAvgPersonalityExtraversion(),
                user.getPersonalityOpenness() != null ? user.getPersonalityOpenness() : stats.getAvgPersonalityOpenness(),
                user.getPersonalityConscientiousness() != null ? user.getPersonalityConscientiousness() : stats.getAvgPersonalityConscientiousness(),
                user.getLifeValueFamily() != null ? user.getLifeValueFamily() : stats.getAvgLifeValueFamily(),
                user.getLifeValueCareer() != null ? user.getLifeValueCareer() : stats.getAvgLifeValueCareer(),
                user.getActivityLevel() != null ? user.getActivityLevel() : stats.getAvgActivityLevel()
        };
    }

    private UserTemporalData createTemporaryData(VectorizedUser vectorize){
        Random random = new Random();
        UserTemporalData temporalData = new UserTemporalData();
        temporalData.setOffsetUsers(0);
        temporalData.setUserTag(vectorize.getUserTag());
        int tableNumber = Math.abs(vectorize.getUserTag().hashCode()) % 5;
        temporalData.setTemporaryTable(tableNumber);
        return temporalData;
    }
}
