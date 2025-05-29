package com.example.TTS_web.backend.repository;

import com.example.TTS_web.backend.entity.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {
    @Query(value = "select * from user_history " +
            "where user_id=:userId", nativeQuery = true)
    List<UserHistory> findByUserId(long userId);
}
