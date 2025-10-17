package com.tinder.tinder.repository;

import com.tinder.tinder.dto.response.UserMatchResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.tinder.tinder.model.Users;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
    @Query(value = "SELECT id as userId, full_name as fullName, YEAR(CURDATE()) - year(birthday) as age, location, " +
            "       ST_Distance_Sphere(POINT(address_lon, address_lat), POINT(:lon, :lat)) AS distanceKm " +
            "FROM user " +
            "WHERE ST_Distance_Sphere(POINT(address_lon, address_lat), POINT(:lon, :lat)) < :range and id <> :userId and interested_in <> :interestedIn " +
            "ORDER BY distanceKm;", nativeQuery = true)
    List<UserMatchResult> findAllExcept(@Param("userId") Long userId, @Param("interestedIn") Integer interestedIn,
                                        @Param("lat") String lat, @Param("lon") String lon, @Param("range") double range);
}
