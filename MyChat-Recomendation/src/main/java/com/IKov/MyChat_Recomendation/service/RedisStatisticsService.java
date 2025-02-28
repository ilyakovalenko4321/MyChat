package com.IKov.MyChat_Recomendation.service;

import com.IKov.MyChat_Recomendation.domain.statistic.RecommendationStatistics;
import com.IKov.MyChat_Recomendation.domain.user.GENDER;

public interface RedisStatisticsService {

    RecommendationStatistics getStatistics(GENDER gender);

    void deleteStatistics(GENDER gender);

    void saveStatistics(GENDER gender, RecommendationStatistics stats);

}
