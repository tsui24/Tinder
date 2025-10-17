package com.tinder.tinder.repository;

import com.tinder.tinder.model.Likes;
import com.tinder.tinder.model.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Likes, Long> {
    List<Likes> findByCreatedAtBefore(LocalDateTime time);

    @Transactional
    @Modifying
    @Query("DELETE FROM Likes l WHERE l.createdAt < :time")
    int deleteOldLikes(LocalDateTime time);

    boolean existsByFromUserAndToUser(Users fromUser, Users toUser);
}
