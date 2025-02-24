package com.IKov.MyChat_UserMicroservice.repository;

import com.IKov.MyChat_UserMicroservice.domain.location.Location;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO user_locations (user_tag, city, country, location)
            VALUES (:userId, :city, :country, ST_GeomFromText(:location, 4326))
            """, nativeQuery = true)
    void saveUserLocation(String userId, String city, String country, String location);

}
