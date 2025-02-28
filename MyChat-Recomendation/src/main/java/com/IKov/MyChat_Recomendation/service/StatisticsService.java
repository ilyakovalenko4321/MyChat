package com.IKov.MyChat_Recomendation.service;

import com.IKov.MyChat_Recomendation.domain.statistic.RecommendationStatistics;
import com.IKov.MyChat_Recomendation.domain.user.UserPropertiesToVectorize;

public interface StatisticsService {

    RecommendationStatistics calculateNewAvg(UserPropertiesToVectorize userProperties);


}
