package com.tinder.tinder.repository;

import com.tinder.tinder.model.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    List<MessageEntity> findByMatchIdOrderBySentAtAsc(Long matchId);
}
