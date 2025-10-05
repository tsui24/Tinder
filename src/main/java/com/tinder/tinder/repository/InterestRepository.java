package com.tinder.tinder.repository;

import com.tinder.tinder.model.Interests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestRepository extends JpaRepository<Interests, Long> {
}
