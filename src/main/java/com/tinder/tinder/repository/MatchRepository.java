package com.tinder.tinder.repository;

import com.tinder.tinder.model.Matches;
import com.tinder.tinder.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Matches, Long> {
    List<Matches> findAllByUser1OrUser2(Users user1, Users user2);
}
