package com.IKov.MyChat_Recomendation.repository;

import com.IKov.MyChat_Recomendation.domain.user.UserTemporalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TemporaryRepository extends JpaRepository<UserTemporalData, String> {

    @Query(value = """
            SELECT user_tag FROM user_temporal_data""", nativeQuery = true)
    List<String> getAllTags();

    @Transactional
    @Modifying
    @Query(value = "TRUNCATE TABLE user_temporal_data", nativeQuery = true)
    void deleteAll();
}
