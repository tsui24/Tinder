package com.tinder.tinder.repository;

import com.tinder.tinder.model.Matches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Matches, Long> {
}
