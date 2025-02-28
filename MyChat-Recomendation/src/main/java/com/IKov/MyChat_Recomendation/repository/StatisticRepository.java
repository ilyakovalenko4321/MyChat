package com.IKov.MyChat_Recomendation.repository;

import com.IKov.MyChat_Recomendation.domain.statistic.RecommendationStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface StatisticRepository extends JpaRepository<RecommendationStatistics, String> {

    @Query("SELECT rs FROM RecommendationStatistics rs WHERE rs.gender = :gender")
    RecommendationStatistics getAvg(@Param("gender") String gender);

    @Transactional
    default void saveOrUpdate(RecommendationStatistics newStatistics) {
        if (newStatistics != null) {
            save(newStatistics);
        }
    }

}
